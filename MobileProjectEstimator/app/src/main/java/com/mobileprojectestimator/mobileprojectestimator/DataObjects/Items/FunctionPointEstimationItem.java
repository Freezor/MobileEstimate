package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items;

/**
 * Created by Oliver Fries on 02.11.2015, 15:37.
 * Project: MobileProjectEstimator
 */
public class FunctionPointEstimationItem
{
    private String name;
    private int value;
    private int simple;
    private int medium;
    private int complex;
    private String information;

    private int WEIGHT_SIMPLE, WEIGHT_MEDIUM, WEIGHT_COMPLEX;

    public FunctionPointEstimationItem(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getSimple()
    {
        return simple;
    }

    public void setSimple(int simple)
    {
        this.simple = simple;
    }

    public int getMedium()
    {
        return medium;
    }

    public void setMedium(int medium)
    {
        this.medium = medium;
    }

    public int getComplex()
    {
        return complex;
    }

    public void setComplex(int complex)
    {
        this.complex = complex;
    }

    public String getInformation()
    {
        return information;
    }

    public void setInformation(String information)
    {
        this.information = information;
    }

    public int getWEIGHT_SIMPLE()
    {
        return WEIGHT_SIMPLE;
    }

    public void setWEIGHT_SIMPLE(int WEIGHT_SIMPLE)
    {
        this.WEIGHT_SIMPLE = WEIGHT_SIMPLE;
    }

    public int getWEIGHT_MEDIUM()
    {
        return WEIGHT_MEDIUM;
    }

    public void setWEIGHT_MEDIUM(int WEIGHT_MEDIUM)
    {
        this.WEIGHT_MEDIUM = WEIGHT_MEDIUM;
    }

    public int getWEIGHT_COMPLEX()
    {
        return WEIGHT_COMPLEX;
    }

    public void setWEIGHT_COMPLEX(int WEIGHT_COMPLEX)
    {
        this.WEIGHT_COMPLEX = WEIGHT_COMPLEX;
    }
}
