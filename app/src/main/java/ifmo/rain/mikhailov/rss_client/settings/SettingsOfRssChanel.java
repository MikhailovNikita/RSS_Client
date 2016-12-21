package ifmo.rain.mikhailov.rss_client.settings;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ifmo.rain.mikhailov.rss_client.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsOfRssChanel.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsOfRssChanel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsOfRssChanel extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public SettingsOfRssChanel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsOfRssChanel.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsOfRssChanel newInstance(String param1, String param2) {
        SettingsOfRssChanel fragment = new SettingsOfRssChanel();
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
        View view = inflater.inflate(R.layout.settings, container, false);
        final Spinner spinnerMain = (Spinner)view.findViewById(R.id.spinnerForMain);
        final Spinner spinnerPolitic = (Spinner)view.findViewById(R.id.spinnerForPolitic);
        List<ChannelRSS> spinnerForMain = new ArrayList<>();
        spinnerForMain.add(new ChannelRSS("Yandex News", "index.rss"));
        spinnerForMain.add(new ChannelRSS("Шаро Новости", "index.rss"));
        spinnerForMain.add(new ChannelRSS("Dudec SHOK NEWS", "index.rss"));
        List<ChannelRSS> spinnerForPolitic = new ArrayList<>();
        spinnerForPolitic.add(new ChannelRSS("Yandex News", "index.rss"));


        ArrayAdapter<ChannelRSS> adapter = new ArrayAdapter<>(view.getContext(),
                R.layout.setting_spinner_item, spinnerForMain);
        spinnerMain.setAdapter(adapter);
        adapter = new ArrayAdapter<>(view.getContext(),
                R.layout.setting_spinner_item, spinnerForPolitic);
        spinnerPolitic.setAdapter(adapter);



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
