package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

/**
 * Created by Oliver Fries on 25.10.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.List;

public class ProjectListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Project> projectsList;
    
    public ProjectListAdapter(Activity activity, List<Project> projectsList) {
        this.activity = activity;
        this.projectsList = projectsList;
    }

    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

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

        creationDate.setText("Created: " + project.getCreationDate().toString());

        return convertView;
    }

}