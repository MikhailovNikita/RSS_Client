package ifmo.rain.mikhailov.rss_client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;

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
        TextView titleTv = (TextView) findViewById(R.id.titleTextView);
        TextView contentTv = (TextView) findViewById(R.id.contentTextView);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
        String title = "\n" + selectedRssItem.getTitle() + "  ( "
                + sdf.format(selectedRssItem.getPubDate()) + " )\n\n";

        String content = selectedRssItem.getDescription() + "\n"
                + selectedRssItem.getLink();


        titleTv.setText(title);
        contentTv.setText(content);
    }
}
