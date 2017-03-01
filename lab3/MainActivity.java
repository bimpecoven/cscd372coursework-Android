package com.impecoven.blake.bimpecovenlab3;

/**
 * Created by Blake Impecoven on 4/14/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    MyAdapter fullList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpandableListView manuList = (ExpandableListView) findViewById(R.id.expandableListView);
        ArrayList<Manufacturer> manufacturers = new ArrayList<>();
        fullList = new MyAdapter(this, manufacturers);

        try {
            if(parseFile("make_model.txt", fullList) == false) {
                Toast.makeText(this, "Error parsing the file...", Toast.LENGTH_LONG).show();
            }//end if
            else {
                manuList.setAdapter(fullList);
            }//end else
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert manuList != null;
        manuList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String displayClick = fullList.manufacturers.get(groupPosition).getManufacturerName();
                displayClick += " " + fullList.manufacturers.get(groupPosition).getModelName(childPosition);
                Toast.makeText(getApplicationContext(), displayClick, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //State restoration
        if(savedInstanceState != null) {
            int count;

            //first group save
            this.fullList.manufacturers.get(0).manufacturerName = savedInstanceState.getString("first_name", fullList.manufacturers.get(0).manufacturerName);
            this.fullList.manufacturers.get(0).models = savedInstanceState.getStringArrayList("first_models");

            //second group save
            this.fullList.manufacturers.get(1).manufacturerName = savedInstanceState.getString("second_name", fullList.manufacturers.get(1).manufacturerName);
            this.fullList.manufacturers.get(1).models = savedInstanceState.getStringArrayList("second_models");

            //third group save
            this.fullList.manufacturers.get(2).manufacturerName = savedInstanceState.getString("third_name", fullList.manufacturers.get(2).manufacturerName);
            this.fullList.manufacturers.get(2).models = savedInstanceState.getStringArrayList("third_models");

            //fourth group save
            this.fullList.manufacturers.get(3).manufacturerName = savedInstanceState.getString("fourth_name", fullList.manufacturers.get(3).manufacturerName);
            this.fullList.manufacturers.get(4).models = savedInstanceState.getStringArrayList("fourth_models");

            //fifth group save
            this.fullList.manufacturers.get(4).manufacturerName = savedInstanceState.getString("fifth_name", fullList.manufacturers.get(4).manufacturerName);
            this.fullList.manufacturers.get(4).models = savedInstanceState.getStringArrayList("fifth_models");

            //sixth group save
            this.fullList.manufacturers.get(5).manufacturerName = savedInstanceState.getString("sixth_name", fullList.manufacturers.get(5).manufacturerName);
            this.fullList.manufacturers.get(5).models = savedInstanceState.getStringArrayList("sixth_models");

        }//end if

    }//end onCreate


    private boolean parseFile(final String fileName, MyAdapter fullList) throws IOException {
        BufferedReader reader = null;
        try {
          //Try to open our file
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            String buffer;
            while((buffer = reader.readLine()) != null) {
                String[]split = buffer.split(",", 2); // we set our limit to 2 because we will later
                                                      // parse the string in another method.
                ArrayList<String> models = new ArrayList<>();

                Manufacturer newManu = new Manufacturer(split[0], models);
                newManu.newModels(split[1]);
                fullList.manufacturers.add(newManu);
            }//end while
        }//end try
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }//end catch

        finally {
            if(reader != null) { //check for file closure
                try {
                    reader.close();
                }//end try
                catch(IOException e) {
                    e.printStackTrace();
                }//end catch
            }//end if
        }//end finally

        return true;
    }//end parseFile


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
        if (id == R.id.action_about) {
            Toast.makeText(this, "Blake Impecoven - Spring 2016 - Lab3", Toast.LENGTH_LONG).show();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //first group save
        outState.putString("first_name", fullList.manufacturers.get(0).getManufacturerName());
        outState.putStringArrayList("first_models", fullList.manufacturers.get(0).getModels());

        //second group save
        outState.putString("second_name", fullList.manufacturers.get(1).getManufacturerName());
        outState.putStringArrayList("second_models", fullList.manufacturers.get(1).getModels());

        //third group save
        outState.putString("third_name", fullList.manufacturers.get(2).getManufacturerName());
        outState.putStringArrayList("third_models", fullList.manufacturers.get(2).getModels());

        //fourth group save
        outState.putString("fourth_name", fullList.manufacturers.get(3).getManufacturerName());
        outState.putStringArrayList("fourth_models", fullList.manufacturers.get(3).getModels());

        //fifth group save
        outState.putString("fifth_name", fullList.manufacturers.get(4).getManufacturerName());
        outState.putStringArrayList("fifth_models", fullList.manufacturers.get(4).getModels());

        //sixth group save
        outState.putString("sixth_name", fullList.manufacturers.get(5).getManufacturerName());
        outState.putStringArrayList("sixth_models", fullList.manufacturers.get(5).getModels());

    }//end onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }//end onRestoreInstanceState
}//end class
