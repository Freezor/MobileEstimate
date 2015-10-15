package com.example.oliverfries.cet_test;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

public class CreateNewProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_project);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void selectEstimationMethod(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Select Estimation Method";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void selectInfluencingFactors(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Influencing Factors";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void selectProjectProperties(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Project Properties";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
