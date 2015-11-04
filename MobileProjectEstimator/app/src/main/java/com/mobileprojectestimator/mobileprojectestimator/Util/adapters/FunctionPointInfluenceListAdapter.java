package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.FunctionPointFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointEstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointInfluenceFactorFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 03.11.2015.
 */
public class FunctionPointInfluenceListAdapter extends BaseAdapter {

    private FunctionPointInfluenceFactorFragment fragment;
    private ArrayList<FunctionPointFactorItem> fpInfluenceItems;
    private LayoutInflater inflater;
    Project project;

    public FunctionPointInfluenceListAdapter(FunctionPointInfluenceFactorFragment projectInfluenceFactorFragment, ArrayList<FunctionPointFactorItem> fpInfluenceItems, Project project) {
        this.fragment = projectInfluenceFactorFragment;
        this.fpInfluenceItems = fpInfluenceItems;
        this.project = project;
    }

    @Override
    public int getCount() {
        return fpInfluenceItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getLayoutInflater();
        if (convertView == null)
            convertView = inflater.inflate(R.layout.function_point_influence_factor_list_item, null);

        TextView itemNameTv = (TextView) convertView.findViewById(R.id.tvInfluenceName);
        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvInfluenceValue);

        // title
        //itemValueTv.setText(""+fpInfluenceItems.get(position).getChosenValue());
        if(fpInfluenceItems.get(position).getChosenValue() >=0)
        {
            itemValueTv.setText(""+fpInfluenceItems.get(position).getChosenValue());
        } else
        {
            itemValueTv.setText("0");
        }
        itemNameTv.setText(fpInfluenceItems.get(position).getName());


        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.standardRowEven);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }
}

