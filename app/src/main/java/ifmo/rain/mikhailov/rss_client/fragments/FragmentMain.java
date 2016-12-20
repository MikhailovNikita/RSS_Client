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
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ifmo.rain.mikhailov.rss_client.AsyncRSSLoader;
import ifmo.rain.mikhailov.rss_client.MapDatabase;
import ifmo.rain.mikhailov.rss_client.R;
import ifmo.rain.mikhailov.rss_client.RSSItem;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragmnent_main, container, false);
        MapDatabase database = MapDatabase.getInstance(this.getContext());
        SQLiteDatabase db = database.getReadableDatabase();
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        List<Pair<String, String>> pairsOfRss = new ArrayList<>();
        try {
            pairsOfRss = database.get(db, nameOfCategory);
            if (pairsOfRss.size() == 0){
                db = database.getWritableDatabase();
                database.put(db, nameOfCategory, "https://news.yandex.ru/index.rss", "Yandex News");
                pairsOfRss.add(new Pair("https://news.yandex.ru/index.rss", "Yandex News"));
                db = database.getReadableDatabase();
            }
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


        newItems.add(new RSSItem("title","description GIGALUL", new Date(), "link"));
        newItems.add(new RSSItem("title","description MEGALUL", new Date(), "link"));

        AsyncRSSLoader asyncLoader = new AsyncRSSLoader(new AsyncRSSLoader.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<RSSItem> list) {
                Log.d("GACHI", list.size() + " ");
                rssItems.clear();
                rssItems.addAll(list);

                aa.notifyDataSetChanged();
            }
        });


        //TODO: add progress bar
        asyncLoader.execute(feedUrl);

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
