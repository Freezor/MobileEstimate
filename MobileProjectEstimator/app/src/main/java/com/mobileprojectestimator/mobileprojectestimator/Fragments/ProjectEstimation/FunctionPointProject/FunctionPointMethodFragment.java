package com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.FunctionPointEstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.EstimationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.FunctionPointEstimationListAdapter;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015, 14:57.
 * Project: MobileProjectEstimator
 */
public class FunctionPointMethodFragment extends EstimationOverviewFragment
{
    private Project project;
    private ArrayList<FunctionPointEstimationItem> functionPointEstimationItems;
    private FunctionPointEstimationListAdapter projectCreationAdapter;
    private ListView fpEstimationLisView;

    @Override
    public void onReloadViews(String text) {

    }

    @Override
    public EstimationOverviewFragment newInstance(Project p) {
        FunctionPointMethodFragment fragment = new FunctionPointMethodFragment();
        Bundle args = new Bundle();
        project = p;
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_function_point_project_activtiy, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        functionPointEstimationItems = new ArrayList<FunctionPointEstimationItem>();
        functionPointEstimationItems.add(new FunctionPointEstimationItem("Input Data",0));
        functionPointEstimationItems.add(new FunctionPointEstimationItem("Requests ",0));
        functionPointEstimationItems.add(new FunctionPointEstimationItem("Output",0));
        functionPointEstimationItems.add(new FunctionPointEstimationItem("Dataset",0));
        functionPointEstimationItems.add(new FunctionPointEstimationItem("Reference Data", 0));


        fpEstimationLisView = (ListView) rootView.findViewById(R.id.lv_function_point_estimation);
        projectCreationAdapter = new FunctionPointEstimationListAdapter(this, functionPointEstimationItems,getFragmentManager(),this.project );
        fpEstimationLisView.setAdapter(projectCreationAdapter);

        return rootView;
    }
}
