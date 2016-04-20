package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Server.ServerConnector;

public class LoginActivity extends AppCompatActivity
{

    private ServerConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connector = new ServerConnector(this);
    }

}
