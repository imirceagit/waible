package com.waibleapp.waible.model;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class LoginEntity {

    private String uid;
    private User user;
    private Restaurant restaurant;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
