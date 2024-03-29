package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
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

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database.DatabaseInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.EstimationMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.GuidedCreationFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.InfluencingFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.ProjectCreationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.ProjectInfoFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.ProjectPropOneFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.ProjectPropTwoFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.SectionsPagerAdapter;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import static com.mobileprojectestimator.mobileprojectestimator.R.id.ivEditItem;
import static com.mobileprojectestimator.mobileprojectestimator.R.id.tvItemValue;

public class GuidedProjectCreationActivity extends DatabaseActivity
{
    protected RelativeLayout sublayout;
    protected ImageView editName;
    protected ImageView editInfluenceFactor;
    protected ImageView editEstimationMethod;
    protected ImageView editIndustrySector;
    protected ImageView editPlatform;
    protected ImageView editProgrammingLanguage;
    protected ImageView editProcessModel;
    protected ImageView editDevelopmentKind;
    protected ImageView editProjectMarket;
    protected ImageView editIconName;
    protected ImageView editDescription;
    protected ListView creationItemsListView;
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
    private TextView projName;
    private TextView influenceFactor;
    private TextView estimationMethod;
    private TextView industrySector;
    private TextView projDescription;
    private TextView platform;
    private TextView programmingLanguage;
    private TextView processModel;
    private TextView developmentKind;
    private TextView projectMarket;
    private TextView iconName;
    private ProjectCreationOverviewFragment projectCreationOverviewFragment;
    private ProjectInfoFragment projectInfoFragment;
    private EditText projectNameET;
    private EditText projectDescriptionET;
    private ImageView projectIcon;
    private TextView projInfoIconName;
    private InfluencingFactorFragment influencingFactorFragment;
    private Spinner influencingFactorsAdapterSpinner;
    private ArrayList<DatabaseInfluenceFactorItem> dbInfluenceFactorItems;
    private ArrayList<String> developmentMarketItems;
    private ArrayList<String> estimationMethodItems;
    private ArrayList<String> developmentKindItems;
    private ArrayList<String> processMethologieItems;
    private ArrayList<String> programmingLanguageItems;
    private ArrayList<String> platformItems;
    private ArrayList<String> industrySectorItems;
    private ArrayList<DatabaseInfluenceFactorItem> influenceFactorItems;
    private Spinner spEstimationTechnique;
    private ArrayAdapter<String> influencingFactorsAdapter;
    private TextView architecture;
    private ImageView editArchitecture;
    private ArrayList<String> softwareArchitectureItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_project_creation);

        if (databaseHelper == null)
        {
            initDatabase();
        }

        projectNew = new Project(this);

        projectNew.setInfluencingFactor(databaseHelper.loadInfluenceFactorById(2001));

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
        mViewPager.setOffscreenPageLimit(6);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDatabase();

        developmentMarketItems = databaseHelper.loadAllPropertiesByName("DevelopmentMarkets");
        Collections.sort(developmentMarketItems);

        developmentKindItems = databaseHelper.loadAllPropertiesByName("DevelopmentTypes");
        Collections.sort(developmentKindItems);

        processMethologieItems = databaseHelper.loadAllPropertiesByName("ProcessMethologies");
        Collections.sort(processMethologieItems);

        programmingLanguageItems = databaseHelper.loadAllPropertiesByName("ProgrammingLanguages");
        Collections.sort(programmingLanguageItems);

        platformItems = databaseHelper.loadAllPropertiesByName("Platforms");
        Collections.sort(platformItems);

        industrySectorItems = databaseHelper.loadAllPropertiesByName("IndustrySectors");
        Collections.sort(industrySectorItems);

        softwareArchitectureItems = databaseHelper.loadAllPropertiesByName("SoftwareArchitecturePatterns");
        Collections.sort(softwareArchitectureItems);

        estimationMethodItems = databaseHelper.getEstimationMethodNames();
        Collections.sort(estimationMethodItems);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("INFO", "FunctionPointProjectActivity: onActivityResult");
        if (resultCode == Integer.parseInt(getString(R.string.PROJECT_ICON_DIALOG_CODE)))
        {
            Bundle res = data.getExtras();
            int projectId = res.getInt("PROJECTICONID");
            mSectionsPagerAdapter.updateIconId(projectId);
            mSectionsPagerAdapter.notifyDataSetChanged();
            if (mViewPager.getCurrentItem() == 5)
            {
                updateProjectCreationOverviewFragment(5);
            }

        }

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
     * Update the Overview Fragment
     * Initialises the Overview View Items if they don´t exist
     *
     * @param position
     */
    private void updateProjectCreationOverviewFragment(int position)
    {
        if (this.projectCreationOverviewFragment != null)
        {
            updateOverviewItemsValue();
        } else
        {
            instanciateOverviewViewItems(position);
        }
    }

    /**
     * Create new instances of the Overview Items
     * Should only be called once the guided Creation Dialog is opened
     *
     * @param position
     */
    private void instanciateOverviewViewItems(int position)
    {
        Log.d("Info", "GuidedProjectCreation: instanciateOverviewViewItems");
        try
        {


            projectCreationOverviewFragment = (ProjectCreationOverviewFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position); //Not sure if this works, eventually extract to updateProjectCreationFragment
            //noinspection ConstantConditions
            creationItemsListView = (ListView) projectCreationOverviewFragment.getView().findViewById(R.id.lvProjectCreation);
            //Set Project Name
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(0);
            projName = (TextView) sublayout.findViewById(tvItemValue);
            editName = (ImageView) sublayout.findViewById(ivEditItem);
            editName.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editProjectName();
                }
            });
            //Set Project Description
            int childPosition = 1;
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            projDescription = (TextView) sublayout.findViewById(tvItemValue);
            editDescription = (ImageView) sublayout.findViewById(ivEditItem);
            editDescription.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editDescription();
                }
            });
            //Set Project Icon
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            iconName = (TextView) sublayout.findViewById(tvItemValue);
            editIconName = (ImageView) sublayout.findViewById(ivEditItem);
            editIconName.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editIcon();
                }
            });
            //Set Project Market
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            projectMarket = (TextView) sublayout.findViewById(tvItemValue);
            editProjectMarket = (ImageView) sublayout.findViewById(ivEditItem);
            editProjectMarket.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editProjectMarket();
                }
            });
            //Set Project Development Kind
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            developmentKind = (TextView) sublayout.findViewById(tvItemValue);
            editDevelopmentKind = (ImageView) sublayout.findViewById(ivEditItem);
            editDevelopmentKind.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editDevelopmentKind();
                }
            });
            //Set Process Methology
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            processModel = (TextView) sublayout.findViewById(tvItemValue);
            editProcessModel = (ImageView) sublayout.findViewById(ivEditItem);
            editProcessModel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editProcessMethology();
                }
            });
            //Set Programming Language
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            programmingLanguage = (TextView) sublayout.findViewById(tvItemValue);
            editProgrammingLanguage = (ImageView) sublayout.findViewById(ivEditItem);
            editProgrammingLanguage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editProgrammingLanguage();
                }
            });
            //Set Platform
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            platform = (TextView) sublayout.findViewById(tvItemValue);
            editPlatform = (ImageView) sublayout.findViewById(ivEditItem);
            editPlatform.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editPlatform();
                }
            });
            //Set Industry Sector
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            industrySector = (TextView) sublayout.findViewById(tvItemValue);
            editIndustrySector = (ImageView) sublayout.findViewById(ivEditItem);
            editIndustrySector.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editIndustrySector();
                }
            });
            //Set Software Architecture
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            architecture = (TextView) sublayout.findViewById(tvItemValue);
            editArchitecture = (ImageView) sublayout.findViewById(ivEditItem);
            editArchitecture.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editArchitecture();
                }
            });
            //Set Estimation Method
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            estimationMethod = (TextView) sublayout.findViewById(tvItemValue);
            editEstimationMethod = (ImageView) sublayout.findViewById(ivEditItem);
            editEstimationMethod.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editEstimationMethod();
                }
            });
            //Set Influence Factor Set
            sublayout = (RelativeLayout) creationItemsListView.getChildAt(childPosition++);
            influenceFactor = (TextView) sublayout.findViewById(tvItemValue);
            editInfluenceFactor = (ImageView) sublayout.findViewById(ivEditItem);
            editInfluenceFactor.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editInfluenceFactor();
                }
            });

            //fill the items with the actual value
            updateOverviewItemsValue();
            Log.d("Info", "GuidedProjectCreation: instanciateOverviewViewItems - successful");
        } catch (Exception e)
        {
            Log.d("Error", "instanciateOverviewViewItems: " + e.toString());
        }
    }

    private void editArchitecture()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_architecture));

        final CharSequence[] items = softwareArchitectureItems.toArray(new String[softwareArchitectureItems.size()]);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setArchitecture(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Influence Factor
     */
    private void editInfluenceFactor()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.estimation_technique_hint));

        influenceFactorItems = databaseHelper.getActiveInfluenceFactorItems(databaseHelper.getEstimationMethodId(projectNew.getEstimationMethod()));
        ArrayList<String> infItems = new ArrayList<>();
        for (DatabaseInfluenceFactorItem i : influenceFactorItems)
        {
            infItems.add(i.get_name());
        }

        final CharSequence[] items = infItems.toArray(new String[infItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                String factorName = items[item].toString();
                for (DatabaseInfluenceFactorItem i : influenceFactorItems)
                {
                    if (i.get_name().equals(factorName))
                    {
                        projectNew.setInfluencingFactor(databaseHelper.loadInfluenceFactorById(i.get_id()));
                        updateProjectCreationOverviewFragment(5);
                        break;
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Estimation Method
     */
    private void editEstimationMethod()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.estimation_technique_hint));

        final CharSequence[] items = estimationMethodItems.toArray(new String[estimationMethodItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.setEstimationMethod(items[item].toString());
                influenceFactorItems = databaseHelper.getActiveInfluenceFactorItems(databaseHelper.getEstimationMethodId(projectNew.getEstimationMethod()));
                projectNew.setInfluencingFactor(databaseHelper.loadInfluenceFactorById(influenceFactorItems.get(0).get_id()));
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Industry Sector
     */
    private void editIndustrySector()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_industry_sector));

        final CharSequence[] items = industrySectorItems.toArray(new String[industrySectorItems.size()]);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setIndustrySector(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Platform
     */
    private void editPlatform()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_platform));

        final CharSequence[] items = platformItems.toArray(new String[platformItems.size()]);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setPlatform(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Programming Language
     */
    private void editProgrammingLanguage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_programming_language));

        final CharSequence[] items = programmingLanguageItems.toArray(new String[programmingLanguageItems.size()]);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setProgrammingLanguage(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Process Methology
     */
    private void editProcessMethology()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_methology));

        final CharSequence[] items = processMethologieItems.toArray(new String[processMethologieItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setProcessMethology(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialoge to edit the Development Kind
     */
    private void editDevelopmentKind()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_development_kind));

        final CharSequence[] items = developmentKindItems.toArray(new String[developmentKindItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setDevelopmentKind(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialog to edit the Project Market
     */
    private void editProjectMarket()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_market));

        final CharSequence[] items = developmentMarketItems.toArray(new String[developmentMarketItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                projectNew.getProjectProperties().setMarket(items[item].toString());
                updateProjectCreationOverviewFragment(5);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Opens Dialog to edit the Icon
     */
    private void editIcon()
    {
        Intent intent = new Intent(this, ChooseProjectIconActivity.class);
        startActivityForResult(intent, Integer.parseInt(getString(R.string.PROJECT_ICON_DIALOG_CODE)));
    }

    /**
     * Opens Dialog to edit the Description
     */
    private void editDescription()
    {
        Log.d("Info", "GuidedProjectCreation: editDescription");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_description));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setSingleLine(false);
        if (projectNew.getProjectDescription() != null && !projectNew.getProjectDescription().equals(""))
        {
            input.setText(projectNew.getProjectDescription());
        }
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                projectNew.setProjectDescription(input.getText().toString());
                updateProjectCreationOverviewFragment(5);
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

    /**
     * Opens Dialog to edit the Name
     */
    private void editProjectName()
    {
        Log.d("Info", "GuidedProjectCreation: editProjectName");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_project_name));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        if (projectNew.getTitle() != null && !projectNew.getTitle().equals(""))
        {
            input.setText(projectNew.getTitle());
        }
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                projectNew.setTitle(input.getText().toString());
                updateProjectCreationOverviewFragment(5);
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

    /**
     * Update all View Items with the actual value the user selected
     */
    private void updateOverviewItemsValue()
    {
        Log.d("Info", "GuidedProjectCreation: updateOverviewItemsValue");
        if (projName == null)
        {
            Log.d("Info", "GuidedProjectCreation: updateOverviewItemsValue: values null");
            instanciateOverviewViewItems(5);
        } else
        {
            projName.setText(projectNew.getTitle());
            if (projectNew.getProjectDescription() != null)
            {
                projDescription.setText(projectNew.getProjectDescription());
            } else
            {
                projDescription.setText("");
            }
            iconName.setText(projectNew.getIconName());
            projectMarket.setText(projectNew.getProjectProperties().getMarket());
            developmentKind.setText(projectNew.getProjectProperties().getDevelopmentKind());
            processModel.setText(projectNew.getProjectProperties().getProcessMethology());
            programmingLanguage.setText(projectNew.getProjectProperties().getProgrammingLanguage());
            platform.setText(projectNew.getProjectProperties().getPlatform());
            industrySector.setText(projectNew.getProjectProperties().getIndustrySector());
            architecture.setText(projectNew.getProjectProperties().getArchitecture());
            estimationMethod.setText(projectNew.getEstimationMethod());
            //TODO if factor not in list of possible factors set factor to first in list
            if (projectNew.getInfluencingFactor() == null)
            {
                influenceFactor.setText(getString(R.string.property_not_set));
            } else
            {
                influenceFactor.setText(projectNew.getInfluencingFactor().getInfluenceFactorSetName());
            }
        }
    }


    /**
     * Update the project Info Fragment
     * Set the Values and initialise the Views
     *
     * @param position
     */
    private void updateProjectInfoFragment(int position)
    {
        if (projectInfoFragment == null)
        {
            initialiseProjectInfoFragment(position);
        } else
        {
            setProjectInfoValues();
        }
    }

    /**
     * Set the actual values for the Project Info Fragment
     */
    private void setProjectInfoValues()
    {
        projectNameET.setText(projectNew.getTitle());
        projectDescriptionET.setText(projectNew.getProjectDescription());
        projectIcon.setImageBitmap(projectNew.getImage());
        projInfoIconName.setText(projectNew.getIconName());
    }

    /**
     * Init all Views of the Project Info Fragment
     *
     * @param position
     */
    private void initialiseProjectInfoFragment(int position)
    {
        try
        {
            projectInfoFragment = (ProjectInfoFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
            //noinspection ConstantConditions
            projectNameET = (EditText) projectInfoFragment.getView().findViewById(R.id.projectNameET);
            projectDescriptionET = (EditText) projectInfoFragment.getView().findViewById(R.id.projectDescriptionET);
            projectIcon = (ImageView) projectInfoFragment.getView().findViewById(R.id.projectIconIV);
            projInfoIconName = (TextView) projectInfoFragment.getView().findViewById(R.id.tvProjectImageName);

            setProjectInfoValues();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param position
     */
    private void updateInfluencingFactorFragment(int position)
    {
        if (this.influencingFactorFragment != null)
        {
            updateInfluenceFactorFragment();
        } else
        {
            initialiseInfluencingFactorFragment(position);
        }


    }

    private void updateInfluenceFactorFragment()
    {
        final ArrayAdapter<String> estimationTechniques = new ArrayAdapter<String>(influencingFactorFragment.getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, databaseHelper.getEstimationMethodNames());
        if (projectNew.getEstimationMethod() == null || projectNew.getEstimationMethod().equals(""))
        {
            projectNew.setEstimationMethod(estimationTechniques.getItem(0));
        } else
        {
            spEstimationTechnique.setSelection(estimationTechniques.getPosition(projectNew.getEstimationMethod()));
        }
        influencingFactorsAdapter = new ArrayAdapter<>(influencingFactorFragment.getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, loadInfluencingFactorsSetList());
        if (loadInfluencingFactorsSetList().contains(projectNew.getInfluencingFactor()))
        {
            influencingFactorsAdapterSpinner.setSelection(influencingFactorsAdapter.getPosition(projectNew.getInfluencingFactor().getInfluenceFactorSetName()));

        } else
        {
            influencingFactorsAdapterSpinner.setSelection(0);
        }
        influencingFactorsAdapterSpinner.setAdapter(influencingFactorsAdapter);
        influencingFactorsAdapter.notifyDataSetChanged();
    }

    /**
     * Initialises the Factor Set Fragment View Elements
     *
     * @param position
     */
    private void initialiseInfluencingFactorFragment(int position)
    {

        try
        {
            influencingFactorFragment = (InfluencingFactorFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);

            final ArrayAdapter<String> estimationTechniques = new ArrayAdapter<String>(influencingFactorFragment.getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, databaseHelper.getEstimationMethodNames());
            spEstimationTechnique = (Spinner) influencingFactorFragment.getView().findViewById(R.id.spEstimationTechnique);
            spEstimationTechnique.setAdapter(estimationTechniques);
            spEstimationTechnique.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    projectNew.setEstimationMethod(estimationTechniques.getItem(position));
                    updateInfluencingFactorFragment(guidedCreationFragmentsArrayList.indexOf(influencingFactorFragment));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

            if (projectNew.getEstimationMethod() == null || projectNew.getEstimationMethod().equals(""))
            {
                projectNew.setEstimationMethod(estimationTechniques.getItem(0));
            } else
            {
                spEstimationTechnique.setSelection(estimationTechniques.getPosition(projectNew.getEstimationMethod()));
            }

            influencingFactorsAdapter = new ArrayAdapter<>(influencingFactorFragment.getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, loadInfluencingFactorsSetList());
            influencingFactorsAdapterSpinner = (Spinner) influencingFactorFragment.getView().findViewById(R.id.influencingSet);
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

            if (projectNew.getInfluencingFactor().getInfluenceFactorSetName() != null)
            {
                influencingFactorsAdapterSpinner.setSelection(influencingFactorsAdapter.getPosition(projectNew.getInfluencingFactor().getInfluenceFactorSetName()));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
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

        if (estimationMethod.equals(getString(R.string.estimation_technique_function_point)))
        {
            influencingFactor = loadFunctionPointInfluencingFactors(itemName);
        } else if (estimationMethod.equals(getString(R.string.estimation_technique_cocomo)))
        {
            influencingFactor = loadCocomoInfluencingFactors(itemName);
        } else if (estimationMethod.equals(getString(R.string.estimation_technique_cocomo_2)))
        {
            influencingFactor = loadCocomo2InfluencingFactors(itemName);
        } else
        {
            influencingFactor = new InfluencingFactor(this, InfluencingFactor.FUNCTIONPOINTFACTORS);
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
        InfluencingFactor influencingFactor = new InfluencingFactor(this, InfluencingFactor.COCOMO2FACTORS);
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
        InfluencingFactor influencingFactor = new InfluencingFactor(this, InfluencingFactor.COCOMO2FACTORS);
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
        InfluencingFactor influencingFactor = new InfluencingFactor(this, InfluencingFactor.FUNCTIONPOINTFACTORS);
        influencingFactor.setName(itemName);

        int itemId = 0;

        for (DatabaseInfluenceFactorItem item : dbInfluenceFactorItems)
        {
            if (item.get_name().equals(itemName))
            {
                itemId = item.get_influenceFactorId();
            }
        }

        if (itemId == 0)
        {
            Log.d("ERROR", String.format("Item ID not found for: %s", itemName));
        }

        ArrayList<Integer> influenceFactorValues = databaseHelper.loadFunctionPointInfluenceValues(itemId);

        influencingFactor.getInfluenceFactorItems().get(0).setChosenValue(influenceFactorValues.get(0));
        influencingFactor.getInfluenceFactorItems().get(1).setChosenValue(influenceFactorValues.get(1));
        influencingFactor.getInfluenceFactorItems().get(2).setChosenValue(influenceFactorValues.get(2));

        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(0).setChosenValue(influenceFactorValues.get(3));
        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(1).setChosenValue(influenceFactorValues.get(4));
        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(2).setChosenValue(influenceFactorValues.get(5));
        influencingFactor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(3).setChosenValue(influenceFactorValues.get(6));

        influencingFactor.getInfluenceFactorItems().get(3).setChosenValue(influenceFactorValues.get(7));
        influencingFactor.getInfluenceFactorItems().get(5).setChosenValue(influenceFactorValues.get(8));

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
        Log.d("Info", "loadInfluencingFactorsSetList");
        //initDatabase doesn´t work here. Don´t know why...
        this.databaseHelper = new DataBaseHelper(this);

        try
        {

            databaseHelper.createDataBase();

        } catch (IOException ioe)
        {

            throw new Error("Unable to create database");

        }

        try
        {

            databaseHelper.openDataBase();

        } catch (SQLException sqle)
        {
            Log.d("ERROR", sqle.toString());
        }

        influencingFactorItems = new ArrayList<>();

        int estimationMethodDbId = databaseHelper.getEstimationMethodId(projectNew.getEstimationMethod());
        dbInfluenceFactorItems = databaseHelper.getActiveInfluenceFactorItems(estimationMethodDbId);


        if (!dbInfluenceFactorItems.isEmpty())
        {
            for (DatabaseInfluenceFactorItem item : dbInfluenceFactorItems)
            {
                influencingFactorItems.add(item.get_name());
            }
        } else
        {
            influencingFactorItems.add(getString(R.string.error_this_should_not_happen));
        }
        return influencingFactorItems;
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
            case R.id.action_list_creation:
                projectNew.getProjectProperties().setProgrammingLanguage(programmingLanguageItems.get(0));
                projectNew.getProjectProperties().setPlatform(platformItems.get(0));
                projectNew.getProjectProperties().setIndustrySector(industrySectorItems.get(0));
                projectNew.getProjectProperties().setArchitecture(softwareArchitectureItems.get(0));
                mViewPager.setCurrentItem(5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
