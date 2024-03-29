package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.Activities.ChooseProjectIconActivity;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 01.11.2015, 16:23.
 * Project: MobileProjectEstimator
 */
public class ProjectInfoFragment extends GuidedCreationFragment
{
    private Project project;
    private int projectIconId;
    private View rootView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ProjectInfoFragment newInstance(Project proj)
    {
        ProjectInfoFragment fragment = new ProjectInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        project = proj;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        initDatabase();

        rootView = inflater.inflate(R.layout.project_info_fragment, container, false);
        if (project == null)
        {
            project = new Project(getActivity());
        }
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);

        final EditText projectNameET = (EditText) rootView.findViewById(R.id.projectNameET);
        projectNameET.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            hideKeyboard();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }


        });
        projectNameET.addTextChangedListener(new TextWatcher()
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
                project.setTitle(projectNameET.getText().toString());
            }
        });

        final EditText projectDescription = (EditText) rootView.findViewById(R.id.projectDescriptionET);
        projectDescription.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                project.setProjectDescription(projectDescription.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //TODO: Load Standard Icon from DB
        projectIconId = 1;
        setProjectIconInformations();

        Button changeProjectIconBtn = (Button) rootView.findViewById(R.id.changeProjectIcon);
        changeProjectIconBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), ChooseProjectIconActivity.class);
                startActivityForResult(intent, Integer.parseInt((v.getContext().getString(R.string.PROJECT_ICON_DIALOG_CODE))));
            }
        });

        ImageView projectImage = (ImageView) rootView.findViewById(R.id.projectIconIV);
        projectImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), ChooseProjectIconActivity.class);
                startActivityForResult(intent, Integer.parseInt((v.getContext().getString(R.string.PROJECT_ICON_DIALOG_CODE))));
            }
        });
        return rootView;
    }

    private void hideKeyboard()
    {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /**
     * set the information of the chosen icon to the view
     */
    private void setProjectIconInformations()
    {
        HashMap<String, String> infos = databaseHelper.getIconInformationsById(projectIconId);

        Bitmap projectIcon = loadProjectIcon(infos);

        project.setImage(projectIcon);

        project.setIconName(databaseHelper.getStringResourceValueByResourceName(infos.get("name")));
        project.setIconId(infos.get("id"));

        TextView iconName = (TextView) rootView.findViewById(R.id.tvProjectImageName);
        iconName.setText(project.getIconName());

        ImageView projectImage = (ImageView) rootView.findViewById(R.id.projectIconIV);
        projectImage.setImageBitmap(project.getImage());
    }


    /**
     * Load the project icon from assets folder
     *
     * @param infos
     * @return projectIcon
     */
    private Bitmap loadProjectIcon(HashMap<String, String> infos)
    {
        AssetManager assetManager = getContext().getAssets();
        InputStream istr;
        Bitmap projectIcon = null;
        try
        {
            istr = assetManager.open(infos.get("path"));
            projectIcon = BitmapFactory.decodeStream(istr);
        } catch (IOException e)
        {
            // handle exception
        }
        return projectIcon;
    }

    @Override
    public void onReloadViews(String text)
    {

    }

    public void updateChosenIcon(int iconId)
    {
        this.projectIconId = iconId;
        setProjectIconInformations();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
