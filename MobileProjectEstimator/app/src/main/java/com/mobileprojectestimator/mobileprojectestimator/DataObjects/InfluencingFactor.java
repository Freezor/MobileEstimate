package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 25.10.2015.
 * <p/>
 * Base Class for all influence factor sets
 */
public abstract class InfluencingFactor
{

    private String name;
    private ArrayList<FunctionPointInfluenceFactorItem> functionPointInfluenceFactorItems;

    public InfluencingFactor()
    {
        functionPointInfluenceFactorItems = new ArrayList<>();
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<FunctionPointInfluenceFactorItem> getFunctionPointInfluenceFactorItems()
    {
        return functionPointInfluenceFactorItems;
    }

    public void addFactorItem(FunctionPointInfluenceFactorItem item)
    {
        functionPointInfluenceFactorItems.add(item);
    }

    public void setFunctionPointInfluenceFactorItems(ArrayList<FunctionPointInfluenceFactorItem> functionPointInfluenceFactorItems)
    {
        this.functionPointInfluenceFactorItems = functionPointInfluenceFactorItems;
    }

    /**
     * Each Influence Factor class needs to implement this method, that all factors are set.
     * This Method should be called in the constructor.
     */
    abstract void setFactorItems();

}
