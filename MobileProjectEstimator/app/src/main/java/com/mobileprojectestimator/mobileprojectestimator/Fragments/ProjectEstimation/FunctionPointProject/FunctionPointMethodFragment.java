package com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
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
    private ArrayList<FunctionPointItem> functionPointEstimationItems;
    private FunctionPointEstimationListAdapter projectCreationAdapter;
    @SuppressWarnings("FieldCanBeLocal")
    private ListView fpEstimationListView;
    private TextView totalPoints;
    private TextView evaluatedFunctionPoints;
    private TextView evaluatedPersonDays;

    @Override
    public EstimationOverviewFragment newInstance(Project p)
    {
        Log.d("INFO", "FunctionPointMethodFragment: newInstance");
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
        functionPointEstimationItems = this.project.getFunctionPointItems();

        totalPoints = (TextView) rootView.findViewById(R.id.totalPointsTextView);
        totalPoints.setText(String.format("%s %d", getContext().getString(R.string.function_point_estimation_total_points), getTotalPoints()));

        evaluatedFunctionPoints = (TextView) rootView.findViewById(R.id.evaluatedFunctionPointsTextView);
        evaluatedFunctionPoints.setText(String.format("%s %s", getContext().getString(R.string.function_point_estimation_evaluated_total_points), getEvaluatedPoints()));

        evaluatedPersonDays = (TextView) rootView.findViewById(R.id.evaluatedPersonDaysTextView);
        evaluatedPersonDays.setText(String.format("%s %d", getContext().getString(R.string.function_point_estimation_evaluated_person_days), this.project.getEvaluatedPersonDays()));

        fpEstimationListView = (ListView) rootView.findViewById(R.id.lv_function_point_estimation);
        projectCreationAdapter = new FunctionPointEstimationListAdapter(this, functionPointEstimationItems, getFragmentManager(), this.project);
        fpEstimationListView.setAdapter(projectCreationAdapter);

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
        updateEstimationItems();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Update all estimation Items that exist in the hashMap
     */
    public void updateEstimationItems()
    {
        Log.d("INFO", "FunctionPointMethodFragment: updateEstimationItems");
        //TODO: Bisherige Werte werden gelöscht. Muss noch abgefangen werden
        functionPointEstimationItems = this.project.getFunctionPointItems();
        projectCreationAdapter.updateProject(this.project);
        projectCreationAdapter.notifyDataSetChanged();
        fpEstimationListView.invalidateViews();
        fpEstimationListView.refreshDrawableState();
    }

    @Override
    public void onResume()
    {
        Log.d("INFO", "FunctionPointMethodFragment: onResume");
        updateEstimationItems();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.d("INFO", "FunctionPointMethodFragment: onPause");
        super.onPause();
    }

    /**
     * Calculate the total function points
     *
     * @return
     */
    public int getTotalPoints()
    {
        int totalPoints = 0;
        for (FunctionPointItem item : this.project.getFunctionPointItems())
        {
            totalPoints += item.getTotalAmount();
        }
        return totalPoints;
    }

    /**
     * calculate the evaluated points
     * TotalPoints * InfluenceFactors
     *
     * @return
     */
    public double getEvaluatedPoints()
    {
        double erg = (double) getTotalPoints() * this.project.getFactorInfluenceRating();
        erg = (double) Math.round(erg * 10000d) / 10000d;
        return erg;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }
}
