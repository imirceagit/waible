package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

@IgnoreExtraProperties
public class RestaurantMenu {

    @Exclude
    private List<MenuCategory> categoryList;
    private HashMap<String, MenuCategory> categories;
    private String defaultLanguage;
    private HashMap<String, Boolean> languages;

    public RestaurantMenu() {
    }

    public RestaurantMenu(List<MenuCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public RestaurantMenu(HashMap<String, MenuCategory> categories, String defaultLanguage, HashMap<String, Boolean> languages) {
        this.categories = categories;
        this.defaultLanguage = defaultLanguage;
        this.languages = languages;
    }

    public RestaurantMenu(List<MenuCategory> categoryList, HashMap<String, MenuCategory> categories, String defaultLanguage, HashMap<String, Boolean> languages) {
        this.categoryList = categoryList;
        this.categories = categories;
        this.defaultLanguage = defaultLanguage;
        this.languages = languages;
    }

    public void setCategoriesId(){
        categoryList = new ArrayList<>();
        Set<String> keySet = categories.keySet();
        for (String key : keySet){
            categories.get(key).setCategoryId(key);
            categoryList.add(categories.get(key));
        }
        categories.clear();
    }

    public List<MenuCategory> cloneCategoryList(List<MenuCategory> list){
        list.clear();
        list.addAll(categoryList);
        return list;
    }

    public List<MenuCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<MenuCategory> categoryList) {
        this.categoryList = categoryList;
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

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "categories=" + categories +
                ", defaultLanguage='" + defaultLanguage + '\'' +
                ", languages=" + languages +
                '}';
    }
}
