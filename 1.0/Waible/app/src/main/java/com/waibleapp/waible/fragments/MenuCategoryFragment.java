package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.adapters.MenuItemAdapter;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.listeners.RecyclerItemClickListener;
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.MenuItem;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoryFragment extends Fragment implements OnUpdateUIListener {

    private final String TAG = "MenuCategoryFragment";
    private OnMenuCategoryFragmentInteractionListener mListener;

    private Gson gson = new Gson();
    private MenuCategory menuCategory;
    private int position = 0;

    private DatabaseService databaseService;
    private SessionEntity sessionEntity;
    private MenuItemAdapter menuItemAdapter;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;
    private RelativeLayout menuItemFragmentLoadingPanel;

    private boolean isLoading = true;
    private List<MenuItem> menuItemList;

    public MenuCategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menuCategory = gson.fromJson(getArguments().getString(Constants.MenuCategoryBundleParams.menuCategoryParam), MenuCategory.class);
            Log.v(TAG, menuCategory.toString());
            position = getArguments().getInt(Constants.MenuCategoryBundleParams.positionParam);
            Log.v(TAG, String.valueOf(position));
        }
        databaseService = DatabaseService.getInstance();
        sessionEntity = SessionEntity.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_category, container, false);

        menuItemFragmentLoadingPanel = (RelativeLayout) view.findViewById(R.id.menu_item_fragment_loading_panel);

        ImageView menuCategoryImageView = (ImageView) view.findViewById(R.id.menu_category_image_view);
        menuCategoryImageView.setImageResource(R.drawable.grill);

        menuItemList = new ArrayList<>();

        getMenuItemsForCategory(sessionEntity.getRestaurant().getRestaurantId(), menuCategory.getCategoryId());

        menuItemAdapter = new MenuItemAdapter(menuItemList, getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_item_category);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(1));
        recyclerView.setAdapter(menuItemAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void updateUI(){
        if (isLoading){
            recyclerView.setVisibility(View.GONE);
            menuItemFragmentLoadingPanel.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            menuItemFragmentLoadingPanel.setVisibility(View.GONE);
        }
        menuItemAdapter.notifyDataSetChanged();
    }

    private void getMenuItemsForCategory(String restaurantId, String categoryId){
        databaseService.getMenuItemsForCategory(restaurantId, categoryId, new OnCompleteCallback() {
            @Override
            public void onCompleteSuccessCallback(Object result) {
                isLoading = false;
                List<MenuItem> list = (List<MenuItem>) result;
                menuItemList.addAll(list);
                Log.v(TAG, "menuItemList" + menuItemList.toString());
                updateUI();
            }

            @Override
            public void onCompleteErrorCallback(String result) {
                isLoading = false;
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                updateUI();
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMenuCategoryFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).addOnUpdateUIListeners(this);
        if (context instanceof OnMenuCategoryFragmentInteractionListener) {
            mListener = (OnMenuCategoryFragmentInteractionListener) context;
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

    public interface OnMenuCategoryFragmentInteractionListener {
        void onMenuCategoryFragmentInteraction(Uri uri);
    }
}
