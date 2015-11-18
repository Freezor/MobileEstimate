package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public class ProjectPropOneFragment extends GuidedCreationFragment {
    private static Project project;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectPropOneFragment newInstance(Project proj) {
        ProjectPropOneFragment fragment = new ProjectPropOneFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.project_prop_one_fragment, container, false);
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
        dot2.setBackgroundResource(R.drawable.circle_blue);

        //TODO: Import Spinner Data from Database
        /**
         * Initialise the Spinner Data
         */
        ArrayList<String> marketItems = new ArrayList<String>();
        marketItems.add("Inhouse");
        marketItems.add("Customer");
        marketItems.add("Anonymous Market");
        ArrayAdapter<String> marketsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, marketItems);
        final Spinner marketSpinner = (Spinner) rootView.findViewById(R.id.market);
        marketSpinner.setAdapter(marketsAdapter);
        marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                project.getProjectProperties().setMarket(marketSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                project.getProjectProperties().setMarket(marketSpinner.getSelectedItem().toString());
            }
        });

        ArrayList<String> developmentItems = new ArrayList<String>();
        developmentItems.add("New Project");
        developmentItems.add("Extension");
        developmentItems.add("Research Project");
        ArrayAdapter<String> developmentAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, developmentItems);
        final Spinner developmentSpinner = (Spinner) rootView.findViewById(R.id.developmentKind);
        developmentSpinner.setAdapter(developmentAdapter);
        developmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                project.getProjectProperties().setDevelopmentKind(developmentSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                project.getProjectProperties().setDevelopmentKind(developmentSpinner.getSelectedItem().toString());
            }
        });

        ArrayList<String> processMethologyItems = new ArrayList<String>();
        processMethologyItems.add("V-Model");
        processMethologyItems.add("Scrum");
        processMethologyItems.add("Waterfall");
        processMethologyItems.add("Spiral");
        processMethologyItems.add("Iterativ");
        processMethologyItems.add("Prototyping");
        ArrayAdapter<String> processModelAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, processMethologyItems);
        final Spinner processMethologySpinner = (Spinner) rootView.findViewById(R.id.processModel);
        processMethologySpinner.setAdapter(processModelAdapter);
        processMethologySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                project.getProjectProperties().setProcessMethology(processMethologySpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                project.getProjectProperties().setProcessMethology(processMethologySpinner.getSelectedItem().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onReloadViews(String text) {

    }
}
