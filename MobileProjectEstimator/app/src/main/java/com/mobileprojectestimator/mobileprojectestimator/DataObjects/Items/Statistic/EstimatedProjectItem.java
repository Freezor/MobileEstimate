package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Statistic;

/**
 * Created by Oliver Fries on 21.01.2016, 11:52.
 * Project: MobileProjectEstimator
 */
public class EstimatedProjectItem
{
    private String estimationMethod;
    private int numberOfProjects;

    public EstimatedProjectItem(String methodName, int projectsCount)
    {
        this.estimationMethod = methodName;
        this.numberOfProjects = projectsCount;
    }

    public String getEstimationMethod()
    {
        return estimationMethod;
    }

    @SuppressWarnings("unused")
    public void setEstimationMethod(String estimationMethod)
    {
        this.estimationMethod = estimationMethod;
    }

    public int getNumberOfProjects()
    {
        return numberOfProjects;
    }

    @SuppressWarnings("unused")
    public void setNumberOfProjects(int numberOfProjects)
    {
        this.numberOfProjects = numberOfProjects;
    }
}
