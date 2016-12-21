package ifmo.rain.mikhailov.rss_client;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import ifmo.rain.mikhailov.rss_client.fragments.FragmentBookmarks;
import ifmo.rain.mikhailov.rss_client.fragments.FragmentMain;
import ifmo.rain.mikhailov.rss_client.initialize.InitializeDatabaseByCommonObject;
import ifmo.rain.mikhailov.rss_client.settings.ArrayOfRss;
import ifmo.rain.mikhailov.rss_client.settings.SettingsOfRssChanel;

import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_AUTO;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_BOOKMARK;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_BUSINESS;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_COMPUTERS;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_CULTURE;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_INCIDENT;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_MAIN;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_POLITICS;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_SCIENCE;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_SOCIETY;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_SPORT;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_WORLD;


/**
 * Created by user on 18.12.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public FragmentMain fMain;
    public FragmentBookmarks fBook;
    public SettingsOfRssChanel fSettings;
    public String nameOfGroup = null;
    public static ArrayOfRss globalRssChanel = new ArrayOfRss();
    final String local = "nameOfGroup";

    private static final String KEY_OF_GROUP = "KEY_OF_GROUP";

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(KEY_OF_GROUP, nameOfGroup);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nameOfGroup = savedInstanceState.getString(KEY_OF_GROUP);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (nameOfGroup == null) nameOfGroup = CATEGORY_MAIN;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitializeDatabaseByCommonObject l = new InitializeDatabaseByCommonObject(MainActivity.this, nameOfGroup);
        l.checkThisGroup();
        FeedsDatabase dbHelper = FeedsDatabase.getInstance(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        MapDatabase mapDbHelper = MapDatabase.getInstance(this);
        SQLiteDatabase sqLiteDatabaseMap = dbHelper.getWritableDatabase();

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
        FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fMain = new FragmentMain();
        fBook = new FragmentBookmarks();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        InitializeDatabaseByCommonObject l = new InitializeDatabaseByCommonObject(MainActivity.this, nameOfGroup);
        l.checkThisGroup();
        fMain = new FragmentMain();
        fBook = new FragmentBookmarks();
        android.support.v4.app.FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        TextView toolBarView = (TextView) findViewById(R.id.ToolbarView);
        toolBarView.setTextSize(28);
        switch (id) {
            case R.id.nav_Main: {
                fMain.nameOfCategory = CATEGORY_MAIN;
                nameOfGroup = CATEGORY_MAIN;
                toolBarView.setText("Главные Новости");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_politics: {
                fMain.nameOfCategory = CATEGORY_POLITICS;
                nameOfGroup = CATEGORY_POLITICS;
                toolBarView.setText("Политика");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_society: {
                fMain.nameOfCategory = CATEGORY_SOCIETY;
                nameOfGroup = CATEGORY_SOCIETY;
                toolBarView.setText("Общество");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_bookmarks: {
                fBook.nameOfCategory = CATEGORY_BOOKMARK;
                nameOfGroup = CATEGORY_BOOKMARK;
                toolBarView.setText("Закладки");
                fTrans.replace(R.id.content_main, fBook);
                break;
            }
            case R.id.nav_business: {
                fMain.nameOfCategory = CATEGORY_BUSINESS;
                nameOfGroup = CATEGORY_BUSINESS;
                toolBarView.setText("Экономика");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_world: {
                nameOfGroup = CATEGORY_WORLD;
                fMain.nameOfCategory = CATEGORY_WORLD;
                toolBarView.setText("Новости мира");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_sport: {
                nameOfGroup = CATEGORY_SPORT;
                fMain.nameOfCategory = CATEGORY_SPORT;
                toolBarView.setText("Спорт");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_incident: {
                fMain.nameOfCategory = CATEGORY_INCIDENT;
                nameOfGroup = CATEGORY_INCIDENT;
                toolBarView.setText("Происшествия");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_culture: {
                fMain.nameOfCategory = CATEGORY_CULTURE;
                nameOfGroup = CATEGORY_CULTURE;
                toolBarView.setText("Культура");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_science: {
                fMain.nameOfCategory = CATEGORY_SCIENCE;
                nameOfGroup = CATEGORY_SCIENCE;
                toolBarView.setText("Наука");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_computers: {
                nameOfGroup = CATEGORY_COMPUTERS;
                fMain.nameOfCategory = CATEGORY_COMPUTERS;
                toolBarView.setText("Компьютерные технологии");
                toolBarView.setTextSize(20);
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
            case R.id.nav_auto: {
                fMain.nameOfCategory = CATEGORY_AUTO;
                nameOfGroup = CATEGORY_AUTO;
                toolBarView.setText("Авто");
                fTrans.replace(R.id.content_main, fMain);
                break;
            }
        }

        fTrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fMain = new FragmentMain();
        android.support.v4.app.FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (nameOfGroup.equals(CATEGORY_BOOKMARK)) {
            nameOfGroup = CATEGORY_MAIN;
            TextView toolBarView = (TextView) findViewById(R.id.ToolbarView);
            toolBarView.setText("Главные Новости");
        }
        fMain.nameOfCategory = nameOfGroup;
        fTrans.replace(R.id.content_main, fMain);
        fTrans.commit();
    }

}
