package com.example.pepper_hotel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pepper_hotel.Adapter.RoomAdapter;
import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.Model.RoomModel;
import com.example.pepper_hotel.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {
    RecyclerView rc;
    RoomAdapter adapter;
    ArrayList<RoomModel> rmList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
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
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        rc = view.findViewById(R.id.room_rc);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false);
        rc.setLayoutManager(llm);
        rmList = new ArrayList<>();
        rmList.add(new RoomModel(R.drawable.rooms, getResources().getString(R.string.rooms)));
        rmList.add(new RoomModel(R.drawable.room3, getResources().getString(R.string.room3)));
        rmList.add(new RoomModel(R.drawable.room4, getResources().getString(R.string.room4)));
        adapter = new RoomAdapter(rmList);

        //-----------------------This code is responsible for automatic recyclerview scrolling----------------------------//

        final int duration = 10000;
        final int pixelsToMove = 1280;
        final Handler mHandler = new Handler(Looper.getMainLooper());
        final Runnable SCROLLING_RUNNABLE = new Runnable() {

            @Override
            public void run() {
                rc.smoothScrollBy(pixelsToMove, 0);
                mHandler.postDelayed(this, duration);
            }
        };
        rc.setAdapter(adapter);
        rc.setClipToOutline(true);

        rc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = llm.findLastCompletelyVisibleItemPosition();
                if(lastItem == llm.getItemCount()-1){
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rc.setAdapter(null);
                            rc.setAdapter(adapter);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 10000);
                        }
                    }, 10000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 10000);
        //---------------------------------------end--------------------------------------------------------------
        return view;
    }
}