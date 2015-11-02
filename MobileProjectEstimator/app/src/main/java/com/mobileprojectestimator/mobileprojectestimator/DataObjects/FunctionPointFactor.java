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
        this.addFactorItem(new FactorItem("Integration into other applications", 0, 5));
        this.addFactorItem(new FactorItem("Local Data Processing", 0, 5));
        this.addFactorItem(new FactorItem("Transaction Rate", 0, 5));

        ArrayList<FactorItem> subItems = new ArrayList<>();
        subItems.add(new FactorItem("Arithmetic Operation", 0, 10));
        subItems.add(new FactorItem("Control Procedure", 0, 5));
        subItems.add(new FactorItem("Exception Regulation", 0, 10));
        subItems.add(new FactorItem("Logic", 0, 5));
        this.addFactorItem(new FactorItem("Processing Logic", 0, 0, true, subItems));

        this.addFactorItem(new FactorItem("Reusability", 0, 5));
        this.addFactorItem(new FactorItem("Stock Conversion", 0, 5));
        this.addFactorItem(new FactorItem("Adaptability", 0, 5));
    }

}
