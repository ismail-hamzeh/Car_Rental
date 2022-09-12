package com.example.graduationproject.Models;

public class CarItem {

     String car_img;
     String car_name;
     String car_price;
     String seats_number;
     String doors_number;
     String suitcase_number;
     String gear_shift_txt;

    public CarItem() {

    }


    public String getCar_img() {
        return car_img;
    }

    public void setCar_img(String car_img) {
        this.car_img = car_img;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_price() {
        return car_price;
    }

    public void setCar_price(String car_price) {
        this.car_price = car_price;
    }

    public String getSeats_number() {
        return seats_number;
    }

    public void setSeats_number(String seats_number) {
        this.seats_number = seats_number;
    }

    public String getDoors_number() {
        return doors_number;
    }

    public void setDoors_number(String doors_number) {
        this.doors_number = doors_number;
    }

    public String getSuitcase_number() {
        return suitcase_number;
    }

    public void setSuitcase_number(String suitcase_number) {
        this.suitcase_number = suitcase_number;
    }

    public String getGear_shift_txt() {
        return gear_shift_txt;
    }

    public void setGear_shift_txt(String gear_shift_txt) {
        this.gear_shift_txt = gear_shift_txt;
    }

    public CarItem(String car_img, String car_name, String car_price, String seats_number,
                   String doors_number, String suitcase_number, String gear_shift_txt) {

        this.car_img = car_img;
        this.car_name = car_name;
        this.car_price = car_price;
        this.seats_number = seats_number;
        this.doors_number = doors_number;
        this.suitcase_number = suitcase_number;
        this.gear_shift_txt = gear_shift_txt;
    }





    @Override
    public String toString() {
        return "CarItem{" +
                "car_img=" + car_img +
                ", car_name=" + car_name +
                ", car_price=" + car_price +
                ", seats_number=" + seats_number +
                ", doors_number=" + doors_number +
                ", suitcase_number=" + suitcase_number +
                ", gear_shift_txt=" + gear_shift_txt +
                '}';
    }
}
