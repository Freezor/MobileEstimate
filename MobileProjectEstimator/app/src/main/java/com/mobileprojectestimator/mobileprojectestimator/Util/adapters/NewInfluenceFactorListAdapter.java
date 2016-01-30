package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.InfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 16.12.2015, 11:58.
 * Project: MobileProjectEstimator
 */
public class NewInfluenceFactorListAdapter extends ArrayAdapter<InfluenceFactorItem>
{

    private ArrayList<InfluenceFactorItem> influenceFactorItems;

    public NewInfluenceFactorListAdapter(Context context, int textViewResourceId,
                                         ArrayList<InfluenceFactorItem> items)
    {
        super(context, textViewResourceId, items);
        this.influenceFactorItems = new ArrayList<>();
        this.influenceFactorItems.addAll(items);
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
        EditText etInfluenceValue;
        ImageView infoImageView;
        TextView tvShowSubitems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

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
                        Toast.makeText(getContext(), "Show Sub Items Clicked "+item.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                holder.infoImageView.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Toast.makeText(getContext(), "Info Clicked: " + finalHolder.tvInfluenceName.getText(), Toast.LENGTH_SHORT).show();
                    }
                });


                convertView.setTag(holder);
            } else
            {
                convertView = vi.inflate(R.layout.function_point_influence_factorset_list_item, null);

                holder = new ViewHolder();
                holder.hasSubitems = false;
                holder.tvInfluenceName = (TextView) convertView.findViewById(R.id.tvInfluenceName);
                holder.etInfluenceValue = (EditText) convertView.findViewById(R.id.etInfluenceValue);
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
                        Toast.makeText(getContext(), "Info Clicked: " + finalHolder.tvInfluenceName.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                holder.etInfluenceValue.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {

                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        if (s.toString().equals(""))
                        {
                            influenceFactorItems.get(position).setChosenValue(0);
                        } else
                        {
                            influenceFactorItems.get(position).setChosenValue(Integer.parseInt(s.toString()));
                        }
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
                holder.etInfluenceValue.setText(String.valueOf(item.getChosenValue()));
            }
        }

        return convertView;

    }

}
