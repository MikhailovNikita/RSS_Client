package ifmo.rain.mikhailov.rss_client.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ifmo.rain.mikhailov.rss_client.AsyncRSSLoader;
import ifmo.rain.mikhailov.rss_client.FeedsDatabase;
import ifmo.rain.mikhailov.rss_client.MapDatabase;
import ifmo.rain.mikhailov.rss_client.R;
import ifmo.rain.mikhailov.rss_client.RSSItem;
import ifmo.rain.mikhailov.rss_client.initialize.InitializeDatabaseByCommonObject;

/**
 * Created by user on 27.12.2016.
 */

class AssyncUpdateFragments extends AsyncTask<Void, Void, Void> {
    private String nameOfCategory;
    private Context context;
    private String feedUrl;
    private List<String> rssName;
    private List<Pair<String, String>> pairsOfRss;
    private List<String> rssLink;
    private RecyclerView.Adapter mAdapter;
    public static RSSItem selectedRssItem = null;
    ArrayAdapter<RSSItem> arrayAdapter;
    RecyclerView rssListView = null;
    private ArrayList<RSSItem> rssItems = new ArrayList<>();
    private ArrayList<ArrayList<RSSItem>> fullRssItems = new ArrayList<>();
    View view;
    AssyncUpdateFragments(String nameOfCategory, Context context, View view){
        this.nameOfCategory = nameOfCategory;
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        InitializeDatabaseByCommonObject l = new InitializeDatabaseByCommonObject(context, nameOfCategory);
        l.checkThisGroup();
        MapDatabase database = MapDatabase.getInstance(context);
        SQLiteDatabase db = database.getReadableDatabase();

        pairsOfRss = new ArrayList<>();
        try {
            pairsOfRss = database.get(db, nameOfCategory);
        } catch (FileNotFoundException e){
            pairsOfRss.add(new Pair<>("no one chanel founded", "no one chanel founded"));
        }
        rssName = new ArrayList<>();
        rssLink = new ArrayList<>();
        for (int i = 0; i < pairsOfRss.size(); ++i){
            rssLink.add(pairsOfRss.get(i).first);
            rssName.add(pairsOfRss.get(i).second);
            feedUrl = rssLink.get(i);
            refreshRSSList();
            fullRssItems.add(rssItems);
        }
        rssItems = fullRssItems.get(0);
        return null;
    }

     private void ViewList(View view){
         rssListView = (RecyclerView) view.findViewById(R.id.rssListView);
         LinearLayoutManager llm = new LinearLayoutManager(context);
         rssListView.setLayoutManager(llm);
         mAdapter = new RecyclerAdapter(rssItems);
         rssListView.setAdapter(mAdapter);
    }




    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(context, R.layout.setting_spinner_item, rssName);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                feedUrl = rssLink.get(selectedItemPosition);
                for (int i = 0; i < rssLink.size(); ++i){
                    if (feedUrl.equals(rssLink.get(i))){
                        rssItems = fullRssItems.get(i);
                        ViewList(view);
                        break;
                    }
                }
                ViewList(view);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Log.d("BUTTON", feedUrl);
        Log.d("BUTTON", "Let's load some news");
        rssListView = (RecyclerView) view.findViewById(R.id.rssListView);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        rssListView.setItemAnimator(itemAnimator);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rssListView.setLayoutManager(llm);
        mAdapter = new RecyclerAdapter(rssItems);
        rssListView.setAdapter(mAdapter);
        rssListView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, rssListView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int index) {
                        selectedRssItem = rssItems.get(index);
                        FragmentMain.selectedRssItem = selectedRssItem;
                        Intent intent = new Intent(
                                "ifmo.rain.mikhailov.displayRssItem");
                        intent.putExtra("nameOfFragment", "main");
                        context.startActivity(intent);
                    }
                    @Override public void onLongItemClick(View view, int index) {

                    }
                })
        );

        spinner.setVisibility(spinner.VISIBLE);
        rssListView.setVisibility(rssListView.VISIBLE);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }


    private void refreshRSSList() {

        final ArrayList<RSSItem> newItems = new ArrayList<>();

        FeedsDatabase databaseOfFeed = FeedsDatabase.getInstance(context);
        SQLiteDatabase db = databaseOfFeed.getReadableDatabase();
        try {
            rssItems = databaseOfFeed.get(db, feedUrl);
            Log.d("Catch BD", String.valueOf(rssItems.size()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            rssItems = new ArrayList<>();
        }
       /* if (rssItems.size()==0) {
            AsyncRSSLoader asyncLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
                @Override
                public void processFinish(ArrayList<RSSItem> list) {
                    Log.d("GACHI", list.size() + " ");
                    rssItems.clear();
                    rssItems.addAll(list);
                    FeedsDatabase databaseOfFeed = FeedsDatabase.getInstance(context);
                    SQLiteDatabase db = databaseOfFeed.getReadableDatabase();
                    for (int i = 0; i < rssItems.size(); ++i) {
                        databaseOfFeed.put(db, rssItems.get(i), feedUrl, nameOfCategory);
                    }
                }
            });
            asyncLoader.execute(feedUrl);
        }*/
        Collections.reverse(rssItems);

    }
}