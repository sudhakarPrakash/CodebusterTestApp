package com.example.testapp.utilCode;

import android.graphics.Bitmap;

public class Hospital {

    private String hospitalId;
    private String hospitalName;
    private String description;
    private Bitmap hospitalImage;
    private String hospitalAddress;

    public Hospital() {

    }

    public Hospital(String hospitalId, String hospitalName, String description, Bitmap hospitalImage, String hospitalAddress) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.description = description;
        this.hospitalImage = hospitalImage;
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getHospitalImage() {
        return hospitalImage;
    }

    public void setHospitalImage(Bitmap hospitalImage) {
        this.hospitalImage = hospitalImage;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

}
