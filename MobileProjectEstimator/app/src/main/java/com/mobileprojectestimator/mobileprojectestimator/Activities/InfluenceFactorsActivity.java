package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class InfluenceFactorsActivity extends AppCompatActivity {

    private TextView estimationMethodName;
    private Spinner influenceFactorSetSpinner;
    private ImageView editinfluenceFactorSet;
    private TextView sumOfInfluences;
    private ListView influenceFactorItemsLust;

    private ArrayList<String> estimationMethodsList;

    private InfluencingFactor influencingFactor;

    private ArrayList<String> influenceFactorSetNames;

    private String selectedEstimationMethod;

    private int totalSumOfInfluences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_influence_factors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInfluenceFactorActivity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addNewInfluenceFactor);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreateNewInfluenceFactor();
            }
        });

        View influenceFactorContent = (View) findViewById(R.id.influenceFactorContent);
        estimationMethodName = (TextView) influenceFactorContent.findViewById(R.id.tvEstimationMethodName);
        influenceFactorSetSpinner = (Spinner) influenceFactorContent.findViewById(R.id.influenceFactorSetSpinner);
        editinfluenceFactorSet = (ImageView) influenceFactorContent.findViewById(R.id.ivEditInfluenceFactor);
        sumOfInfluences = (TextView) influenceFactorContent.findViewById(R.id.tvSumOfInfluences);
        influenceFactorItemsLust = (ListView) influenceFactorContent.findViewById(R.id.lvInfluenceFactors);

        loadEstimationMethods();

        loadInfluenceFactorNames();

        loadInfluenceFactor(influenceFactorSetNames.get(0).toString());

    }

    private void loadInfluenceFactor(String factorName)
    {
        //TODO: load factor set from database
    }

    private void loadInfluenceFactorNames() {
        if (estimationMethodsList.isEmpty()){
            loadEstimationMethods();
        }

        //TODO: load names from database

        influenceFactorSetNames = new ArrayList<>();
        influenceFactorSetNames.add("Big old team");
        influenceFactorSetNames.add("Young new team");

        //Set Factors to spinner

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, influenceFactorSetNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        influenceFactorSetSpinner.setAdapter(dataAdapter);
    }

    private void loadEstimationMethods() {
        //TODO: Load from database
        estimationMethodsList = new ArrayList<>();
        estimationMethodsList.add("Function Point");
        estimationMethodsList.add("COCOMO");
        estimationMethodsList.add("COCOMO 2");

        selectedEstimationMethod = estimationMethodsList.get(0).toString();
        estimationMethodName.setText(selectedEstimationMethod);
    }

    private void onClickCreateNewInfluenceFactor()
    {
        //TODO: start new influence Factor activity
    }
}
