package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database.DatabaseInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AnalysisActivity extends DatabaseActivity
{
    private String selectedEstimationMethod;
    private String selectedInfluenceFactorSet;
    private ArrayList<DatabaseInfluenceFactorItem> influenceItems;
    private ArrayList<String> estimationMethods;
    private CombinedChart personDaysChart;

    private ArrayList<String> projectNames;
    private ArrayList<Project> selectedProjects;
    private boolean estimationMethodChanged;
    private boolean influenceFactorChanged;
    private TextView tvInfluenceFactorSet;
    private ArrayList<String> influenceSetNames;
    private TextView tvEstimationMethod;
    private LineDataSet lineDataSet;
    private BarDataSet barDataSet;
    private String projectId;
    private TextView tvProjectName;
    private TextView tvFunctionPoints;
    private TextView tvPersonDays;
    private Project chosenProject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        projectId = "";

        initDatabase();
        projectNames = new ArrayList<>();
        selectedProjects = new ArrayList<>();

        selectedEstimationMethod = "";
        selectedInfluenceFactorSet = "";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAnalysis);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initEstimationMethods();
        initInfluenceFactorSets();
        selectedInfluenceFactorSet = influenceItems.get(0).get_name();

        loadProjectData();

        personDaysChart = (CombinedChart) findViewById(R.id.personDaysChart);
        initPersonDaysChart();
        personDaysChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight)
            {
                Log.d("INFO","Selected Value: "+entry.toString()+" index: "+i+ " highlight "+highlight.toString());
                chosenProject = selectedProjects.get(entry.getXIndex());
                tvProjectName.setText(chosenProject.getTitle());
                tvFunctionPoints.setText(String.valueOf(chosenProject.getEvaluatedPoints()));
                tvPersonDays.setText(String.valueOf(chosenProject.getFinalPersonDays()));
            }

            @Override
            public void onNothingSelected()
            {

            }
        });

        tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvFunctionPoints = (TextView) findViewById(R.id.tvFunctionPoints);
        tvPersonDays = (TextView) findViewById(R.id.tvPersonDays);

        Intent intent = getIntent();
        projectId = intent.getStringExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID));
        if(projectId == null || projectId.equals("")){
            tvProjectName.setText("");
            tvFunctionPoints.setText("");
            tvPersonDays.setText("");
        } else {
            boolean projectFound=false;
            for(Project p: selectedProjects){
                if(p.getProjectId() == Integer.valueOf(projectId)){
                    projectFound = true;
                    chosenProject = p;
                    tvProjectName.setText(chosenProject.getTitle());
                    tvFunctionPoints.setText(String.valueOf(chosenProject.getEvaluatedPoints()));
                    tvPersonDays.setText(String.valueOf(chosenProject.getFinalPersonDays()));
                    break;
                }
            }
            if(!projectFound){
                tvProjectName.setText("");
                tvFunctionPoints.setText("");
                tvPersonDays.setText("");
            }
        }
    }

    /**
     * Load the Projects depending of the estimation method and chosen influence factor
     *
     * Sorts the projects after the estimated days
     */
    private void loadProjectData()
    {
        projectNames = new ArrayList<>();
        if (selectedEstimationMethod.isEmpty() || selectedEstimationMethod.equals(""))
        {
            selectedEstimationMethod = estimationMethods.get(0);
        }
        if (selectedInfluenceFactorSet.isEmpty() || selectedInfluenceFactorSet.equals(""))
        {
            selectedInfluenceFactorSet = influenceItems.get(0).get_name();
        }
        Log.d("INFO", "AnalysisActivity: loadProjectData: selectedEstimationMethod " + selectedEstimationMethod + " selectedInfluenceFactor " + selectedInfluenceFactorSet);
        selectedProjects = databaseHelper.loadActiveProjectsByEstimationMethodAndInfluenceSet(selectedEstimationMethod, selectedInfluenceFactorSet);
        Collections.sort(selectedProjects, new Comparator<Project>()
        {
            @Override
            public int compare(Project p1, Project p2)
            {
                return Double.compare(p1.getEvaluatedPoints(), p2.getEvaluatedPoints());
            }

        });
        for (Project p : selectedProjects)
        {
            projectNames.add(p.getTitle());
        }
        Log.d("INFO", "AnalysisActivity: loadProjectData: Number of loaded Projects: " + selectedProjects.size());
    }

    private void initPersonDaysChart()
    {
        personDaysChart.setDescription("");
        personDaysChart.setBackgroundColor(Color.WHITE);
        personDaysChart.setDrawGridBackground(false);
        personDaysChart.setDrawBarShadow(false);
        personDaysChart.setVisibleXRange(2, 6);

        // draw bars behind lines
        personDaysChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        YAxis rightAxis = personDaysChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = personDaysChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        XAxis xAxis = personDaysChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        CombinedData data = new CombinedData(projectNames);

        data.setData(loadLineDataTerminatedPersonDays());
        data.setData(loadBarDataEstimatedPersonDays());

        personDaysChart.setData(data);
        personDaysChart.invalidate();
    }

    private LineData loadLineDataTerminatedPersonDays()
    {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < selectedProjects.size(); index++)
            entries.add(new Entry(selectedProjects.get(index).getFinalPersonDays(), index));

        lineDataSet = new LineDataSet(entries, "Terminated Person Days");
        lineDataSet.setColor(ContextCompat.getColor(this, R.color.even_darker_orange));
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(ContextCompat.getColor(this, R.color.dark_orange));
        //set.setCircleRadius(5f);
        lineDataSet.setFillColor(ContextCompat.getColor(this, R.color.dark_orange));
        lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(ContextCompat.getColor(this, R.color.even_darker_orange));

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(lineDataSet);

        return d;
    }

    private BarData loadBarDataEstimatedPersonDays()
    {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int index = 0; index < selectedProjects.size(); index++)
            entries.add(new BarEntry((float) selectedProjects.get(index).getEvaluatedPersonDays(), index));

        barDataSet = new BarDataSet(entries, "Estimated Person Days");
        barDataSet.setColor(ContextCompat.getColor(this, R.color.light_blue));
        barDataSet.setValueTextColor(ContextCompat.getColor(this, R.color.dark_blue));
        barDataSet.setValueTextSize(10f);
        d.addDataSet(barDataSet);

        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }

    private void refreshAnalysis()
    {
        lineDataSet.clear();
        barDataSet.clear();
        if (estimationMethodChanged)
        {
            Log.d("INFO", "Project Analysis: Estimation Method Changed");
            estimationMethodChanged = false;
            initInfluenceFactorSets();
            tvEstimationMethod.setText(selectedEstimationMethod);
            loadProjectData();
            reloadChartData();
        } else if (influenceFactorChanged)
        {
            Log.d("INFO", "Project Analysis: Influence Set Changed");
            influenceFactorChanged = false;
            loadProjectData();
            reloadChartData();
        }

    }

    private void reloadChartData()
    {
        Log.d("Info", "Analysis Activity: reloadCharData");

        lineDataSet.clear();
        barDataSet.clear();
        loadProjectData();
        initPersonDaysChart();
        personDaysChart.notifyDataSetChanged();
        personDaysChart.invalidate();

        /*personDaysChart.clear();
        CombinedData data = new CombinedData(projectNames);
        data.setData(loadLineDataTerminatedPersonDays());
        data.setData(loadBarDataEstimatedPersonDays());
        initPersonDaysChart();
        personDaysChart.notifyDataSetChanged();
        personDaysChart.invalidate();*/
    }

    private void initInfluenceFactorSets()
    {
        if (selectedEstimationMethod.isEmpty() || selectedEstimationMethod.equals(""))
        {
            selectedEstimationMethod = estimationMethods.get(0);
        }
        influenceItems = databaseHelper.getInfluenceFactorItems(databaseHelper.getEstimationMethodId(selectedEstimationMethod));
        if (tvInfluenceFactorSet == null)
        {
            tvInfluenceFactorSet = (TextView) findViewById(R.id.tvInfluenceFactorSet);
            tvInfluenceFactorSet.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AnalysisActivity.this);
                    builder.setTitle(getString(R.string.dialog_change_influence_Factor_title));
                    builder.setCancelable(false);
                    builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // User cancelled the dialog
                        }
                    });
                    influenceSetNames = new ArrayList<String>();
                    for (DatabaseInfluenceFactorItem item : influenceItems)
                    {
                        influenceSetNames.add(item.get_name());
                    }
                    final CharSequence[] items = influenceSetNames.toArray(new String[influenceSetNames.size()]);
                    builder.setItems(items, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int item)
                        {
                            selectedInfluenceFactorSet = items[item].toString();
                            influenceFactorChanged = true;
                            refreshAnalysis();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
        if (selectedInfluenceFactorSet.isEmpty() || !influenceItems.contains(selectedInfluenceFactorSet))
        {
            selectedInfluenceFactorSet = influenceItems.get(0).get_name();
        }
        tvInfluenceFactorSet.setText(selectedInfluenceFactorSet);
    }


    private void initEstimationMethods()
    {
        estimationMethods = databaseHelper.getEstimationMethodNames();
        if (tvEstimationMethod == null)
        {
            tvEstimationMethod = (TextView) findViewById(R.id.tvEstimationMethod);
            tvEstimationMethod.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AnalysisActivity.this);
                    builder.setTitle(getString(R.string.dialog_change_estimation_method_title));
                    builder.setCancelable(false);
                    builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // User cancelled the dialog
                        }
                    });
                    final CharSequence[] items = estimationMethods.toArray(new String[estimationMethods.size()]);
                    builder.setItems(items, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int item)
                        {
                            selectedEstimationMethod = items[item].toString();
                            estimationMethodChanged = true;
                            refreshAnalysis();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
        if (selectedEstimationMethod.isEmpty())
        {
            selectedEstimationMethod = estimationMethods.get(0);
        }
        tvEstimationMethod.setText(selectedEstimationMethod);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_analysis, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_open_statistics);
        return true;
    }
}
