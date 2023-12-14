package com.example.savethefood;

public class data_avaiable_food {

    String food_uid;
    String food_name;
    String food_buying_price;
    String food_selling_price;
    String food_location;
    String food_restaurant;
    String food_img;
    String food_date;
    String food_id;


    public data_avaiable_food() {
    }

    public data_avaiable_food(String food_uid, String food_name, String food_buying_price, String food_selling_price, String food_location, String food_restaurant, String food_img, String food_date, String food_id) {
        this.food_uid = food_uid;
        this.food_name = food_name;
        this.food_buying_price = food_buying_price;
        this.food_selling_price = food_selling_price;
        this.food_location = food_location;
        this.food_restaurant = food_restaurant;
        this.food_img = food_img;
        this.food_date = food_date;
        this.food_id = food_id;
    }

    public String getFood_uid() {
        return food_uid;
    }

    public void setFood_uid(String food_uid) {
        this.food_uid = food_uid;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_buying_price() {
        return food_buying_price;
    }

    public void setFood_buying_price(String food_buying_price) {
        this.food_buying_price = food_buying_price;
    }

    public String getFood_selling_price() {
        return food_selling_price;
    }

    public void setFood_selling_price(String food_selling_price) {
        this.food_selling_price = food_selling_price;
    }

    public String getFood_location() {
        return food_location;
    }

    public void setFood_location(String food_location) {
        this.food_location = food_location;
    }

    public String getFood_restaurant() {
        return food_restaurant;
    }

    public void setFood_restaurant(String food_restaurant) {
        this.food_restaurant = food_restaurant;
    }

    public String getFood_img() {
        return food_img;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

    public String getFood_date() {
        return food_date;
    }

    public void setFood_date(String food_date) {
        this.food_date = food_date;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }
}