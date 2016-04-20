package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.ProjectItemForCreation;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation.ProjectCreationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 31.10.2015.
 * <p/>
 * Adapter to list all information's for creating a project.
 */
public class ProjectCreationListAdapter extends BaseAdapter
{

    /**
     * The fragment ist used for the location, where the items will be displayed. This is needed for accessing the layout information
     */
    private final ProjectCreationOverviewFragment fragment;
    /**
     * Array List with all items that will be shown
     */
    private final ArrayList<ProjectItemForCreation> creationItems;
    /**
     * The inflater for accessing the layout
     */
    private LayoutInflater inflater;

    /**
     * Standard Constructor
     * Beside the Fragment, where the items will be shown it gets the items that will be displayed
     *
     * @param projectCreationOverviewFragment
     * @param creationItems
     */
    public ProjectCreationListAdapter(ProjectCreationOverviewFragment projectCreationOverviewFragment, ArrayList<ProjectItemForCreation> creationItems)
    {
        this.fragment = projectCreationOverviewFragment;
        this.creationItems = creationItems;
    }

    @Override
    public int getCount()
    {
        return creationItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (inflater == null)
            inflater = fragment.getActivity().getLayoutInflater();
        if (convertView == null)
            convertView = inflater.inflate(R.layout.project_list_creation_item, parent,false);

        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvItemValue);
        TextView itemDescriptionTv = (TextView) convertView.findViewById(R.id.tvItemDescription);

        itemValueTv.setText(creationItems.get(position).getValue());
        itemDescriptionTv.setText(creationItems.get(position).getName());


        setListLayout(position, convertView);
        return convertView;
    }

    /**
     * Changes the List layout. Every row with an even number will get a different background color.
     *
     * @param position
     * @param convertView
     */
    private void setListLayout(int position, View convertView)
    {
        if (position % 2 != 1)
        {
            convertView.setBackgroundResource(R.color.standardRowEven);// this set background color
        }
    }
}
