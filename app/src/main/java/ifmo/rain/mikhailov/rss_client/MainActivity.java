package ifmo.rain.mikhailov.rss_client;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import ifmo.rain.mikhailov.rss_client.fragments.FragmentMain;
import ifmo.rain.mikhailov.rss_client.fragments.FragmentSettings;


/**
 * Created by user on 18.12.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public FragmentMain fMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//
//        startService(new Intent(MainActivity.this, BackgroundDownloadService.class));

        final FeedsDatabase dbHelper = new FeedsDatabase(this);
        final SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dbHelper.put(sqLiteDatabase, new RSSItem("KEK", "LUL", new Date(), "ROFL"), "MAMKIN LINK", "MAMKI");
                dbHelper.put(sqLiteDatabase, new RSSItem("KEKI4", "LUL", new Date(), "ROFL"), "MAMKIN LINK", "PAPKA");
                dbHelper.put(sqLiteDatabase, new RSSItem("ORU", "LUL", new Date(), "ROFL"), "PAPKIN LINK", "PAPKA");

                try{
                    ArrayList<RSSItem> list = dbHelper.get(sqLiteDatabase, "PAPKIN LINK");
                    for(RSSItem item : list){
                        Log.d("ROFL", item.toString());
                    }
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                    Log.d("OCHEN ZHAL", "KEK");
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fMain = new FragmentMain();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fMain = new FragmentMain();
        android.support.v4.app.FragmentTransaction Ftrans = getSupportFragmentManager().beginTransaction();

        switch (id){
            case R.id.nav_Main:{
                fMain.nameOfNews = "mainRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_politics:{
                fMain.nameOfNews = "politicRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_society:{
                fMain.nameOfNews = "societyRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_settings:{
                Intent intent = new Intent(this, FragmentSettings.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_business: {
                fMain.nameOfNews = "businessRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_world: {
                fMain.nameOfNews = "worldRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_sport: {
                fMain.nameOfNews = "sportRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_incident: {
                fMain.nameOfNews = "incidentRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_culture: {
                fMain.nameOfNews = "cultureRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_science: {
                fMain.nameOfNews = "scienceRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_computers: {
                fMain.nameOfNews = "computersRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_auto: {
                fMain.nameOfNews = "autoRss";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
        }

        Ftrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
