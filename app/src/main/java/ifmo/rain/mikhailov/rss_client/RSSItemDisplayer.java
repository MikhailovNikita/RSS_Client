package ifmo.rain.mikhailov.rss_client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Михайлов Никита on 17.12.2016.
 * RSS_Client
 */

public class RSSItemDisplayer extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_displayer);

        RSSItem selectedRssItem = RSSActivity.selectedRssItem;
        //Bundle extras = getIntent().getExtras();

        TextView titleView = (TextView) findViewById(R.id.titleTextView);
        TextView contentView = (TextView) findViewById(R.id.contentTextView);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM 'at' HH:mm", Locale.ENGLISH);
        String title = "\n" + selectedRssItem.getTitle() + " "
                + sdf.format(selectedRssItem.getPubDate()) + " \n\n";

        String content = selectedRssItem.getDescription() + "\n"
                + selectedRssItem.getLink();


        titleView.setText(title);
        contentView.setText(content);
    }
}
