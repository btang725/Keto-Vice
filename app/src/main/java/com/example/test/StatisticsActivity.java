package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class StatisticsActivity extends AppCompatActivity {
    //Variables
    double BMI;
    EditText weight, height, bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //Declarations
        /*weight.setText("Weight: " + uWeight + " lb");
        height.setText("Weight: " + uWeight + " ft");

        BMI = (703 * uWeight) / (uHeight * uHeight);
        if (BMI < 18.5)
            bmi.setText("BMI: " + uBMI + "\nYou are Underweight\n\nTry eating foods with \nhigher calories");
        else if(BMI <= 24.9)
            bmi.setText("BMI: " + uBMI + "\nYou are Normal weight\n\nYou are doing fantastic!");
        else if(BMI <= 29.9)
            bmi.setText("BMI: " + uBMI + "\nYou are Overweight\n\nTry eating foods with \nless calories");
        else
            bmi.setText("BMI: " + uBMI + "\nYou are Obese\n\nTry consulting a doctor \nto look at your options");*/
    }
}
