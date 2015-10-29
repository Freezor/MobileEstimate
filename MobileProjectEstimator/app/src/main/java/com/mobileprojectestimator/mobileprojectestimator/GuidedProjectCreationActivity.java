package com.mobileprojectestimator.mobileprojectestimator;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        projectNew = new Project(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),projectNew);

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guided_project_creation, menu);
        return true;
    }

    private void cancelCreationDialog()
    {
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
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return ProjectInfoFragment.newInstance(position,this.project);
                case 1:
                    return ProjectPropOneFragment.newInstance(position);
                case 2:
                    return ProjectPropTwoFragment.newInstance(position);
                case 3:
                    return EstimationMethodFragment.newInstance(position);
                case 4:
                    return InfluencingFactorFragment.newInstance(position);
                case 5:
                    return ProjectCreationOverviewFragment.newInstance(position);
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
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

    public static class EstimationMethodFragment extends Fragment
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
        public static EstimationMethodFragment newInstance(int sectionNumber) {
            EstimationMethodFragment fragment = new EstimationMethodFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public EstimationMethodFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.estimation_method_fragment, container, false);
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
            //Set ProgressBar
            ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
            dot1.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
            dot2.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
            dot3.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot4 = (ImageView) rootView.findViewById(R.id.dot4);
            dot4.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot5 = (ImageView) rootView.findViewById(R.id.dot5);
            dot5.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot6 = (ImageView) rootView.findViewById(R.id.dot6);
            dot6.setBackgroundResource(R.drawable.circle_blue);

            //Set Text for all items
            //TODO: get Values from the chosen values
            RelativeLayout ll = (RelativeLayout)rootView.findViewById(R.id.projectNameValue);
            TextView tv = (TextView)ll.findViewById(R.id.tvItemValue);
            tv.setText("Test Name");

            return rootView;
        }
    }

    public static class ProjectInfoFragment extends Fragment
    {
        private static Project project;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProjectInfoFragment newInstance(int sectionNumber, Project proj) {
            ProjectInfoFragment fragment = new ProjectInfoFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            project = proj;


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

    public static class InfluencingFactorFragment extends Fragment
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
        public static InfluencingFactorFragment newInstance(int sectionNumber) {
            InfluencingFactorFragment fragment = new InfluencingFactorFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public InfluencingFactorFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.influencing_factor_fragment, container, false);
            ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
            dot1.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
            dot2.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
            dot3.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot4 = (ImageView) rootView.findViewById(R.id.dot4);
            dot4.setBackgroundResource(R.drawable.circle_blue);
            ImageView dot5 = (ImageView) rootView.findViewById(R.id.dot5);
            dot5.setBackgroundResource(R.drawable.circle_blue);

            //TODO: Import Spinner Data from Database
            /**
             * Initialise the Spinner Data
             */
            ArrayList<String> influencingFactorItems = new ArrayList<String>();
            influencingFactorItems.add("Small new Team");
            influencingFactorItems.add("Big Old Team");
            ArrayAdapter<String> influencingFactorsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, influencingFactorItems);
            Spinner influencingFactorsAdapterSpinner = (Spinner)rootView.findViewById(R.id.influencingSet);
            influencingFactorsAdapterSpinner.setAdapter(influencingFactorsAdapter);
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

            //TODO: Import Spinner Data from Database
            /**
             * Initialise the Spinner Data
             */
            ArrayList<String> programmingLanguageItems = new ArrayList<String>();
            programmingLanguageItems.add("C");
            programmingLanguageItems.add("C#");
            programmingLanguageItems.add("C++");
            programmingLanguageItems.add("Clojure");
            programmingLanguageItems.add("COBOL");
            programmingLanguageItems.add("Java");
            programmingLanguageItems.add("Javascript");
            programmingLanguageItems.add("Matlab");
            programmingLanguageItems.add("Objective-C");
            programmingLanguageItems.add("PHP");
            programmingLanguageItems.add("Prolog");
            programmingLanguageItems.add("Python");
            programmingLanguageItems.add("Scala");
            ArrayAdapter<String> programmingLanguageAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, programmingLanguageItems);
            Spinner programmingLanguageSpinner = (Spinner)rootView.findViewById(R.id.programmingLanguage);
            programmingLanguageSpinner.setAdapter(programmingLanguageAdapter);


            ArrayList<String> platformItems = new ArrayList<String>();
            platformItems.add("Android");
            platformItems.add("IOS");
            platformItems.add("Windows 7");
            platformItems.add("Windows 8");
            platformItems.add("Windows 10");
            platformItems.add("Linux");
            platformItems.add("Windows Phone");
            platformItems.add("Web Development");
            platformItems.add("Mac OS");
            ArrayAdapter<String> platformAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, platformItems);
            Spinner platformSpinner = (Spinner)rootView.findViewById(R.id.developmentPlatform);
            platformSpinner.setAdapter(platformAdapter);

            ArrayList<String> industrySectorItems = new ArrayList<String>();
            industrySectorItems.add("Agriculture");
            industrySectorItems.add("Automotive");
            industrySectorItems.add("Banking");
            industrySectorItems.add("Bars & Restaurants");
            industrySectorItems.add("Business Service");
            industrySectorItems.add("Construction");
            industrySectorItems.add("Electronics");
            industrySectorItems.add("Entertainment");
            industrySectorItems.add("Finance");
            industrySectorItems.add("Health");
            industrySectorItems.add("Internet");
            industrySectorItems.add("Music Production");
            industrySectorItems.add("Pharmaceutical Manufacturing");
            industrySectorItems.add("Education");
            ArrayAdapter<String> industrySectorAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, industrySectorItems);
            Spinner industrySectorSpinner = (Spinner)rootView.findViewById(R.id.industrySector);
            industrySectorSpinner.setAdapter(industrySectorAdapter);

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
