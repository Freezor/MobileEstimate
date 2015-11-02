package com.mobileprojectestimator.mobileprojectestimator.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.ProjectCreationItem;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointEstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectCreationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public class FunctionPointEstimationListAdapter extends BaseAdapter {

    private FunctionPointMethodFragment fragment;
    private ArrayList<FunctionPointEstimationItem> fpEstimationItems;
    private LayoutInflater inflater;

    public FunctionPointEstimationListAdapter(FunctionPointMethodFragment projectCreationOverviewFragment, ArrayList<FunctionPointEstimationItem> fpEstimationItems) {
        this.fragment = projectCreationOverviewFragment;
        this.fpEstimationItems = fpEstimationItems;
    }

    @Override
    public int getCount() {
        return fpEstimationItems.size();
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
            convertView = inflater.inflate(R.layout.function_point_estimation_list_item, null);

        TextView itemValueTv = (TextView) convertView.findViewById(R.id.tvValue);
        TextView itemNameTv = (TextView) convertView.findViewById(R.id.tvEstimationCategory);

        // title
        itemValueTv.setText(""+fpEstimationItems.get(position).getValue());
        itemNameTv.setText(fpEstimationItems.get(position).getName());


        if (position % 2 == 1) {
            //convertView.setBackgroundResource(R.color.standardRowOdd);// this set background color
        } else {
            convertView.setBackgroundResource(R.color.standardRowEven);// this set background color
        }
        return convertView;
    }
}
