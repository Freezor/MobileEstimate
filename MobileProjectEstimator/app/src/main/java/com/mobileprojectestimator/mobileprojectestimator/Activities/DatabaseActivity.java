package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Oliver Fries on 15.12.2015, 18:31.
 * Project: MobileProjectEstimator
 * Extends AppCompatActivity
 * For all Activities that need access to the database
 */
public class DatabaseActivity extends AppCompatActivity
{
    /**
     * The Database Helper Object for sending queries to the database
     */
    protected DataBaseHelper databaseHelper;

    /**
     * Initialise the Database Helper class and loads the database
     */
    protected void initDatabase()
    {
        Log.d("Info", "Database Initialisation");
        this.databaseHelper = new DataBaseHelper(this);

        try
        {

            databaseHelper.createDataBase();

        } catch (IOException ioe)
        {

            throw new Error("Unable to create database");

        }

        try
        {

            databaseHelper.openDataBase();

        } catch (SQLException sqle)
        {
            Log.d("ERROR", sqle.toString());
        }

        databaseHelper.logDatabaseInformation();
    }

    public void hideKeyboard()
    {
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
