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
        int height = 0;
        int weight = 0;
        int age = 0;

        if (activityLevel == ActivityLevel.LIGHT)
            additional += 50;
        if (activityLevel == ActivityLevel.HEAVY)
            additional += 150;
        if (weightPreference == WeightPreference.LOSE)
            additional -= 300;
        if (weightPreference == WeightPreference.GAIN)
            additional += 300;

        // BMR formula
        // Adult male: 66 + (6.3 x body weight in lbs.) + (12.9 x height in inches) - (6.8 x age in years) = BMR
        // Adult female: 655 + (4.3 x weight in lbs.) + (4.7 x height in inches) - (4.7 x age in years) = BMR



        return 0;
    }

    public int getNeededFats()
    {
        return 0;
    }

    public int getNeededCarbs()
    {
        return 0;
    }

    public int getNeededProtein()
    {
        return 0;
    }
}
