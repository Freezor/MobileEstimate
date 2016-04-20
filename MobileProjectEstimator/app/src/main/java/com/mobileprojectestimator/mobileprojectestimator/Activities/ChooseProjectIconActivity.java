package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.ImageItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.ProjectIconsGridViewAdapter;

import java.util.ArrayList;

public class ChooseProjectIconActivity extends DatabaseActivity
{
    private GridView gridView;
    private ProjectIconsGridViewAdapter gridAdapter;
    private ImageItem chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_project_icon);
        initDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProjectIcon);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.projectIconsgridView);
        gridAdapter = new ProjectIconsGridViewAdapter(this, R.layout.project_icons_grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                chosenImage = (ImageItem) parent.getItemAtPosition(position);
            }
        });
        chosenImage =(ImageItem) gridView.getItemAtPosition(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_choose_project_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_save_icon:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<ImageItem> getData()
    {
        return databaseHelper.loadAllProjectIcons();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = this.getIntent();
        if (chosenImage.getImageDatabaseId() < 1)
        {
            intent.putExtra("PROJECTICONID", 1);
        } else
        {
            intent.putExtra("PROJECTICONID", chosenImage.getImageDatabaseId());
        }
        this.setResult(Integer.parseInt((getString(R.string.PROJECT_ICON_DIALOG_CODE))), intent);
        finish();
    }
}
