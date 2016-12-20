package ifmo.rain.mikhailov.rss_client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ifmo.rain.mikhailov.rss_client.fragments.FragmentMain;

/**
 * Created by Михайлов Никита on 17.12.2016.
 * RSS_Client
 */

public class RSSItemDisplayer extends Activity {
    private String link;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_displayer);

        RSSItem selectedRssItem = FragmentMain.selectedRssItem;

        TextView titleView = (TextView) findViewById(R.id.titleTextView);
        TextView contentView = (TextView) findViewById(R.id.contentTextView);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // --TODO ADD TO BOOKBARKS
                FloatingActionButton v = (FloatingActionButton) view;
                Toast.makeText(RSSItemDisplayer.this, "Запись добавлена в закладки",Toast.LENGTH_SHORT).show();
            }
        });
        Button button = (Button)findViewById(R.id.go_by_link);
        link= selectedRssItem.getLink();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM 'at' HH:mm", Locale.ENGLISH);
        String title = "\n" + selectedRssItem.getTitle() + " \n\n";

        String content = selectedRssItem.getDescription() + " \n\n"
                + sdf.format(selectedRssItem.getPubDate()) + "\n";




        titleView.setText(title);
        contentView.setText(content);
    }
    public void go_by_link(View view)
    {


        Intent browserIntent = new
                Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }


}
