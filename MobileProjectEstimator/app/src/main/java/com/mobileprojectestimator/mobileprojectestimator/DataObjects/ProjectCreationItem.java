package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.graphics.Bitmap;

/**
 * Created by Oliver Fries on 31.10.2015.
 */
public class ProjectCreationItem {
    private String Name;
    private String Value;
    private Bitmap itemIcon;

    public ProjectCreationItem(String name, String standardValue){
        this.Name = name;
        this.Value = standardValue;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Bitmap getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Bitmap itemIcon) {
        this.itemIcon = itemIcon;
    }
}
