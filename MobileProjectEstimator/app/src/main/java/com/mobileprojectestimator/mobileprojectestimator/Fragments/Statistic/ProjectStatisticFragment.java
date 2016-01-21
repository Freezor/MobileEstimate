package com.mobileprojectestimator.mobileprojectestimator.Fragments.Statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 21.01.2016, 09:53.
 * Project: MobileProjectEstimator
 */
public class ProjectStatisticFragment extends StatisticFragment
{
    private View rootView;
    private TextView tvTitle;
    private PieChart mChart;

    @Override
    public StatisticFragment reloadStatistic()
    {
        ProjectStatisticFragment fragment = new ProjectStatisticFragment();
        initDatabase();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        initDatabase();

        rootView = inflater.inflate(R.layout.fragment_statistic_estimation_projects, container, false);
        View myLayout = rootView.findViewById(R.id.buttonPanel);
        ImageView dot2 = (ImageView) myLayout.findViewById(R.id.dot2);
        dot2.setBackgroundResource(R.drawable.circle_blue);

        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.fragment_statistic_projects_title);

        mChart = (PieChart) rootView.findViewById(R.id.estimationMethodProjectsChart);

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Active And Terminated Projects Share");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        return rootView;
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        initDatabase();
        ArrayList<Integer> projects = databaseHelper.loadActiveAndTerminatedProjectsStatistic();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < projects.size(); i++)
        {
            yVals1.add(new Entry(projects.get(i), i));
        }
        xVals.add("Active");
        xVals.add("Terminated");

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Project Status");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }
}