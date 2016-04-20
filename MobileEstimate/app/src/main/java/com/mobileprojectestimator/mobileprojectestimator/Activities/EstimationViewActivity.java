package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.EstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class EstimationViewActivity extends DatabaseActivity
{

    private int projectId;
    private int relatedProjectId;
    private ListView lvEstimationItems;
    private Button bTransferEvaluation;
    private TextView tvEvaluatedDays;
    private Project relatedProject;
    private Project project;
    private EstimationViewItemsAdapter dataAdapter;
    private ArrayList<EstimationItem> estimationItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimation_view);

        initDatabase();

        Intent intent = getIntent();
        projectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), 0);
        relatedProjectId = intent.getIntExtra(getString(R.string.ACTIVITY_EXTRA_SELECTED_PROJECTID), 0);

        project = databaseHelper.loadProjectById(this, String.valueOf(projectId));
        relatedProject = databaseHelper.loadProjectById(this, String.valueOf(relatedProjectId));

        tvEvaluatedDays = (TextView) findViewById(R.id.tvEvaluatedDays);
        bTransferEvaluation = (Button) findViewById(R.id.bTransferEvaluation);
        lvEstimationItems = (ListView) findViewById(R.id.lvEstimationItems);

        tvEvaluatedDays.setText(getString(R.string.function_point_estimation_evaluated_person_days) + " " + project.getEvaluatedPersonDays());

        if (relatedProject.getEstimationMethod().equals(project.getEstimationMethod()))
        {
            bTransferEvaluation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    transferEvaluation();
                }
            });
        } else
        {
            bTransferEvaluation.setText(getString(R.string.b_transfer_evaluation_not_possible));
        }

        loadEstimationItems();

        setTitle(project.getTitle());
    }

    private void loadEstimationItems()
    {
        estimationItems = new ArrayList<>();
        if (project.getEstimationItems().isEmpty() || project.getEstimationItems().size() == 0)
        {
            FunctionPointItem item = new FunctionPointItem();
            estimationItems.add(item);
        } else
        {
            estimationItems = project.getEstimationItems();
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new EstimationViewItemsAdapter(this,
                R.layout.deleted_projects_listview_items, estimationItems, project.getEstimationMethod());
        dataAdapter.notifyDataSetChanged();
        lvEstimationItems.setAdapter(dataAdapter);
    }

    private void transferEvaluation()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format(getString(R.string.transfer_evaluation_dialog_title), relatedProject.getTitle()));
        builder.setMessage(String.format(getString(R.string.transfer_evaluation_dialog_content), relatedProject.getTitle()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if (relatedProject.getEstimationMethod().equals(getString(R.string.estimation_technique_function_point)))
                        {
                            copyEstimationItems();
                            databaseHelper.updateFunctionPointEstimationItems(relatedProject.getEstimationItems());
                            Toast.makeText(EstimationViewActivity.this, R.string.transfer_evaluation_dialog_success, Toast.LENGTH_SHORT).show();
                        } else
                        {
                            Toast.makeText(EstimationViewActivity.this, R.string.transfer_evaluation_dialog_not_possible, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }

    private void copyEstimationItems()
    {
        if (project.getEstimationMethod().equals(getString(R.string.estimation_technique_function_point)))
        {
            for (EstimationItem i : project.getEstimationItems())
            {
                FunctionPointItem item = (FunctionPointItem) i;
                for (EstimationItem j : relatedProject.getEstimationItems())
                {
                    if (j.getItemName().equals(item.getItemName()))
                    {
                        j = (FunctionPointItem) j;
                        ((FunctionPointItem) j).setFunctionPointCategoryItems(item.getFunctionPointCategoryItems());
                        ((FunctionPointItem) j).setTotalAmount(item.getTotalAmount());
                        break;
                    }

                }
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        finishView();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void finishView()
    {
        Intent i = new Intent();
        i.putExtra(getString(R.string.ACTIVITY_EXTRA_PROJECTID), relatedProjectId);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private class EstimationViewItemsAdapter extends ArrayAdapter<EstimationItem>
    {

        private ArrayList<EstimationItem> estimationItemsList;

        private String estimationMethod;

        public EstimationViewItemsAdapter(Context context, int textViewResourceId,
                                          ArrayList<EstimationItem> list, String method)
        {
            super(context, textViewResourceId, list);
            this.estimationItemsList = new ArrayList<>();
            this.estimationItemsList.addAll(list);
            this.estimationMethod = method;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null)
            {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.estimation_view_listview_items, null);

                holder = new ViewHolder();
                holder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
                holder.tvItemValue = (TextView) convertView.findViewById(R.id.tvItemValue);

                convertView.setTag(holder);

            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            if (estimationMethod.equals(getString(R.string.estimation_technique_function_point)))
            {
                FunctionPointItem item = (FunctionPointItem) estimationItemsList.get(position);

                holder.tvItemName.setText(item.getItemName());
                holder.tvItemValue.setText(String.format("Simple: %d Medium: %d Complex: %d", item.getSimpleValue(), item.getMediumValue(), item.getComplexValue()));
            } else
            {
                holder.tvItemName.setText("Error");
                holder.tvItemValue.setText("While Loading Estimation Item Informations");
            }
            return convertView;
        }

        private class ViewHolder
        {
            TextView tvItemName;
            TextView tvItemValue;
        }

    }
}
