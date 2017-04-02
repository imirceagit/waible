package com.waibleapp.waible.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.waibleapp.waible.R;
import com.waibleapp.waible.holders.CategoryViewHolder;
import com.waibleapp.waible.model.MenuCategory;

import java.util.List;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final String LOG_TAG = CategoryAdapter.class.getSimpleName();

    private List<MenuCategory> list;

    private int lastPosition = -1;

    public CategoryAdapter(List<MenuCategory> list) {
        this.list = list;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.updateUI(list.get(position));
    }

//    private void setAnimation(View viewToAnimate, int position) {
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
