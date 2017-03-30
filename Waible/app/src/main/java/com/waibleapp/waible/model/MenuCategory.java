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
    private HashMap<String, String> name;
    private int total;

    public MenuCategory() {
    }

    public MenuCategory(HashMap<String, String> name) {
        this.categoryId = FirebaseDatabase.getInstance().getReference().push().getKey();
        this.name = name;
    }

    public MenuCategory(String categoryId, HashMap<String, String> name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public HashMap<String, String> getName() {
        return name;
    }

    public void setName(HashMap<String, String> name) {
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
