package com.waibleapp.waible.listeners;

import com.waibleapp.waible.model.MenuCategory;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

public interface OnMenuItemInteractionListener {
    void onMenuItemInteraction(MenuCategory menuCategory, int position);
}
