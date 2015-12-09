package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

/**
 * Created by Oliver Fries on 25.10.2015, 17:46.
 * Project: MobileProjectEstimator
 * Adapter for the projects
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter Class for the projects that are displayed in the main Overview in the app.
 * It Contains a Project List with the Project Object of each contained project.
 */
public class ProjectListAdapter extends BaseAdapter
{
    /**
     * Activtiy Object that the inflater get the adapter information
     */
    private Activity activity;
    /**
     * The layout that the adapter can access  the layout
     */
    private LayoutInflater inflater;

    public ArrayList<String> getProjectNamesList()
    {
        ArrayList<String> names = new ArrayList<String>();
        for(Project p:this.projectsList){
            names.add(p.getTitle());
        }
        return names;
    }

    /**
     * Project List with all projects that will be displayed
     */
    private List<Project> projectsList;

    /**
     * The Standard constructor for this adapter
     *
     * @param activity
     * @param projectsList
     */
    public ProjectListAdapter(Activity activity, List<Project> projectsList)
    {
        this.activity = activity;
        this.projectsList = projectsList;
    }

    @Override
    /**
     * Returns the number of projects
     */
    public int getCount()
    {
        return projectsList.size();
    }

    @Override
    /**
     * Returns a specific project at the position in the array
     */
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    /**
     * Returns the ID of an project at a position
     */
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.projectTitle);
        ImageView projectImage = (ImageView) convertView.findViewById(R.id.projectIcon);
        TextView estimationType = (TextView) convertView.findViewById(R.id.estimationType);
        TextView creationDate = (TextView) convertView.findViewById(R.id.creationDate);

        // getting movie data for the row
        Project project = projectsList.get(position);

        // project image
        projectImage.setImageBitmap(project.getImage());

        // title
        title.setText(project.getTitle());

        estimationType.setText(project.getEstimationMethod());

        creationDate.setText(String.format("%s%s", activity.getString(R.string.project_created_text_part), project.getCreationDate()));

        return convertView;
    }

}