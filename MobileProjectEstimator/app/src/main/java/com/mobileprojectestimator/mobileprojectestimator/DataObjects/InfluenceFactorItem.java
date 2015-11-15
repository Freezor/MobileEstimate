package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 *
 * Class for each influence factor item in a function point estimation project
 */
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
     * @param name
     * @param minValue
     * @param maxValue
     */
    public InfluenceFactorItem(String name, int minValue, int maxValue)
    {
        this(name, minValue, maxValue, false);
    }

    /**
     * Influence Factor Item with already created list of subitems
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
     * @return
     */
    public boolean hasSubItems()
    {
        return hasSubItems;
    }

    public void setHasSubItems(boolean hasSubItems)
    {
        this.hasSubItems = hasSubItems;
    }

    /**
     * Get the name of this influence factor item
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

    public void setMinValue(int minValue)
    {
        this.minValue = minValue;
    }

    public int getMaxValue()
    {
        return maxValue;
    }

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
        this.chosenValue = chosenValue;
    }
}
