package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
    //Variables
    double BMI, BMR, caloricNeeds;
    int ftHeight, inHeight, inchesH, iWeight;
    TextView weight, height, bmi, bmr, caloricN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //Declarations
        weight = findViewById(R.id.textMyWeight);
        height = findViewById(R.id.textMyHeight);
        bmi = findViewById(R.id.textMyBMI);
        bmr = findViewById(R.id.textMyBMR);
        caloricN = findViewById(R.id.textMyCaloricNeeds);
        ftHeight = Integer.parseInt(User.CURRENT.height);
        inHeight = Integer.parseInt(User.CURRENT.height2);
        iWeight = Integer.parseInt(User.CURRENT.weight);
        inchesH = ftHeight*12 + inHeight;

        weight.setText("Weight: " + User.CURRENT.weight + " lb");
        height.setText("Height: " + User.CURRENT.height + " ft");

        //Calculating BMI
        BMI = (703 * iWeight) / (inchesH * inchesH);

        //Calculating BMR
        if(User.CURRENT.gender.equals("Male"))
            BMR = 66 + (6.3 * iWeight) + (12.9 * inchesH) - (6.8 *Integer.parseInt(User.CURRENT.age));
        else
            BMR = 655 + (4.3 * iWeight) + (4.7 * inchesH) - (4.7 *Integer.parseInt(User.CURRENT.age));

        //Calculating caloric needs
        if (User.CURRENT.exer.equals("Sedentary"))
            caloricNeeds = BMR * 1.2;
        else if (User.CURRENT.exer.equals("Moderately Active"))
            caloricNeeds = BMR * 1.55;
        else
            caloricNeeds = BMR * 1.725;

        //Setting BMI
        if (BMI < 18.5)
            bmi.setText("BMI: " + BMI + "\nYou are Underweight");
        else if(BMI <= 24.9)
            bmi.setText("BMI: " + BMI + "\nYou are Normal weight");
        else if(BMI <= 29.9)
            bmi.setText("BMI: " + BMI + "\nYou are Overweight");
        else
            bmi.setText("BMI: " + BMI + "\nYou are Obese");

        //Setting BMR
        bmr.setText("BMR: " + BMR);

        //Setting Caloric Need
        caloricN.setText("Consume " + caloricNeeds +" calories a day to " + User.CURRENT.goal.toLowerCase() + ".");
    }
}
