package com.example.pepper_hotel.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.Adapter.SplashAdapter;
import com.example.pepper_hotel.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SplashScreenFragment extends Fragment {
    private static final String TAG = "MSI_Fragment";
    private RecyclerView recyclerView;
    private ArrayList<Integer> splashPics;
    private SplashAdapter splashAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SplashScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SplashScreenFragment newInstance(String param1, String param2) {
        SplashScreenFragment fragment = new SplashScreenFragment();
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
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        recyclerView = view.findViewById(R.id.splash_rc);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(llm);
        splashPics= new ArrayList<>(Arrays.asList(R.drawable.downtown, R.drawable.burj_alarab, R.drawable.skyline, R.drawable.ain));
        splashAdapter = new SplashAdapter(splashPics);

        //-----------------------This code is responsible for automatic recyclerview scrolling----------------------------//

        final int duration = 10000;
        final int pixelsToMove = 1280;
        final Handler mHandler = new Handler(Looper.getMainLooper());
        final Runnable SCROLLING_RUNNABLE = new Runnable() {

            @Override
            public void run() {
                recyclerView.smoothScrollBy(pixelsToMove, 0);
                mHandler.postDelayed(this, duration);
            }
        };
        recyclerView.setAdapter(splashAdapter);
        recyclerView.setClipToOutline(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(splashAdapter);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 1000);
                        }
                    }, 1000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 10000);
        //---------------------------------------end--------------------------------------------------------------
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

