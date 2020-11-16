package com.example.covid;

public class User {
    public  String id, password,email,position , address , contact , cnic;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public User(String id , String password, String email, String position, String address , String contact , String cnic) {

        this.id=id;

        this.password=password;

        this.email = email;

        this.position = position;
        this.address =address;
        this.contact=contact;
        this.cnic=cnic;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }


    public String getPosition() {
        return position;
    }




    public void setEmail(String email) {
        this.email = email;
    }


    public void setPosition(String position) {
        this.position = position;
    }



}
