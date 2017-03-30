package com.waibleapp.waible.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.Localization;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.SessionEntity;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout cardMenuCategory;
    private TextView cardMenuCategoryName;
    private TextView cardMenuCategoryNoItems;

    public MenuCategoryViewHolder(View itemView) {
        super(itemView);

        cardMenuCategory = (LinearLayout) itemView.findViewById(R.id.card_menu_category);
        cardMenuCategoryName = (TextView) itemView.findViewById(R.id.card_menu_category_name);
        cardMenuCategoryNoItems = (TextView) itemView.findViewById(R.id.card_menu_category_no_items);
    }

    public void updateUI(MenuCategory menuCategory){
        cardMenuCategory.setBackgroundResource(MainActivity.mainActivity.getResources().getIdentifier("breakfast", "drawable", MainActivity.mainActivity.getPackageName()));
        cardMenuCategoryName.setText(menuCategory.getName().get(Localization.defaultLanguage));
        // TODO NUMBER OF ITEMS
        cardMenuCategoryNoItems.setText(String.valueOf(1));
    }
}
