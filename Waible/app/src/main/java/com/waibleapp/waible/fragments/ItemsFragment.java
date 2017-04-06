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

import com.waibleapp.waible.R;
import com.waibleapp.waible.adapters.MenuItemsAdapter;
import com.waibleapp.waible.decorators.VerticalSpaceItemDecorator;
import com.waibleapp.waible.listeners.OnFirebaseCompleteListener;
import com.waibleapp.waible.listeners.RecyclerItemClickListener;
import com.waibleapp.waible.model.MenuItem;
import com.waibleapp.waible.service.DatabaseService;
import com.waibleapp.waible.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsFragment extends Fragment {

    private final String LOG_TAG = ItemsFragment.class.getSimpleName();

    private OnItemsFragmentInteractionListener mListener;

    private DatabaseService databaseService;
    private RecyclerView recyclerView;
    private MenuItemsAdapter menuItemAdapter;
    private RelativeLayout menuItemFragmentLoadingPanel;

    private boolean isLoading = true;
    private List<MenuItem> menuItemList;
    private String categoryId;
    private String restaurantId;

    public ItemsFragment() {

    }

    public static ItemsFragment newInstance(String restaurantId, String categoryId) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FragmentArgumentParams.itemsFragmentCategoryIdParam, categoryId);
        args.putString(Constants.FragmentArgumentParams.itemsFragmentRestaurantIdParam, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString(Constants.FragmentArgumentParams.itemsFragmentCategoryIdParam);
            restaurantId = getArguments().getString(Constants.FragmentArgumentParams.itemsFragmentRestaurantIdParam);
        }else {
            categoryId = "";
            restaurantId = "";
        }
        databaseService = DatabaseService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        menuItemList = new ArrayList<>();

        ImageView menuCategoryImageView = (ImageView) view.findViewById(R.id.menu_category_image_view);
        menuCategoryImageView.setImageResource(R.drawable.grill);
        menuItemFragmentLoadingPanel = (RelativeLayout) view.findViewById(R.id.menu_item_fragment_loading_panel);

        getMenuItemsFromDatabase();

        menuItemAdapter = new MenuItemsAdapter(menuItemList, getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_items);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateUI();
        return view;
    }

    private void updateUI(){
        if (isLoading){
            recyclerView.setVisibility(View.GONE);
            menuItemFragmentLoadingPanel.setVisibility(View.VISIBLE);
        }else {
            menuItemFragmentLoadingPanel.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        menuItemAdapter.notifyDataSetChanged();
    }

    private void onItemSelected(String menuItemId) {
        if (mListener != null) {
            mListener.onItemsFragmentItemsSelectedInteraction(menuItemId);
        }
    }

    private void getMenuItemsFromDatabase(){
        databaseService.getMenuItemsFromDatabase(restaurantId, categoryId, new OnFirebaseCompleteListener() {
            @Override
            public void onCompleteSuccessCalback(Object result) {
                List<MenuItem> itemList = ((List<MenuItem>) result);
                menuItemList.clear();
                menuItemList.addAll(itemList);
                isLoading = false;
                updateUI();
            }

            @Override
            public void onCompleteErrorCalback(String message) {
                isLoading = false;
                Log.d(LOG_TAG, message);
                updateUI();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemsFragmentInteractionListener) {
            mListener = (OnItemsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemsFragmentInteractionListener {
        void onItemsFragmentItemsSelectedInteraction(String menuItemId);
    }
}
