package com.mobileprojectestimator.mobileprojectestimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import java.util.HashMap;

public class FunctionPointEstimationValueActivity extends AppCompatActivity
{

    private int simpleValue = 0;
    private TextView valueSimple;
    private TextView valueMedium;
    private int mediumValue = 0;
    private int complexValue = 0;
    private TextView valueComplex;
    protected String title;
    private Project project;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
        closeActivity();
        super.onBackPressed();
    }

    /**
     * Put the Results of this activity in the extras and finishes this activity
     */
    private void closeActivity()
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.NewProjectIntentValueParam), project.toHashMap());
        setResult(1, returnIntent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == android.R.id.home)
        {
            closeActivity();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_point_estimation_value);

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("NEWPROJECT");
        project = new Project(this);
        if (!hashMap.isEmpty())
        {
            project.toObjectFromHashMap(hashMap);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFunctionPointEstimation);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView description = (TextView) findViewById(R.id.tvFpEstimationDescription);
        description.setMovementMethod(new ScrollingMovementMethod());

        initSimpleCat();

        initMediumCat();

        initComplexCat();
    }

    /**
     * initialise the views for the complex category
     */
    private void initComplexCat()
    {
        RelativeLayout layoutComplexEstimation = (RelativeLayout) findViewById(R.id.i_complex_value);
        valueComplex = (TextView) layoutComplexEstimation.findViewById(R.id.tvValue);
        valueComplex.setText(String.format("%d", complexValue));
        ImageView decreaseButtonComplexCat = (ImageView) layoutComplexEstimation.findViewById(R.id.ivLeft);
        decreaseButtonComplexCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (complexValue > 0)
                {
                    complexValue--;
                    valueComplex.setText(String.format("%d", complexValue));
                }
            }
        });
        ImageView increaseButtonComplexCat = (ImageView) layoutComplexEstimation.findViewById(R.id.ivRight);
        increaseButtonComplexCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                complexValue++;
                valueComplex.setText(String.format("%d", complexValue));
            }
        });
    }

    /**
     * initialise the views for the medium category
     */
    private void initMediumCat()
    {
        RelativeLayout layoutMediumEstimation = (RelativeLayout) findViewById(R.id.i_medium_value);
        valueMedium = (TextView) layoutMediumEstimation.findViewById(R.id.tvValue);
        valueMedium.setText(String.format("%d", mediumValue));
        ImageView decreaseButtonMediumCat = (ImageView) layoutMediumEstimation.findViewById(R.id.ivLeft);
        decreaseButtonMediumCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mediumValue > 0)
                {
                    mediumValue--;
                    valueMedium.setText(String.format("%d", mediumValue));
                }
            }
        });
        ImageView increaseButtonMediumCat = (ImageView) layoutMediumEstimation.findViewById(R.id.ivRight);
        increaseButtonMediumCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mediumValue++;
                valueMedium.setText(String.format("%d", mediumValue));
            }
        });
    }

    /**
     * initialise the views for the simple category
     */
    private void initSimpleCat()
    {
        RelativeLayout layoutSimpleEstimation = (RelativeLayout) findViewById(R.id.i_simple_value);
        valueSimple = (TextView) layoutSimpleEstimation.findViewById(R.id.tvValue);
        valueSimple.setText(String.format("%d", simpleValue));
        ImageView decreaseButtonSimpleCat = (ImageView) layoutSimpleEstimation.findViewById(R.id.ivLeft);
        decreaseButtonSimpleCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (simpleValue > 0)
                {
                    simpleValue--;
                    valueSimple.setText(String.format("%d", simpleValue));
                }
            }
        });
        ImageView increaseButtonSimpleCat = (ImageView) layoutSimpleEstimation.findViewById(R.id.ivRight);
        increaseButtonSimpleCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                simpleValue++;
                valueSimple.setText(String.format("%d", simpleValue));
            }
        });
    }
}
