package com.example.pepper_hotel.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pepper_hotel.Adapter.HotelAdapter;
import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.Model.HotelModel;
import com.example.pepper_hotel.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelFragment extends Fragment implements ListItemClickListener {
    RecyclerView hotel_rc;

    public ArrayList<HotelModel> hotelModelArrayList;
    public HotelAdapter hotelAdapter;
    public String myText, myDescription;
    public int myImage, myId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
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
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        Context context = view.getContext();
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        hotel_rc = view.findViewById(R.id.hotel_grid);
        hotel_rc.setLayoutManager(new GridLayoutManager(context, 2));
        hotelModelArrayList = new ArrayList<>();
        hotelModelArrayList.add(new HotelModel(0, "About Us", R.drawable.hotel_building3, getResources().getString(R.string.About_us)));
        hotelModelArrayList.add(new HotelModel(1, "Hotel Facilities", R.drawable.hotel_ameneties, getResources().getString(R.string.hotel_facilities)));
        hotelModelArrayList.add(new HotelModel(2, "Dining", R.drawable.dining, getResources().getString(R.string.dining)));
        hotelModelArrayList.add(new HotelModel(3, "Rooms", R.drawable.rooms, getResources().getString(R.string.rooms)));
        hotelAdapter = new HotelAdapter(this, hotelModelArrayList);
        hotel_rc.setAdapter(hotelAdapter);

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        getHotelCatById(clickedItemIndex);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.hotelFragment.myId==3)
        {
            mainActivity.showRoomFragment();
        }
        else
        {
            mainActivity.showHotelDetailFragment();
        }

    }

    public void getHotelCatById(int id)
    {
        myId = hotelModelArrayList.get(id).getId();
        myText = hotelModelArrayList.get(id).getHotelCat();
        myDescription = hotelModelArrayList.get(id).getHotel_text();
        myImage = hotelModelArrayList.get(id).getHotel_image();
    }

}
