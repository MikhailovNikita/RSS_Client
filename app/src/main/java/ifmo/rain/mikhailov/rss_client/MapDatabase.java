package ifmo.rain.mikhailov.rss_client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static ifmo.rain.mikhailov.rss_client.MapDatabaseContract.RSS_CATEGORY;
import static ifmo.rain.mikhailov.rss_client.MapDatabaseContract.RSS_LINK;
import static ifmo.rain.mikhailov.rss_client.MapDatabaseContract.TABLE_NAME;


/**
 * Created by Михайлов Никита on 20.12.2016.
 * RSS_Client
 */

public class MapDatabase extends SQLiteOpenHelper {

    private volatile static MapDatabase instance;

    private MapDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public static MapDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (FeedsDatabase.class) {
                if (instance == null) {
                    instance = new MapDatabase(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createDatabase(SQLiteDatabase sqLiteDatabase) {
        Log.d("DATABASE", "Database creation");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RSS_CATEGORY + " TEXT, " + RSS_LINK + " TEXT)");
    }

    public List<String> get(SQLiteDatabase sqLiteDatabase, String category) throws FileNotFoundException{
        List<String> response = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{RSS_LINK}, RSS_CATEGORY + " = ?",
                    new String[]{category}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                while (true) {
                    response.add(cursor.getString(0));

                    if (!cursor.moveToNext()) break;
                }


            }

            return response;

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        throw new FileNotFoundException();
    }

    public void put(SQLiteDatabase sqLiteDatabase, Pair<String, String> pair) {
        ContentValues cv = new ContentValues();
        cv.put(RSS_CATEGORY, pair.first);
        cv.put(RSS_LINK, pair.second);

        try {
            sqLiteDatabase.insert(TABLE_NAME, null, cv);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }


}