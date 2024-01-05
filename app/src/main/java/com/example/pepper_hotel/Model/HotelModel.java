package com.example.pepper_hotel.Model;

public class HotelModel {
    private int id;
    private String hotelCat;
    private int hotel_image;
    private String hotel_text;

    public HotelModel() {
    }

    public HotelModel(int id, String hotelCat, int hotel_image, String hotel_text) {
        this.id = id;
        this.hotelCat = hotelCat;
        this.hotel_image = hotel_image;
        this.hotel_text = hotel_text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelCat() {
        return hotelCat;
    }

    public void setHotelCat(String hotelCat) {
        this.hotelCat = hotelCat;
    }

    public int getHotel_image() {
        return hotel_image;
    }

    public void setHotel_image(int hotel_image) {
        this.hotel_image = hotel_image;
    }

    public String getHotel_text() {
        return hotel_text;
    }

    public void setHotel_text(String hotel_text) {
        this.hotel_text = hotel_text;
    }
}
