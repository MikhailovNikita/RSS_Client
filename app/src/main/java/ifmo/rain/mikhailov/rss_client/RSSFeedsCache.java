package ifmo.rain.mikhailov.rss_client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михайлов Никита on 18.12.2016.
 * RSS_Client
 */

public class RSSFeedsCache {
    @NonNull
    private final Context context;


    private final RSSFeedDatabaseHandler dbHandler;


    @AnyThread
    public RSSFeedsCache(@NonNull Context context) {
        this.context = context;

        this.dbHandler = new RSSFeedDatabaseHandler(this.context);
    }

    @WorkerThread
    @NonNull
    public List<String> get() throws FileNotFoundException {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(RSSFeedContract.TABLE_NAME,
                new String[]{RSSFeedContract.FEED_LINK},
                null,
                null,
                null,
                null,
                null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                List<String> rssFeeds = new ArrayList<>();

                while (true) {
                    rssFeeds.add(cursor.getString(0));

                    if (!cursor.moveToNext()) break;
                }

                return rssFeeds;

            }

            //TODO: make more helpful exception message
            throw new FileNotFoundException("Something's wrong with database");
        } finally {
            if (cursor != null) cursor.close();

            sqLiteDatabase.close();
        }

    }

    @WorkerThread
    public void put(@NonNull List<String> rssFeeds) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();

        SQLiteStatement statement = sqLiteDatabase.compileStatement("INSERT INTO " + RSSFeedContract.TABLE_NAME
                + " (" + RSSFeedContract.FEED_LINK + ") VALUES (?)");

        sqLiteDatabase.beginTransaction();
        try {
            for (String rssLink : rssFeeds) {
                statement.bindString(1, rssLink);

                statement.executeInsert();
            }

            sqLiteDatabase.setTransactionSuccessful();
        }finally {
            sqLiteDatabase.endTransaction();
        }

        sqLiteDatabase.close();
    }

    private static class RSSFeedDatabaseHandler extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "RSS_feed_list";

        public RSSFeedDatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            createTable(sqLiteDatabase);
        }

        private void createTable(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + RSSFeedContract.TABLE_NAME
                    + " ( " + RSSFeedContract.FEED_LINK + " TEXT" + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            //No upgrades, only one version available
        }
    }


}
