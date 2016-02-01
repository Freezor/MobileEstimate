package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.HelpArticleItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.HelpParagraphsListAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HelpArticleActivity extends AppCompatActivity
{

    private String nameTag;
    private HelpArticleItem helpArticleItem;
    private TextView tvProjectName;
    private ListView paragraphsListView;
    private HelpParagraphsListAdapter helpParagraphsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHelpAndFeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        nameTag = i.getStringExtra(String.valueOf(R.string.intent_extra_help_article_name_tag));

        helpArticleItem = new HelpArticleItem();
        helpArticleItem.setNameTag(nameTag);

        loadArticleFromXml();

        tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvProjectName.setText(helpArticleItem.getName());

        paragraphsListView = (ListView) findViewById(R.id.lvArticleParagraphs);
        helpParagraphsAdapter = new HelpParagraphsListAdapter(this, helpArticleItem.getParagraphs());
        paragraphsListView.setAdapter(helpParagraphsAdapter);
    }

    private void loadArticleFromXml()
    {
        XmlPullParserFactory factory = null;
        InputStream is = null;
        try
        {
            is = openFileInput("help_data.xml");
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
            String currentName = "";
            ArrayList<String> paragraphs = new ArrayList<>();
            boolean startTitleText = false;
            boolean startParagraphText = false;
            boolean isSelectedArticle = false;
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equals("article"))
                    {
                        currentName = xpp.getAttributeValue(null, "name");
                        if (currentName.equals(nameTag))
                        {
                            isSelectedArticle = true;
                        }
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
                        if (isSelectedArticle)
                        {
                            if (helpArticleItem.getNameTag().equals(currentName))
                            {
                                isSelectedArticle = false;
                                helpArticleItem.setName(title);
                                helpArticleItem.setParagraphs(paragraphs);
                                paragraphs = new ArrayList<>();
                                title = "";
                                currentName = "";
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
                    if (isSelectedArticle)
                    {
                        if (startTitleText)
                        {
                            title = xpp.getText();
                        } else if (startParagraphText)
                        {
                            paragraphs.add(xpp.getText());
                        }
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
    }

}
