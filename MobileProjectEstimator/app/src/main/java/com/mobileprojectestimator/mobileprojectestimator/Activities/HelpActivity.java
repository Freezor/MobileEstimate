package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.R;

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
        //TODO: start feedback activity to send feedback
        Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
    }

    private void onClickHelpItem(int position)
    {
        //TODO: start hel activity to sho help details
        ArrayList<String> helpItems = helpArticlesAdapter.getHelpTitlesArrayList();
        Toast.makeText(this, "Item: "+helpItems.get(position).toString(), Toast.LENGTH_SHORT).show();
    }
}
