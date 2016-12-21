package ifmo.rain.mikhailov.rss_client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
    final MapDatabase mapDBHelper = MapDatabase.getInstance(this);
    final SQLiteDatabase sqLiteDatabaseMap = mapDBHelper.getWritableDatabase();
    final FeedsDatabase dbHelper = FeedsDatabase.getInstance(this);
    final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    final static int NOTIF_CONST = 42;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE", "Service launched");
        startLoading();
        return Service.START_STICKY;
    }

    private void startLoading() {
        Timer timer = new Timer();


        List<String> links = new ArrayList<>();


        try {
            links = mapDBHelper.getAll(sqLiteDatabaseMap);
            Log.d("TAG", String.valueOf(links.size()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        timer.schedule(new DownloadTimerTask(links), 0, 20_000);
    }

    public class DownloadTimerTask extends TimerTask {
        List<String> rssLinks;

        DownloadTimerTask(List<String> list) {
            rssLinks = list;
        }

        @Override
        public void run() {

            Log.d("TAG", "Run launched");

            for (String link : this.rssLinks) {
                final String curLink = link;
                Log.d("CURRENT LINK", curLink);
                AsyncRSSLoader asyncRSSLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
                    @Override
                    public void processFinish(ArrayList<RSSItem> list) {
                        Date lastPubDate = new Date(mapDBHelper.getDate(sqLiteDatabaseMap, curLink));
                        Date newDate = lastPubDate;
                        boolean isThereAnyNews = false;
                        String newsExample = null;
                        for (RSSItem item : list) {
                            Date recordingDate = item.getPubDate();

                            Log.d("AsLoader", "==============================================");
                            Log.d("AsLoader", recordingDate.toString());
                            Log.d("AsLoader", mapDBHelper.getDate(sqLiteDatabaseMap, curLink));


                             if (recordingDate.after(lastPubDate)) {
                                Log.d("NEWS ADDED", item.getTitle());
                                if(recordingDate.after(newDate)){
                                    newDate = recordingDate;
                                    isThereAnyNews = true;
                                    newsExample = item.getTitle();
                                }
                                dbHelper.put(sqLiteDatabase, item, curLink, "CATEGORY NAME");
                            }

                        }

                        //I WILL NEVER HARDCODE STRINGS AGAIN
                        //I WILL NEVER HARDCODE STRINGS AGAIN
                        //.....
                        //I WILL NEVER HARDCODE STRINGS AGAIN
                        if(mapDBHelper.getCategoryByLink(sqLiteDatabaseMap, curLink).equals("main")
                                && isThereAnyNews){
                           showNotification(newsExample,mapDBHelper.getChannelNameByLink(sqLiteDatabaseMap,curLink));
                        }
                        mapDBHelper.updateDate(sqLiteDatabaseMap, newDate.toString(), curLink);
                    }
                });
                asyncRSSLoader.execute(link);
            }

        }
    }

    private void showNotification(String newsTitle, String channelName) {
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Resources res = context.getResources();


        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_menu_gallery))
                .setTicker("RSS Update")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(channelName)
                .setContentText(newsTitle);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIF_CONST, notification);
    }
}
