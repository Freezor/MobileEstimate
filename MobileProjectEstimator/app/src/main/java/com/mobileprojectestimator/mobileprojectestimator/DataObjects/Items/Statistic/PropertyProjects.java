package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Statistic;

/**
 * Created by Oliver Fries on 21.01.2016, 12:31.
 * Project: MobileProjectEstimator
 */
public class PropertyProjects
{
    private String propertyName;
    private int numberOfProjects;

    public PropertyProjects()
    {

    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public int getNumberOfProjects()
    {
        return numberOfProjects;
    }

    public void setNumberOfProjects(int numberOfProjects)
    {
        this.numberOfProjects = numberOfProjects;
    }
}
