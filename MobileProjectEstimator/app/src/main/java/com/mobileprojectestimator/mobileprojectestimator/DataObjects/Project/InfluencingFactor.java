package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import android.content.Context;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver Fries on 25.10.2015.
 * <p/>
 * Base Class for all influence factor sets
 */
public class InfluencingFactor
{
    public static final int FUNCTIONPOINTFACTORS = 1;
    public static final int COCOMOFACTORS = 2;
    public static final int COCOMO2FACTORS = 3;
    private final int influencingFactorId;
    private final Context context;
    private String name;
    private ArrayList<InfluenceFactorItem> influenceFactorItems;
    private int sumOfInfluences;
    private int influenceItemSum;

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
     * Changes the selected value of an influence factor item.
     * Returns false if the item does not exist.
     *
     * @param itemName
     * @param chosenVal
     * @return
     */
    public boolean changeFactorItemValue(String itemName, int chosenVal)
    {
        int position = findItemPosition(itemName);
        if (position != -1)
        {
            influenceFactorItems.get(position).setChosenValue(chosenVal);
            return true;
        } else
        {
            return false;
        }
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
     * Set the value of a subitem from a specific item
     *
     * @param parentItem
     * @param subItemName
     * @param value
     * @return
     */
    public boolean setSubItemValue(String parentItem, String subItemName, int value)
    {
        int position = findItemPosition(parentItem);
        if (position != -1)
        {
            ArrayList<InfluenceFactorItem> subitems = influenceFactorItems.get(position).getSubInfluenceFactorItemsList();

            subitems.get(findSubItemPosition(subItemName, subitems)).setChosenValue(value);
            return true;
        } else
        {
            return false;
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
     * Creates HashMap out of the actual influence factor set
     *
     * @return
     */
    public Map<? extends String, ? extends String> toHashMap()
    {
        HashMap<String, String> infFactorItemsHashMap = new HashMap<>();

        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subItem : item.getSubInfluenceFactorItemsList())
                {
                    infFactorItemsHashMap.put(subItem.getName(), String.valueOf(subItem.getChosenValue()));
                }
            } else
            {
                infFactorItemsHashMap.put(item.getName(), String.valueOf(item.getChosenValue()));
            }
        }

        return infFactorItemsHashMap;
    }

    /**
     * Creates new FactorItem Arraylist and sets the values from the hashmap
     *
     * @param map
     * @param estimationMethod
     * @return
     */
    public void setValuesFromHashMap(HashMap<String, String> map, int estimationMethod)
    {
        influenceFactorItems = new ArrayList<>();
        switch (estimationMethod)
        {
            case FUNCTIONPOINTFACTORS:
                setFunctionPointInfluenceFactorItems();
                setFunctionPointInfluenceFactorValues(map);
                break;
            default:
                break;
        }
    }

    private boolean setFunctionPointInfluenceFactorValues(HashMap<String, String> map)
    {
        try
        {
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_integration))).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_integration))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_local_data))).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_local_data))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_transaction_rate))).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_transaction_rate))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_reusability))).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_reusability))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_stock_conversion))).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_stock_conversion))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_adaptability))).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_adaptability))));

            //Set SubItems
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_processing_logic))).getSubInfluenceFactorItemsList().get(findSubItemPosition(context.getString(R.string.function_point_influence_factor_item_arithmetic_operation), influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_arithmetic_operation))).getSubInfluenceFactorItemsList())).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_adaptability))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_processing_logic))).getSubInfluenceFactorItemsList().get(findSubItemPosition(context.getString(R.string.function_point_influence_factor_item_exception_regulation), influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_exception_regulation))).getSubInfluenceFactorItemsList())).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_exception_regulation))));
            influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_processing_logic))).getSubInfluenceFactorItemsList().get(findSubItemPosition(context.getString(R.string.function_point_influence_factor_item_logic), influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_logic))).getSubInfluenceFactorItemsList())).setChosenValue(Integer.parseInt(map.get(context.getString(R.string.function_point_influence_factor_item_logic))));

        } catch (Exception e)
        {
            return false;
        }
        return true;
    }

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

    private int getFunctionPointInfluenceSum() {
        influenceItemSum = 0;

        influenceItemSum += influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_integration))).getChosenValue();
        influenceItemSum += influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_local_data))).getChosenValue();
        influenceItemSum += influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_transaction_rate))).getChosenValue();
        influenceItemSum += influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_reusability))).getChosenValue();
        influenceItemSum += influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_stock_conversion))).getChosenValue();
        influenceItemSum += influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_adaptability))).getChosenValue();

        ArrayList<InfluenceFactorItem> subitems = influenceFactorItems.get(findItemPosition(context.getString(R.string.function_point_influence_factor_item_processing_logic))).getSubInfluenceFactorItemsList();
        influenceItemSum +=  subitems.get(0).getChosenValue();
        influenceItemSum +=  subitems.get(1).getChosenValue();
        influenceItemSum +=  subitems.get(2).getChosenValue();
        influenceItemSum +=  subitems.get(3).getChosenValue();
        return influenceItemSum;
    }

    public double getFactorInfluenceRating() {
        double influenceSum = (double) getFunctionPointInfluenceSum();

        double erg = influenceSum / 100.0;
        erg += 0.7;

        return erg;
    }
}
