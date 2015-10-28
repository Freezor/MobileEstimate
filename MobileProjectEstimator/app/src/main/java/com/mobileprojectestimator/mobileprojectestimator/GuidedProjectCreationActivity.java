package com.mobileprojectestimator.mobileprojectestimator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import java.util.ArrayList;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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

        projectNew = new Project(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guided_project_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return ProjectInfoFragment.newInstance(position);
                case 1:
                    return ProjectPropOneFragment.newInstance(position);
                case 2:
                    return ProjectPropTwoFragment.newInstance(position);
                case 3:
                    return ProjectPropThreeFragment.newInstance(position);
                case 4:
                    return ProjectCreationOverviewFragment.newInstance(position);
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
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
                    return "Project Properties 3";
                case 4:
                    return "Influencing Factors";
            }
            return null;
        }
    }

    public static class ProjectCreationOverviewFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProjectCreationOverviewFragment newInstance(int sectionNumber) {
            ProjectCreationOverviewFragment fragment = new ProjectCreationOverviewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ProjectCreationOverviewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.project_creation_overview_fragment, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public static class ProjectInfoFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProjectInfoFragment newInstance(int sectionNumber) {
            ProjectInfoFragment fragment = new ProjectInfoFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ProjectInfoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.project_info_fragment, container, false);
            ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
            dot1.setBackgroundResource(R.drawable.circle_blue);
            return rootView;
        }
    }

    public static class ProjectPropThreeFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProjectPropThreeFragment newInstance(int sectionNumber) {
            ProjectPropThreeFragment fragment = new ProjectPropThreeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ProjectPropThreeFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.project_prop_three_fragment, container, false);
            ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
            dot1.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
            dot2.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
            dot3.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot4 = (ImageView) rootView.findViewById(R.id.dot4);
            dot4.setBackgroundResource(R.drawable.circle_blue);
            return rootView;
        }
    }

    public static class ProjectPropTwoFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProjectPropTwoFragment newInstance(int sectionNumber) {
            ProjectPropTwoFragment fragment = new ProjectPropTwoFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ProjectPropTwoFragment() {
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
            return rootView;
        }
    }

    public static class ProjectPropOneFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProjectPropOneFragment newInstance(int sectionNumber) {
            ProjectPropOneFragment fragment = new ProjectPropOneFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ProjectPropOneFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.project_prop_one_fragment, container, false);
            ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
            dot1.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
            dot2.setBackgroundResource(R.drawable.circle_blue);

            //TODO: Import Spinner Data from Database
            /**
             * Initialise the Spinner Data
             */
            ArrayList<String> marketItems = new ArrayList<String>();
            marketItems.add("Inhouse");
            marketItems.add("Customer");
            marketItems.add("Anonymous Market");
            ArrayAdapter<String> marketsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, marketItems);
            Spinner marketSpinner = (Spinner)rootView.findViewById(R.id.market);
            marketSpinner.setAdapter(marketsAdapter);

            ArrayList<String> developmentItems = new ArrayList<String>();
            developmentItems.add("New Project");
            developmentItems.add("Extension");
            developmentItems.add("Research Project");
            ArrayAdapter<String> developmentAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, developmentItems);
            Spinner developmentSpinner = (Spinner)rootView.findViewById(R.id.developmentKind);
            developmentSpinner.setAdapter(developmentAdapter);


            ArrayList<String> processModelItems = new ArrayList<String>();
            processModelItems.add("New Project");
            processModelItems.add("Extension");
            developmentItems.add("Research Project");
            ArrayAdapter<String> processModelAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, processModelItems);
            Spinner processModelSpinner = (Spinner)rootView.findViewById(R.id.processModel);
            processModelSpinner.setAdapter(processModelAdapter);

            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_guided_project_creation, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
