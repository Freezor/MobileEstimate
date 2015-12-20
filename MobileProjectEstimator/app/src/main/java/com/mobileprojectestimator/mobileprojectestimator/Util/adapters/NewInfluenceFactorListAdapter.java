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
import java.util.HashMap;

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
    private ArrayList<String> factorNameArrayList;
    private HashMap<String, String> textValues = new HashMap<String, String>();

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
        boolean convertViewWasNull = false;
        if (inflater == null)
            inflater = influenceFactorsActivity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.function_point_influence_factorset_list_item, null);
            convertViewWasNull = true;
        }
        itemNameTv = (TextView) convertView.findViewById(R.id.tvInfluenceName);
        itemValueEt = (EditText) convertView.findViewById(R.id.tvInfluenceValue);

        Log.d("INFO", "Set Text for item value: name=  " + factorNameArrayList.get(position) +" value = "+loadInfluenceFactorChosenValue(factorNameArrayList.get(position)));
        itemValueEt.setText(String.format("%d", loadInfluenceFactorChosenValue(factorNameArrayList.get(position))));
        itemNameTv.setText(factorNameArrayList.get(position));

        if(convertViewWasNull){
            itemValueEt.addTextChangedListener(new GenericTextWatcher(itemValueEt));
            Log.d("INFO", "Create New Text Change Listener for: " + factorNameArrayList.get(position));
        }
        Log.d("INFO","ITEM VALUE TAG: " + factorNameArrayList.get(position));
        itemValueEt.setTag(factorNameArrayList.get(position));

        setListViewBackgroundColor(position, convertView);
        return convertView;
    }

    private void updateItemChosenValue(String itemName, int val) {
        Log.d("INFO","update chosen value for: " + itemName + "with: " + val);
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitem : item.getSubInfluenceFactorItemsList())
                {
                    if (subitem.getName().equals(itemName))
                    {
                        Log.d("INFO","Found in subitem " + subitem.getName() + "; pre value = " + subitem.getChosenValue());
                        subitem.setChosenValue(val);
                    }
                }
            } else
            {
                if (item.getName().equals(itemName))
                {
                    Log.d("INFO","Found in subitem " + item.getName() + "; pre value = " + item.getChosenValue());
                    item.setChosenValue(val);
                }
            }
        }
    }

    /**
     *
     * @param value
     * @param itemName
     * @return
     */
    public String checkInputSize(String value, String itemName)
    {
        if(value == null || value.isEmpty() || value.equals("")){
            value = "0";
        }
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
        Log.d("INFO","Load Influence Factor Value for " + factorName);
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitems : item.getSubInfluenceFactorItemsList())
                {
                    if (subitems.getName().equals(factorName))
                    {
                        Log.d("INFO","Found in subitem " + subitems.getName()+ " with value = "+subitems.getChosenValue());
                        value = subitems.getChosenValue();
                        break;
                    }
                }
            } else
            {
                if (item.getName().equals(factorName))
                {
                    Log.d("INFO","Found in item " + item.getName()+ " with value = "+item.getChosenValue());
                    value = item.getChosenValue();
                    break;
                }
            }
        }
        Log.d("INFO","Return Value for " + factorName+ "value = "+value);
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

    public ArrayList<InfluenceFactorItem> getInfluenceFactorItems()
    {
        for(String influenceName : factorNameArrayList){
            String value = textValues.get(influenceName);
            value = checkInputSize(value,influenceName);
            updateItemChosenValue(influenceName, Integer.parseInt(value));
        }
        return influenceFactorItems;
    }

    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            //save the value for the given tag :
            Log.d("INFO", "Update Text values: Tag= " + view.getTag().toString() + " Value = " + editable.toString());
            String tag = view.getTag().toString();
            String value = editable.toString();
            NewInfluenceFactorListAdapter.this.textValues.put(tag, value);
            int checkedVal = Integer.parseInt(checkInputSize(value, tag));
            NewInfluenceFactorListAdapter.this.updateItemChosenValue(tag, checkedVal);
        }
    }
}
