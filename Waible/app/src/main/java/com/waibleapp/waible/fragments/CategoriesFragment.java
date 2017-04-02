package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.adapters.CategoryAdapter;
import com.waibleapp.waible.decorators.VerticalSpaceItemDecorator;
import com.waibleapp.waible.listeners.OnFirebaseCompleteListener;
import com.waibleapp.waible.listeners.RecyclerItemClickListener;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.RestaurantMenu;
import com.waibleapp.waible.service.DatabaseService;
import com.waibleapp.waible.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private final String LOG_TAG = CategoriesFragment.class.getSimpleName();

    private DatabaseService databaseService;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    private List<MenuCategory> menuCategories;
    private RelativeLayout menuFragmentLoadingPanel;
    private ActionBar actionBar;

    private String restaurantId;
    private boolean isLoading = true;

    private OnCategoriesFragmentInteractionListener mListener;

    public CategoriesFragment() {

    }

    public static CategoriesFragment newInstance(String restaurantId){
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FragmentArgumentParams.categoriesFragmentRestaurantIdParam, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantId = getArguments().getString(Constants.FragmentArgumentParams.categoriesFragmentRestaurantIdParam);
        }else {
            restaurantId = "";
        }
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        menuCategories = new ArrayList<>();
        databaseService = DatabaseService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        menuFragmentLoadingPanel = (RelativeLayout) view.findViewById(R.id.menu_fragment_loading_panel);

        getRestaurantMenuFromDatabase();

        categoryAdapter = new CategoryAdapter(menuCategories);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu_category);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                onItemSelected(menuCategories.get(position).getCategoryId());
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new VerticalSpaceItemDecorator(1));
        recyclerView.setAdapter(categoryAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

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
        categoryAdapter.notifyDataSetChanged();
    }

    private void getRestaurantMenuFromDatabase(){
        databaseService.getRestaurantMenuFromDatabase(restaurantId, new OnFirebaseCompleteListener() {
            @Override
            public void onCompleteSuccessCalback(Object result) {
                ((RestaurantMenu) result).cloneCategoryList(menuCategories);
                isLoading = false;
                updateUI();
            }

            @Override
            public void onCompleteErrorCalback(String message) {
                Log.d(LOG_TAG, message);
            }
        });
    }

    private void onItemSelected(String categoryId) {
        if (mListener != null) {
            mListener.onCategoriesFragmentItemSelectedInteraction(categoryId);
        }
    }

    @Override
    public void onStart() {
        if (actionBar != null && !actionBar.isShowing()){
            actionBar.show();
        }
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoriesFragmentInteractionListener) {
            mListener = (OnCategoriesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategoriesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCategoriesFragmentInteractionListener {
        void onCategoriesFragmentItemSelectedInteraction(String categoryId);
    }
}
