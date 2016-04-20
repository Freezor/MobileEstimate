package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 01.11.2015, 15:36.
 * Project: MobileProjectEstimator
 */
public class EstimationMethodFragment extends GuidedCreationFragment
{
    private static Project project;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public EstimationMethodFragment newInstance(Project proj)
    {
        EstimationMethodFragment fragment = new EstimationMethodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        initDatabase();
        final View rootView = inflater.inflate(R.layout.estimation_method_fragment, container, false);
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
        dot2.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
        dot3.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot4 = (ImageView) rootView.findViewById(R.id.dot4);
        dot4.setBackgroundResource(R.drawable.circle_blue);

        ArrayList<String> architectureItems = new ArrayList<>();
        architectureItems.addAll(databaseHelper.loadAllPropertiesByName("SoftwareArchitecturePatterns"));
        ArrayAdapter<String> architectureAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, architectureItems);
        final Spinner architectureSpinner = (Spinner) rootView.findViewById(R.id.architecture);
        architectureSpinner.setAdapter(architectureAdapter);
        architectureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                project.getProjectProperties().setArchitecture(architectureSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                project.getProjectProperties().setArchitecture(architectureSpinner.getSelectedItem().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onReloadViews(String text)
    {

    }
}
