package com.impecoven.blake.bimpecovenlab7;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            case "spring_stiffness":
                pref.setSummary(sharedPreferences.getString(key, "1.5"));
                break;

            case "coils":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 11)));
                break;

            case "displacement":
                pref.setSummary(Integer.toString(sharedPreferences.getInt(key, 0)));
                break;

            case "mass_shape":
                pref.setSummary(sharedPreferences.getString(key, "rectangle"));
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
        onSharedPreferenceChanged(sharedPreferences, "spring_stiffness");
        onSharedPreferenceChanged(sharedPreferences, "coils");
        onSharedPreferenceChanged(sharedPreferences, "displacement");
        onSharedPreferenceChanged(sharedPreferences, "mass_shape");
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
