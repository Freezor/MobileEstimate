package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Oliver Fries on 01.11.2015, 17:47.
 * Project: MobileProjectEstimator
 */
public abstract class GuidedCreationFragment extends Fragment
{
    public DataBaseHelper databaseHelper;

    public abstract void onReloadViews(String text);

    public abstract GuidedCreationFragment newInstance(Project p);

    /**
     * Initialise the Database Helper class and loads the database
     */
    public void initDatabase()
    {
        Log.d("Info", "Database Initialisation");
        databaseHelper = new DataBaseHelper(getContext());

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
}
