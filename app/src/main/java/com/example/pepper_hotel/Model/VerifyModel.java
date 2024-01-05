package com.example.pepper_hotel.Model;

import android.util.Log;

import com.example.pepper_hotel.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerifyModel {
    private String name;
    private int room_number;
    private long mobile_number;

    public VerifyModel(String name, int room_number, long mobile_number) {
        this.name = name;
        this.room_number = room_number;
        this.mobile_number = mobile_number;
    }

    public VerifyModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public long getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(long mobile_number) {
        this.mobile_number = mobile_number;
    }

    public static VerifyModel fromJson(JSONObject verifyJson)
    {
        VerifyModel verifyModel = new VerifyModel();
        try {
            verifyModel.setName(verifyJson.getString("name"));
            verifyModel.setRoom_number(verifyJson.getInt("room_number"));
            verifyModel.setMobile_number(verifyJson.getLong("mobile_number"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return verifyModel;
    }

    public static ArrayList<VerifyModel> fromJson(JSONArray result) {

        JSONObject verifyJson;
        ArrayList<VerifyModel> verifyModelsList = new ArrayList<>();

        for (int i = 0; i<result.length(); i++)
        {
            try {
                verifyJson = result.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            VerifyModel verifyModel = VerifyModel.fromJson(verifyJson);
            if (verifyModel!=null)
            {
                verifyModelsList.add(verifyModel);
            }
        }
        return verifyModelsList;
    }
}
