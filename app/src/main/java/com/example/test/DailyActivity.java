package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {
    //Variables
    Button add;
    SearchView foodSearch;
    TextView date, calories, fats, carbs, proteins, food;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //Recycler View Items
        final ArrayList<FoodItem> foodHistory = new ArrayList<>();
        foodHistory.add(new FoodItem("Bananas", "69","fat", "carbs", "protein"));         //TEST
        foodHistory.add(new FoodItem("Rocks", "1337","fat", "carbs", "protein"));         //TEST
        foodHistory.add(new FoodItem("Choccy Milk", "420", "fat", "carbs", "protein"));    //TEST
        mRecyclerView = findViewById(R.id.foodHistoryVertical);
        mRecyclerView.setHasFixedSize(true);    //Improves Performance
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HistoryAdapter(foodHistory);
        mRecyclerView.setLayoutManager(mLayoutManager);
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

        //Declarations
        add = findViewById(R.id.buttonAdd);
        foodSearch = findViewById(R.id.searchView); //Search View to work with...
        date = findViewById(R.id.textCurrentDay);
        calories = findViewById(R.id.textCalories);
        fats = findViewById(R.id.textFats);
        carbs = findViewById(R.id.textCarbs);
        proteins = findViewById(R.id.textProteins);
        //food = findViewById(R.id.textSelection);

        //Setting Text
        date.setText(bundle.getString("date"));

        calories.setText(   "Calories:    0 / " + User.CURRENT.getNeededCalories());
        fats.setText(       "Fats:        0 / " + User.CURRENT.getNeededFats());
        carbs.setText(      "Carbs:       0 / " + User.CURRENT.getNeededCarbs());
        proteins.setText(   "Proteins:    0 / " + User.CURRENT.getNeededProtein());
        //food.setText("");
    }
}
