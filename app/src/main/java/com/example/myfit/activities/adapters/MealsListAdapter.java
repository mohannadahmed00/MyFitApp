package com.example.myfit.activities.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfit.R;
import com.example.myfit.activities.models.MealModel;

import java.util.ArrayList;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.ViewHolder> {


    ArrayList<MealModel> list;
    Context context;

    public MealsListAdapter(@NonNull Context context, @NonNull ArrayList<MealModel> objects) {
        list=new ArrayList<>();
        for (MealModel meal : objects) {
            if (meal.isStatus()) {
                this.list.add(meal);
            }
        }
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMeal(list.get(position));
        MealModel meal = list.get(position);
        Glide.with(context).load(meal.getImgURL()).into(holder.ivMealImg);
        holder.tvMealName.setText(meal.getName());
        holder.tvMealDes.setText(meal.getDes());
    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private final ImageView ivMealImg;
        private final TextView tvMealName, tvMealDes;
        MealModel meal;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.ivMealImg = view.findViewById(R.id.iv_image_meal);
            this.tvMealName = view.findViewById(R.id.tv_name_meal);
            this.tvMealDes = view.findViewById(R.id.tv_des_meal);
            view.setOnLongClickListener(this);
        }

        public void setMeal(MealModel meal){
            this.meal=meal;
        }



        @Override
        public boolean onLongClick(View view) {
            Dialog dialog;
            ImageView ivMeal;


            dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.meal);
            ivMeal=dialog.findViewById(R.id.iv_image_meal);

            Glide.with(view.getContext()).load(meal.getImgURL()).into(ivMeal);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            return false;
        }
    }
}
