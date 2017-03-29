package com.waibleapp.waible.model;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class MenuCategory {

    private String name;
    private int noItems;
    private String image;

    public MenuCategory(String name, int noItems, String image) {
        this.name = name;
        this.noItems = noItems;
        this.image = image;
    }

    public MenuCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoItems() {
        return noItems;
    }

    public void setNoItems(int noItems) {
        this.noItems = noItems;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
