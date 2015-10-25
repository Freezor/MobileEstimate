package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;

import java.util.Date;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public class Project {

    private String Title;
    private Bitmap image;
    private Date creationDate;
    private String estimationType;//TODO: change with db



    public Project(String title, Date creationDate,String estimationType) {
        this.Title = title;
        this.creationDate = creationDate;
        this.estimationType = estimationType;
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
