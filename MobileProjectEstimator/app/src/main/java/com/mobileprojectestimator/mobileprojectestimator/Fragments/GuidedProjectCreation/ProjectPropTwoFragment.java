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
 * Created by Oliver Fries on 01.11.2015, 17:46.
 * Project: MobileProjectEstimator
 * <p/>
 * The second fragment screen for the guided creation
 */
public class ProjectPropTwoFragment extends GuidedCreationFragment
{
    private Project project;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectPropTwoFragment newInstance(Project proj)
    {
        ProjectPropTwoFragment fragment = new ProjectPropTwoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        this.project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        initDatabase();
        View rootView = inflater.inflate(R.layout.project_prop_two_fragment, container, false);
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
        dot2.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
        dot3.setBackgroundResource(R.drawable.circle_blue);

        //TODO: Import Spinner Data from Database
        /**
         * Initialise the Spinner Data
         */
        ArrayList<String> programmingLanguageItems = new ArrayList<>();
        programmingLanguageItems.addAll(databaseHelper.loadAllPropertiesByName("ProgrammingLanguages"));
        ArrayAdapter<String> programmingLanguageAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, programmingLanguageItems);
        final Spinner programmingLanguageSpinner = (Spinner) rootView.findViewById(R.id.tvEstimationMethod);
        programmingLanguageSpinner.setAdapter(programmingLanguageAdapter);
        programmingLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                project.getProjectProperties().setProgrammingLanguage(programmingLanguageSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                project.getProjectProperties().setProgrammingLanguage(programmingLanguageSpinner.getSelectedItem().toString());
            }
        });

        ArrayList<String> platformItems = new ArrayList<>();
        platformItems.addAll(databaseHelper.loadAllPropertiesByName("Platforms"));
        ArrayAdapter<String> platformAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, platformItems);
        final Spinner platformSpinner = (Spinner) rootView.findViewById(R.id.developmentPlatform);
        platformSpinner.setAdapter(platformAdapter);
        platformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                project.getProjectProperties().setPlatform(platformSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                project.getProjectProperties().setPlatform(platformSpinner.getSelectedItem().toString());
            }
        });

        ArrayList<String> industrySectorItems = new ArrayList<>();
        industrySectorItems.addAll(databaseHelper.loadAllPropertiesByName("IndustrySectors"));
        ArrayAdapter<String> industrySectorAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, industrySectorItems);
        final Spinner industrySectorSpinner = (Spinner) rootView.findViewById(R.id.industrySector);
        industrySectorSpinner.setAdapter(industrySectorAdapter);
        industrySectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                project.getProjectProperties().setIndustrySector(industrySectorSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                project.getProjectProperties().setIndustrySector(industrySectorSpinner.getSelectedItem().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onReloadViews(String text)
    {

    }
}
