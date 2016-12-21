package ifmo.rain.mikhailov.rss_client.initialize;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ifmo.rain.mikhailov.rss_client.MapDatabase;

/**
 * Created by user on 21.12.2016.
 */

public class InitializeDatabaseByCommonObject {
    Context context;
    String nameOfGroup;
    MapDatabase database;
    public InitializeDatabaseByCommonObject(Context context, String nameOfGroup){
        this.context = context;
        this.nameOfGroup = nameOfGroup;
    }

    public void checkThisGroup(){
        database = MapDatabase.getInstance(this.context);
        SQLiteDatabase db = database.getReadableDatabase();
        List<Pair<String, String>> pairOfRss = new ArrayList<>();
        try {
            pairOfRss = database.get(db, nameOfGroup);
        } catch (FileNotFoundException e){
            pairOfRss.add(new Pair("no one chanel founded", "no one chanel founded"));
        }
        if (pairOfRss.size() == 0){
            tryAdd();
        }else{
            return;
        }
    }

    private void tryAdd(){
        SQLiteDatabase db = database.getReadableDatabase();
        switch (nameOfGroup){
            case ("bookmark"):{
                break;
            }
            case ("main"):{
                database.put(db, nameOfGroup, "https://news.yandex.ru/index.rss", "Yandex");
                database.put(db, nameOfGroup, "https://tjournal.ru/rss", "TJ");
                database.put(db, nameOfGroup, "https://lenta.ru/rss/top7", "Lenta");
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/main.xml", "Коммерсант");
                database.put(db, nameOfGroup, "https://meduza.io/rss/all", "Meduza");
                break;
            }
            case ("politic"):{
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/world", "Lenta");
                database.put(db, nameOfGroup, "https://news.yandex.ru/politics.rss", "Yandex");
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/weekly.xml", "Коммерсант");

                break;
            }
            case ("society"):{
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/style", "Lenta");
                database.put(db, nameOfGroup, "https://news.yandex.ru/society.rss", "Yandex");
                database.put(db, nameOfGroup, "https://www.kommersant.ru/RSS/section-society.xml", "Коммерсант");
                break;
            }
            case ("business"):{
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/weekly.xml", "Коммерсант");
                database.put(db, nameOfGroup, "https://news.yandex.ru/business.rss", "Yandex");
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/economics", "Lenta");
                break;
            }
            case ("world"):{
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/world", "Lenta");
                database.put(db, nameOfGroup, "https://news.yandex.ru/world.rss", "Yandex");
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/section-world.xml", "Коммерсант");
                break;
            }case ("sport"):{
                database.put(db, nameOfGroup, "https://news.yandex.ru/sport.rss", "Yandex");
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/sport", "Lenta");
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/section-sport.xml", "Коммерсант");
                break;
            }case ("incident"):{
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/media", "Lenta");
                database.put(db, nameOfGroup, "https://news.yandex.ru/incident.rss", "Yandex");
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/section-accidents.xml", "Коммерсант");
                break;
            }case ("culture"):{
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/culture", "Lenta");
                database.put(db, nameOfGroup, "https://news.yandex.ru/culture.rss", "Yandex");
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/section-culture.xml", "Коммерсант");
                break;
            }case ("science"):{
                database.put(db, nameOfGroup, "https://lenta.ru/rss/news/science", "Lenta");
                database.put(db, nameOfGroup, "https://news.yandex.ru/science.rss", "Yandex");

                break;
            }case ("computers"):{
                database.put(db, nameOfGroup, "https://news.yandex.ru/computers.rss", "Yandex");
                break;
            }case ("auto"):{
                database.put(db, nameOfGroup, "http://www.kommersant.ru/RSS/auto.xml", "Коммерсант");
                database.put(db, nameOfGroup, "https://news.yandex.ru/auto.rss", "Yandex");
                break;
            }
        }
    }
}
