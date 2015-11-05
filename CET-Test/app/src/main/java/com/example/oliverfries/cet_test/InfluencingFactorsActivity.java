package com.example.oliverfries.cet_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InfluencingFactorsActivity extends AppCompatActivity {

    private FunctionPointInfluenceFactors influenceFactors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_influencing_factors);

        influenceFactors = new FunctionPointInfluenceFactors();
    }

    private void startFactorPickerActivity(String factorName, int maxValue)
    {
        Intent intent = new Intent(InfluencingFactorsActivity.this,FactorPickerActivity.class);
        intent.putExtra("EXTRA_INFLUENCE_NAME", factorName);
        intent.putExtra("EXTRA_MAX_VALUE", maxValue);
        startActivity(intent);
    }

    public void onClickIntegrationFactor(View v)
    {
        startFactorPickerActivity("InfluencingFactor", 5);
    }

    public void onClickLocalDataFactor(View v)
    {
        startFactorPickerActivity("TransactionRateFactor", 5);
    }

    public void onClickTransactionRateFactor(View v)
    {
        startFactorPickerActivity("TransactionRateFactor", 5);
    }

    public void onClickArithmeticOperationFactor(View v)
    {
        startFactorPickerActivity("ArithmeticOperationFactor", 10);
    }

    public void onClickControlProcedureFactor(View v)
    {
        startFactorPickerActivity("ControlProcedureFactor", 5);
    }

    public void onClickExceptionRegulationFactor(View v)
    {
        startFactorPickerActivity("ExceptionRegulationFactor", 10);
    }

    public void onClickLogicFactor(View v)
    {
        startFactorPickerActivity("LogicFactor", 5);
    }

    public void onClickReusabilityFactor(View v)
    {
        startFactorPickerActivity("ReusabilityFactor",5);
    }

    public void onClickAdaptabilityFactor(View v)
    {
        startFactorPickerActivity("AdaptabilityFactor",5);
    }

    public void onClickStockConversionsFactor(View v)
    {
        startFactorPickerActivity("StockConversionFactor",5);
    }

    public void onClickIntegrationFactorHelp(View v){
        Context context = getApplicationContext();
        CharSequence text = "To be implemented";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String test = intent.getStringExtra("EXTRA_INFLUENCE_NAME");
        int val = intent.getIntExtra("EXTRA_VALUE", 0);
        val = 0;
    }
}
