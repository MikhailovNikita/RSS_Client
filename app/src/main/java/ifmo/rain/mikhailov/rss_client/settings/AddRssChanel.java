package ifmo.rain.mikhailov.rss_client.settings;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ifmo.rain.mikhailov.rss_client.MapDatabase;
import ifmo.rain.mikhailov.rss_client.R;

public class AddRssChanel extends AppCompatActivity {
    EditText editTRssName;
    EditText editTRss;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rss_chanel);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name = null;
            } else {
                name = extras.getString("nameOfGroup");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("nameOfGroup");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_add_rss_chanel);
        editTRssName = (EditText)findViewById(R.id.editRssName2);
        editTRss = (EditText)findViewById(R.id.editRss2);
        Button button = (Button) findViewById(R.id.endOfEdit2);
    }

    public void onMyButtonClick2(View view)
    {
        String linkToRss = editTRss.getText().toString();
        String rssName = editTRssName.getText().toString();
        MapDatabase database = MapDatabase.getInstance(AddRssChanel.this);
        SQLiteDatabase db = database.getWritableDatabase();
        database.put(db, name, linkToRss, rssName);
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
    }
}
