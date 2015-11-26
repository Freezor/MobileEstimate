package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation;

/**
 * Created by Oliver Fries on 21.11.2015, 11:57.
 * Project: MobileProjectEstimator
 * <p/>
 * Items for the function Point Estimation
 * Only items available per definition are simple, medium and compley
 */
public class FunctionPointCategoryItem
{
    /**
     * Simple Item
     */
    public static final String CATEGORISATIONSIMPLE = "simple";
    /**
     * Medium Item
     */
    public static final String CATEGORISATIONMEDIUM = "medium";
    /**
     * Complex Item
     */
    public static final String CATEGORISATIONCOMPLEX = "complex";

    /**
     * The Category of the actual item. Must Contain one of the above static Strings
     */
    private final String categorisation;
    /**
     * The Weight for this item. Can only be initialised and not altered later
     */
    private final int weight;
    /**
     * total Number of Items in this category
     */
    private int totalItemCount;
    /**
     * Weighted Value of this Category = totalItemCount * weight
     */
    private int sumOfCategory;

    /**
     * Standard Constructor for function point items
     * Item Name need to be set with the FunctionPointCategoryItem static strings
     *
     * @param categorisation
     * @param weight
     */
    public FunctionPointCategoryItem(String categorisation, int weight)
    {
        this.categorisation = categorisation;
        this.totalItemCount = 0;
        this.sumOfCategory = 0;
        this.weight = weight;
    }

    /**
     * Get the Name of the Category
     *
     * @return
     */
    public String getCategorisation()
    {
        return categorisation;
    }

    /**
     * Get the total amount of items in this category
     *
     * @return
     */
    public int getTotalItemCount()
    {
        return totalItemCount;
    }

    /**
     * Set the total amount of items
     *
     * @param totalItemCount
     */
    public void setTotalItemCount(int totalItemCount)
    {
        this.totalItemCount = totalItemCount;
    }

    /**
     * get the sum of this category.
     * Updates the value each time (weight * amount)
     *
     * @return
     */
    public int getSumOfCategory()
    {
        calculateSumOfCategory();
        return sumOfCategory;
    }

    public void setSumOfCategory(int sumOfCategory)
    {
        this.sumOfCategory = sumOfCategory;
    }

    public int getWeight()
    {
        return weight;
    }

    private void calculateSumOfCategory()
    {
        this.sumOfCategory = totalItemCount * weight;
    }

    /**
     * Add an item to this category
     */
    public void addItem()
    {
        this.totalItemCount++;
    }

    /**
     * Subtract an item from this category.
     * Value cannot be lower than 0
     */
    public void subtractItem()
    {
        if (this.totalItemCount > 0)
        {
            this.totalItemCount--;
        }
    }
}
