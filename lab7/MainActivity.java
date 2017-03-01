package com.impecoven.blake.bimpecovenlab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Blake Impecoven on 5/4/16.
 */

public class MainActivity extends AppCompatActivity {

    float spring_stiffness;
    int coils;
    int displacement;
    String mass_shape;

    CustomView customView;
    boolean clicked = false;
    Handler clockHandler;
    TimerTask clockTimer;
    int frameRate = 100;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customView = (CustomView)findViewById(R.id.animation);
        Button start = (Button)findViewById(R.id.button);
        clockHandler = new Handler();

        assert start != null;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = !clicked;

                if(clicked) {
                    startTimer();
                }//end if on
                else {
                    timer.cancel();
                }//end if off
            }
        });
    }//end onCreate

    public void startTimer(){
        clockTimer = new TimerTask() {
            @Override
            public void run() {
                clockHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        customView.reDraw();
                    }
                });
            }
        };

        timer = new Timer(true);
        timer.scheduleAtFixedRate(clockTimer, 0L, (long)frameRate);
    }//end startTimer

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class) ;
                startActivity(intent) ;
                return true;
            case R.id.action_about:
                Toast.makeText(this, "Blake Impecoven - Spring 2016 - Lab7", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item) ;
        }//end switch
    }//end onOptionsItemSelected

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("top", customView.rectTop);
        outState.putFloat("velocity", customView.velocity);
        outState.putFloat("y", customView.y);
    }//end onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        customView.rectTop = savedInstanceState.getFloat("top");
        customView.velocity = savedInstanceState.getFloat("velocity");
        customView.y = savedInstanceState.getFloat("y");
    }//end onRestoreInstanceState

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * This will keep all of our preferences updated.
        **/
        spring_stiffness = Float.parseFloat(sharedPreferences.getString("spring_stiffness", "1.5f"));
        setSpringStiffness(spring_stiffness);

        coils = sharedPreferences.getInt("coils", 11);
        setCoils(coils);

        displacement = sharedPreferences.getInt("displacement", 0);
        setDisplacement(displacement);

        mass_shape = sharedPreferences.getString("mass_shape", "Rectangle");
        setMassShape(mass_shape);
    }//end onResume

    public void setSpringStiffness(final float spring_stiffness) { customView.k = spring_stiffness; }
    public void setCoils(final int coils){ customView.coils = coils; }
    public void setDisplacement(final int displacement) { customView.displacement = displacement; }
    public void setMassShape(final String mass_shape) { customView.mass_shape = mass_shape; }
}
