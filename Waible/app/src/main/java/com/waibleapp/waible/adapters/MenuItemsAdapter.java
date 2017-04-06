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
 * Created by mircea.ionita on 4/3/2017.
 */

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemViewHolder> {

    private Context context;
    private List<MenuItem> list;

    public MenuItemsAdapter(List<MenuItem> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_item, parent, false);
        return new MenuItemViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        MenuItem menuItem = list.get(position);
        holder.updateUI(menuItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
