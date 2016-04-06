package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 * <p/>
 * Class for each influence factor item in a function point estimation project
 */
@SuppressWarnings("JavaDoc")
public class InfluenceFactorItem
{
    private boolean hasSubItems;
    private String name;
    private int minValue;
    private int maxValue;
    private ArrayList<InfluenceFactorItem> subInfluenceFactorItemsList;
    private int chosenValue = 0;


    /**
     * Standard constructor
     *
     * @param name
     * @param minValue
     * @param maxValue
     * @param hasSubItems
     */
    public InfluenceFactorItem(String name, int minValue, int maxValue, boolean hasSubItems)
    {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.hasSubItems = hasSubItems;
        if (hasSubItems)
        {
            subInfluenceFactorItemsList = new ArrayList<>();
        }
    }

    /**
     * create item with no sub items
     *
     * @param name
     * @param maxValue
     */
    public InfluenceFactorItem(String name, int maxValue)
    {
        this(name, 0, maxValue, false);
    }

    /**
     * Influence Factor Item with already created list of subitems
     *
     * @param name
     * @param minValue
     * @param maxValue
     * @param hasSubItems
     * @param subInfluenceFactorItemsList
     */
    public InfluenceFactorItem(String name, int minValue, int maxValue, boolean hasSubItems, ArrayList<InfluenceFactorItem> subInfluenceFactorItemsList)
    {
        this(name, minValue, maxValue, hasSubItems);
        if (hasSubItems)
        {
            this.subInfluenceFactorItemsList = subInfluenceFactorItemsList;
        }
    }

    /**
     * Has the Factor Item subitems
     *
     * @return
     */
    public boolean hasSubItems()
    {
        return hasSubItems;
    }

    @SuppressWarnings("unused")
    public void setHasSubItems(boolean hasSubItems)
    {
        this.hasSubItems = hasSubItems;
    }

    /**
     * Get the name of this influence factor item
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMinValue()
    {
        return minValue;
    }

    @SuppressWarnings("unused")
    public void setMinValue(int minValue)
    {
        this.minValue = minValue;
    }

    public int getMaxValue()
    {
        return maxValue;
    }

    @SuppressWarnings("unused")
    public void setMaxValue(int maxValue)
    {
        this.maxValue = maxValue;
    }

    public ArrayList<InfluenceFactorItem> getSubInfluenceFactorItemsList()
    {
        if (hasSubItems)
        {
            return subInfluenceFactorItemsList;
        } else
        {
            return null;
        }

    }

    @SuppressWarnings("unused")
    public void setSubInfluenceFactorItemsList(ArrayList<InfluenceFactorItem> subInfluenceFactorItemsList)
    {
        if (hasSubItems)
        {
            this.subInfluenceFactorItemsList = subInfluenceFactorItemsList;
        }
    }

    public int getChosenValue()
    {
        return chosenValue;
    }

    public void setChosenValue(int chosenValue)
    {
        if (chosenValue >= minValue && chosenValue <= maxValue)
        {
            this.chosenValue = chosenValue;
        } else if (chosenValue < minValue)
        {
            this.chosenValue = minValue;
        } else if (chosenValue > maxValue)
        {
            this.chosenValue = maxValue;
        }

    }

    /**
     * Set the chosen value for the subitem. returns false if item does not exist
     *
     * @param chosenValue
     * @param subItemName
     * @return
     */
    public boolean setSubItemChosenValue(int chosenValue, String subItemName)
    {
        for (InfluenceFactorItem item : subInfluenceFactorItemsList)
        {
            if (item.getName().equals(subItemName))
            {
                item.setChosenValue(chosenValue);
                return true;
            }
        }
        return false;
    }
}
