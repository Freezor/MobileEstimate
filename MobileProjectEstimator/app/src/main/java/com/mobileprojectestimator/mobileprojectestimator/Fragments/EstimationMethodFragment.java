package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public class EstimationMethodFragment extends GuidedCreationFragment {
    private Project project;
    private TextView chosenMethodTv;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public  EstimationMethodFragment newInstance(Project proj) {
        EstimationMethodFragment fragment = new EstimationMethodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        this.project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.estimation_method_fragment, container, false);
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
        dot2.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
        dot3.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot4 = (ImageView) rootView.findViewById(R.id.dot4);
        dot4.setBackgroundResource(R.drawable.circle_blue);

        final RadioGroup estimationMethodRadioGroup = (RadioGroup) rootView.findViewById(R.id.estimationMethodRadioGroup);
        estimationMethodRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = estimationMethodRadioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton estimatiionRB = (RadioButton) rootView.findViewById(selectedId);
                project.setEstimationMethod(estimatiionRB.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onReloadViews(String text) {

    }
}
