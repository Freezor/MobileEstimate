package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;

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

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.ProjectItemForCreation;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.ProjectCreationListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 01.11.2015, 16:01.
 * Project: MobileProjectEstimator
 */
public class ProjectCreationOverviewFragment extends GuidedCreationFragment
{
    protected ListView projectCreationListView;
    protected ProjectCreationListAdapter projectCreationAdapter;
    protected ArrayList<ProjectItemForCreation> creationItems;
    private Project project;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectCreationOverviewFragment newInstance(Project project)
    {
        ProjectCreationOverviewFragment fragment = new ProjectCreationOverviewFragment();
        Bundle args = new Bundle();
        this.project = project;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_save_project:
                if (project.getTitle().equals("") || project.getTitle().isEmpty())
                {
                    Toast.makeText(this.getActivity().getBaseContext(), "Please Insert a Project Name", Toast.LENGTH_SHORT).show();
                } else
                {
                    this.project.initialiseEstimationItems(this.project.getEstimationMethod());

                    Intent intent = new Intent();
                    //Send Title to Project Overview for logging
                    intent.putExtra(this.getString(R.string.NewProjectIntentValueParam), project.getTitle());
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveItem = menu.getItem(0);
        saveItem.setVisible(true);
        MenuItem listCreationItem = menu.getItem(1);
        listCreationItem.setVisible(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.project_creation_overview_fragment, container, false);
        if (this.project == null)
        {

            creationItems = new ArrayList<>();
            creationItems.add(new ProjectItemForCreation("Project Name: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Project Description: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Project Icon: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Project Market: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Development Kind: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Process Methology: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Programming Language: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Platform: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Industry Sector: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Estimation Method: ", "ERROR"));
            creationItems.add(new ProjectItemForCreation("Influencing Factor: ", "ERROR"));
        } else
        {
            creationItems = new ArrayList<>();
            creationItems.add(new ProjectItemForCreation("Project Name: ", this.project.getTitle()));
            creationItems.add(new ProjectItemForCreation("Project Description: ", this.project.getProjectDescription()));
            creationItems.add(new ProjectItemForCreation("Project Icon: ", this.project.getIconName()));
            creationItems.add(new ProjectItemForCreation("Project Market: ", this.project.getProjectProperties().getMarket()));
            creationItems.add(new ProjectItemForCreation("Development Kind: ", this.project.getProjectProperties().getDevelopmentKind()));
            creationItems.add(new ProjectItemForCreation("Process Methology: ", this.project.getProjectProperties().getProcessMethology()));
            creationItems.add(new ProjectItemForCreation("Programming Language: ", this.project.getProjectProperties().getProgrammingLanguage()));
            creationItems.add(new ProjectItemForCreation("Platform: ", this.project.getProjectProperties().getPlatform()));
            creationItems.add(new ProjectItemForCreation("Industry Sector: ", this.project.getProjectProperties().getIndustrySector()));
            creationItems.add(new ProjectItemForCreation("Estimation Method: ", this.project.getEstimationMethod()));
            creationItems.add(new ProjectItemForCreation("Influencing Factor: ", this.project.getInfluencingFactor().getInfluenceFactorSetName()));
        }

        projectCreationListView = (ListView) rootView.findViewById(R.id.lvProjectCreation);
        projectCreationAdapter = new ProjectCreationListAdapter(this, creationItems);
        projectCreationListView.setAdapter(projectCreationAdapter);
        return rootView;
    }

    @Override
    public void onReloadViews(String text)
    {

    }

}
