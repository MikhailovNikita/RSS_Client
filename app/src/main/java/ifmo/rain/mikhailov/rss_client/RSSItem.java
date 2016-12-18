package ifmo.rain.mikhailov.rss_client;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Михайлов Никита on 17.12.2016.
 * RSS_Client
 */

public class RSSItem {
    private String title;
    private String description;
    private Date pubDate;
    private String link;

    public RSSItem(String title, String description, Date pubDate, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }

    String getTitle() {
        return this.title;
    }

    String getLink() {
        return this.link;
    }

    String getDescription() {
        return this.description;
    }

    Date getPubDate() {
        return this.pubDate;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM 'at' HH:mm", Locale.ENGLISH);

        String result = getTitle() + "  \n" + sdf.format(this.getPubDate()) + " ";
        return result;
    }

}
