package com.example.pepper_hotel.Model;

public class ServiceModel {
    private int id;
    private int serviceImg;
    private String serviceText;

    public ServiceModel() {
    }

    public ServiceModel(int id, int serviceImg, String serviceText) {
        this.id = id;
        this.serviceImg = serviceImg;
        this.serviceText = serviceText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceImg() {
        return serviceImg;
    }

    public void setServiceImg(int serviceImg) {
        this.serviceImg = serviceImg;
    }

    public String getServiceText() {
        return serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }
}
