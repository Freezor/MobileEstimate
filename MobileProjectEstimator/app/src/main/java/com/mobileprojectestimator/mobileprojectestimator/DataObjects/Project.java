package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;

import com.mobileprojectestimator.mobileprojectestimator.ProjectOverview;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.Date;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public class Project {

    private String Title;
    private Bitmap image;
    private Date creationDate;
    private String estimationType;//TODO: change with db
    private InfluencingFactor influencingFactor;
    private Context context;

    public Project(Context current, String title, Date creationDate, String estimationType) {
        this.Title = title;
        this.creationDate = creationDate;
        this.estimationType = estimationType;
        this.context = current;
        setInfluencingFactors();

    }

    private void setInfluencingFactors()
    {
        if (estimationType.equals(context.getResources().getString(R.string.functionPoint)))
        {
            influencingFactor = new InfluencingFactor<FunctionPointFactor>();
        }
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
}
