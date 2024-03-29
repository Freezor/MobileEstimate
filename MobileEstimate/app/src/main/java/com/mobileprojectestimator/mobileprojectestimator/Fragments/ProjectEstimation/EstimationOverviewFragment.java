package com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Oliver Fries on 02.11.2015, 15:37.
 * Project: MobileProjectEstimator
 */
public abstract class EstimationOverviewFragment extends Fragment
{
    /**
     * The Database Helper Object for sending queries to the database
     */
    protected DataBaseHelper databaseHelper;

    public abstract EstimationOverviewFragment newInstance(final Project p);

    /**
     * Initialise the Database Helper class and loads the database
     */
    protected void initDatabase()
    {
        Log.d("Info", "Database Initialisation");
        this.databaseHelper = new DataBaseHelper(this.getContext());
        try
        {
            databaseHelper.createDataBase();
        } catch (final IOException ioe)
        {
            throw new Error("Unable to create database");
        }
        try
        {
            databaseHelper.openDataBase();

        } catch (final SQLException sqle)
        {
            Log.d("ERROR", sqle.toString());
        }
        databaseHelper.logDatabaseInformation();
    }
}
