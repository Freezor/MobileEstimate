package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.EstimationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject.FunctionPointInfluenceFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class FunctionPointProjectActivtiy extends DatabaseActivity
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<EstimationOverviewFragment> fragmentsList;

    @SuppressWarnings("FieldCanBeLocal")
    private Project project;
    private ViewPager mViewPager;

    public void FunctionPointProjectActivtiy()
    {
        initDatabase();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("INFO", "FunctionPointProjectActivity: onActivityResult");
        if (resultCode == Integer.parseInt(getString(R.string.PROJECT_VIEW_CODE)))
        {
            if (databaseHelper == null)
            {
                initDatabase();
            }
            project = databaseHelper.loadProjectById(this, String.valueOf(project.getProjectId()));
            mSectionsPagerAdapter.setProject(project);
            mSectionsPagerAdapter.notifyDataSetChanged();
            //Richtige Projektinformationen sind bis hier geladen

        }

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.d("INFO", "FunctionPointProjectActivity: onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart()
    {
        Log.d("INFO", "FunctionPointProjectActivity: onRestart");
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("INFO", "FunctionPointProjectActivity: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_point_project_activtiy);

        try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.function_point_toolbar);
            setSupportActionBar(toolbar);
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();
        String projectId = null;
        if (extras != null)
        {
            projectId = String.valueOf(extras.getInt(getString(R.string.SELECTEDPROJECTID)));
        }
        if (databaseHelper == null)
        {
            initDatabase();
        }
        if (projectId == null)
        {
            Log.d("ERROR", "Loading Project from bundle Error");
        } else
        {
            project = databaseHelper.loadProjectById(this, projectId);
            Log.d("Info", project.getTitle() + " wurde geöffnet.");
        }


        fragmentsList = new ArrayList<>();
        fragmentsList.add(new FunctionPointMethodFragment());//Position 0
        fragmentsList.add(new FunctionPointInfluenceFactorFragment());//Position 1

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.project);

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_function_point_project_activtiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_project_properties)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        private Project project;

        public SectionsPagerAdapter(FragmentManager fm, Project p)
        {
            super(fm);
            Log.d("INFO", "SectionsPagerAdapter: constructor");
            this.project = p;
        }

        @Override
        public Fragment getItem(int position)
        {
            Log.d("INFO", "SectionsPagerAdapter: getItem");
            EstimationOverviewFragment f = fragmentsList.get(position);
            fragmentsList.set(position, f.newInstance(this.project));
            return f;
        }

        public void update(Project p)
        {
            this.project = p;
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object)
        {
            if (object instanceof FunctionPointMethodFragment)
            {
                ((FunctionPointMethodFragment) object).update(project);
            }
            //don't return POSITION_NONE, avoid fragment recreation.
            return super.getItemPosition(object);
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "Estimation";
                case 1:
                    return "Influencing Factors";
            }
            return null;
        }

        public void setProject(Project project)
        {
            this.project = project;
        }
    }

}
