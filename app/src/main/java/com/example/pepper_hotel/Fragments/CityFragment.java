package com.example.pepper_hotel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.example.pepper_hotel.Adapter.CityAdapter;
import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.Model.CityModel;
import com.example.pepper_hotel.NetworkUtils;
import com.example.pepper_hotel.R;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends Fragment implements ListItemClickListener {

    RecyclerView cityGridView;
    Button home;
    public ArrayList<CityModel> cities = new ArrayList<>();
    public Phrase[] cityString;
    CityAdapter cityAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFragment newInstance(String param1, String param2) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
//        fragment.new CityAsyncTask().execute(NetworkUtils.CITY_URL);
        MainActivity ma = new MainActivity();

        fragment.new CityAsyncTask().execute(param2+"cities");
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
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        Context context = getContext();
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        cityGridView = view.findViewById(R.id.city_grid);
        cityGridView.setLayoutManager(new GridLayoutManager(context, 4));
        cityAdapter = new CityAdapter(cities, this);
        cityGridView.setAdapter(cityAdapter);
//        home = view.findViewById(R.id.home_btn);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity ma = (MainActivity) getActivity();
//                ma.GoHomeFragment();
//            }
//        });
        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.placeFragment.GetPlaceByCity(clickedItemIndex);
        mainActivity.showPlaceFragment();
    }

    public class CityAsyncTask extends AsyncTask<String, Void, JSONArray>
    {

        @Override
        protected JSONArray doInBackground(String... urls) {
            String url = urls[0];
            JSONArray result = null;
            try {
                Log.e("newUrl: ", url);
                result = NetworkUtils.getArrayResponse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (result!=null && !result.equals(""))
            {
                   cities =  CityModel.fromJson(result);
                   cityString = new Phrase[cities.size()];
                   for (int i = 0; i<cityString.length; i++)
                   {
                        cityString[i] = new Phrase(cities.get(i).getCityName());
                   }
            }else
                Log.e("cityFragment: ", "there is some problem in postExecute");
        }
    }

}