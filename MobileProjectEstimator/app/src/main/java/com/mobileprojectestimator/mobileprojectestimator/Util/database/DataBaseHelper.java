package com.mobileprojectestimator.mobileprojectestimator.Util.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

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
            this.getReadableDatabase();

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
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

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
        projectEstimationDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
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
}
