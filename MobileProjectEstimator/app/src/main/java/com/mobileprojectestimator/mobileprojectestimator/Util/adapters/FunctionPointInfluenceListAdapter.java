package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject.FunctionPointInfluenceFactorFragment;
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
     * The Project object, where we get the information from
     */
    final Project project;
    /**
     * The fragment, where the adapter is used
     */
    private final FunctionPointInfluenceFactorFragment fragment;
    /**
     * Array List with all influence factor items
     */
    private final ArrayList<InfluenceFactorItem> fpInfluenceItems;
    /**
     * The Inflater object
     */
    private LayoutInflater inflater;

    /**
     * Standard constructor
     *
     * @param projectInfluenceFactorFragment
     * @param fpInfluenceItems
     * @param project
     */
    public FunctionPointInfluenceListAdapter(FunctionPointInfluenceFactorFragment projectInfluenceFactorFragment, ArrayList<InfluenceFactorItem> fpInfluenceItems, Project project)
    {
        this.fragment = projectInfluenceFactorFragment;
        this.fpInfluenceItems = fpInfluenceItems;
        this.project = project;
    }

    /**
     * Updates all values from the Project values
     *
     * @param context
     */
    public void updateChosenValues(Context context)
    {
        try
        {
            for (InfluenceFactorItem item : fpInfluenceItems)
            {
                if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_integration)))
                {
                    item.setChosenValue(project.getInfluencingFactor().getFactorItemValue(context.getString(R.string.function_point_influence_factor_item_integration)));
                } else if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_local_data)))
                {
                    item.setChosenValue(project.getInfluencingFactor().getFactorItemValue(context.getString(R.string.function_point_influence_factor_item_local_data)));
                } else if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_transaction_rate)))
                {
                    item.setChosenValue(project.getInfluencingFactor().getFactorItemValue(context.getString(R.string.function_point_influence_factor_item_transaction_rate)));
                } else if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_processing_logic)))
                {
                    try
                    {
                        String parentName = context.getString(R.string.function_point_influence_factor_item_processing_logic);
                        String arithmetic = context.getString(R.string.function_point_influence_factor_item_arithmetic_operation);
                        String controlProcedure = context.getString(R.string.function_point_influence_factor_item_control_procedure);
                        String exceptionRegulation = context.getString(R.string.function_point_influence_factor_item_exception_regulation);
                        String logic = context.getString(R.string.function_point_influence_factor_item_logic);

                        boolean arithmeticBool = item.setSubItemChosenValue(project.getInfluencingFactor().getSubItemValue(parentName, arithmetic), arithmetic);
                        boolean controlBool = item.setSubItemChosenValue(project.getInfluencingFactor().getSubItemValue(parentName, controlProcedure), controlProcedure);
                        boolean exceptionBool = item.setSubItemChosenValue(project.getInfluencingFactor().getSubItemValue(parentName, exceptionRegulation), exceptionRegulation);
                        boolean logicBool = item.setSubItemChosenValue(project.getInfluencingFactor().getSubItemValue(parentName, logic), logic);

                        if (!arithmeticBool || !controlBool || !exceptionBool || !logicBool)
                        {
                            Log.d("ERROR", "FunctionPointInfluenceListAdapter.updateChosenValues(): Arithmetic = " + arithmeticBool);
                            Log.d("ERROR", "FunctionPointInfluenceListAdapter.updateChosenValues(): Control = " + controlBool);
                            Log.d("ERROR", "FunctionPointInfluenceListAdapter.updateChosenValues(): Exception = " + exceptionBool);
                            Log.d("ERROR", "FunctionPointInfluenceListAdapter.updateChosenValues(): Logic = " + logicBool);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                } else if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_reusability)))
                {
                    item.setChosenValue(project.getInfluencingFactor().getFactorItemValue(context.getString(R.string.function_point_influence_factor_item_reusability)));
                } else if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_stock_conversion)))
                {
                    item.setChosenValue(project.getInfluencingFactor().getFactorItemValue(context.getString(R.string.function_point_influence_factor_item_stock_conversion)));
                } else if (item.getName().equals(context.getString(R.string.function_point_influence_factor_item_adaptability)))
                {
                    item.setChosenValue(project.getInfluencingFactor().getFactorItemValue(context.getString(R.string.function_point_influence_factor_item_adaptability)));
                } else
                {
                    Log.d("ERROR", "FunctionPointInfluenceListAdapter.updateChosenValues(): THIS SHOULD NOT HAPPEN");
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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

