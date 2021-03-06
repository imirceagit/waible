package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

@IgnoreExtraProperties
public class MenuItem {

    @Exclude
    private String menuItemId;
    private HashMap<String, String> name;
    private Double price;
    private String imageURL;

    public MenuItem() {
    }

    public MenuItem(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public MenuItem(HashMap<String, String> name, Double price, String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public MenuItem(String menuItemId, HashMap<String, String> name, Double price, String imageURL) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public HashMap<String, String> getName() {
        return name;
    }

    public void setName(HashMap<String, String> name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId='" + menuItemId + '\'' +
                ", name=" + name +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
