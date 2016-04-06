package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Server.ServerConnector;

public class LoginActivity extends AppCompatActivity
{

    @SuppressWarnings("FieldCanBeLocal")
    private ServerConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connector = new ServerConnector(this);
    }

}
