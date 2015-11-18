package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;


import android.support.v4.app.Fragment;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public abstract class GuidedCreationFragment extends Fragment {
    public abstract void onReloadViews(String text);

    public abstract GuidedCreationFragment newInstance(Project p);
}
