package ifmo.rain.mikhailov.rss_client.settings;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;

import ifmo.rain.mikhailov.rss_client.FeedsDatabase;
import ifmo.rain.mikhailov.rss_client.MapDatabase;
import ifmo.rain.mikhailov.rss_client.R;
import ifmo.rain.mikhailov.rss_client.RSSItemDisplayer;

public class EditRssChanel extends Activity {
    String nameOfGroup;
    String linkToRss;
    String nameOfRss;
    MapDatabase database;
    SQLiteDatabase db;
    EditText editTRssName;
    EditText editTRss;

    private static final String KEY_OF_GROUP = "KEY_OF_GROUP";
    private static final String KEY_OF_RSS = "KEY_OF_RSS";
    private static final String KEY_OF_NAME = "KEY_OF_NAME";

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(KEY_OF_GROUP, nameOfGroup);
        saveInstanceState.putString(KEY_OF_RSS, linkToRss);
        saveInstanceState.putString(KEY_OF_NAME, nameOfRss);
        super.onSaveInstanceState(saveInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = MapDatabase.getInstance(this);
        db = database.getWritableDatabase();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nameOfRss = null;
                nameOfGroup = null;
                linkToRss =null;

            } else {
                nameOfRss = extras.getString("nameOfRss");
                linkToRss = extras.getString("linkToRss");
                nameOfGroup = extras.getString("nameOfGroup");
            }
        } else {
            nameOfGroup = savedInstanceState.getString(KEY_OF_GROUP);
            nameOfRss = savedInstanceState.getString(KEY_OF_NAME);
            linkToRss = savedInstanceState.getString(KEY_OF_RSS);
            if (nameOfGroup == null) {
                nameOfRss = (String) savedInstanceState.getSerializable("nameOfRss");
                linkToRss = (String) savedInstanceState.getSerializable("linkToRss");
                nameOfGroup = (String) savedInstanceState.getSerializable("nameOfGroup");
            }
        }
        setContentView(R.layout.activity_edit_rss_chanel);
        FloatingActionButton fab5 = (FloatingActionButton) findViewById(R.id.fab5);
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = database.getReadableDatabase();
                database.delete(db, linkToRss);
                Toast.makeText(EditRssChanel.this, "Chanel deleted", Toast.LENGTH_SHORT).show();
            }
        });

        editTRssName = (EditText)findViewById(R.id.editRssName);
        editTRss = (EditText)findViewById(R.id.editRss);
        Button button = (Button) findViewById(R.id.endOfEdit);
        editTRssName.setText(nameOfRss);
        editTRss.setText(linkToRss);
    }

    public void onMyButtonClick(View view)
    {
        database.delete(db, linkToRss);
        database.put(db, nameOfGroup, editTRss.getText().toString(), editTRssName.getText().toString());
    }


}
