package com.example.oliverfries.cet_test;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewProject extends AppCompatActivity {

    private EstimationProject _newProject;

    private TextView _estimationMethodTextView;

    private EditText _projectNameEditText;

    private EditText _projectNotesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_project);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        _newProject = new EstimationProject();
        _estimationMethodTextView = (TextView) findViewById(R.id.estimationMethodTextView);
        _projectNameEditText = (EditText) findViewById(R.id.project_name);

        _projectNotesEditText = (EditText) findViewById(R.id.project_note);

        _projectNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _newProject.set_projectName(_projectNameEditText.getText().toString());
            }
        });

        _projectNotesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _newProject.set_notes(_projectNotesEditText.getText().toString());
            }
        });
    }

    public void selectEstimationMethod(View v) {
        EstimationMethodSelectionDialogView();
    }

    private void EstimationMethodSelectionDialogView() {
        final CharSequence[] items = { "Function Point", "Cocomo", "Cocomo 2", "Prince" };

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewProject.this);//ERROR ShowDialog cannot be resolved to a type
        builder.setTitle("Select the Estimation Method you want to use");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        _newProject.set_estimationMethod(items[item].toString());
                    }
                });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(CreateNewProject.this, _newProject.get_estimationMethod() + " chosen", Toast.LENGTH_SHORT)
                        .show();
                _estimationMethodTextView.setText(_newProject.get_estimationMethod());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(CreateNewProject.this, "Canceled", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void selectInfluencingFactors(View v) {
        Context context = getApplicationContext();
        CharSequence text = _newProject.get_projectName();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void selectProjectProperties(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Project Properties";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
