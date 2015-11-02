package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectCreationItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.ProjectCreationListAdapter;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public class FunctionPointInfluenceFactorFragment extends EstimationOverviewFragment {
    private Project project;

    @Override
    public void onReloadViews(String text) {
    }

    @Override
    public EstimationOverviewFragment newInstance(Project p) {
        FunctionPointInfluenceFactorFragment fragment = new FunctionPointInfluenceFactorFragment();
        Bundle args = new Bundle();
        project = p;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_function_point_project_activtiy, container, false);

        return rootView;
    }
}
