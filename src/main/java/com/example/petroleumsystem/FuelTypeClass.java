package com.example.petroleumsystem;

public class FuelTypeClass {
    private  int id ;
    private String fuelType ;

    public FuelTypeClass(int id , String fuelType){
        this.id = id ;
        this.fuelType = fuelType;
    }

    public int getId() {
        return id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
