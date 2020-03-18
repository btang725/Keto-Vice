package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class DailyActivity extends AppCompatActivity {
    //Variables
    Button add;
    SearchView foodSearch;
    TextView date, calories, fats, carbs, proteins, food;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference myRef;    //Testing a reference for Firebase
    private FirebaseDatabase fbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference();

        //Recycler View Items
        final ArrayList<FoodItem> foodHistory = new ArrayList<>();//TEST
        mRecyclerView = findViewById(R.id.foodHistoryVertical);
        mRecyclerView.setHasFixedSize(true);    //Improves Performance
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HistoryAdapter(foodHistory);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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

        calories.setText(   "0 / " + User.CURRENT.getNeededCalories());
        fats.setText(       "0 / " + User.CURRENT.getNeededFats());
        carbs.setText(      "0 / " + User.CURRENT.getNeededCarbs());
        proteins.setText(   "0 / " + User.CURRENT.getNeededProtein());

        // Get initial needed calorie and macro nutrients
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FoodItem> foodHistory = new ArrayList<>();

                String food_name = "";
                long temp_cals = 0;
                long temp_fats = 0;
                long temp_carbs = 0;
                long temp_proteins = 0;

                long eaten_cals = 0;
                long eaten_fats = 0;
                long eaten_carbs = 0;
                long eaten_proteins = 0;

                String select_date = date.getText().toString();
                String history_child = "Users/" + User.CURRENT.email + "/history/" + select_date.replace('/', '-');

                if(snapshot.hasChild(history_child)) {
                    Iterator<DataSnapshot> data_iter = snapshot.child(history_child).getChildren().iterator();
                    DataSnapshot data;

                    while (data_iter.hasNext()) {
                        data = data_iter.next();
                        String key = data.getKey();

                        food_name = (String) snapshot.child(history_child).child(key).child("name").getValue();
                        temp_cals += (long) snapshot.child(history_child).child(key).child("cal").getValue();
                        temp_carbs += (long) snapshot.child(history_child).child(key).child("carb").getValue();
                        temp_fats += (long) snapshot.child(history_child).child(key).child("fat").getValue();
                        temp_proteins += (long) snapshot.child(history_child).child(key).child("protein").getValue();

                        eaten_cals += temp_cals;
                        eaten_fats += temp_fats;
                        eaten_carbs += temp_carbs;
                        eaten_proteins += temp_proteins;

                        foodHistory.add(new FoodItem(
                                food_name,
                                String.valueOf(temp_cals),
                                String.valueOf(temp_fats),
                                String.valueOf(temp_carbs),
                                String.valueOf(temp_proteins))
                        );

                    }
                }

                calories.setText(   "Calories:    " + eaten_cals + " / " + User.CURRENT.getNeededCalories());
                fats.setText(       "Fats:        " + eaten_fats + " / " + User.CURRENT.getNeededFats());
                carbs.setText(      "Carbs:       " + eaten_carbs + " / " + User.CURRENT.getNeededCarbs());
                proteins.setText(   "Proteins:    " + eaten_proteins + " / " + User.CURRENT.getNeededProtein());

                mAdapter = new HistoryAdapter(foodHistory);
                mRecyclerView.setAdapter(mAdapter);

                //For removing via Swipe
                ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder target, int direction) {
                        int position = target.getAdapterPosition();
                        foodHistory.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                helper.attachToRecyclerView(mRecyclerView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println("BUTTON WORKS");
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
