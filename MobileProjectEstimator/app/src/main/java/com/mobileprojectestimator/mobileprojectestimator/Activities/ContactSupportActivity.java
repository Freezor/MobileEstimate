package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.R;

public class ContactSupportActivity extends AppCompatActivity
{

    private EditText etProblemDescription;
    private CheckBox cbSendLogFiles;

    private static int MINWORDCOUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);

        etProblemDescription = (EditText) findViewById(R.id.etProblemDescription);
        cbSendLogFiles = (CheckBox) findViewById(R.id.cbSendLogFiles);
        cbSendLogFiles.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_support, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_contact_support_forward:
                String description = etProblemDescription.getText().toString();
                int words = countWords(description);
                Log.d("INFO","Word Count = "+words);
                if(words <MINWORDCOUNT ){
                    Toast.makeText(getApplicationContext(),getString(R.string.support_message_too_short),Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String userName = sharedPref.getString(getString(R.string.pref_key_user_name), "");
                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Question From User: " + userName);
                    intent.putExtra(Intent.EXTRA_TEXT, description);

                    //Uri log = Uri.parse(getFilesDir() + "/log.txt");
                    //intent.putExtra(Intent.EXTRA_STREAM, log);
                    intent.setData(Uri.parse("mailto:frieso@fh-trier.de")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static int countWords(String s){

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }
}
