package com.example.savethefood;

public class data_admin_user {
String first_name;
String last_name;
String address;
String email;
String phone;
String image;
String uid;

    public data_admin_user(){}

    public data_admin_user(String f_name, String l_name, String address, String phone, String p_img,String uid) {
        this.first_name = f_name;
        this.last_name = l_name;
        this.address = address;
        this.phone = phone;
        this.image = p_img;
        this.uid=uid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
