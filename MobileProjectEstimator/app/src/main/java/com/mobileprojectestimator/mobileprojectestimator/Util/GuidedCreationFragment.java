package com.mobileprojectestimator.mobileprojectestimator.Util;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public abstract class GuidedCreationFragment extends Fragment {
    public abstract void onReloadViews(LayoutInflater inflater);
}
