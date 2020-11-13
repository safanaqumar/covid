package com.example.covid;

public class User {
    public  String id, password,email,position;





    public User(String id , String password, String email, String position) {

        this.id=id;

        this.password=password;

        this.email = email;

        this.position = position;

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
