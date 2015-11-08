package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectItemForCreation;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.ProjectCreationListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public class ProjectCreationOverviewFragment extends GuidedCreationFragment {
    private Project project;
    private ListView projectCreationListView;
    private ProjectCreationListAdapter projectCreationAdapter;
    private ArrayList<ProjectItemForCreation> creationItems;

    private Gson gson;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_save_project:
                if(project.getTitle().equals("") ||project.getTitle().isEmpty() ||project.getTitle() == null ){
                    Toast.makeText(this.getActivity().getBaseContext(), "Please Insert a Project Name", Toast.LENGTH_SHORT).show();
                } else
                {
                    Intent intent = new Intent();
                    HashMap<String,String> projectHashMap = project.toHashMap();
                    intent.putExtra(this.getString(R.string.NewProjectIntentValueParam),projectHashMap);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        creationItems = new ArrayList<ProjectItemForCreation>();
        creationItems.add(new ProjectItemForCreation("Project Name: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Project Description: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Project Icon: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Project Market: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Development Kind: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Process Model: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Programming Language: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Platform: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Industry Sector: ","Item Value"));
        creationItems.add(new ProjectItemForCreation("Estimation Method: ", "Item Value"));
        creationItems.add(new ProjectItemForCreation("Influencing Factor: ", "Item Value"));

        projectCreationListView = (ListView) rootView.findViewById(R.id.lvProjectCreation);
        projectCreationAdapter = new ProjectCreationListAdapter(this, creationItems);
        projectCreationListView.setAdapter(projectCreationAdapter);
        return rootView;
    }

    @Override
    public void onReloadViews(String text) {

    }

}
