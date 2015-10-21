package com.example.oliverfries.cet_test;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Projects_Overview extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> projectsList;

    public Projects_Overview() {
        projectsList = new ArrayList<String>();
    }

    private int clickCounter = 1;

    private ListView projectsListView;

    private ArrayAdapter<String> projectsAdapter;

    private Toolbar toolbar;

    private SharedPreferences sharedPreferences;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Projects_Overview.context = getApplicationContext();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setContentView(R.layout.activity_projects__overview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                projectsList);
        setListAdapter(projectsAdapter);

        getListView();
        projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Projects_Overview.this,FunctionPointProjectActivity.class);
                startActivity(i);
            }
        });
        //TestCase
        for (int i = 0; i<10;i++)
        {
            addProject();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Projects_Overview.this,CreateNewProject.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void addProject() {
        projectsList.add("Project "+clickCounter++);
        projectsAdapter.notifyDataSetChanged();
    }

    public static Context getAppContext() {
        return Projects_Overview.context;
    }

    protected ListView getListView() {
        if (projectsListView == null) {
            projectsListView = (ListView) findViewById(R.id.myProjectsList);
        }
        return projectsListView;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.projects__overview, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        retrieveData();
    }

    private void retrieveData() {
        /*Gson gson = new Gson();
        String json = sharedPreferences.getString("NewCreatedProject", "");
        EstimationProject obj = gson.fromJson(json, EstimationProject.class);
        projectsList.add(obj.get_projectName());
        projectsAdapter.notifyDataSetChanged();*/
        SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
        long highScore = sharedPref.getInt("A", 0);
        Log.e("AAA", String.valueOf(highScore));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_influencing_factors) {
            Intent i = new Intent(Projects_Overview.this,InfluencingFactorsActivity.class);
            startActivity(i);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
