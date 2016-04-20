package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation;

/**
 * Created by Oliver Fries on 21.11.2015, 11:53.
 * Project: MobileProjectEstimator
 */
public abstract class EstimationItem
{
    private String itemName;


    private String itemId;

    public String getItemName()
    {
        return itemName;
    }

    void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public abstract void refresh();

    /**
     * Set the database table id
     * @param itemId
     */
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    /**
     * get the database table id
     * @return
     */
    public String getItemId()
    {
        return itemId;
    }
}