package com.mobileprojectestimator.mobileprojectestimator.Util.database;

import android.content.Context;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.HelpArticleItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Oliver Fries on 21.01.2016, 14:34.
 * Project: MobileProjectEstimator
 */
public class XmlHelper
{
    private Context context;

    public XmlHelper(Context c)
    {
        this.context = c;
    }

    /**
     * Load all Help Items from the XML File
     *
     * @param helpArticleItems
     * @return
     */
    public ArrayList<HelpArticleItem> loadHelpArticlesFromXML(ArrayList<HelpArticleItem> helpArticleItems)
    {
        XmlPullParserFactory factory = null;
        InputStream is = null;
        try
        {
            is = context.openFileInput("help_data.xml");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(is, null);
            int eventType = xpp.getEventType();

            String title = "";
            String name = "";
            ArrayList<String> paragraphs = new ArrayList<>();
            boolean startTitleText = false;
            boolean startParagraphText = false;
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equals("article"))
                    {
                        name = xpp.getAttributeValue(null, "name");
                    } else if (xpp.getName().equals("title"))
                    {
                        startTitleText = true;
                    } else if (xpp.getName().equals("paragraph"))
                    {
                        startParagraphText = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equals("article"))
                    {
                        for (HelpArticleItem item : helpArticleItems)
                        {
                            if (item.getNameTag().equals(name))
                            {
                                item.setName(title);
                                item.setParagraphs(paragraphs);
                                paragraphs = new ArrayList<>();
                                title = "";
                                name = "";
                                startTitleText = false;
                                startParagraphText = false;
                                break;
                            }
                        }
                    } else if (xpp.getName().equals("title"))
                    {
                        startTitleText = false;
                    } else if (xpp.getName().equals("paragraph"))
                    {
                        startParagraphText = false;
                    }
                } else if (eventType == XmlPullParser.TEXT)
                {
                    if (startTitleText)
                    {
                        title = xpp.getText();
                    } else if (startParagraphText)
                    {
                        paragraphs.add(xpp.getText());
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return helpArticleItems;
    }

    public String loadDescriptionText(String searchTerm)
    {
        String description = "";
        XmlPullParserFactory factory = null;
        InputStream is = null;
        try
        {
            is = context.openFileInput("texts.xml");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(is, null);
            int eventType = xpp.getEventType();

            ArrayList<String> paragraphs = new ArrayList<>();
            boolean rightNode = false;
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String name;
                if (eventType == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equals("text"))
                    {
                        name = xpp.getAttributeValue(null, "name");
                        if (name.equals(searchTerm))
                        {
                            rightNode = true;
                        }
                    }
                }else if (eventType == XmlPullParser.TEXT)
                {
                    if (rightNode)
                    {
                        description = xpp.getText();
                        break;
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return description;
    }
}
