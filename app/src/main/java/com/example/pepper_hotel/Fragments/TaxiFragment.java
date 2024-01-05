package com.example.pepper_hotel.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.NetworkUtils;
import com.example.pepper_hotel.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaxiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaxiFragment extends Fragment {
    TextView tv, tv_tb;
    EditText room, name;
    Button mobile_submit;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayout;
    String taxi_ref;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaxiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaxiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaxiFragment newInstance(String param1, String param2) {
        TaxiFragment fragment = new TaxiFragment();
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
        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        tv = view.findViewById(R.id.abcv);
        tv_tb = view.findViewById(R.id.tv_tb);
        relativeLayout = view.findViewById(R.id.myLinear);
        room = view.findViewById(R.id.room_number_box);
        name = view.findViewById(R.id.name_box);
        room.setSaveEnabled(false);
        name.setSaveEnabled(false);
        mobile_submit = view.findViewById(R.id.mobile_number_submit);
        mobile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myProgress();
                    int room_number = Integer.parseInt(room.getText().toString());
                    String firstName = name.getText().toString();
                    room.getText().clear();
                    name.getText().clear();
                    boolean isThere = false;
                    for (int i = 0; i < ma.homeFragment.verifyModelArrayList.size(); i++) {
                        if (room_number == ma.homeFragment.verifyModelArrayList.get(i).getRoom_number()) {
                            isThere = true;
                            if (firstName.equalsIgnoreCase(ma.homeFragment.verifyModelArrayList.get(i).getName())) {
                                new PostTaxiBooking().execute(String.valueOf(room_number), mParam2+"taxi-bookings");
                                hideSoftKeyboard(getActivity());
                                break;

                            } else {
                                String msgNoData = "this room does not belongs to you";
                                String bookmarkMsg = "init_verify";
                                getReferenceStatus(msgNoData, bookmarkMsg);
                                break;
                            }

                        }
                    }
                    if (isThere == false) {
                        String msgNoData = "this room does not belongs to you";
                        String bookmarkMsg = "init_verify";
                        getReferenceStatus(msgNoData, bookmarkMsg);

                    }

                } catch (NumberFormatException e) {
                    progressDialog.dismiss();
                    ma.goToBookmark("init_roomnumber", "chat");
                }
            }
        });
        return view;
    }

    public class PostTaxiBooking extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... urls) {
            String room = urls[0];
            String url = urls[1];
            taxi_ref = "T" + System.currentTimeMillis() / 1000;
            int response = 0;
            try {
                Log.e("newUrl: ", url);
                response = NetworkUtils.taxiBooking(room, taxi_ref, url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(Integer response) {
            super.onPostExecute(response);
            if (response == 200) {
                String msgReference = "Your reference number is: " + taxi_ref + "\n" + "We will let you know, as soon as your taxi arrives";
                String bookmarkMsg = "init_contact";
                getReferenceStatus(msgReference, bookmarkMsg);
            } else {
                String errorMsg = "Due to a technical glitch Taxi Booking service is not working";
                String bookmarkMsg = "init_glitch_taxi";
                getReferenceStatus(errorMsg, bookmarkMsg);
            }

        }
    }

    public void getReferenceStatus(String message, String bookmark) {
        progressDialog.dismiss();
        MainActivity ma = (MainActivity) getActivity();
        tv_tb.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        tv.setText(message);
        ma.goToBookmark(bookmark, "chat");
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        ma.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_tb.setVisibility(View.VISIBLE);
                                tv.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                                ma.showHomeFragment();
                                ma.goToBookmark("init_help", "chat");
                            }
                        });

                    }
                },
                7000
        );
    }

    public void myProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Connecting server...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}