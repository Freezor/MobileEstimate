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
public class ProjectPropTwoFragment extends GuidedCreationFragment {
    private Project project;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectPropTwoFragment newInstance(Project proj) {
        ProjectPropTwoFragment fragment = new ProjectPropTwoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        this.project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        ArrayList<String> programmingLanguageItems = new ArrayList<String>();
        programmingLanguageItems.add("C");
        programmingLanguageItems.add("C#");
        programmingLanguageItems.add("C++");
        programmingLanguageItems.add("Clojure");
        programmingLanguageItems.add("COBOL");
        programmingLanguageItems.add("Java");
        programmingLanguageItems.add("Javascript");
        programmingLanguageItems.add("Matlab");
        programmingLanguageItems.add("Objective-C");
        programmingLanguageItems.add("PHP");
        programmingLanguageItems.add("Prolog");
        programmingLanguageItems.add("Python");
        programmingLanguageItems.add("Scala");
        ArrayAdapter<String> programmingLanguageAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, programmingLanguageItems);
        final Spinner programmingLanguageSpinner = (Spinner) rootView.findViewById(R.id.programmingLanguage);
        programmingLanguageSpinner.setAdapter(programmingLanguageAdapter);
        programmingLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                project.getProjectProperties().setProgrammingLanguage(programmingLanguageSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                project.getProjectProperties().setProgrammingLanguage(programmingLanguageSpinner.getSelectedItem().toString());
            }
        });

        ArrayList<String> platformItems = new ArrayList<String>();
        platformItems.add("Android");
        platformItems.add("IOS");
        platformItems.add("Windows 7");
        platformItems.add("Windows 8");
        platformItems.add("Windows 10");
        platformItems.add("Linux");
        platformItems.add("Windows Phone");
        platformItems.add("Web Development");
        platformItems.add("Mac OS");
        ArrayAdapter<String> platformAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, platformItems);
        final Spinner platformSpinner = (Spinner) rootView.findViewById(R.id.developmentPlatform);
        platformSpinner.setAdapter(platformAdapter);
        platformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                project.getProjectProperties().setPlatform(platformSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                project.getProjectProperties().setPlatform(platformSpinner.getSelectedItem().toString());
            }
        });

        ArrayList<String> industrySectorItems = new ArrayList<String>();
        industrySectorItems.add("Agriculture");
        industrySectorItems.add("Automotive");
        industrySectorItems.add("Banking");
        industrySectorItems.add("Bars & Restaurants");
        industrySectorItems.add("Business Service");
        industrySectorItems.add("Construction");
        industrySectorItems.add("Electronics");
        industrySectorItems.add("Entertainment");
        industrySectorItems.add("Finance");
        industrySectorItems.add("Health");
        industrySectorItems.add("Internet");
        industrySectorItems.add("Music Production");
        industrySectorItems.add("Pharmaceutical Manufacturing");
        industrySectorItems.add("Education");
        ArrayAdapter<String> industrySectorAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, industrySectorItems);
        final Spinner industrySectorSpinner = (Spinner) rootView.findViewById(R.id.industrySector);
        industrySectorSpinner.setAdapter(industrySectorAdapter);
        industrySectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                project.getProjectProperties().setIndustrySector(industrySectorSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                project.getProjectProperties().setIndustrySector(industrySectorSpinner.getSelectedItem().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onReloadViews(String text) {

    }
}
