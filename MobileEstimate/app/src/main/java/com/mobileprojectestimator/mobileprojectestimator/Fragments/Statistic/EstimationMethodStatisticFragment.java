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
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Statistic.EstimatedProjectItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 21.01.2016, 09:53.
 * Project: MobileProjectEstimator
 */
public class EstimationMethodStatisticFragment extends StatisticFragment
{
    private PieChart mChart;

    public StatisticFragment reloadStatistic()
    {
        final ProjectStatisticFragment fragment = new ProjectStatisticFragment();
        initDatabase();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_statistic_estimation_projects, container, false);
        final View myLayout = rootView.findViewById(R.id.buttonPanel);
        final ImageView dot1 = (ImageView) myLayout.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);

        final TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.fragment_statistic_estimation_projects_title);

        mChart = (PieChart) rootView.findViewById(R.id.estimationMethodProjectsChart);
        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Projects Estimation Technique Share");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(false);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h)
            {
                // display msg when value selected
                if (e == null)
                    //noinspection UnnecessaryReturnStatement
                    return;
            }

            @Override
            public void onNothingSelected()
            {

            }
        });

        // add data
        addData();

        // customize legends
        final Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        return rootView;
    }

    private void addData()
    {
        initDatabase();
        final ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        final ArrayList<EstimatedProjectItem> values = databaseHelper.loadProjectsEstimationMethodStatistic();
        final ArrayList<String> xVals = new ArrayList<String>();

        boolean zeroValues = false;

        final int values_size = values.size();// Moved  values.size() call out of the loop to local variable values_size
        for (int i = 0; i < values_size; i++)
        {
            if (values.get(i).getNumberOfProjects() > 0)
            {
                yVals1.add(new Entry(values.get(i).getNumberOfProjects(), i));
                xVals.add(values.get(i).getEstimationMethod());
            } else
            {
                zeroValues = true;
            }
        }

        if (zeroValues)
        {
            yVals1.add(new Entry(0, yVals1.size() + 1));
            xVals.add("Other");
        }

        // create pie data set
        final PieDataSet dataSet = new PieDataSet(yVals1, "Estimation Methods");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        final ArrayList<Integer> colors = new ArrayList<Integer>();

        for (final int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (final int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (final int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (final int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (final int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        final PieData data = new PieData(xVals, dataSet);
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
