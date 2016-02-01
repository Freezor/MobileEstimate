package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items;

import android.graphics.Bitmap;

/**
 * Created by Oliver Fries on 07.01.2016, 11:50.
 * Project: MobileProjectEstimator
 */
public class ImageItem
{
    private int imageDatabaseId;
    private Bitmap image;
    private String title;

    public ImageItem(Bitmap image, String title)
    {
        super();
        imageDatabaseId = 0;
        this.image = image;
        this.title = title;
    }

    public ImageItem()
    {
        super();
        imageDatabaseId = 0;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getImageDatabaseId()
    {
        return imageDatabaseId;
    }

    public void setImageDatabaseId(int imageDatabaseId)
    {
        this.imageDatabaseId = imageDatabaseId;
    }
}
