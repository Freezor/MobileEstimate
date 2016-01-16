package com.mobileprojectestimator.mobileprojectestimator.Server;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Xml;

import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.UserDatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * Created by Oliver Fries on 16.01.2016, 12:44.
 * Project: MobileProjectEstimator
 */
public class ServerConnector
{
    private DataBaseHelper applicationDatabase;
    private UserDatabaseHelper userDatabase;
    private Context context;

    public ServerConnector(Context c){
        this.context = c;
        initApplicationDatabase(c);
        initUserDatabase(c);
        userDatabase.logDatabaseInformation();
        userDatabase.logAllTableNames();
    }

    /**
     * Initialise the Database Helper class and loads the database
     */
    protected void initApplicationDatabase(Context c)
    {
        Log.d("Info", "Database Initialisation");
        this.applicationDatabase = new DataBaseHelper(c);

        try
        {

            applicationDatabase.createDataBase();

        } catch (IOException ioe)
        {

            throw new Error("Unable to create database");

        }

        try
        {

            applicationDatabase.openDataBase();

        } catch (SQLException sqle)
        {
            Log.d("ERROR",sqle.toString());
        }

        applicationDatabase.logDatabaseInformation();
    }

    /**
     * Initialise the Database Helper class and loads the database
     */
    protected void initUserDatabase(Context c)
    {
        Log.d("Info", "Database Initialisation");
        this.userDatabase = new UserDatabaseHelper(c);

        try
        {

            userDatabase.createDataBase();

        } catch (IOException ioe)
        {

            throw new Error("Unable to create database");

        }

        try
        {

            userDatabase.openDataBase();

        } catch (SQLException sqle)
        {
            Log.d("ERROR",sqle.toString());
        }

        userDatabase.logDatabaseInformation();
    }

    /**
     * Initialises the Connection to the server
     * @return
     */
    public boolean initConnection(){
        return true;
    }

    /**
     * Loads the messages from the server Message Queue and returns the amount of messages
     * @return
     */
    public int loadMessages(){
        return 1;
    }

    /**
     * Synchronises the database with the server Database
     * @return
     */
    public boolean synchronise(){
        loadHelpArticles();
        return true;
    }

    private void loadHelpArticles()
    {
        File dir = context.getFilesDir();
        File file = new File(dir, "help_data.xml");
        file.delete();

        final String xmlFile = "help_data.xml";
        try {
            FileOutputStream fileos= context.openFileOutput(xmlFile, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "helpitems");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "create_new_project");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("Create New Project");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("You can create a new project by selecting the + in the project overview");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "how_an_estimation_works");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("How an estimation works");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("This is how an estimation works");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("It just do");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "function_point_estimations");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("Function Point Estimation");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("A function point is a \"unit of measurement\" to express the amount of business functionality an information system (as a product) provides to a user. Function points measure software size. The cost (in dollars or hours) of a single unit is calculated from past projects");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("After each estimated project the next estimation will be more accurate");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "cocomo_estimations");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("Cocomo Project Estimation");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("The Constructive Cost Model (COCOMO) is an algorithmic software cost estimation model developed by Barry W. Boehm. The model uses a basic regression formula with parameters that are derived from historical project data and current as well as future project characteristics.");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "cocomo_2_estimations");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("Cocomo 2 Project Estimation");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("The Constructive Cost Model (COCOMO) is an algorithmic software cost estimation model developed by Barry W. Boehm. The model uses a basic regression formula with parameters that are derived from historical project data and current as well as future project characteristics.");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "project_comparison");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("Project Comparison");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("Projects can be compared with their properties. This will result in a list of projects which are more likely to each other and can give a more detailed insight how a similar project was estimated.");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "terminate_project");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("How to terminate a Project");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("After terminating a project this project can no more be edited. You have to insert the final project time.");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.startTag(null, "article").attribute(null, "name", "server_connection");
            xmlSerializer.startTag(null, "title");
            xmlSerializer.text("Server Connection");
            xmlSerializer.endTag(null, "title");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("The Server Connection is only available for users that got an account on our homepage.");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.startTag(null, "paragraph");
            xmlSerializer.text("With the server connection you can save your projects online and get access to our database to make more precise estimations.");
            xmlSerializer.endTag(null, "paragraph");
            xmlSerializer.endTag(null, "article");

            xmlSerializer.endTag(null, "helpitems");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
