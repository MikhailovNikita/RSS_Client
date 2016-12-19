package ifmo.rain.mikhailov.rss_client.settings;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ifmo.rain.mikhailov.rss_client.MainActivity;
import ifmo.rain.mikhailov.rss_client.R;
import ifmo.rain.mikhailov.rss_client.RSSItem;

public class SettingActivityForRss extends Activity {
    final String local = "nameOfGroup";
    public String name;
    public static ChanelRss selectedRss = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name = null;
            } else {
                name = extras.getString(local);
            }
        } else {
            name = (String) savedInstanceState.getSerializable(local);
        }
        setContentView(R.layout.activity_setting_for_rss);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("ifmo.rain.mikhailov.settings.AddRssChanel");
                //intent.putExtra(local, nameOfGroup);
                startActivity(intent);
            }
        });
        final List<ChanelRss> ListRss = new ArrayList<>();
        ListRss.add(new ChanelRss("NEWS","ket"));
        ListRss.add(new ChanelRss("NEWS1","ket"));
        ListRss.add(new ChanelRss("NEWS2","ket"));
        ListRss.add(new ChanelRss("NEWS3","ket"));
        ListView rssListView = (ListView) findViewById(R.id.rssSettingsView);
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRss = ListRss.get(index);
                Intent intent = new Intent("ifmo.rain.mikhailov.settings.EditRssChanel");
                intent.putExtra("nameOfRss", selectedRss.rssName);
                startActivity(intent);
            }
        });



        ArrayAdapter<ChanelRss> adapter = new ArrayAdapter<ChanelRss>(this, R.layout.list_item, ListRss);
        rssListView.setAdapter(adapter);
    }

}
