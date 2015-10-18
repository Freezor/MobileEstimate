package com.example.oliverfries.cet_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectPropertiesActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, String> listLayoutChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_properties);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, listLayoutChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listLayoutChild = new HashMap<String, String>();

        // Adding child data
        listDataHeader.add("Process Model ");
        listDataHeader.add("Platform");
        listDataHeader.add("Project Type");

        // Adding child data
        List<String> processModelsList = new ArrayList<String>();
        processModelsList.add("Scrum");
        processModelsList.add("Waterfall Model");
        processModelsList.add("V-Model");

        List<String> platformsList = new ArrayList<String>();
        platformsList.add("Windows");
        platformsList.add("Mac OS");
        platformsList.add("iOS");
        platformsList.add("Android");
        platformsList.add("Windows Server");
        platformsList.add("Ubuntu");

        List<String> projectTypesList = new ArrayList<String>();
        projectTypesList.add("New Development");
        projectTypesList.add("Extension Project");

        listDataChild.put(listDataHeader.get(0), processModelsList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), platformsList);
        listDataChild.put(listDataHeader.get(2), projectTypesList);

        listLayoutChild.put(listDataHeader.get(0), String.valueOf(R.string.layout_project_properties_radio_button)); // Header, Child layout data
        listLayoutChild.put(listDataHeader.get(1), String.valueOf(R.string.layout_project_properties_checkbox_button));
        listLayoutChild.put(listDataHeader.get(2), String.valueOf(R.string.layout_project_properties_radio_button));
    }
}
