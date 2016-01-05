package com.mobileprojectestimator.mobileprojectestimator.Util.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database.DatabaseInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.EstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.ProjectProperties;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 15.12.2015, 11:33.
 * Project: MobileProjectEstimator
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    /**
     * The Android's default system path of your application database.
     */
    private static String DB_PATH = "/data/data/com.mobileprojectestimator.mobileprojectestimator/databases/";

    /**
     * The name of the database
     */
    private static String DB_NAME = "projectEstimationDatabase";

    /**
     * The database Object
     */
    private SQLiteDatabase projectEstimationDataBase;

    private final Context context;

    public static HashMap<String, Integer> resourcesIdMap;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        if (DataBaseHelper.resourcesIdMap == null)
        {
            DataBaseHelper.resourcesIdMap = new HashMap<>();
        }
        this.context = context;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        boolean dbExist = checkDataBase();

        if (!dbExist)
        {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.

            //TODO: This throws error here
            //this.getWritableDatabase();

            try
            {

                copyDataBase();

            } catch (IOException e)
            {
                Log.d("Error", "Database Creation Error: " + e.getCause());
                throw new Error("Error copying database");

            }
        } else
        {
            Log.d("Info", "Database Already exists");
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e)
        {

            Log.d("ERROR", "DATABASE DOES NOT EXIST!");

        }

        if (checkDB != null)
        {

            checkDB.close();

        }

        return checkDB != null;
    }

    /**
     * Opens the database from the file system. Afterwards database is ready for editing
     *
     * @throws SQLException
     */
    public void openDataBase() throws SQLException
    {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        projectEstimationDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException
    {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /**
     * @throws IOException
     */
    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if (!dbExist)
        {
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            } catch (IOException e)
            {
                Log.d("ERROR", "Database could not be copied " + e.getCause());
                throw new Error("Database could not be copied");
            }
        }
    }

    @Override
    public synchronized void close()
    {

        if (projectEstimationDataBase != null)
            projectEstimationDataBase.close();

        super.close();

    }

    /**
     * Output Logcat information about the database
     */
    public void logDatabaseInformation()
    {
        checkDatabaseCreation();
        Log.d("Info", String.format("Database Name: %s", DB_NAME));
        Log.d("Info", String.format("Database Version: %s", projectEstimationDataBase.getVersion()));
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    /**
     * Check if database exists
     */
    private void checkDatabaseCreation()
    {
        try
        {
            String destPath = "/data/data/com.mobileprojectestimator.mobileprojectestimator/databases/" + DB_NAME;

            File f = new File(destPath);
            File c = new File("/data/data/com.mobileprojectestimator.mobileprojectestimator/databases/");

            // ---if directory doesn't exist then create it---
            if (!c.exists())
            {
                //noinspection ResultOfMethodCallIgnored
                c.mkdir();
            }

            // ---if db file doesn't exist then create it---
            if (!f.exists())
            {
                copyDataBase();
            }
        } catch (FileNotFoundException e)
        {
            Log.d("ERROR", "checkDatabaseCreation: FileNotFoundException = " + e.getCause());
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.d("ERROR", "checkDatabaseCreation: IOException = " + e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * Returns the String value from Resource by the string name
     * <p/>
     * Input estimation_method_function_point
     * Output "Function Point"
     *
     * @param resourceName
     * @return
     */
    public String getStringResourceValueByResourceName(String resourceName)
    {
        int resID = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());
        String name = context.getResources().getString(resID);
        DataBaseHelper.resourcesIdMap.put(name, resID);
        return name;
    }

    /**
     * Input e.g. "Function Point"
     * Output e.g. String id estimation_method_function_point
     *
     * @param resourceValue
     * @return
     */
    public String getResourceNameByStringResourceValue(String resourceValue)
    {
        //String resourceName = v.getResources().getResourceName(resource id);
        int resId = DataBaseHelper.resourcesIdMap.get(resourceValue);
        String resValue = context.getResources().getResourceEntryName(resId);

        return resValue;
    }

    //~~~~SQL Queries Start here~~~~\\

    /**
     * Query to return all Influence Factors for the estimation Method.
     * ATTENTION: This returns only items with the id. For getting an item with the value you need to select from the estimation method influence factor table with the influence_factor_id
     *
     * @param estimationId
     * @return
     */
    public ArrayList<DatabaseInfluenceFactorItem> getInfluenceFactorItems(int estimationId)
    {
        ArrayList<DatabaseInfluenceFactorItem> influenceFactorItems = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM InfluenceFactors WHERE estimation_method_id = %d ORDER BY lower(name) ASC;", estimationId);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    DatabaseInfluenceFactorItem databaseInfluenceFactorItem = new DatabaseInfluenceFactorItem();
                    databaseInfluenceFactorItem.set_id(c.getInt((c.getColumnIndex("_id"))));
                    databaseInfluenceFactorItem.set_name(c.getString(c.getColumnIndex("name")));
                    databaseInfluenceFactorItem.set_estimationMethodId(c.getInt((c.getColumnIndex("estimation_method_id"))));
                    databaseInfluenceFactorItem.set_influenceFactorId(c.getInt((c.getColumnIndex("influence_factor_id"))));
                    influenceFactorItems.add(databaseInfluenceFactorItem);
                } while (c.moveToNext());
            }
        }
        db.close();
        return influenceFactorItems;
    }

    /**
     * Log all table names of the database
     */
    public void logAllTableNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor c = db.rawQuery("SELECT name FROM sqlite_master  WHERE type='table'", null))
        {
            if (c.moveToFirst())
            {
                while (!c.isAfterLast())
                {
                    Log.d("INFO", "Table Name => " + c.getString(0));
                    c.moveToNext();
                }
            }
        }
        db.close();
    }


    /**
     * load estimation method
     *
     * @param estimationMethod
     * @return
     */
    public int getEstimationMethodId(String estimationMethod)
    {
        String query = String.format("SELECT _id FROM EstimationMethod WHERE name = '%s';", getResourceNameByStringResourceValue(estimationMethod));
        SQLiteDatabase db = this.getReadableDatabase();

        int estimationId;
        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            estimationId = c.getInt(c.getColumnIndex("_id"));
        }

        return estimationId;
    }

    /**
     * load influence values for function point values
     *
     * @param itemId
     * @return
     */
    public ArrayList<Integer> loadFunctionPointInfluenceValues(int itemId)
    {
        ArrayList<Integer> influenceFactorValues = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM FunctionPointInfluenceFactor WHERE _id = %d;", itemId);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c != null)
                c.moveToFirst();

            influenceFactorValues.add(c.getInt(c.getColumnIndex("Integration")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("LocalDataProcessing")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("TransactionRate")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("ArithmeticOperation")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("ControlProcedure")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("ExceptionalRule")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("Logic")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("Reusability")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("StockConversion")));
            influenceFactorValues.add(c.getInt(c.getColumnIndex("Adaptability")));
        }

        db.close();
        return influenceFactorValues;
    }

    /**
     * Returns a string ArrayList with all available Estimation Methods
     *
     * @return
     */
    public ArrayList<String> getEstimationMethodNames()
    {
        ArrayList<String> estimationMethodNames = new ArrayList<>();

        String selectQuery = "SELECT name FROM EstimationMethod;";

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    String estimationMethod = getStringResourceValueByResourceName(c.getString(c.getColumnIndex("name")));
                    estimationMethodNames.add(estimationMethod);
                } while (c.moveToNext());
            }
        }
        db.close();

        return estimationMethodNames;
    }

    /**
     * Returns the influence factor id for the factor name in the parameter
     *
     * @param factorName
     * @return
     */
    public int getFactorItemId(String factorName)
    {
        String query = String.format("SELECT influence_factor_id FROM InfluenceFactors where name = '%s'", factorName);
        SQLiteDatabase db = this.getReadableDatabase();

        int factorItemId;
        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            factorItemId = c.getInt(c.getColumnIndex("influence_factor_id"));
        }

        return factorItemId;
    }

    /**
     * create new project in database
     *
     * @param selectedEstimationMethod
     * @param influencingFactor
     */
    public void createNewInfluenceFactor(String selectedEstimationMethod, InfluencingFactor influencingFactor)
    {
        int influenceFactorDatabaseId = 0;
        int estimationMethodId = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        if (selectedEstimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            ArrayList<InfluenceFactorItem> items = influencingFactor.getInfluenceFactorItems();
            int integration = items.get(0).getChosenValue();
            int localData = items.get(1).getChosenValue();
            int transactionRate = items.get(2).getChosenValue();
            int arithmetic = items.get(3).getSubInfluenceFactorItemsList().get(0).getChosenValue();
            int control = items.get(3).getSubInfluenceFactorItemsList().get(1).getChosenValue();
            int exceptional = items.get(3).getSubInfluenceFactorItemsList().get(2).getChosenValue();
            int logic = items.get(3).getSubInfluenceFactorItemsList().get(3).getChosenValue();
            int reusability = items.get(4).getChosenValue();
            int stockConversion = items.get(5).getChosenValue();
            int adaptability = items.get(6).getChosenValue();


            String selectCreatedIdQuery = "SELECT _id FROM FunctionPointInfluenceFactor ORDER BY _id DESC LIMIT 1;";
            try (Cursor c = db.rawQuery(selectCreatedIdQuery, null))
            {
                if (c != null)
                    c.moveToFirst();

                influenceFactorDatabaseId = c.getInt(c.getColumnIndex("_id"));
            }
            influenceFactorDatabaseId++;

            ContentValues insertValues = new ContentValues();
            insertValues.put("_id", influenceFactorDatabaseId);
            insertValues.put("Integration", integration);
            insertValues.put("LocalDataProcessing", localData);
            insertValues.put("TransactionRate", transactionRate);
            insertValues.put("ArithmeticOperation", arithmetic);
            insertValues.put("ControlProcedure", control);
            insertValues.put("ExceptionalRule", exceptional);
            insertValues.put("Logic", logic);
            insertValues.put("Reusability", reusability);
            insertValues.put("StockConversion", stockConversion);
            insertValues.put("Adaptability", adaptability);
            db.insert("FunctionPointInfluenceFactor", null, insertValues);

            estimationMethodId = getEstimationMethodId(selectedEstimationMethod);
        }

        ContentValues insertInfluenceValues = new ContentValues();
        insertInfluenceValues.put("name", influencingFactor.getInfluenceFactorSetName());
        insertInfluenceValues.put("estimation_method_id", estimationMethodId);
        insertInfluenceValues.put("influence_factor_id", influenceFactorDatabaseId);
        db.insert("InfluenceFactors", null, insertInfluenceValues);

        db.close();

    }

    public void updateExistingInfluenceFactor(String selectedEstimationMethod, String oldFactorName, InfluencingFactor influencingFactor)
    {
        //TODO: Create Statement to update existing Influence Factor
    }

    /**
     * Load the project by the Id from the Projects _id. Completes the Information with the other tables
     *
     * @param context
     * @param projectId
     * @return
     */
    public Project loadProjectById(Context context, String projectId)
    {
        Project p = new Project(context);

        String query = String.format("SELECT * FROM Projects where _id = '%s'", projectId);
        SQLiteDatabase db = this.getReadableDatabase();

        int estimation_items_id;
        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            p.setTitle(c.getString(c.getColumnIndex("name")));
            p.setCreationDate(c.getString(c.getColumnIndex("created_at")));
            String estimationMethodId = c.getString(c.getColumnIndex("estimation_method_id"));
            p.setEstimationMethod(getEstimationMethodNameById(estimationMethodId));
            int detailsId = c.getInt(c.getColumnIndex("project_details_id"));

            p.setImage(loadProjectImage(detailsId));

            query = String.format("SELECT * FROM ProjectDetails where _id = '%s'", detailsId);
            int project_properties_id;
            try (Cursor c2 = db.rawQuery(query, null))
            {

                if (c2 != null)
                    c2.moveToFirst();

                p.setEvaluatedPersonDays(c2.getInt(c2.getColumnIndex("evaluated_person_days")));

                estimation_items_id = c2.getInt(c2.getColumnIndex("estimation_items_id"));
                project_properties_id = c2.getInt(c2.getColumnIndex("project_properties_id"));
                int description_id = c2.getInt(c2.getColumnIndex("description_id"));
                int influence_factorset_id = c2.getInt(c2.getColumnIndex("influence_factorset_id"));
                p.setProjectDescription(loadDescriptionById(description_id));
                p.setInfluencingFactor(loadInfluenceFactorById(influence_factorset_id));
            }
            p.setEstimationItems(loadEstimationItemsById(p.getEstimationMethod(), estimation_items_id));
            p.setProjectProperties(loadProjectPropertiesById(project_properties_id));
        }
        db.close();
        return p;
    }

    /**
     * Load the projectProperties by the property Id
     *
     * @param project_properties_id
     * @return
     */
    public ProjectProperties loadProjectPropertiesById(int project_properties_id)
    {
        ProjectProperties properties = new ProjectProperties();

        String query = String.format("SELECT * FROM ProjectProperties where _id = '%s'", project_properties_id);
        SQLiteDatabase db = this.getReadableDatabase();

        String developmentMarktedId;
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            developmentMarktedId = c.getString(c.getColumnIndex("DevelopmentMarket_id"));
            String developmentKindId = c.getString(c.getColumnIndex("DevelopmentMarket_id"));
            String processMethologyId = c.getString(c.getColumnIndex("DevelopmentMarket_id"));
            String programmingLanguageId = c.getString(c.getColumnIndex("DevelopmentMarket_id"));
            String platformId = c.getString(c.getColumnIndex("DevelopmentMarket_id"));
            String industrySectorId = c.getString(c.getColumnIndex("DevelopmentMarket_id"));
            properties.setMarket(loadMarketNameById(developmentMarktedId));
            properties.setDevelopmentKind(loadDevelopmentKindNameById(developmentKindId));
            properties.setProgrammingLanguage(loadProgrammingLanguageNameById(programmingLanguageId));
            properties.setProcessMethology(loadProcessMethologyNameById(processMethologyId));
            properties.setPlatform(loadPlatformNameById(platformId));
            properties.setIndustrySector(loadIndustrySectorNameById(industrySectorId));
        }
        db.close();
        return properties;
    }

    public String loadIndustrySectorNameById(String industrySectorId)
    {
        String name;

        String query = String.format("SELECT * FROM IndustrySectors where _id = '%s'", industrySectorId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
    }

    public String loadPlatformNameById(String platformId)
    {
        String name;

        String query = String.format("SELECT * FROM Platforms where _id = '%s'", platformId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
    }

    public String loadProcessMethologyNameById(String processMethologyId)
    {
        String name;

        String query = String.format("SELECT * FROM ProcessMethologies where _id = '%s'", processMethologyId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
    }

    public String loadProgrammingLanguageNameById(String programmingLanguageId)
    {
        String name;

        String query = String.format("SELECT * FROM ProgrammingLanguages where _id = '%s'", programmingLanguageId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
    }

    public String loadDevelopmentKindNameById(String developmentKindId)
    {
        String name;

        String query = String.format("SELECT * FROM DevelopmentTypes where _id = '%s'", developmentKindId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
    }

    public String loadMarketNameById(String developmentMarktedId)
    {
        String name;

        String query = String.format("SELECT * FROM DevelopmentMarkets where _id = '%s'", developmentMarktedId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
    }

    /**
     * Load the EstimationItems
     *
     * @param estimationMethod
     * @param estimation_items_id
     * @return
     */
    public ArrayList<EstimationItem> loadEstimationItemsById(String estimationMethod, int estimation_items_id)
    {
        ArrayList<EstimationItem> estimationItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if (estimationMethod.equals(this.context.getString(R.string.estimation_method_function_point)))
        {
            String query = String.format("SELECT * FROM FunctionPointEstimationItems where _id = '%s'", estimation_items_id);
            FunctionPointItem item;
            try (Cursor c = db.rawQuery(query, null))
            {
                if (c != null)
                    c.moveToFirst();
                String inputDataItemsId = c.getString(c.getColumnIndex("InputDataItems_id"));
                String requestItemsId = c.getString(c.getColumnIndex("RequestItems_id"));
                String outputItemsId = c.getString(c.getColumnIndex("OutputItems_id"));
                String datasetItemsId = c.getString(c.getColumnIndex("DatasetItems_id"));
                String referenceDataItemsId = c.getString(c.getColumnIndex("ReferenceDataItems_id"));

                query = String.format("SELECT * FROM InputDataItems where _id = '%s'", inputDataItemsId);
                try (Cursor c1 = db.rawQuery(query, null))
                {
                    if (c1 != null)
                        c1.moveToFirst();
                    item = new FunctionPointItem();
                    item.setSimpleValue(c1.getInt(c1.getColumnIndex("simple")));
                    item.setMediumValue(c1.getInt(c1.getColumnIndex("medium")));
                    item.setComplexValue(c1.getInt(c1.getColumnIndex("complex")));
                }
                estimationItems.add(item);

                query = String.format("SELECT * FROM RequestItems where _id = '%s'", requestItemsId);
                try (Cursor c2 = db.rawQuery(query, null))
                {
                    if (c2 != null)
                        c2.moveToFirst();
                    item = new FunctionPointItem();
                    item.setSimpleValue(c2.getInt(c2.getColumnIndex("simple")));
                    item.setMediumValue(c2.getInt(c2.getColumnIndex("medium")));
                    item.setComplexValue(c2.getInt(c2.getColumnIndex("complex")));
                }
                estimationItems.add(item);

                query = String.format("SELECT * FROM OutputItems where _id = '%s'", outputItemsId);
                try (Cursor c3 = db.rawQuery(query, null))
                {
                    if (c3 != null)
                        c3.moveToFirst();
                    item = new FunctionPointItem();
                    item.setSimpleValue(c3.getInt(c3.getColumnIndex("simple")));
                    item.setMediumValue(c3.getInt(c3.getColumnIndex("medium")));
                    item.setComplexValue(c3.getInt(c3.getColumnIndex("complex")));
                }
                estimationItems.add(item);

                query = String.format("SELECT * FROM DatasetItems where _id = '%s'", datasetItemsId);
                try (Cursor c4 = db.rawQuery(query, null))
                {
                    if (c4 != null)
                        c4.moveToFirst();
                    item = new FunctionPointItem();
                    item.setSimpleValue(c4.getInt(c4.getColumnIndex("simple")));
                    item.setMediumValue(c4.getInt(c4.getColumnIndex("medium")));
                    item.setComplexValue(c4.getInt(c4.getColumnIndex("complex")));
                }
                estimationItems.add(item);

                query = String.format("SELECT * FROM ReferenceDataItems where _id = '%s'", referenceDataItemsId);
                try (Cursor c5 = db.rawQuery(query, null))
                {
                    if (c5 != null)
                        c5.moveToFirst();
                    item = new FunctionPointItem();
                    item.setSimpleValue(c5.getInt(c5.getColumnIndex("simple")));
                    item.setMediumValue(c5.getInt(c5.getColumnIndex("medium")));
                    item.setComplexValue(c5.getInt(c5.getColumnIndex("complex")));
                }
                estimationItems.add(item);
            }
        }
        db.close();
        return estimationItems;
    }

    /**
     * Load the influence factor from the database.
     *
     * @param influence_factorset_id
     * @return
     */
    public InfluencingFactor loadInfluenceFactorById(int influence_factorset_id)
    {
        InfluencingFactor factor = null;
        String query = String.format("SELECT * FROM InfluenceFactors where _id = '%s'", influence_factorset_id);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            String name = c.getString(c.getColumnIndex("name"));
            String estimationMethod = getEstimationMethodNameById(c.getString(c.getColumnIndex("estimation_method_id")));
            String influenceFactorId = c.getString(c.getColumnIndex("influence_factor_id"));

            if (estimationMethod.equals(this.context.getString(R.string.estimation_method_function_point)))
            {
                factor = new InfluencingFactor(this.context, InfluencingFactor.FUNCTIONPOINTFACTORS);
                factor.setName(name);
                query = String.format("SELECT * FROM FunctionPointInfluenceFactors where _id = '%s'", influenceFactorId);
                try (Cursor c2 = db.rawQuery(query, null))
                {

                    if (c2 != null)
                        c2.moveToFirst();

                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Integration")), context.getString(R.string.function_point_influence_factor_item_integration));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("LocalDataProcessing")), context.getString(R.string.function_point_influence_factor_item_local_data));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("TransactionRate")), context.getString(R.string.function_point_influence_factor_item_transaction_rate));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("ArithmeticOperation")), context.getString(R.string.function_point_influence_factor_item_arithmetic_operation));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Reusability")), context.getString(R.string.function_point_influence_factor_item_reusability));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("StockConversion")), context.getString(R.string.function_point_influence_factor_item_stock_conversion));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Adaptability")), context.getString(R.string.function_point_influence_factor_item_adaptability));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("ArithmeticOperation")), context.getString(R.string.function_point_influence_factor_item_arithmetic_operation));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("ExceptionalRule")), context.getString(R.string.function_point_influence_factor_item_exception_regulation));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Logic")), context.getString(R.string.function_point_influence_factor_item_logic));
                }

            } else if (estimationMethod.equals(this.context.getString(R.string.estimation_method_cocomo)))
            {
                factor = new InfluencingFactor(this.context, InfluencingFactor.COCOMOFACTORS);
            } else if (estimationMethod.equals(this.context.getString(R.string.estimation_method_cocomo_2)))
            {
                factor = new InfluencingFactor(this.context, InfluencingFactor.COCOMO2FACTORS);
            }
        }

        db.close();
        return factor;
    }

    /**
     * Returns the Description text
     *
     * @param description_id
     * @return
     */
    public String loadDescriptionById(int description_id)
    {
        String query = String.format("SELECT * FROM ProjectDescriptions where _id = '%s'", description_id);
        SQLiteDatabase db = this.getReadableDatabase();

        String description;
        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            description = c.getString(c.getColumnIndex("text"));
        }
        db.close();
        return description;
    }

    /**
     * Return true if projects exist
     *
     * @return
     */
    public boolean hasProjects()
    {
        String query = "SELECT Count(*) From Projects";
        SQLiteDatabase db = this.getReadableDatabase();

        int factorItemId;
        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            factorItemId = c.getInt(0);
        }

        db.close();

        return factorItemId > 0;
    }

    /**
     * Return all projects in database as arrayList with name, estimation method, creation date, detailsId and Icon Information
     *
     * @param activity
     * @return
     */
    public ArrayList<Project> getAllProjects(Activity activity)
    {
        ArrayList<Project> projects = new ArrayList<>();

        String selectQuery = "SELECT * FROM Projects;";

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    Project p = new Project(activity.getBaseContext());
                    p.setTitle(c.getString(c.getColumnIndex("name")));
                    p.setEstimationMethod(getEstimationMethodNameById(c.getString(c.getColumnIndex("estimation_method_id"))));
                    int detailsId = c.getInt(c.getColumnIndex("project_details_id"));

                    p.setImage(loadProjectImage(detailsId));
                    p.setCreationDate(c.getString(c.getColumnIndex("created_at")));

                    p.setProjectId(c.getInt(c.getColumnIndex("_id")));
                    projects.add(p);
                } while (c.moveToNext());
            }
        }
        db.close();
        return projects;
    }

    /**
     * Load the Bitmap from Icon from the assets Folder
     *
     * @param detailsId
     * @return
     */
    public Bitmap loadProjectImage(int detailsId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = String.format("SELECT * FROM ProjectDetails WHERE _id = %d;", detailsId);
        String path;
        try (Cursor c = db.rawQuery(selectQuery, null))
        {

            if (c != null)
                c.moveToFirst();

            int iconId = c.getInt(c.getColumnIndex("icon_id"));

            selectQuery = String.format("SELECT * FROM ProjectIcons WHERE _id = %d", iconId);
            try (Cursor c2 = db.rawQuery(selectQuery, null))
            {

                if (c2 != null)
                    c2.moveToFirst();

                path = c2.getString(c2.getColumnIndex("path"));
            }
        }
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try
        {
            istr = assetManager.open(path);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e)
        {
            // handle exception
        }

        db.close();
        return bitmap;
    }

    /**
     * Returns the name of an estimation Method id
     *
     * @param estimation_method_id
     * @return
     */
    public String getEstimationMethodNameById(String estimation_method_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = String.format("SELECT * FROM EstimationMethod WHERE _id = %s;", estimation_method_id);
        String name;
        try (Cursor c = db.rawQuery(selectQuery, null))
        {

            if (c != null)
                c.moveToFirst();

            name = c.getString(1);
        }

        db.close();
        return getStringResourceValueByResourceName(name);
    }

    /**
     * Load all project properties as Arraylist from the database
     * TableNames: DevelopmentMarkets, DevelopmentTypes, IndustrySectors, Platforms, ProcessMethologies, ProgrammingLanguages,
     *
     * @param tableName
     * @return
     */
    public ArrayList<String> loadAllPropertiesByName(String tableName)
    {
        ArrayList<String> items = new ArrayList<>();
        Log.d("Info", "Load project properties from table: " + tableName);
        String selectQuery = String.format("SELECT * FROM %s;", tableName);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    String marketName = c.getString(c.getColumnIndex("name"));
                    items.add(getStringResourceValueByResourceName(marketName));
                } while (c.moveToNext());
            }
        }
        db.close();
        Collections.sort(items);
        return items;
    }

}
