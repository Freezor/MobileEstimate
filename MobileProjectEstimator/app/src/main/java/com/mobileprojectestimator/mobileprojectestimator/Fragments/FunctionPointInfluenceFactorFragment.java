package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.FunctionPointInfluenceFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.FunctionPointInfluenceListAdapter;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public class FunctionPointInfluenceFactorFragment extends EstimationOverviewFragment {
    private Project project;
    private FunctionPointInfluenceFactor functionPointInfluenceFactor;
    private ListView fpInfluenceListView;
    private FunctionPointInfluenceListAdapter projectInfluenceListAdapter;
    private ArrayList<Object> fragmentsList;

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
        View rootView = inflater.inflate(R.layout.fragment_function_point_influence_factor_activtiy, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        functionPointInfluenceFactor = new FunctionPointInfluenceFactor();
        functionPointInfluenceFactor.setFunctionPointInfluenceFactorItems(project.getInfluencingFactor().getFunctionPointInfluenceFactorItems());


        fpInfluenceListView = (ListView) rootView.findViewById(R.id.lvInfluenceFactors);
        projectInfluenceListAdapter = new FunctionPointInfluenceListAdapter(this, functionPointInfluenceFactor.getFunctionPointInfluenceFactorItems(),project);
        fpInfluenceListView.setAdapter(projectInfluenceListAdapter);
        fpInfluenceListView.setScrollbarFadingEnabled(false);
        return rootView;
    }
}
