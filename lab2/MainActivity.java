package com.impecoven.blake.bimpecovenlab2;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CharSequence title;
    private CharSequence exposed;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    ImageView setPicture;
    ListView drawerList;
    TextView textLogo;
    String[] images;
    final int[] ids = new int[] {
        R.drawable.cindy,
        R.drawable.fred,
        R.drawable.kate,
        R.drawable.keith,
        R.drawable.logo,
        R.drawable.matt,
        R.drawable.rickey
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = getTitle();
        exposed = "Select a Page";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        textLogo = (TextView)findViewById(R.id.textLogo);
        images = getResources().getStringArray(R.array.photo_names);
        setPicture = (ImageView) findViewById(R.id.imageView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.opened_drawer) {

            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                setTitle(title);
                invalidateOptionsMenu();
            }//end onDrawerClosed

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(exposed);
                invalidateOptionsMenu();
            }//end onDrawerOpened

        }; //end drawer toggle

        drawerLayout.setDrawerListener(drawerToggle);

        if(savedInstanceState != null) {
            int tag = savedInstanceState.getInt("id");//grab our tag
            setPicture.setImageResource(tag);
            setPicture.setTag(tag); //reset the tag for second rotation
        }//end if restore

        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.nav_list_row, R.id.textLogo, images));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPicture.setImageResource(ids[position]);
                setPicture.setTag(ids[position]);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }//end onCreate

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }//end onPostCreate

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", (Integer) setPicture.getTag());
        //setPicture.getTag();
    }//end onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }//end onRestoreInstanceState

    /* This method will be called within a call to
     * invalidateOptionsMenu()
    **/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_about).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }//end onPrepareOptionsMenu

    /* The next two methods will handle all of our
     * menu stuff (onCreateOptionsMenu & onOptionsItemSelected)
    **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about) {
            Toast.makeText(this.getBaseContext(),"Blake Impecoven - Lab 2", Toast.LENGTH_LONG).show();
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) { //this handles our drawer icon
            return true;
        }

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

}//end class
