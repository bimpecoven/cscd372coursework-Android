package com.impecoven.blake.bimpecovenlab5;

import android.os.Bundle;
import android.os.Handler;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Blake Impecoven - Spring 2016 - Lab5", Toast.LENGTH_LONG).show();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
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
}
