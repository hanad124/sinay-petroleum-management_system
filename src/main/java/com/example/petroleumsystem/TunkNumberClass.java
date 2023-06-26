package com.example.petroleumsystem;

public class TunkNumberClass {
    private  int id ;
    private int tunkNumber ;
    private  String tunkCapacity ;

    public TunkNumberClass(int id , int tunkNumber , String tunkCapacity){
        this.id = id ;
        this.tunkNumber = tunkNumber;
        this.tunkCapacity = tunkCapacity ;
    }

    public int getId() {
        return id;
    }

    public int getTunkNumber() {
        return tunkNumber;
    }

    public String getTunkCapacity() {
        return tunkCapacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTunkNumber(int tunkNumber) {
        this.tunkNumber = tunkNumber;
    }

    public void setTunkCapacity(String tunkCapacity) {
        this.tunkCapacity = tunkCapacity;
    }
}
