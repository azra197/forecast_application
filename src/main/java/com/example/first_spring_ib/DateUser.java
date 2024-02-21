package com.example.first_spring_ib;

public class DateUser {
    private String date;
    private User user;


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user= user;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date=date;
    }
}
