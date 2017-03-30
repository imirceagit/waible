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
public class Restaurant {

    @Exclude
    private String restaurantId;
    private String name;
    private int numberOfTables;
    private PriceTier priceTier;

    public Restaurant() {
    }

    public Restaurant(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant(String name, int numberOfTables, PriceTier priceTier) {
        this.restaurantId = FirebaseDatabase.getInstance().getReference().push().getKey();
        this.name = name;
        this.numberOfTables = numberOfTables;
        this.priceTier = priceTier;
    }

    public Restaurant(String restaurantId, String name, int numberOfTables, PriceTier priceTier) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.numberOfTables = numberOfTables;
        this.priceTier = priceTier;
    }

    public void cloneRestaurant(Restaurant restaurant){
        this.setName(restaurant.getName());
        this.setNumberOfTables(restaurant.getNumberOfTables());
        this.setPriceTier(restaurant.getPriceTier());
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public PriceTier getPriceTier() {
        return priceTier;
    }

    public void setPriceTier(PriceTier priceTier) {
        this.priceTier = priceTier;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = priceTier.getFirebaseMap();
        result.put("name", name);
        result.put("numberOfTables", numberOfTables);
        return result;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId='" + restaurantId + '\'' +
                ", name='" + name + '\'' +
                ", numberOfTables=" + numberOfTables +
                ", priceTier=" + priceTier +
                '}';
    }
}
