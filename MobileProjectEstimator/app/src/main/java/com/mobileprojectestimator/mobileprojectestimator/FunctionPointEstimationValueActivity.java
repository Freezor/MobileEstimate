package com.mobileprojectestimator.mobileprojectestimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Util.InputFilterMinMax;

import java.util.HashMap;

public class FunctionPointEstimationValueActivity extends AppCompatActivity
{

    protected String title;
    private int simpleValue = 0;
    private EditText valueSimple;
    private EditText valueMedium;
    private int mediumValue = 0;
    private int complexValue = 0;
    private EditText valueComplex;
    private Project project;
    private FunctionPointItem item;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("INFO", "FunctionPointEstimationValueActivity: onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
        Log.d("INFO", "FunctionPointEstimationValueActivity: onBackPressed");
        closeActivity();
        super.onBackPressed();
    }

    /**
     * Put the Results of this activity in the extras and finishes this activity
     */
    private void closeActivity()
    {

        Log.d("INFO", "FunctionPointEstimationValueActivity: closeActivtiy");
        if (this.project.updateEstimationItem(title, item))
        {
            this.project.updateFunctionPointItem(item);
            Intent returnIntent = new Intent();
            //TODO: bereits gesetzte Items sind hier wieder 0
            returnIntent.putExtra(getString(R.string.NewProjectIntentValueParam), project.toHashMap());
            setResult(1, returnIntent);
            finish();
        } else
        {
            Toast.makeText(this, "ERROR: Estimation Item does not exist", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        Log.d("INFO", "FunctionPointEstimationValueActivity: onOptionsItemSelected");
        if (menuItem.getItemId() == android.R.id.home)
        {
            closeActivity();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("INFO", "FunctionPointEstimationValueActivity: onCreate");
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
        item = (FunctionPointItem) project.getEstimationItemByName(this.title);

        simpleValue = Integer.valueOf(hashMap.get(title+"_SIMPLE"));
        mediumValue = Integer.valueOf(hashMap.get(title+"_MEDIUM"));
        complexValue = Integer.valueOf(hashMap.get(title + "_COMPLEX"));

        item.updateItems(simpleValue,mediumValue,complexValue);

        try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFunctionPointEstimation);
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

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
        valueComplex = (EditText) layoutComplexEstimation.findViewById(R.id.tvValue);
        valueComplex.setText(String.format("%d", complexValue));
        valueComplex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = valueComplex.getText().toString();
                if (value.contains(".")) {
                    value.replace(".", "");
                }
                if (value.startsWith("0")) {
                    value = value.substring(1);
                }
                complexValue = Integer.parseInt(value);
                //TODO: Error = Fehler wenn im edit text gelöscht wird. fehler wenn erstes Element "."
            }
        });
        ImageView decreaseButtonComplexCat = (ImageView) layoutComplexEstimation.findViewById(R.id.ivLeft);
        decreaseButtonComplexCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (complexValue > 0)
                {
                    item.raiseItem(2, false);
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
                item.raiseItem(2, true);
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
        valueMedium = (EditText) layoutMediumEstimation.findViewById(R.id.tvValue);
        valueMedium.setText(String.format("%d", mediumValue));
        ImageView decreaseButtonMediumCat = (ImageView) layoutMediumEstimation.findViewById(R.id.ivLeft);
        decreaseButtonMediumCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mediumValue > 0)
                {
                    item.raiseItem(1, false);
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
                item.raiseItem(1, true);
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
        valueSimple = (EditText) layoutSimpleEstimation.findViewById(R.id.tvValue);
        valueSimple.setText(String.format("%d", simpleValue));
        ImageView decreaseButtonSimpleCat = (ImageView) layoutSimpleEstimation.findViewById(R.id.ivLeft);
        decreaseButtonSimpleCat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (simpleValue > 0)
                {
                    item.raiseItem(0, false);
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
                item.raiseItem(0, true);
                simpleValue++;
                valueSimple.setText(String.format("%d", simpleValue));
            }
        });
    }

}
