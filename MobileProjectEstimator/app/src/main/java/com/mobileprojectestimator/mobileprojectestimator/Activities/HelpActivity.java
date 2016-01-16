package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.HelpArticleItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.HelpArticlesListAdapter;

import java.util.ArrayList;

public class HelpActivity extends DatabaseActivity
{
    private ArrayList<HelpArticleItem> helpArticleItems;

    private HelpArticlesListAdapter helpArticlesAdapter;
    private ListView articlesListView;
    private TextView showAllArticlesListView;
    private boolean allArticlesShown;
    private View articlesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initDatabase();

        helpArticleItems = databaseHelper.loadAllHelpItems();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHelpAndFeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articlesLayout = (View) findViewById(R.id.articlesLayout);

        articlesListView = (ListView) articlesLayout.findViewById(R.id.listViewHelpArticles);
        helpArticlesAdapter = new HelpArticlesListAdapter(this);
        helpArticlesAdapter.setHelpArticles(helpArticleItems);
        articlesListView.setAdapter(helpArticlesAdapter);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                onClickHelpItem(position);
            }
        });

        allArticlesShown = false;

        showAllArticlesListView = (TextView) articlesLayout.findViewById(R.id.tvAllArticles);
        showAllArticlesListView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (allArticlesShown)
                {
                    allArticlesShown = false;
                    showAllArticlesListView.setText(R.string.help_activity_show_all_articles);
                    helpArticlesAdapter.setHelpArticles(helpArticleItems);
                    helpArticlesAdapter.notifyDataSetChanged();
                    articlesLayout.invalidate();
                } else
                {
                    allArticlesShown = true;
                    showAllArticlesListView.setText(R.string.help_activity_show_relevant_articles);
                    helpArticlesAdapter.setAllHelpArticles(helpArticleItems);
                    helpArticlesAdapter.notifyDataSetChanged();
                    articlesLayout.invalidate();
                }
            }
        });
        View feedbackView = (View) articlesLayout.findViewById(R.id.help_activity_feedback);
        feedbackView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickFeedback();
            }
        });
    }

    private void onClickFeedback()
    {
        //TODO: send feedback with extra intent and without mail app
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "support@cost-estimator.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_feedback_intent_title)));
    }

    private void onClickHelpItem(int position)
    {
        //TODO: start hel activity to sho help details
        ArrayList<String> helpItems = helpArticlesAdapter.getHelpTitlesArrayList();
        String name = helpItems.get(position).toString();
        for (HelpArticleItem item : helpArticleItems)
        {
            if (item.getName().equals(name))
            {
                Intent i = new Intent(HelpActivity.this,HelpArticleActivity.class);
                i.putExtra(String.valueOf(R.string.intent_extra_help_article_name_tag),item.getNameTag());
                startActivity(i);
                break;
            }
        }
    }
}
