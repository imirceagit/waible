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
public class MenuItems {

    @Exclude
    private String menuItemId;
    private HashMap<String, String> name;
    private double price;

    public MenuItems() {
    }

    public MenuItems(HashMap<String, String> name, double price) {
        this.menuItemId = FirebaseDatabase.getInstance().getReference().push().getKey();
        this.name = name;
        this.price = price;
    }

    public MenuItems(String menuItemId, HashMap<String, String> name, double price) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        return result;
    }

    @Override
    public String toString() {
        return "MenuItems{" +
                "menuItemId='" + menuItemId + '\'' +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}
