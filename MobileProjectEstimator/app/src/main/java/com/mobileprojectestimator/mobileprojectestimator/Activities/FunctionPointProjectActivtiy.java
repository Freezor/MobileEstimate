package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    public FunctionPointProjectActivtiy()
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
            setTitle(project.getTitle());
            mSectionsPagerAdapter.update(project);
            mSectionsPagerAdapter.notifyDataSetChanged();
            //Richtige Projektinformationen sind bis hier geladen

        }
        if (requestCode == Integer.parseInt(getString(R.string.CREATE_NEW_PROJECT_REQUEST_CODE)))
        {

            if (databaseHelper == null)
            {
                initDatabase();
            }
            project = databaseHelper.loadProjectById(this, String.valueOf(project.getProjectId()));
            setTitle(project.getTitle());
            mSectionsPagerAdapter.update(project);
            mSectionsPagerAdapter.notifyDataSetChanged();
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
            setTitle(project.getTitle());
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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                onChangeViewPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

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
        switch (item.getItemId())
        {
            case R.id.action_project_properties:
                openProjectProperties();
                return true;
            case R.id.action_person_days_overview:
                startProjectAnalysis();
                return true;
            case R.id.action_terminate:
                if (project.isTerminated())
                {
                    showFinalPersonDaysDialog();
                } else
                {
                    openTerminateProjectDialog();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startProjectAnalysis()
    {
        Intent i = new Intent(FunctionPointProjectActivtiy.this, AnalysisActivity.class);
        i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), project.getProjectId());
        startActivityForResult(i, Integer.parseInt((getString(R.string.CREATE_NEW_PROJECT_REQUEST_CODE))));
    }

    private void showFinalPersonDaysDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_terminated_project_title));
        builder.setMessage(String.format(getString(R.string.dialog_terminated_project_message), project.getFinalPersonDays()));
        builder.show();
    }

    private void openTerminateProjectDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_terminate_project_title));
        builder.setMessage(getString(R.string.dialog_terminate_project_message));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setSingleLine(true);
        input.setText("0");
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                double totalDays = Double.parseDouble(input.getText().toString());
                if (totalDays > 1)
                {
                    project.setFinalPersonDays(totalDays);
                    project.setIsTerminated(true);
                    databaseHelper.terminateProject(project);
                    project = databaseHelper.loadProjectById(FunctionPointProjectActivtiy.this, String.valueOf(project.getProjectId()));
                } else
                {
                    //TODO: Somehow display error message
                    Toast.makeText(FunctionPointProjectActivtiy.this, getString(R.string.toast_terminate_project_error), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openProjectProperties()
    {
        Intent i = new Intent(FunctionPointProjectActivtiy.this, ProjectInformationActivity.class);
        i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), project.getProjectId());
        startActivityForResult(i, Integer.parseInt((getString(R.string.CREATE_NEW_PROJECT_REQUEST_CODE))));
    }

    private void onChangeViewPage(int position)
    {
        if (fragmentsList.get(position) instanceof FunctionPointInfluenceFactorFragment)
        {
            instanciateInfluenceItems(position);

        }
    }

    public void instanciateInfluenceItems(int position)
    {
        try
        {
            //TODO: only works for the first values
            FunctionPointInfluenceFactorFragment functionPointInfluenceFactorFragment = (FunctionPointInfluenceFactorFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
            //noinspection ConstantConditions
            ListView fpInfluenceListView = (ListView) functionPointInfluenceFactorFragment.getView().findViewById(R.id.lvInfluenceFactors);
            //Set Project Name
            RelativeLayout sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(0);
            ImageView infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_integration));
                }
            });

            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(1);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_local_data));
                }
            });

            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(2);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_transaction_rate));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(3);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_arithmetic_operation));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(4);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_control_procedure));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(5);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_exception_regulation));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(6);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_logic));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(7);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_reusability));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(8);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_stock_conversion));
                }
            });
            sublayout = (RelativeLayout) fpInfluenceListView.getChildAt(9);
            infoImageView = (ImageView) sublayout.findViewById(R.id.infoImageView);
            infoImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openFactorInfoDialog(v, getString(R.string.function_point_influence_factor_item_adaptability));
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void openFactorInfoDialog(View v, String item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item);
        builder.setMessage(databaseHelper.getXmlHelper().loadDescriptionText(item.replace(" ", "_").toLowerCase()));
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        builder.show();
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

    }

}
