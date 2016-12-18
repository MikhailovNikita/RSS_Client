package ifmo.rain.mikhailov.rss_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RSSActivity extends AppCompatActivity {

    public static RSSItem selectedRssItem = null;
    String feedUrl;
    ListView rssListView = null;
    ArrayList<RSSItem> rssItems = new ArrayList<>();
    ArrayAdapter<RSSItem> arrayAdapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);


        final TextView rssURL = (TextView) findViewById(R.id.rssURL);
        Button fetchRss = (Button) findViewById(R.id.fetchRss);
        Button settingsButton = (Button) findViewById(R.id.settings);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("ifmo.rain.mikhailov.Settings");
                startActivity(intent);
            }
        });

        fetchRss.setOnClickListener(new View.OnClickListener() {
            //TODO: add progress bar
            @Override
            public void onClick(View v) {
                feedUrl = rssURL.getText().toString();
                Log.d("BUTTON", feedUrl);
                Log.d("BUTTON", "Let's load some news");
                refreshRSSList();

            }
        });


        rssListView = (ListView) findViewById(R.id.rssListView);

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);


                Intent intent = new Intent(
                        "ifmo.rain.mikhailov.displayRssItem");
                startActivity(intent);
            }
        });


        arrayAdapter = new ArrayAdapter<>(RSSActivity.this, R.layout.list_item, rssItems);
        rssListView.setAdapter(arrayAdapter);
    }

    private void refreshRSSList() {

        AsyncRSSLoader asyncLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<RSSItem> list) {
                Log.d("GACHI", list.size() + " ");
                rssItems.clear();
                rssItems.addAll(list);

                arrayAdapter.notifyDataSetChanged();
            }
        });


        //TODO: add progress bar
        asyncLoader.execute(feedUrl);

    }
}
