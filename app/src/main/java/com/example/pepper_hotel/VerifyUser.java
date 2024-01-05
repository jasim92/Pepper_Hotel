package com.example.pepper_hotel;

import android.os.AsyncTask;

import com.example.pepper_hotel.Model.VerifyModel;

import org.json.JSONArray;

import java.io.IOException;

public class VerifyUser extends AsyncTask<String, Void, JSONArray> {
    MainActivity ma = new MainActivity();

    @Override
    protected JSONArray doInBackground(String... strings) {
        String url = strings[0];
        JSONArray result = null;
        try {
            result = NetworkUtils.getArrayResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        if (jsonArray != null && !jsonArray.equals(""))
            ma.homeFragment.verifyModelArrayList = VerifyModel.fromJson(jsonArray);

    }
}
