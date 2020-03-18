package com.example.test;

public class User {

    public enum WeightPreference {
        LOSE, GAIN, MAINTAIN
    }

    public enum ActivityLevel {
        NONE, LIGHT, HEAVY
    }

    public String name;
    public String age;
    public String height;
    public String weight;
    public String gender;
    public String email;
    public String password;
    public WeightPreference weightPreference;
    public ActivityLevel activityLevel;

    // Use User.CURRENT to get any needed fields
    public static User CURRENT;

    public User(String name, String age, String height, String weight, String gender, String email, String password){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.email = email;
        this.password = password;

        this.weightPreference = WeightPreference.MAINTAIN; // Change this
        this.activityLevel = ActivityLevel.NONE;
    }

    public int getNeededCalories()
    {
        int additional = 0;
        double multiplier = 1.2;

        int height = 72;
        int weight = 160;
        int age = 21;

        if (activityLevel == ActivityLevel.LIGHT)
            multiplier = 1.4;
        if (activityLevel == ActivityLevel.HEAVY)
            multiplier = 1.7;
        if (weightPreference == WeightPreference.LOSE)
            additional -= 500;
        if (weightPreference == WeightPreference.GAIN)
            additional += 500;

        // BMR formula
        // Adult male: 66 + (6.3 x body weight in lbs.) + (12.9 x height in inches) - (6.8 x age in years) = BMR
        // Adult female: 655 + (4.3 x weight in lbs.) + (4.7 x height in inches) - (4.7 x age in years) = BMR

        if(gender == "Male")
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
        int weight = 160;

        // Protein intake in 0.8 grams per pound of body weight.
        return (int)(0.8 * weight);
    }
}
