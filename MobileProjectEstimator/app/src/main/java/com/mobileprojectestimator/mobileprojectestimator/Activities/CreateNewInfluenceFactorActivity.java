package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.NewInfluenceFactorListAdapter;

import java.util.ArrayList;

public class CreateNewInfluenceFactorActivity extends DatabaseActivity
{
    private boolean isNewFactor = true;
    private View view;
    private EditText factorName;
    private ListView factorItemsListView;
    private TextView sumOfFactors;
    private String oldFactorName;
    private String selectedEstimationMethod;
    private InfluencingFactor influencingFactor;
    private NewInfluenceFactorListAdapter influenceListAdapter;
    private int oldFactorId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_influence_factor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCreateNewInfluenceFactorActivity);

        initDatabase();

        view = findViewById(R.id.editInfluenceFactorContent);

        factorName = (EditText) view.findViewById(R.id.etInfluenceFactorName);
        factorItemsListView = (ListView) view.findViewById(R.id.lvInfluenceFactors);
        sumOfFactors = (TextView) view.findViewById(R.id.tvSumOfInfluences);

        sumOfFactors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateSumOfInfluences();
            }
        });

        Bundle bundle = getIntent().getExtras();
        isNewFactor = bundle.getBoolean(getString(R.string.ISNEWFACTOR));
        selectedEstimationMethod = bundle.getString(getString(R.string.NEWFACTORESTIMATIONMETHOD));
        if (isNewFactor)
        {
            toolbar.setTitle("New Factor");
            oldFactorName = "";
        } else
        {
            toolbar.setTitle("Edit Factor");
            oldFactorName = bundle.getString(getString(R.string.NEWFACTORINFLUENCESETNAME));
            oldFactorId = bundle.getInt(getString(R.string.NEWFACTORINFLUENCESETID));
            factorName.setText(oldFactorName);
        }
        toolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadInfluenceFactor();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_influence_factor, menu);

        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem deleteInfluenceFactor = menu.findItem(R.id.action_delete_influence_factor);
        MenuItem createNewInfluenceFactor = menu.findItem(R.id.action_create_new_influence_factor);
        deleteInfluenceFactor.setVisible(!oldFactorName.isEmpty());
        createNewInfluenceFactor.setVisible(!oldFactorName.isEmpty());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_save_influence_factor:
                saveFactorToDatabase();
                break;
            case R.id.action_delete_influence_factor:
                deleteInfluenceFactor();
                break;
            case R.id.action_create_new_influence_factor:
                createNewInfluenceFactor();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void createNewInfluenceFactor()
    {
        Intent i = new Intent(CreateNewInfluenceFactorActivity.this, CreateNewInfluenceFactorActivity.class);
        i.putExtra(getString(R.string.ISNEWFACTOR), true);
        i.putExtra(getString(R.string.NEWFACTORESTIMATIONMETHOD), selectedEstimationMethod);
        startActivityForResult(i, Integer.parseInt(getString(R.string.new_influence_factor_request_code)));
        finish();
    }

    private void deleteInfluenceFactor()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(oldFactorName);
        builder.setMessage(R.string.dialog_delete_influence_factor)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        if (databaseHelper.setDeleteFlagForInfluenceFactor(influencingFactor.getDbId()))
                        {
                            Toast.makeText(CreateNewInfluenceFactorActivity.this, "Influence Factor successfully deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Integer.parseInt((getString(R.string.influence_factor_subitem_request_code))))
        {
            Bundle bundle = data.getExtras();
            String itemName = bundle.getString("SUBITEMCATEGORYNAME");
            ArrayList<String> itemNames = bundle.getStringArrayList("SUBITEMNAMES");
            ArrayList<Integer> itemValues = bundle.getIntegerArrayList("SUBITEMVALUES");
            int i = 0;
            for (InfluenceFactorItem item : influencingFactor.getInfluenceFactorItems())
            {
                if (item.getName().equals(itemName))
                {
                    for (String name : itemNames)
                    {
                        for (InfluenceFactorItem subitem : item.getSubInfluenceFactorItemsList())
                        {
                            if (subitem.getName().equals(name))
                            {
                                subitem.setChosenValue(itemValues.get(i++));
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            influenceListAdapter.setInfluenceFactorItems(influencingFactor.getInfluenceFactorItems());
        }
        updateSumOfInfluences();
    }

    private void saveFactorToDatabase()
    {
        influencingFactor.setInfluenceFactorItems(influenceListAdapter.getInfluenceFactorItems());

        if (factorName.getText().toString().equals(""))
        {
            Toast.makeText(this, "No Factor Name set.", Toast.LENGTH_SHORT).show();
        } else
        {
            influencingFactor.setName(factorName.getText().toString());
            influencingFactor.setInfluenceFactorItems(influenceListAdapter.getInfluenceFactorItems());
            if (oldFactorName.isEmpty() || oldFactorName.equals(""))
            {
                databaseHelper.createNewInfluenceFactor(selectedEstimationMethod, influencingFactor);
                Intent returnIntent = new Intent();
                setResult(Integer.parseInt(getString(R.string.new_influence_factor_request_code)), returnIntent);
            } else
            {
                databaseHelper.updateExistingInfluenceFactor(selectedEstimationMethod, oldFactorId, influencingFactor);
                Intent returnIntent = new Intent();
                setResult(Integer.parseInt(getString(R.string.edit_influence_factor_request_code)), returnIntent);
            }
            finish();
        }
    }

    /**
     * Loads the influence factors from the database
     */
    private void loadInfluenceFactor()
    {
        if (selectedEstimationMethod.equals(getString(R.string.estimation_method_function_point)))
        {
            influencingFactor = new InfluencingFactor(this, InfluencingFactor.FUNCTIONPOINTFACTORS);

            if (!isNewFactor)
            {
                ArrayList<Integer> factorValues = databaseHelper.loadFunctionPointInfluenceValues(databaseHelper.getInfluenceFactorDetailsId(oldFactorId));
                influencingFactor.setFunctionPointValuesFromArrayList(factorValues);
                influencingFactor.setName(oldFactorName);
                influencingFactor.setDbId(oldFactorId);
            }
            influenceListAdapter = new NewInfluenceFactorListAdapter(this, R.layout.function_point_influence_factorset_list_item, influencingFactor.getInfluenceFactorItems());
            factorItemsListView.setAdapter(influenceListAdapter);
            factorItemsListView.setScrollbarFadingEnabled(false);
            updateSumOfInfluences();
        }
    }

    /**
     * Update the Influence Sum TextView with the sum of all Influence Factors
     */
    private void updateSumOfInfluences()
    {
        sumOfFactors.setText(String.format("%s %d", getString(R.string.function_point_sum_of_influences), influenceListAdapter.getSumOfInfluences()));
    }
}
