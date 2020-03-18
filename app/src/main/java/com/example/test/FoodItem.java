package com.example.test;

public class FoodItem {
    private String food, cals, fat, carbs, protein, id;

    public FoodItem(String foodName, String calCount, String fatCount,String carbsCount,String proteinCount, String foodID) {
        food = foodName;
        cals = calCount;
        fat = fatCount;
        carbs = carbsCount;
        protein = proteinCount;
        id = foodID;
    }

    public String getFood() {
        return food;
    }

    public String getCals() {
        return cals;
    }

    public String getFat() {
        return fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getProtein() {
        return protein;
    }

    public String getId() {return id; }
}
