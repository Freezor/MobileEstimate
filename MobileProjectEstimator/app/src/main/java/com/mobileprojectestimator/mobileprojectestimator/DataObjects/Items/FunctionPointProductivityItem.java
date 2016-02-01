package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items;

/**
 * Created by Oliver Fries on 13.01.2016, 16:28.
 * Project: MobileProjectEstimator
 */
public class FunctionPointProductivityItem
{
    private int id;
    private int project_id;
    private int influenceFactorsetId;
    private double functionPoints;
    private double terminatedDays;
    private double pointsPerDay;

    public FunctionPointProductivityItem()
    {
        this.id = 0;
        this.project_id = 0;
        this.functionPoints = 0.0;
        this.influenceFactorsetId = 0;
        this.terminatedDays = 0.0;
        this.pointsPerDay = 0.0;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getProject_id()
    {
        return project_id;
    }

    public void setProject_id(int project_id)
    {
        this.project_id = project_id;
    }

    public double getFunctionPoints()
    {
        return functionPoints;
    }

    public void setFunctionPoints(double functionPoints)
    {
        this.functionPoints = functionPoints;
    }

    public int getInfluenceFactorsetId()
    {
        return influenceFactorsetId;
    }

    public void setInfluenceFactorsetId(int influenceFactorsetId)
    {
        this.influenceFactorsetId = influenceFactorsetId;
    }

    public double getTerminatedDays()
    {
        return terminatedDays;
    }

    public void setTerminatedDays(double terminatedDays)
    {
        this.terminatedDays = terminatedDays;
    }

    public double getPointsPerDay()
    {
        return pointsPerDay;
    }

    public void setPointsPerDay(double pointsPerDay)
    {
        this.pointsPerDay = pointsPerDay;
    }
}
