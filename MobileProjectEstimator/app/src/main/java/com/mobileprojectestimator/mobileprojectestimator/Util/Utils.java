package com.mobileprojectestimator.mobileprojectestimator.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Oliver Fries on 26.01.2016, 21:33.
 * Project: MobileProjectEstimator
 */
public class Utils
{

    private static String path;

    /**
     * Create a new file with the given name and content, in this application's
     * cache directory.
     *
     * @param context
     *            - Context - context to use.
     * @param fileName
     *            - String - the name of the file to create.
     * @param content
     *            - String - the content to put in the new file.
     * @throws IOException
     */
    public static void createCachedFile(Context context, String fileName,
                                        String content) throws IOException
    {

        path = context.getCacheDir().toString();

        File cacheFile = new File(context.getCacheDir() + File.separator
                + fileName);

        cacheFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(cacheFile);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
        PrintWriter pw = new PrintWriter(osw);

        pw.println(content);

        pw.flush();
        pw.close();
    }

    /**
     * Returns an intent that can be used to launch Gmail and send an email with
     * the specified file from this application's cache attached.
     *
     * @param context
     *            - Context - the context to use.
     * @param email
     *            - String - the 'to' email address.
     * @param subject
     *            - String - the email subject.
     * @param body
     *            - String - the email body.
     * @param fileName
     *            - String - the name of the file in this application's cache to
     *            attach to the email.
     * @return An Intent that can be used to launch the Gmail composer with the
     *         specified file attached.
     */
    public static Intent getSendEmailIntent(Context context, String email,
                                            String subject, String body, String fileName) {

        final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        // Explicitly only use Gmail to send
        emailIntent.setClassName("com.google.android.gm",
                "com.google.android.gm.ComposeActivityGmail");

        emailIntent.setType("plain/text");

        // Add the recipients
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { email });

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);

        // Add the attachment by specifying a reference to our custom
        // ContentProvider
        // and the specific file of interest

        Uri xcd = Uri.parse("content://" + FileProvider.AUTHORITY + "/" + fileName);

        /*emailIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.parse("content://" + FileProvider.AUTHORITY + "/"
                        + fileName));*/
        emailIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.parse("content://" + path + "/"
                        + fileName));

        return emailIntent;
    }
}
