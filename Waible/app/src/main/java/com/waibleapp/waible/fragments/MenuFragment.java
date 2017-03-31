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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.adapters.MenuCategoryAdapter;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.listeners.RecyclerItemClickListener;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.RestaurantMenu;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.services.DatabaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuFragment extends Fragment implements OnUpdateUIListener {

    private final String TAG = "MenuFragment";
    private OnMenuFragmentInteractionListener mListener;

    private DatabaseService databaseService;
    private MenuCategoryAdapter menuCategoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private SessionEntity sessionEntity;

    private List<MenuCategory> menuCategories;
    private RestaurantMenu restaurantMenu;
    private ActionBar actionBar;

    private RelativeLayout menuFragmentLoadingPanel;

    private boolean isLoading = true;

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionEntity = SessionEntity.getInstance();
        databaseService = DatabaseService.getInstance();
        menuCategories = new ArrayList<>();
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        if (actionBar != null && !actionBar.isShowing()){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }

        menuFragmentLoadingPanel = (RelativeLayout) view.findViewById(R.id.menu_fragment_loading_panel);

        HashMap<String, MenuCategory> map = new HashMap<>();
        restaurantMenu = new RestaurantMenu();
        restaurantMenu.setCategories(map);

        databaseService.getMenuCategoriesForRestaurant(sessionEntity.getRestaurant().getRestaurantId(), new OnCompleteCallback() {
            @Override
            public void onCompleteSuccessCallback(Object result) {
                restaurantMenu = (RestaurantMenu) result;
                isLoading = false;
                updateUI();
            }

            @Override
            public void onCompleteErrorCallback(String result) {
                isLoading = false;
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                updateUI();
            }
        });

        menuCategories = restaurantMenu.getCategoriesAsList(menuCategories);
        menuCategoryAdapter = new MenuCategoryAdapter(menuCategories, getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_category);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                onItemSelected(menuCategories.get(position), position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(1));
        recyclerView.setAdapter(menuCategoryAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateUI();
        return view;
    }

    private void updateUI(){
        if (isLoading){
            menuFragmentLoadingPanel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            menuFragmentLoadingPanel.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        menuCategories = restaurantMenu.getCategoriesAsList(menuCategories);
        menuCategoryAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(MenuCategory menuCategory, int position) {
        Log.v(TAG, String.valueOf(position));
        linearLayoutManager.scrollToPositionWithOffset(position, 0);
//        for (MenuCategory m : menuCategories){
//
//        }
        if (mListener != null) {
            mListener.onMenuFragmentInteraction(menuCategory, position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).addOnUpdateUIListeners(this);
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
        ((MainActivity) getActivity()).removeOnUpdateUIListeners(this);
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
