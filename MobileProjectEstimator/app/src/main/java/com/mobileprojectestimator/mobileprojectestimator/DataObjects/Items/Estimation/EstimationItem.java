package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation;

/**
 * Created by Oliver Fries on 21.11.2015, 11:53.
 * Project: MobileProjectEstimator
 */
public abstract class EstimationItem
{
    private String itemName;

    public String getItemName()
    {
        return itemName;
    }

    void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
}
