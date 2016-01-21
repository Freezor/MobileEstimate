package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class ExportProjectActivity extends AppCompatActivity
{

    private ArrayList<String> projectNames;
    private Button exportSettings;
    private TextView exportPdf;
    private TextView exportExcel;
    private Spinner projectsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarExport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View articlesLayout = findViewById(R.id.exportProjectLayout);

        exportSettings = (Button) articlesLayout.findViewById(R.id.bExportSettings);
        exportPdf = (TextView) articlesLayout.findViewById(R.id.tvExportPdf);
        exportExcel = (TextView) articlesLayout.findViewById(R.id.tvExportExcel);
        projectsSpinner = (Spinner) articlesLayout.findViewById(R.id.projectSpinner);

        Intent intent = getIntent();
        projectNames = intent.getStringArrayListExtra(getString(R.string.ProjectsNameList));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, projectNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectsSpinner.setAdapter(dataAdapter);

        exportSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showExportSettings();
            }
        });

        exportPdf.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                exportToPdf();
            }
        });

        exportExcel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                exportToExcel();
            }
        });
    }

    private void exportToExcel()
    {
        //TODO: Export the Project
        Toast.makeText(this, "Export Project to Excel", Toast.LENGTH_SHORT).show();
    }

    private void exportToPdf()
    {
        //TODO: Export the Project
        Toast.makeText(this, "Export Project to PDF", Toast.LENGTH_SHORT).show();
    }

    private void showExportSettings()
    {
        //TODO: Call the settings
        Toast.makeText(this, "Export Settings", Toast.LENGTH_SHORT).show();
    }
}
