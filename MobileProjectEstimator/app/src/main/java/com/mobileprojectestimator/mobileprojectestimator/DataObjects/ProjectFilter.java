package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

/**
 * Created by Oliver Fries on 27.01.2016, 11:20.
 * Project: MobileProjectEstimator
 */
public class ProjectFilter
{
    private String estimationMethod;
    private boolean isActive;

    public ProjectFilter()
    {
        this.estimationMethod = null;
        this.isActive = true;
    }

    public String getEstimationMethod()
    {
        return estimationMethod;
    }

    public void setEstimationMethod(String estimationMethod)
    {
        this.estimationMethod = estimationMethod;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }
}
