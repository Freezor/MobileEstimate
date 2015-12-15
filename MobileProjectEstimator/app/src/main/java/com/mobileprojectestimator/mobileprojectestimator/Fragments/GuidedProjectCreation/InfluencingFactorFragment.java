package com.mobileprojectestimator.mobileprojectestimator.Fragments.GuidedProjectCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 01.11.2015, 15:53.
 * Project: MobileProjectEstimator
 */
public class InfluencingFactorFragment extends GuidedCreationFragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public Project project;
    protected TextView estimationMethodTitelTV;
    protected View rootView;
    protected ViewGroup container;
    private ArrayList<String> influencingFactorItems;
    private String text;
    private ArrayAdapter<String> influencingFactorsAdapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public InfluencingFactorFragment newInstance(Project proj)
    {
        InfluencingFactorFragment fragment = new InfluencingFactorFragment();
        Bundle args = new Bundle();
        project = proj;
        //TODO: Projekt als Json mitgeben
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("INFO", "InfluencingFactorFragment: onCreate");
        initDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("INFO", "onCreateView");
        //TODO: always load & safe inffactset
        this.container = container;
        rootView = inflater.inflate(R.layout.influencing_factor_fragment, container, false);
        ImageView dot1 = (ImageView) rootView.findViewById(R.id.dot1);
        dot1.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot2 = (ImageView) rootView.findViewById(R.id.dot2);
        dot2.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot3 = (ImageView) rootView.findViewById(R.id.dot3);
        dot3.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot4 = (ImageView) rootView.findViewById(R.id.dot4);
        dot4.setBackgroundResource(R.drawable.circle_blue);
        ImageView dot5 = (ImageView) rootView.findViewById(R.id.dot5);
        dot5.setBackgroundResource(R.drawable.circle_blue);

        estimationMethodTitelTV = (TextView) rootView.findViewById(R.id.textViewChosenEstimationMethod);
        estimationMethodTitelTV.setText(text);

        Button factorsetButton = (Button) rootView.findViewById(R.id.influencingFactorSetButton);
        factorsetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(project.getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public ArrayList<String> getInfluencingFactorItems()
    {
        return influencingFactorItems;
    }

    public ArrayAdapter<String> getInfluencingFactorsAdapter()
    {
        return influencingFactorsAdapter;
    }

    public void setNewInfluencingFactorItems(ArrayList<String> items)
    {
        influencingFactorItems = new ArrayList<>();
        influencingFactorItems = items;
        influencingFactorsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, influencingFactorItems);
    }

    @Override
    public void onReloadViews(String text)
    {
        this.text = text;
    }
}
