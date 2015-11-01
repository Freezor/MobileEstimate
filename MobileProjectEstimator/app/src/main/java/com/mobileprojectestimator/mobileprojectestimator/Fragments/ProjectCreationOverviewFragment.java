package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectCreationItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.ProjectCreationListAdapter;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public class ProjectCreationOverviewFragment extends GuidedCreationFragment {
    private Project project;
    private ListView projectCreationListView;
    private ProjectCreationListAdapter projectCreationAdapter;
    private ArrayList<ProjectCreationItem> creationItems;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectCreationOverviewFragment newInstance(Project proj) {
        ProjectCreationOverviewFragment fragment = new ProjectCreationOverviewFragment();
        Bundle args = new Bundle();
        project = proj;
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveItem = menu.getItem(0);
        saveItem.setVisible(true);
        MenuItem listCreationItem = menu.getItem(1);
        listCreationItem.setVisible(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.project_creation_overview_fragment, container, false);

        //Set Text for all items
        //TODO: get Values from the chosen values

        creationItems = new ArrayList<ProjectCreationItem>();
        creationItems.add(new ProjectCreationItem("Project Name: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Project Description: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Project Icon: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Project Market: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Project Kind: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Project Model: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Process Model: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Programming Language: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Industry Sector: ","Item Value"));
        creationItems.add(new ProjectCreationItem("Estimation Method: ", "Item Value"));
        creationItems.add(new ProjectCreationItem("Influencing Factor: ", "Item Value"));

        projectCreationListView = (ListView) rootView.findViewById(R.id.lvProjectCreation);
        projectCreationAdapter = new ProjectCreationListAdapter(this, creationItems);
        projectCreationListView.setAdapter(projectCreationAdapter);
        return rootView;
    }

    @Override
    public void onReloadViews(String text) {

    }
}
