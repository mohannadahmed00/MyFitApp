package com.example.myfit.activities.models;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MealViewModel extends ViewModel {
    LiveData<String> MealLiveData;
    ArrayList<MealModel> meals;
    public MutableLiveData<ArrayList<MealModel>> mealMutableLiveData =new MutableLiveData<>();

    public void getMeal(){

        mealMutableLiveData.setValue(getMealsFromDB());
    }
    private ArrayList<MealModel> getMealsFromDB(){
        meals=new ArrayList<>();
        meals.add(new MealModel("https://i.pinimg.com/564x/ef/63/dd/ef63ddb07e1ece007b2de75e34e37e79.jpg","Water",true));
        meals.add(new MealModel("https://i.pinimg.com/564x/86/31/ec/8631eca869f628fc162a5bc211940cf9.jpg","Fried Eggs and Avocado",true));
        meals.add(new MealModel("https://i.pinimg.com/564x/0e/7f/b3/0e7fb3fc0e58d925d5de5420c38712f1.jpg","Green Apple",true));
        meals.add(new MealModel("https://i.pinimg.com/564x/0f/a3/ce/0fa3ce738b59efbb405f5753a8e30e16.jpg","Grilled Chicken with Salad",true));
        meals.add(new MealModel("https://i.pinimg.com/564x/a4/ec/01/a4ec01e3080969524e25ee85be218691.jpg","Yoghurt with Fruit and Granola",true));
        return meals;
    }
    /*public void removeMeal(int position){
        meals.get(position).setStatus(false);
        mealMutableLiveData.setValue(meals);
    }*/
}
