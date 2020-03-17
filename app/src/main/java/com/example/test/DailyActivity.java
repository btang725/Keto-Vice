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
        fats = findViewById(R.id.textCalories);
        carbs = findViewById(R.id.textCalories);
        proteins = findViewById(R.id.textCalories);
        food = findViewById(R.id.textSelection);

        //Setting Text
        date.setText(bundle.getString("date"));

        /*calories.setText("Calories:  0000");
        fats.setText("Fats:         0000");
        carbs.setText("Carbs:      0000");
        proteins.setText("Proteins:  0000");
        food.setText("");*/
    }
}
