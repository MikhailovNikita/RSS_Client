package ifmo.rain.mikhailov.rss_client;

import android.app.Service;
import android.content.Intent;
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

        timer.schedule(new DownloadTimerTask(), 0, 300_000);
    }

    public class DownloadTimerTask extends TimerTask{
        @Override
        public void run() {
            AsyncRSSLoader asyncRSSLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
                @Override
                public void processFinish(ArrayList<RSSItem> list) {
                    Log.d("TAG", list.get(1).toString());
                }
            });


            asyncRSSLoader.execute("https://news.yandex.ru/sport.rss");

        }
    }
}
