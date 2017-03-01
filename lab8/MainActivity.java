package com.impecoven.blake.bimpecovenlab8;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    ImageView xImage;
    ImageView oImage;

    ImageView cell_00;
    ImageView cell_01;
    ImageView cell_02;
    ImageView cell_10;
    ImageView cell_11;
    ImageView cell_12;
    ImageView cell_20;
    ImageView cell_21;
    ImageView cell_22;

    Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First Row
                cell_00.setImageResource(R.drawable.blank);
                cell_00.setTag(R.drawable.blank);
                cell_01.setImageResource(R.drawable.blank);
                cell_01.setTag(R.drawable.blank);
                cell_02.setImageResource(R.drawable.blank);
                cell_02.setTag(R.drawable.blank);
                // Second Row
                cell_10.setImageResource(R.drawable.blank);
                cell_10.setTag(R.drawable.blank);
                cell_11.setImageResource(R.drawable.blank);
                cell_11.setTag(R.drawable.blank);
                cell_12.setImageResource(R.drawable.blank);
                cell_12.setTag(R.drawable.blank);
                // Third Row
                cell_20.setImageResource(R.drawable.blank);
                cell_20.setTag(R.drawable.blank);
                cell_21.setImageResource(R.drawable.blank);
                cell_21.setTag(R.drawable.blank);
                cell_22.setImageResource(R.drawable.blank);
                cell_22.setTag(R.drawable.blank);
            }//end onClick
        });

        /*
         * Initialize playing pieces, X and O.
        **/
        xImage = (ImageView)findViewById(R.id.xImage);
        oImage = (ImageView)findViewById(R.id.oImage);

        xImage.setTag(R.drawable.x);
        oImage.setTag(R.drawable.o);

        xImage.setOnTouchListener(this);
        oImage.setOnTouchListener(this);

        /*
         * Initialize the game board cells, 00-22,
         * set listeners and tags for all
        **/


        cell_00 = (ImageView)findViewById(R.id.cell00);
        cell_00.setOnDragListener(this);
        cell_01 = (ImageView)findViewById(R.id.cell01);
        cell_01.setOnDragListener(this);
        cell_02 = (ImageView)findViewById(R.id.cell02);
        cell_02.setOnDragListener(this);
        cell_10 = (ImageView)findViewById(R.id.cell10);
        cell_10.setOnDragListener(this);
        cell_11 = (ImageView)findViewById(R.id.cell11);
        cell_11.setOnDragListener(this);
        cell_12 = (ImageView)findViewById(R.id.cell12);
        cell_12.setOnDragListener(this);
        cell_20 = (ImageView)findViewById(R.id.cell20);
        cell_20.setOnDragListener(this);
        cell_21 = (ImageView)findViewById(R.id.cell21);
        cell_21.setOnDragListener(this);
        cell_22 = (ImageView)findViewById(R.id.cell22);
        cell_22.setOnDragListener(this);

        cell_00.setTag(R.drawable.blank);
        cell_01.setTag(R.drawable.blank);
        cell_02.setTag(R.drawable.blank);
        cell_10.setTag(R.drawable.blank);
        cell_11.setTag(R.drawable.blank);
        cell_12.setTag(R.drawable.blank);
        cell_20.setTag(R.drawable.blank);
        cell_21.setTag(R.drawable.blank);
        cell_22.setTag(R.drawable.blank);

        if(savedInstanceState != null) {
            // First Row
            int cell00 = savedInstanceState.getInt("cell00");
            cell_00.setImageResource(cell00);
            cell_00.setTag(cell00);
            int cell01 = savedInstanceState.getInt("cell01");
            cell_01.setImageResource(cell01);
            cell_01.setTag(cell01);
            int cell02 = savedInstanceState.getInt("cell02");
            cell_02.setImageResource(cell02);
            cell_02.setTag(cell02);
            // Second Row
            int cell10 = savedInstanceState.getInt("cell10");
            cell_10.setImageResource(cell10);
            cell_10.setTag(cell10);
            int cell11 = savedInstanceState.getInt("cell11");
            cell_11.setImageResource(cell11);
            cell_11.setTag(cell11);
            int cell12 = savedInstanceState.getInt("cell12");
            cell_12.setImageResource(cell12);
            cell_12.setTag(cell12);
            // Third Row
            int cell20 = savedInstanceState.getInt("cell20");
            cell_20.setImageResource(cell20);
            cell_20.setTag(cell20);
            int cell21 = savedInstanceState.getInt("cell21");
            cell_21.setImageResource(cell21);
            cell_21.setTag(cell21);
            int cell22 = savedInstanceState.getInt("cell22");
            cell_22.setImageResource(cell22);
            cell_22.setTag(cell22);
        }//end if

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
        if (id == R.id.action_about) {
            Toast.makeText(this, "Blake Impecoven - Spring 2016 - Lab8", Toast.LENGTH_LONG).show();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null, dragShadowBuilder, v, 0);
            return true;
        }//end if
        return false;
    }//end onTouch

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if(event.getAction() == DragEvent.ACTION_DROP) {

            ImageView dragged_cell = (ImageView)event.getLocalState();
            ImageView target_cell = (ImageView)v;
            /*
             * Condition checking to see if the user has played in this
             * cell already.
            **/
            Integer target_tag = (Integer)target_cell.getTag();

            if(target_tag == R.drawable.x || target_tag == R.drawable.o) {
                Toast.makeText(this, "Cell already marked!", Toast.LENGTH_SHORT).show();
                return false;
            }//end if

            Drawable dragged_draw = dragged_cell.getDrawable();
            target_cell.setImageDrawable(dragged_draw);
            Integer tag = (Integer)dragged_cell.getTag();
            target_cell.setTag(tag);
//
//            Integer integer = (Integer)target_cell.getTag();
//
//            target_cell.setImageDrawable();
//            target_cell.setTag(integer);
        }//end if
        return true;
    }//end onDrag

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // First Row
        outState.putInt("cell00", (Integer)cell_00.getTag());
        outState.putInt("cell01", (Integer)cell_01.getTag());
        outState.putInt("cell02", (Integer)cell_02.getTag());
        // Second Row
        outState.putInt("cell10", (Integer)cell_10.getTag());
        outState.putInt("cell11", (Integer)cell_11.getTag());
        outState.putInt("cell12", (Integer)cell_12.getTag());
        // Third Row
        outState.putInt("cell20", (Integer)cell_20.getTag());
        outState.putInt("cell21", (Integer)cell_21.getTag());
        outState.putInt("cell22", (Integer)cell_22.getTag());
    }//end onSaveInstanceState
}//end class
