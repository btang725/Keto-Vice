package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class DailyActivity extends AppCompatActivity {
    //Variables
    Button add;
    SearchView foodSearch;
    TextView date, calories, fats, carbs, proteins, food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //Declarations
        add = findViewById(R.id.buttonAdd);
        foodSearch = findViewById(R.id.searchView); //Search View to work with...
        date = findViewById(R.id.textCurrentDay);
        calories = findViewById(R.id.textCalories);
        fats = findViewById(R.id.textFats);
        carbs = findViewById(R.id.textCarbs);
        proteins = findViewById(R.id.textProteins);

        //Setting Text
        date.setText(bundle.getString("date"));

        calories.setText(   "Calories:    0 / " + User.CURRENT.getNeededCalories());
        fats.setText(       "Fats:        0 / " + User.CURRENT.getNeededFats());
        carbs.setText(      "Carbs:       0 / " + User.CURRENT.getNeededCarbs());
        proteins.setText(   "Proteins:    0 / " + User.CURRENT.getNeededProtein());
    }
}
