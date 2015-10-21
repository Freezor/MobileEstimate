package com.example.oliverfries.cet_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FunctionPointAddFunctionActivity extends AppCompatActivity {

    private TextView simpleTV;
    private TextView meridianTV;
    private TextView complexTV;

    private int simple,meridian,complex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_point_add_function);
        simpleTV = (TextView) findViewById(R.id.simpleCountTV);
        meridianTV = (TextView) findViewById(R.id.meridianCountTV);
        complexTV = (TextView) findViewById(R.id.complexCountTV);
        simple = 0;
        meridian = 0;
        complex=0;
    }

    public void simpleIncrease(View view) {
        simple++;
        simpleTV.setText(String.valueOf(simple));
    }

    public void simpleDecrease(View view) {
        if(simple>0)
        {
            simple--;
            simpleTV.setText(String.valueOf(simple));
        }
    }

    public void meridianIncrease(View view) {
        meridian++;
        meridianTV.setText(String.valueOf(meridian));
    }

    public void meridianDecrease(View view) {
        if(meridian>0)
        {
            meridian--;
            meridianTV.setText(String.valueOf(meridian));
        }
    }

    public void complexDecrease(View view) {
        if(complex>0)
        {
            complex--;
            complexTV.setText(String.valueOf(complex));
        }
    }

    public void complexIncrease(View view) {
        complex++;
        complexTV.setText(String.valueOf(complex));
    }
}
