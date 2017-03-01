package com.impecoven.blake.bimpecovenlab4_new;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public int ctr = 10;
    public SevenSegment sevenSeg;
    public SevenSegment sevenSeg2;
    public SevenSegment sevenSeg3;
    public SevenSegment sevenSeg4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sevenSeg = (SevenSegment)findViewById(R.id.view);
        sevenSeg2 = (SevenSegment)findViewById(R.id.view2);
        sevenSeg3 = (SevenSegment)findViewById(R.id.view3);
        sevenSeg4 = (SevenSegment)findViewById(R.id.view4);

        Button increment = (Button) findViewById(R.id.increment);

        assert increment != null;
        increment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ctr++;

                sevenSeg.setValue(ctr);
                sevenSeg2.setValue(ctr + 1);
                sevenSeg3.setValue(ctr + 2);
                sevenSeg4.setValue(ctr + 3);
                //sevenSeg.invalidate();
            }//end onClick
        });//end listener
        //sevenSeg.setValue(4);

        if(savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }//end onCreate

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
            Toast.makeText(this, "Blake Impecoven - Spring 2016 - Lab4", Toast.LENGTH_LONG).show();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("value", sevenSeg.getValue());
    }//end onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int saved = savedInstanceState.getInt("value");

        sevenSeg.setValue(saved);
        sevenSeg2.setValue(saved + 1);
        sevenSeg3.setValue(saved + 2);
        sevenSeg4.setValue(saved + 3);

        ctr = saved;
    }//end onRestoreInstanceState
}//end class
