package com.example.petroleumsystem;

public class FuelClass{
    private int id ;
    private int tunk_number;
    private String fuel_type;
    private String tunk_capacity;

    public FuelClass(int id , int tunk_number , String fuel_type , String tunk_capacity){

        this.id = id;
        this.tunk_number = tunk_number;
        this.fuel_type = fuel_type;
        this.tunk_capacity = tunk_capacity;

    }

    public int getId() {
        return id;
    }

    public int getTunk_number() {
        return tunk_number;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public String getTunk_capacity() {
        return tunk_capacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTunk_number(int tunk_number) {
        this.tunk_number = tunk_number;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public void setTunk_capacity(String tunk_capacity) {
        this.tunk_capacity = tunk_capacity;
    }
}
