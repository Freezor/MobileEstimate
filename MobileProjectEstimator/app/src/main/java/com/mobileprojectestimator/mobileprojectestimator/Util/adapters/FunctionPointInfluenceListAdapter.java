package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.FunctionPointInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointInfluenceFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 03.11.2015.
 * <p/>
 * This adapter contains all influence factor items for a function point estimation
 */
public class FunctionPointInfluenceListAdapter extends BaseAdapter
{
    /**
     * The fragment, where the adapter is used
     */
    private FunctionPointInfluenceFactorFragment fragment;
    /**
     * Array List with all influence factor items
     */
    private ArrayList<FunctionPointInfluenceFactorItem> fpInfluenceItems;
    /**
     * The Inflater object
     */
    private LayoutInflater inflater;
    /**
     * The Project object, where we get the information from
     */
    Project project;

    /**
     * Standard constructor
     *
     * @param projectInfluenceFactorFragment
     * @param fpInfluenceItems
     * @param project
     */
    public FunctionPointInfluenceListAdapter(FunctionPointInfluenceFactorFragment projectInfluenceFactorFragment, ArrayList<FunctionPointInfluenceFactorItem> fpInfluenceItems, Project project)
    {
        this.fragment = projectInfluenceFactorFragment;
        this.fpInfluenceItems = fpInfluenceItems;
        this.project = project;
    }

    @Override
    public int getCount()
    {
        return fpInfluenceItems.size();
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
            convertView = inflater.inflate(R.layout.function_point_influence_factor_list_item, null);

        TextView itemNameTv = (TextView) convertView.findViewById(R.id.tvInfluenceName);
        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvInfluenceValue);

        if (fpInfluenceItems.get(position).getChosenValue() >= 0)
        {
            itemValueTv.setText(String.format("%d", fpInfluenceItems.get(position).getChosenValue()));
        } else
        {
            itemValueTv.setText(R.string.factor_value_standard_value);
        }
        itemNameTv.setText(fpInfluenceItems.get(position).getName());


        setListViewBackgroundColor(position, convertView);
        return convertView;
    }

    /**
     * Set the Background for the list view
     *
     * @param position
     * @param convertView
     */
    private void setListViewBackgroundColor(int position, View convertView)
    {
        if (position % 2 == 0)
        {
            convertView.setBackgroundResource(R.color.standardRowEven);
        } else
        {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}

