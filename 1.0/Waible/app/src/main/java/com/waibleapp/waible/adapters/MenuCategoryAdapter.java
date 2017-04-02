package com.waibleapp.waible.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.waibleapp.waible.R;
import com.waibleapp.waible.holders.MenuCategoryViewHolder;
import com.waibleapp.waible.model.MenuCategory;

import java.util.List;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryViewHolder> {

    private Context context;
    private List<MenuCategory> menuCategories;

    private int lastPosition = -1;

    public MenuCategoryAdapter(List<MenuCategory> menuCategories, Context context) {
        this.context = context;
        this.menuCategories = menuCategories;
    }

    @Override
    public MenuCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_category, parent, false);
        return new MenuCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuCategoryViewHolder holder, final int position) {
        final MenuCategory menuCategory = menuCategories.get(position);
        holder.updateUI(menuCategory);
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return menuCategories.size();
    }
}
