package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

@IgnoreExtraProperties
public class PriceTier {

    private boolean hasMenu;

    public PriceTier() {
    }

    public PriceTier(boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public boolean isHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("hasMenu", hasMenu);
        return result;
    }

    @Override
    public String toString() {
        return "PriceTier{" +
                "hasMenu=" + hasMenu +
                '}';
    }
}
