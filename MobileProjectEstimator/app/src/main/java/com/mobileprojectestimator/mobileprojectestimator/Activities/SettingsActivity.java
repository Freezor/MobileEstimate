package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.mobileprojectestimator.mobileprojectestimator.R;

/**
 * Created by Oliver Fries on 09.12.2015, 10:38.
 * Project: MobileProjectEstimator
 */
public class SettingsActivity extends PreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.statisticToolbar);

        addPreferencesFromResource(R.xml.preferences);
        //getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        toolbar.setClickable(true);
        toolbar.setNavigationIcon(getResIdFromAttribute(this, R.attr.homeAsUpIndicator));
        toolbar.setTitle(R.string.settings_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0) {
            return 0;
        }
        final TypedValue typedvalueattr = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}