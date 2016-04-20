package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LicenceActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        TextView tvLicence = (TextView) findViewById(R.id.tvLicence);

        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("licence.txt")));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        StringBuilder text = new StringBuilder();
        // do reading, usually loop until end of file reading
        String mLine;
        try
        {
            assert reader != null;
            while ((mLine = reader.readLine()) != null)
            {

                text.append(mLine);
                text.append('\n');
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        tvLicence.setText(text);
        tvLicence.setMovementMethod(new ScrollingMovementMethod());

    }
}
