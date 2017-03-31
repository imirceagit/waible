package com.waibleapp.waible.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waibleapp.waible.R;
import com.waibleapp.waible.holders.MenuItemViewHolder;
import com.waibleapp.waible.model.MenuItem;

import java.util.List;

/**
 * Created by mircea.ionita on 3/31/2017.
 */

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemViewHolder> {

    private Context context;
    private List<MenuItem> menuItems;

    public MenuItemAdapter(List<MenuItem> menuItems, Context context) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_item, parent, false);
        return new MenuItemViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        final MenuItem menuItem = (MenuItem) menuItems.get(position);
        holder.updateUI(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
