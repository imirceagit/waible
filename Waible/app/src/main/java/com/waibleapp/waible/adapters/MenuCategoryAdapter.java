package com.waibleapp.waible.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waibleapp.waible.R;
import com.waibleapp.waible.holders.MenuCategoryViewHolder;
import com.waibleapp.waible.model.MenuCategory;

import java.util.List;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryViewHolder> {

    private List<OnMenuItemInteractionListener> mListeners;

    private List<MenuCategory> menuCategories;

    public MenuCategoryAdapter(List<MenuCategory> menuCategories) {
        this.menuCategories = menuCategories;
    }

    @Override
    public MenuCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_category, parent, false);
        return new MenuCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuCategoryViewHolder holder, int position) {
        final MenuCategory menuCategory = menuCategories.get(position);
        final int currentPosition = position;
        holder.updateUI(menuCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OnMenuItemInteractionListener listener : mListeners){
                    listener.onMenuItemInteraction(menuCategory, currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuCategories.size();
    }

    public void addOnMenuItemInteractionListener(OnMenuItemInteractionListener listener){
        mListeners.add(listener);
    }

    public interface OnMenuItemInteractionListener {
        void onMenuItemInteraction(MenuCategory menuCategory, int position);
    }
}
