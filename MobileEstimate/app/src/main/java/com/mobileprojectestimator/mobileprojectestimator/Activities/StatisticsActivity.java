package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.DevelopmentKindStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.DevelopmentMarketStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.EstimationMethodStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.IndustrySectorStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.PlatformStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.ProcessMethologyStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.ProgrammingLanguageStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.ProjectStatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic.StatisticFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity
{

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
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArrayList<StatisticFragment> fragmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.statisticToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        fragmentsList = new ArrayList<>();
        fragmentsList.add(new EstimationMethodStatisticFragment());
        fragmentsList.add(new ProjectStatisticFragment());
        fragmentsList.add(new IndustrySectorStatisticFragment());
        fragmentsList.add(new PlatformStatisticFragment());
        fragmentsList.add(new ProgrammingLanguageStatisticFragment());
        fragmentsList.add(new ProcessMethologyStatisticFragment());
        fragmentsList.add(new DevelopmentKindStatisticFragment());
        fragmentsList.add(new DevelopmentMarketStatisticFragment());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.statisticContainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            StatisticFragment f = fragmentsList.get(position);
            fragmentsList.set(position, f.reloadStatistic());
            return f;
        }

        @Override
        public int getCount()
        {
            return fragmentsList.size();
        }

    }
}
