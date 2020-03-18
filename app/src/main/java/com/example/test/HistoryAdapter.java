package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.FoodHistoryViewHolder> {
    private ArrayList<FoodItem> mFoodItemsList;

    public static class FoodHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView food, cals, fat, carbs, protein;

        public FoodHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            food = itemView.findViewById(R.id.textFoodName);
            cals = itemView.findViewById(R.id.textFoodCalories);
            fat = itemView.findViewById(R.id.textFoodFats);
            carbs = itemView.findViewById(R.id.textFoodCarbs);
            protein = itemView.findViewById(R.id.textFoodProteins);
        }
    }

    public HistoryAdapter(ArrayList<FoodItem> foodList) {
        mFoodItemsList = foodList;
    }

    @NonNull
    @Override
    public FoodHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        FoodHistoryViewHolder fhvh = new FoodHistoryViewHolder(v);
        return fhvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHistoryViewHolder holder, int position) {
        FoodItem currentFood = mFoodItemsList.get(position);

        holder.food.setText(currentFood.getFood());
        holder.cals.setText(currentFood.getCals());
        holder.fat.setText(currentFood.getFat());
        holder.carbs.setText(currentFood.getCarbs());
        holder.protein.setText(currentFood.getProtein());
    }

    @Override
    public int getItemCount() {
        return mFoodItemsList.size();
    }
}
