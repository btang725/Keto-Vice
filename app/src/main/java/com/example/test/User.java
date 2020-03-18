package com.example.test;

public class User {
    public String name;
    public String age;
    public String height;
    public String height2;
    public String weight;
    public String gender;
    public String email;
    public String password;
    public String exer; //string for acitivity level
    public String goal; //string for weight preference

    // Use User.CURRENT to get any needed fields
    public static User CURRENT;

    public User(String name, String age, String height, String height2, String weight, String gender, String email, String password, String goal, String exer){
        this.name = name;
        this.age = age;
        this.height = height; // feet
        this.height2 = height2; // inches
        this.weight = weight;
        this.gender = gender;
        this.email = email;
        this.password = password;

        this.exer = exer;
        this.goal = goal;
    }

    public int getNeededCalories()
    {
        int additional = 0;
        double multiplier = 1.2;
        int height = (Integer.valueOf(this.height) * 12) + Integer.valueOf(this.height2);
        int weight = Integer.valueOf(this.weight);
        int age = Integer.valueOf(this.age);

        if (this.exer == "Moderate")
            multiplier = 1.4;
        if (this.exer == "Active")
            multiplier = 1.7;
        if (this.goal == "Lose Weight")
            additional -= 500;
        if (this.goal == "Gain Weight")
            additional += 500;

        // BMR formula
        // Adult male: 66 + (6.3 x body weight in lbs.) + (12.9 x height in inches) - (6.8 x age in years) = BMR
        // Adult female: 655 + (4.3 x weight in lbs.) + (4.7 x height in inches) - (4.7 x age in years) = BMR

        if(this.gender == "Male")
        {
            return (int)((66 + (6.3 * weight) + (12.9 * height) - (6.8 * age)) * multiplier) + additional;
        }
        else
        {
            return (int)((655 + (4.3 * weight) + (4.7 * height) - (4.7 * age)) * multiplier) + additional;
        }

    }

    public int getNeededFats()
    {
        // Fat intake in grams is 30% of calories divided by 9.
        return (int)((getNeededCalories() * 0.3) / 9);
    }

    public int getNeededCarbs()
    {
        // Limit to 20 grams per day
        return 20;
    }

    public int getNeededProtein()
    {
        // Protein intake in 0.8 grams per pound of body weight.
        return (int)(0.8 * Integer.valueOf(this.weight));
    }
}
