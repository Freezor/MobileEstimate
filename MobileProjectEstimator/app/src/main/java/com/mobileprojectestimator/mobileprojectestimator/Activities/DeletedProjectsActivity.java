package com.mobileprojectestimator.mobileprojectestimator.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project.Project;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

public class DeletedProjectsActivity extends DatabaseActivity
{

    private boolean allProjectsSelected;
    private DeletedProjectsAdapter dataAdapter;
    private ListView listView;
    private MenuItem selectionMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_projects);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deleted_projects, menu);

        initDatabase();

        allProjectsSelected = false;

        displayListView();
        restoreSelectedButtonClick();
        eraseSelectedButtonClick();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_select_all_projects:
                selectAllProjects(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectAllProjects(MenuItem item)
    {
        selectionMenuItem = item;
        final ArrayList<Project> selectProjectsList = new ArrayList<Project>();
        if (allProjectsSelected)
        {
            item.setTitle(getString(R.string.action_select_all_projects));
            allProjectsSelected = false;
            ArrayList<Project> projectsList = dataAdapter.projectsList;
            for (int i = 0; i < projectsList.size(); i++)
            {
                Project p = projectsList.get(i);
                if (!p.isSelected())
                {
                    selectProjectsList.add(p);
                } else {
                    p.setSelected(false);
                    selectProjectsList.add(p);
                }
            }
        } else
        {
            item.setTitle(getString(R.string.action_de_select_all_projects));
            allProjectsSelected = true;
            ArrayList<Project> projectsList = dataAdapter.projectsList;
            for (int i = 0; i < projectsList.size(); i++)
            {
                Project p = projectsList.get(i);
                if (p.isSelected())
                {
                    selectProjectsList.add(p);
                } else {
                    p.setSelected(true);
                    selectProjectsList.add(p);
                }
            }
        }
        dataAdapter = new DeletedProjectsAdapter(this,
                R.layout.deleted_projects_listview_items, selectProjectsList);
        dataAdapter.notifyDataSetChanged();
        listView.setAdapter(dataAdapter);
    }

    private void displayListView()
    {
        listView = (ListView) findViewById(R.id.lvDeletedProjects);
        loadDeletedProjects();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // When clicked on row

            }
        });

    }

    private void loadDeletedProjects()
    {
        ArrayList<Project> projectsList = databaseHelper.getAllDeletedProjects(this);

        if (projectsList.isEmpty() || projectsList.size() == 0)
        {
            Project p = new Project(this);
            p.setTitle("No Deleted Projects in Database");
            p.setProjectId(-1);
            projectsList.add(p);
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new DeletedProjectsAdapter(this,
                R.layout.deleted_projects_listview_items, projectsList);
        dataAdapter.notifyDataSetChanged();
        listView.setAdapter(dataAdapter);
    }

    private void eraseSelectedButtonClick()
    {
        Button myButton = (Button) findViewById(R.id.bErase);
        myButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ArrayList<Project> projectsList = dataAdapter.projectsList;
                final ArrayList<Project> deleteProjectsList = new ArrayList<Project>();

                for (int i = 0; i < projectsList.size(); i++)
                {
                    Project p = projectsList.get(i);
                    if (p.isSelected())
                    {
                        deleteProjectsList.add(p);
                    }
                }
                if(deleteProjectsList.size()>=1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeletedProjectsActivity.this);
                    builder.setTitle(String.format(getString(R.string.dialog_erase_projects_title), deleteProjectsList.size()));
                    builder.setMessage(R.string.dialog_erase_projects_message)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    for (Project p : deleteProjectsList)
                                    {
                                        databaseHelper.deleteProjectFromDatabase(p.getProjectId());
                                    }
                                    loadDeletedProjects();
                                    Toast.makeText(getApplicationContext(),
                                            R.string.erase_projects_successful,
                                            Toast.LENGTH_LONG).show();
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
            }
        });
    }

    private void restoreSelectedButtonClick()
    {


        Button myButton = (Button) findViewById(R.id.bRecover);
        myButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ArrayList<Project> projectsList = dataAdapter.projectsList;
                final ArrayList<Project> recoverProjectsList = new ArrayList<Project>();

                for (int i = 0; i < projectsList.size(); i++)
                {
                    Project p = projectsList.get(i);
                    if (p.isSelected())
                    {
                        recoverProjectsList.add(p);
                    }
                }

                if(recoverProjectsList.size()>=1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeletedProjectsActivity.this);
                    builder.setTitle(String.format(getString(R.string.dialog_recover_projects_title), recoverProjectsList.size()));
                    builder.setMessage(R.string.dialog_recover_projects_message)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    //databaseHelper.deleteProjectFromDatabase(projectsList.get(position).getProjectId());
                                    //Only Set the deletion Flag to the project
                                    databaseHelper.recoverProjects(recoverProjectsList);
                                    loadDeletedProjects();
                                    Toast.makeText(getApplicationContext(),
                                            R.string.recover_projects_successful,
                                            Toast.LENGTH_LONG).show();
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
            }
        });

    }

    private class DeletedProjectsAdapter extends ArrayAdapter<Project>
    {

        private ArrayList<Project> projectsList;

        public DeletedProjectsAdapter(Context context, int textViewResourceId,
                                      ArrayList<Project> projectsList)
        {
            super(context, textViewResourceId, projectsList);
            this.projectsList = new ArrayList<Project>();
            this.projectsList.addAll(projectsList);
        }

        private class ViewHolder
        {
            TextView tvProjectName;
            CheckBox cbProject;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            Project p = projectsList.get(position);

            if (convertView == null)
            {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.deleted_projects_listview_items, null);

                holder = new ViewHolder();
                holder.tvProjectName = (TextView) convertView.findViewById(R.id.tvProjectName);
                holder.cbProject = (CheckBox) convertView.findViewById(R.id.cbDeletedProject);

                if (p.getProjectId() == -1)
                {
                    holder.cbProject.setVisibility(View.INVISIBLE);
                }

                convertView.setTag(holder);

                holder.cbProject.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        CheckBox cb = (CheckBox) v;
                        Project p = (Project) cb.getTag();
                        p.setSelected(cb.isChecked());
                    }
                });
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvProjectName.setText(p.getTitle());
            holder.cbProject.setText("");
            holder.cbProject.setChecked(p.isSelected());
            holder.cbProject.setTag(p);

            return convertView;

        }

    }

}
