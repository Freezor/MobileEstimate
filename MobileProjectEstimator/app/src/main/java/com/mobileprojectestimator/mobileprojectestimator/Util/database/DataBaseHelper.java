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
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.FunctionPointProductivityItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.HelpArticleItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.ImageItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Statistic.EstimatedProjectItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Statistic.PropertyProjects;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.ProgrammingLanguageProperty;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.ProjectProperties;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.SoftwareArchitectureProperty;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectFilter;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private XmlHelper xmlHelper;

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
        xmlHelper = new XmlHelper(context);
        this.context = context;
    }


    public String getDbPath()
    {
        return DB_PATH;
    }

    public String getDbName()
    {
        return DB_NAME;
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
        Log.d("Info", "getStringResourceValueByResourceName. resourceName = " + resourceName + " resId= " + resID);
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
    public ArrayList<DatabaseInfluenceFactorItem> getActiveInfluenceFactorItems(int estimationId)
    {
        ArrayList<DatabaseInfluenceFactorItem> influenceFactorItems = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM InfluenceFactors WHERE estimation_method_id = %d AND is_deleted = 0 ORDER BY lower(name) ASC;", estimationId);

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
        } catch (Exception e)
        {
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
        } catch (Exception e)
        {
        }
        db.close();
    }


    /**
     * load estimation method
     * Input "Function Point"
     *
     * @param estimationMethod
     * @return
     */
    public int getEstimationMethodId(String estimationMethod)
    {
        String query = String.format("SELECT _id FROM EstimationMethod WHERE name = \"%s\";", getResourceNameByStringResourceValue(estimationMethod));
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

        if (selectedEstimationMethod.equals(context.getString(R.string.estimation_technique_function_point)))
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

    public void updateExistingInfluenceFactor(String selectedEstimationMethod, int oldFactorId, InfluencingFactor influencingFactor)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //Update Project Name
        ContentValues args = new ContentValues();
        args.put("name", influencingFactor.getInfluenceFactorSetName());
        db.update("InfluenceFactors", args, "_id=" + oldFactorId, null);

        int factorId = 0;
        String selectCreatedIdQuery = String.format("SELECT * FROM InfluenceFactors WHERE _id= '%d';", oldFactorId);
        try (Cursor c = db.rawQuery(selectCreatedIdQuery, null))
        {
            if (c != null)
            {
                c.moveToFirst();
                factorId = c.getInt(c.getColumnIndex("influence_factor_id"));
            }
        }
        if (selectedEstimationMethod.equals(context.getString(R.string.estimation_technique_function_point)))
        {
            ArrayList<InfluenceFactorItem> items = influencingFactor.getInfluenceFactorItems();
            args = new ContentValues();
            args.put("Integration", items.get(0).getChosenValue());
            args.put("LocalDataProcessing", items.get(1).getChosenValue());
            args.put("TransactionRate", items.get(2).getChosenValue());
            args.put("ArithmeticOperation", items.get(3).getSubInfluenceFactorItemsList().get(0).getChosenValue());
            args.put("ControlProcedure", items.get(3).getSubInfluenceFactorItemsList().get(1).getChosenValue());
            args.put("ExceptionalRule", items.get(3).getSubInfluenceFactorItemsList().get(2).getChosenValue());
            args.put("Logic", items.get(3).getSubInfluenceFactorItemsList().get(3).getChosenValue());
            args.put("Reusability", items.get(4).getChosenValue());
            args.put("StockConversion", items.get(5).getChosenValue());
            args.put("Adaptability", items.get(6).getChosenValue());
            db.update("FunctionPointInfluenceFactor", args, "_id=" + factorId, null);
        }
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

        p.setProjectId(Integer.parseInt(projectId));
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
            int isdeleted = c.getInt(c.getColumnIndex("is_deleted"));
            if (isdeleted > 0)
            {
                p.setIsDeleted(true);
            } else
            {
                p.setIsDeleted(false);
            }
            int istermintated = c.getInt(c.getColumnIndex("is_terminated"));
            if (istermintated > 0)
            {
                p.setIsTerminated(true);
            } else
            {
                p.setIsTerminated(false);
            }

            p.setImage(loadProjectImageFromProjectDetails(detailsId));

            query = String.format("SELECT * FROM ProjectDetails where _id = '%s'", detailsId);
            int project_properties_id;

            db = this.getReadableDatabase();
            try (Cursor c2 = db.rawQuery(query, null))
            {

                if (c2 != null)
                    c2.moveToFirst();

                p.setEvaluatedPersonDays(c2.getDouble(c2.getColumnIndex("evaluated_person_days")));
                p.setIconId(c2.getString(c2.getColumnIndex("icon_id")));
                estimation_items_id = c2.getInt(c2.getColumnIndex("estimation_items_id"));
                project_properties_id = c2.getInt(c2.getColumnIndex("project_properties_id"));
                int description_id = c2.getInt(c2.getColumnIndex("description_id"));
                int influence_factorset_id = c2.getInt(c2.getColumnIndex("influence_factorset_id"));
                p.setProjectDescription(loadDescriptionById(description_id));
                p.setInfluencingFactor(loadInfluenceFactorById(influence_factorset_id));
            }
            Log.d("INFO", "Load Estimation Items: " + estimation_items_id);
            p.setEstimationItems(loadEstimationItemsById(p.getEstimationMethod(), estimation_items_id));
            p.setProjectProperties(loadProjectPropertiesById(project_properties_id));

            if (p.isTerminated())
            {
                if (p.getEstimationMethod().equals(context.getString(R.string.estimation_technique_function_point)))
                {
                    query = String.format("SELECT * FROM FunctionPointProductivity where project_id = '%s'", p.getProjectId());
                    db = this.getReadableDatabase();
                    try (Cursor c2 = db.rawQuery(query, null))
                    {
                        if (c2 != null)
                            c2.moveToFirst();
                        p.setFinalPersonDays(c2.getInt(c2.getColumnIndex("terminated_days")));
                    }
                }
            } else
            {
                p.setFinalPersonDays(0);
            }
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
            String developmentKindId = c.getString(c.getColumnIndex("DevelopmentKind_id"));
            String processMethologyId = c.getString(c.getColumnIndex("ProcessMethology_id"));
            String programmingLanguageId = c.getString(c.getColumnIndex("ProgrammingLanguage_id"));
            String platformId = c.getString(c.getColumnIndex("Platform_id"));
            String industrySectorId = c.getString(c.getColumnIndex("IndustrySector_id"));
            String architectureId = c.getString(c.getColumnIndex("Architecture_id"));
            properties.setMarket(loadMarketNameById(developmentMarktedId));
            properties.setDevelopmentKind(loadDevelopmentKindNameById(developmentKindId));
            properties.setProgrammingLanguage(loadProgrammingLanguageNameById(programmingLanguageId));
            properties.setProcessMethology(loadProcessMethologyNameById(processMethologyId));
            properties.setPlatform(loadPlatformNameById(platformId));
            properties.setIndustrySector(loadIndustrySectorNameById(industrySectorId));
            properties.setArchitecture(loadArchitectureNameById(architectureId));
        }
        db.close();
        return properties;
    }

    private String loadArchitectureNameById(String architectureId)
    {
        String name;

        String query = String.format("SELECT * FROM SoftwareArchitecturePatterns where _id = '%s'", architectureId);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {
            if (c != null)
                c.moveToFirst();

            name = c.getString(c.getColumnIndex("name"));
        }

        return getStringResourceValueByResourceName(name);
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
        if (estimationMethod.equals(this.context.getString(R.string.estimation_technique_function_point)))
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
                    item = new FunctionPointItem((context.getString(R.string.function_point_estimation_input_data)), 3, 4, 6);
                    item.setItemId(inputDataItemsId);
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
                    item = new FunctionPointItem((context.getString(R.string.function_point_estimation_requests)), 3, 4, 6);
                    item.setItemId(requestItemsId);
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
                    item = new FunctionPointItem((context.getString(R.string.function_point_estimation_output)), 4, 5, 7);
                    item.setItemId(outputItemsId);
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
                    item = new FunctionPointItem((context.getString(R.string.function_point_estimation_dataset)), 7, 10, 15);
                    item.setItemId(datasetItemsId);
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
                    item = new FunctionPointItem((context.getString(R.string.function_point_estimation_reference_data)), 5, 7, 10);
                    item.setItemId(referenceDataItemsId);
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
        String query = String.format("SELECT * FROM InfluenceFactors where _id = '%d'", influence_factorset_id);
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(query, null))
        {

            if (c != null)
                c.moveToFirst();

            int dbId = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            String estimationMethod = getEstimationMethodNameById(c.getString(c.getColumnIndex("estimation_method_id")));
            String influenceFactorId = c.getString(c.getColumnIndex("influence_factor_id"));

            if (estimationMethod.equals(this.context.getString(R.string.estimation_technique_function_point)))
            {
                factor = new InfluencingFactor(this.context, InfluencingFactor.FUNCTIONPOINTFACTORS);
                factor.setName(name);
                factor.setDbId(dbId);
                query = String.format("SELECT * FROM FunctionPointInfluenceFactor where _id = '%s'", influenceFactorId);
                db = this.getReadableDatabase();
                try (Cursor c2 = db.rawQuery(query, null))
                {

                    if (c2 != null)
                        c2.moveToFirst();

                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Integration")), context.getString(R.string.function_point_influence_factor_item_integration));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("LocalDataProcessing")), context.getString(R.string.function_point_influence_factor_item_local_data));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("TransactionRate")), context.getString(R.string.function_point_influence_factor_item_transaction_rate));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("ArithmeticOperation")), context.getString(R.string.function_point_influence_factor_item_arithmetic_operation));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("ControlProcedure")), context.getString(R.string.function_point_influence_factor_item_control_procedure));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("ExceptionalRule")), context.getString(R.string.function_point_influence_factor_item_exception_regulation));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Logic")), context.getString(R.string.function_point_influence_factor_item_logic));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Reusability")), context.getString(R.string.function_point_influence_factor_item_reusability));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("StockConversion")), context.getString(R.string.function_point_influence_factor_item_stock_conversion));
                    factor.setChosenValueOfItem(c2.getInt(c2.getColumnIndex("Adaptability")), context.getString(R.string.function_point_influence_factor_item_adaptability));
                }

            } else if (estimationMethod.equals(this.context.getString(R.string.estimation_technique_cocomo)))
            {
                factor = new InfluencingFactor(this.context, InfluencingFactor.COCOMOFACTORS);
                factor.setName(name);
                factor.setDbId(dbId);
            } else if (estimationMethod.equals(this.context.getString(R.string.estimation_technique_cocomo_2)))
            {
                factor = new InfluencingFactor(this.context, InfluencingFactor.COCOMO2FACTORS);
                factor.setName(name);
                factor.setDbId(dbId);
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

    public ArrayList<Project> getAllActiveProjects(Activity activity)
    {
        ArrayList<Project> projects = new ArrayList<>();

        String selectQuery = "SELECT * FROM Projects WHERE is_deleted = 0;";

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

                    p.setImage(loadProjectImageFromProjectDetails(detailsId));
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

                    p.setImage(loadProjectImageFromProjectDetails(detailsId));
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
    public Bitmap loadProjectImageFromProjectDetails(int detailsId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = String.format("SELECT * FROM ProjectDetails WHERE _id = %d;", detailsId);
        String path;
        try (Cursor c = db.rawQuery(selectQuery, null))
        {

            if (c != null)
                c.moveToFirst();

            int iconId = c.getInt(c.getColumnIndex("icon_id"));

            path = getIconInformationsById(iconId).get("path");
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
     * Get Icon Informations (name & path) by the Icon Id
     *
     * @param iconId
     * @return
     */
    public HashMap<String, String> getIconInformationsById(int iconId)
    {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery;
        HashMap<String, String> iconInfos = new HashMap<>();
        selectQuery = String.format("SELECT * FROM ProjectIcons WHERE _id = %d", iconId);
        try (Cursor c2 = db.rawQuery(selectQuery, null))
        {

            if (c2 != null)
                c2.moveToFirst();

            iconInfos.put("path", c2.getString(c2.getColumnIndex("path")));
            iconInfos.put("id", String.valueOf(c2.getInt(c2.getColumnIndex("_id"))));
            iconInfos.put("name", c2.getString(c2.getColumnIndex("name")));
        }
        db.close();
        return iconInfos;
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

    /**
     * Fills the DatabaseHelper.resourcesIdMap with all string values
     */
    public void preloadResourcesIdMap()
    {
        Log.d("Info", "Preload DatabaseHelper.resourcesIdMap");
        ArrayList<String> tables = new ArrayList<>();
        tables.add("DevelopmentMarkets");
        tables.add("DevelopmentTypes");
        tables.add("EstimationMethod");
        tables.add("IndustrySectors");
        tables.add("SoftwareArchitecturePatterns");
        tables.add("Platforms");
        tables.add("ProcessMethologies");
        tables.add("ProgrammingLanguages");
        tables.add("ProjectIcons");

        for (String tablename : tables)
        {
            loadAllPropertiesByName(tablename);
        }

        Log.d("INFO", "Number of resource ids = " + DataBaseHelper.resourcesIdMap.size());
    }

    /**
     * Returns the next possible id for a table
     *
     * @param tablename
     * @return
     */
    private int getNextIdFromTable(String tablename)
    {
        String query = "SELECT _id FROM " + tablename + " ORDER BY _id DESC LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        int id = 0;
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c.moveToFirst())
            {
                id = c.getInt(c.getColumnIndex("_id"));
            }
        }
        db.close();
        id++;
        return id;
    }

    /**
     * Saves a new Project to the database
     *
     * @param project
     */
    public void saveNewProject(Project project)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("INFO", "Start Saving New Project " + project.getTitle());
        //Insert Project Properties
        int projectPropertyId = getNextIdFromTable("ProjectProperties");
        int marketId = getPropertyIdFromTable("DevelopmentMarkets", project.getProjectProperties().getMarket());
        int devKindId = getPropertyIdFromTable("DevelopmentTypes", project.getProjectProperties().getDevelopmentKind());
        int processMethodId = getPropertyIdFromTable("ProcessMethologies", project.getProjectProperties().getProcessMethology());
        int programmingLangId = getPropertyIdFromTable("ProgrammingLanguages", project.getProjectProperties().getProgrammingLanguage());
        int platformId = getPropertyIdFromTable("Platforms", project.getProjectProperties().getPlatform());
        int industrySectorId = getPropertyIdFromTable("IndustrySectors", project.getProjectProperties().getIndustrySector());
        int architectureId = getPropertyIdFromTable("SoftwareArchitecturePatterns", project.getProjectProperties().getArchitecture());

        ContentValues projectPropertyValues = new ContentValues();
        projectPropertyValues.put("_id", projectPropertyId);
        projectPropertyValues.put("DevelopmentMarket_id", marketId);
        projectPropertyValues.put("DevelopmentKind_id", devKindId);
        projectPropertyValues.put("ProcessMethology_id", processMethodId);
        projectPropertyValues.put("ProgrammingLanguage_id", programmingLangId);
        projectPropertyValues.put("Platform_id", platformId);
        projectPropertyValues.put("IndustrySector_id", industrySectorId);
        projectPropertyValues.put("Architecture_id", architectureId);
        db = this.getWritableDatabase();
        db.insert("ProjectProperties", null, projectPropertyValues);
        Log.d("INFO", "Saved new Property with id " + projectPropertyId);

        //Insert Project Description
        int projectDescriptionId = getNextIdFromTable("ProjectDescriptions");
        ContentValues projectDescriptionValues = new ContentValues();
        projectDescriptionValues.put("_id", projectDescriptionId);
        if (project.getProjectDescription() == null)
        {
            projectDescriptionValues.put("text", "");
        } else
        {
            projectDescriptionValues.put("text", project.getProjectDescription());
        }
        db = this.getWritableDatabase();
        db.insert("ProjectDescriptions", null, projectDescriptionValues);
        Log.d("INFO", "Saved new Description with id " + projectDescriptionId);

        //Create Estimation Items
        int estimationItemsId = 0;
        if (project.getEstimationMethod().equals(this.context.getString(R.string.estimation_technique_function_point)))
        {
            int inputDataItemId = getNextIdFromTable("InputDataItems");
            ContentValues inputDataValues = new ContentValues();
            inputDataValues.put("_id", inputDataItemId);
            inputDataValues.put("simple", 0);
            inputDataValues.put("medium", 0);
            inputDataValues.put("complex", 0);
            db = this.getWritableDatabase();
            db.insert("InputDataItems", null, inputDataValues);
            Log.d("INFO", "Saved new InputData with id " + inputDataItemId);

            int requestDataItemId = getNextIdFromTable("RequestItems");
            inputDataValues = new ContentValues();
            inputDataValues.put("_id", requestDataItemId);
            inputDataValues.put("simple", 0);
            inputDataValues.put("medium", 0);
            inputDataValues.put("complex", 0);
            db = this.getWritableDatabase();
            db.insert("RequestItems", null, inputDataValues);
            Log.d("INFO", "Saved new Request Data with id " + requestDataItemId);

            int outputDataItemId = getNextIdFromTable("OutputItems");
            inputDataValues = new ContentValues();
            inputDataValues.put("_id", outputDataItemId);
            inputDataValues.put("simple", 0);
            inputDataValues.put("medium", 0);
            inputDataValues.put("complex", 0);
            db = this.getWritableDatabase();
            db.insert("OutputItems", null, inputDataValues);
            Log.d("INFO", "Saved new Output Data with id " + outputDataItemId);

            int datasetItemId = getNextIdFromTable("DatasetItems");
            inputDataValues = new ContentValues();
            inputDataValues.put("_id", datasetItemId);
            inputDataValues.put("simple", 0);
            inputDataValues.put("medium", 0);
            inputDataValues.put("complex", 0);
            db = this.getWritableDatabase();
            db.insert("DatasetItems", null, inputDataValues);
            Log.d("INFO", "Saved new Dataset with id " + datasetItemId);

            int referenceDataItemId = getNextIdFromTable("ReferenceDataItems");
            inputDataValues = new ContentValues();
            inputDataValues.put("_id", referenceDataItemId);
            inputDataValues.put("simple", 0);
            inputDataValues.put("medium", 0);
            inputDataValues.put("complex", 0);
            db = this.getWritableDatabase();
            db.insert("ReferenceDataItems", null, inputDataValues);
            Log.d("INFO", "Saved new ReferenceData with id " + referenceDataItemId);

            estimationItemsId = getNextIdFromTable("FunctionPointEstimationItems");
            inputDataValues = new ContentValues();
            inputDataValues.put("_id", estimationItemsId);
            inputDataValues.put("InputDataItems_id", inputDataItemId);
            inputDataValues.put("RequestItems_id", requestDataItemId);
            inputDataValues.put("OutputItems_id", outputDataItemId);
            inputDataValues.put("DatasetItems_id", datasetItemId);
            inputDataValues.put("ReferenceDataItems_id", referenceDataItemId);
            db = this.getWritableDatabase();
            db.insert("FunctionPointEstimationItems", null, inputDataValues);
            Log.d("INFO", "Saved new EstimationItem with id " + estimationItemsId);
        }

        //Save Project Details
        String query = String.format("SELECT _id FROM InfluenceFactors WHERE name ='%s'", project.getInfluencingFactor().getInfluenceFactorSetName());
        int influenceFactorId = 2001;
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c.moveToFirst())
            {
                influenceFactorId = c.getInt(c.getColumnIndex("_id"));
            }
        }

        int projectIconId = 1;
        query = String.format("SELECT _id FROM ProjectIcons WHERE name ='%s'", getResourceNameByStringResourceValue(project.getIconName()));
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c.moveToFirst())
            {
                projectIconId = c.getInt(c.getColumnIndex("_id"));
            }
        }

        int projectDetailsId = getNextIdFromTable("ProjectDetails");
        ContentValues projectDetailsValues = new ContentValues();
        projectDetailsValues.put("_id", projectDetailsId);
        projectDetailsValues.put("icon_id", projectIconId);
        projectDetailsValues.put("estimation_items_id", estimationItemsId);
        projectDetailsValues.put("project_properties_id", projectPropertyId);
        projectDetailsValues.put("description_id", projectDescriptionId);
        projectDetailsValues.put("influence_factorset_id", influenceFactorId);
        projectDetailsValues.put("evaluated_person_days", project.getEvaluatedPersonDays());
        db = this.getWritableDatabase();
        db.insert("ProjectDetails", null, projectDetailsValues);
        Log.d("INFO", "Saved new Project Details with id " + projectDetailsId);

        int projectId = getNextIdFromTable("Projects");
        ContentValues projectValues = new ContentValues();
        projectValues.put("_id", projectId);
        projectValues.put("name", project.getTitle());
        projectValues.put("estimation_method_id", getEstimationMethodId(project.getEstimationMethod()));
        projectValues.put("project_details_id", projectDetailsId);
        db = this.getWritableDatabase();
        db.insert("Projects", null, projectValues);
        Log.d("INFO", "Saved new Project with id " + projectId);

        db.close();
    }

    /**
     * get the Id of an value from the database. Only works with tables that got the "name" coloumn
     *
     * @param tablename
     * @param property
     * @return
     */
    private int getPropertyIdFromTable(String tablename, String property)
    {

        String query = String.format("SELECT _id FROM %s WHERE name = '%s'", tablename, getResourceNameByStringResourceValue(property));
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        try (Cursor c = db.rawQuery(query, null))
        {
            if (c.moveToFirst())
            {
                id = c.getInt(c.getColumnIndex("_id"));
            }
        }
        db.close();
        return id;
    }

    /**
     * Saves the estimation item to the database. Overwrites the existing values
     *
     * @param estimationItem
     */
    public void updateFunctionPointEstimationItem(FunctionPointItem estimationItem)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if (estimationItem.getItemName().equals(context.getString(R.string.function_point_estimation_input_data)))
        {
            values.put("simple", estimationItem.getSimpleValue());
            values.put("medium", estimationItem.getMediumValue());
            values.put("complex", estimationItem.getComplexValue());
            db.update("InputDataItems", values, "_id=" + estimationItem.getItemId(), null);

        } else if (estimationItem.getItemName().equals(context.getString(R.string.function_point_estimation_requests)))
        {
            values.put("simple", estimationItem.getSimpleValue());
            values.put("medium", estimationItem.getMediumValue());
            values.put("complex", estimationItem.getComplexValue());
            db.update("RequestItems", values, "_id=" + estimationItem.getItemId(), null);
        } else if (estimationItem.getItemName().equals(context.getString(R.string.function_point_estimation_output)))
        {
            values.put("simple", estimationItem.getSimpleValue());
            values.put("medium", estimationItem.getMediumValue());
            values.put("complex", estimationItem.getComplexValue());
            db.update("OutputItems", values, "_id=" + estimationItem.getItemId(), null);
        } else if (estimationItem.getItemName().equals(context.getString(R.string.function_point_estimation_dataset)))
        {
            values.put("simple", estimationItem.getSimpleValue());
            values.put("medium", estimationItem.getMediumValue());
            values.put("complex", estimationItem.getComplexValue());
            db.update("DatasetItems", values, "_id=" + estimationItem.getItemId(), null);
        } else if (estimationItem.getItemName().equals(context.getString(R.string.function_point_estimation_reference_data)))
        {
            values.put("simple", estimationItem.getSimpleValue());
            values.put("medium", estimationItem.getMediumValue());
            values.put("complex", estimationItem.getComplexValue());
            db.update("ReferenceDataItems", values, "_id=" + estimationItem.getItemId(), null);
        } else
        {
            Log.d("ERROR", "updateFunctionPointEstimationItem: Problem with EstimationItem " + estimationItem.getItemName());
        }
        db.close();
    }

    /**
     * Loads all Project icons as an ArrayList<ImageItem>
     *
     * @return
     */
    public ArrayList<ImageItem> loadAllProjectIcons()
    {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM ProjectIcons";
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    ImageItem item = new ImageItem();
                    item.setImageDatabaseId(c.getInt(c.getColumnIndex("_id")));
                    item.setTitle(getStringResourceValueByResourceName(c.getString(c.getColumnIndex("name"))));
                    item.setImage(loadProjectIcon(c.getString(c.getColumnIndex("path"))));
                    imageItems.add(item);
                } while (c.moveToNext());
            }
        }
        return imageItems;
    }

    /**
     * Loads a project Icon by its path
     *
     * @param path
     * @return
     */
    public Bitmap loadProjectIcon(String path)
    {
        AssetManager assetManager = this.context.getAssets();
        InputStream istr;
        Bitmap projectIcon = null;
        try
        {
            istr = assetManager.open(path);
            projectIcon = BitmapFactory.decodeStream(istr);
        } catch (IOException e)
        {
            // handle exception
        }
        return projectIcon;
    }

    /**
     * Set the deletion Flag to a project. For later deletion of the project
     * <p/>
     * Note: isdeleted 0 = false ; 1 = true
     *
     * @param projectId
     */
    public void setDeleteFlagForProject(int projectId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //Update Project Name
        ContentValues args = new ContentValues();
        args.put("is_deleted", 1);
        db.update("Projects", args, "_id=" + projectId, null);
        db.close();
    }

    /**
     * Completly deletes a project from the database
     *
     * @param projectId
     */
    public void deleteProjectFromDatabase(int projectId)
    {
        String selectQuery = String.format("SELECT * FROM Projects WHERE _id = %d", projectId);
        SQLiteDatabase db = this.getReadableDatabase();
        int projectDetailsId = 0;
        String estimationMethod = null;
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                projectDetailsId = c.getInt(c.getColumnIndex("project_details_id"));
                estimationMethod = getEstimationMethodNameById(String.valueOf(c.getInt(c.getColumnIndex("estimation_method_id"))));

            }
        }
        db = this.getWritableDatabase();
        db.delete("Projects", "_id = " + projectId, null);

        int estimationItemsId = 0;
        int descriptionId = 0;
        int propertiesId = 0;
        selectQuery = String.format("SELECT * FROM ProjectDetails WHERE _id = %d", projectDetailsId);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                estimationItemsId = c.getInt(c.getColumnIndex("estimation_items_id"));
                descriptionId = c.getInt(c.getColumnIndex("description_id"));
                propertiesId = c.getInt(c.getColumnIndex("project_properties_id"));
            }
        }
        db.delete("ProjectDetails", "_id = " + projectDetailsId, null);
        db.delete("ProjectDescriptions", "_id = " + descriptionId, null);
        db.delete("ProjectProperties", "_id = " + propertiesId, null);

        if (estimationMethod.equals(context.getString(R.string.estimation_technique_function_point)))
        {
            int inputId = 0;
            int requestId = 0;
            int outputId = 0;
            int datasetId = 0;
            int referenceId = 0;
            selectQuery = String.format("SELECT * FROM FunctionPointEstimationItems WHERE _id = %d", estimationItemsId);
            try (Cursor c = db.rawQuery(selectQuery, null))
            {
                if (c.moveToFirst())
                {
                    inputId = c.getInt(c.getColumnIndex("InputDataItems_id"));
                    requestId = c.getInt(c.getColumnIndex("RequestItems_id"));
                    outputId = c.getInt(c.getColumnIndex("OutputItems_id"));
                    datasetId = c.getInt(c.getColumnIndex("DatasetItems_id"));
                    referenceId = c.getInt(c.getColumnIndex("ReferenceDataItems_id"));
                }
            }
            db.delete("FunctionPointEstimationItems", "_id = " + estimationItemsId, null);
            db.delete("InputDataItems", "_id = " + inputId, null);
            db.delete("RequestItems", "_id = " + requestId, null);
            db.delete("OutputItems", "_id = " + outputId, null);
            db.delete("DatasetItems", "_id = " + datasetId, null);
            db.delete("ReferenceDataItems", "_id = " + referenceId, null);
        }
        db.close();
    }

    /**
     * Updates the Project Informations. Not Included are Estimation Items and InfluenceFactor Sets
     *
     * @param project
     */
    public void updateExistingProjectInformations(Project project)
    {
        Log.d("INFO", "updateExistingProjectInformations for " + project.getTitle());
        SQLiteDatabase db = this.getWritableDatabase();

        //Update Project Name
        ContentValues args = new ContentValues();
        args.put("name", project.getTitle());
        db.update("Projects", args, "_id=" + project.getProjectId(), null);

        //Load the ProjectDetailsId
        int detailsId = getProjectDetailsId(project.getProjectId());

        db = this.getWritableDatabase();
        //Update Project Icon and evaluated days
        args = new ContentValues();
        args.put("icon_id", Integer.valueOf(project.getIconId()));
        args.put("evaluated_person_days", project.getEvaluatedPersonDays());
        db.update("ProjectDetails", args, "_id=" + detailsId, null);

        //Load Properties and Description IDs
        int propertiesId = 0;
        int descriptionId = 0;
        String selectQuery = String.format("SELECT * FROM ProjectDetails WHERE _id = %d", detailsId);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                propertiesId = c.getInt(c.getColumnIndex("project_properties_id"));
                descriptionId = c.getInt(c.getColumnIndex("description_id"));
            }
        }

        //Update Project Description
        args = new ContentValues();
        args.put("text", project.getProjectDescription());
        db.update("ProjectDescriptions", args, "_id=" + descriptionId, null);

        //Update ProjectProperties
        int marketId = getPropertyIdFromTable("DevelopmentMarkets", project.getProjectProperties().getMarket());
        int devkindId = getPropertyIdFromTable("DevelopmentTypes", project.getProjectProperties().getDevelopmentKind());
        int methologyId = getPropertyIdFromTable("ProcessMethologies", project.getProjectProperties().getProcessMethology());
        int langId = getPropertyIdFromTable("ProgrammingLanguages", project.getProjectProperties().getProgrammingLanguage());
        int platformId = getPropertyIdFromTable("Platforms", project.getProjectProperties().getPlatform());
        int industrySectId = getPropertyIdFromTable("IndustrySectors", project.getProjectProperties().getIndustrySector());
        int architectureId = getPropertyIdFromTable("SoftwareArchitecturePatterns", project.getProjectProperties().getArchitecture());
        db.close();

        //TODO: Property Werte werden nicht übernommen. Werte richtig jedoch für jede Spalte 1. Fehler beim Daten holen
        SQLiteDatabase db2 = this.getWritableDatabase();
        args = new ContentValues();
        args.put("DevelopmentMarket_id", marketId);
        args.put("DevelopmentKind_id", devkindId);
        args.put("ProcessMethology_id", methologyId);
        args.put("ProgrammingLanguage_id", langId);
        args.put("Platform_id", platformId);
        args.put("IndustrySector_id", industrySectId);
        args.put("Architecture_id", architectureId);
        //long i = db2.update("ProjectProperties", args, "_id= '" + propertiesId+"'", null);
        long i = db2.update("ProjectProperties", args, "_id= ?", new String[]{String.valueOf(propertiesId)});
        db2.close();
    }

    /**
     * Update the ID of the influence Factor set used by a project
     *
     * @param project
     */
    public void updateExistingProjectInfluenceFactor(Project project)
    {
        int detailsId = getProjectDetailsId(project.getProjectId());
        SQLiteDatabase db = this.getWritableDatabase();
        //Update Project Icon and evaluated days
        ContentValues args = new ContentValues();
        int setId = loadInfluenceFactorSetIdByName(project.getInfluencingFactor().getInfluenceFactorSetName(), getEstimationMethodId(project.getEstimationMethod()));
        args.put("influence_factorset_id", setId);
        db.update("ProjectDetails", args, "_id=" + detailsId, null);

    }

    private int loadInfluenceFactorSetIdByName(String name, int estMethodId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int infSetId = 0;
        String selectQuery = String.format("SELECT * FROM InfluenceFactors WHERE name = '%s' AND estimation_method_id = %d", name, estMethodId);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                infSetId = c.getInt(c.getColumnIndex("_id"));
            }
        }
        return infSetId;
    }

    /**
     * Get the projectDetailsID of a project
     *
     * @param projectId
     * @return
     */
    public int getProjectDetailsId(int projectId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int detailsId = 0;
        String selectQuery = String.format("SELECT * FROM Projects WHERE _id = %d", projectId);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                detailsId = c.getInt(c.getColumnIndex("project_details_id"));
            }
        }
        return detailsId;
    }

    public void terminateProject(Project project)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Update Project Icon and evaluated days
        ContentValues args = new ContentValues();
        args.put("is_terminated", 1);
        db.update("Projects", args, "_id=" + project.getProjectId(), null);
        if (project.getEstimationMethod().equals(context.getString(R.string.estimation_technique_function_point)))
        {
            int tableId = getNextIdFromTable("FunctionPointProductivity");
            if (tableId < 10)
            {
                tableId = 3500;
            }
            ContentValues insertValues = new ContentValues();
            insertValues.put("_id", tableId);
            insertValues.put("project_id", project.getProjectId());
            insertValues.put("influence_factor_set_id", loadInfluenceFactorSetIdByName(project.getInfluencingFactor().getInfluenceFactorSetName(), getEstimationMethodId(project.getEstimationMethod())));
            insertValues.put("terminated_days", project.getFinalPersonDays());
            db = this.getWritableDatabase();
            db.insert("FunctionPointProductivity", null, insertValues);
        }
        db.close();
    }

    /**
     * Returns the amount of terminated Function Point Projects from the table FunctionPointProductivity
     * <p/>
     * Returns 0 if there is no terminated Project yet.
     *
     * @return
     */
    public int getAmountTerminatedFunctionPointProject()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int amountTerminatedProjects = 0;
        String selectQuery = "SELECT COUNT(*) FROM FunctionPointProductivity";
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                amountTerminatedProjects = c.getInt(0);
            }
        }
        return amountTerminatedProjects;
    }

    /**
     * Evaluate the estimated person days with the base productivity from the database
     *
     * @param project
     * @return
     */
    public double evaluateFunctionPointPersonDaysWithBaseProductivity(Project project)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        double evaluatedPoints = project.getEvaluatedPoints();
        double pointsPerDay = 1.0;
        String selectQuery = String.format("SELECT * FROM FunctionPointBaseProductivity WHERE %s>= min_fp AND %s< max_fp", evaluatedPoints, evaluatedPoints);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                pointsPerDay = (double) c.getInt(c.getColumnIndex("points_per_day"));
            }
        }
        if (pointsPerDay == 1)
        {
            selectQuery = String.format("SELECT * FROM FunctionPointBaseProductivity WHERE min_fp IS NULL AND %s< max_fp", evaluatedPoints);
            try (Cursor c = db.rawQuery(selectQuery, null))
            {
                if (c.moveToFirst())
                {
                    pointsPerDay = (double) c.getInt(c.getColumnIndex("points_per_day"));
                }
            }
        }
        if (pointsPerDay == 1)
        {
            selectQuery = String.format("SELECT * FROM FunctionPointBaseProductivity WHERE %s>= min_fp AND max_fp IS NULL", evaluatedPoints);
            try (Cursor c = db.rawQuery(selectQuery, null))
            {
                if (c.moveToFirst())
                {
                    pointsPerDay = (double) c.getInt(c.getColumnIndex("points_per_day"));
                }
            }
        }

        return roundDoubleTwoDecimals(evaluatedPoints / pointsPerDay);
    }

    /**
     * evaluate the person days with already estimated projects
     *
     * @param project
     * @return
     */
    public double evaluateFunctionPointPersonDaysWithExistingProductivity(Project project)
    {
        double evaluatedDays = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<FunctionPointProductivityItem> functionPointProductivityItems = new ArrayList<>();
        int influenceFactorsetId = loadInfluenceFactorSetIdByName(project.getInfluencingFactor().getInfluenceFactorSetName(), getEstimationMethodId(project.getEstimationMethod()));
        String selectQuery = String.format("SELECT * FROM FunctionPointProductivity WHERE influence_factor_set_id = %d", influenceFactorsetId);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    FunctionPointProductivityItem item = new FunctionPointProductivityItem();
                    item.setId(c.getInt(c.getColumnIndex("_id")));
                    item.setProject_id(c.getInt(c.getColumnIndex("project_id")));
                    item.setInfluenceFactorsetId(c.getInt(c.getColumnIndex("influence_factor_set_id")));
                    item.setTerminatedDays(c.getInt(c.getColumnIndex("terminated_days")));
                    item.setFunctionPoints(loadProjectFunctionPoints(item.getProject_id()));
                    item.setPointsPerDay(item.getFunctionPoints() / item.getTerminatedDays());
                    functionPointProductivityItems.add(item);
                } while (c.moveToNext());
            }
        }
        FunctionPointProductivityItem smallerItem = null;
        for (FunctionPointProductivityItem item : functionPointProductivityItems)
        {
            if (item.getFunctionPoints() < project.getEvaluatedPoints())
            {
                if (smallerItem == null)
                {
                    smallerItem = item;
                } else if (item.getFunctionPoints() > smallerItem.getFunctionPoints())
                {
                    smallerItem = item;
                }
            }
        }
        FunctionPointProductivityItem biggerItem = null;
        for (FunctionPointProductivityItem item : functionPointProductivityItems)
        {
            if (item.getFunctionPoints() > project.getEvaluatedPoints())
            {
                if (biggerItem == null)
                {
                    biggerItem = item;
                } else if (item.getFunctionPoints() < biggerItem.getFunctionPoints())
                {
                    biggerItem = item;
                }
            }
        }

        if (biggerItem == null)
        {
            biggerItem = new FunctionPointProductivityItem();
            selectQuery = String.format("SELECT * FROM FunctionPointBaseProductivity WHERE %s>= min_fp AND %s< max_fp", project.getEvaluatedPoints(), project.getEvaluatedPoints());
            try (Cursor c = db.rawQuery(selectQuery, null))
            {
                if (c.moveToFirst())
                {
                    biggerItem.setPointsPerDay((double) c.getInt(c.getColumnIndex("points_per_day")));
                }
            }
        }
        if (smallerItem == null)
        {
            biggerItem = new FunctionPointProductivityItem();
            selectQuery = String.format("SELECT * FROM FunctionPointBaseProductivity WHERE %s< min_fp AND %s>= max_fp", project.getEvaluatedPoints(), project.getEvaluatedPoints());
            try (Cursor c = db.rawQuery(selectQuery, null))
            {
                if (c.moveToFirst())
                {
                    biggerItem.setPointsPerDay((double) c.getInt(c.getColumnIndex("points_per_day")));
                }
            }
        }
        double averagePointsPerDay = (smallerItem.getPointsPerDay() + biggerItem.getPointsPerDay()) / 2;
        averagePointsPerDay = roundDoubleTwoDecimals(averagePointsPerDay);
        evaluatedDays = roundDoubleTwoDecimals(project.getEvaluatedPoints() / averagePointsPerDay);

        db.close();
        return evaluatedDays;
    }

    private double loadProjectFunctionPoints(int project_id)
    {
        double points = 0.0;
        Project p = loadProjectById(context, String.valueOf(project_id));
        points = p.getEvaluatedPoints();
        return points;
    }

    /**
     * Round a double value to two  decimal numbers
     * <p/>
     * e.g. 3.34
     *
     * @param d
     * @return
     */
    public double roundDoubleTwoDecimals(double d)
    {
        //DecimalFormat twoDForm = new DecimalFormat("#.##");
        //return Double.valueOf(twoDForm.format(d));
        //Changed to new rounding method because of some Exception java.lang.NumberFormatException: Invalid double: "0,78"
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Load all Help Items from the Database and the content from the XML file
     *
     * @return
     */
    public ArrayList<HelpArticleItem> loadAllHelpItems()
    {
        ArrayList<HelpArticleItem> helpArticleItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM HelpArticles";
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    HelpArticleItem item = new HelpArticleItem();
                    item.setId(c.getInt(c.getColumnIndex("_id")));
                    item.setNameTag(c.getString(c.getColumnIndex("name_string")));
                    helpArticleItems.add(item);
                } while (c.moveToNext());
            }
        }

        helpArticleItems = xmlHelper.loadHelpArticlesFromXML(helpArticleItems);

        db.close();
        return helpArticleItems;
    }


    public ArrayList<Project> loadActiveProjectsByEstimationMethodAndInfluenceSet(String selectedEstimationMethod, String selectedInfluenceFactorSet)
    {
        ArrayList<Project> projects = new ArrayList<>();

        int estimationId = getEstimationMethodId(selectedEstimationMethod);

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = String.format("SELECT * FROM Projects WHERE estimation_method_id = %d AND is_deleted = 0", estimationId);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    Project p = loadProjectById(context, String.valueOf(c.getInt(c.getColumnIndex("_id"))));
                    if (selectedInfluenceFactorSet.equals("All"))
                    {
                        projects.add(p);
                    } else if (p.getInfluencingFactor().getInfluenceFactorSetName().equals(selectedInfluenceFactorSet))
                    {
                        projects.add(p);
                    }

                } while (c.moveToNext());
            }
        }

        db.close();


        return projects;
    }

    public ArrayList<EstimatedProjectItem> loadProjectsEstimationMethodStatistic()
    {
        ArrayList<EstimatedProjectItem> projects = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM EstimationMethod";
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    int methodId = c.getInt(c.getColumnIndex("_id"));
                    String methodName = getEstimationMethodNameById(String.valueOf(methodId));
                    db = this.getReadableDatabase();
                    int projectsCount = 0;
                    String selectQuery2 = String.format("SELECT * FROM Projects WHERE estimation_method_id = %d", methodId);
                    try (Cursor c2 = db.rawQuery(selectQuery2, null))
                    {
                        if (c2.moveToFirst())
                        {
                            do
                            {
                                projectsCount++;
                            } while (c2.moveToNext());
                        }
                    }
                    projects.add(new EstimatedProjectItem(methodName, projectsCount));
                } while (c.moveToNext());
            }
        }

        db.close();

        return projects;
    }

    /**
     * Returns Active and Terminated Projects
     * <p/>
     * First Value are the active Projects and the second are the terminated Projects
     * <p/>
     * Does not use the deleted projects
     *
     * @return
     */
    public ArrayList<Integer> loadActiveAndTerminatedProjectsStatistic()
    {
        ArrayList<Integer> projects = new ArrayList<>();

        int active = 0;
        int terminated = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM Projects WHERE is_deleted = 0";
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    int isterminated = c.getInt(c.getColumnIndex("is_terminated"));
                    if (isterminated == 0)
                    {
                        terminated++;
                    } else
                    {
                        active++;
                    }
                } while (c.moveToNext());
            }
        }

        db.close();
        projects.add(active);
        projects.add(terminated);

        return projects;
    }

    public ArrayList<PropertyProjects> loadPropertyStatistic(String tablename, String propertyColumnname)
    {
        ArrayList<PropertyProjects> values = new ArrayList<>();

        int propertyId = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = String.format("SELECT * FROM %s", tablename);
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    PropertyProjects p = new PropertyProjects();
                    propertyId = c.getInt(c.getColumnIndex("_id"));
                    p.setPropertyName(getStringResourceValueByResourceName(c.getString(c.getColumnIndex("name"))));
                    db = this.getReadableDatabase();
                    String selectQuery2 = String.format("SELECT COUNT(*) FROM ProjectProperties WHERE %s = %d", propertyColumnname, propertyId);
                    try (Cursor c2 = db.rawQuery(selectQuery2, null))
                    {
                        if (c2.moveToFirst())
                        {
                            p.setNumberOfProjects(c2.getInt(0));
                        }
                    }
                    values.add(p);
                } while (c.moveToNext());
            }
        }

        db.close();

        return values;
    }

    public XmlHelper getXmlHelper()
    {
        return xmlHelper;
    }

    public ArrayList<Project> searchAllProjectsWithName(Context context, String searchQuery)
    {
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<String> searchTerms = new ArrayList<>();
        for (String word : searchQuery.split(" "))
        {
            searchTerms.add(word);
        }

        String selectQuery = "SELECT * FROM Projects WHERE is_deleted = 0;";

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    Project p = new Project(context);
                    p.setTitle(c.getString(c.getColumnIndex("name")));

                    for (String key : searchTerms)
                    {
                        if (p.getTitle().toLowerCase().contains(key.toLowerCase()))
                        {
                            p.setEstimationMethod(getEstimationMethodNameById(c.getString(c.getColumnIndex("estimation_method_id"))));
                            int detailsId = c.getInt(c.getColumnIndex("project_details_id"));

                            p.setImage(loadProjectImageFromProjectDetails(detailsId));
                            p.setCreationDate(c.getString(c.getColumnIndex("created_at")));

                            p.setProjectId(c.getInt(c.getColumnIndex("_id")));
                            projects.add(p);
                            break;
                        }
                    }
                } while (c.moveToNext());
            }
        }
        db.close();
        return projects;
    }

    public int completeDeleteProjects()
    {
        String selectQuery = "SELECT * FROM Projects WHERE is_deleted = 1;";

        int project = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                do
                {
                    project++;
                    int id = c.getInt(c.getColumnIndex("_id"));
                    deleteProjectFromDatabase(id);
                } while (c.moveToNext());
            }
        }
        db.close();
        return project;
    }

    public ArrayList<Project> getAllDeletedProjects(Activity activity)
    {
        ArrayList<Project> projects = new ArrayList<>();

        String selectQuery = "SELECT * FROM Projects WHERE is_deleted = 1;";

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

                    p.setImage(loadProjectImageFromProjectDetails(detailsId));
                    p.setCreationDate(c.getString(c.getColumnIndex("created_at")));

                    p.setProjectId(c.getInt(c.getColumnIndex("_id")));
                    projects.add(p);
                } while (c.moveToNext());
            }
        }
        db.close();
        return projects;
    }

    public void recoverProjects(ArrayList<Project> recoverProjectsList)
    {
        for (Project p : recoverProjectsList)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues args = new ContentValues();
            args.put("is_deleted", 0);
            db.update("Projects", args, "_id=" + p.getProjectId(), null);
            db.close();
        }
    }

    public ArrayList<Project> getAllActiveProjectsWithFilter(Activity activity, ProjectFilter filter)
    {
        ArrayList<Project> projects = new ArrayList<>();

        String selectQuery = "SELECT * FROM Projects WHERE is_deleted = 0";

        if (!filter.getEstimationMethod().isEmpty() && !filter.getEstimationMethod().equals("") && !filter.getEstimationMethod().equals(context.getString(R.string.filter_none)))
        {
            selectQuery = selectQuery + " AND estimation_method_id = " + getEstimationMethodId(filter.getEstimationMethod()) + ";";

        }

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

                    p.setImage(loadProjectImageFromProjectDetails(detailsId));
                    p.setCreationDate(c.getString(c.getColumnIndex("created_at")));

                    p.setProjectId(c.getInt(c.getColumnIndex("_id")));
                    projects.add(p);
                } while (c.moveToNext());
            }
        }
        db.close();
        return projects;
    }

    public int getEstimationMethodDistance(String estimationMethod1, String estimationMethod2)
    {
        int id1 = getEstimationMethodId(estimationMethod1);
        int id2 = getEstimationMethodId(estimationMethod2);

        int distance = 0;

        String selectQuery1 = String.format("SELECT * FROM EstimationMethodComparison WHERE em_id_1=%d AND em_id_2 =%d;", id1, id2);
        String selectQuery2 = String.format("SELECT * FROM EstimationMethodComparison WHERE em_id_1=%d AND em_id_2 =%d;", id2, id1);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery1, null))
        {
            if (c.moveToFirst())
            {
                distance = c.getInt(c.getColumnIndex("em_distance"));

            } else
            {
                try (Cursor c2 = db.rawQuery(selectQuery2, null))
                {
                    if (c2.moveToFirst())
                    {
                        distance = c2.getInt(c.getColumnIndex("em_distance"));
                    }
                }
            }
        }
        db.close();

        return distance;
    }

    public int getPropertyDistance(String baseTable, String tableComparisonName, String column1, String column2, String idColumn, String market1, String market2)
    {
        int id1 = getPropertyIdFromTable(baseTable, market1);
        int id2 = getPropertyIdFromTable(baseTable, market2);

        int distance = 0;

        String selectQuery1 = String.format("SELECT * FROM %s WHERE %s=%d AND %s=%d;", tableComparisonName, column1, id1, column2, id2);
        String selectQuery2 = String.format("SELECT * FROM %s WHERE %s=%d AND %s=%d;", tableComparisonName, column1, id2, column2, id1);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery1, null))
        {
            if (c.moveToFirst())
            {
                distance = c.getInt(c.getColumnIndex(idColumn));

            } else
            {
                try (Cursor c2 = db.rawQuery(selectQuery2, null))
                {
                    if (c2.moveToFirst())
                    {
                        distance = c2.getInt(c.getColumnIndex(idColumn));
                    }
                }
            }
        }
        db.close();

        return distance;
    }

    public ProgrammingLanguageProperty loadProgrammingLanguageProperty(String programmingLanguage)
    {
        ProgrammingLanguageProperty programmingLanguageProperty = new ProgrammingLanguageProperty();

        int languageId = getPropertyIdFromTable("ProgrammingLanguages", programmingLanguage);

        String selectQuery = String.format("SELECT * FROM ProgrammingLanguageProperties WHERE _id = %d", languageId);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                programmingLanguageProperty.setId(languageId);
                programmingLanguageProperty.setImperative(c.getInt(c.getColumnIndex("imperative")));
                programmingLanguageProperty.setObjectoriented(c.getInt(c.getColumnIndex("objectoriented")));
                programmingLanguageProperty.setFunctional(c.getInt(c.getColumnIndex("functional")));
                programmingLanguageProperty.setProcedural(c.getInt(c.getColumnIndex("procedural")));
                programmingLanguageProperty.setGeneric(c.getInt(c.getColumnIndex("generic")));
                programmingLanguageProperty.setReflective(c.getInt(c.getColumnIndex("reflective")));
                programmingLanguageProperty.setEventdriven(c.getInt(c.getColumnIndex("eventdriven")));
                programmingLanguageProperty.setFailsafe(c.getInt(c.getColumnIndex("failsafe")));
                programmingLanguageProperty.setTypeSafety(c.getInt(c.getColumnIndex("type_safety")));
                programmingLanguageProperty.setTypeExpression(c.getInt(c.getColumnIndex("type_expression")));
                programmingLanguageProperty.setTypeCompatibility(c.getInt(c.getColumnIndex("type_compatibility")));
                programmingLanguageProperty.setTypeChecking(c.getInt(c.getColumnIndex("type_checking")));

            }
        }
        db.close();
        return programmingLanguageProperty;
    }

    /**
     * Updates all Items from Project to Function Point Tables
     * <p/>
     * WARNING: Only use with function point projects
     *
     * @param estimationItems
     */
    public void updateFunctionPointEstimationItems(ArrayList<EstimationItem> estimationItems)
    {
        for (EstimationItem item : estimationItems)
        {
            updateFunctionPointEstimationItem((FunctionPointItem) item);
        }
    }

    public boolean setDeleteFlagForInfluenceFactor(int influenceFactorSetId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        //Update Project Icon and evaluated days
        ContentValues args = new ContentValues();
        args.put("is_deleted", 1);
        db.update("InfluenceFactors", args, "_id=" + influenceFactorSetId, null);
        return true;
    }

    public boolean deleteInfluenceFactor(int influenceFactorSetId)
    {
        boolean isDeleted = false;

        String selectQuery = String.format("SELECT * FROM InfluenceFactors WHERE _id = '%s'", influenceFactorSetId);
        SQLiteDatabase db = this.getReadableDatabase();
        int influenceFactorDetailsId = 0;
        String estimationMethod = null;
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                influenceFactorDetailsId = c.getInt(c.getColumnIndex("influence_factor_id"));
                estimationMethod = getEstimationMethodNameById(String.valueOf(c.getInt(c.getColumnIndex("estimation_method_id"))));
                isDeleted = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            isDeleted = false;
        }
        db = this.getWritableDatabase();
        db.delete("InfluenceFactors", "_id = " + influenceFactorSetId, null);

        if (estimationMethod.equals(context.getString(R.string.estimation_technique_function_point)))
        {

            db = this.getWritableDatabase();
            db.delete("FunctionPointInfluenceFactor", "_id = " + influenceFactorDetailsId, null);
        }
        db.close();
        return isDeleted;
    }

    public int getInfluenceFactorDetailsId(int oldFactorId)
    {
        int detailsId = 2001;
        String selectQuery = String.format("SELECT * FROM InfluenceFactors WHERE _id = '%s'", oldFactorId);
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                detailsId = c.getInt(c.getColumnIndex("influence_factor_id"));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return detailsId;
    }

    public SoftwareArchitectureProperty loadArchitectureProperty(String architecture)
    {
        SoftwareArchitectureProperty softwareArchitectureProperty = new SoftwareArchitectureProperty();

        int architectureId = getPropertyIdFromTable("SoftwareArchitecturePatterns", architecture);

        String selectQuery = String.format("SELECT * FROM SoftwareArchitecturePatternsProperties WHERE _id = %d", architectureId);

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor c = db.rawQuery(selectQuery, null))
        {
            if (c.moveToFirst())
            {
                softwareArchitectureProperty.setId(architectureId);
                softwareArchitectureProperty.setCategory(c.getInt(c.getColumnIndex("category_id")));
                softwareArchitectureProperty.setOverallAgility(c.getInt(c.getColumnIndex("overallAgility")));
                softwareArchitectureProperty.setEaseOfDeployment(c.getInt(c.getColumnIndex("easeOfDeployment")));
                softwareArchitectureProperty.setTestability(c.getInt(c.getColumnIndex("testability")));
                softwareArchitectureProperty.setPerformance(c.getInt(c.getColumnIndex("performance")));
                softwareArchitectureProperty.setScalability(c.getInt(c.getColumnIndex("scalability")));
                softwareArchitectureProperty.setEaseOfDevelopment(c.getInt(c.getColumnIndex("easeOfDevelopment")));

            }
        }
        db.close();
        return softwareArchitectureProperty;
    }
}
