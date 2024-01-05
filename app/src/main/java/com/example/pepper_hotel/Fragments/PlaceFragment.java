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

import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.example.pepper_hotel.Adapter.PlaceAdapter;
import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.Model.PlaceModel;
import com.example.pepper_hotel.NetworkUtils;
import com.example.pepper_hotel.PlaceDTO;
import com.example.pepper_hotel.R;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceFragment extends Fragment implements ListItemClickListener {

    RecyclerView recyclerView;
    PlaceAdapter placeAdapter;
    public ArrayList<PlaceModel> placeModelArrayList = new ArrayList<>();

    ArrayList<PlaceModel> placeByCity = new ArrayList<>();
    ArrayList<PlaceModel> placeById = new ArrayList<>();

    public Phrase[] placeString;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceFragment newInstance(String param1, String param2) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
//        fragment.new PlaceAsyncTask().execute(NetworkUtils.PLACE_URL);
        fragment.new PlaceAsyncTask().execute(param2+"places");
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
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.place_rc);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        placeAdapter = new PlaceAdapter(placeByCity, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(placeAdapter);
        return view;
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        MainActivity ma = (MainActivity) getActivity();
        ma.GetPlaceById(clickedItemIndex);
        ma.showPlaceDetailFragment();

    }

    public class PlaceAsyncTask extends AsyncTask<String, Void, JSONArray>
    {

        @Override
        protected JSONArray doInBackground(String... urls) {
            String url = urls[0];
            JSONArray result = null;
            try {
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
                placeModelArrayList = PlaceModel.fromJson(result);
                placeString = new Phrase[placeModelArrayList.size()];
                for (int i=0; i<placeString.length;i++)
                {
                    placeString[i] = new Phrase(placeModelArrayList.get(i).getPlaceName());
                }

            }else
                Log.e("placeFragment: ", "there is some problem in postExecute");
        }
    }

    public void GetPlaceByCity(int cityId)
    {
        placeByCity.clear();
        for (int i=0; i<placeModelArrayList.size(); i++)
        {
            if (placeModelArrayList.get(i).getCityId()==cityId)
                placeByCity.add(placeModelArrayList.get(i));
        }

    }

    public PlaceDTO GetPlaceDTOById(int id)
    {
        PlaceDTO placeDTO = new PlaceDTO();
        PlaceModel placeTmp = null;
        for (PlaceModel placeModel: placeModelArrayList)
        {
            if (placeModel.getPlaceId()==id)
            {
                placeTmp = placeModel;
                break;
            }
        }
        if (placeTmp!=null)
        {
            placeDTO.newPlaceName =placeTmp.getPlaceName();
            placeDTO.newPlaceDescription = placeTmp.getPlaceDescription();
            placeDTO.newPlaceImgUrl = placeTmp.getPlaceImgUrl();
        }

        return placeDTO;

    }

}