package com.waibleapp.waible.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.adapters.MenuCategoryAdapter;
import com.waibleapp.waible.model.MenuCategory;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private final String TAG = "MenuFragment";
    private OnMenuFragmentInteractionListener mListener;

    private MenuCategoryAdapter menuCategoryAdapter;

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (!actionBar.isShowing()){
            actionBar.show();
            actionBar.setTitle("LA MAMA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        List<MenuCategory> menuCategoryList = new ArrayList<>();

        menuCategoryList.add(new MenuCategory("Breakfast", 10, "breakfast"));
        menuCategoryList.add(new MenuCategory("Grill", 8, "grill"));
        menuCategoryList.add(new MenuCategory("Breakfast", 10, "breakfast"));
        menuCategoryList.add(new MenuCategory("Grill", 8, "grill"));
        menuCategoryList.add(new MenuCategory("Breakfast", 10, "breakfast"));
        menuCategoryList.add(new MenuCategory("Grill", 8, "grill"));

        menuCategoryAdapter = new MenuCategoryAdapter(menuCategoryList);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_category);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(1));
        recyclerView.setAdapter(menuCategoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMenuFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuFragmentInteractionListener) {
            mListener = (OnMenuFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMenuFragmentInteractionListener {
        void onMenuFragmentInteraction(Uri uri);
    }
}

class VerticalSpaceItemDecorator extends RecyclerView.ItemDecoration {

    private  final int spacer;

    public VerticalSpaceItemDecorator(int spacer) {
        this.spacer = spacer;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = spacer;
    }
}
