package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
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
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProjectPropertiesActivity extends DatabaseActivity
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
    private TextView tvArchitectureValue;
    private ArrayList<String> architectureItems;

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
        tvArchitectureValue = (TextView) findViewById(R.id.tvArchitectureValue);

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
        tvProjectName.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvProjectName.getText().toString());
                return true;
            }
        });
        tvMarketValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvMarketValue.getText().toString());
                return true;
            }
        });
        tvDevKindValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvDevKindValue.getText().toString());
                return true;
            }
        });
        tvMethologyValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvMethologyValue.getText().toString());
                return true;
            }
        });
        tvProgrammingLangValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvProgrammingLangValue.getText().toString());
                return true;
            }
        });
        tvPlatformValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvPlatformValue.getText().toString());
                return true;
            }
        });
        tvIndustrySectorValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvIndustrySectorValue.getText().toString());
                return true;
            }
        });
        tvArchitectureValue.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvArchitectureValue.getText().toString());
                return true;
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
        tvArchitectureValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSoftwareArchitectureDialog();
            }
        });
        loadPropertyValues();
    }

    private void openSoftwareArchitectureDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.project_creation_architecture));
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        final CharSequence[] items = architectureItems.toArray(new String[architectureItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                project.getProjectProperties().setArchitecture(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showValueToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("INFO", "FunctionPointProjectActivity: onActivityResult");
        if (resultCode == Integer.parseInt(getString(R.string.PROJECT_ICON_DIALOG_CODE)))
        {
            Bundle res = data.getExtras();
            int projectId = res.getInt("PROJECTICONID");
            HashMap<String, String> map = databaseHelper.getIconInformationsById(projectId);
            this.project.setIconId(map.get("id"));
            this.project.setIconName(databaseHelper.getStringResourceValueByResourceName(map.get("name")));
            this.project.setImage(databaseHelper.loadProjectIcon(map.get("path")));
            updateContent();
        } else if (requestCode == Integer.parseInt((getString(R.string.FIND_RELATED_PROJECT_REQUEST_CODE))))
        {
            Log.d("INFO", "Filter Request");
            if (resultCode == RESULT_OK)
            {
                Intent intent = getIntent();
                projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), 0);
            }
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

        architectureItems = databaseHelper.loadAllPropertiesByName("SoftwareArchitecturePatterns");
        Collections.sort(architectureItems);

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
        tvArchitectureValue.setText(project.getProjectProperties().getArchitecture());
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
    public void onBackPressed()
    {
        //TODO: ask for not saved informations
        setResult(Activity.RESULT_OK);
        finish();
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
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save_project_informations:
                saveProject();
                return true;
            case R.id.action_change_estimation_method:
                return super.onOptionsItemSelected(item);
            case R.id.action_find_related_project:
                Intent i = new Intent(ProjectPropertiesActivity.this, FindRelatedProjectsActivity.class);
                i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), this.project.getProjectId());
                startActivityForResult(i, Integer.parseInt((getString(R.string.FIND_RELATED_PROJECT_REQUEST_CODE))));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveProject()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectPropertiesActivity.this);
        builder.setMessage(R.string.dialog_save_project_request)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        databaseHelper.updateExistingProjectInformations(project);
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
