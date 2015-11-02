package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public abstract class InfluencingFactor {

    private String name;
    private ArrayList<FactorItem> factorItems;

    public InfluencingFactor(){
        factorItems = new ArrayList<>();
    }

    public InfluencingFactor(String name){
        this.name = name;
        factorItems = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FactorItem> getFactorItems() {
        return factorItems;
    }

    public void addFactorItem(FactorItem item)
    {
        factorItems.add(item);
    }

    public void setFactorItems(ArrayList<FactorItem> factorItems) {
        this.factorItems = factorItems;
    }

    abstract void setFactorItems();
}
