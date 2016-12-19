package ifmo.rain.mikhailov.rss_client;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
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
    private final String RSS_LINK = "rss_feed_link";
    private final String DESCRIPTION = "rss_description";
    private final String PUB_DATE = "rss_pub_date";
    private final String RSS_TITLE = "rss_title";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doSomething();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doSomething(){
        Timer timer = new Timer();

        //где-то взять все линки, наверное из бд специально для них, а пока
        ArrayList<String> links = new ArrayList<>();
        links.add("https://news.yandex.ru/law.rss");
        links.add("https://news.yandex.ru/sport.rss");
        timer.schedule(new DownloadTimerTask(links), 0, 20_000);
    }

    public class DownloadTimerTask extends TimerTask{
        ArrayList<String> rssLinks;

        DownloadTimerTask(ArrayList<String> list) {
            rssLinks.addAll(list);
        }

        @Override
        public void run() {

            Log.d("TAG", "Run launched");
            for(String link : this.rssLinks){

                AsyncRSSLoader asyncRSSLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
                    @Override
                    public void processFinish(ArrayList<RSSItem> list) {
                        Log.d("TAG", list.get(1).toString());


                        //пока буду просто пихать, и баяны, и классеку(нет)

                        for(RSSItem item : list){
                            ContentValues cv = new ContentValues();
                            cv.put(RSS_LINK, "https://news.yandex.ru/law.rss"); //пока так
                            cv.put(DESCRIPTION, item.getDescription());
                            cv.put(PUB_DATE, item.getPubDate().toString());
                            cv.put(RSS_TITLE, item.getTitle());
                            sqLiteDatabase.insert("fucking_news", null, cv);
                        }
                    }
                });
                asyncRSSLoader.execute(link);
            }
        }
    }
}
