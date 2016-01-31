package com.mobileprojectestimator.mobileprojectestimator.Server;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.UserDatabaseHelper;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        loadTextResources();
        return true;
    }

    private void loadTextResources()
    {
        File dir = context.getFilesDir();
        File file = new File(dir, "texts.xml");
        file.delete();

        final String xmlFile = "texts.xml";
        try {
            FileOutputStream fileos= context.openFileOutput(xmlFile, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "textresourcess");

            xmlSerializer.startTag(null, "category").attribute(null, "name", "function_point_estimation_items");
            xmlSerializer.startTag(null, "text").attribute(null, "name", "input_data_description");
            xmlSerializer.text("External Inputs (EI) - is an elementary process in which data crosses the boundary from outside to inside.  This data may come from a data input screen or another application. The data may be used to maintain one or more internal logical files.  The data can be either control information or business information.  If the data is control information it does not have to update an internal logical file.\n\n " +
                    "Multiplikatoren:\n " +
                    "simple = *3\n" +
                    "medium = *4\n" +
                    "complex = *6.");
            xmlSerializer.endTag(null, "text");
            xmlSerializer.startTag(null, "text").attribute(null, "name", "request_description");
            xmlSerializer.text("External Inquiry (EQ) - an elementary process with both input and output components that result in data retrieval from one or more internal logical files and external interface files.  The input process does not update any Internal Logical Files, and the output side does not contain derived data.\n\n" +
                    "Multiplikatoren:\n " +
                    "simple = *3\n" +
                    "medium = *4\n" +
                    "complex = *6.");
            xmlSerializer.endTag(null, "text");
            xmlSerializer.startTag(null, "text").attribute(null, "name", "output_description");
            xmlSerializer.text("External Outputs (EO) - an elementary process in which derived data passes across the boundary from inside to outside.   Additionally, an EO may update an ILF.  The data creates reports or output files sent to other applications.  These reports and files are created from one or more internal logical files and external interface file.\n\n" +
                    "Multiplikatoren:\n " +
                    "simple = *4\n" +
                    "medium = *5\n" +
                    "complex = *7.");
            xmlSerializer.endTag(null, "text");
            xmlSerializer.startTag(null, "text").attribute(null, "name", "dataset_description");
            xmlSerializer.text("Internal Logical Files (ILF’s) - a user identifiable group of logically related data that resides entirely within the applications boundary and is maintained through external inputs." +
                    "Multiplikatoren:\n " +
                    "simple = *7\n" +
                    "medium = *10\n" +
                    "complex = *15.");
            xmlSerializer.endTag(null, "text");
            xmlSerializer.startTag(null, "text").attribute(null, "name", "reference_data_description");
            xmlSerializer.text("External Interface Files (EIF’s) - a user identifiable group of logically related data that is used for reference purposes only. The data resides entirely outside the application and is maintained by another application. The external interface file is an internal logical file for another application.\n\n" +
                    "Multiplikatoren:\n " +
                    "simple = *5\n" +
                    "medium = *7\n" +
                    "complex = *10.");
            xmlSerializer.endTag(null, "text");
            xmlSerializer.endTag(null, "category");


            xmlSerializer.startTag(null, "category").attribute(null, "name", "function_point_influence_factor_items");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "integration_into_other_applications");
            xmlSerializer.text("The system will work with different applications and will send and receive data from other applications. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 - The application does not work work with other applications.\n" +
                    "1 - The application prepares Data to use in other systems and applications.\n" +
                    "2 - Data will be send to other systems or components and will be used there\n" +
                    "3 - The application and it´s data iwill be used directly in another system .\n" +
                    "4 - The integration in other applications takes place online but only in one direction.\n" +
                    "5 - The integration in other applications takes place online in both directions.");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "local_data_processing");
            xmlSerializer.text("The system will work with distributed data. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 - The application does not work work with distributed data.\n" +
                    "1 - The application prepares Data to use in other systems and applications.\n" +
                    "2 - Data will be send to other systems or components and will be used there.\n" +
                    "3 - The application and it´s data iwill be used directly in another system .\n" +
                    "4 - The integration in other applications takes place online but only in one direction.\n" +
                    "5 - The integration in other applications takes place online in both directions.");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "transaction_rate");
            xmlSerializer.text("A high transaction rate affects planing, development, installation and maintenance of the system. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 - There no extreme transactions to be expectet.\n" +
                    "1 - There are optional limitations but there is no need for much effort.\n" +
                    "2 - There will be extreme transactions once a week\n" +
                    "3 - There will be extreme transactions daily\n" +
                    "4 - The expected transaction rates are so high that an enormous effort in the design phase is needed.\n" +
                    "5 - The transactions rates that are set in the user and service requirements are so high, that special analysis tools need to be used. ");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "arithmetic_operation");
            xmlSerializer.text("The user has special requirements for the time and information flow-rate in this system. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 - The user doesn´t have special requirements.\n" +
                    "2 - The´re special requirements but they don´t need much effort.\n" +
                    "4 - Requirements are critical to the main time and the processing must be done to the next cycle.\n" +
                    "6 - The requirements are always critical and must be balanced with other applications.\n" +
                    "8 - The requirements are so critical that there has to be a special activity in the design phase.\n" +
                    "10 - The user requirements are so critical that there are special analysis tools needed.");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "control_procedure");
            xmlSerializer.text("NOT DONE YET. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 - .\n" +
                    "1 - .\n" +
                    "2 - .\n" +
                    "3 - .\n" +
                    "4 - .\n" +
                    "5 - .");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "exception_regulation");
            xmlSerializer.text("NOT DONE YET. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 -\n" +
                    "2 -\n" +
                    "4 -\n" +
                    "6 -\n" +
                    "8 -\n" +
                    "10 -");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "logic");
            xmlSerializer.text("NOT DONE YET. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 -\n" +
                    "2 -\n" +
                    "4 -\n" +
                    "6 -\n" +
                    "8 -\n" +
                    "10 -");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "processing_logic");
            xmlSerializer.text("This is an influence factor with 4 subfactors. Those factors describe if the application has extensive logical or mathematical processing.\n" +
                    "\n");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "reusability");
            xmlSerializer.text("NOT DONE YET. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 -\n" +
                    "2 -\n" +
                    "4 -\n" +
                    "6 -\n" +
                    "8 -\n" +
                    "10 -");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "stock_conversion");
            xmlSerializer.text("NOT DONE YET. The following values describe how the chosen value for this influence factor can be understood.\n" +
                    "\n" +
                    "0 -\n" +
                    "2 -\n" +
                    "4 -\n" +
                    "6 -\n" +
                    "8 -\n" +
                    "10 -");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.startTag(null, "text").attribute(null, "name", "adaptability");
            xmlSerializer.text("The application was aspecially planned and developed that changes could be made easily. The following values describe how the chosen value for this influence factor can be understood.\n\n" +
                    "0 - There are no arrangements made for easy adaptability.\n" +
                    "1 - A simple system for requests and reports is implemented.\n" +
                    "2 - A flexible system for requests and reports is implemented, that allows medium queries.\n" +
                    "3 - A flexible system for requests and reports is implemented, that allows complex queries.\n" +
                    "4 - Control data can be managed from the user in an online system. The changes are available on the next day.\n" +
                    "5 - Control data can be managed from the user in an online system. The changes are available immediately .");
            xmlSerializer.endTag(null, "text");

            xmlSerializer.endTag(null, "category");

            xmlSerializer.endTag(null, "textresourcess");

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
            xmlSerializer.text("It just does.");
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
