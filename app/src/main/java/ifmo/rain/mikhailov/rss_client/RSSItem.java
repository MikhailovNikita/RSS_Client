package ifmo.rain.mikhailov.rss_client;

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
    public boolean equals(Object obj) {
        if(obj instanceof  RSSItem){
            RSSItem item = (RSSItem) obj;
            return this.title.equals(item.getTitle());
        }

        return false;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM 'в' HH:mm", Locale.getDefault());

        String result = getTitle() + "  \n" + sdf.format(this.getPubDate()) + " ";
        return result;
    }

}
