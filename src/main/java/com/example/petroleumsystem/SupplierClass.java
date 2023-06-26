package com.example.petroleumsystem;

public class SupplierClass {
    private int id ;
    private String name ;
    private int phone ;
    private String address ;

   public SupplierClass(int id , String name , int phone , String address){
       this.id = id ;
       this.name = name ;
       this.phone = phone ;
       this.address = address ;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
