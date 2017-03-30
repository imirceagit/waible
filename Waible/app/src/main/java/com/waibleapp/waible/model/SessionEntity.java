package com.waibleapp.waible.model;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

public class SessionEntity {

    private static SessionEntity instance;

    private String sessionId;
    private User user;
    private String restaurantUserId;
    private Restaurant restaurant;
    private String tableNo;
    private boolean scanned;

    private SessionEntity() {
        scanned = false;
        sessionId = RandomStringUtils.randomAlphabetic(10);
    }

    public static SessionEntity getInstance(){
        if (instance == null){
            instance = new SessionEntity();
        }
        return instance;
    }

    public String getRestaurantPath(){
        String path = "/restaurants/" + restaurantUserId + "/" + restaurant.getRestaurantId() + "/";
        return path;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRestaurantUserId() {
        return restaurantUserId;
    }

    public void setRestaurantUserId(String restaurantUserId) {
        this.restaurantUserId = restaurantUserId;
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
