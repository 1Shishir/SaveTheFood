package com.example.savethefood;

public class data_cart {
    String food_name;
    String restaurant;
    String buying_price;
    String selling_price;
    String location;

    String img;

    String customer;
    String food_id;
    String date;

    public data_cart() {
    }

    public data_cart(String food_name, String restaurant, String buying_price, String selling_price, String location, String img, String customer, String food_id,String date) {
        this.food_name = food_name;
        this.restaurant = restaurant;
        this.buying_price = buying_price;
        this.selling_price = selling_price;
        this.location = location;
        this.img = img;
        this.customer = customer;
        this.food_id = food_id;
        this.date=date;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(String buying_price) {
        this.buying_price = buying_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

