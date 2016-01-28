package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_related_projects);

        initDatabase();

        Intent intent = getIntent();
        projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), 0);

        selectedProject = databaseHelper.loadProjectById(this, String.valueOf(projectId));

        ivProjectImage = (ImageView) findViewById(R.id.ivProjectImage);
        tvProjectTitle = (TextView) findViewById(R.id.tvProjectTitle);
        tvEstimationMethod = (TextView) findViewById(R.id.tvEstimationMethod);
        lvRelatedProjects = (ListView) findViewById(R.id.lvRelatedProjects);

        ivProjectImage.setImageBitmap(selectedProject.getImage());
        tvProjectTitle.setText(selectedProject.getTitle());
        tvEstimationMethod.setText(selectedProject.getEstimationMethod());

        lvRelatedProjects.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                RelatedProject p = projectsList.get(position);
                Toast.makeText(FindRelatedProjectsActivity.this,"Clicked "+p.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        loadRelatedProjects();
    }

    private void loadRelatedProjects()
    {
        ProjectRelationSolver solver = new ProjectRelationSolver(this,selectedProject, databaseHelper.getAllProjects(this));
        projectsList = solver.getRelatedProject(50.0);

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
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
