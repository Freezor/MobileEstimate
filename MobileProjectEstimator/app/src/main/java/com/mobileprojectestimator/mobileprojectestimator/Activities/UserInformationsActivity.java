package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.R;

public class UserInformationsActivity extends AppCompatActivity
{

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_informations);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String userName = sharedPref.getString(getString(R.string.pref_key_user_name), "");
        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserName.setText(userName);
    }
}
