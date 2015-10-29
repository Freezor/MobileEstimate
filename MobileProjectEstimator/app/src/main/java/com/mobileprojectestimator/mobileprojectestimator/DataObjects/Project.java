package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.content.Context;
import android.graphics.Bitmap;

import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public class Project implements Serializable{

    private String Title;
    private Bitmap image;
    private Date creationDate;
    private String estimationType;//TODO: change with db
    private InfluencingFactor influencingFactor;
    private Context context;
    private ProjectProperties projectProperties;

    /**
     * Create a Project with already known title, creation Date and estimation Type
     * Mostly for test purpose
     * @param current
     * @param title
     * @param creationDate
     * @param estimationType
     */
    public Project(Context current, String title, Date creationDate, String estimationType) {
        this.Title = title;
        this.creationDate = creationDate;
        this.estimationType = estimationType;
        this.context = current;
        setInfluencingFactors();

    }

    /**
     * Create an empty project
     * @param current
     */
    public Project(Context current) {
        this.context = current;
    }

    public Context getContext(){return this.context;}

    private void setInfluencingFactors()
    {
        if (estimationType.equals(context.getResources().getString(R.string.functionPoint)))
        {
            influencingFactor = new InfluencingFactor<FunctionPointFactor>();
        }
        projectProperties = new ProjectProperties();
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImage(Bitmap img)
    {
        this.image = img;
    }

    public Bitmap getImage() {
        return image;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEstimationType() {
        return estimationType;
    }

    public void setEstimationType(String estimationType) {
        this.estimationType = estimationType;
    }

    public InfluencingFactor getInfluencingFactor() {
        return influencingFactor;
    }

    public void setInfluencingFactor(InfluencingFactor influencingFactor) {
        this.influencingFactor = influencingFactor;
    }

    public ProjectProperties getProjectProperties() {
        return projectProperties;
    }

    public void setProjectProperties(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }
}
