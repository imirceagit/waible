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
public class MenuItem {

    @Exclude
//    private String menuItemId;
    private Map<String, String> name;
    private String price;
    private String imageURL;

    public MenuItem() {
    }

    public MenuItem(Map<String, String> name, String price, String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

//    public MenuItem(String menuItemId, Map<String, String> name, String price, String imageURL) {
//        this.menuItemId = menuItemId;
//        this.name = name;
//        this.price = price;
//        this.imageURL = imageURL;
//    }

//    public String getMenuItemId() {
//        return menuItemId;
//    }
//
//    public void setMenuItemId(String menuItemId) {
//        this.menuItemId = menuItemId;
//    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Exclude
    public Map<String, Object> getFirebaseMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("imageURL", imageURL);
        return result;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name=" + name +
                ", price='" + price + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
