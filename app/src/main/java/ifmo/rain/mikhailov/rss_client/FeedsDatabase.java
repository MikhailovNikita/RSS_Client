package ifmo.rain.mikhailov.rss_client;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import static ifmo.rain.mikhailov.rss_client.DatabaseContract.RSS_CATEGORY;
import static ifmo.rain.mikhailov.rss_client.DatabaseContract.RSS_DESCRIPTION;
import static ifmo.rain.mikhailov.rss_client.DatabaseContract.RSS_NEWS_LINK;
import static ifmo.rain.mikhailov.rss_client.DatabaseContract.RSS_PUB_DATE;
import static ifmo.rain.mikhailov.rss_client.DatabaseContract.RSS_SOURCE_LINK;
import static ifmo.rain.mikhailov.rss_client.DatabaseContract.RSS_TITLE;
import static ifmo.rain.mikhailov.rss_client.DatabaseContract.TABLE_NAME;

/**
 * Created by Михайлов Никита on 19.12.2016.
 * RSS_Client
 */
//title link description date
public class FeedsDatabase extends SQLiteOpenHelper {
    Context context;

    public FeedsDatabase(Context context) {
        super(context, "RSS_FEEDS_DATABASE", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DATABASE", "Database creation");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RSS_CATEGORY + " TEXT, " + RSS_SOURCE_LINK + " TEXT, " + RSS_TITLE + " TEXT, "
                + RSS_DESCRIPTION + " TEXT, " + RSS_PUB_DATE + " TEXT, " + RSS_NEWS_LINK + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //empty for now
    }

    public void put(SQLiteDatabase sqLiteDatabase, @NonNull RSSItem item, String sourceFeed, String category){
        ContentValues cv = new ContentValues();
        cv.put(RSS_TITLE, item.getTitle());
        cv.put(RSS_NEWS_LINK, item.getLink());
        cv.put(RSS_DESCRIPTION, item.getDescription());
        cv.put(RSS_PUB_DATE, item.getPubDate().toString());
        cv.put(RSS_SOURCE_LINK, sourceFeed);
        cv.put(RSS_CATEGORY, category);


        sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    public void get(SQLiteDatabase sqLiteDatabase){

    }
}
