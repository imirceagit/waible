package com.waibleapp.waible.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fmsirvent.ParallaxEverywhere.PEWImageView;
import com.waibleapp.waible.R;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.utils.Localization;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private PEWImageView cardMenuCategoryImage;
    private TextView cardMenuCategoryName;
    private TextView cardMenuCategoryNoItems;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        cardMenuCategoryImage = (PEWImageView) itemView.findViewById(R.id.card_menu_category_image);
        cardMenuCategoryName = (TextView) itemView.findViewById(R.id.card_menu_category_name);
        cardMenuCategoryNoItems = (TextView) itemView.findViewById(R.id.card_menu_category_no_items);
    }

    public void updateUI(MenuCategory menuCategory){
        cardMenuCategoryImage.setImageResource(R.drawable.breakfast);
        cardMenuCategoryName.setText(menuCategory.getName().get(Localization.defaultLanguage));
        cardMenuCategoryNoItems.setText(String.valueOf(menuCategory.getTotal()));
    }
}
