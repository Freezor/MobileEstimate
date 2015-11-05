package com.mobileprojectestimator.mobileprojectestimator.Util.adapters;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointEstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.FunctionPointMethodFragment;
import com.mobileprojectestimator.mobileprojectestimator.FunctionPointEstimationValueActivity;
import com.mobileprojectestimator.mobileprojectestimator.FunctionPointProjectActivtiy;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 02.11.2015.
 */
public class FunctionPointEstimationListAdapter extends BaseAdapter {

    private FunctionPointMethodFragment fragment;
    private ArrayList<FunctionPointEstimationItem> fpEstimationItems;
    private LayoutInflater inflater;
    private Project project;
    private String itemName;

    FragmentManager fm;
    private TextView itemNameTv;

    public FunctionPointEstimationListAdapter(FunctionPointMethodFragment projectCreationOverviewFragment, ArrayList<FunctionPointEstimationItem> fpEstimationItems, FragmentManager fm, Project project) {
        this.fragment = projectCreationOverviewFragment;
        this.fpEstimationItems = fpEstimationItems;
        this.fm = fm;
        this.project = project;
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
        itemNameTv = (TextView) convertView.findViewById(R.id.tvEstimationCategory);

        // title
        itemName = fpEstimationItems.get(position).getName();
        itemValueTv.setText(""+fpEstimationItems.get(position).getValue());
        itemNameTv.setText(itemName);

        //TODO: Button wird nicht für jedes Element einzeln gesetzt.
        ImageView editButton = (ImageView) convertView.findViewById(R.id.ivEditButton);//TODO: Zugriff immer auf alle EditButtons. Es muss auf jedes Listelement zugegriffen werden.
        //ImageView editButton = (ImageView) convertView.findViewById(R.id.ivEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FunctionPointEstimationValueActivity.class);
                intent.putExtra("TITLE", itemName);

                intent.putExtra("NEWPROJECT", project.toHashMap());
                fragment.startActivityForResult(intent, 1);
            }
        });

        convertView.setTag(123);

        if (position % 2 == 1) {
            //convertView.setBackgroundResource(R.color.standardRowOdd);// this set background color
        } else {
            convertView.setBackgroundResource(R.color.standardRowEven);// this set background color
        }
        return convertView;
    }
}
