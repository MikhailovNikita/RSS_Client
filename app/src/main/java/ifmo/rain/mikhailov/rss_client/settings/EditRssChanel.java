package ifmo.rain.mikhailov.rss_client.settings;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;

import ifmo.rain.mikhailov.rss_client.MapDatabase;
import ifmo.rain.mikhailov.rss_client.R;

public class EditRssChanel extends AppCompatActivity {
    String nameOfGroup;
    String linkToRss;
    MapDatabase database;
    SQLiteDatabase db;
    EditText editTRssName;
    EditText editTRss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String nameOfRss;
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
            nameOfRss = (String) savedInstanceState.getSerializable("nameOfRss");
            linkToRss = (String) savedInstanceState.getSerializable("linkToRss");
            nameOfGroup = (String) savedInstanceState.getSerializable("nameOfGroup");
        }
        setContentView(R.layout.activity_edit_rss_chanel);
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
        return;
    }


}
