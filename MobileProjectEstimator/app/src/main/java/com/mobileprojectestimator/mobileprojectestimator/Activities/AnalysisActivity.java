package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database.DatabaseInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class AnalysisActivity extends DatabaseActivity
{

    private Spinner estimationMethodSpinner;
    private String selectedEstimationMethod;
    private ArrayAdapter<String> influencingFactorsAdapter;
    private ArrayAdapter<String> estimationMethodsAdapter;
    private Spinner influencingFactorSpinner;
    private String selectedInfluenceFactorSet;
    private ArrayList<DatabaseInfluenceFactorItem> influenceItems;
    private CombinedChart personDaysChart;

    private ArrayList<String> projectNames;
    private ArrayList<Project> selectedProjects;
    private boolean estimationMethodChanged;
    private boolean influenceFactorChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        initDatabase();
        projectNames = new ArrayList<>();
        selectedProjects = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAnalysis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initEstimationMethodSpinner();
        selectedEstimationMethod = estimationMethodSpinner.getItemAtPosition(0).toString();
        initInfluenceFactorSetSpinner();
        selectedInfluenceFactorSet =  influencingFactorSpinner.getItemAtPosition(0).toString();

        loadProjectData();

        personDaysChart = (CombinedChart) findViewById(R.id.personDaysChart);
        initPersonDaysChart();

    }

    private void loadProjectData()
    {
        selectedProjects = databaseHelper.loadProjectByEstimationMethodAndInfluenceSet(selectedEstimationMethod, selectedInfluenceFactorSet);
        for(Project p : selectedProjects){
            projectNames.add(p.getTitle());
        }
    }

    private void initPersonDaysChart()
    {
        personDaysChart.setDescription("");
        personDaysChart.setBackgroundColor(Color.WHITE);
        personDaysChart.setDrawGridBackground(false);
        personDaysChart.setDrawBarShadow(false);
        personDaysChart.setVisibleXRangeMaximum(50);

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

    private LineData loadLineDataTerminatedPersonDays() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < selectedProjects.size(); index++)
            entries.add(new Entry(selectedProjects.get(index).getFinalPersonDays(), index));

        LineDataSet set = new LineDataSet(entries, "Terminated Person Days");
        set.setColor(ContextCompat.getColor(this, R.color.even_darker_orange));
        set.setLineWidth(2.5f);
        set.setCircleColor(ContextCompat.getColor(this, R.color.dark_orange));
        //set.setCircleRadius(5f);
        set.setFillColor(ContextCompat.getColor(this, R.color.dark_orange));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(ContextCompat.getColor(this, R.color.even_darker_orange));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }

    private BarData loadBarDataEstimatedPersonDays() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int index = 0; index < selectedProjects.size(); index++)
            entries.add(new BarEntry((float) selectedProjects.get(index).getEvaluatedPersonDays(), index));

        BarDataSet set = new BarDataSet(entries, "Estimated Person Days");
        set.setColor(ContextCompat.getColor(this, R.color.light_blue));
        set.setValueTextColor(ContextCompat.getColor(this, R.color.dark_blue));
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }

    private void refreshAnalysis()
    {
        if (estimationMethodChanged){
            Log.d("INFO", "Project Analysis: Estimation Method Changed");
            estimationMethodChanged= false;
            initInfluenceFactorSetSpinner();
            selectedInfluenceFactorSet =  influencingFactorSpinner.getItemAtPosition(0).toString();
            loadLineDataTerminatedPersonDays();
            loadBarDataEstimatedPersonDays();
            loadProjectData();
            reloadChartData();
        } else if (influenceFactorChanged){
            influenceFactorChanged = false;
        }

    }

    private void reloadChartData()
    {
        CombinedData data = new CombinedData(projectNames);
        data.setData(loadLineDataTerminatedPersonDays());
        data.setData(loadBarDataEstimatedPersonDays());
        data.notifyDataChanged();
        personDaysChart.notifyDataSetChanged();
        personDaysChart.invalidate();
    }

    private void initInfluenceFactorSetSpinner()
    {
        influenceItems = databaseHelper.getInfluenceFactorItems(databaseHelper.getEstimationMethodId(selectedEstimationMethod));
        ArrayList<String> influenceItemNames = new ArrayList<>();
        for(DatabaseInfluenceFactorItem item : influenceItems){
            influenceItemNames.add(item.get_name());
        }
        influencingFactorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, influenceItemNames);
        influencingFactorSpinner = (Spinner) findViewById(R.id.spInfluenceFactorSet);
        influencingFactorSpinner.setAdapter(influencingFactorsAdapter);
        influencingFactorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedInfluenceFactorSet = influencingFactorSpinner.getItemAtPosition(position).toString();
                influenceFactorChanged = true;
                //refreshAnalysis();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }


    private void initEstimationMethodSpinner()
    {
        estimationMethodsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, databaseHelper.getEstimationMethodNames());
        estimationMethodSpinner = (Spinner) findViewById(R.id.spEstimationMethod);
        estimationMethodSpinner.setAdapter(estimationMethodsAdapter);
        estimationMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedEstimationMethod = estimationMethodSpinner.getItemAtPosition(position).toString();
                estimationMethodChanged = true;
                //refreshAnalysis();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}
