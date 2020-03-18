package com.example.test;

public class FoodItem {
    public String food_1, id_1;
    public long cal_1, fat_1, carb_1, protein_1;

    public FoodItem(String foodName, long calCount, long fatCount, long carbsCount, long proteinCount, String foodID) {
        food_1 = foodName;
        cal_1 = calCount;
        fat_1 = fatCount;
        carb_1 = carbsCount;
        protein_1 = proteinCount;
        id_1 = foodID;
    }

    public String getFood() {
        return food_1;
    }

    public String getCals() {
        return String.valueOf(cal_1);
    }

    public String getFat() {
        return String.valueOf(fat_1);
    }

    public String getCarbs() { return String.valueOf(carb_1); }

    public String getProtein() {
        return String.valueOf(protein_1);
    }

    public String getId() {return id_1; }
}
