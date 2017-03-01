package com.impecoven.blake.bimpecovenprojectmissilecommand;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {
        // Required empty public constructor
    }//end constructor

    //    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);

        switch(key){
            case "scud_max_fall":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 13)));
                break;

            case "scud_min_fall":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 7)));
                break;

            case "level_speed":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 5)));
                break;

            case "patriot_advantage":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 5)));
                break;

            case "launch_per_sec":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 50)));
                break;

            case "launch_per_level":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 20)));
                break;

            default:
                pref.setSummary(sharedPreferences.getString(key, ""));
                break;
        }//end switch
    }//end onSharedPreferenceChangeListener

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load the XML definition of our preferences
        addPreferencesFromResource(R.xml.preferences);

        //re-init on backing out of prefs
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        onSharedPreferenceChanged(sharedPreferences, "scud_max_fall");
        onSharedPreferenceChanged(sharedPreferences, "scud_min_fall");
        onSharedPreferenceChanged(sharedPreferences, "level_speed");
        onSharedPreferenceChanged(sharedPreferences, "patriot_advantage");
        onSharedPreferenceChanged(sharedPreferences, "launch_per_sec");
        onSharedPreferenceChanged(sharedPreferences, "launch_per_level");
    }//end onCreate

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(this);
    }//end onPause

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().
                registerOnSharedPreferenceChangeListener(this);
    }//end onResume
}//end class
