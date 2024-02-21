package com.example.first_spring_ib;

public class User {
    private String name;
    private String surname;
    private String city;

    User(String name, String surname, String city) {

        this.name = name;
        this.surname = surname;
        this.city = city;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getCity() {
        return this.city;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setSurname(String surname) {
        this.surname= surname;
    }

    public void setCity(String city) {
        this.city= city;
    }

    User(){
    }

}
