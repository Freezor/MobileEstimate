package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.HelpArticleItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.HelpArticlesListAdapter;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.SearchedHelpArticlesListAdapter;

import java.util.ArrayList;

public class HelpActivity extends DatabaseActivity
{
    private ArrayList<HelpArticleItem> helpArticleItems;
    private ArrayList<HelpArticleItem> searchedHelpArticleItems;

    private HelpArticlesListAdapter helpArticlesAdapter;
    private ListView articlesListView;
    private TextView showAllArticlesListView;
    private boolean allArticlesShown;
    private View articlesLayout;
    private SearchView searchView;
    private boolean isSearched = false;
    private String searchTerm = "";
    private ArrayList<String> searchTerms;
    private TextView tvArtikel;
    private View feedbackView;
    private SearchedHelpArticlesListAdapter searchedHelpArticlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initDatabase();

        helpArticleItems = databaseHelper.loadAllHelpItems();
        searchedHelpArticleItems = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHelpAndFeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }

        articlesLayout = (View) findViewById(R.id.articlesLayout);
        articlesListView = (ListView) articlesLayout.findViewById(R.id.listViewHelpArticles);
        showAllArticlesListView = (TextView) articlesLayout.findViewById(R.id.tvAllArticles);
        searchView = (SearchView) articlesLayout.findViewById(R.id.searchView);
        tvArtikel = (TextView) articlesLayout.findViewById(R.id.tvArtikel);
        feedbackView = (View) articlesLayout.findViewById(R.id.help_activity_feedback);

        if (!isSearched)
        {
            resetSearch();
        } else
        {
            doSearch(searchTerm);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query)
    {
        searchTerm = query;
        searchedHelpArticleItems = new ArrayList<>();
        searchTerms = new ArrayList<>();
        for (String word : searchTerm.split(" "))
        {
            searchTerms.add(word);
        }

        hideKeyboard();
        isSearched = true;
        Log.d("INFO", "Help View Search Query: " + query);

        tvArtikel.setText(R.string.help_activity_search_results);
        showAllArticlesListView.setText(R.string.help_activity_reset_search);
        showAllArticlesListView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetSearch();
            }
        });
        searchedHelpArticlesAdapter = new SearchedHelpArticlesListAdapter(this);

        searchInHelpItems();

        searchedHelpArticlesAdapter.setHelpItems(searchedHelpArticleItems);
        articlesListView.setAdapter(searchedHelpArticlesAdapter);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                onClickHelpItem(position);
            }
        });
        helpArticlesAdapter.notifyDataSetChanged();
        articlesLayout.invalidate();
    }

    private void searchInHelpItems()
    {
        for (HelpArticleItem item : helpArticleItems)
        {

            for (String key : searchTerms)
            {
                if (item.getName().toLowerCase().contains(key.toLowerCase()))
                {
                    searchedHelpArticleItems.add(item);
                    break;
                } else
                {
                    for (String paragraph : item.getParagraphs())
                    {
                        if (paragraph.toLowerCase().contains(key.toLowerCase()))
                        {
                            searchedHelpArticleItems.add(item);
                            break;
                        }
                    }
                }
            }
        }
        if (searchedHelpArticleItems.size() < 1)
        {
            HelpArticleItem item = new HelpArticleItem();
            item.setName("No Result fund");
            ArrayList<String> paragraphs = new ArrayList<>();
            paragraphs.add("Please change your search query.");
            item.setParagraphs(paragraphs);
            searchedHelpArticleItems.add(item);
        }
    }

    private void resetSearch()
    {
        isSearched = false;
        searchedHelpArticleItems = new ArrayList<>();
        searchTerm = "";
        searchView.setQuery("", false);
        searchView.clearFocus();
        tvArtikel.setText(R.string.help_activity_article_headline);
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
        showAllArticlesListView.setText(R.string.help_activity_show_relevant_articles);
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
        feedbackView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickFeedback();
            }
        });
        articlesLayout.invalidate();
    }

    private void onClickFeedback()
    {
        //TODO: send feedback with extra intent and without mail app
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "frieso@fh-trier.de", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_feedback_intent_title)));
    }

    private void onClickHelpItem(int position)
    {
        ArrayList<String> helpItems = helpArticlesAdapter.getHelpTitlesArrayList();
        String name = helpItems.get(position).toString();
        for (HelpArticleItem item : helpArticleItems)
        {
            if (item.getName().equals(name))
            {
                Intent i = new Intent(HelpActivity.this, HelpArticleActivity.class);
                i.putExtra(String.valueOf(R.string.intent_extra_help_article_name_tag), item.getNameTag());
                startActivity(i);
                break;
            }
        }
    }
}
