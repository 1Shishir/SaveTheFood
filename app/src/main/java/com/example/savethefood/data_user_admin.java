package com.example.savethefood;

public class data_user_admin {
    String uid;
    String first_name;
    String last_name;
    String image;
    String phone;
    String address;

    public data_user_admin() {
    }

    public data_user_admin(String uid, String first_name, String last_name, String image, String phone, String address) {
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.image = image;
        this.phone = phone;
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
