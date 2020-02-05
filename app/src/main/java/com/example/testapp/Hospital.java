package com.example.testapp;

import android.graphics.Bitmap;

class Hospital {

    private String hospitalId;
    private String hospitalName;
    private String description;
    private Bitmap hospitalImage;
    private String hospitalAddress;

    Hospital(){

    }

    Hospital(String hospitalId, String hospitalName, String description, Bitmap hospitalImage, String hospitalAddress) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.description = description;
        this.hospitalImage = hospitalImage;
        this.hospitalAddress = hospitalAddress;
    }

    String getHospitalId() {
        return hospitalId;
    }

    String getHospitalName() {
        return hospitalName;
    }

    String getDescription() {
        return description;
    }

    Bitmap getHospitalImage() {
        return hospitalImage;
    }

    String getHospitalAddress() {
        return hospitalAddress;
    }

    void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setHospitalImage(Bitmap hospitalImage) {
        this.hospitalImage = hospitalImage;
    }

    void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

}
