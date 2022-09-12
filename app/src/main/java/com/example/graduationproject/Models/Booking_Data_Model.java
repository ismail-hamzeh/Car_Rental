package com.example.graduationproject.Models;

public class Booking_Data_Model {

    String car_image, car_name, car_seats, car_doors, car_bags, car_gearShift, pickUp_location
            , return_location, pickUp_date, return_date, driver, car_price_total;

    public Booking_Data_Model(String car_image, String car_name, String car_seats, String car_doors, String car_bags,
                              String car_gearShift, String pickUp_location, String return_location, String pickUp_date,
                              String return_date, String driver, String car_price_total) {
        this.car_image = car_image;
        this.car_name = car_name;
        this.car_seats = car_seats;
        this.car_doors = car_doors;
        this.car_bags = car_bags;
        this.car_gearShift = car_gearShift;
        this.pickUp_location = pickUp_location;
        this.return_location = return_location;
        this.pickUp_date = pickUp_date;
        this.return_date = return_date;
        this.driver = driver;
        this.car_price_total = car_price_total;
    }

    public Booking_Data_Model() {

    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_seats() {
        return car_seats;
    }

    public void setCar_seats(String car_seats) {
        this.car_seats = car_seats;
    }

    public String getCar_doors() {
        return car_doors;
    }

    public void setCar_doors(String car_doors) {
        this.car_doors = car_doors;
    }

    public String getCar_bags() {
        return car_bags;
    }

    public void setCar_bags(String car_bags) {
        this.car_bags = car_bags;
    }

    public String getCar_gearShift() {
        return car_gearShift;
    }

    public void setCar_gearShift(String car_gearShift) {
        this.car_gearShift = car_gearShift;
    }

    public String getPickUp_location() {
        return pickUp_location;
    }

    public void setPickUp_location(String pickUp_location) {
        this.pickUp_location = pickUp_location;
    }

    public String getReturn_location() {
        return return_location;
    }

    public void setReturn_location(String return_location) {
        this.return_location = return_location;
    }

    public String getPickUp_date() {
        return pickUp_date;
    }

    public void setPickUp_date(String pickUp_date) {
        this.pickUp_date = pickUp_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCar_price_total() {
        return car_price_total;
    }

    public void setCar_price_total(String car_price_total) {
        this.car_price_total = car_price_total;
    }
}
