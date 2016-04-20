package com.mobileprojectestimator.mobileprojectestimator.Util;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Oliver Fries on 02.02.2016, 09:11.
 * Project: MobileProjectEstimator
 */
public class InputFilterMinMax implements InputFilter
{
    private double mMinValue;
    private double mMaxValue;
    private Context context;

    private static final double MIN_VALUE_DEFAULT = Double.MIN_VALUE;
    private static final double MAX_VALUE_DEFAULT = Double.MAX_VALUE;

    public InputFilterMinMax(Context baseContext, Double min, Double max)
    {
        this.context = baseContext;
        this.mMinValue = (min != null ? min : MIN_VALUE_DEFAULT);
        this.mMaxValue = (max != null ? max : MAX_VALUE_DEFAULT);
    }

    public InputFilterMinMax(Context context, Integer min, Integer max)
    {
        this.context = context;
        this.mMinValue = (min != null ? min : MIN_VALUE_DEFAULT);
        this.mMaxValue = (max != null ? max : MAX_VALUE_DEFAULT);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {
        try
        {
            String replacement = source.subSequence(start, end).toString();
            String newVal = dest.subSequence(0, dstart).toString() + replacement
                    + dest.subSequence(dend, dest.length()).toString();

            // check if there are leading zeros
            if (newVal.matches("0\\d+.*"))
                if (TextUtils.isEmpty(source))
                    return dest.subSequence(dstart, dend);
                else
                {
                    Toast.makeText(context, "Value must be in range "+mMinValue+" - "+mMaxValue, Toast.LENGTH_SHORT).show();
                    return "";
                }

            // check range
            double input = Double.parseDouble(newVal);
            if (!isInRange(mMinValue, mMaxValue, input))
                if (TextUtils.isEmpty(source))
                    return dest.subSequence(dstart, dend);
                else
                {
                    Toast.makeText(context, "Value must be in range "+mMinValue+" - "+mMaxValue, Toast.LENGTH_SHORT).show();
                    return "";
                }

            return null;
        } catch (NumberFormatException nfe)
        {
            Toast.makeText(context, "Not a Number: "+nfe.getCause(), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private boolean isInRange(double a, double b, double c)
    {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}