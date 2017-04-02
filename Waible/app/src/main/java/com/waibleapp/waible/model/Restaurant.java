package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

@IgnoreExtraProperties
public class Restaurant {

    @Exclude
    private String restaurantId;
    private String name;
    private Integer numberOfTables;
    private PriceTier priceTier;

    public Restaurant() {
    }

    public Restaurant(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant(String name, Integer numberOfTables, PriceTier priceTier) {
        this.name = name;
        this.numberOfTables = numberOfTables;
        this.priceTier = priceTier;
    }

    public Restaurant(String restaurantId, String name, Integer numberOfTables, PriceTier priceTier) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.numberOfTables = numberOfTables;
        this.priceTier = priceTier;
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

    public Integer getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(Integer numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public PriceTier getPriceTier() {
        return priceTier;
    }

    public void setPriceTier(PriceTier priceTier) {
        this.priceTier = priceTier;
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
