package com.example.pepper_hotel.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link LaundryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaundryFragment extends Fragment {

    TextView textTitle, text_ref;
    EditText l_room_num, l_name;
    Button l_room_submit;
    String service_ref;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LaundryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaundryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaundryFragment newInstance(String param1, String param2) {
        LaundryFragment fragment = new LaundryFragment();
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
        View view = inflater.inflate(R.layout.fragment_laundry, container, false);
        MainActivity ma = (MainActivity) getActivity();
        ma.back_button(view);
        ma.home_button(view);
        textTitle = view.findViewById(R.id.title_laundry);
        textTitle.setText(ma.restaurantFragment.serviceText);
        text_ref = view.findViewById(R.id.ref_text);
        relativeLayout = view.findViewById(R.id.myLaundryLinear);
        l_room_num = view.findViewById(R.id.l_room_number_box);
        l_name = view.findViewById(R.id.l_name_box);
        l_room_submit = view.findViewById(R.id.l_room_number_submit);
        l_room_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myProgress();
                    int room_number = Integer.parseInt(l_room_num.getText().toString());
                    String name = l_name.getText().toString();
                    l_name.getText().clear();
                    l_room_num.getText().clear();
                    boolean isThere = false;
                    for (int i = 0; i < ma.homeFragment.verifyModelArrayList.size(); i++) {
                        if (room_number == ma.homeFragment.verifyModelArrayList.get(i).getRoom_number()) {
                            isThere = true;
                            if (name.equalsIgnoreCase(ma.homeFragment.verifyModelArrayList.get(i).getName())) {
                                service_ref = "C" + System.currentTimeMillis() / 1000;
//                                  do your networking work
                                new PostLaundryService().execute(String.valueOf(room_number), mParam2+"laundries");
                                break;
                            } else {
                                String msgNoData = "this room does not belongs to you";
                                String bookmark = "init_verify";
                                getReferenceStatus(msgNoData,bookmark);
                                break;

                            }
                        }

                    }
                    if (isThere == false) {
                        String msgNoData = "this room does not belongs to you";
                        String bookmark = "init_verify";
                        getReferenceStatus(msgNoData,bookmark);

                    }

                } catch (NumberFormatException e) {
                    progressDialog.dismiss();
                    ma.goToBookmark("init_roomnumber", "chat");
                }
            }
        });
        return view;
    }

    public class PostLaundryService extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... urls) {
            String room = urls[0];
            String url = urls[1];
            int response = 0;
            try {
                Log.e("newUrl: ", url);
                response = NetworkUtils.taxiBooking(room, service_ref, url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(Integer response) {
            super.onPostExecute(response);
            if (response == 200) {

                String L_msg = "Your reference number is: " + service_ref + "\n"
                        + "Someone will collect laundry from your room in few minutes";
                String L_bookmark = "init_service_laundry";
                getReferenceStatus(L_msg, L_bookmark);

            } else {
                String gitchMsg = "Due to a technical glitch this service is not working";
                String glitchBookmark = "init_glitch";
                getReferenceStatus(gitchMsg,glitchBookmark);
            }
        }
    }

    public void myProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Connecting server...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void getReferenceStatus(String message, String bookmark)
    {
        MainActivity ma = (MainActivity) getActivity();
        progressDialog.dismiss();
        text_ref.setText(message);
        text_ref.setVisibility(View.VISIBLE);
        textTitle.setVisibility(View.GONE);
        ma.goToBookmark(bookmark, "chat");
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        ma.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text_ref.setVisibility(View.GONE);
                                textTitle.setVisibility(View.VISIBLE);
                                ma.showHomeFragment();
                                ma.goToBookmark("init_help", "chat");
                            }
                        });

                    }
                },
                7000
        );
    }
    }
