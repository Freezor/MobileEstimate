package com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.FunctionPointEstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.EstimationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.FunctionPointEstimationListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 02.11.2015, 14:57.
 * Project: MobileProjectEstimator
 */
public class FunctionPointMethodFragment extends EstimationOverviewFragment
{
    private Project project;
    private ArrayList<FunctionPointEstimationItem> functionPointEstimationItems;
    private FunctionPointEstimationListAdapter projectCreationAdapter;
    @SuppressWarnings("FieldCanBeLocal")
    private ListView fpEstimationLisView;

    @Override
    public EstimationOverviewFragment newInstance(Project p)
    {
        FunctionPointMethodFragment fragment = new FunctionPointMethodFragment();
        Bundle args = new Bundle();
        project = p;
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("INFO", "FunctionPointMethodFragment: onCreateViews");
        View rootView = inflater.inflate(R.layout.fragment_function_point_project_activtiy, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        functionPointEstimationItems = new ArrayList<>();
        String inputData = getContext().getString(R.string.function_point_estimation_input_data);
        functionPointEstimationItems.add(new FunctionPointEstimationItem(inputData, this.project.getEstimationItemSum(inputData)));
        String request = getContext().getString(R.string.function_point_estimation_requests);
        functionPointEstimationItems.add(new FunctionPointEstimationItem(request, this.project.getEstimationItemSum(request)));
        String output = getContext().getString(R.string.function_point_estimation_output);
        functionPointEstimationItems.add(new FunctionPointEstimationItem(output, this.project.getEstimationItemSum(output)));
        String dataset = getContext().getString(R.string.function_point_estimation_dataset);
        functionPointEstimationItems.add(new FunctionPointEstimationItem(dataset, this.project.getEstimationItemSum(dataset)));
        String referenceData = getContext().getString(R.string.function_point_estimation_reference_data);
        functionPointEstimationItems.add(new FunctionPointEstimationItem(referenceData, this.project.getEstimationItemSum(referenceData)));

        fpEstimationLisView = (ListView) rootView.findViewById(R.id.lv_function_point_estimation);
        projectCreationAdapter = new FunctionPointEstimationListAdapter(this, functionPointEstimationItems, getFragmentManager(), this.project);
        fpEstimationLisView.setAdapter(projectCreationAdapter);

        return rootView;
    }

    @Override
    public void onStart()
    {
        Log.d("INFO", "FunctionPointMethodFragment: onStart");
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("INFO", "FunctionPointMethodFragment: onActivtiyResult");
        try
        {
            HashMap<String, String> hashMap = (HashMap<String, String>) data.getSerializableExtra(this.getString(R.string.NewProjectIntentValueParam));
            this.project.toObjectFromHashMap(hashMap);
            updateEstimationItems();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateEstimationItems()
    {
        for (FunctionPointEstimationItem item : functionPointEstimationItems)
        {
            this.project.updateFunctionPointItem(item);
        }
        projectCreationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume()
    {
        Log.d("INFO", "FunctionPointMethodFragment: onResume");
        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.d("INFO", "FunctionPointMethodFragment: onPause");
        super.onPause();
    }
}
