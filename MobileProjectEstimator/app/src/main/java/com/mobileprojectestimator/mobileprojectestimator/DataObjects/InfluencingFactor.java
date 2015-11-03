package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public abstract class InfluencingFactor {

    private String name;
    private ArrayList<FunctionPointFactorItem> functionPointFactorItems;
    public InfluencingFactor(){
        functionPointFactorItems = new ArrayList<>();
    }

    public InfluencingFactor(String name){
        this.name = name;
        functionPointFactorItems = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FunctionPointFactorItem> getFunctionPointFactorItems() {
        return functionPointFactorItems;
    }

    public void addFactorItem(FunctionPointFactorItem item)
    {
        functionPointFactorItems.add(item);
    }

    public void setFunctionPointFactorItems(ArrayList<FunctionPointFactorItem> functionPointFactorItems) {
        this.functionPointFactorItems = functionPointFactorItems;
    }

    abstract void setFactorItems();

}
