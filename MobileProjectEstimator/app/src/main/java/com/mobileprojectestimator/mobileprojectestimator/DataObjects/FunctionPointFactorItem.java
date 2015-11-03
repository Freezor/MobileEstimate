package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public class FunctionPointFactorItem {
    private boolean hasSubItems;
    private String name;
    private int minValue;
    private int maxValue;
    private ArrayList<FunctionPointFactorItem> subFunctionPointFactorItemsList;
    private int chosenValue = 0;

    public FunctionPointFactorItem(String name, int minValue, int maxValue, boolean hasSubItems) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.hasSubItems = hasSubItems;
        if(hasSubItems){
            subFunctionPointFactorItemsList = new ArrayList<>();
        }
    }

    public FunctionPointFactorItem(String name, int minValue, int maxValue) {
        this( name,  minValue,  maxValue,false);
    }

    public FunctionPointFactorItem(String name, int minValue, int maxValue, boolean hasSubItems, ArrayList<FunctionPointFactorItem> subFunctionPointFactorItemsList) {
        this( name,  minValue,  maxValue, hasSubItems);
        if(hasSubItems){
            this.subFunctionPointFactorItemsList = subFunctionPointFactorItemsList;
        }
    }

    public FunctionPointFactorItem() {
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

    public ArrayList<FunctionPointFactorItem> getSubFunctionPointFactorItemsList() {
        if(hasSubItems){
            return subFunctionPointFactorItemsList;
        } else {
            return null;
        }

    }

    public void setSubFunctionPointFactorItemsList(ArrayList<FunctionPointFactorItem> subFunctionPointFactorItemsList) {
        if ( hasSubItems){
            this.subFunctionPointFactorItemsList = subFunctionPointFactorItemsList;
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
