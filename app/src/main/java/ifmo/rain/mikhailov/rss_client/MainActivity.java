package ifmo.rain.mikhailov.rss_client;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ifmo.rain.mikhailov.rss_client.fragments.FragmentMain;
import ifmo.rain.mikhailov.rss_client.fragments.FragmentSettings;
import ifmo.rain.mikhailov.rss_client.settings.ArrayOfRss;
import ifmo.rain.mikhailov.rss_client.settings.SettingsOfRssChanel;


/**
 * Created by user on 18.12.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public FragmentMain fMain;
    public SettingsOfRssChanel fSettings;
    public String nameOfGroup;
    public static ArrayOfRss globalRssChanel = new ArrayOfRss();
    final String local = "nameOfGroup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameOfGroup = "main";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FeedsDatabase dbHelper = FeedsDatabase.getInstance(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        startService(new Intent(MainActivity.this, BackgroundDownloadService.class));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("ifmo.rain.mikhailov.settings.SettingActivityForRss");
                intent.putExtra(local, nameOfGroup);
                startActivity(intent);
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
        fSettings = new SettingsOfRssChanel();
        android.support.v4.app.FragmentTransaction Ftrans = getSupportFragmentManager().beginTransaction();

        switch (id){
            case R.id.nav_Main:{
                fMain.nameOfCategory = "main";
                nameOfGroup = "main";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_politics:{
                fMain.nameOfCategory = "politic";
                nameOfGroup = "politic";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_society:{
                fMain.nameOfCategory = "society";
                nameOfGroup = "society";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_settings:{
                nameOfGroup = "main";
                Intent intent = new Intent(this, FragmentSettings.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_business: {
                nameOfGroup = "business";
                fMain.nameOfCategory = "business";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_world: {
                nameOfGroup = "world";
                fMain.nameOfCategory = "world";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_sport: {
                nameOfGroup = "sport";
                fMain.nameOfCategory = "sport";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_incident: {
                nameOfGroup = "incident";
                fMain.nameOfCategory = "incident";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_culture: {
                nameOfGroup = "culture";
                fMain.nameOfCategory = "culture";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_science: {
                nameOfGroup = "science";
                fMain.nameOfCategory = "science";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_computers: {
                nameOfGroup = "computers";
                fMain.nameOfCategory = "computers";
                Ftrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_auto: {
                nameOfGroup = "auto";
                fMain.nameOfCategory = "auto";
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
