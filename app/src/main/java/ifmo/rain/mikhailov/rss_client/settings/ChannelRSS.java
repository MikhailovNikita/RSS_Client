package ifmo.rain.mikhailov.rss_client.settings;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by user on 19.12.2016.
 */

public class ChannelRSS {
    String rssName;
    String linkToRss;
    ChannelRSS(String rssName, String linkToRss){
        this.linkToRss = linkToRss;
        this.rssName = rssName;
    }
    @Override
    public String toString() {
        return rssName;
    }
}
