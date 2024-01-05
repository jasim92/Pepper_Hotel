/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.pepper_hotel;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    public final static String BASE_URL =
            "http://192.168.0.137:1338/";

    public final static String CITY_URL =
            BASE_URL + "cities";

    public final static String PLACE_URL =
            BASE_URL + "places";
    public final static String TAXI_BOOKING =
            BASE_URL + "taxi-bookings";
    public final static String LAUNDRY_BOOKING =
            BASE_URL + "laundries";
    public final static String CLEANING_BOOKING =
            BASE_URL + "room-cleanings";
    public final static String ROOM_VERIFY =
            BASE_URL + "rooms";

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */

    public static JSONArray getArrayResponse(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        try {
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                Log.e("response: ", String.valueOf(response.code()));

                String body = response.body().string();
                JSONArray bodyJson = new JSONArray(body);
                return bodyJson;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } finally {
//            okHttpClient.disconnect();
        }
    }
    public static int taxiBooking(String  mobile_number, String taxi_ref, String url) throws IOException
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Request.Builder builder = new Request.Builder();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = null;
            if (url.contains("taxi-bookings"))
            {
                 body = RequestBody.create(mediaType, "mobile_number="+mobile_number+"&"+"Reference="+taxi_ref);
            }
            if (url.contains("laundries"))
            {
                body = RequestBody.create(mediaType, "room_number="+mobile_number+"&"+"reference="+taxi_ref);
            }
            if (url.contains("room-cleanings"))
            {
                body = RequestBody.create(mediaType, "room_number="+mobile_number+"&"+"reference="+taxi_ref);
            }

            builder.url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Request request = builder.build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                Log.e(url+": ", String.valueOf(response.code()));
                return response.code();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        } finally {
        }
    }
}