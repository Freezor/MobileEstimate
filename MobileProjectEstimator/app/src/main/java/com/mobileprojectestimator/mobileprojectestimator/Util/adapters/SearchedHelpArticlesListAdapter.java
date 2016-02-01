package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.HelpArticleItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 17.01.2016, 12:29.
 * Project: MobileProjectEstimator
 */
public class SearchedHelpArticlesListAdapter extends BaseAdapter
{
    private final Activity activity;
    private LayoutInflater inflater;
    private ArrayList<HelpArticleItem> helpArticleItems;

    public SearchedHelpArticlesListAdapter(Activity activity)
    {
        this.activity = activity;
        this.helpArticleItems = new ArrayList<HelpArticleItem>();
    }

    public void setHelpItems(ArrayList<HelpArticleItem> items){
        this.helpArticleItems = items;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount()
    {
        return helpArticleItems.size();
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
        return position;
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
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.searched_help_article_item, null);
        }

        TextView helpArticleTitle = (TextView) convertView.findViewById(R.id.tvHelpItemTitle);
        helpArticleTitle.setText(helpArticleItems.get(position).getName());

        TextView helpArticleContent = (TextView) convertView.findViewById(R.id.tvHelpItemContent);
        helpArticleContent.setText(helpArticleItems.get(position).getParagraphs().get(0));

        return convertView;
    }
}
