package com.example.testapp.utilCode;


import android.graphics.Bitmap;

public class Doctor {

    private Bitmap mDoctorImage;
    private String mDoctorName;
    private String mDoctorSpecialization;


    public Doctor(Bitmap mDoctorImage, String mDoctorName, String mDoctorSpecialization) {
        this.mDoctorImage = mDoctorImage;
        this.mDoctorName = mDoctorName;
        this.mDoctorSpecialization = mDoctorSpecialization;
    }

    public Bitmap getmDoctorImage() {
        return mDoctorImage;
    }

    public Doctor setmDoctorImage(Bitmap mDoctorImage) {
        this.mDoctorImage = mDoctorImage;
        return this;
    }

    public String getmDoctorName() {
        return mDoctorName;
    }

    public Doctor setmDoctorName(String mDoctorName) {
        this.mDoctorName = mDoctorName;
        return this;
    }

    public String getmDoctorSpecialization() {
        return mDoctorSpecialization;
    }

    public Doctor setmDoctorSpecialization(String mDoctorSpecialization) {
        this.mDoctorSpecialization = mDoctorSpecialization;
        return this;
    }
}
