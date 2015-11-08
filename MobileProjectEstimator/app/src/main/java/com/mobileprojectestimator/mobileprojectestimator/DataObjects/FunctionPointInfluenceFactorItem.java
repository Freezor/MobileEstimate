package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 *
 * Class for each influence factor item in a function point estimation project
 */
public class FunctionPointInfluenceFactorItem
{
    private boolean hasSubItems;
    private String name;
    private int minValue;
    private int maxValue;
    private ArrayList<FunctionPointInfluenceFactorItem> subFunctionPointInfluenceFactorItemsList;
    private int chosenValue = 0;

    /**
     * Creates and returns a copy of this {@code Object}. The default
     * implementation returns a so-called "shallow" copy: It creates a new
     * instance of the same class and then copies the field values (including
     * object references) from this instance to the new instance. A "deep" copy,
     * in contrast, would also recursively clone nested objects. A subclass that
     * needs to implement this kind of cloning should call {@code super.clone()}
     * to create the new instance and then create deep copies of the nested,
     * mutable objects.
     *
     * @return a copy of this object.
     * @throws CloneNotSupportedException if this object's class does not implement the {@code
     *                                    Cloneable} interface.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    /**
     * Standard constructor
     * @param name
     * @param minValue
     * @param maxValue
     * @param hasSubItems
     */
    public FunctionPointInfluenceFactorItem(String name, int minValue, int maxValue, boolean hasSubItems)
    {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.hasSubItems = hasSubItems;
        if (hasSubItems)
        {
            subFunctionPointInfluenceFactorItemsList = new ArrayList<>();
        }
    }

    /**
     * create item with no sub items
     * @param name
     * @param minValue
     * @param maxValue
     */
    public FunctionPointInfluenceFactorItem(String name, int minValue, int maxValue)
    {
        this(name, minValue, maxValue, false);
    }

    /**
     * Influence Factor Item with already created list of subitems
     * @param name
     * @param minValue
     * @param maxValue
     * @param hasSubItems
     * @param subFunctionPointInfluenceFactorItemsList
     */
    public FunctionPointInfluenceFactorItem(String name, int minValue, int maxValue, boolean hasSubItems, ArrayList<FunctionPointInfluenceFactorItem> subFunctionPointInfluenceFactorItemsList)
    {
        this(name, minValue, maxValue, hasSubItems);
        if (hasSubItems)
        {
            this.subFunctionPointInfluenceFactorItemsList = subFunctionPointInfluenceFactorItemsList;
        }
    }

    /**
     * Has the Factor Item subitems
     * @return
     */
    public boolean isHasSubItems()
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

    public ArrayList<FunctionPointInfluenceFactorItem> getSubFunctionPointInfluenceFactorItemsList()
    {
        if (hasSubItems)
        {
            return subFunctionPointInfluenceFactorItemsList;
        } else
        {
            return null;
        }

    }

    public void setSubFunctionPointInfluenceFactorItemsList(ArrayList<FunctionPointInfluenceFactorItem> subFunctionPointInfluenceFactorItemsList)
    {
        if (hasSubItems)
        {
            this.subFunctionPointInfluenceFactorItemsList = subFunctionPointInfluenceFactorItemsList;
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
