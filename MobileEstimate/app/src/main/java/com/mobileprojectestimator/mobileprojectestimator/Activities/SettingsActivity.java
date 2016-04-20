package com.mobileprojectestimator.mobileprojectestimator.Activities;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileprojectestimator.mobileprojectestimator.R;
import com.mobileprojectestimator.mobileprojectestimator.Util.database.DataBaseHelper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity
{
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
    {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value)
        {
            String stringValue = value.toString();

            if (preference instanceof ListPreference)
            {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else
            {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference)
    {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            if (!super.onMenuItemSelected(featureId, item))
            {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane()
    {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName)
    {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || UserPreferenceFragment.class.getName().equals(fragmentName)
                || SynchronisationPreferenceFragment.class.getName().equals(fragmentName)
                || SupportPreferenceFragment.class.getName().equals(fragmentName)
                || DatabasePreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class UserPreferenceFragment extends PreferenceFragment
    {
        private SharedPreferences sharedPref;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_user_informations);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            final Preference usernamePref = (Preference) findPreference(getString(R.string.pref_key_user_name));
            usernamePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newVal)
                {
                    Log.d("INFO", "Shared pref User name new val: " + newVal.toString());
                    String userName = newVal.toString().trim();
                    if (userName.isEmpty() || userName.length() == 0 || userName.equals("") || userName == null)
                    {
                        SharedPreferences.Editor prefsEditor = sharedPref.edit();
                        prefsEditor.putString(getString(R.string.pref_key_user_name), "");
                        prefsEditor.commit();
                        usernamePref.setSummary(getString(R.string.pref_user_name_summary));
                    } else
                    {
                        usernamePref.setSummary(userName);
                    }
                    return true;
                }

            });
            String userName = sharedPref.getString(getString(R.string.pref_key_user_name), "");
            if (userName.isEmpty() || userName.equals(""))
            {
                usernamePref.setSummary(getString(R.string.pref_user_name_summary));
            } else
            {
                bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_user_name)));
            }
            final Preference passwordPref = (Preference) findPreference(getString(R.string.pref_key_user_password));
            passwordPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newVal)
                {
                    String userName = newVal.toString();
                    if (userName.isEmpty() || userName.length() == 0 || userName.equals("") || userName == null)
                    {
                        SharedPreferences.Editor prefsEditor = sharedPref.edit();
                        prefsEditor.putString(getString(R.string.pref_key_user_password), "");
                        prefsEditor.commit();
                        passwordPref.setSummary(getString(R.string.pref_password_summary));
                    } else
                    {
                        passwordPref.setSummary(getString(R.string.pref_password_is_set_summary));
                    }
                    return true;
                }

            });
            String password = sharedPref.getString(getString(R.string.pref_key_user_password), "");
            if (password.isEmpty() || password.equals(""))
            {
                passwordPref.setSummary(getString(R.string.pref_password_summary));
            } else
            {
                passwordPref.setSummary(getString(R.string.pref_password_is_set_summary));
            }

            Preference checkLogin = (Preference) findPreference(getString(R.string.pref_check_login));
            checkLogin.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    return true;
                }
            });

            Preference userInformations = (Preference) findPreference(getString(R.string.preferences_user_informations_key));
            userInformations.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    Intent i = new Intent(getActivity(), UserInformationsActivity.class);
                    startActivity(i);
                    return true;
                }
            });
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if (id == android.R.id.home)
            {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SynchronisationPreferenceFragment extends PreferenceFragment
    {
        private SharedPreferences sharedPref;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_synchronisation);
            setHasOptionsMenu(true);

            sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            final Preference syncFreqPref = (Preference) findPreference(getString(R.string.pref_key_sync_frequency));
            syncFreqPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newVal)
                {
                    String value = newVal.toString();
                    if (value.isEmpty() || value.equals(""))
                    {
                        syncFreqPref.setSummary(getString(R.string.pref_sync_frequency_summary));
                    } else
                    {
                        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_sync_frequency)));
                    }
                    return true;
                }

            });
            String syncFreq = sharedPref.getString(getString(R.string.pref_key_sync_frequency), "");
            if (syncFreq.isEmpty() || syncFreq.equals(""))
            {
                syncFreqPref.setSummary(getString(R.string.pref_sync_frequency_summary));
            } else
            {
                bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_sync_frequency)));
            }


            final Preference connectOptionsPref = (Preference) findPreference(getString(R.string.pref_key_sync_connection_option));
            connectOptionsPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newVal)
                {
                    if (((HashSet) newVal).size() == 0)
                    {
                        connectOptionsPref.setSummary("");
                    } else
                    {
                        String[] connections = getResources().getStringArray(R.array.pref_sync_connection_option_titles);
                        String output = "";

                        if (((HashSet) newVal).contains("1"))
                        {
                            output = output + connections[0] + ", ";
                        }
                        if (((HashSet) newVal).contains("2"))
                        {
                            output = output + connections[1] + ", ";
                        }
                        if (((HashSet) newVal).contains("3"))
                        {
                            output = output + connections[2] + ", ";
                        }
                        int endIndex = output.lastIndexOf(",");
                        if (endIndex != -1)
                        {
                            output = output.substring(0, endIndex);
                        }
                        connectOptionsPref.setSummary(output);

                    }
                    return true;
                }

            });
            Set<String> connectOptions = sharedPref.getStringSet(getString(R.string.pref_key_sync_connection_option), new HashSet<String>());
            if (connectOptions.size() == 0)
            {
                connectOptionsPref.setSummary("");
            } else
            {
                String[] connections = getResources().getStringArray(R.array.pref_sync_connection_option_titles);
                String output = "";

                if (((HashSet) connectOptions).contains("1"))
                {
                    output = output + connections[0] + ", ";
                }
                if (((HashSet) connectOptions).contains("2"))
                {
                    output = output + connections[1] + ", ";
                }
                if (((HashSet) connectOptions).contains("3"))
                {
                    output = output + connections[2] + ", ";
                }
                int endIndex = output.lastIndexOf(",");
                if (endIndex != -1)
                {
                    output = output.substring(0, endIndex);
                }
                connectOptionsPref.setSummary(output);

            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if (id == android.R.id.home)
            {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DatabasePreferenceFragment extends PreferenceFragment
    {
        /**
         * The Database Helper Object for sending queries to the database
         */
        protected DataBaseHelper databaseHelper;

        public static String formatSize(long v)
        {
            if (v < 1024) return v + " B";
            int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
            return String.format("%.1f %sB", (double) v / (1L << (z * 10)), " KMGTPE".charAt(z));
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_database);
            setHasOptionsMenu(true);

            initDatabase();

            final Preference databaseSize = (Preference) findPreference(getString(R.string.pref_database_size_key));
            long size = new File(databaseHelper.getDbPath() + databaseHelper.getDbName()).length();
            databaseSize.setSummary(formatSize(size));
            databaseSize.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    //File Size in MB
                    long size = new File(databaseHelper.getDbPath() + databaseHelper.getDbName()).length();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle(R.string.alert_dialog_database_size_title);
                    alertDialogBuilder.setMessage(getActivity().getString(R.string.alert_dialog_database_size_content) + formatSize(size));
                    //null should be your on click listener
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });


                    alertDialogBuilder.create();
                    alertDialogBuilder.show();
                    return true;
                }
            });


            Preference viewDeletedProjects = (Preference) findPreference(getString(R.string.pref_deleted_projects_key));
            viewDeletedProjects.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    Intent i = new Intent(getActivity(), DeletedProjectsActivity.class);
                    startActivity(i);
                    return true;
                }
            });

            final Preference completeDeleteProjects = (Preference) findPreference(getString(R.string.pref_delete_projects_key));
            completeDeleteProjects.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle(R.string.alert_dialog_complete_delete_projects_title);
                    alertDialogBuilder.setMessage(getActivity().getString(R.string.alert_dialog_complete_delete_projects_content));
                    //null should be your on click listener
                    alertDialogBuilder.setPositiveButton(getActivity().getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            int projects = databaseHelper.completeDeleteProjects();
                            Toast.makeText(getActivity(), projects + " Project(s) are completly deleted from the database now.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialogBuilder.setNegativeButton(getActivity().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.create();
                    alertDialogBuilder.show();
                    return true;
                }
            });
        }

        /**
         * Initialise the Database Helper class and loads the database
         */
        protected void initDatabase()
        {
            Log.d("Info", "Database Initialisation");
            this.databaseHelper = new DataBaseHelper(getActivity());

            try
            {

                databaseHelper.createDataBase();

            } catch (IOException ioe)
            {

                throw new Error("Unable to create database");

            }

            try
            {

                databaseHelper.openDataBase();

            } catch (SQLException sqle)
            {
                Log.d("ERROR", sqle.toString());
            }

            databaseHelper.logDatabaseInformation();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if (id == android.R.id.home)
            {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SupportPreferenceFragment extends PreferenceFragment
    {
        private SharedPreferences sharedPref;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_support);
            setHasOptionsMenu(true);

            sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            Preference about = (Preference) findPreference(getString(R.string.pref_about_key));
            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    Intent i = new Intent(getActivity(), AboutActivity.class);
                    startActivity(i);
                    return true;
                }
            });

            Preference contactSupport = (Preference) findPreference(getString(R.string.pref_ask_question_key));
            contactSupport.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    Intent i = new Intent(getActivity(), ContactSupportActivity.class);
                    startActivity(i);
                    return true;
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if (id == android.R.id.home)
            {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
