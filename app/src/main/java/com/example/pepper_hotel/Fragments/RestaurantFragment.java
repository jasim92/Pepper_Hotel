package com.example.pepper_hotel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.Adapter.ServiceAdapter;
import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.Model.ServiceModel;
import com.example.pepper_hotel.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment implements ListItemClickListener {

    RecyclerView recyclerView;
    public ArrayList<ServiceModel> serviceArrayList;
    ServiceAdapter serviceAdapter;
    String serviceText;
    int serviceImage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        recyclerView = view.findViewById(R.id.service_rc);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        serviceArrayList = new ArrayList<>();
        serviceArrayList.add(new ServiceModel(0, R.drawable.cleaning, "Makeup My Room"));
        serviceArrayList.add(new ServiceModel(1, R.drawable.laundry1, "Laundry"));
        serviceAdapter = new ServiceAdapter(serviceArrayList, this);
        recyclerView.setAdapter(serviceAdapter);
        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        getServiceCatById(clickedItemIndex);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (clickedItemIndex == 0)
            mainActivity.showCleaningFragment();
        if (clickedItemIndex == 1)
            mainActivity.showLaundryFragment();

    }

    public void getServiceCatById(int id) {

        serviceText = serviceArrayList.get(id).getServiceText();
        serviceImage = serviceArrayList.get(id).getServiceImg();
    }
}