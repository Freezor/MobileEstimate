package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Activtiy for the Overview of all projects
 */
public class ProjectOverviewActivity extends DatabaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private final List<Project> projectsList = new ArrayList<>();
    private ListView projectsListView;
    private ProjectListAdapter projectsAdapter;
    private TextView navigationDrawerUserNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initDatabase();
        databaseHelper.logAllTableNames();

        setContentView(R.layout.activity_project_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addProject);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onClickCreateNewProject();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        navigationDrawerUserNameTextView = (TextView) header.findViewById(R.id.userName);

        //Load previous data from preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPref.getString(getString(R.string.pref_key_user_name), "");
        if (userName.isEmpty() || userName.equals(""))
        {
            navigationDrawerUserNameTextView.setText(R.string.user_name_error);
        } else
        {
            navigationDrawerUserNameTextView.setText(userName);
        }

        //Create and Load the projects
        loadProjects();
        projectsListView = (ListView) findViewById(R.id.projectsList);
        projectsAdapter = new ProjectListAdapter(this, projectsList);
        projectsListView.setAdapter(projectsAdapter);
        projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                onClickProject(position);
            }
        });
        databaseHelper.preloadResourcesIdMap();
    }

    /**
     * Open the Project Activity when a project is clicked
     *
     * @param position
     */
    private void onClickProject(int position)
    {
        Intent intent = new Intent(getApplicationContext(), FunctionPointProjectActivtiy.class);
        //Changed from toHashMap with introduction of database. Only Id is needed now
        /*HashMap<String, String> projectHashMap = projectsList.get(position).toHashMap();
        intent.putExtra(getString(R.string.NewProjectIntentValueParam), projectHashMap);*/
        intent.putExtra(getString(R.string.SELECTEDPROJECTID), projectsList.get(position).getProjectId());
        startActivityForResult(intent, Integer.parseInt((getString(R.string.PROJECT_VIEW_CODE))));
        //startActivity(intent);
    }

    /**
     * Open the Activity that guides the user through the creation of a new project
     */
    private void onClickCreateNewProject()
    {
        Intent i = new Intent(ProjectOverviewActivity.this, GuidedProjectCreationActivity.class);
        startActivityForResult(i, Integer.parseInt((getString(R.string.FRAGMENT_REQUEST_CODE))));
    }

    /**
     * Load all saved Projects from Database
     */
    private void loadProjects()
    {
        //if somehow projects table is empty load the demo project
        if (databaseHelper.hasProjects())
        {
            projectsList.clear();
            projectsList.addAll(databaseHelper.getAllProjects(this));
        } else
        {
            generateTestProject();
        }
    }

    /**
     * Only a Method for Test Purpose
     * Generates a Test Project
     */
    private void generateTestProject()
    {
        Project p = new Project(this, "Demo Project", "20.04.2009", getResources().getString(R.string.estimation_method_function_point));
        p.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.project));
        InfluencingFactor factor = new InfluencingFactor(this, InfluencingFactor.FUNCTIONPOINTFACTORS);
        factor.setName("Team Mates");
        factor.getInfluenceFactorItems().get(0).setChosenValue(2);
        factor.getInfluenceFactorItems().get(1).setChosenValue(2);
        factor.getInfluenceFactorItems().get(2).setChosenValue(2);

        factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(0).setChosenValue(8);
        factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(1).setChosenValue(2);
        factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(2).setChosenValue(5);
        factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(3).setChosenValue(1);

        factor.getInfluenceFactorItems().get(3).setChosenValue(0);
        factor.getInfluenceFactorItems().get(5).setChosenValue(3);
        factor.getInfluenceFactorItems().get(6).setChosenValue(5);

        p.setInfluencingFactor(factor);
        projectsList.add(p);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_overview, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Integer.parseInt((getString(R.string.FRAGMENT_REQUEST_CODE))))
        {
            if (resultCode == RESULT_OK)
            {
                //TODO: Check if result is null
                getDataFromProjectCreationProcess(data);
            }
        } else if (requestCode == Integer.parseInt((getString(R.string.RESULT_SETTINGS_REQUEST_CODE))))
        {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String userName = sharedPref.getString(getString(R.string.pref_key_user_name), "");
            if (userName.isEmpty() || userName.equals(""))
            {
                navigationDrawerUserNameTextView.setText(R.string.user_name_error);
            } else
            {
                navigationDrawerUserNameTextView.setText(userName);
            }
        }
    }

    /**
     * This method processes the created project
     *
     * @param data
     */
    private void getDataFromProjectCreationProcess(Intent data)
    {
        //TODO: Check if HasMap is null
        String title =  data.getStringExtra(this.getString(R.string.NewProjectIntentValueParam));
        loadProjects();
        projectsAdapter = new ProjectListAdapter(this, projectsList);
        projectsListView.setAdapter(projectsAdapter);
        projectsAdapter.notifyDataSetChanged();
        Log.d("Info", title + " wurde erstellt.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        //TODO: Change to new drawer menu functions

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        String title = item.getTitle().toString();

        Intent i;
        if (title.equals((getString(R.string.project_analysis))))
        {

        } else if (title.equals((getString(R.string.my_projects))))
        {

        } else if (title.equals((getString(R.string.influencing_factors))))
        {
            i = new Intent(this, InfluenceFactorsActivity.class);
            startActivityForResult(i, Integer.parseInt(getString(R.string.INFLUENCEFACTORACTIVITYREQUESTCODE)));
        } else if (title.equals((getString(R.string.export))))
        {
            i = new Intent(this, ExportProjectActivity.class);
            i.putExtra(getString(R.string.ProjectsNameList), projectsAdapter.getProjectNamesList());
            startActivity(i);
        } else if (title.equals((getString(R.string.help))))
        {
            i = new Intent(this, HelpActivity.class);
            startActivity(i);
        } else if (title.equals((getString(R.string.settings))))
        {
            i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, Integer.parseInt((getString(R.string.RESULT_SETTINGS_REQUEST_CODE))));
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
