package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectCreationItem;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectCreationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 31.10.2015.
 */
public class ProjectCreationListAdapter extends BaseAdapter {

    private ProjectCreationOverviewFragment fragment;
    private ArrayList<ProjectCreationItem> creationItems;
    private LayoutInflater inflater;

    public ProjectCreationListAdapter(ProjectCreationOverviewFragment projectCreationOverviewFragment, ArrayList<ProjectCreationItem> creationItems) {
        this.fragment = projectCreationOverviewFragment;
        this.creationItems = creationItems;
    }

    @Override
    public int getCount() {
        return creationItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getLayoutInflater();
        if (convertView == null)
            convertView = inflater.inflate(R.layout.project_list_creation_item, null);

        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvItemValue);
        TextView itemDescriptionTv = (TextView) convertView.findViewById(R.id.tvItemDescription);

        // title
        itemValueTv.setText(creationItems.get(position).getValue());
        itemDescriptionTv.setText(creationItems.get(position).getName());


        if (position % 2 == 1) {
            //convertView.setBackgroundResource(R.color.standardRowOdd);// this set background color
        } else {
            convertView.setBackgroundResource(R.color.standardRowEven);// this set background color
        }
        return convertView;
    }
}
