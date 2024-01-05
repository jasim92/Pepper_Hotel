package com.example.pepper_hotel;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.BaseQiChatExecutor;
import com.example.pepper_hotel.Model.CityModel;
import com.example.pepper_hotel.Model.HotelModel;
import com.example.pepper_hotel.Model.PlaceModel;
import com.example.pepper_hotel.Model.ServiceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StatusExecutor extends BaseQiChatExecutor {
    private QiContext qiContext;
    private MainActivity ma;
    private String TAG = "MSI_StatusExecutor";

    MediaPlayer mediaPlayer;
    Context context;
    protected StatusExecutor(QiContext qiContext, MainActivity mainActivity,MediaPlayer mediaPlayer , Context context) {
        super(qiContext);
        this.qiContext = qiContext;
        this.ma = mainActivity;
        this.context = context;
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void runWith(List<String> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        String field = params.get(0);
        String value = "";
        if(params.size() == 2){
            value = params.get(1);
        }
        Log.d(TAG,"field : " + field + " value : "+ value);

        //use switch case here

        switch (field){
            case ("city_select"):
                ArrayList<CityModel> cityList = ma.cityFragment.cities;
                for (int i=0; i<cityList.size(); i++)
                {
                    if (cityList.get(i).getCityName().equalsIgnoreCase(value))
                    {
                        ma.placeFragment.GetPlaceByCity(cityList.get(i).getCityId());
                        ma.showPlaceFragment();
                    }
                }
                break;
            case ("place_select"):
                ArrayList<PlaceModel> placeList = ma.placeFragment.placeModelArrayList;
                for (int i=0; i<placeList.size(); i++)
                {
                    if (placeList.get(i).getPlaceName().equalsIgnoreCase(value))
                    {
                        ma.GetPlaceById(placeList.get(i).getPlaceId());
                        ma.showPlaceDetailFragment();
                        ma.placeDetailFragment.setupNewPlace();
                        break;
                    }
                }
                break;
            case("hotelc_select"):
                ArrayList<HotelModel> hotelList = ma.hotelFragment.hotelModelArrayList;
                for (int i = 0; i<hotelList.size(); i++)
                {
                    if (hotelList.get(i).getHotelCat().equalsIgnoreCase(value))
                    {
                        if (hotelList.get(i).getId()==3)
                        {
                            ma.hotelFragment.getHotelCatById(3);
                            ma.showRoomFragment();
                            break;
                        }

                        else
                        {
                            ma.hotelFragment.getHotelCatById(hotelList.get(i).getId());
                            ma.showHotelDetailFragment();
                            break;
                        }


                    }
                }
                break;
            case ("hotel_select"):
                ma.showHotelFragment();
                break;
            case("home_select"):
                ma.GoHomeFragment();
                break;
            case("back_select"):
                ma.GoBackFragment();
                break;
            case("guide_select"):
                ma.showCityFragment();
                break;
            case("taxi_select"):
                ma.showTaxiConfirmFragment();
                break;
            case ("taxi_booking_select"):
                ma.showTaxiFragment();
                break;
            case("service_select"):
                ma.showRestaurantFragment();
                break;
            case("dubai_select"):
                ma.showPlaceFragment();
                break;
            case ("cleaning_select"):
                ArrayList<ServiceModel> serviceList = ma.restaurantFragment.serviceArrayList;
//                for (int i = 0; i<serviceList.size(); i++)
//                {
//                    if (serviceList.get(i).getServiceText().contains(value))
//                    {
//                        ma.restaurantFragment.getServiceCatById(serviceList.get(i).getId());
//                        ma.showServiceBookFragment();
//                        break;
//                    }
//                }
                ma.restaurantFragment.getServiceCatById(serviceList.get(0).getId());
                ma.showCleaningFragment();
                break;
            case ("laundry_select"):
                ArrayList<ServiceModel> serviceList2 = ma.restaurantFragment.serviceArrayList;
//                for (int i = 0; i<serviceList2.size(); i++)
//                {
//                    if (serviceList2.get(i).getServiceText().equalsIgnoreCase(value))
//                    {
//                        ma.restaurantFragment.getServiceCatById(serviceList2.get(i).getId());
//                        ma.showServiceBookFragment();
//                        break;
//                    }
//                }
                ma.restaurantFragment.getServiceCatById(serviceList2.get(1).getId());
                ma.showLaundryFragment();
                break;
//            case ("shakehands_animation"):
//                Animation last = AnimationBuilder.with(getQiContext())
//                        .withResources(R.raw.thankyou)
//                        .build();
//                Animate animate2 = AnimateBuilder.with(getQiContext())
//                        .withAnimation(last)
//                        .build();
//                animate2.async().run();
//                break;
            case ("guitar_animation"):
                new Timer("Diamond", false).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        playmedia(R.raw.california);
                    }

                    private void playmedia(int mediaResource) {
                        mediaPlayer.reset();
                        mediaPlayer = MediaPlayer.create(context, mediaResource);
                        mediaPlayer.start();
                    }
                }, 2000);
                Animation guitarAnim = AnimationBuilder.with(getQiContext())
                        .withResources(R.raw.guitar_a001)
                        .build();
                Animate guitarAnimate = AnimateBuilder.with(getQiContext())
                        .withAnimation(guitarAnim)
                        .build();
                guitarAnimate.async().run();
                break;
        }

    }

    @Override
    public void stop() {

    }
}
