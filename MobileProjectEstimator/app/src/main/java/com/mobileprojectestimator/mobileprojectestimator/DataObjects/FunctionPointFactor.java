package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public class FunctionPointFactor extends InfluencingFactor{


    public FunctionPointFactor(){
        setFactorItems();
    }

    public FunctionPointFactor(String name){
        this.setName(name);
        setFactorItems();
    }

    @Override
    void setFactorItems() {
        this.addFactorItem(new FunctionPointFactorItem("Integration into other applications", 0, 5));
        this.addFactorItem(new FunctionPointFactorItem("Local Data Processing", 0, 5));
        this.addFactorItem(new FunctionPointFactorItem("Transaction Rate", 0, 5));

        ArrayList<FunctionPointFactorItem> subItems = new ArrayList<>();
        subItems.add(new FunctionPointFactorItem("Arithmetic Operation", 0, 10));
        subItems.add(new FunctionPointFactorItem("Control Procedure", 0, 5));
        subItems.add(new FunctionPointFactorItem("Exception Regulation", 0, 10));
        subItems.add(new FunctionPointFactorItem("Logic", 0, 5));
        this.addFactorItem(new FunctionPointFactorItem("Processing Logic", 0, 0, true, subItems));

        this.addFactorItem(new FunctionPointFactorItem("Reusability", 0, 5));
        this.addFactorItem(new FunctionPointFactorItem("Stock Conversion", 0, 5));
        this.addFactorItem(new FunctionPointFactorItem("Adaptability", 0, 5));
    }

}
