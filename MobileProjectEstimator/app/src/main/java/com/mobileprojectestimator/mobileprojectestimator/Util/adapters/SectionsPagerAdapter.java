package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.GuidedCreationFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{

    private Project project;
    private ArrayList<GuidedCreationFragment> guidedCreationFragmentsArrayList;

    public SectionsPagerAdapter(FragmentManager fm, Project proj)
    {
        super(fm);
        this.project = proj;
        this.project.setTitle("");
        this.project.setEstimationMethod("Function Point");//TODO: Standard Estimation Method anders setzen

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c.getTime());
        this.project.setCreationDate(formattedDate);
        guidedCreationFragmentsArrayList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position)
    {
        GuidedCreationFragment f = guidedCreationFragmentsArrayList.get(position);
        guidedCreationFragmentsArrayList.set(position, f.newInstance(this.project));
        return f;
    }

    @Override
    public int getCount()
    {
        // Show 6 total pages.
        return guidedCreationFragmentsArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Project Name and Description";
            case 1:
                return "Project Properties 1";
            case 2:
                return "Project Properties 2";
            case 3:
                return "Estimation Method";
            case 4:
                return "Influencing Factors";
            case 5:
                return "New Project Overview";
        }
        return null;
    }


    public void setFragmentList(ArrayList<GuidedCreationFragment> guidedCreationFragmentsArrayList)
    {
        this.guidedCreationFragmentsArrayList = guidedCreationFragmentsArrayList;
    }
}