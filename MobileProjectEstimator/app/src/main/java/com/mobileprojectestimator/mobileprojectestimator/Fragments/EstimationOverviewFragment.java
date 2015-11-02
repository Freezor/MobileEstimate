package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.support.v4.app.Fragment;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public abstract class EstimationOverviewFragment extends Fragment {
    public abstract void onReloadViews(String text);

    public abstract EstimationOverviewFragment newInstance(Project p);
}
