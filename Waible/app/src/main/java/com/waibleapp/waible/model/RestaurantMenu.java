package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

@IgnoreExtraProperties
public class RestaurantMenu {

    private List<MenuCategory> categories;
    private String defaultLanguage;
    private HashMap<String, Boolean> languages;

    public RestaurantMenu() {
    }

    public RestaurantMenu(List<MenuCategory> categories, String defaultLanguage, HashMap<String, Boolean> languages) {
        this.categories = categories;
        this.defaultLanguage = defaultLanguage;
        this.languages = languages;
    }

    public List<MenuCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategory> categories) {
        this.categories = categories;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public HashMap<String, Boolean> getLanguages() {
        return languages;
    }

    public void setLanguages(HashMap<String, Boolean> languages) {
        this.languages = languages;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("defaultLanguage", defaultLanguage);
        result.put("languages", languages);
        return result;
    }

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "categories=" + categories +
                ", defaultLanguage='" + defaultLanguage + '\'' +
                ", languages=" + languages +
                '}';
    }
}
