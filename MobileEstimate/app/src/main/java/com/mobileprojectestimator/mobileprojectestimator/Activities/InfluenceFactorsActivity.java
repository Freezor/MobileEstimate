package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database.DatabaseInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.LoggingHelper;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.InfluenceListAdapter;

import java.util.ArrayList;

@SuppressWarnings("JavaDoc")
public class InfluenceFactorsActivity extends DatabaseActivity
{

    private TextView estimationMethodName;
    private Spinner influenceFactorSetSpinner;
    private TextView sumOfInfluences;
    private ListView influenceFactorItemsList;

    private ArrayList<String> estimationMethodsList;

    private ArrayList<DatabaseInfluenceFactorItem> dbInfluenceFactorItems;

    private String selectedEstimationMethod;

    private InfluenceListAdapter influenceListAdapter;
    private ArrayList<String> influenceFactorNames;
    private ArrayList<Integer> influenceFactorIds;
    private ArrayAdapter<String> dataAdapter;
    private LoggingHelper loggingHelper;
    private ArrayList<String> factornames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        loggingHelper = new LoggingHelper(this);
        initDatabase();

        setContentView(R.layout.activity_influence_factors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInfluenceFactorActivity);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addNewInfluenceFactor);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onClickCreateNewInfluenceFactor();
            }
        });

        View influenceFactorContent = findViewById(R.id.influenceFactorContent);
        estimationMethodName = (TextView) influenceFactorContent.findViewById(R.id.tvEstimationMethodName);
        influenceFactorSetSpinner = (Spinner) influenceFactorContent.findViewById(R.id.influenceFactorSetSpinner);
        ImageView editinfluenceFactorSet = (ImageView) influenceFactorContent.findViewById(R.id.ivEditInfluenceFactor);
        sumOfInfluences = (TextView) influenceFactorContent.findViewById(R.id.tvSumOfInfluences);
        influenceFactorItemsList = (ListView) influenceFactorContent.findViewById(R.id.lvInfluenceFactors);

        loadEstimationMethods();

        loadInfluenceFactorNames();

        loadInfluenceFactor(influenceFactorNames.get(0));

        influenceFactorItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                openFactorInfoDialog(factornames.get(position));
            }
        });

        estimationMethodName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfluenceFactorsActivity.this);
                final CharSequence[] items = estimationMethodsList.toArray(new CharSequence[estimationMethodsList.size()]);
                builder.setTitle("Choose an estimation Method")
                        .setItems(items, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                selectedEstimationMethod = estimationMethodsList.get(which);
                                estimationMethodName.setText(selectedEstimationMethod);
                                loadInfluenceFactorNames();
                                loadInfluenceFactor(influenceFactorNames.get(0));
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = builder.create();

                // show it
                alertDialog.show();
            }
        });

        editinfluenceFactorSet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(InfluenceFactorsActivity.this, CreateNewInfluenceFactorActivity.class);
                i.putExtra(getString(R.string.ISNEWFACTOR), false);
                i.putExtra(getString(R.string.NEWFACTORESTIMATIONTECHNIQUE), selectedEstimationMethod);
                i.putExtra(getString(R.string.NEWFACTORINFLUENCESETNAME), influenceFactorNames.get(influenceFactorSetSpinner.getSelectedItemPosition()));
                i.putExtra(getString(R.string.NEWFACTORINFLUENCESETID), influenceFactorIds.get(influenceFactorSetSpinner.getSelectedItemPosition()));
                startActivityForResult(i, Integer.parseInt(getString(R.string.edit_influence_factor_request_code)));
            }
        });
    }

    private void openFactorInfoDialog(String item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item);
        builder.setMessage(databaseHelper.getXmlHelper().loadDescriptionText(item.replace(" ", "_").toLowerCase()));
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        builder.show();
    }

    /**
     * Loads the influence factors from the database
     *
     * @param factorName
     */
    private void loadInfluenceFactor(String factorName)
    {
        if (selectedEstimationMethod.equals(getString(R.string.estimation_technique_function_point)))
        {
            InfluencingFactor influencingFactor = new InfluencingFactor(this, InfluencingFactor.FUNCTIONPOINTFACTORS);
            factornames = new ArrayList<>();
            for (DatabaseInfluenceFactorItem item : dbInfluenceFactorItems)
            {
                if (item.get_name().equals(factorName))
                {
                    ArrayList<Integer> factorValues = databaseHelper.loadFunctionPointInfluenceValues(item.get_influenceFactorId());
                    influencingFactor.setFunctionPointValuesFromArrayList(factorValues);
                    break;
                }
            }

            for (InfluenceFactorItem item : influencingFactor.getInfluenceFactorItems())
            {
                if (item.hasSubItems())
                {
                    for (InfluenceFactorItem subitems : item.getSubInfluenceFactorItemsList())
                    {
                        factornames.add(subitems.getName());
                    }
                } else
                {
                    factornames.add(item.getName());
                }
            }
            influenceListAdapter = new InfluenceListAdapter(this, influencingFactor.getInfluenceFactorItems());
            influenceFactorItemsList.setAdapter(influenceListAdapter);
            influenceFactorItemsList.setScrollbarFadingEnabled(false);
            updateSumOfInfluences();
        }
    }

    /**
     * Update the Influence Sum TextView with the sum of all Influence Factors
     */
    private void updateSumOfInfluences()
    {
        sumOfInfluences.setText(String.format("%s %d", getString(R.string.function_point_sum_of_influences), influenceListAdapter.getSumOfInfluences()));
    }

    /**
     * Load the names of all Influence Factors for the selected Estimation Method and fill the Spinner
     */
    private void loadInfluenceFactorNames()
    {
        if (estimationMethodsList.isEmpty())
        {
            loadEstimationMethods();
        }
        dbInfluenceFactorItems = databaseHelper.getActiveInfluenceFactorItems(databaseHelper.getEstimationMethodId(selectedEstimationMethod));

        influenceFactorNames = new ArrayList<>();
        influenceFactorIds = new ArrayList<>();
        for (DatabaseInfluenceFactorItem item : dbInfluenceFactorItems)
        {
            influenceFactorNames.add(item.get_name());
            influenceFactorIds.add(item.get_id());
        }

        //Set Factors to spinner

        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, influenceFactorNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        influenceFactorSetSpinner.setAdapter(dataAdapter);
        influenceFactorSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                loadInfluenceFactor(influenceFactorNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }


    private void updateInfluenceFactorNames()
    {
        if (estimationMethodsList.isEmpty())
        {
            loadEstimationMethods();
        }
        dbInfluenceFactorItems = databaseHelper.getActiveInfluenceFactorItems(databaseHelper.getEstimationMethodId(selectedEstimationMethod));
        loadInfluenceFactorNames();

        influenceFactorNames = new ArrayList<>();
        for (DatabaseInfluenceFactorItem item : dbInfluenceFactorItems)
        {
            influenceFactorNames.add(item.get_name());
        }
        //Set Factors to spinner
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.clear();
        dataAdapter.addAll(influenceFactorNames);
        dataAdapter.notifyDataSetChanged();
    }

    /**
     * Load the estimation methods and set the first method to the selected one
     */
    private void loadEstimationMethods()
    {
        estimationMethodsList = databaseHelper.getEstimationMethodNames();
        selectedEstimationMethod = estimationMethodsList.get(0);
        estimationMethodName.setText(selectedEstimationMethod);
    }

    /**
     * Open the Activity for creating a new Influence Factor
     */
    private void onClickCreateNewInfluenceFactor()
    {
        Intent i = new Intent(InfluenceFactorsActivity.this, CreateNewInfluenceFactorActivity.class);
        i.putExtra(getString(R.string.ISNEWFACTOR), true);
        i.putExtra(getString(R.string.NEWFACTORESTIMATIONTECHNIQUE), selectedEstimationMethod);
        loggingHelper.writeToLog("Start Intent CreateNewInfluenceFactorActivity: New Factor for " + selectedEstimationMethod, LoggingHelper.LOGLEVEL_INFO);
        startActivityForResult(i, Integer.parseInt(getString(R.string.new_influence_factor_request_code)));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        updateInfluenceFactorNames();
    }

}
