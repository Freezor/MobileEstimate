package com.mobileprojectestimator.mobileprojectestimator.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

/**
 * Created by Oliver Fries on 01.11.2015.
 */
public class ProjectInfoFragment extends GuidedCreationFragment {
    private Project project;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectInfoFragment newInstance(Project proj) {
        ProjectInfoFragment fragment = new ProjectInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.project_info_fragment, container, false);
        if (project == null) {
            project = new Project(getActivity());
        }
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);

        final EditText projectNameET = (EditText) rootView.findViewById(R.id.projectNameET);
        projectNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                project.setTitle(projectNameET.getText().toString());
            }
        });

        final EditText projectDescription = (EditText) rootView.findViewById(R.id.projectDescriptionET);
        projectDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                project.setProjectDescription(projectDescription.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Bitmap projectIcon = BitmapFactory.decodeResource(getResources(), R.drawable.project);
        project.setImage(projectIcon);
        project.setIconName("Standard Project Icon");

        TextView iconName = (TextView) rootView.findViewById(R.id.tvProjectImageName);
        iconName.setText(project.getIconName());

        Button changeProjectIconBtn = (Button) rootView.findViewById(R.id.changeProjectIcon);
        changeProjectIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(project.getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onReloadViews(String text) {

    }
}
