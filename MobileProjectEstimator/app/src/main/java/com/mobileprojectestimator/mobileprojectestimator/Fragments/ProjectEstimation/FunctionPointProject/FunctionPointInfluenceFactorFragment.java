package com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.FunctionPointProject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database.DatabaseInfluenceFactorItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.InfluencingFactor;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.Fragments.ProjectEstimation.EstimationOverviewFragment;
import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.adapters.FunctionPointInfluenceListAdapter;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 02.11.2015, 15:38.
 * Project: MobileProjectEstimator
 */
public class FunctionPointInfluenceFactorFragment extends EstimationOverviewFragment
{
    protected InfluencingFactor influencingFactor;
    protected ListView fpInfluenceListView;
    protected FunctionPointInfluenceListAdapter projectInfluenceListAdapter;
    private Project project;
    private ArrayList<Object> fragmentsList;
    private TextView sumOfInfluences;
    private TextView factorInfluenceRating;
    private Context mContext;
    private ArrayList<DatabaseInfluenceFactorItem> influenceFactorItems;

    @Override
    public EstimationOverviewFragment newInstance(Project p)
    {
        FunctionPointInfluenceFactorFragment fragment = new FunctionPointInfluenceFactorFragment();
        Bundle args = new Bundle();
        project = p;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mContext = getActivity();
        initDatabase();

        View rootView = inflater.inflate(R.layout.fragment_function_point_influence_factor_activtiy, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        influencingFactor = new InfluencingFactor(this.getContext(), InfluencingFactor.FUNCTIONPOINTFACTORS);

        fpInfluenceListView = (ListView) rootView.findViewById(R.id.lvInfluenceFactors);
        projectInfluenceListAdapter = new FunctionPointInfluenceListAdapter(this, project.getInfluencingFactor().getInfluenceFactorItems(), project);
        projectInfluenceListAdapter.updateChosenValues(getContext());
        fpInfluenceListView.setAdapter(projectInfluenceListAdapter);
        fpInfluenceListView.setScrollbarFadingEnabled(false);

        sumOfInfluences = (TextView) rootView.findViewById(R.id.tvSumOfInfluences);
        sumOfInfluences.setText(String.format("%s %d", getContext().getString(R.string.function_point_sum_of_influences), this.project.getSumOfInfluences()));

        factorInfluenceRating = (TextView) rootView.findViewById(R.id.tvFactorInfluenceRating);
        factorInfluenceRating.setText(String.format("%s %s", getContext().getString(R.string.function_point_influence_rating), this.project.getFactorInfluenceRating()));

        Button changeInfluenceFactorButton = (Button) rootView.findViewById(R.id.bChangeFactorSet);
        changeInfluenceFactorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (project.isTerminated())
                {
                    openProjectIsTerminatedDialog();
                } else
                {
                    openInfluenceFactorSetDialog();
                }
            }
        });

        return rootView;
    }

    private void openProjectIsTerminatedDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_project_ist_terminated_title));
        builder.setMessage(R.string.dialog_project_ist_terminated_text);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openInfluenceFactorSetDialog()
    {
        Log.d("Info", "FunctionPointInfluenceFactorFragment: openInfluenceFactorSetDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.estimation_method_hint));

        influenceFactorItems = databaseHelper.getInfluenceFactorItems(databaseHelper.getEstimationMethodId(project.getEstimationMethod()));
        ArrayList<String> infItems = new ArrayList<>();
        for (DatabaseInfluenceFactorItem i : influenceFactorItems)
        {
            infItems.add(i.get_name());
        }

        final CharSequence[] items = infItems.toArray(new String[infItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                String factorName = items[item].toString();
                for (DatabaseInfluenceFactorItem i : influenceFactorItems)
                {
                    if (i.get_name().equals(factorName))
                    {
                        project.setInfluencingFactor(databaseHelper.loadInfluenceFactorById(i.get_id()));
                        databaseHelper.updateExistingProjectInfluenceFactor(project);
                        project = databaseHelper.loadProjectById(getContext(), String.valueOf(project.getProjectId()));
                        projectInfluenceListAdapter = new FunctionPointInfluenceListAdapter(projectInfluenceListAdapter.fragment, project.getInfluencingFactor().getInfluenceFactorItems(), project);
                        projectInfluenceListAdapter.updateChosenValues(getContext());
                        projectInfluenceListAdapter.notifyDataSetChanged();
                        fpInfluenceListView.setAdapter(projectInfluenceListAdapter);
                        sumOfInfluences.setText(String.format("%s %d", getContext().getString(R.string.function_point_sum_of_influences), project.getSumOfInfluences()));
                        factorInfluenceRating.setText(String.format("%s %s", getContext().getString(R.string.function_point_influence_rating), project.getFactorInfluenceRating()));
                        //TODO: Update Evaluated Function Points in Estimation Tab
                        break;
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
