package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.HelpArticlesListAdapter;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity
{


    private HelpArticlesListAdapter helpArticlesAdapter;
    private ListView articlesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHelpAndFeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View articlesLayout = (View) findViewById(R.id.articlesLayout);

        articlesListView = (ListView) articlesLayout.findViewById(R.id.listViewHelpArticles);
        helpArticlesAdapter = new HelpArticlesListAdapter(this);
        articlesListView.setAdapter(helpArticlesAdapter);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                onClickHelpItem(position);
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
        Toast.makeText(this, "Item: "+helpItems.get(position).toString(), Toast.LENGTH_SHORT).show();
    }
}
