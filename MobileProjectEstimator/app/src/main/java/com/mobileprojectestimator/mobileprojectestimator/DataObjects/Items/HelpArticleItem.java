package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 16.01.2016, 15:36.
 * Project: MobileProjectEstimator
 */
public class HelpArticleItem
{
    private int id;
    private String nameTag;
    private String name;
    private ArrayList<String> paragraphs;

    public HelpArticleItem()
    {
        this.name = "EMPTY";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNameTag()
    {
        return nameTag;
    }

    public void setNameTag(String nameTag)
    {
        this.nameTag = nameTag;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<String> getParagraphs()
    {
        return paragraphs;
    }

    public void setParagraphs(ArrayList<String> paragraphs)
    {
        this.paragraphs = paragraphs;
    }
}
