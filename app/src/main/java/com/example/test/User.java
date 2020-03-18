package com.example.test;

public class User {

    public String name;
    public String age;
    public String height;
    public String weight;
    public String gender;
    public String email;
    public String password;
    public String goal;
    public String exercise;

    // Use User.CURRENT to get any needed fields
    public static User CURRENT;

    public User(String name, String age, String height, String weight, String gender, String email,
                String password, String goal, String exercise){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.goal = goal;
        this.exercise = exercise;
    }


    //TODO: Not Done
    public int getNeededCalories(String activityLevel, String weightPreference)
    {
        int additional = 0;
        int height = 0;
        int weight = 0;
        int age = 0;

        if (activityLevel == "Sedentary")
            additional += 25;
        if (activityLevel == "Moderate")
            additional += 50;
        if (activityLevel == "Active")
            additional += 150;
        if (weightPreference == "")
            additional -= 300;
        if (weightPreference == "")
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
