package ifmo.rain.mikhailov.rss_client;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ifmo.rain.mikhailov.rss_client.fragments.FragmentBookmarks;
import ifmo.rain.mikhailov.rss_client.fragments.FragmentMain;

import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_BOOKMARK;
import static ifmo.rain.mikhailov.rss_client.Constants.CATEGORY_MAIN;

/**
 * Created by Михайлов Никита on 17.12.2016.
 * RSS_Client
 */


public class RSSItemDisplayer extends Activity {
    private String link;
    private static final String KEY_OF_TITLE = "KEY_OF_TITLE";
    private static final String KEY_OF_DATA = "KEY_OF_DATA";
    private static final String KEY_OF_DESCRIPTION = "KEY_OF_DESCRIPTION";
    private static final String KEY_OF_LINK = "KEY_OF_LINK";
    RSSItem selectedRssItemloc;

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(KEY_OF_TITLE, selectedRssItemloc.getTitle());
        saveInstanceState.putString(KEY_OF_DATA, selectedRssItemloc.getPubDate().toString());
        saveInstanceState.putString(KEY_OF_DESCRIPTION, selectedRssItemloc.getDescription());
        saveInstanceState.putString(KEY_OF_LINK, selectedRssItemloc.getLink());
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String title = savedInstanceState.getString(KEY_OF_TITLE);
        String date = savedInstanceState.getString(KEY_OF_DATA);
        String description = savedInstanceState.getString(KEY_OF_DESCRIPTION);
        String link = savedInstanceState.getString(KEY_OF_LINK);
        selectedRssItemloc = new RSSItem(title, description, new Date(date), link);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_displayer);
        RSSItem selectedRssItemLocal;
        String name;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
            } else {
                name = extras.getString("nameOfFragment");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("nameOfFragment");
        }
        if (name != null) {
            if (name.equals(CATEGORY_MAIN)) {
                selectedRssItemLocal = FragmentMain.selectedRssItem;
            } else {
                selectedRssItemLocal = FragmentBookmarks.selectedRssItem;
            }
        } else {
            selectedRssItemLocal = null;
        }
        if (selectedRssItemloc != null) selectedRssItemLocal = selectedRssItemloc;
        final RSSItem selectedRssItem = selectedRssItemLocal;
        selectedRssItemloc = selectedRssItem;
        TextView titleView = (TextView) findViewById(R.id.titleTextView);
        TextView contentView = (TextView) findViewById(R.id.contentTextView);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton v = (FloatingActionButton) view;
                FeedsDatabase databaseOfFeed = FeedsDatabase.getInstance(RSSItemDisplayer.this);
                SQLiteDatabase db = databaseOfFeed.getWritableDatabase();
                databaseOfFeed.put(db, selectedRssItem, CATEGORY_BOOKMARK, CATEGORY_BOOKMARK);
                Toast.makeText(RSSItemDisplayer.this, "Запись добавлена в закладки", Toast.LENGTH_SHORT).show();
            }
        });
        Button button = (Button) findViewById(R.id.go_by_link);
        link = selectedRssItem.getLink();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM 'at' HH:mm", Locale.ENGLISH);
        String title = "\n" + selectedRssItem.getTitle() + " \n\n";

        String content = selectedRssItem.getDescription() + " \n\n"
                + sdf.format(selectedRssItem.getPubDate()) + "\n";


        titleView.setText(title);
        contentView.setText(content);
    }

    public void go_by_link(View view) {
        Intent browserIntent = new
                Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }


}
