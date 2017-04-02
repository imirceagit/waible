package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

@IgnoreExtraProperties
public class User {

    @Exclude
    private String uid;
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
