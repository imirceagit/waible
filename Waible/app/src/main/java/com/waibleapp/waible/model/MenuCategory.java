package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

@IgnoreExtraProperties
public class MenuCategory {

    @Exclude
    private String categoryId;
    private HashMap<String, String> name;
    private Integer total;

    public MenuCategory() {
    }

    public MenuCategory(String categoryId) {
        this.categoryId = categoryId;
    }

    public MenuCategory(HashMap<String, String> name, Integer total) {
        this.name = name;
        this.total = total;
    }

    public MenuCategory(String categoryId, HashMap<String, String> name, Integer total) {
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

    public HashMap<String, String> getName() {
        return name;
    }

    public void setName(HashMap<String, String> name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
