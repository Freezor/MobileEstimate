package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProjectInformationActivity extends DatabaseActivity
{
    private Project project;
    private TextView tvProjectName;
    private ImageView ivProjectIcon;
    private TextView tvIndustrySectorValue;
    private TextView tvPlatformValue;
    private TextView tvProgrammingLangValue;
    private TextView tvMethologyValue;
    private TextView tvDevKindValue;
    private TextView tvMarketValue;
    private TextView tvProjectDescription;
    private int projectId;
    private ArrayList<String> developmentMarketItems;
    private ArrayList<String> estimationMethodItems;
    private ArrayList<String> developmentKindItems;
    private ArrayList<String> processMethologieItems;
    private ArrayList<String> programmingLanguageItems;
    private ArrayList<String> platformItems;
    private ArrayList<String> industrySectorItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProjectIcon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatabase();

        tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        ivProjectIcon = (ImageView) findViewById(R.id.ivProjectIcon);
        tvProjectDescription = (TextView) findViewById(R.id.tvProjectDescription);
        tvMarketValue = (TextView) findViewById(R.id.tvMarketValue);
        tvDevKindValue = (TextView) findViewById(R.id.tvDevKindValue);
        tvMethologyValue = (TextView) findViewById(R.id.tvMethologyValue);
        tvProgrammingLangValue = (TextView) findViewById(R.id.tvProgLangValue);
        tvPlatformValue = (TextView) findViewById(R.id.tvPlatformValue);
        tvIndustrySectorValue = (TextView) findViewById(R.id.tvIndustrySectorValue);

        Intent intent = getIntent();
        projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), 0);

        updateProjectInformationValues();

        tvProjectName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectNameDialog();
            }
        });
        ivProjectIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectIconDialog();
            }
        });

        tvProjectDescription.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectDescriptionDialog();
            }
        });
        tvMarketValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectMarketDialog();
            }
        });
        tvDevKindValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectDevelopmentKindDialog();
            }
        });
        tvMethologyValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectMethologyDialog();
            }
        });
        tvProgrammingLangValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectProgrammingLanguageDialog();
            }
        });
        tvPlatformValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectPlatformValueDialog();
            }
        });
        tvIndustrySectorValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProjectIndustrySectorDialog();
            }
        });

        loadPropertyValues();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("INFO", "FunctionPointProjectActivity: onActivityResult");
        if (resultCode == Integer.parseInt(getString(R.string.PROJECT_ICON_DIALOG_CODE)))
        {
            Bundle res = data.getExtras();
            int projectId = res.getInt("PROJECTICONID");
            HashMap<String, String> map = databaseHelper.getIconInformationsById(projectId);
            project.setIconName(map.get("name"));
            project.setImage(databaseHelper.loadProjectIcon(map.get("path")));
            updateContent();
        }

    }

    /**
     * Load the all possible Property Values from the database
     */
    private void loadPropertyValues()
    {
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

        estimationMethodItems = databaseHelper.getEstimationMethodNames();
        Collections.sort(estimationMethodItems);
    }

    /**
     * (Re-)Loads the projectFrom the database and set the values to the textViews
     */
    private void updateProjectInformationValues()
    {
        project = databaseHelper.loadProjectById(this, String.valueOf(projectId));

        updateContent();
    }

    /**
     * Updates the content of the View
     */
    private void updateContent()
    {
        tvProjectName.setText(project.getTitle());
        ivProjectIcon.setImageBitmap(project.getImage());
        tvProjectDescription.setText(project.getProjectDescription());
        tvMarketValue.setText(project.getProjectProperties().getMarket());
        tvDevKindValue.setText(project.getProjectProperties().getDevelopmentKind());
        tvMethologyValue.setText(project.getProjectProperties().getProcessMethology());
        tvProgrammingLangValue.setText(project.getProjectProperties().getProgrammingLanguage());
        tvPlatformValue.setText(project.getProjectProperties().getPlatform());
        tvIndustrySectorValue.setText(project.getProjectProperties().getIndustrySector());
    }

    private void openProjectNameDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_project_name));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        if (project.getTitle() != null && !project.getTitle().equals(""))
        {
            input.setText(project.getTitle());
        }
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                project.setTitle(input.getText().toString());
                updateContent();
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

    private void openProjectIconDialog()
    {
        Intent intent = new Intent(this, ChooseProjectIconActivity.class);
        startActivityForResult(intent, Integer.parseInt(getString(R.string.PROJECT_ICON_DIALOG_CODE)));
    }

    private void openProjectDescriptionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_description));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setSingleLine(false);
        if (project.getProjectDescription() != null && !project.getProjectDescription().equals(""))
        {
            input.setText(project.getProjectDescription());
        }
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                project.setProjectDescription(input.getText().toString());
                updateContent();
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

    private void openProjectMarketDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_market));

        final CharSequence[] items = developmentMarketItems.toArray(new String[developmentMarketItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setMarket(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openProjectDevelopmentKindDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_development_kind));

        final CharSequence[] items = developmentKindItems.toArray(new String[developmentKindItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setDevelopmentKind(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openProjectMethologyDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_methology));
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        final CharSequence[] items = processMethologieItems.toArray(new String[processMethologieItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setProcessMethology(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openProjectProgrammingLanguageDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_programming_language));

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        final CharSequence[] items = programmingLanguageItems.toArray(new String[programmingLanguageItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setProgrammingLanguage(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openProjectPlatformValueDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_platform));
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        final CharSequence[] items = platformItems.toArray(new String[platformItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setPlatform(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openProjectIndustrySectorDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_industry_sector));
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        final CharSequence[] items = industrySectorItems.toArray(new String[industrySectorItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setIndustrySector(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_information, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_save_project_informations:
                saveProject();
            case R.id.action_change_estimation_method:
                return super.onOptionsItemSelected(item);
            case R.id.action_find_related_project:
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveProject()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectInformationActivity.this);
        builder.setMessage(R.string.dialog_save_project_request)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        databaseHelper.updateExistingProject(project);
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
}