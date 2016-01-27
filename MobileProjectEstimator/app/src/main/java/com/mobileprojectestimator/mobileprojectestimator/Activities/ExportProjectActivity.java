package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExportProjectActivity extends DatabaseActivity
{

    private ArrayList<String> projectNames;
    private Button exportExcel;
    private Spinner projectsSpinner;
    private ArrayList<Project> projects;
    private Project selectedProject;
    private File outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_project);

        initDatabase();

        projects = databaseHelper.getAllActiveProjects(this);
        selectedProject = projects.get(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarExport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View articlesLayout = findViewById(R.id.exportProjectLayout);

        exportExcel = (Button) articlesLayout.findViewById(R.id.bExportExcel);
        projectsSpinner = (Spinner) articlesLayout.findViewById(R.id.projectSpinner);

        projectNames = new ArrayList<>();
        for (Project p : projects)
        {
            projectNames.add(p.getTitle());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, projectNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectsSpinner.setAdapter(dataAdapter);
        projectsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selected = parent.getItemAtPosition(position).toString();
                for (Project p : projects)
                {
                    if (p.getTitle().equals(selected))
                    {
                        selectedProject = p;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

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
        selectedProject = databaseHelper.loadProjectById(this, String.valueOf(selectedProject.getProjectId()));

        if (selectedProject.getEstimationMethod().equals(getString(R.string.estimation_method_function_point)))
        {
            if(generateFunctionPointExcel()){
                Toast.makeText(this, String.format(getString(R.string.exported_project_toast), selectedProject.getTitle()), Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                //i.setDataAndType(Uri.fromFile(outputFile), "text/csv");
                i.setDataAndType(Uri.fromFile(outputFile), "application/vnd.ms-excel");
                startActivity(i);
            }
        } else if (selectedProject.getEstimationMethod().equals(getString(R.string.estimation_method_cocomo)))
        {
            if(generateCocomoPointExcel()){
                Toast.makeText(this, String.format(getString(R.string.exported_project_toast), selectedProject.getTitle()), Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                //i.setDataAndType(Uri.fromFile(outputFile), "text/csv");
                i.setDataAndType(Uri.fromFile(outputFile), "application/vnd.ms-excel");
                startActivity(i);
            }
        } else if (selectedProject.getEstimationMethod().equals(getString(R.string.estimation_method_cocomo_2)))
        {
            if(generateCocomo2PointExcel()){
                Toast.makeText(this, String.format(getString(R.string.exported_project_toast), selectedProject.getTitle()), Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                //i.setDataAndType(Uri.fromFile(outputFile), "text/csv");
                i.setDataAndType(Uri.fromFile(outputFile), "application/vnd.ms-excel");
                startActivity(i);
            }
        } else
        {
            Toast.makeText(this, String.format(getString(R.string.exported_project_error_toast), selectedProject.getTitle()), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean generateCocomoPointExcel()
    {
        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.e("ERROR", "Storage not available or read only");
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle headerColumn = wb.createCellStyle();
        headerColumn.setFillForegroundColor(HSSFColor.TURQUOISE.index);
        headerColumn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        int rowCount = 0;

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(selectedProject.getEstimationMethod() + " Estimation");

        // Generate column headings
        Row row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Cocomo Estimation");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue("NOT IMPLEMENTED YET");
        c.setCellStyle(headerColumn);

        sheet1.setColumnWidth(0, (15 * 250));
        sheet1.setColumnWidth(1, (15 * 250));


        //~~~~~CREATE SHEET 2~~~~~~~\\
        rowCount = 0;

        //New Sheet
        Sheet sheet2 = null;
        sheet2 = wb.createSheet("Project Properties");

        // Generate column headings
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Project Name");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getTitle());

        //ROW 2
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Description");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectDescription());

        //ROW 3
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Development Market");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getMarket());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Development Kind");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getDevelopmentKind());

        //ROW 5
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Process Methology");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getProcessMethology());

        //ROW 6
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Programming Language");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getProgrammingLanguage());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Platform");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getPlatform());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Industry Sector");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getIndustrySector());


        sheet2.setColumnWidth(0, (15 * 400));
        sheet2.setColumnWidth(1, (15 * 400));
        sheet2.setColumnWidth(2, (15 * 400));
        sheet2.setColumnWidth(3, (15 * 400));
        sheet2.setColumnWidth(4, (15 * 400));

        // Create a path where we will place our List of objects on external storage
        outputFile = new File(getExternalFilesDir(null), selectedProject.getTitle() + ".xls");

        FileOutputStream os = null;

        try
        {
            os = new FileOutputStream(outputFile);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + outputFile);
            success = true;
        } catch (IOException e)
        {
            Log.w("FileUtils", "Error writing " + outputFile, e);
        } catch (Exception e)
        {
            Log.w("FileUtils", "Failed to save file", e);
        } finally
        {
            try
            {
                if (null != os)
                    os.close();
            } catch (Exception ex)
            {
            }
        }
        return success;
    }

    private boolean generateCocomo2PointExcel()
    {
        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.e("ERROR", "Storage not available or read only");
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle headerColumn = wb.createCellStyle();
        headerColumn.setFillForegroundColor(HSSFColor.TURQUOISE.index);
        headerColumn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        int rowCount = 0;

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(selectedProject.getEstimationMethod() + " Estimation");

        // Generate column headings
        Row row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Cocomo 2 Estimation");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue("NOT IMPLEMENTED YET");
        c.setCellStyle(headerColumn);

        sheet1.setColumnWidth(0, (15 * 250));
        sheet1.setColumnWidth(1, (15 * 250));


        //~~~~~CREATE SHEET 2~~~~~~~\\
        rowCount = 0;

        //New Sheet
        Sheet sheet2 = null;
        sheet2 = wb.createSheet("Project Properties");

        // Generate column headings
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Project Name");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getTitle());

        //ROW 2
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Description");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectDescription());

        //ROW 3
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Development Market");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getMarket());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Development Kind");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getDevelopmentKind());

        //ROW 5
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Process Methology");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getProcessMethology());

        //ROW 6
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Programming Language");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getProgrammingLanguage());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Platform");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getPlatform());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Industry Sector");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getIndustrySector());


        sheet2.setColumnWidth(0, (15 * 400));
        sheet2.setColumnWidth(1, (15 * 400));
        sheet2.setColumnWidth(2, (15 * 400));
        sheet2.setColumnWidth(3, (15 * 400));
        sheet2.setColumnWidth(4, (15 * 400));

        // Create a path where we will place our List of objects on external storage
        outputFile = new File(getExternalFilesDir(null), selectedProject.getTitle() + ".xls");

        FileOutputStream os = null;

        try
        {
            os = new FileOutputStream(outputFile);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + outputFile);
            success = true;
        } catch (IOException e)
        {
            Log.w("FileUtils", "Error writing " + outputFile, e);
        } catch (Exception e)
        {
            Log.w("FileUtils", "Failed to save file", e);
        } finally
        {
            try
            {
                if (null != os)
                    os.close();
            } catch (Exception ex)
            {
            }
        }
        return success;
    }

    private boolean generateFunctionPointExcel()
    {
        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.e("ERROR", "Storage not available or read only");
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle headerColumn = wb.createCellStyle();
        headerColumn.setFillForegroundColor(HSSFColor.TURQUOISE.index);
        headerColumn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        int rowCount = 0;

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(selectedProject.getEstimationMethod() + " Estimation");

        // Generate column headings
        Row row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Category");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue("Amount");
        c.setCellStyle(headerColumn);

        c = row.createCell(2);
        c.setCellValue("Classification");
        c.setCellStyle(headerColumn);

        c = row.createCell(3);
        c.setCellValue("Weight");
        c.setCellStyle(headerColumn);

        c = row.createCell(4);
        c.setCellValue("Sum of Row");
        c.setCellStyle(headerColumn);

        sheet1.setColumnWidth(0, (15 * 250));
        sheet1.setColumnWidth(1, (15 * 250));
        sheet1.setColumnWidth(2, (15 * 250));
        sheet1.setColumnWidth(3, (15 * 250));
        sheet1.setColumnWidth(4, (15 * 250));


        //INPUT DATA
        FunctionPointItem inputData = (FunctionPointItem) selectedProject.getEstimationItems().get(0);

        //INPUT DATA Row 1
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Input Data");

        c = row.createCell(1);
        c.setCellValue(inputData.getSimpleValue());

        c = row.createCell(2);
        c.setCellValue("simple");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(inputData.getSimpleValue() * 3);

        //INPUT DATA Row 2
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(inputData.getMediumValue());

        c = row.createCell(2);
        c.setCellValue("medium");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(inputData.getMediumValue() * 4);

        //INPUT DATA Row 3
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(inputData.getComplexValue());

        c = row.createCell(2);
        c.setCellValue("complex");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(inputData.getComplexValue() * 6);

        //REQUEST DATA
        FunctionPointItem requestData = (FunctionPointItem) selectedProject.getEstimationItems().get(1);

        //REQUEST DATA Row 1
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Request Data");

        c = row.createCell(1);
        c.setCellValue(requestData.getSimpleValue());

        c = row.createCell(2);
        c.setCellValue("simple");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(requestData.getSimpleValue() * 3);

        //REQUEST DATA Row 2
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(requestData.getMediumValue());

        c = row.createCell(2);
        c.setCellValue("medium");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(requestData.getMediumValue() * 4);

        //REQUEST DATA Row 3
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(requestData.getComplexValue());

        c = row.createCell(2);
        c.setCellValue("complex");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(requestData.getComplexValue() * 6);

        //OUTPUT DATA
        FunctionPointItem outputData = (FunctionPointItem) selectedProject.getEstimationItems().get(2);

        //OUTPUT DATA Row 1
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Output Data");

        c = row.createCell(1);
        c.setCellValue(outputData.getSimpleValue());

        c = row.createCell(2);
        c.setCellValue("simple");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(outputData.getSimpleValue() * 4);

        //OUTPUT DATA Row 2
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(outputData.getMediumValue());

        c = row.createCell(2);
        c.setCellValue("medium");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(outputData.getMediumValue() * 5);

        //OUTPUT DATA Row 3
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(outputData.getComplexValue());

        c = row.createCell(2);
        c.setCellValue("complex");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(outputData.getComplexValue() * 7);

        //DATASET DATA
        FunctionPointItem datasetData = (FunctionPointItem) selectedProject.getEstimationItems().get(3);

        //DATASET DATA Row 1
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Output Data");

        c = row.createCell(1);
        c.setCellValue(datasetData.getSimpleValue());

        c = row.createCell(2);
        c.setCellValue("simple");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(datasetData.getSimpleValue() * 7);

        //DATASET DATA Row 2
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(datasetData.getMediumValue());

        c = row.createCell(2);
        c.setCellValue("medium");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(datasetData.getMediumValue() * 10);

        //DATASET DATA Row 3
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(datasetData.getComplexValue());

        c = row.createCell(2);
        c.setCellValue("complex");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(datasetData.getComplexValue() * 15);


        //REFERENCE DATA
        FunctionPointItem referenceData = (FunctionPointItem) selectedProject.getEstimationItems().get(4);

        //REFERENCE DATA Row 1
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Output Data");

        c = row.createCell(1);
        c.setCellValue(referenceData.getSimpleValue());

        c = row.createCell(2);
        c.setCellValue("simple");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(referenceData.getSimpleValue() * 5);

        //REFERENCE DATA Row 2
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(referenceData.getMediumValue());

        c = row.createCell(2);
        c.setCellValue("medium");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(referenceData.getMediumValue() * 7);

        //REFERENCE DATA Row 3
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(1);
        c.setCellValue(referenceData.getComplexValue());

        c = row.createCell(2);
        c.setCellValue("complex");

        c = row.createCell(3);
        c.setCellValue("3");

        c = row.createCell(4);
        c.setCellValue(referenceData.getComplexValue() * 10);

        //Sum Row
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Sum");

        c = row.createCell(3);
        c.setCellValue("E1");

        int totalPoints = 0;
        for (FunctionPointItem item : this.selectedProject.getFunctionPointItems())
        {
            totalPoints += item.getTotalAmount();
        }
        c = row.createCell(4);
        c.setCellValue(totalPoints);

        //Influence Factor
        InfluencingFactor factor = selectedProject.getInfluencingFactor();

        //Influence Factor Row 1
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Influence Factors");

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_integration));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(0).getChosenValue());

        //Influence Factor Row 2
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_local_data));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(1).getChosenValue());

        //Influence Factor Row 3
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_transaction_rate));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(2).getChosenValue());

        //Influence Factor Row 4
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_processing_logic));

        //Influence Factor Row 5
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_arithmetic_operation));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(0).getChosenValue());

        //Influence Factor Row 6
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_control_procedure));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(1).getChosenValue());

        //Influence Factor Row 6
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_exception_regulation));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(2).getChosenValue());

        //Influence Factor Row 8
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_logic));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(3).getSubInfluenceFactorItemsList().get(3).getChosenValue());

        //Influence Factor Row 9
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_reusability));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(4).getChosenValue());

        //Influence Factor Row 10
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_stock_conversion));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(5).getChosenValue());

        //Influence Factor Row 10
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(2);
        c.setCellValue(getString(R.string.function_point_influence_factor_item_adaptability));

        c = row.createCell(4);
        c.setCellValue(factor.getInfluenceFactorItems().get(6).getChosenValue());

        //Influence Sum Row
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Sum of Influences");

        c = row.createCell(2);
        c.setCellValue("E2");

        c = row.createCell(4);
        c.setCellValue(selectedProject.getInfluencingFactor().getSumOfInfluences());

        //Influence Evaluation Row
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Influence Factor Evaluation");

        c = row.createCell(2);
        c.setCellValue("E3");

        c = row.createCell(4);
        c.setCellValue(selectedProject.getFactorInfluenceRating());

        //Evaluated Function Points Row
        row = sheet1.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Evaluated Function Points");

        c = row.createCell(1);
        c.setCellValue("E1 * E3");

        double erg = (double) totalPoints * this.selectedProject.getFactorInfluenceRating();
        erg = (double) Math.round(erg * 10000d) / 10000d;

        c = row.createCell(4);
        c.setCellValue(selectedProject.getFactorInfluenceRating());

        //~~~~~CREATE SHEET 2~~~~~~~\\
        rowCount = 0;

        //New Sheet
        Sheet sheet2 = null;
        sheet2 = wb.createSheet("Project Properties");

        // Generate column headings
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Project Name");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getTitle());

        //ROW 2
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Description");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectDescription());

        //ROW 3
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Development Market");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getMarket());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Development Kind");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getDevelopmentKind());

        //ROW 5
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Process Methology");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getProcessMethology());

        //ROW 6
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Programming Language");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getProgrammingLanguage());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Platform");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getPlatform());

        //ROW 4
        row = sheet2.createRow(rowCount);
        rowCount++;

        c = row.createCell(0);
        c.setCellValue("Industry Sector");
        c.setCellStyle(headerColumn);

        c = row.createCell(1);
        c.setCellValue(selectedProject.getProjectProperties().getIndustrySector());


        sheet2.setColumnWidth(0, (15 * 400));
        sheet2.setColumnWidth(1, (15 * 400));
        sheet2.setColumnWidth(2, (15 * 400));
        sheet2.setColumnWidth(3, (15 * 400));
        sheet2.setColumnWidth(4, (15 * 400));

        // Create a path where we will place our List of objects on external storage
        outputFile = new File(getExternalFilesDir(null), selectedProject.getTitle() + ".xls");

        FileOutputStream os = null;

        try
        {
            os = new FileOutputStream(outputFile);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + outputFile);
            success = true;
        } catch (IOException e)
        {
            Log.w("FileUtils", "Error writing " + outputFile, e);
        } catch (Exception e)
        {
            Log.w("FileUtils", "Failed to save file", e);
        } finally
        {
            try
            {
                if (null != os)
                    os.close();
            } catch (Exception ex)
            {
            }
        }
        return success;
    }


    public static boolean isExternalStorageReadOnly()
    {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState))
        {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable()
    {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState))
        {
            return true;
        }
        return false;
    }

}
