package com.waibleapp.waible.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.waibleapp.waible.R;
import com.waibleapp.waible.model.MenuItem;
import com.waibleapp.waible.utils.Localization;

/**
 * Created by mircea.ionita on 4/3/2017.
 */

public class MenuItemViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    private ImageView cardMenuItemImageView;
    private TextView cardMenuItemName;
    private TextView cardMenuItemPrice;

    public MenuItemViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        cardMenuItemImageView = (ImageView) itemView.findViewById(R.id.card_menu_item_image_view);
        cardMenuItemName = (TextView) itemView.findViewById(R.id.card_menu_item_name);
        cardMenuItemPrice = (TextView) itemView.findViewById(R.id.card_menu_item_price);
    }

    public void updateUI(MenuItem menuItem){
        if (menuItem.getImageURL() != null){
            Picasso.with(context).load(menuItem.getImageURL()).into(cardMenuItemImageView);
        }
        cardMenuItemName.setText(menuItem.getName().get(Localization.defaultLanguage));
        cardMenuItemPrice.setText(String.valueOf(menuItem.getPrice()));
    }

}
