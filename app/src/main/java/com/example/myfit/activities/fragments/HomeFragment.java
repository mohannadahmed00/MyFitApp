package com.example.myfit.activities.fragments;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myfit.R;
import com.example.myfit.activities.adapters.MealsListAdapter;
import com.example.myfit.activities.models.MealViewModel;
import com.example.myfit.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    MealViewModel mealViewModel;
    FragmentHomeBinding homeBinding;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        init();
        return homeBinding.getRoot();
    }

    void init() {
        Glide.with(this).load("https://i.pinimg.com/564x/84/8d/37/848d37221be0da5cca04a9cd9441992c.jpg").into(homeBinding.ivUserImgFragmentHome);
        mealViewModel = ViewModelProviders.of(this).get(MealViewModel.class);
        homeBinding.setMealViewModel(mealViewModel);
        homeBinding.rvMealsFragmentHome.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mealViewModel.mealMutableLiveData.observe(getViewLifecycleOwner(), s ->{

                    homeBinding.rvMealsFragmentHome.setAdapter(new MealsListAdapter(requireContext(), s));

                });



        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.trans));

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //mealViewModel.removeMeal(viewHolder.getAdapterPosition());
                //mealViewModel.getMeal();
                /*mealModels.remove(viewHolder.getAdapterPosition());
                mealViewModel.getMeal();*/
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(homeBinding.rvMealsFragmentHome);
    }


}