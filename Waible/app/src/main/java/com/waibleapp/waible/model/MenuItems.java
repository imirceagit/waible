package com.waibleapp.waible.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mircea.ionita on 3/31/2017.
 */

@IgnoreExtraProperties
public class MenuItems {

    private List<MenuItem> menuItems;

    public MenuItems() {
    }

//    public void setMenuItemId(){
//        Set<String> keySet = menuItems.keySet();
//        for (String key : keySet){
//            menuItems.get(key).setMenuItemId(key);
//        }
//    }


    public MenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("menuItems", menuItems);
        return result;
    }

    @Override
    public String toString() {
        return "MenuItems{" +
                "menuItems=" + menuItems +
                '}';
    }
}
