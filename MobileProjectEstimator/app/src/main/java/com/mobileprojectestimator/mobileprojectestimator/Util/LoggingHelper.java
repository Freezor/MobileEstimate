package com.mobileprojectestimator.mobileprojectestimator.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Oliver Fries on 26.01.2016, 17:45.
 * Project: MobileProjectEstimator
 */
public class LoggingHelper
{
    public static int LOGLEVEL_INFO = 0;
    public static int LOGLEVEL_WARNING = 1;
    public static int LOGLEVEL_ERROR = 2;

    private File logFile;
    private Context context;

    public LoggingHelper(Context c)
    {
        this.context = c;
        openOrCreateLog();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void openOrCreateLog()
    {
        logFile = new File(context.getApplicationContext().getFilesDir(), "log.txt");
        if(!logFile.exists()) {
            try
            {
                logFile.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void writeToLog(String text,int logLevel)
    {
        openOrCreateLog();
        try {

            FileWriter fw = new FileWriter(logFile,true);
            switch(logLevel){
                case 0:
                    try
                    {
                        fw.append("INFO: " + text +"\r\n");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try
                    {
                        fw.append("WARNING: " + text +"\r\n");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try
                    {
                        fw.append("ERROR: " + text +"\r\n");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
