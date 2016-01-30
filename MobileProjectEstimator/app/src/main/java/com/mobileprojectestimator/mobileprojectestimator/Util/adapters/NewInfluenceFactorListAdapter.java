package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.Activities.InfluenceFactorSubitemActivity;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Oliver Fries on 16.12.2015, 11:58.
 * Project: MobileProjectEstimator
 */
public class NewInfluenceFactorListAdapter extends ArrayAdapter<InfluenceFactorItem>
{

    private ArrayList<InfluenceFactorItem> influenceFactorItems;

    private Context c;

    public NewInfluenceFactorListAdapter(Context context, int textViewResourceId,
                                         ArrayList<InfluenceFactorItem> items)
    {
        super(context, textViewResourceId, items);
        this.c = context;
        initDatabase();
        this.influenceFactorItems = new ArrayList<>();
        this.influenceFactorItems.addAll(items);
    }

    /**
     * The Database Helper Object for sending queries to the database
     */
    protected DataBaseHelper databaseHelper;

    /**
     * Initialise the Database Helper class and loads the database
     */
    protected void initDatabase()
    {
        Log.d("Info", "Database Initialisation");
        this.databaseHelper = new DataBaseHelper(getContext());

        try
        {

            databaseHelper.createDataBase();

        } catch (IOException ioe)
        {

            throw new Error("Unable to create database");

        }

        try
        {

            databaseHelper.openDataBase();

        } catch (SQLException sqle)
        {
            Log.d("ERROR", sqle.toString());
        }

        databaseHelper.logDatabaseInformation();
    }

    public int getSumOfInfluences()
    {
        int sum = 0;
        for (InfluenceFactorItem item : influenceFactorItems)
        {
            if (item.hasSubItems())
            {
                for (InfluenceFactorItem subitem : item.getSubInfluenceFactorItemsList())
                {
                    sum += subitem.getChosenValue();
                }
            } else
            {
                sum += item.getChosenValue();
            }
        }
        return sum;
    }

    public ArrayList<InfluenceFactorItem> getInfluenceFactorItems()
    {
        return influenceFactorItems;
    }

    private class ViewHolder
    {
        boolean hasSubitems;
        TextView tvInfluenceName;
        TextView tvInfluenceValue;
        ImageView infoImageView;
        TextView tvShowSubitems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder = null;

        InfluenceFactorItem item = influenceFactorItems.get(position);

        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            if (item.hasSubItems())
            {
                convertView = vi.inflate(R.layout.function_point_influence_factorset_with_subitems_list_item, null);

                holder = new ViewHolder();
                holder.hasSubitems = true;
                holder.tvShowSubitems = (TextView) convertView.findViewById(R.id.tvShowSubitems);
                holder.tvInfluenceName = (TextView) convertView.findViewById(R.id.tvInfluenceName);
                holder.infoImageView = (ImageView) convertView.findViewById(R.id.infoImageView);

                if ((position % 2) != 0)
                {
                    convertView.setBackgroundResource(R.color.light_blue);
                }

                final ViewHolder finalHolder = holder;
                holder.tvShowSubitems.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        InfluenceFactorItem item = influenceFactorItems.get(position);

                        ArrayList<String> itemNames = new ArrayList<>();
                        ArrayList<Integer> itemValues = new ArrayList<>();
                        ArrayList<Integer> minItemValues = new ArrayList<>();
                        ArrayList<Integer> maxItemValues = new ArrayList<>();

                        for (InfluenceFactorItem sub : item.getSubInfluenceFactorItemsList())
                        {
                            itemNames.add(sub.getName());
                            itemValues.add(sub.getChosenValue());
                            minItemValues.add(sub.getMinValue());
                            maxItemValues.add(sub.getMaxValue());
                        }

                        Intent i = new Intent(getContext(), InfluenceFactorSubitemActivity.class);
                        i.putExtra("SUBITEMCATEGORYNAME", item.getName());
                        i.putExtra("SUBITEMNAMES", itemNames);
                        i.putExtra("SUBITEMVALUES", itemValues);
                        i.putExtra("SUBITEMMIN", minItemValues);
                        i.putExtra("SUBITEMMAX", maxItemValues);
                        ((Activity) c).startActivityForResult(i, Integer.parseInt(getContext().getString(R.string.influence_factor_subitem_request_code)));
                    }
                });
                holder.infoImageView.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        openFactorInfoDialog(String.valueOf(finalHolder.tvInfluenceName.getText()));
                    }
                });


                convertView.setTag(holder);
            } else
            {
                convertView = vi.inflate(R.layout.function_point_influence_factorset_list_item, null);

                holder = new ViewHolder();
                holder.hasSubitems = false;
                holder.tvInfluenceName = (TextView) convertView.findViewById(R.id.tvInfluenceName);
                holder.tvInfluenceValue = (TextView) convertView.findViewById(R.id.etInfluenceValue);
                holder.infoImageView = (ImageView) convertView.findViewById(R.id.infoImageView);

                if ((position % 2) != 0)
                {
                    convertView.setBackgroundResource(R.color.light_blue);
                }


                final ViewHolder finalHolder = holder;
                holder.infoImageView.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        openFactorInfoDialog(String.valueOf(finalHolder.tvInfluenceName.getText()));
                    }
                });
                holder.tvInfluenceValue.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showValueDialog(position);
                    }
                });

                convertView.setTag(holder);
            }
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvInfluenceName.setText(item.getName());
        if (holder.hasSubitems)
        {

        } else
        {
            if (item.getChosenValue() >= 0)
            {
                holder.tvInfluenceValue.setText(String.valueOf(item.getChosenValue()));
            }
        }

        return convertView;

    }

    private void showValueDialog(final int position)
    {
        InfluenceFactorItem item = influenceFactorItems.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(item.getName());
        builder.setMessage("Change Influence Factor value.");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER );
        input.setText(String.valueOf(item.getChosenValue()));
        InputFilterMinMax filter = new InputFilterMinMax(item.getMinValue(),item.getMaxValue());
        input.setFilters((new InputFilter[] { filter }));
        builder.setView(input);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                influenceFactorItems.get(position).setChosenValue(Integer.parseInt(input.getText().toString()));
                notifyDataSetChanged();

            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        builder.show();
    }

    private void openFactorInfoDialog(String item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    public class InputFilterMinMax implements InputFilter
    {

        private int min, max;

        public InputFilterMinMax(int min, int max)
        {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max)
        {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            try
            {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe)
            {
            }
            Toast.makeText(getContext(), "Input value out of factor range. Maximum value is " + this.max + ".", Toast.LENGTH_SHORT).show();
            return "";
        }

        private boolean isInRange(int a, int b, int c)
        {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
