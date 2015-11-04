package com.mobileprojectestimator.mobileprojectestimator;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.EstimationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointInfluenceFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedCreationFragment;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionPointProjectActivtiy extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<EstimationOverviewFragment> fragmentsList;

    private Project project;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_point_project_activtiy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.function_point_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        HashMap<String, String> hashMap = new HashMap<String,String>();
        hashMap = (HashMap<String, String>)intent.getSerializableExtra(getString(R.string.NewProjectIntentValueParam));
        project = new Project(this);
        if(hashMap != null){
            project.toObjectFromHashMap(hashMap);
            Log.d("Info", project.getTitle() + " wurde geöffnet.");
        }

        fragmentsList = new ArrayList<>();
        fragmentsList.add(new FunctionPointMethodFragment());//Position 0
        fragmentsList.add(new FunctionPointInfluenceFactorFragment());//Position 1

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this.project);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_function_point_project_activtiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_project_properties) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Project project;

        public SectionsPagerAdapter(FragmentManager fm,Project p) {
            super(fm);
            this.project = p;
        }

        @Override
        public Fragment getItem(int position) {
            EstimationOverviewFragment f = fragmentsList.get(position);
            fragmentsList.set(position, f.newInstance(this.project));
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Estimation";
                case 1:
                    return "Influencing Factors";
            }
            return null;
        }
    }

}
