package com.example.pepper_hotel.Model;

import android.util.Log;

import com.example.pepper_hotel.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityModel {
    private int cityId;
    private String cityName;
    private String cityImgUrl;

    public CityModel(int cityId, String cityName, String cityImgUrl) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityImgUrl = cityImgUrl;
    }

    public CityModel() {
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityImgUrl() {
        return cityImgUrl;
    }

    public void setCityImgUrl(String cityImgUrl) {
        this.cityImgUrl = cityImgUrl;
    }

    public static CityModel fromJson(JSONObject cityJson)
    {
        CityModel cityModel = new CityModel();
        try {
            cityModel.setCityId(cityJson.getInt("id"));

            cityModel.setCityName(cityJson.getString("cityName"));

            JSONObject imageObject = cityJson.getJSONObject("cityLogo");
            JSONObject formateObject = imageObject.getJSONObject("formats");
            JSONObject imageThumbnailObject = formateObject.getJSONObject("thumbnail");
            cityModel.setCityImgUrl(NetworkUtils.BASE_URL.substring(0, NetworkUtils.BASE_URL.length()-1)
                    + imageThumbnailObject.getString("url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cityModel;
    }

    public static ArrayList<CityModel> fromJson(JSONArray result) {

        JSONObject cityJson;
        ArrayList<CityModel> cities = new ArrayList<>();

        for (int i = 0; i<result.length(); i++)
        {
            try {
                cityJson = result.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            CityModel cityModel = CityModel.fromJson(cityJson);
            if (cityModel!=null)
            {
                cities.add(cityModel);
            }
        }
        return cities;
    }
}
