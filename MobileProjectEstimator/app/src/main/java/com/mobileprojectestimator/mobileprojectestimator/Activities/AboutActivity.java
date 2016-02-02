package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.R;

public class AboutActivity extends AppCompatActivity
{

    private TextView tvVersion;
    private Button bLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try
        {
            info = manager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        String version = info.versionName;
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvVersion.setText(getString(R.string.app_version)+ " " +version+" - "+getString(R.string.app_version_tag_name));

        bLicence = (Button) findViewById(R.id.bLicence);
        bLicence.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(AboutActivity.this,LicenceActivity.class);
                startActivity(i);
            }
        });
    }
}
