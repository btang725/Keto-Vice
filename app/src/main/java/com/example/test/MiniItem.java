package com.example.test;
import com.example.test.FoodItem;

public class MiniItem {
    public String name, id;
    public long cal, fat, carb, protein;

    public MiniItem(FoodItem i)
    {
        this.name = i.food_1;
        this.id = i.id_1;
        this.cal = i.cal_1;
        this.carb = i.carb_1;
        this.protein = i.protein_1;
        this.fat = i.fat_1;
    }
}