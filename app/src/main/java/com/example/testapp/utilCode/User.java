package com.example.testapp.utilCode;

public class User {
    private String name;
    private String city;
    private String mobileNo;
    private String email;
    private String gender;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public User setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

}
