package com.example.covid;

public class User {
    public  String password,email,position;

    public User()
    {

    }



    public User(String password,String email, String position) {

        this.password=password;

        this.email = email;

        this.position = position;

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
