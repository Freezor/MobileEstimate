package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectFilter;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.Collections;

public class ProjectFilterActivity extends DatabaseActivity
{

    private ProjectFilter filter;
    private Switch sFilterActive;
    private TextView tvMethodName;
    private ArrayList<String> estimationMethodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_filter);

        initDatabase();

        filter = new ProjectFilter();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        filter.setIsActive(sharedPref.getBoolean(getString(R.string.filter_is_active_key), false));
        String estimationMethodFilter = sharedPref.getString(getString(R.string.filter_estimation_method_key), "");
        filter.setEstimationMethod(estimationMethodFilter);

        sFilterActive = (Switch) findViewById(R.id.sFilterActive);
        tvMethodName = (TextView) findViewById(R.id.tvMethodName);

        loadAllFilterOptions();

        sFilterActive.setChecked(filter.isActive());
        sFilterActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                filter.setIsActive(isChecked);
            }
        });

        if(estimationMethodFilter.isEmpty()||estimationMethodFilter.equals("")){
            tvMethodName.setText(R.string.filter_none);
        } else {
            tvMethodName.setText(filter.getEstimationMethod());
        }
        tvMethodName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showEstimationMethodDialog();
            }
        });
    }

    private void loadAllFilterOptions()
    {
        estimationMethodItems = databaseHelper.getEstimationMethodNames();
        Collections.sort(estimationMethodItems);
        estimationMethodItems.add(0,getString(R.string.filter_none));

    }

    private void showEstimationMethodDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.filter_estimation_method_dialog_title));
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        final CharSequence[] items = estimationMethodItems.toArray(new String[estimationMethodItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                filter.setEstimationMethod(items[item].toString());
                updateContent();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void updateContent()
    {
        tvMethodName.setText(filter.getEstimationMethod());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.home:
                onBackPressed();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        saveFilter();
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void saveFilter()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.filter_is_active_key), filter.isActive());
        editor.putString(getString(R.string.filter_estimation_method_key), filter.getEstimationMethod());
        editor.commit();
    }
}
