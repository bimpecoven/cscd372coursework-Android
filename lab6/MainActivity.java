package com.impecoven.blake.bimpecovenlab6;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager;
    FragmentTransaction transactionManager;
    String timestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        transactionManager = fragmentManager.beginTransaction();

        if(savedInstanceState == null) {
            if(!inLandscape()) {
                MainFragment mainFragment = new MainFragment();
                transactionManager.add(R.id.portHolder, mainFragment);
                transactionManager.commit();
            }//end if
            else { //dual pane mode
                DetailFragment detailFragment = new DetailFragment();
                transactionManager.add(R.id.detailHolder, detailFragment);
                transactionManager.commit();
            }//end else
//
//            DetailFragment detailFragment = (DetailFragment)fragmentManager.findFragmentById(R.id.portHolder);
//            transactionManager.add(R.id.portHolder, detailFragment);
        }//end if
        else {
            if(!inLandscape()) {
                MainFragment mainFragment = new MainFragment();
                transactionManager.replace(R.id.portHolder, mainFragment);
                transactionManager.commit();
                onFragmentInteraction(savedInstanceState.getString("update_string"));
                DetailFragment detailFragment = (DetailFragment)fragmentManager.findFragmentById(R.id.detailHolder);
                detailFragment.setText(savedInstanceState.getString("update_string"));
            }//end if
            else {
                DetailFragment detailFragment = (DetailFragment)fragmentManager.findFragmentById(R.id.detailHolder);
                detailFragment.setText(savedInstanceState.getString("update_string"));
            }//end else
        }//end else
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Blake Impecoven - Spring 2016 - Lab6", Toast.LENGTH_LONG).show();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    public void onFragmentInteraction(String str) {
        timestamp = str;

        if(inLandscape()) {//in landscape
            DetailFragment detailFragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailHolder);
            detailFragment.setText(str);
        }//end if
        else {//in portrait
            FragmentManager fragManage = getSupportFragmentManager();
            DetailFragment detailFragment = (DetailFragment) fragManage.findFragmentByTag("DETAIL_FRAGMENT");

            if (detailFragment == null) {
                Log.i("MainActivity", "Detail Fragment - NULL - Creating new one");

                detailFragment = new DetailFragment();
                Bundle arguments = new Bundle();
                arguments.putString("update_string", str);
                detailFragment.setArguments(arguments);
            }//end if
            else {
                Log.i("MainActivity", "Detail Fragment - Not NULL");
            }//end else

            FragmentTransaction transManage = fragManage.beginTransaction();
            transManage.replace(R.id.portHolder, detailFragment, "DETAIL_FRAGMENT");

            int count = fragmentManager.getBackStackEntryCount();
            if (count == 0 || (count > 0 && !fragmentManager.getBackStackEntryAt(count - 1).getName().equals("DETAIL_FRAGMENT"))) {
                transManage.addToBackStack("DETAIL_FRAGMENT");
            }//end if

            transManage.commit();

        }//end else
    }//end onFragmentInteraction

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("update_string", timestamp);
    }//end onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }//end onRestoreInstanceState

    /*
     * Returns true if we are in landscape mode, false if portrait
    **/
    public boolean inLandscape() {
        return getResources().getBoolean(R.bool.dual_pane);
    }//end inLandscape


}
