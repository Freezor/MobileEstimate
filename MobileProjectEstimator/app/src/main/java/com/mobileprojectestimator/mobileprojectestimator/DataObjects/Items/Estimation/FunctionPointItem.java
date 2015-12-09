package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 21.11.2015, 11:55.
 * Project: MobileProjectEstimator
 */
public class FunctionPointItem extends EstimationItem
{
    //TODO: zusammenfügen mit FunctionPointEstimationItem
    private int totalAmount;

    private ArrayList<FunctionPointCategoryItem> functionPointCategoryItems;

    public FunctionPointItem()
    {
        this.functionPointCategoryItems = new ArrayList<>();
    }

    /**
     * Standard Constructor
     *
     * @param itemName
     * @param weightSimple
     * @param weightMedium
     * @param weightComplex
     */
    public FunctionPointItem(String itemName, int weightSimple, int weightMedium, int weightComplex)
    {
        this.setItemName(itemName);
        this.functionPointCategoryItems = new ArrayList<>();
        this.functionPointCategoryItems.add(new FunctionPointCategoryItem(FunctionPointCategoryItem.CATEGORISATIONSIMPLE, weightSimple));
        this.functionPointCategoryItems.add(new FunctionPointCategoryItem(FunctionPointCategoryItem.CATEGORISATIONMEDIUM, weightMedium));
        this.functionPointCategoryItems.add(new FunctionPointCategoryItem(FunctionPointCategoryItem.CATEGORISATIONCOMPLEX, weightComplex));
    }

    /**
     * Calculates the total Weighted Function Points of this Category
     */
    private void calculateTotalAmount()
    {
        totalAmount = 0;
        for (FunctionPointCategoryItem item : functionPointCategoryItems)
        {
            totalAmount += item.getSumOfCategory();
        }
    }

    /**
     * Returns the calculated total amount of function points from this category
     *
     * @return
     */
    public int getTotalAmount()
    {
        calculateTotalAmount();
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public ArrayList<FunctionPointCategoryItem> getFunctionPointCategoryItems()
    {
        return functionPointCategoryItems;
    }

    public void setFunctionPointCategoryItems(ArrayList<FunctionPointCategoryItem> functionPointCategoryItems)
    {
        this.functionPointCategoryItems = functionPointCategoryItems;
    }

    /**
     * Adds an item to the list and checks if this item already exists
     *
     * @param item
     */
    public void addFunctionPointCategoryItem(FunctionPointCategoryItem item)
    {
        if (!this.functionPointCategoryItems.contains(item))
        {
            this.functionPointCategoryItems.add(item);
        }
    }

    /**
     * Updates the item at the index.
     * Decide with the boolean if you want to raise or lower the value
     *
     * @param index
     * @param raiseValue
     */
    public void raiseItem(int index, boolean raiseValue)
    {
        if (raiseValue)
        {
            functionPointCategoryItems.get(index).addItem();
        } else
        {
            functionPointCategoryItems.get(index).subtractItem();
        }
    }

    public void updateItem(int index, int value)
    {
        functionPointCategoryItems.get(index).setTotalItemCount(value);
    }

    public int getItemTotalAmountOfIndex(int index)
    {
        return functionPointCategoryItems.get(index).getTotalItemCount();
    }

    @Override
    public void refresh()
    {
        this.totalAmount = 0;
        for (FunctionPointCategoryItem item : functionPointCategoryItems)
        {
            item.setSumOfCategory(item.getWeight() * item.getTotalItemCount());
            this.totalAmount += item.getSumOfCategory();
        }
    }

    /**
     * Update all category SUm of all the items of this item
     *
     * @param simpleValue
     * @param mediumValue
     * @param complexValue
     */
    public void updateItems(int simpleValue, int mediumValue, int complexValue)
    {
        functionPointCategoryItems.get(0).setTotalItemCount(simpleValue);
        functionPointCategoryItems.get(1).setTotalItemCount(mediumValue);
        functionPointCategoryItems.get(2).setTotalItemCount(complexValue);
    }

    /**
     * Set the item count of an estimation item at the array index
     *
     * @param index
     * @param value
     */
    public void setValue(int index, int value)
    {
        functionPointCategoryItems.get(index).setTotalItemCount(value);
    }
}
