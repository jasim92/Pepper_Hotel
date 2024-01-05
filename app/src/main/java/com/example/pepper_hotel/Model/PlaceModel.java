package com.example.pepper_hotel.Model;

import android.util.Log;

import com.example.pepper_hotel.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceModel {
    private int placeId;
    private String placeName;
    private String placeDescription;
    private String PlaceImgUrl;
    private int cityId;

    public PlaceModel() {
    }

    public PlaceModel(int placeId, String placeName, String placeDescription, String placeImgUrl, int cityId) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        PlaceImgUrl = placeImgUrl;
        this.cityId = cityId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceImgUrl() {
        return PlaceImgUrl;
    }

    public void setPlaceImgUrl(String placeImgUrl) {
        PlaceImgUrl = placeImgUrl;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public static ArrayList<PlaceModel> fromJson(JSONArray result)
    {
        JSONObject placeJson;
        ArrayList<PlaceModel> places = new ArrayList<>();
        for (int i=0; i<result.length(); i++)
        {
            try {
                placeJson = result.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            PlaceModel placeModel = PlaceModel.fromJson(placeJson);
            if (placeModel!=null)
            {
                places.add(placeModel);
            }
        }
        return places;
    }

    private static PlaceModel fromJson(JSONObject placeJson) {
        PlaceModel placeModel = new PlaceModel();

        try {
            placeModel.setPlaceId(placeJson.getInt("id"));

            placeModel.setPlaceName(placeJson.getString("placeName"));

            placeModel.setPlaceDescription(placeJson.getString("placeDescription"));
            JSONObject cityObject = placeJson.getJSONObject("city");
            placeModel.setCityId(cityObject.getInt("id"));

            JSONObject imageObject = placeJson.getJSONObject("placeImage");
            JSONObject formatObject = imageObject.getJSONObject("formats");
            JSONObject thumbnailObject =  formatObject.getJSONObject("medium");
            placeModel.setPlaceImgUrl(NetworkUtils.BASE_URL.substring(0, NetworkUtils.BASE_URL.length()-1)
                    + thumbnailObject.getString("url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return placeModel;

    }
}
