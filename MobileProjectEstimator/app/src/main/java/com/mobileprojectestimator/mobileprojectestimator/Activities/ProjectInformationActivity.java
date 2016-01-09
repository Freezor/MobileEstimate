package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProjectIcon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatabase();

        Intent intent = getIntent();
        int projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID),0);
        project = databaseHelper.loadProjectById(this,String.valueOf(projectId));

        tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        ivProjectIcon = (ImageView) findViewById(R.id.ivProjectIcon);
        tvProjectDescription = (TextView) findViewById(R.id.tvProjectDescription);
        tvMarketValue = (TextView) findViewById(R.id.tvMarketValue);
        tvDevKindValue = (TextView) findViewById(R.id.tvDevKindValue);
        tvMethologyValue = (TextView) findViewById(R.id.tvMethologyValue);
        tvProgrammingLangValue = (TextView) findViewById(R.id.tvProgLangValue);
        tvPlatformValue = (TextView) findViewById(R.id.tvPlatformValue);
        tvIndustrySectorValue = (TextView) findViewById(R.id.tvIndustrySectorValue);

        tvProjectName.setText(project.getTitle());
        ivProjectIcon.setImageBitmap(project.getImage());
        tvProjectDescription.setText(project.getProjectDescription());
        tvMarketValue.setText(project.getProjectProperties().getMarket());
        tvDevKindValue.setText(project.getProjectProperties().getDevelopmentKind());
        tvMethologyValue.setText(project.getProjectProperties().getProcessMethology());
        tvProgrammingLangValue.setText(project.getProjectProperties().getProgrammingLanguage());
        tvPlatformValue.setText(project.getProjectProperties().getPlatform());
        tvIndustrySectorValue.setText(project.getProjectProperties().getIndustrySector());

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
    }

    private void openProjectNameDialog()
    {
        
    }

    private void openProjectIconDialog()
    {

    }

    private void openProjectDescriptionDialog()
    {

    }

    private void openProjectMarketDialog()
    {

    }

    private void openProjectDevelopmentKindDialog()
    {

    }

    private void openProjectMethologyDialog()
    {

    }

    private void openProjectProgrammingLanguageDialog()
    {

    }

    private void openProjectPlatformValueDialog()
    {

    }

    private void openProjectIndustrySectorDialog()
    {

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
                return super.onOptionsItemSelected(item);
            case R.id.action_change_estimation_method:
                return super.onOptionsItemSelected(item);
            case R.id.action_find_related_project:
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
