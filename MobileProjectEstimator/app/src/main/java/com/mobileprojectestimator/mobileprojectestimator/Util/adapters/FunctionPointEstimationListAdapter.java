package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.RowViewHolder;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.Activities.FunctionPointEstimationValueActivity;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 02.11.2015.
 * <p/>
 * Adapter for the FUnction Point Estimation
 */
public class FunctionPointEstimationListAdapter extends BaseAdapter
{

    /**
     * local fragment manager
     */
    final FragmentManager fm;
    private final FunctionPointMethodFragment fragment;
    /**
     * array list for all items to estimate
     */
    private ArrayList<FunctionPointItem> fpEstimationItems;
    private final HashMap<Integer, RowViewHolder> rowViewHolderHashMap;
    /**
     * the project object
     */
    private Project project;
    private LayoutInflater inflater;

    /**
     * Standard constructor
     *
     * @param projectCreationOverviewFragment
     * @param fpEstimationItems
     * @param fm
     * @param project
     */
    public FunctionPointEstimationListAdapter(FunctionPointMethodFragment projectCreationOverviewFragment, ArrayList<FunctionPointItem> fpEstimationItems, FragmentManager fm, Project project)
    {
        this.fragment = projectCreationOverviewFragment;
        this.fpEstimationItems = fpEstimationItems;
        this.fm = fm;
        this.project = project;
        rowViewHolderHashMap = new HashMap<>();
    }

    public void updateProject(Project p)
    {
        this.project = p;
        this.fpEstimationItems = p.getFunctionPointItems();

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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (inflater == null)
            inflater = fragment.getActivity().getLayoutInflater();
        convertView = inflater.inflate(R.layout.function_point_estimation_list_item, null);

        RowViewHolder holder = new RowViewHolder();

        holder.itemValueTv = (TextView) convertView.findViewById(R.id.tvValue);
        holder.itemNameTv = (TextView) convertView.findViewById(R.id.tvEstimationCategory);

        holder.item = fpEstimationItems.get(position);
        holder.itemValueTv.setText(String.format("%d", fpEstimationItems.get(position).getTotalAmount()));
        holder.itemNameTv.setText(holder.item.getItemName());

        //TODO: Button wird nicht für jedes Element einzeln gesetzt.
        holder.editButton = (ImageView) convertView.findViewById(R.id.ivEditButton);//TODO: Zugriff immer auf alle EditButtons. Es muss auf jedes Listelement zugegriffen werden.
        //ImageView editButton = (ImageView) convertView.findViewById(R.id.ivEditButton);
        holder.editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), FunctionPointEstimationValueActivity.class);
                intent.putExtra("TITLE", rowViewHolderHashMap.get(position).item.getItemName());
                //TODO: Error: Fehler bei Umwandlung durch den zweiten Aufruf der Methode
                //TODO: Richtiger Total Amount wird hier noch mitgeliefert
                intent.putExtra("NEWPROJECT", project.getDetailsId());
                fragment.startActivityForResult(intent, 1);
            }
        });

        convertView.setTag(holder);
        rowViewHolderHashMap.put(position, holder);
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
