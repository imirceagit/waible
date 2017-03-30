package com.waibleapp.waible.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.adapters.MenuCategoryAdapter;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.listeners.OnMenuItemInteractionListener;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.RestaurantMenu;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.services.DatabaseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuFragment extends Fragment implements OnUpdateUIListener {

    private final String TAG = "MenuFragment";
    private OnMenuFragmentInteractionListener mListener;

    private DatabaseService databaseService;
    private MenuCategoryAdapter menuCategoryAdapter;
    private SessionEntity sessionEntity;

    private List<MenuCategory> menuCategories;
    private RestaurantMenu restaurantMenu;

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionEntity = SessionEntity.getInstance();
        databaseService = DatabaseService.getInstance();
        menuCategories = new ArrayList<>();
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null && !actionBar.isShowing()){
            actionBar.show();
            actionBar.setTitle("LA MAMA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        HashMap<String, MenuCategory> map = new HashMap<>();
        HashMap<String, String> names = new HashMap<String, String>();
        names.put("eu", "Soups");
        map.put("dasddas", new MenuCategory(names, 8));
        restaurantMenu = new RestaurantMenu();
        restaurantMenu.setCategories(map);

        databaseService.getMenuForRestaurant(sessionEntity.getRestaurant().getRestaurantId(), new OnCompleteCallback() {
            @Override
            public void onCompleteSuccessCallback(Object result) {
                restaurantMenu = (RestaurantMenu) result;
                updateUI();
            }

            @Override
            public void onCompleteErrorCallback(String result) {
                MainActivity.makeToast(result);
            }
        });

        menuCategories = restaurantMenu.getCategoriesAsList();
        menuCategoryAdapter = new MenuCategoryAdapter(menuCategories);
        menuCategoryAdapter.addMenuItemInteractionListener(new OnMenuItemInteractionListener() {
            @Override
            public void onMenuItemInteraction(MenuCategory menuCategory, int position) {
                onItemSelected(menuCategory, position);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_category);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(1));
        recyclerView.setAdapter(menuCategoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void updateUI(){
        Log.v(TAG, "PREV == UPDATE ===== " + menuCategories);
        menuCategories = restaurantMenu.getCategoriesAsList();
        Log.v(TAG, "UPDATE ===== " + menuCategories);
        menuCategoryAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(MenuCategory menuCategory, int position) {
        if (mListener != null) {
            mListener.onMenuFragmentInteraction(menuCategory, position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.mainActivity.addOnUpdateUIListeners(this);
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
        MainActivity.mainActivity.removeOnUpdateUIListeners(this);
        mListener = null;
    }

    @Override
    public void onUpdateUIListener() {
        updateUI();
    }

    public interface OnMenuFragmentInteractionListener {
        void onMenuFragmentInteraction(MenuCategory menuCategory, int position);
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
