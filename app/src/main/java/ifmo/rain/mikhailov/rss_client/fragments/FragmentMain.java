package ifmo.rain.mikhailov.rss_client.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ifmo.rain.mikhailov.rss_client.AsyncRSSLoader;
import ifmo.rain.mikhailov.rss_client.FeedsDatabase;
import ifmo.rain.mikhailov.rss_client.MainActivity;
import ifmo.rain.mikhailov.rss_client.MapDatabase;
import ifmo.rain.mikhailov.rss_client.R;
import ifmo.rain.mikhailov.rss_client.RSSItem;
import ifmo.rain.mikhailov.rss_client.initialize.InitializeDatabaseByCommonObject;
import ifmo.rain.mikhailov.rss_client.settings.AddRssChanel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public static RSSItem selectedRssItem = null;
    String feedUrl;
    ListView rssListView = null;
    ArrayList<RSSItem> rssItems = new ArrayList<>();
    ArrayAdapter<RSSItem> aa;
    public String nameOfCategory;
    View view;
    private OnFragmentInteractionListener mListener;

    private static final String KEY_OF_GROUP = "KEY_OF_GROUP";

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(KEY_OF_GROUP, nameOfCategory);
        super.onSaveInstanceState(saveInstanceState);
    }


    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmnentMain.
     */
    // TODO: Rename and change types and number of parameters



    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            nameOfCategory = savedInstanceState.getString(KEY_OF_GROUP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragmnent_main, container, false);
        InitializeDatabaseByCommonObject l = new InitializeDatabaseByCommonObject(FragmentMain.this.getContext(), nameOfCategory);
        l.checkThisGroup();
        MapDatabase database = MapDatabase.getInstance(this.getContext());
        SQLiteDatabase db = database.getReadableDatabase();
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        List<Pair<String, String>> pairsOfRss = new ArrayList<>();
        try {
            pairsOfRss = database.get(db, nameOfCategory);
        } catch (FileNotFoundException e){
            pairsOfRss.add(new Pair("no one chanel founded", "no one chanel founded"));
        }
        List<String> rssName = new ArrayList<>();
        final List<String> rssLink = new ArrayList<>();
        for (int i = 0; i < pairsOfRss.size(); ++i){
            rssLink.add(pairsOfRss.get(i).first);
            rssName.add(pairsOfRss.get(i).second);
        }
        feedUrl = rssLink.get(0);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(view.getContext(), R.layout.setting_spinner_item, rssName);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                feedUrl = rssLink.get(selectedItemPosition);
                refreshRSSList();
                ViewList(view);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        refreshRSSList();
        Log.d("BUTTON", feedUrl);
        Log.d("BUTTON", "Let's load some news");
        final TextView rssURL = (TextView) view.findViewById(R.id.rssURL);
        rssListView = (ListView) view.findViewById(R.id.rssListView);
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);
                Intent intent = new Intent(
                        "ifmo.rain.mikhailov.displayRssItem");
                intent.putExtra("nameOfFragment", "main");
                startActivity(intent);
            }
        });

        ViewList(view);
        return view;
    }
    private void ViewList(View view){
        aa = new ArrayAdapter<RSSItem>(view.getContext(), R.layout.list_item, rssItems);
        rssListView.setAdapter(aa);
    }


    private void refreshRSSList() {

        final ArrayList<RSSItem> newItems = new ArrayList<>();

        FeedsDatabase databaseOfFeed = FeedsDatabase.getInstance(FragmentMain.this.getContext());
        SQLiteDatabase db = databaseOfFeed.getReadableDatabase();
        try {
            rssItems = databaseOfFeed.get(db, feedUrl);
            Log.d("Catch BD", String.valueOf(rssItems.size()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            rssItems = new ArrayList<>();
            //rssItems.add(new RSSItem("0", "0", new Date(), "0"));
        }
        if (rssItems.size()==0) {
            AsyncRSSLoader asyncLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
                @Override
                public void processFinish(ArrayList<RSSItem> list) {
                    Log.d("GACHI", list.size() + " ");
                    rssItems.clear();
                    rssItems.addAll(list);
                    aa.notifyDataSetChanged();
                    FeedsDatabase databaseOfFeed = FeedsDatabase.getInstance(FragmentMain.this.getContext());
                    SQLiteDatabase db = databaseOfFeed.getReadableDatabase();
                    for (int i = 0; i < rssItems.size(); ++i) {
                        databaseOfFeed.put(db, rssItems.get(i), feedUrl, nameOfCategory);
                    }
                }
            });
            asyncLoader.execute(feedUrl);
        }
        Collections.reverse(rssItems);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
