package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Restaurant
        findViewById(R.id.restaurant).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Home.this, RestaurantActivity.class));
            }
        });

        // Calendar
        findViewById(R.id.calendar).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Home.this, CalendarActivity.class));
            }
        });

        // Recommendation
        findViewById(R.id.recommend).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Home.this, RecommendationActivity.class));
            }
        });
    }
}
