package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public class FactorItem {
    private boolean hasSubItems;
    private String name;
    private int minValue;
    private int maxValue;
    private ArrayList<FactorItem> subFactorItemsList;
    private int chosenValue = 0;

    public FactorItem(String name, int minValue, int maxValue, boolean hasSubItems) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.hasSubItems = hasSubItems;
        if(hasSubItems){
            subFactorItemsList = new ArrayList<>();
        }
    }

    public FactorItem(String name, int minValue, int maxValue) {
        this( name,  minValue,  maxValue,false);
    }

    public FactorItem(String name, int minValue, int maxValue,boolean hasSubItems,ArrayList<FactorItem> subFactorItemsList) {
        this( name,  minValue,  maxValue, hasSubItems);
        if(hasSubItems){
            this.subFactorItemsList = subFactorItemsList;
        }
    }

    public FactorItem() {
    }

    public boolean isHasSubItems() {
        return hasSubItems;
    }

    public void setHasSubItems(boolean hasSubItems) {
        this.hasSubItems = hasSubItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public ArrayList<FactorItem> getSubFactorItemsList() {
        if(hasSubItems){
            return subFactorItemsList;
        } else {
            return null;
        }

    }

    public void setSubFactorItemsList(ArrayList<FactorItem> subFactorItemsList) {
        if ( hasSubItems){
            this.subFactorItemsList = subFactorItemsList;
        } else {

        }
    }

    public int getChosenValue() {
        return chosenValue;
    }

    public void setChosenValue(int chosenValue) {
        this.chosenValue = chosenValue;
    }
}
