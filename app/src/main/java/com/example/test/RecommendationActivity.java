package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;


public class RecommendationActivity extends AppCompatActivity {
    //Variables
    Button buttonDummy;
    private DatabaseReference myRef;    //Testing a reference for Firebase
    private FirebaseDatabase fbd;
    TextView foodName, calories, carbs, fat, protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        buttonDummy = (Button) findViewById(R.id.buttonDummy);

        //Declarations
        foodName = findViewById(R.id.foodName);
        calories = findViewById(R.id.rCals);
        carbs = findViewById(R.id.rCarbs);
        fat = findViewById(R.id.rFat);
        protein = findViewById(R.id.rProtein);

        //Setting Text
        foodName.setText("Click Below!");

        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference(); //Testing Firebase

        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        final String date = String.format("%d-%d-%d", today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));

        buttonDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String history_child = "Users/" + User.CURRENT.email + "/history/" + date;
                        int currentCalories = User.CURRENT.getNeededCalories();

                        if(snapshot.hasChild(history_child))
                        {
                            Iterator<DataSnapshot> data_iter = snapshot.child(history_child).getChildren().iterator();
                            DataSnapshot data;
                            int i = 0;

                            while(data_iter.hasNext())
                            {
                                data = data_iter.next();
                                String key = data.getKey();

                                currentCalories -= (Long) snapshot.child(history_child).child(key).child("cal").getValue();
                            }
                        }
                        Iterator<DataSnapshot> rec_iter = snapshot.child("Recommendations").getChildren().iterator();
                        DataSnapshot foods;

                        String bestMatchID = null;
                        int minCals = 0;

                        while(rec_iter.hasNext())
                        {
                            foods = rec_iter.next();
                            String key = foods.getKey();

                            if(bestMatchID != null)
                            {
                                long foundCals = (Long) snapshot.child("Recommendations").child(key).child("cal").getValue();
                                int temp = Math.abs(currentCalories - ((int) foundCals));

                                if(temp < minCals)
                                {
                                    minCals = temp;
                                    bestMatchID = key;
                                }
                            }
                            else
                            {
                                long foundCals = (Long) snapshot.child("Recommendations").child(key).child("cal").getValue();
                                bestMatchID = key;
                                minCals = Math.abs(currentCalories - ((int) foundCals));;
                            }
                        }

                        System.out.println((String) snapshot.child("Recommendations").child(bestMatchID).child("name").getValue());
                        System.out.println(String.valueOf(minCals));

                        foodName.setText(
                                String.format("%s",
                                        (String) snapshot.child("Recommendations").child(bestMatchID).child("name").getValue()
                                        )
                        );
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
