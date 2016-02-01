package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.RelatedProject;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.ProjectRelationSolver;

import java.util.ArrayList;

public class FindRelatedProjectsActivity extends DatabaseActivity
{

    private int projectId;
    private Project selectedProject;

    private ImageView ivProjectImage;
    private TextView tvProjectTitle;
    private TextView tvEstimationMethod;
    private ListView lvRelatedProjects;
    private RelatedProjectsAdapter dataAdapter;
    private ArrayList<RelatedProject> projectsList;
    private double percentageBorder;
    private boolean isShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_related_projects);
        percentageBorder = 50.0;
        isShowAll = false;
        initDatabase();

        Intent intent = getIntent();
        projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), 0);

        selectedProject = databaseHelper.loadProjectById(this, String.valueOf(projectId));

        ivProjectImage = (ImageView) findViewById(R.id.ivProjectImage);
        tvProjectTitle = (TextView) findViewById(R.id.tvProjectTitle);
        tvEstimationMethod = (TextView) findViewById(R.id.tvEstimationMethod);
        lvRelatedProjects = (ListView) findViewById(R.id.lvEstimationItems);

        ivProjectImage.setImageBitmap(selectedProject.getImage());
        tvProjectTitle.setText(selectedProject.getTitle());
        tvEstimationMethod.setText(selectedProject.getEstimationMethod());

        tvProjectTitle.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showValueToast(tvProjectTitle.getText().toString());
                return true;
            }
        });

        lvRelatedProjects.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                onClickProject(position);
            }
        });
        loadRelatedProjects();
    }

    private void showValueToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    private void onClickProject(final int position)
    {
        //RelatedProject p = projectsList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> infItems = new ArrayList<>();
        infItems.add("Project Informations");
        infItems.add("View Estimation");
        final CharSequence[] items = infItems.toArray(new String[infItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                String optionItem = items[item].toString();
                if (optionItem.equals("Project Informations"))
                {
                    Intent i = new Intent(FindRelatedProjectsActivity.this, ProjectPropertiesActivity.class);
                    i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), projectsList.get(position).getProjectId());
                    startActivityForResult(i, Integer.parseInt((getString(R.string.CREATE_NEW_PROJECT_REQUEST_CODE))));
                } else if (optionItem.equals("View Estimation"))
                {
                    Project p = projectsList.get(position);
                    if (p.getEstimationMethod().equals(getString(R.string.estimation_method_function_point)))
                    {
                        Intent i = new Intent(FindRelatedProjectsActivity.this, EstimationViewActivity.class);
                        i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), p.getProjectId());
                        i.putExtra(getString(R.string.ACTIVITY_EXTRA_SELECTED_PROJECTID), selectedProject.getProjectId());
                        startActivityForResult(i, Integer.parseInt((getString(R.string.ACTIVITY_ESTIMATION_VIEW_REQUEST_CODE))));
                    } else
                    {
                        Toast.makeText(FindRelatedProjectsActivity.this, "This Estimation Method is currently not supported", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadRelatedProjects()
    {
        ProjectRelationSolver solver = new ProjectRelationSolver(this, selectedProject, databaseHelper.getAllProjects(this));
        projectsList = solver.getRelatedProject(percentageBorder);

        if (projectsList.isEmpty() || projectsList.size() == 0)
        {
            RelatedProject p = new RelatedProject(this);
            p.setTitle("No Projects found");
            p.setProjectId(-1);
            projectsList.add(p);
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new RelatedProjectsAdapter(this,
                R.layout.deleted_projects_listview_items, projectsList);
        dataAdapter.notifyDataSetChanged();
        lvRelatedProjects.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        finishView();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_show_all_relations:
                if(isShowAll){
                    item.setTitle(getString(R.string.action_show_all_relations));
                    isShowAll=false;
                    percentageBorder=50.0;
                }else {
                    item.setTitle(getString(R.string.action_show_only_relevant_relations));
                    isShowAll=true;
                    percentageBorder=0.0;
                }
                loadRelatedProjects();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_find_related, menu);
        return true;
    }

    public void finishView()
    {
        Intent i = new Intent();
        i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), projectId);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private class RelatedProjectsAdapter extends ArrayAdapter<RelatedProject>
    {

        private ArrayList<RelatedProject> projectsList;

        public RelatedProjectsAdapter(Context context, int textViewResourceId,
                                      ArrayList<RelatedProject> projectsList)
        {
            super(context, textViewResourceId, projectsList);
            this.projectsList = new ArrayList<RelatedProject>();
            this.projectsList.addAll(projectsList);
        }

        private class ViewHolder
        {
            ImageView ivProjectImage;
            TextView tvProjectName;
            TextView tvRelation;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            RelatedProject p = projectsList.get(position);

            if (convertView == null)
            {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.related_projects_listview_items, null);

                holder = new ViewHolder();
                holder.ivProjectImage = (ImageView) convertView.findViewById(R.id.ivProjectImage);
                holder.tvProjectName = (TextView) convertView.findViewById(R.id.tvProjectName);
                holder.tvRelation = (TextView) convertView.findViewById(R.id.tvRelation);

                convertView.setTag(holder);

            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvProjectName.setText(p.getTitle());
            holder.ivProjectImage.setImageBitmap(p.getImage());
            holder.tvRelation.setText((String.format("%s%%", p.getRelatedPercentage())));

            return convertView;

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Integer.parseInt((getString(R.string.ACTIVITY_ESTIMATION_VIEW_REQUEST_CODE))))
        {
            if (resultCode == RESULT_OK)
            {
                //TODO: Check if result is null
                Intent intent = getIntent();
                projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), 0);
                loadRelatedProjects();
            }
        }
    }
}
