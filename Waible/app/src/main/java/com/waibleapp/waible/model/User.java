package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

@IgnoreExtraProperties
public class User {

    @Exclude
    private String userId;
    private String name;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
