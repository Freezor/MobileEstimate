package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import android.content.Context;
import android.util.Log;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Oliver Fries on 25.10.2015.
 * <p/>
 * Base Class for all influence factor sets
 */
@SuppressWarnings("JavaDoc")
public class InfluencingFactor
{
    /**
     * The ID for Function Point
     */
    public static final int FUNCTIONPOINTFACTORS = 101;
    /**
     * The ID for COCOMO
     */
    public static final int COCOMOFACTORS = 102;
    /**
     * The ID for COCOMO 2
     */
    public static final int COCOMO2FACTORS = 103;
    private final int influencingFactorId;
    private final Context context;
    private int dbId;
    private String name;

    public void setInfluenceFactorItems(ArrayList<InfluenceFactorItem> influenceFactorItems) {
        this.influenceFactorItems = influenceFactorItems;
    }

    private ArrayList<InfluenceFactorItem> influenceFactorItems;
    private int sumOfInfluences;

    /**
     *
     * @param current
     * @param influencingFactorId
     */
    public InfluencingFactor(Context current, int influencingFactorId)
    {
        this.context = current;
        this.influencingFactorId = influencingFactorId;
        influenceFactorItems = new ArrayList<>();
        loadInfluencingFactorItems();
    }

    /**
     * loads all available factor items for the set estimation method
     */
    private void loadInfluencingFactorItems()
    {
        switch (influencingFactorId)
        {
            case FUNCTIONPOINTFACTORS:
                setFunctionPointInfluenceFactorItems();
                break;
            case COCOMOFACTORS:
                break;
            case COCOMO2FACTORS:
                break;
            default:
                break;
        }
    }


    public String getInfluenceFactorSetName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get the complete factor item list
     *
     * @return
     */
    public ArrayList<InfluenceFactorItem> getInfluenceFactorItems()
    {
        return influenceFactorItems;
    }

    /**
     * Adds a new Factor item to the list
     *
     * @param item
     */
    public void addFactorItem(InfluenceFactorItem item)
    {
        influenceFactorItems.add(item);
    }

    /**
     * Returns the actual value of this item
     *
     * @param itemName
     * @return
     */
    public int getFactorItemValue(String itemName)
    {
        int position = findItemPosition(itemName);
        if (position != -1)
        {
            return influenceFactorItems.get(position).getChosenValue();
        } else
        {
            return -1;
        }
    }

    /**
     * Returns the Chosen Value of a subitem
     *
     * @param parentItem
     * @param subItemName
     * @return
     */
    public int getSubItemValue(String parentItem, String subItemName)
    {
        int position = findItemPosition(parentItem);
        if (position != -1)
        {
            ArrayList<InfluenceFactorItem> subitems = influenceFactorItems.get(position).getSubInfluenceFactorItemsList();

            return subitems.get(findSubItemPosition(subItemName, subitems)).getChosenValue();
        } else
        {
            return -1;
        }
    }

    /**
     * Returns the Position of the subItem
     *
     * @param subItemName
     * @param subitems
     * @return
     */
    private int findSubItemPosition(String subItemName, ArrayList<InfluenceFactorItem> subitems)
    {
        for (InfluenceFactorItem item : subitems)
        {
            if (item.getName().equals(subItemName))
            {
                return item.getSubInfluenceFactorItemsList().indexOf(item);
            }
        }
        return -1;
    }

    /**
     * returns the position of an item in the influenceFactorItems ArrayList.
     * Result is -1 if item was not found
     *
     * @param itemName
     * @return
     */
    private int findItemPosition(String itemName)
    {
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.getName().equals(itemName))
            {
                return influenceFactorItems.indexOf(item);
            }
        }
        return -1;
    }

    /**
     * Each Influence Factor class needs to implement this method, that all factors are set.
     * This Method should be called in the constructor.
     */
    public void setFunctionPointInfluenceFactorItems()
    {
        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_integration), 5));
        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_local_data), 5));
        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_transaction_rate), 5));

        ArrayList<InfluenceFactorItem> subItems = new ArrayList<>();
        subItems.add(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_arithmetic_operation), 10));
        subItems.add(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_control_procedure), 5));
        subItems.add(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_exception_regulation), 10));
        subItems.add(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_logic), 5));
        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_processing_logic), 0, 0, true, subItems));

        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_reusability), 5));
        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_stock_conversion), 5));
        this.addFactorItem(new InfluenceFactorItem(context.getString(R.string.function_point_influence_factor_item_adaptability), 5));
    }

    /**
     * Set the chosen values for an influence factor
     * Needed for initialisation from the database
     * @param values
     */
    public void setFunctionPointValuesFromArrayList(ArrayList<Integer> values)
    {
        influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_integration))).setChosenValue(values.get(0));
        influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_local_data))).setChosenValue(values.get(1));
        influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_transaction_rate))).setChosenValue(values.get(2));

        int processingLogicPosition = findItemPosition(context.getString(R.string.function_point_influence_factor_item_processing_logic));
        //Set SubItems
        influenceFactorItems.get(processingLogicPosition).getSubInfluenceFactorItemsList().get(0).setChosenValue(values.get(3));
        influenceFactorItems.get(processingLogicPosition).getSubInfluenceFactorItemsList().get(1).setChosenValue(values.get(4));
        influenceFactorItems.get(processingLogicPosition).getSubInfluenceFactorItemsList().get(2).setChosenValue(values.get(5));
        influenceFactorItems.get(processingLogicPosition).getSubInfluenceFactorItemsList().get(3).setChosenValue(values.get(6));

        influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_reusability))).setChosenValue(values.get(7));
        influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_stock_conversion))).setChosenValue(values.get(8));
        influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_adaptability))).setChosenValue(values.get(9));

    }

    /**
     * Returns the sum of all influences regardless of the estimation Method
     * @return
     */
    public int getSumOfInfluences()
    {
        switch (influencingFactorId)
        {
            case FUNCTIONPOINTFACTORS:
                sumOfInfluences = getFunctionPointInfluenceSum();
                break;
            case COCOMOFACTORS:
                break;
            case COCOMO2FACTORS:
                break;
            default:
                break;
        }
        return sumOfInfluences;
    }

    /**
     * Returns the evaluated sum of all influence factor Items
     * Use only in Function Point Method Influence Factor
     * @return
     */
    private int getFunctionPointInfluenceSum()
    {
        int influenceItemSum = 0;

        for(InfluenceFactorItem item : influenceFactorItems){
            if(item.hasSubItems()){
                for (InfluenceFactorItem subitem: item.getSubInfluenceFactorItemsList()){
                    influenceItemSum += subitem.getChosenValue();
                }
            } else {
                influenceItemSum += item.getChosenValue();
            }
        }
        Log.d("Info","Sum of Influence = "+ influenceItemSum);
        return influenceItemSum;
    }

    public double getFactorInfluenceRating()
    {
        double influenceSum = (double) getFunctionPointInfluenceSum();

        double erg = influenceSum / 100.0;
        erg += 0.7;

        return roundDoubleTwoDecimals(erg);
    }

    /**
     * Round a double value to two  decimal numbers
     * <p/>
     * e.g. 3.34
     *
     * @param d
     * @return
     */
    public double roundDoubleTwoDecimals(double d)
    {
        //DecimalFormat twoDForm = new DecimalFormat("#.##");
        //return Double.valueOf(twoDForm.format(d));
        //Changed to new rounding method because of some Exception java.lang.NumberFormatException: Invalid double: "0,78"
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Search all items and subitems for the itemName and set the chosen value
     * @param value
     * @param itemName
     */
    public void setChosenValueOfItem(int value, String itemName)
    {
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitem : item.getSubInfluenceFactorItemsList())
                {
                    if (subitem.getName().equals(itemName))
                    {
                        subitem.setChosenValue(value);
                        break;
                    }
                }
            } else
            {
                if (item.getName().equals(itemName))
                {
                    item.setChosenValue(value);
                    break;
                }
            }
        }
    }

    public int getDbId()
    {
        return dbId;
    }

    public void setDbId(int dbId)
    {
        this.dbId = dbId;
    }
}
