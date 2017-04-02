package com.waibleapp.waible.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

@IgnoreExtraProperties
public class PriceTier {

    private Boolean hasMenu;

    public PriceTier() {
    }

    public PriceTier(Boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public Boolean getHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(Boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    @Override
    public String toString() {
        return "PriceTier{" +
                "hasMenu=" + hasMenu +
                '}';
    }
}
