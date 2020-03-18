package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
    //Variables
    double BMI;
    int ftHeight, inHeight, inchesH, iWeight;
    TextView weight, height, bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //Declarations
        weight = findViewById(R.id.textMyWeight);
        height = findViewById(R.id.textMyHeight);
        bmi = findViewById(R.id.textMyBMI);
        ftHeight = Integer.parseInt(User.CURRENT.height);
        inHeight = Integer.parseInt(User.CURRENT.height2);
        iWeight = Integer.parseInt(User.CURRENT.weight);
        inchesH = ftHeight*12 + inHeight;

        weight.setText("Weight: " + User.CURRENT.weight + " lb");
        height.setText("Height: " + User.CURRENT.height + " ft");

        BMI = (703 * iWeight) / (inchesH * inchesH);
        if (BMI < 18.5)
            bmi.setText("BMI: " + BMI + "\nYou are Underweight\n\nTry eating foods with \nhigher calories");
        else if(BMI <= 24.9)
            bmi.setText("BMI: " + BMI + "\nYou are Normal weight\n\nYou are doing fantastic!");
        else if(BMI <= 29.9)
            bmi.setText("BMI: " + BMI + "\nYou are Overweight\n\nTry eating foods with \nless calories");
        else
            bmi.setText("BMI: " + BMI + "\nYou are Obese\n\nTry consulting a doctor \nto look at your options");
    }
}
