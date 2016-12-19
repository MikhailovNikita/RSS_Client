package ifmo.rain.mikhailov.rss_client;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Михайлов Никита on 19.12.2016.
 * RSS_Client
 */
//title link description date
public class FeedsDatabase extends SQLiteOpenHelper {
    private static final ArrayList<String> tableNames = new ArrayList<>();
    private final String RSS_LINK = "rss_feed_link";
    private final String DESCRIPTION = "rss_description";
    private final String PUB_DATE = "rss_pub_date";
    private final String RSS_TITLE = "rss_title";

    public FeedsDatabase(Context context) {
        super(context, "RSS_FEEDS_DATABASE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DATABASE", "Database creation");

        for(String feed : tableNames){
            sqLiteDatabase.execSQL("CREATE TABLE " + feed + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RSS_TITLE + " TEXT, " + RSS_LINK + " TEXT, " + DESCRIPTION + " TEXT, " + PUB_DATE + " TEXT)");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //empty for now
    }

    public void addNewFeed(String feed, SQLiteDatabase sqLiteDatabase){
        tableNames.add(feed);
        sqLiteDatabase.execSQL("CREATE TABLE " + feed + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "
        + RSS_TITLE + " TEXT, " + RSS_LINK + " TEXT, " + DESCRIPTION + " TEXT, " + PUB_DATE + " TEXT)");
    }
}
