package com.example.pepper_hotel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelDetailFragment extends Fragment {

    TextView textView;
    ImageView imageView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotelDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelDetailFragment newInstance(String param1, String param2) {
        HotelDetailFragment fragment = new HotelDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_hotel_detail, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        textView = view.findViewById(R.id.dummyText);
        imageView = view.findViewById(R.id.background_hotel);

        setupNewHotel();
        return view;
    }

    void setupNewHotel()
    {
        getActivity().runOnUiThread(()->{
            MainActivity ma = (MainActivity) getActivity();

            if (ma.hotelFragment.myId==1)
            {
                //---------------this code is responsible for to set the view height & width and margins-------------------
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,678);
                params.setMargins(4, 8, 4, 8);
                imageView.setLayoutParams(params);
                //----------------------------------------end--------------------------------------------------------------
                imageView.setImageResource(ma.hotelFragment.myImage);
                ma.goToBookmark("init_hotel_amenities", "chat");

            }
            textView.setText(ma.hotelFragment.myDescription);
            imageView.setImageResource(ma.hotelFragment.myImage);
        });

    }


}