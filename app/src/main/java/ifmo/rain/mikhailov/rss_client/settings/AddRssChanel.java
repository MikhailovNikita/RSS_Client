package ifmo.rain.mikhailov.rss_client.settings;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ifmo.rain.mikhailov.rss_client.R;

public class AddRssChanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rss_chanel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_add_rss_chanel);
        EditText editTRssName = (EditText)findViewById(R.id.editRssName2);
        EditText editTRss = (EditText)findViewById(R.id.editRss2);
        Button button = (Button) findViewById(R.id.endOfEdit2);
    }

    public void onMyButtonClick2(View view)
    {
        // --TODO save changes of Rss and RssName into BD with nameOfRss
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
    }

}
