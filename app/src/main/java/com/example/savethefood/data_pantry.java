package com.example.savethefood;

public class data_pantry {
    String pantry_day;
    String pantry_img;
    String pantry_name;
    String pantry_qut;
    public data_pantry(){}
    public data_pantry(String pantry_day, String pantry_img, String pantry_name, String pantry_qut) {
        this.pantry_day = pantry_day;
        this.pantry_img = pantry_img;
        this.pantry_name = pantry_name;
        this.pantry_qut = pantry_qut;
    }

    public String getPantry_day() {
        return pantry_day;
    }

    public void setPantry_day(String pantry_day) {
        this.pantry_day = pantry_day;
    }

    public String getPantry_img() {
        return pantry_img;
    }

    public void setPantry_img(String pantry_img) {
        this.pantry_img = pantry_img;
    }

    public String getPantry_name() {
        return pantry_name;
    }

    public void setPantry_name(String pantry_name) {
        this.pantry_name = pantry_name;
    }

    public String getPantry_qut() {
        return pantry_qut;
    }

    public void setPantry_qut(String pantry_qut) {
        this.pantry_qut = pantry_qut;
    }
}
