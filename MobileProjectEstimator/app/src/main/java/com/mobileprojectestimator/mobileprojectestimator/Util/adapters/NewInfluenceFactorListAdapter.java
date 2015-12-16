package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.Activities.CreateNewInfluenceFactorActivity;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 16.12.2015, 11:58.
 * Project: MobileProjectEstimator
 */
public class NewInfluenceFactorListAdapter extends BaseAdapter
{
    private CreateNewInfluenceFactorActivity influenceFactorsActivity;
    private ArrayList<InfluenceFactorItem> influenceFactorItems;
    private LayoutInflater inflater;
    private TextView itemNameTv;
    private EditText itemValueEt;
    private ImageView editFactorIv;
    private ArrayList<String> factorNameArrayList;
    private String val;

    public NewInfluenceFactorListAdapter(CreateNewInfluenceFactorActivity influenceFactorsActivity, ArrayList<InfluenceFactorItem> influenceFactorItems)
    {
        this.influenceFactorsActivity = influenceFactorsActivity;
        this.influenceFactorItems = influenceFactorItems;
        initFactorArrayList();
    }

    private void initFactorArrayList()
    {
        factorNameArrayList = new ArrayList<>();
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitems : item.getSubInfluenceFactorItemsList())
                {
                    factorNameArrayList.add(subitems.getName());
                }
            } else
            {
                factorNameArrayList.add(item.getName());
            }
        }
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount()
    {
        int sum = 0;
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                sum += item.getSubInfluenceFactorItemsList().size();
            } else
            {
                sum += 1;
            }
        }
        return sum;
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
            convertView = inflater.inflate(R.layout.function_point_influence_factorset_list_item, null);

        itemNameTv = (TextView) convertView.findViewById(R.id.tvInfluenceName);
        itemValueEt = (EditText) convertView.findViewById(R.id.tvInfluenceValue);

        itemValueEt.setText(String.format("%d", loadInfluenceFactorChosenValue(factorNameArrayList.get(position))));
        itemNameTv.setText(factorNameArrayList.get(position));

        itemValueEt.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                //TODO: Abfrage und AKtualisierung der Werte das im Rahmen von Min / Max
                //val = checkInputSize(itemValueEt.getText().toString(), itemNameTv.getText().toString());
                //Log.d("INFO", "Factor: " + itemNameTv.getText().toString() + " Value: " + val);

            }
        });

        setListViewBackgroundColor(position, convertView);
        return convertView;
    }

    /**
     *
     * @param value
     * @param itemName
     * @return
     */
    public String checkInputSize(String value, String itemName)
    {
        int val = Integer.parseInt(value);
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitem : item.getSubInfluenceFactorItemsList())
                {
                    if (subitem.getName().equals(itemName))
                    {
                        if(val >= subitem.getMinValue() && val <= subitem.getMaxValue()){
                            return String.valueOf(val);
                        } else if(val >subitem.getMaxValue()){
                            return String.valueOf(val);
                        } else{
                            return String.valueOf(subitem.getMinValue());
                        }
                    }
                }
            } else
            {
                if (item.getName().equals(itemName))
                {
                    if(val >= item.getMinValue() && val <= item.getMaxValue()){
                        return String.valueOf(val);
                    } else if(val >item.getMaxValue()){
                        return String.valueOf(val);
                    } else{
                        return String.valueOf(item.getMinValue());
                    }
                }
            }
        }
        return String.valueOf(val);
    }

    /**
     * Load the influence Factor items and sets an item for each subitem
     * @param factorName
     * @return
     */
    private int loadInfluenceFactorChosenValue(String factorName)
    {
        int value = 0;
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitems : item.getSubInfluenceFactorItemsList())
                {
                    if (subitems.getName().equals(factorName))
                    {
                        value = subitems.getChosenValue();
                        break;
                    }
                }
            } else
            {
                if (item.getName().equals(factorName))
                {
                    value = item.getChosenValue();
                    break;
                }
            }
        }
        return value;
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

    /**
     * Returns the sum of all influences
     *
     * @return
     */
    public int getSumOfInfluences()
    {
        int sum = 0;
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            sum += item.getChosenValue();
        }
        return sum;
    }
}
