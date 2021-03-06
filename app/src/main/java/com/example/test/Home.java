package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private String email;
    private TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        //email = bundle.getString("email");
        welcomeMsg = findViewById(R.id.welcomeMsg);
        welcomeMsg.setText("Welcome " + User.CURRENT.name + "!");

        // Restaurant
        findViewById(R.id.restaurant).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri intentURI = Uri.parse("geo:0,0?q=keto%20friendly%20restaurants%20near%20me");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentURI);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
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

        // Edit Profile
        findViewById(R.id.edit_profile).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Home.this, EditProfileActivity.class));
            }
        });

        // My Statistics   buttonStats
        findViewById(R.id.buttonStats).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Home.this, StatisticsActivity.class));
            }
        });
    }
}
