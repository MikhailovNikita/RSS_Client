package ifmo.rain.mikhailov.rss_client.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import ifmo.rain.mikhailov.rss_client.FeedsDatabase;
import ifmo.rain.mikhailov.rss_client.R;
import ifmo.rain.mikhailov.rss_client.RSSItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBookmarks.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBookmarks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBookmarks extends Fragment {
    public static RSSItem selectedRssItem = null;
    String feedUrl;
    ListView rssListView = null;
    ArrayList<RSSItem> rssItems = new ArrayList<>();
    public String nameOfCategory;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentBookmarks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBookmarks.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBookmarks newInstance(String param1, String param2) {
        FragmentBookmarks fragment = new FragmentBookmarks();
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
        View view = inflater.inflate(R.layout.fragment_fragmen_bookmarks, container, false);
        FeedsDatabase databaseOfFeed = FeedsDatabase.getInstance(this.getContext());
        SQLiteDatabase db = databaseOfFeed.getReadableDatabase();
        try {
            rssItems = databaseOfFeed.get(db, "bookmark");
            Log.d("Catch BD", String.valueOf(rssItems.size()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            rssItems = new ArrayList<>();
            //rssItems.add(new RSSItem("0", "0", new Date(), "0"));
        }
        rssListView = (ListView) view.findViewById(R.id.rssListViewOfBookMark);
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);

                Intent intent = new Intent(
                        "ifmo.rain.mikhailov.displayRssItem");
                intent.putExtra("nameOfFragment", "bookmark");
                startActivity(intent);
            }
        });
        ArrayAdapter<RSSItem> aa = new ArrayAdapter<>(view.getContext(), R.layout.list_item, rssItems);;
        rssListView.setAdapter(aa);
        return view;
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
