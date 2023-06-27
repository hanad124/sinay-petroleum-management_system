package com.example.petroleumsystem;

import java.util.Date;

public class salesClass {
    private int id ;
    private String customer_name ;
    private int customer_phone ;
    private String fuel_type ;
    private int tunk_number ;
    private int litters ;
    private int per_litters;
    private int total_price ;
    private Date date ;
    private String status ;

    public salesClass(int id , String customer_name , int customer_phone , String fuel_type , int tunk_number , int litters , int per_litters , int total_price , Date date , String status){
        this.id = id;
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.fuel_type = fuel_type;
        this.tunk_number = tunk_number;
        this.litters = litters;
        this.per_litters = per_litters;
        this.total_price = total_price ;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public int getTunk_number() {
        return tunk_number;
    }

    public int getLitters() {
        return litters;
    }

    public int getPer_litters() {
        return per_litters;
    }

    public int getTotal_price() {
        return total_price;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSupplier_name(String supplier_name) {
        this.customer_name = supplier_name;
    }

    public void setSupplier_phone(int supplier_phone) {
        this.customer_phone = supplier_phone;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }
    public void setTunk_number(int tunk_number) {
        this.tunk_number = tunk_number;
    }

    public void setLitters(int litters) {
        this.litters = litters;
    }

    public void setPer_litters(int per_litters) {
        this.per_litters = per_litters;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
