package com.mobileprojectestimator.mobileprojectestimator.Util;

import android.app.Activity;
import android.util.Log;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.ProgrammingLanguageProperty;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.RelatedProject;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Oliver Fries on 28.01.2016, 11:22.
 * Project: MobileProjectEstimator
 */
public class ProjectRelationSolver
{
    private Project project;
    private final ArrayList<Project> allProjects;
    private final Activity activity;
    private ProgrammingLanguageProperty baseLanguageProperty;

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
        for (Project p : allProjects)
        {
            if (p.getProjectId() == project.getProjectId())
            {
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

        baseLanguageProperty = databaseHelper.loadProgrammingLanguageProperty(project.getProjectProperties().getProgrammingLanguage());

        for (Project p : allProjects)
        {
            p = databaseHelper.loadProjectById(activity, String.valueOf(p.getProjectId()));
            RelatedProject relatedProject = new RelatedProject(activity);
            relatedProject.convertFromProject(p);
            relatedProject.setRelatedPercentage(compareWithChosenProject(p));
            if (relatedProject.getRelatedPercentage() >= relationBorder)
            {
                relatedProjects.add(relatedProject);
            }
        }
        Collections.sort(relatedProjects, new Comparator<RelatedProject>()
        {
            @Override
            public int compare(RelatedProject p1, RelatedProject p2)
            {
                return (int) (p1.getRelatedPercentage() - p2.getRelatedPercentage()); // Ascending
            }

        });
        return relatedProjects;
    }

    private double compareWithChosenProject(Project p)
    {
        double estimationMethodDistance = databaseHelper.getEstimationMethodDistance(project.getEstimationMethod(), p.getEstimationMethod());
        //TODO: Change with future estimation Methods
        double factorSetDistance = 60;//Set to max possible factor distance
        if (estimationMethodDistance == 0)
        {
            factorSetDistance = Math.abs(project.getSumOfInfluences() - p.getSumOfInfluences());
        }
        double developmentMarketDistance = databaseHelper.getPropertyDistance("DevelopmentMarkets", "DevelopmentMarketComparison", "market_id_1", "market_id_2", "comparison_distance", project.getProjectProperties().getMarket(), p.getProjectProperties().getMarket());
        double developmentTypeDistance = databaseHelper.getPropertyDistance("DevelopmentTypes", "DevelopmentTypeComparison", "dev_type_1", "dev_type_2", "comparison_distance", project.getProjectProperties().getDevelopmentKind(), p.getProjectProperties().getDevelopmentKind());
        double processMethologyDistance = databaseHelper.getPropertyDistance("ProcessMethologies", "ProcessMethologyComparison", "pm_id_1", "pm_id_2", "comparison_distance", project.getProjectProperties().getProcessMethology(), p.getProjectProperties().getProcessMethology());
        double platformDistance = databaseHelper.getPropertyDistance("Platforms", "PlatformPortingCosts", "from_platform_id", "to_platform_id", "costs", project.getProjectProperties().getPlatform(), p.getProjectProperties().getPlatform());
        double industrySectorDistance = 0;
        if (!project.getProjectProperties().getIndustrySector().equals(p.getProjectProperties().getIndustrySector()))
        {
            industrySectorDistance = 1;
        }
        int programmingLanguageConversionCosts = databaseHelper.getPropertyDistance("ProgrammingLanguages", "ProgrammingLanguageConversion", "pl_id_1", "pl_id_2", "conversion_cost", project.getProjectProperties().getProgrammingLanguage(), p.getProjectProperties().getProgrammingLanguage());
        ProgrammingLanguageProperty selectedProjectLanguageProperty = databaseHelper.loadProgrammingLanguageProperty(p.getProjectProperties().getProgrammingLanguage());
        double programmingLanguageDistance = estimateLanguageDistance(programmingLanguageConversionCosts, selectedProjectLanguageProperty);

        estimationMethodDistance*=2.0;
        factorSetDistance*=0.1;
        developmentMarketDistance*=1.5;
        developmentTypeDistance*=2.0;
        processMethologyDistance*=2.0;
        platformDistance*=1.5;
        industrySectorDistance*=4.0;
        programmingLanguageDistance*=1.2;

        double distanceSum = estimationMethodDistance+factorSetDistance+developmentMarketDistance+developmentTypeDistance+processMethologyDistance+platformDistance+industrySectorDistance+programmingLanguageDistance;
        double differencePercentage = 0.0;
        try{
            differencePercentage =databaseHelper.roundDoubleTwoDecimals(distanceSum/90)*100;
        } catch (Exception e){
            Log.d("Error","Relation Error "+e.getCause());
        }
        if(differencePercentage <0){
            return 0;
        } else {
            double relatedPercentage = 100.0 -differencePercentage;
            return relatedPercentage;
        }
    }

    private int estimateLanguageDistance(int programmingLanguageConversionCosts, ProgrammingLanguageProperty selectedProjectLanguageProperty)
    {
        int imp = selectedProjectLanguageProperty.getImperative() - baseLanguageProperty.getImperative();
        int obj = selectedProjectLanguageProperty.getObjectoriented() - baseLanguageProperty.getObjectoriented();
        int func = selectedProjectLanguageProperty.getFunctional() - baseLanguageProperty.getFunctional();
        int proc = selectedProjectLanguageProperty.getProcedural() - baseLanguageProperty.getProcedural();
        int gen = selectedProjectLanguageProperty.getGeneric() - baseLanguageProperty.getGeneric();
        int ref = selectedProjectLanguageProperty.getReflective() - baseLanguageProperty.getReflective();
        int evnt = selectedProjectLanguageProperty.getEventdriven() - baseLanguageProperty.getEventdriven();
        int fail = selectedProjectLanguageProperty.getFailsafe() - baseLanguageProperty.getFailsafe();
        int sfty = selectedProjectLanguageProperty.getTypeSafety() - baseLanguageProperty.getTypeSafety();
        int expr = selectedProjectLanguageProperty.getTypeExpression() - baseLanguageProperty.getTypeExpression();
        int comp = selectedProjectLanguageProperty.getTypeCompatibility() - baseLanguageProperty.getTypeCompatibility();
        int chk = selectedProjectLanguageProperty.getTypeChecking() - baseLanguageProperty.getTypeChecking();
        programmingLanguageConversionCosts += Math.abs(imp) + Math.abs(obj) + Math.abs(func) + Math.abs(proc) + Math.abs(gen) + Math.abs(ref) + Math.abs(evnt) + Math.abs(fail) + Math.abs(sfty) + Math.abs(expr) + Math.abs(comp) + Math.abs(chk);
        return programmingLanguageConversionCosts;
    }
}
