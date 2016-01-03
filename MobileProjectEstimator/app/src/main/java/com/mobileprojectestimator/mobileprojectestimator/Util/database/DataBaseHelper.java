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
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

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


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context)
    {

        super(context, DB_NAME, null, 1);
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

        if (dbExist)
        {
            //do nothing - database already exist
        } else
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

                throw new Error("Error copying database");

            }
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

        return checkDB != null ? true : false;
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
        if (dbExist)
        {

        } else
        {
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            } catch (IOException e)
            {
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
                c.mkdir();
            }

            // ---if db file doesn't exist then create it---
            if (!f.exists())
            {
                copyDataBase();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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

        String selectQuery = String.format("SELECT * FROM \"InfluenceFactors\" WHERE estimation_method_id = %d ORDER BY lower(name) ASC;", estimationId);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
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
        db.close();
        return influenceFactorItems;
    }

    /**
     * Log all table names of the database
     */
    public void logAllTableNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master  WHERE type='table'", null);
        if (c.moveToFirst())
        {
            while (!c.isAfterLast())
            {
                Log.d("INFO", "Table Name => " + c.getString(0));
                c.moveToNext();
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
        String query = String.format("SELECT _id FROM EstimationMethod WHERE name = '%s';", estimationMethod);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        int estimationId = c.getInt(c.getColumnIndex("_id"));

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

        Cursor c = db.rawQuery(selectQuery, null);
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

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst())
        {
            do
            {
                estimationMethodNames.add(c.getString(c.getColumnIndex("name")));
            } while (c.moveToNext());
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

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        int factorItemId = c.getInt(c.getColumnIndex("influence_factor_id"));

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

        if (selectedEstimationMethod.equals(context.getString(R.string.function_point)))
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
            Cursor c = db.rawQuery(selectCreatedIdQuery, null);
            if (c != null)
                c.moveToFirst();

            influenceFactorDatabaseId = c.getInt(c.getColumnIndex("_id"));
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

    }

    public Project loadProjectById(Context context, String projectId)
    {
        Project p = new Project(context);

        return p;
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

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        int factorItemId = c.getInt(0);

        db.close();

        if (factorItemId > 0)
        {
            return true;
        } else
        {
            return false;
        }
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

        Cursor c = db.rawQuery(selectQuery, null);
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
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        int iconId = c.getInt(c.getColumnIndex("icon_id"));

        selectQuery = String.format("SELECT * FROM ProjectIcons WHERE _id = %d", iconId);
        c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String path = c.getString(c.getColumnIndex("path"));
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
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String name = c.getString(1);

        db.close();
        return name;
    }
}
