package ifmo.rain.mikhailov.rss_client;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Михайлов Никита on 18.12.2016.
 * RSS_Client
 */

public class PrefActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: deprecated?
        addPreferencesFromResource(R.xml.pref);
    }
}
