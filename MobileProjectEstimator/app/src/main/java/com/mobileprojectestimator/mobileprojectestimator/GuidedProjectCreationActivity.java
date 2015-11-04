package com.mobileprojectestimator.mobileprojectestimator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.FunctionPointFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.InfluencingFactor;
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

    private static int STRINGLENGTH = 12;

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

        guidedCreationFragmentsArrayList = new ArrayList<>();
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
                   updateInfluencingFactorFragment(position);

               } else if(guidedCreationFragmentsArrayList.get(position) instanceof ProjectInfoFragment) {
                   updateProjectInfoFragment(position);

               } else if(guidedCreationFragmentsArrayList.get(position) instanceof ProjectCreationOverviewFragment) {
                   updateProjectCreationOverviewFragment(position);

               }
                //TODO: gug
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void updateProjectCreationOverviewFragment(int position) {
        final ProjectCreationOverviewFragment f = (ProjectCreationOverviewFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
        ListView creationItemsListView = (ListView) f.getView().findViewById(R.id.lvProjectCreation);
        //Set Project Name
        final RelativeLayout rv0 = (RelativeLayout) creationItemsListView.getChildAt(0);
        TextView projName = (TextView) rv0.findViewById(R.id.tvItemValue);
        projName.setText(shorten(projectNew.getTitle(),STRINGLENGTH));
        ImageView editName = (ImageView) rv0.findViewById(R.id.ivEditItem);
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Description
        final RelativeLayout rv1 = (RelativeLayout) creationItemsListView.getChildAt(1);
        TextView projDescription = (TextView) rv1.findViewById(R.id.tvItemValue);
        if(projectNew.getProjectDescription() != null)
        {
            projDescription.setText(shorten(projectNew.getProjectDescription(), STRINGLENGTH));
        } else {
            projDescription.setText("");
        }
        ImageView editDescription = (ImageView) rv1.findViewById(R.id.ivEditItem);
        editDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Icon
        final RelativeLayout rv2 = (RelativeLayout) creationItemsListView.getChildAt(2);
        TextView iconName = (TextView) rv2.findViewById(R.id.tvItemValue);
        iconName.setText(shorten(projectNew.getIconName(), STRINGLENGTH));
        ImageView editIconName = (ImageView) rv2.findViewById(R.id.ivEditItem);
        editIconName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Market
        final RelativeLayout rv3 = (RelativeLayout) creationItemsListView.getChildAt(3);
        TextView projectMarket = (TextView) rv3.findViewById(R.id.tvItemValue);
        projectMarket.setText(shorten(projectNew.getProjectProperties().getMarket(), STRINGLENGTH));
        ImageView editProjectMarket = (ImageView) rv3.findViewById(R.id.ivEditItem);
        editProjectMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Development Kind
        final RelativeLayout rv4 = (RelativeLayout) creationItemsListView.getChildAt(4);
        TextView developmentKind = (TextView) rv4.findViewById(R.id.tvItemValue);
        developmentKind.setText(shorten(projectNew.getProjectProperties().getDevelopmentKind(), STRINGLENGTH));
        ImageView editDevelopmentKind = (ImageView) rv4.findViewById(R.id.ivEditItem);
        editDevelopmentKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Process Methology
        final RelativeLayout rv5 = (RelativeLayout) creationItemsListView.getChildAt(5);
        TextView processModel = (TextView) rv5.findViewById(R.id.tvItemValue);
        processModel.setText(shorten(projectNew.getProjectProperties().getProcessMethology(), STRINGLENGTH));
        ImageView editProcessModel = (ImageView) rv5.findViewById(R.id.ivEditItem);
        editProcessModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Programming Language
        final RelativeLayout rv6 = (RelativeLayout) creationItemsListView.getChildAt(6);
        TextView programmingLanguage = (TextView) rv6.findViewById(R.id.tvItemValue);
        programmingLanguage.setText(shorten(projectNew.getProjectProperties().getProgrammingLanguage(), STRINGLENGTH));
        ImageView editProgrammingLanguage = (ImageView) rv6.findViewById(R.id.ivEditItem);
        editProgrammingLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Platform
        final RelativeLayout rv7 = (RelativeLayout) creationItemsListView.getChildAt(7);
        TextView platform = (TextView) rv7.findViewById(R.id.tvItemValue);
        platform.setText(shorten(projectNew.getProjectProperties().getPlatform(), STRINGLENGTH));
        ImageView editPlatform = (ImageView) rv7.findViewById(R.id.ivEditItem);
        editPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Industry Sector
        final RelativeLayout rv8 = (RelativeLayout) creationItemsListView.getChildAt(8);
        TextView industrySector = (TextView) rv8.findViewById(R.id.tvItemValue);
        industrySector.setText(shorten(projectNew.getProjectProperties().getIndustrySector(), STRINGLENGTH));
        ImageView editIndustrySector = (ImageView) rv8.findViewById(R.id.ivEditItem);
        editIndustrySector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Estimation Method
        final RelativeLayout rv9 = (RelativeLayout) creationItemsListView.getChildAt(9);
        TextView estimationMethod = (TextView) rv9.findViewById(R.id.tvItemValue);
        estimationMethod.setText(shorten(projectNew.getEstimationMethod(),STRINGLENGTH));
        ImageView editEstimationMethod = (ImageView) rv9.findViewById(R.id.ivEditItem);
        editEstimationMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Influence Factor Set
        final RelativeLayout rv10 = (RelativeLayout) creationItemsListView.getChildAt(10);
        TextView influenceFactor = (TextView) rv10.findViewById(R.id.tvItemValue);
        //TODO: SET Influence Factor Name
        influenceFactor.setText(shorten(projectNew.getInfluencingFactor().getName(),STRINGLENGTH));
        ImageView editInfluenceFactor = (ImageView) rv10.findViewById(R.id.ivEditItem);
        editInfluenceFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String shorten(String text, int length) {
        if(text.length() > length){

            String s = text.substring(0,length);
            s = s + "...";
            return s;
        } else {
            return text;
        }
    }


    private void updateProjectInfoFragment(int position) {
        ProjectInfoFragment f = (ProjectInfoFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
        EditText projectNameET = (EditText) f.getView().findViewById(R.id.projectNameET);
        projectNameET.setText(projectNew.getTitle());
        EditText projectDescriptionET = (EditText) f.getView().findViewById(R.id.projectDescriptionET);
        projectDescriptionET.setText(projectNew.getProjectDescription());
        ImageView projectIcon = (ImageView) f.getView().findViewById(R.id.projectIconIV);
        projectIcon.setImageBitmap(projectNew.getImage());
        TextView iconName = (TextView) f.getView().findViewById(R.id.tvProjectImageName);
        iconName.setText(projectNew.getIconName());
    }

    private void updateInfluencingFactorFragment(int position) {
        InfluencingFactorFragment f = (InfluencingFactorFragment) mSectionsPagerAdapter.instantiateItem(mViewPager,position);

        TextView t = (TextView) f.getView().findViewById(R.id.textViewChosenEstimationMethod);
        t.setText("You have chosen the " + projectNew.getEstimationMethod() + " Estimation Method.");

        ArrayAdapter<String> influencingFactorsAdapter = new ArrayAdapter<String>(f.getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, loadInfluencingFactorsSetList());
        final Spinner influencingFactorsAdapterSpinner = (Spinner)  f.getView().findViewById(R.id.influencingSet);
        influencingFactorsAdapterSpinner.setAdapter(influencingFactorsAdapter);
        influencingFactorsAdapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectNew.setInfluencingFactor(loadInfluencingFactorsSet(projectNew.getEstimationMethod(), influencingFactorsAdapterSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (projectNew.getEstimationMethod().equals("")){
            t.setText("You have chosen no Estimation Method.");
        }

    }

    private InfluencingFactor loadInfluencingFactorsSet(String estimationMethod, String factor) {
        InfluencingFactor influencingFactor;
        if(estimationMethod.equals("Function Point")){
            //TODO: Load Factor From Database
            influencingFactor = new FunctionPointFactor();
            //TODO: load Factor Set from database
            influencingFactor.setName("Factor Set 1");
            influencingFactor.getFunctionPointFactorItems().get(0).setChosenValue(2);
            influencingFactor.getFunctionPointFactorItems().get(1).setChosenValue(2);
            influencingFactor.getFunctionPointFactorItems().get(2).setChosenValue(2);

            influencingFactor.getFunctionPointFactorItems().get(3).getSubFunctionPointFactorItemsList().get(0).setChosenValue(8);
            influencingFactor.getFunctionPointFactorItems().get(3).getSubFunctionPointFactorItemsList().get(1).setChosenValue(2);
            influencingFactor.getFunctionPointFactorItems().get(3).getSubFunctionPointFactorItemsList().get(2).setChosenValue(5);
            influencingFactor.getFunctionPointFactorItems().get(3).getSubFunctionPointFactorItemsList().get(3).setChosenValue(1);

            influencingFactor.getFunctionPointFactorItems().get(3).setChosenValue(0);
            influencingFactor.getFunctionPointFactorItems().get(5).setChosenValue(3);
            influencingFactor.getFunctionPointFactorItems().get(6).setChosenValue(5);

        } else if(estimationMethod.equals("COCOMO")){
            //TODO Create COCOMO Factor
            influencingFactor = new FunctionPointFactor();
            influencingFactor.setName("COCOMO");
        } else if(estimationMethod.equals("COCOMO 2")){
            //TODO Create COCOMO 2 Factor
            influencingFactor = new FunctionPointFactor();
            influencingFactor.setName("COCOMO 2");
        }else{
            influencingFactor = new FunctionPointFactor("No Influencing Factor Set Createt yet");
        }

        return influencingFactor;
    }

    private ArrayList<String> loadInfluencingFactorsSetList() {
        ArrayList<String> influencingFactorItems;
        influencingFactorItems = new ArrayList<>();

        switch (projectNew.getEstimationMethod()) {
            case "Function Point":

                influencingFactorItems.add("Small new Team");
                influencingFactorItems.add("Big Old Team");

                break;
            case "COCOMO":
                influencingFactorItems.add("Cocomo");
                break;
            case "COCOMO 2":
                influencingFactorItems.add("Cocomo 2");
                break;
            default:
                influencingFactorItems.add("");
                break;
        }
        return influencingFactorItems;
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
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
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
