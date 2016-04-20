package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items;

import android.graphics.Bitmap;

/**
 * Created by Oliver Fries on 31.10.2015.
 * <p/>
 * Item for create a new project
 */
@SuppressWarnings("JavaDoc")
public class ProjectItemForCreation
{
    /**
     * Name of the item
     */
    private String Name;
    /**
     * Value of the Item
     */
    private String Value;
    /**
     * Eventual Item Icon
     */
    private Bitmap itemIcon;

    /**
     * Standard Constructor
     *
     * @param name
     * @param standardValue
     */
    public ProjectItemForCreation(String name, String standardValue)
    {
        this.Name = name;
        this.Value = standardValue;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getValue()
    {
        return Value;
    }

    public void setValue(String value)
    {
        Value = value;
    }

    @SuppressWarnings("unused")
    public Bitmap getItemIcon()
    {
        return itemIcon;
    }

    @SuppressWarnings("unused")
    public void setItemIcon(Bitmap itemIcon)
    {
        this.itemIcon = itemIcon;
    }
}
