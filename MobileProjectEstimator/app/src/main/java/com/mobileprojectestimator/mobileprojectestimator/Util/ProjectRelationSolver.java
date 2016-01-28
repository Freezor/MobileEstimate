package com.mobileprojectestimator.mobileprojectestimator.Util;

import android.app.Activity;
import android.util.Log;

import com.mobileprojectestimator.mobileprojectestimator.Activities.FindRelatedProjectsActivity;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.RelatedProject;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Oliver Fries on 28.01.2016, 11:22.
 * Project: MobileProjectEstimator
 */
public class ProjectRelationSolver
{
    private Project project;
    private final ArrayList<Project> allProjects;
    private final Activity activity;

    public ProjectRelationSolver(Activity activity, Project selectedProject, ArrayList<Project> projects)
    {
        this.activity = activity;
        this.project = selectedProject;
        this.allProjects = projects;
        initDatabase();
        refreshProjectsList();
    }

    private void refreshProjectsList()
    {
        for(Project p:  allProjects){
            if(p.getProjectId() == project.getProjectId()){
                allProjects.remove(p);
                break;
            }
        }
    }

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
        this.databaseHelper = new DataBaseHelper(activity);

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


    public ArrayList<RelatedProject> getRelatedProject(double relationBorder)
    {
        ArrayList<RelatedProject> relatedProjects = new ArrayList<>();
        project = databaseHelper.loadProjectById(activity, String.valueOf(project.getProjectId()));

        for(Project p:allProjects){
            p = databaseHelper.loadProjectById(activity, String.valueOf(p.getProjectId()));
            RelatedProject relatedProject = new RelatedProject(activity);
            relatedProject.convertFromProject(p);
            relatedProject.setRelatedPercentage(compareWithChosenProject(p));
            relatedProjects.add(relatedProject);
        }

        return relatedProjects;
    }

    private double compareWithChosenProject(Project p)
    {
        int estimationMethodDistance = databaseHelper.getEstimationMethodDistance(project.getEstimationMethod(),p.getEstimationMethod());

        return 0;
    }
}
