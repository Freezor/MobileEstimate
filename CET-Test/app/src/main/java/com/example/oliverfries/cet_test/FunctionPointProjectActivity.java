package com.example.oliverfries.cet_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FunctionPointProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_point_project);
    }

    public void addFunction(View v)
    {
        Intent i = new Intent(FunctionPointProjectActivity.this,FunctionPointAddFunctionActivity.class);
        startActivity(i);
    }
}
