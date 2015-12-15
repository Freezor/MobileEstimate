package com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.EstimationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.FunctionPointInfluenceListAdapter;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015, 15:38.
 * Project: MobileProjectEstimator
 */
public class FunctionPointInfluenceFactorFragment extends EstimationOverviewFragment
{
    protected InfluencingFactor influencingFactor;
    protected ListView fpInfluenceListView;
    protected FunctionPointInfluenceListAdapter projectInfluenceListAdapter;
    private Project project;
    private ArrayList<Object> fragmentsList;
    private TextView sumOfInfluences;
    private TextView factorInfluenceRating;

    @Override
    public EstimationOverviewFragment newInstance(Project p)
    {
        FunctionPointInfluenceFactorFragment fragment = new FunctionPointInfluenceFactorFragment();
        Bundle args = new Bundle();
        project = p;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_function_point_influence_factor_activtiy, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        influencingFactor = new InfluencingFactor(this.getContext(), InfluencingFactor.FUNCTIONPOINTFACTORS);

        fpInfluenceListView = (ListView) rootView.findViewById(R.id.lvInfluenceFactors);
        projectInfluenceListAdapter = new FunctionPointInfluenceListAdapter(this, influencingFactor.getInfluenceFactorItems(), project);
        projectInfluenceListAdapter.updateChosenValues(getContext());
        fpInfluenceListView.setAdapter(projectInfluenceListAdapter);
        fpInfluenceListView.setScrollbarFadingEnabled(false);

        sumOfInfluences = (TextView) rootView.findViewById(R.id.tvSumOfInfluences);
        sumOfInfluences.setText(String.format("%s %d", getContext().getString(R.string.function_point_sum_of_influences), this.project.getSumOfInfluences()));

        factorInfluenceRating = (TextView) rootView.findViewById(R.id.tvFactorInfluenceRating);
        factorInfluenceRating.setText(String.format("%s %s", getContext().getString(R.string.function_point_influence_rating), this.project.getFactorInfluenceRating()));

        return rootView;
    }
}
