package com.example.oliverfries.cet_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class FactorPickerActivity extends AppCompatActivity {

    private int maxValue = 0;

    private int chosenValue= 0;

    private NumberPicker np;

    private String influenceName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor_picker);

        //  get passed intent
        Intent intent = getIntent();

        // get message value from intent
        maxValue = intent.getIntExtra("EXTRA_MAX_VALUE", 0);
        influenceName = intent.getStringExtra("EXTRA_INFLUENCE_NAME");

        // Create the array of numbers that will populate the numberpicker
        final String[] nums = new String[maxValue+1];
        for(int i=0; i<nums.length; i++) {
            nums[i] = Integer.toString(i);
        }

        // Set the max and min values of the numberpicker, and give it the
        // array of numbers created above to be the displayed numbers
        np = (NumberPicker) findViewById(R.id.np);
        np.setMaxValue(maxValue);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(nums);
        np.setOnValueChangedListener( new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setChosenValue(newVal);
            }
        });
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getChosenValue() {
        return chosenValue;
    }

    public void setChosenValue(int chosenValue) {
        this.chosenValue = chosenValue;
    }

    @Override
    protected void onPause() {
        Intent intent = new Intent();
        intent.putExtra("EXTRA_VALUE", getChosenValue());
        intent.putExtra("EXTRA_INFLUENCE_NAME", influenceName);
        setResult(1, intent);
        super.onPause();
    }


}
