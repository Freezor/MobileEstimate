package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.EstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.InputFilterMinMax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

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
    private TextView tvFinalPersonDays;
    private TextView tvEvaluatedPersonDays;
    private Project chosenProject;
    private View layout;
    private boolean isArrangementEvaluatedPoints;
    private Menu menu;
    private Double MAXDAYS=1999.0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isArrangementEvaluatedPoints = false;
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

        tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvFinalPersonDays = (TextView) findViewById(R.id.tvFinalPersonDays);
        tvEvaluatedPersonDays = (TextView) findViewById(R.id.tvEvaluatedPersonDays);
        layout = findViewById(R.id.analysis_layout);
        tvFinalPersonDays.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chosenProject != null)
                {
                    openChangeFinalPersonDays();
                }
            }
        });
        tvEvaluatedPersonDays.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chosenProject != null)
                {
                    openChangeEvaluatedPersonDaysDialog();
                }
            }
        });

        personDaysChart = (CombinedChart) findViewById(R.id.personDaysChart);
        initPersonDaysChart();
        personDaysChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight)
            {
                Log.d("INFO", "Selected Value: " + entry.toString() + " index: " + i + " highlight " + highlight.toString());
                chosenProject = selectedProjects.get(entry.getXIndex());
                tvProjectName.setText(chosenProject.getTitle());
                tvFinalPersonDays.setText(String.valueOf(chosenProject.getFinalPersonDays()));
                tvEvaluatedPersonDays.setText(String.valueOf(chosenProject.getEvaluatedPersonDays()));
            }

            @Override
            public void onNothingSelected()
            {

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            projectId = String.valueOf(extras.getInt(getString(R.string.ACTIVITY_EXTRA_PROJECTID)));
        }

        if (projectId == null || projectId.equals(""))
        {
            tvProjectName.setText("");
            tvFinalPersonDays.setText("");
            tvEvaluatedPersonDays.setText("");
        } else
        {
            boolean projectFound = false;
            for (Project p : selectedProjects)
            {
                if (p.getProjectId() == Integer.valueOf(projectId))
                {
                    projectFound = true;
                    chosenProject = p;
                    tvProjectName.setText(chosenProject.getTitle());
                    tvFinalPersonDays.setText(String.valueOf(chosenProject.getFinalPersonDays()));
                    tvEvaluatedPersonDays.setText(String.valueOf(chosenProject.getEvaluatedPoints()));
                    break;
                }
            }
            if (!projectFound)
            {
                tvProjectName.setText("");
                tvFinalPersonDays.setText("");
                tvEvaluatedPersonDays.setText("");
            }
        }
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private void openChangeFinalPersonDays()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_change_final_person_days_title));
        builder.setMessage(getString(R.string.dialog_change_final_person_days_message));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setSingleLine(true);
        InputFilterMinMax filter = new InputFilterMinMax(getBaseContext(),0.0, MAXDAYS);
        input.setFilters((new InputFilter[]{filter}));
        input.setText("" + chosenProject.getFinalPersonDays());
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (!isNumeric(input.getText().toString()) || Double.parseDouble(input.getText().toString()) < 0)
                {
                    chosenProject.setFinalPersonDays(0);

                } else
                {
                    chosenProject.setFinalPersonDays(Double.parseDouble(input.getText().toString()));

                }
                tvFinalPersonDays.setText(String.valueOf(chosenProject.getFinalPersonDays()));
                reEvaluatePersonDaysForAllProjects();
                for (Project p : selectedProjects)
                {
                    if (p.getProjectId() == chosenProject.getProjectId())
                    {
                        selectedProjects.remove(p);
                        selectedProjects.add(chosenProject);
                        Collections.sort(selectedProjects, new Comparator<Project>()
                        {
                            @Override
                            public int compare(Project p1, Project p2)
                            {
                                return Double.compare(p1.getEvaluatedPoints(), p2.getEvaluatedPoints());
                            }

                        });
                        break;
                    }
                }
                refreshCharData();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void reEvaluatePersonDaysForAllProjects()
    {
        ArrayList<Project> tempProjects = new ArrayList<>();
        for (Project p : selectedProjects)
        {
            if (p.getProjectId() != chosenProject.getProjectId())
            {
                int terminatedProject = databaseHelper.getAmountTerminatedFunctionPointProject();
                if (terminatedProject < 1)
                {
                    p.setEvaluatedPersonDays(databaseHelper.evaluateFunctionPointPersonDaysWithBaseProductivity(p));
                } else
                {
                    Project smaller = null;
                    Project bigger = null;
                    for (Project proj : selectedProjects)
                    {
                        if (proj.getEvaluatedPersonDays() < p.getEvaluatedPersonDays())
                        {
                            if (smaller == null)
                            {
                                smaller = proj;
                            } else if (smaller.getEvaluatedPersonDays() < proj.getEvaluatedPersonDays())
                            {
                                smaller = proj;
                            }
                        } else if (proj.getEvaluatedPersonDays() > p.getEvaluatedPersonDays())
                        {

                            if (bigger == null)
                            {
                                bigger = proj;
                            } else if (bigger.getEvaluatedPersonDays() > proj.getEvaluatedPersonDays())
                            {
                                bigger = proj;
                            }
                        }
                    }
                    if (smaller == null)
                    {
                        smaller = p;
                    }
                    if (bigger == null)
                    {
                        bigger = p;
                    }
                    double smallPointsPerDay = smaller.getEvaluatedPoints() / smaller.getEvaluatedPersonDays();
                    double bigPointsPerDay = bigger.getEvaluatedPoints() / bigger.getEvaluatedPersonDays();

                    double averagePointsPerDay = (smallPointsPerDay + bigPointsPerDay) / 2;
                    averagePointsPerDay = databaseHelper.roundDoubleTwoDecimals(averagePointsPerDay);

                    p.setEvaluatedPersonDays(databaseHelper.roundDoubleTwoDecimals(p.getEvaluatedPoints() / averagePointsPerDay));
                }
            }
            tempProjects.add(p);
        }
        int terminatedProject = databaseHelper.getAmountTerminatedFunctionPointProject();
        if (terminatedProject < 1)
        {
            chosenProject.setEvaluatedPersonDays(databaseHelper.evaluateFunctionPointPersonDaysWithBaseProductivity(chosenProject));
        } else
        {
            chosenProject.setEvaluatedPersonDays(databaseHelper.evaluateFunctionPointPersonDaysWithExistingProductivity(chosenProject));
        }
        selectedProjects = tempProjects;
    }

    private void openChangeEvaluatedPersonDaysDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_change_evaluated_person_days_title));
        builder.setMessage(getString(R.string.dialog_change_evaluated_person_days_message));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setSingleLine(true);
        InputFilterMinMax filter = new InputFilterMinMax(getBaseContext(),0.0, MAXDAYS);
        input.setFilters((new InputFilter[]{filter}));
        input.setText("" + chosenProject.getEvaluatedPersonDays());
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (!isNumeric(input.getText().toString()) || Double.parseDouble(input.getText().toString()) < 0)
                {
                    chosenProject.setEvaluatedPersonDays(0);
                } else
                {
                    chosenProject.setEvaluatedPersonDays(Double.parseDouble(input.getText().toString()));
                }
                tvEvaluatedPersonDays.setText(String.valueOf(chosenProject.getEvaluatedPersonDays()));
                for (Project p : selectedProjects)
                {
                    if (p.getProjectId() == chosenProject.getProjectId())
                    {
                        selectedProjects.remove(p);
                        selectedProjects.add(chosenProject);
                        Collections.sort(selectedProjects, new Comparator<Project>()
                        {
                            @Override
                            public int compare(Project p1, Project p2)
                            {
                                return Double.compare(p1.getEvaluatedPoints(), p2.getEvaluatedPoints());
                            }

                        });
                        break;
                    }
                }
                refreshCharData();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Load the Projects depending of the estimation method and chosen influence factor
     * <p/>
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
        //personDaysChart.setVisibleXRange(2, 6);

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

        data.setData(loadLineDataEvaluatedPoints());
        data.setData(loadBarDataEstimatedPersonDays());

        personDaysChart.setData(data);
        personDaysChart.invalidate();
    }

    private LineData loadLineDataEvaluatedPoints()
    {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < selectedProjects.size(); index++)
            entries.add(new Entry((float) selectedProjects.get(index).getEvaluatedPersonDays(), index));

        lineDataSet = new LineDataSet(entries, "Estimated Person Days");
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

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int index = 0; index < selectedProjects.size(); index++)
            entries.add(new BarEntry((float) selectedProjects.get(index).getEvaluatedPoints(), index));

        barDataSet = new BarDataSet(entries, "Evaluated Points");
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
        MenuItem arrangementMenuItem = menu.findItem(R.id.action_change_analysis_sort);
        arrangementMenuItem.setTitle(getString(R.string.action_change_analysis_sort_evaluated_points));
        isArrangementEvaluatedPoints = false;
    }

    private void refreshCharData()
    {
        for (Project p : selectedProjects)
        {
            if (p.getProjectId() == chosenProject.getProjectId())
            {
                selectedProjects.remove(p);
                selectedProjects.add(chosenProject);
                Collections.sort(selectedProjects, new Comparator<Project>()
                {
                    @Override
                    public int compare(Project p1, Project p2)
                    {
                        return Double.compare(p1.getEvaluatedPersonDays(), p2.getEvaluatedPersonDays());
                    }

                });
                projectNames = new ArrayList<>();
                for (Project proj : selectedProjects)
                {
                    projectNames.add(proj.getTitle());
                }
                break;
            }
        }

        CombinedData data = new CombinedData(projectNames);

        data.setData(loadLineDataEvaluatedPoints());
        data.setData(loadBarDataEstimatedPersonDays());
        personDaysChart.setData(data);

        personDaysChart.notifyDataSetChanged();
        personDaysChart.invalidate();

        MenuItem arrangementMenuItem = menu.findItem(R.id.action_change_analysis_sort);
        arrangementMenuItem.setTitle(getString(R.string.action_change_analysis_sort_evaluated_points));
        isArrangementEvaluatedPoints = false;

        layout.invalidate();
    }

    private void reloadChartData()
    {
        Log.d("Info", "Analysis Activity: reloadCharData");

        lineDataSet.clear();
        barDataSet.clear();
        loadProjectData();
        CombinedData data = new CombinedData(projectNames);

        data.setData(loadLineDataEvaluatedPoints());
        data.setData(loadBarDataEstimatedPersonDays());
        personDaysChart.setData(data);

        personDaysChart.notifyDataSetChanged();
        personDaysChart.invalidate();
        layout.invalidate();
    }

    private void initInfluenceFactorSets()
    {
        if (selectedEstimationMethod.isEmpty() || selectedEstimationMethod.equals(""))
        {
            selectedEstimationMethod = estimationMethods.get(0);
        }
        influenceItems = databaseHelper.getActiveInfluenceFactorItems(databaseHelper.getEstimationMethodId(selectedEstimationMethod));
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
                    influenceSetNames.add(0, "All");
                    final CharSequence[] items = influenceSetNames.toArray(new String[influenceSetNames.size()]);
                    builder.setItems(items, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int item)
                        {
                            selectedInfluenceFactorSet = items[item].toString();
                            tvInfluenceFactorSet.setText(selectedInfluenceFactorSet);
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
                    builder.setTitle(getString(R.string.dialog_change_technique_method_title));
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
        this.menu = menu;
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
            case R.id.action_generate_demo_values:
                generateDemoValues();
                return true;
            case R.id.action_open_statistics:
                openStatisticActivtiy();
                return true;
            case R.id.action_change_analysis_sort:
                changeDiagrammArrangement();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openStatisticActivtiy()
    {
        Intent i = new Intent(AnalysisActivity.this, StatisticsActivity.class);
        startActivity(i);
    }

    private void changeDiagrammArrangement()
    {
        MenuItem arrangementMenuItem = menu.findItem(R.id.action_change_analysis_sort);
        if (!isArrangementEvaluatedPoints)
        {
            isArrangementEvaluatedPoints = true;
            arrangementMenuItem.setTitle(getString(R.string.action_change_analysis_sort_estimated_days));
            Collections.sort(selectedProjects, new Comparator<Project>()
            {
                @Override
                public int compare(Project p1, Project p2)
                {
                    return Double.compare(p1.getEvaluatedPoints(), p2.getEvaluatedPoints());
                }

            });
            projectNames = new ArrayList<>();
            for (Project proj : selectedProjects)
            {
                projectNames.add(proj.getTitle());
            }
        } else
        {
            isArrangementEvaluatedPoints = false;
            arrangementMenuItem.setTitle(getString(R.string.action_change_analysis_sort_evaluated_points));
            Collections.sort(selectedProjects, new Comparator<Project>()
            {
                @Override
                public int compare(Project p1, Project p2)
                {
                    return Double.compare(p1.getEvaluatedPersonDays(), p2.getEvaluatedPersonDays());
                }

            });
            projectNames = new ArrayList<>();
            for (Project proj : selectedProjects)
            {
                projectNames.add(proj.getTitle());
            }
        }
        CombinedData data = new CombinedData(projectNames);

        data.setData(loadLineDataEvaluatedPoints());
        data.setData(loadBarDataEstimatedPersonDays());
        personDaysChart.setData(data);

        personDaysChart.notifyDataSetChanged();
        personDaysChart.invalidate();
        layout.invalidate();
    }

    private void generateDemoValues()
    {
        tvProjectName.setText("");
        tvFinalPersonDays.setText("");
        tvEvaluatedPersonDays.setText("");

        selectedProjects = new ArrayList<>();

        Random r = new Random();
        int maxPoint = 4000;
        int maxDays = 200;

        for (int i = 0; i < 40; i++)
        {
            int min = r.nextInt((i + 60) - 20 + 1) + 20;

            Project p = new Project(this);
            p.setProjectId(i);
            p.setTitle("Project " + i);
            p.setEvaluatedPoints(r.nextInt(maxPoint - min + 1) + min);
            int evaluatedDays = r.nextInt(maxDays - min + 1) + min;
            double fakt = r.nextInt(80 - 5 + 1) + 5;
            fakt = (fakt / 100) + 1;
            p.setEstimationMethod(getString(R.string.estimation_technique_function_point));
            p.initialiseEstimationItems(getString(R.string.estimation_technique_function_point));

            int minEstimationItemValue = 0;
            int maxEstimationItemValueSimple = 30;
            int maxEstimationItemValueMedium = 10;
            int maxEstimationItemValueComplex = 5;
            for (EstimationItem item : p.getEstimationItems())
            {
                //int minVal = r.nextInt(maxEstimationItemValue - minEstimationItemValue + 1) + minEstimationItemValue;
                ((FunctionPointItem) item).setSimpleValue(r.nextInt(maxEstimationItemValueSimple - minEstimationItemValue + 1) + minEstimationItemValue);
                //((FunctionPointItem) item).setSimpleValue(r.nextInt(evaluatedDays - minVal + 1) + minVal);
                ((FunctionPointItem) item).setMediumValue(r.nextInt(maxEstimationItemValueMedium - minEstimationItemValue + 1) + minEstimationItemValue);
                //((FunctionPointItem) item).setMediumValue(r.nextInt(evaluatedDays - minVal + 1) + minVal);
                ((FunctionPointItem) item).setComplexValue(r.nextInt(maxEstimationItemValueComplex - minEstimationItemValue + 1) + minEstimationItemValue);
                //((FunctionPointItem) item).setComplexValue(r.nextInt(evaluatedDays - minVal + 1) + minVal);
            }
            p.setInfluencingFactor(databaseHelper.loadInfluenceFactorById(2005));
            p.setEvaluatedPoints(p.getEvaluatedPoints());
            int terminatedProject = databaseHelper.getAmountTerminatedFunctionPointProject();
            if (terminatedProject < 1)
            {
                p.setEvaluatedPersonDays(databaseHelper.evaluateFunctionPointPersonDaysWithBaseProductivity(p));
            } else
            {
                p.setEvaluatedPersonDays(databaseHelper.evaluateFunctionPointPersonDaysWithExistingProductivity(p));
            }
            p.setFinalPersonDays(p.getEvaluatedPersonDays() * fakt);

            selectedProjects.add(p);
        }

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

        tvEstimationMethod.setText(getString(R.string.estimation_technique_function_point));
        tvInfluenceFactorSet.setText("Demo Factor");
        chosenProject = selectedProjects.get(0);
        refreshAnalysis();
        refreshCharData();
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_open_statistics);
        return true;
    }
}
