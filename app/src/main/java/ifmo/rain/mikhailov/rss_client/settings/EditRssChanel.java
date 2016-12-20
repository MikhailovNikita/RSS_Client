package ifmo.rain.mikhailov.rss_client.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ifmo.rain.mikhailov.rss_client.R;

public class EditRssChanel extends AppCompatActivity {
    String nameOfRss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nameOfRss = null;
            } else {
                nameOfRss = extras.getString("nameOfRss");
            }
        } else {
            nameOfRss = (String) savedInstanceState.getSerializable("nameOfRss");
        }
        setContentView(R.layout.activity_edit_rss_chanel);
        EditText editTRssName = (EditText)findViewById(R.id.editRssName);
        EditText editTRss = (EditText)findViewById(R.id.editRss);
        Button button = (Button) findViewById(R.id.endOfEdit);

    }

    public void onMyButtonClick(View view)
    {
        // --TODO save changes of Rss and RssName into BD with nameOfRss
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
    }


}
