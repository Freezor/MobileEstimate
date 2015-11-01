package com.mobileprojectestimator.mobileprojectestimator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.EstimationMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedCreationFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.InfluencingFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectCreationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectInfoFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectPropOneFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectPropTwoFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GuidedProjectCreationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ArrayList<GuidedCreationFragment> guidedCreationFragmentsArrayList;

    /**
     * The New Project that will be created
     */
    private Project projectNew;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_project_creation);

        guidedCreationFragmentsArrayList = new ArrayList<GuidedCreationFragment>();
        guidedCreationFragmentsArrayList.add(new ProjectInfoFragment());//Position 0
        guidedCreationFragmentsArrayList.add(new ProjectPropOneFragment());//Position 1
        guidedCreationFragmentsArrayList.add(new ProjectPropTwoFragment());//Position 2
        guidedCreationFragmentsArrayList.add(new EstimationMethodFragment());//Position 3
        guidedCreationFragmentsArrayList.add(new InfluencingFactorFragment());//Position 4
        guidedCreationFragmentsArrayList.add(new ProjectCreationOverviewFragment());//Position 5

        projectNew = new Project(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGuidedCreation);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), projectNew);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Log.d("Info",guidedCreationFragmentsArrayList.get(position).toString()+" Position: "+position);
               if(guidedCreationFragmentsArrayList.get(position) instanceof InfluencingFactorFragment) {
                   InfluencingFactorFragment f = (InfluencingFactorFragment) mSectionsPagerAdapter.instantiateItem(mViewPager,position);
                   TextView t = (TextView) f.getView().findViewById(R.id.textViewChosenEstimationMethod);
                   //t.setText(f.project.getEstimationMethod());
                   //TODO: hier weiter arbeiten, ordentlichen Text einpflegen und für alle Fragments die Daten eintragen
                   //TODO: nächster Schritt Projekt speichern und in Übersicht anzeigen. Start der Projekt Ansicht
                   //TODO: Eintragung von Einflussfaktoren erst nach Auswahl der MEthode ermöglichen
                   t.setText(projectNew.getEstimationMethod());
               }
                //TODO: gug
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guided_project_creation, menu);
        return true;
    }

    private void cancelCreationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GuidedProjectCreationActivity.this);
        builder.setMessage(R.string.cancel_project_creation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NavUtils.navigateUpFromSameTask(GuidedProjectCreationActivity.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    @Override
    public void onBackPressed() {
        //DO not Use. Will be done with cancelCreationDialog
        //super.onBackPressed();
        cancelCreationDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelCreationDialog();
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

        public SectionsPagerAdapter(FragmentManager fm, Project proj) {
            super(fm);
            this.project = proj;
            this.project.setTitle("");
            this.project.setEstimationMethod("Function Point");//TODO: Standard Estimation Method anders setzen

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = df.format(c.getTime());
            this.project.setCreationDate(formattedDate);
        }

        @Override
        public Fragment getItem(int position) {
            GuidedCreationFragment f = guidedCreationFragmentsArrayList.get(position);
            guidedCreationFragmentsArrayList.set(position, f.newInstance(this.project));
            return f;
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return guidedCreationFragmentsArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Project Name and Description";
                case 1:
                    return "Project Properties 1";
                case 2:
                    return "Project Properties 2";
                case 3:
                    return "Estimation Method";
                case 4:
                    return "Influencing Factors";
                case 5:
                    return "New Project Overview";
            }
            return null;
        }


    }
}
