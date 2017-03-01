package com.impecoven.blake.bimpecovenprojectmissilecommand;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    float xAim;
    float yAim;
    final float gameWidth = 600;
    final float gameHeight = 600;
    float xFactor;
    float yFactor;

    int patriotCount = 30;
    int gameScore = 0;

    boolean clicked = false;
    Handler clockHandler;
    TimerTask clockTimer;
    int frameRate = 70;
    Timer timer;
    Button go;
    TextView remainingScuds;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameView = (GameView)findViewById(R.id.gameScreen);
        go = (Button)findViewById(R.id.begin_play);
        clockHandler = new Handler();
        remainingScuds = (TextView)findViewById(R.id.scuds);
        score = (TextView)findViewById(R.id.score);
        //patriotCount = gameView.patriotCount;

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = !clicked;

                if(clicked) { //start game
                    go.setText("Pause");
                    startTimer();

                    gameView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            xAim = event.getX();
                            yAim = event.getY();
                            xFactor = gameWidth / v.getWidth();
                            yFactor = gameHeight / v.getHeight();
                            gameView.setAimer(xAim * xFactor, yAim * yFactor);

                            gameView.reDraw();


                            remainingScuds.setText("Scuds Remaining: " + gameView.scudsLeft());

                            gameScore = gameView.score;
                            score.setText("Score: " + gameView.getScore());
                            return true;
                        }//end onTouch
                    });
                }//end if clicked

                else { //stop or restart game
                    timer.cancel();
                    go.setText("Resume");
                }//end else
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
                        gameView.reDraw();
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
                Toast.makeText(this, "Blake Impecoven - Spring 2016 - Project Missile Command", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item) ;
        }//end switch
    }//end onOptionsItemSelected
}//end class
