package com.example.pepper_hotel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.PlaceDTO;
import com.example.pepper_hotel.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceDetailFragment extends Fragment {

    ImageView place_image;
    TextView place_text;

    private PlaceDTO placeDto;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaceDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceDetailFragment newInstance(String param1, String param2) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_place_detail, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        place_image = view.findViewById(R.id.city_guide_image);
        place_text = view.findViewById(R.id.city_guide_text);
        setupNewPlace();
        return view;
    }

    public void setupNewPlace()
    {

        //----------------this code is responsible for loading image animation----------------------------
        final AtomicBoolean playAnimation = new AtomicBoolean(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, ScaleAnimation.RELATIVE_TO_SELF,
                .75f, ScaleAnimation.RELATIVE_TO_SELF, 0.75f);
        scaleAnimation.setDuration(800);
        getActivity().runOnUiThread(()->{
            place_text.setText(placeDto.newPlaceDescription);
            place_text.startAnimation(scaleAnimation);
            if (placeDto.newPlaceImgUrl !=null && !placeDto.newPlaceImgUrl.equalsIgnoreCase(""))
            {
                Picasso.get().load(placeDto.newPlaceImgUrl).into(place_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (playAnimation.get()) {

                            place_image.startAnimation(scaleAnimation);

                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }


        });

        //--------------------------------------end---------------------------------------------------------
    }

    public void setPlaceDto(PlaceDTO placeDto) {
        this.placeDto = placeDto;
    }
}