package com.mobileprojectestimator.mobileprojectestimator;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Util.ProjectListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectOverview extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Project> projectsList = new ArrayList<Project>();
    private ListView projectsListView;
    private ProjectListAdapter projectsAdapter;
    int FRAGMENT_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addProject);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProjectOverview.this, GuidedProjectCreationActivity.class);
                startActivityForResult(i, FRAGMENT_REQUEST_CODE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Create and Load the projects
        loadProjects();
        projectsListView = (ListView) findViewById(R.id.projectsList);
        projectsAdapter = new ProjectListAdapter(this, projectsList);
        projectsListView.setAdapter(projectsAdapter);
    }

    /**
     * Load all saved Projects from Database
     */
    private void loadProjects() {
        //TODO: This is only text method. Add Database access
        Project p = new Project(this, "Test", "20.04.1989", getResources().getString(R.string.functionPoint));
        p.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.project));
        projectsList.add(p);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_overview, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FRAGMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                HashMap<String, String> hashMap = (HashMap<String, String>)data.getSerializableExtra(this.getString(R.string.NewProjectIntentValueParam));
                Project p = new Project(this);
                p.toObjectFromHashMap(hashMap);
                projectsList.add(p);
                projectsAdapter = new ProjectListAdapter(this, projectsList);
                projectsListView.setAdapter(projectsAdapter);
                projectsAdapter.notifyDataSetChanged();
                Log.d("Info", p.getTitle() + " wurde erstellt.");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_Search) {
            return true;
        } else if (id == R.id.action_Sort) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //TODO: Change to new drawer menu functions
        /*
        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
