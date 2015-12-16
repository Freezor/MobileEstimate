package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_influence_factor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCreateNewInfluenceFactorActivity);

        initDatabase();

        view = (View) findViewById(R.id.editInfluenceFactorContent);

        factorName = (EditText) view.findViewById(R.id.etInfluenceFactorName);
        factorItemsListView = (ListView) view.findViewById(R.id.lvInfluenceFactors);
        sumOfFactors = (TextView) view.findViewById(R.id.tvSumOfInfluences);

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
            factorName.setText((CharSequence) oldFactorName);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.action_save_influence_factor:
                saveFactorToDatabase();
                finish();
                break;
            case R.id.action_delete_influence_factor:
                // another startActivity, this is for item with id "menu_item2"
                break;
            case R.id.action_create_new_influence_factor:
                // another startActivity, this is for item with id "menu_item2"
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void saveFactorToDatabase()
    {
        View v;
        EditText etValue;
        TextView name;
        int value= 0;
        for (int i = 0; i < factorItemsListView.getCount(); i++) {
            v = factorItemsListView.getChildAt(i);
            etValue = (EditText) v.findViewById(R.id.tvInfluenceValue);
            name = (TextView) v.findViewById(R.id.tvInfluenceName);
            value = Integer.parseInt(influenceListAdapter.checkInputSize(String.valueOf(etValue.getText()),name.getText().toString()));
            influencingFactor.setChosenValueOfItem(value,name.getText().toString());
        }
        if(oldFactorName.isEmpty() || oldFactorName.equals(""))
        {
            databaseHelper.createNewInfluenceFactor(selectedEstimationMethod,influencingFactor);
        } else {
            databaseHelper.updateExistingInfluenceFactor(selectedEstimationMethod,oldFactorName,influencingFactor);
        }
    }

    /**
     * Loads the influence factors from the database
     *
     */
    private void loadInfluenceFactor()
    {
        if (selectedEstimationMethod.equals(getString(R.string.estimation_method_function_point)))
        {
            influencingFactor = new InfluencingFactor(this, InfluencingFactor.FUNCTIONPOINTFACTORS);

            if (!isNewFactor)
            {
                ArrayList<Integer> factorValues = databaseHelper.loadFunctionPointInfluenceValues(databaseHelper.getFactorItemId(oldFactorName));
                influencingFactor.setFunctionPointValuesFromArrayList(factorValues);
            }

            influenceListAdapter = new NewInfluenceFactorListAdapter(this, influencingFactor.getInfluenceFactorItems());
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
