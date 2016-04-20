package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectSearchResultsActivity extends DatabaseActivity
{
    private String searchQuery;
    private List<Project> projectsList;
    private ProjectListAdapter projectsAdapter;
    private ListView projectsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_search_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProjectSearchResult);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatabase();

        Intent intent = getIntent();
        searchQuery = intent.getStringExtra(getString(R.string.PROJECT_SEARCH_QUERY));
        projectsList = new ArrayList<>();
        projectsAdapter = new ProjectListAdapter(this, projectsList);
        projectsListView = (ListView) findViewById(R.id.projectsList);

        loadProjects();
        projectsListView.setAdapter(projectsAdapter);
        projectsAdapter.notifyDataSetChanged();
    }

    public void onLongClickProject(final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> infItems = new ArrayList<>();
        infItems.add("Project Informations");
        infItems.add("Delete Project");
        final CharSequence[] items = infItems.toArray(new String[infItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                String optionItem = items[item].toString();
                if (optionItem.equals("Project Informations"))
                {
                    Intent i = new Intent(ProjectSearchResultsActivity.this, ProjectPropertiesActivity.class);
                    i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), projectsList.get(position).getProjectId());
                    startActivityForResult(i, Integer.parseInt((getString(R.string.CREATE_NEW_PROJECT_REQUEST_CODE))));
                } else if (optionItem.equals("Delete Project"))
                {
                    showDeleteProjectDialog(position);
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Shos the dialog for project delete
     *
     * @param position
     */
    private void showDeleteProjectDialog(final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectSearchResultsActivity.this);
        builder.setTitle(projectsList.get(position).getTitle());
        builder.setMessage(R.string.dialog_delete_project_request)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //databaseHelper.deleteProjectFromDatabase(projectsList.get(position).getProjectId());
                        //Only Set the deletion Flag to the project
                        databaseHelper.setDeleteFlagForProject(projectsList.get(position).getProjectId());
                        loadProjects();
                        projectsAdapter.notifyDataSetChanged();
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

    private void onClickProject(int position)
    {
        if (projectsList.get(position).getEstimationMethod().equals(getString(R.string.estimation_technique_function_point)))
        {
            Intent intent = new Intent(getApplicationContext(), FunctionPointProjectActivtiy.class);
            intent.putExtra(getString(R.string.SELECTEDPROJECTID), projectsList.get(position).getProjectId());
            startActivityForResult(intent, Integer.parseInt((getString(R.string.PROJECT_VIEW_CODE))));
        } else if (projectsList.get(position).getEstimationMethod().equals(getString(R.string.estimation_technique_cocomo)))
        {
            Toast.makeText(this, "This Estimation Method is not supported at the moment", Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(getApplicationContext(), FunctionPointProjectActivtiy.class);
            intent.putExtra(getString(R.string.SELECTEDPROJECTID), projectsList.get(position).getProjectId());
            startActivityForResult(intent, Integer.parseInt((getString(R.string.PROJECT_VIEW_CODE))));*/
        } else if (projectsList.get(position).getEstimationMethod().equals(getString(R.string.estimation_technique_cocomo_2)))
        {
            Toast.makeText(this, "This Estimation Method is not supported at the moment", Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(getApplicationContext(), FunctionPointProjectActivtiy.class);
            intent.putExtra(getString(R.string.SELECTEDPROJECTID), projectsList.get(position).getProjectId());
            startActivityForResult(intent, Integer.parseInt((getString(R.string.PROJECT_VIEW_CODE))));*/
        } else
        {
            Toast.makeText(this, "This Estimation Method is not supported at the moment", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProjects()
    {
        projectsList.clear();
        projectsList.addAll(databaseHelper.searchAllProjectsWithName(this, searchQuery));
        if (projectsList.isEmpty() || projectsList.size() == 0)
        {
            Project p = new Project(this, getString(R.string.error), getString(R.string.no_search_results), "");
            projectsList.add(p);
            projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                }
            });
            projectsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                {
                    return true;
                }
            });
        } else
        {
            projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    onClickProject(position);
                }
            });
            projectsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                {
                    onLongClickProject(position);
                    return true;
                }
            });
        }
    }

}
