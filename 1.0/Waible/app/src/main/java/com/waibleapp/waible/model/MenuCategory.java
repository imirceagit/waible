package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

@IgnoreExtraProperties
public class MenuCategory {

    @Exclude
    private String categoryId;
    private Map<String, String> name;
    private int total;

    public MenuCategory() {
    }

    public MenuCategory(Map<String, String> name, int total) {
        this.name = name;
        this.total = total;
    }

    public MenuCategory(String categoryId, Map<String, String> name, int total) {
        this.categoryId = categoryId;
        this.name = name;
        this.total = total;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("total", total);
        return result;
    }

    @Override
    public String toString() {
        return "MenuCategory{" +
                "categoryId='" + categoryId + '\'' +
                ", name=" + name +
                ", total=" + total +
                '}';
    }
}
