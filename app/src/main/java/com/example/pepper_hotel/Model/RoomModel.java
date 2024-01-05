package com.example.pepper_hotel.Model;

public class RoomModel {
    private int roomImg;
    private String roomDescription;

    public RoomModel(int roomImg, String roomDescription) {
        this.roomImg = roomImg;
        this.roomDescription = roomDescription;
    }

    public int getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(int roomImg) {
        this.roomImg = roomImg;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
}
