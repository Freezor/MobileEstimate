package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.Activities.InfluenceFactorsActivity;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 10.12.2015, 16:59.
 * Project: MobileProjectEstimator
 */
public class InfluenceListAdapter extends BaseAdapter
{
    private InfluenceFactorsActivity influenceFactorsActivity;
    private ArrayList<InfluenceFactorItem> influenceFactorItems;
    private LayoutInflater inflater;

    public InfluenceListAdapter(InfluenceFactorsActivity influenceFactorsActivity, ArrayList<InfluenceFactorItem> influenceFactorItems)
    {
        this.influenceFactorsActivity = influenceFactorsActivity;
        this.influenceFactorItems = influenceFactorItems;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount()
    {
        return influenceFactorItems.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position)
    {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (inflater == null)
            inflater = influenceFactorsActivity.getLayoutInflater();
        if (convertView == null)
            convertView = inflater.inflate(R.layout.function_point_influence_factor_list_item, null);

        TextView itemNameTv = (TextView) convertView.findViewById(R.id.tvInfluenceName);
        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvInfluenceValue);

        if (influenceFactorItems.get(position).getChosenValue() >= 0)
        {
            itemValueTv.setText(String.format("%d", influenceFactorItems.get(position).getChosenValue()));
        } else
        {
            itemValueTv.setText(R.string.factor_value_standard_value);
        }
        itemNameTv.setText(influenceFactorItems.get(position).getName());


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
