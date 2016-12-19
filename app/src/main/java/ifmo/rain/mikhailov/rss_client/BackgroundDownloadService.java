package ifmo.rain.mikhailov.rss_client;

import android.app.Service;
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
    final FeedsDatabase dbHelper = new FeedsDatabase(this);
    final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


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
                        items.addAll(list);

                        //впихнуть в дб
                        //хз стоит ли получать бд каждые пять минут, мб стоит один раз на время жизни сервиса
                    }
                });
                asyncRSSLoader.execute(link);
            }


        }
    }
}
