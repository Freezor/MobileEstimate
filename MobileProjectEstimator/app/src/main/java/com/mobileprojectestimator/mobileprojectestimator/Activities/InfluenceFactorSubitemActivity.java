package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.NewInfluenceFactorListAdapter;

import java.util.ArrayList;

public class InfluenceFactorSubitemActivity extends DatabaseActivity
{

    private ArrayList<String> itemNames;
    private ArrayList<Integer> itemValues;
    private ArrayList<Integer> maxItemValues;
    private ArrayList<Integer> minItemValues;
    private String itemName;
    private ListView lvSubInfluenceFactors;
    private NewInfluenceFactorListAdapter influenceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_influence_factor_subitem);

        initDatabase();

        Bundle bundle = getIntent().getExtras();
        itemNames = new ArrayList<>();
        itemValues = new ArrayList<>();
        minItemValues = new ArrayList<>();
        maxItemValues = new ArrayList<>();

        itemName = bundle.getString("SUBITEMCATEGORYNAME");
        itemNames = bundle.getStringArrayList("SUBITEMNAMES");
        itemValues = bundle.getIntegerArrayList("SUBITEMVALUES");
        minItemValues = bundle.getIntegerArrayList("SUBITEMMIN");
        maxItemValues = bundle.getIntegerArrayList("SUBITEMMAX");

        setTitle(itemName);

        lvSubInfluenceFactors = (ListView) findViewById(R.id.lvSubInfluenceFactors);
        lvSubInfluenceFactors.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                openFactorInfoDialog(itemNames.get(position));
            }
        });
        loadItems();
    }

    private void loadItems()
    {
        ArrayList<InfluenceFactorItem> influencingFactorItems = new ArrayList<>();

        for (int i = 0; i < itemNames.size(); i++)
        {
            InfluenceFactorItem factorItem = new InfluenceFactorItem(itemNames.get(i), minItemValues.get(i), maxItemValues.get(i), false);
            factorItem.setChosenValue(itemValues.get(i));
            influencingFactorItems.add(factorItem);
        }

        influenceListAdapter = new NewInfluenceFactorListAdapter(this, R.layout.function_point_influence_factorset_list_item, influencingFactorItems);
        lvSubInfluenceFactors.setAdapter(influenceListAdapter);
        lvSubInfluenceFactors.setScrollbarFadingEnabled(false);

    }

    private void openFactorInfoDialog(String item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item);
        builder.setMessage(databaseHelper.getXmlHelper().loadDescriptionText(item.replace(" ", "_").toLowerCase()));
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed()
    {
        closeActivity();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case android.R.id.home:
                closeActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void closeActivity()
    {
        ArrayList<InfluenceFactorItem> items = influenceListAdapter.getInfluenceFactorItems();
        itemNames = new ArrayList<>();
        itemValues = new ArrayList<>();

        for (InfluenceFactorItem i : items)
        {
            itemNames.add(i.getName());
            itemValues.add(i.getChosenValue());
        }

        Intent i = new Intent();
        i.putExtra("SUBITEMCATEGORYNAME", itemName);
        i.putExtra("SUBITEMNAMES", itemNames);
        i.putExtra("SUBITEMVALUES", itemValues);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
