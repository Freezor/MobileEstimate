package com.mobileprojectestimator.mobileprojectestimator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.EstimationMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedCreationFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.InfluencingFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectCreationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectInfoFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectPropOneFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectPropTwoFragment;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.SectionsPagerAdapter;

import java.util.ArrayList;

public class GuidedProjectCreationActivity extends AppCompatActivity
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

    private static int STRINGLENGTH = 12;

    /**
     * List with all Fragments in the creation dialog
     */
    private ArrayList<GuidedCreationFragment> guidedCreationFragmentsArrayList;

    /**
     * List with all factor items the user can choose to the estimation method
     */
    private ArrayList<String> influencingFactorItems;

    /**
     * The New Project that will be created
     */
    private Project projectNew;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_project_creation);


        projectNew = new Project(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGuidedCreation);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), projectNew);
        createFragmentsArrayList();

        // Set up the ViewPager with the sections adapter.
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
                //Log.d("Info",guidedCreationFragmentsArrayList.get(position).toString()+" Position: "+position);
                onChangeViewPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * When the View Pager is changed on new Fragment
     *
     * @param position
     */
    private void onChangeViewPage(int position)
    {
        if (guidedCreationFragmentsArrayList.get(position) instanceof InfluencingFactorFragment)
        {
            updateInfluencingFactorFragment(position);

        } else if (guidedCreationFragmentsArrayList.get(position) instanceof ProjectInfoFragment)
        {
            updateProjectInfoFragment(position);

        } else if (guidedCreationFragmentsArrayList.get(position) instanceof ProjectCreationOverviewFragment)
        {
            updateProjectCreationOverviewFragment(position);

        }
        //TODO: Handle Error with Stack Overflow and Image Change
    }

    /**
     * Create all Fragments that will be used and save them in the array list
     */
    private void createFragmentsArrayList()
    {
        guidedCreationFragmentsArrayList = new ArrayList<>();
        guidedCreationFragmentsArrayList.add(new ProjectInfoFragment());//Position 0
        guidedCreationFragmentsArrayList.add(new ProjectPropOneFragment());//Position 1
        guidedCreationFragmentsArrayList.add(new ProjectPropTwoFragment());//Position 2
        guidedCreationFragmentsArrayList.add(new EstimationMethodFragment());//Position 3
        guidedCreationFragmentsArrayList.add(new InfluencingFactorFragment());//Position 4
        guidedCreationFragmentsArrayList.add(new ProjectCreationOverviewFragment());//Position 5
        mSectionsPagerAdapter.setFragmentList(guidedCreationFragmentsArrayList);
    }

    /**
     * @param position
     */
    private void updateProjectCreationOverviewFragment(int position)
    {
        //TODO: Hier Refactoring weiterführen
        final ProjectCreationOverviewFragment f = (ProjectCreationOverviewFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
        ListView creationItemsListView = (ListView) f.getView().findViewById(R.id.lvProjectCreation);
        //Set Project Name
        final RelativeLayout rv0 = (RelativeLayout) creationItemsListView.getChildAt(0);
        TextView projName = (TextView) rv0.findViewById(R.id.tvItemValue);
        projName.setText(shorten(projectNew.getTitle(), STRINGLENGTH));
        ImageView editName = (ImageView) rv0.findViewById(R.id.ivEditItem);
        editName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Description
        final RelativeLayout rv1 = (RelativeLayout) creationItemsListView.getChildAt(1);
        TextView projDescription = (TextView) rv1.findViewById(R.id.tvItemValue);
        if (projectNew.getProjectDescription() != null)
        {
            projDescription.setText(shorten(projectNew.getProjectDescription(), STRINGLENGTH));
        } else
        {
            projDescription.setText("");
        }
        ImageView editDescription = (ImageView) rv1.findViewById(R.id.ivEditItem);
        editDescription.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Icon
        final RelativeLayout rv2 = (RelativeLayout) creationItemsListView.getChildAt(2);
        TextView iconName = (TextView) rv2.findViewById(R.id.tvItemValue);
        iconName.setText(shorten(projectNew.getIconName(), STRINGLENGTH));
        ImageView editIconName = (ImageView) rv2.findViewById(R.id.ivEditItem);
        editIconName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Market
        final RelativeLayout rv3 = (RelativeLayout) creationItemsListView.getChildAt(3);
        TextView projectMarket = (TextView) rv3.findViewById(R.id.tvItemValue);
        projectMarket.setText(shorten(projectNew.getProjectProperties().getMarket(), STRINGLENGTH));
        ImageView editProjectMarket = (ImageView) rv3.findViewById(R.id.ivEditItem);
        editProjectMarket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Project Development Kind
        final RelativeLayout rv4 = (RelativeLayout) creationItemsListView.getChildAt(4);
        TextView developmentKind = (TextView) rv4.findViewById(R.id.tvItemValue);
        developmentKind.setText(shorten(projectNew.getProjectProperties().getDevelopmentKind(), STRINGLENGTH));
        ImageView editDevelopmentKind = (ImageView) rv4.findViewById(R.id.ivEditItem);
        editDevelopmentKind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Process Methology
        final RelativeLayout rv5 = (RelativeLayout) creationItemsListView.getChildAt(5);
        TextView processModel = (TextView) rv5.findViewById(R.id.tvItemValue);
        processModel.setText(shorten(projectNew.getProjectProperties().getProcessMethology(), STRINGLENGTH));
        ImageView editProcessModel = (ImageView) rv5.findViewById(R.id.ivEditItem);
        editProcessModel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Programming Language
        final RelativeLayout rv6 = (RelativeLayout) creationItemsListView.getChildAt(6);
        TextView programmingLanguage = (TextView) rv6.findViewById(R.id.tvItemValue);
        programmingLanguage.setText(shorten(projectNew.getProjectProperties().getProgrammingLanguage(), STRINGLENGTH));
        ImageView editProgrammingLanguage = (ImageView) rv6.findViewById(R.id.ivEditItem);
        editProgrammingLanguage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Platform
        final RelativeLayout rv7 = (RelativeLayout) creationItemsListView.getChildAt(7);
        TextView platform = (TextView) rv7.findViewById(R.id.tvItemValue);
        platform.setText(shorten(projectNew.getProjectProperties().getPlatform(), STRINGLENGTH));
        ImageView editPlatform = (ImageView) rv7.findViewById(R.id.ivEditItem);
        editPlatform.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Industry Sector
        final RelativeLayout rv8 = (RelativeLayout) creationItemsListView.getChildAt(8);
        TextView industrySector = (TextView) rv8.findViewById(R.id.tvItemValue);
        industrySector.setText(shorten(projectNew.getProjectProperties().getIndustrySector(), STRINGLENGTH));
        ImageView editIndustrySector = (ImageView) rv8.findViewById(R.id.ivEditItem);
        editIndustrySector.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Estimation Method
        final RelativeLayout rv9 = (RelativeLayout) creationItemsListView.getChildAt(9);
        TextView estimationMethod = (TextView) rv9.findViewById(R.id.tvItemValue);
        estimationMethod.setText(shorten(projectNew.getEstimationMethod(), STRINGLENGTH));
        ImageView editEstimationMethod = (ImageView) rv9.findViewById(R.id.ivEditItem);
        editEstimationMethod.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
        //Set Influence Factor Set
        final RelativeLayout rv10 = (RelativeLayout) creationItemsListView.getChildAt(10);
        TextView influenceFactor = (TextView) rv10.findViewById(R.id.tvItemValue);
        //TODO: SET Influence Factor Name
        influenceFactor.setText(shorten(projectNew.getInfluencingFactor().getInfluenceFactorSetName(), STRINGLENGTH));
        ImageView editInfluenceFactor = (ImageView) rv10.findViewById(R.id.ivEditItem);
        editInfluenceFactor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(f.getActivity().getBaseContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Shortens a string to a specific length and add "..."
     *
     * @param text
     * @param length
     * @return
     */
    private String shorten(String text, int length)
    {
        if (text.length() > length)
        {

            String s = text.substring(0, length);
            s = s + "...";
            return s;
        } else
        {
            return text;
        }
    }


    /**
     * @param position
     */
    private void updateProjectInfoFragment(int position)
    {
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

    /**
     * @param position
     */
    private void updateInfluencingFactorFragment(int position)
    {
        InfluencingFactorFragment f = (InfluencingFactorFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);

        TextView t = (TextView) f.getView().findViewById(R.id.textViewChosenEstimationMethod);
        t.setText(String.format(getString(R.string.msg_guided_project_creation_chosen_estimation_method), new Object[]{projectNew.getEstimationMethod()}));

        ArrayAdapter<String> influencingFactorsAdapter = new ArrayAdapter<>(f.getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, loadInfluencingFactorsSetList());
        final Spinner influencingFactorsAdapterSpinner = (Spinner) f.getView().findViewById(R.id.influencingSet);
        influencingFactorsAdapterSpinner.setAdapter(influencingFactorsAdapter);
        influencingFactorsAdapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                projectNew.setInfluencingFactor(loadInfluencingFactorsSet(projectNew.getEstimationMethod(), influencingFactorsAdapterSpinner.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        if (projectNew.getEstimationMethod().equals(""))
        {
            t.setText(R.string.msg_guided_project_creation_no_estimation_method);
        }

    }

    /**
     * Load the chosen estimation method and adds it to the project
     *
     * @param estimationMethod
     * @param itemName
     * @return
     */
    private InfluencingFactor loadInfluencingFactorsSet(String estimationMethod, String itemName)
    {
        //TODO: change when loading from database
        InfluencingFactor influencingFactor;

        if (estimationMethod.equals(getString(R.string.estimation_method_function_point)))
        {
            influencingFactor = loadFunctionPointInfluencingFactors(itemName);
        } else if (estimationMethod.equals(getString(R.string.estimation_method_cocomo)))
        {
            influencingFactor = loadCocomoInfluencingFactors(itemName);
        } else if (estimationMethod.equals(getString(R.string.estimation_method_cocomo_2)))
        {
            influencingFactor = loadCocomo2InfluencingFactors(itemName);
        } else
        {
            influencingFactor = new InfluencingFactor(this,InfluencingFactor.FUNCTIONPOINTFACTORS);
            influencingFactor.setName("No Influencing Factor Set Created yet");
        }

        return influencingFactor;
    }

    /**
     * The Factor set with the name will be loaded from the database
     *
     * @param itemName
     * @return
     */
    private InfluencingFactor loadCocomo2InfluencingFactors(String itemName)
    {
        InfluencingFactor influencingFactor = new InfluencingFactor(this,InfluencingFactor.COCOMO2FACTORS);
        influencingFactor.setName(itemName);
        return influencingFactor;
    }

    /**
     * The Factor set with the name will be loaded from the database
     *
     * @param itemName
     * @return
     */
    private InfluencingFactor loadCocomoInfluencingFactors(String itemName)
    {
        InfluencingFactor influencingFactor = new InfluencingFactor(this,InfluencingFactor.COCOMO2FACTORS);
        influencingFactor.setName(itemName);
        return influencingFactor;
    }

    /**
     * The Factor set with the name will be loaded from the database
     *
     * @param itemName
     * @return
     */
    private InfluencingFactor loadFunctionPointInfluencingFactors(String itemName)
    {
        //TODO: Load Factor From Database
        InfluencingFactor influencingFactor = new InfluencingFactor(this,InfluencingFactor.FUNCTIONPOINTFACTORS);
        influencingFactor.setName(itemName);
        influencingFactor.getInfluenceFactorItems().get(0).setChosenValue(2);
        influencingFactor.getInfluenceFactorItems().get(1).setChosenValue(2);
        influencingFactor.getInfluenceFactorItems().get(2).setChosenValue(2);

        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(0).setChosenValue(8);
        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(1).setChosenValue(2);
        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(2).setChosenValue(5);
        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(3).setChosenValue(1);

        influencingFactor.getInfluenceFactorItems().get(3).setChosenValue(0);
        influencingFactor.getInfluenceFactorItems().get(5).setChosenValue(3);
        influencingFactor.getInfluenceFactorItems().get(6).setChosenValue(5);
        return influencingFactor;
    }

    /**
     * Load the influencing factor sets.
     * This depends on the chosen estimation method
     *
     * @return
     */
    private ArrayList<String> loadInfluencingFactorsSetList()
    {
        influencingFactorItems = new ArrayList<>();

        if (projectNew.getEstimationMethod().equals(getString(R.string.estimation_method_function_point)))
        {
            loadFunctionPointFactorSet();

        } else if (projectNew.getEstimationMethod().equals(getString(R.string.estimation_method_cocomo)))
        {
            loadCocomoFactorSet();
        } else if (projectNew.getEstimationMethod().equals(getString(R.string.estimation_method_cocomo_2)))
        {
            loadCocomo2FactorSet();
        } else
        {
            influencingFactorItems.add(getString(R.string.error_this_should_not_happen));
        }
        return influencingFactorItems;
    }

    /**
     * load the COCOMO 2 influence factor set from the database
     */
    private void loadCocomo2FactorSet()
    {
        influencingFactorItems.add("Cocomo 2");
    }

    /**
     * load the COCOMO influence factor set from the database
     */
    private void loadCocomoFactorSet()
    {
        influencingFactorItems.add("Cocomo");
    }

    /**
     * load the function point influence factor set from the database
     */
    private void loadFunctionPointFactorSet()
    {
        //TODO: load Factor Set from DB
        influencingFactorItems.add("Small new Team");
        influencingFactorItems.add("Big Old Team");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guided_project_creation, menu);
        return true;
    }

    /**
     * Create and Show the Cancel Dialog when the user wants to exit the creation dialog
     */
    private void cancelCreationDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(GuidedProjectCreationActivity.this);
        builder.setMessage(R.string.cancel_project_creation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        NavUtils.navigateUpFromSameTask(GuidedProjectCreationActivity.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    @Override
    public void onBackPressed()
    {
        //TODO: fix Error
        //DO not Use. Will be done with cancelCreationDialog
        //super.onBackPressed();
        cancelCreationDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case android.R.id.home:
                cancelCreationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
