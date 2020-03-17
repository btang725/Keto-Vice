package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DailyActivity extends AppCompatActivity {
    //Variables
    Button add;
    TextView date, calories, fats, carbs, proteins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //Declarations
        add = findViewById(R.id.buttonAdd);
        date = findViewById(R.id.textCurrentDay);
        calories = findViewById(R.id.textCalories);
        fats = findViewById(R.id.textCalories);
        carbs = findViewById(R.id.textCalories);
        proteins = findViewById(R.id.textCalories);

        date.setText(bundle.getString("date"));
    }
}
