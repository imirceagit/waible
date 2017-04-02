package com.waibleapp.waible.model;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public class SessionEntity {

    private static SessionEntity instance;

    private User user;
    private Restaurant restaurant;
    private String tableNo;
    private boolean scanned;

    private SessionEntity(){
        tableNo = "";
        scanned = false;
    }

    public static SessionEntity getInstance(){
        if (instance == null){
            instance = new SessionEntity();
        }
        return instance;
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

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }
}
