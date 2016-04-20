package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

/**
 * Created by Oliver Fries on 07.02.2016, 16:45.
 * Project: MobileProjectEstimator
 */
public class SoftwareArchitectureProperty
{
    private int category;
    private int overallAgility;
    private int easeOfDeployment;
    private int testability;
    private int performance;
    private int scalability;
    private int easeOfDevelopment;
    private int id;

    public int getEaseOfDevelopment()
    {
        return easeOfDevelopment;
    }

    public void setEaseOfDevelopment(int easeOfDevelopment)
    {
        this.easeOfDevelopment = easeOfDevelopment;
    }

    public int getScalability()
    {
        return scalability;
    }

    public void setScalability(int scalability)
    {
        this.scalability = scalability;
    }

    public int getPerformance()
    {
        return performance;
    }

    public void setPerformance(int performance)
    {
        this.performance = performance;
    }

    public int getTestability()
    {
        return testability;
    }

    public void setTestability(int testability)
    {
        this.testability = testability;
    }

    public int getEaseOfDeployment()
    {
        return easeOfDeployment;
    }

    public void setEaseOfDeployment(int easeOfDeployment)
    {
        this.easeOfDeployment = easeOfDeployment;
    }

    public int getOverallAgility()
    {
        return overallAgility;
    }

    public void setOverallAgility(int overallAgility)
    {
        this.overallAgility = overallAgility;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
