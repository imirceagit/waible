package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

@IgnoreExtraProperties
public class RestaurantMenu {

    private HashMap<String, MenuCategory> categories;
    private String defaultLanguage;
    private HashMap<String, Boolean> languages;

    public RestaurantMenu() {
    }

    public RestaurantMenu(HashMap<String, MenuCategory> categories, String defaultLanguage, HashMap<String, Boolean> languages) {
        this.categories = categories;
        this.defaultLanguage = defaultLanguage;
        this.languages = languages;
    }

    public List<MenuCategory> getCategoriesAsList(List<MenuCategory> list){
        list.clear();
        Set<String> keySet = categories.keySet();
        for (String s : keySet){
            list.add(categories.get(s));
        }
        Collections.sort(list, new Comparator<MenuCategory>() {
            @Override
            public int compare(MenuCategory menuCategory, MenuCategory t1) {
                return menuCategory.getName().get(Localization.defaultLanguage).compareTo(t1.getName().get(Localization.defaultLanguage));
            }
        });
        return list;
    }

    public void setCategoriesId(){
        Set<String> keySet = categories.keySet();
        for (String s : keySet){
            categories.get(s).setCategoryId(s);
        }
    }

    public HashMap<String, MenuCategory> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, MenuCategory> categories) {
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
