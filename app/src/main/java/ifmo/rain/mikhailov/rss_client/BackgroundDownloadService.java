package ifmo.rain.mikhailov.rss_client;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Михайлов Никита on 19.12.2016.
 * RSS_Client
 */

public class BackgroundDownloadService extends Service {
    final ArrayList<RSSItem> items = new ArrayList<>();
    final FeedsDatabase dbHelper = FeedsDatabase.getInstance(this);
    final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    final MapDatabase mapDBHelper = MapDatabase.getInstance(this);
    final SQLiteDatabase sqLiteDatabaseMap = mapDBHelper.getReadableDatabase();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE", "Service launched");
        doSomething();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doSomething() {
        Timer timer = new Timer();


        List<String> links = new ArrayList<>();


        try {
            links = mapDBHelper.getAll(sqLiteDatabaseMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        timer.schedule(new DownloadTimerTask(links), 0, 20_000);
    }

    public class DownloadTimerTask extends TimerTask {
        ArrayList<String> rssLinks;

        DownloadTimerTask(List<String> list) {
            rssLinks.addAll(list);
        }

        @Override
        public void run() {

            Log.d("TAG", "Run launched");

            for (String link : this.rssLinks) {
                final String curLink = link;

                AsyncRSSLoader asyncRSSLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
                    @Override
                    public void processFinish(ArrayList<RSSItem> list) {
                        Log.d("TAG", list.get(1).toString());

                        for (RSSItem item : list) {
                            Date recordingDate = item.getPubDate();


                            if (recordingDate.before(new Date(mapDBHelper.getDate(sqLiteDatabaseMap, curLink)))) {
                                Log.d("NEWS ADDED", item.getTitle());
                                dbHelper.put(sqLiteDatabase, item, curLink, "CATEGORY NAME");
                            }

                        }
                    }
                });
                asyncRSSLoader.execute(link);
            }
        }
    }
}
