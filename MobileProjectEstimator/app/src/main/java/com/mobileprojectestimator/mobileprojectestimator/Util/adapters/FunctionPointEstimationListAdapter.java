package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.FunctionPointEstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.FunctionPointEstimationValueActivity;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 * <p/>
 * Adapter for the FUnction Point Estimation
 */
public class FunctionPointEstimationListAdapter extends BaseAdapter
{

    private FunctionPointMethodFragment fragment;
    /**
     * array list for all items to estimate
     */
    private ArrayList<FunctionPointEstimationItem> fpEstimationItems;
    private LayoutInflater inflater;
    /**
     * the project object
     */
    private Project project;
    /**
     * Name of the item
     */
    private String itemName;

    /**
     * local fragment manager
     */
    FragmentManager fm;

    /**
     * Standard constructor
     *
     * @param projectCreationOverviewFragment
     * @param fpEstimationItems
     * @param fm
     * @param project
     */
    public FunctionPointEstimationListAdapter(FunctionPointMethodFragment projectCreationOverviewFragment, ArrayList<FunctionPointEstimationItem> fpEstimationItems, FragmentManager fm, Project project)
    {
        this.fragment = projectCreationOverviewFragment;
        this.fpEstimationItems = fpEstimationItems;
        this.fm = fm;
        this.project = project;
    }

    @Override
    public int getCount()
    {
        return fpEstimationItems.size();
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
            convertView = inflater.inflate(R.layout.function_point_estimation_list_item, null);

        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvValue);
        TextView itemNameTv = (TextView) convertView.findViewById(R.id.tvEstimationCategory);

        // title
        itemName = fpEstimationItems.get(position).getName();
        itemValueTv.setText(String.format("%d", fpEstimationItems.get(position).getValue()));
        itemNameTv.setText(itemName);

        //TODO: Button wird nicht für jedes Element einzeln gesetzt.
        ImageView editButton = (ImageView) convertView.findViewById(R.id.ivEditButton);//TODO: Zugriff immer auf alle EditButtons. Es muss auf jedes Listelement zugegriffen werden.
        //ImageView editButton = (ImageView) convertView.findViewById(R.id.ivEditButton);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), FunctionPointEstimationValueActivity.class);
                intent.putExtra("TITLE", itemName);

                intent.putExtra("NEWPROJECT", project.toHashMap());
                fragment.startActivityForResult(intent, 1);
            }
        });

        convertView.setTag(123);

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
        if (position % 2 != 1)
        {
            convertView.setBackgroundResource(R.color.standardRowEven);// this set background color
        }
    }
}
