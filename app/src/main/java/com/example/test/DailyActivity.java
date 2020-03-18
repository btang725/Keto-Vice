package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DailyActivity extends AppCompatActivity {
    //Variables
    Button add;
    SearchView foodSearch;
    TextView date, calories, fats, carbs, proteins, food;
    String txtJson;
    ProgressDialog pd;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference myRef;    //Testing a reference for Firebase
    private FirebaseDatabase fbd;

    private void updateDisplay(DataSnapshot snapshot) {
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
        final String history_child = "Users/" + User.CURRENT.email + "/history/" + select_date.replace('/', '-');

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
                        temp_cals,
                        temp_fats,
                        temp_carbs,
                        temp_proteins,
                        key)
                );

            }
        }

        calories.setText(eaten_cals + " / " + User.CURRENT.getNeededCalories());
        fats.setText(eaten_fats + " / " + User.CURRENT.getNeededFats());
        carbs.setText(eaten_carbs + " / " + User.CURRENT.getNeededCarbs());
        proteins.setText(eaten_proteins + " / " + User.CURRENT.getNeededProtein());

        mAdapter = new HistoryAdapter(foodHistory);
        mRecyclerView.setAdapter(mAdapter);

        final long total_cals = eaten_cals;
        final long total_fats = eaten_fats;
        final long total_carbs = eaten_carbs;
        final long total_proteins = eaten_proteins;

        //For removing via Swipe
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int direction) {
                int position = target.getAdapterPosition();
                FoodItem item = foodHistory.get(position);
                myRef.child(history_child).child(item.getId()).removeValue();
                calories.setText((total_cals - Integer.valueOf(item.getCals())) + " / " + User.CURRENT.getNeededCalories());
                fats.setText((total_fats - Integer.valueOf(item.getFat())) + " / " + User.CURRENT.getNeededFats());
                carbs.setText((total_carbs - Integer.valueOf(item.getCarbs())) + " / " + User.CURRENT.getNeededCarbs());
                proteins.setText((total_proteins - Integer.valueOf(item.getProtein())) + " / " + User.CURRENT.getNeededProtein());
                foodHistory.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

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
                updateDisplay(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String base1 = "https://api.nal.usda.gov/fdc/v1/search?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu&pageNumber=1&generalSearchInput="; //Testable String
                String base2 = "https://api.nal.usda.gov/fdc/v1/";
                String apiKey = "?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu";
                String complete1 = base1 + foodSearch.getQuery().toString().replace(" ", "%20");
                String complete2 = "";

                JSONObject reader;
                String name_to_add = "";
                String id_to_add = "";
                double temporary_cals = 0;
                double temporary_carbs = 0;
                double temporary_fats = 0;
                double temporary_proteins = 0;
                try {

                    String json = new JsonTask().execute(complete1).get(); //Will put JSON text into txtJson variable
                    reader = new JSONObject(json);

                    name_to_add = reader.getString("description");
                    id_to_add = reader.getString("fdcId");
                    temporary_cals = reader.getJSONObject("labelNutrients").getJSONObject("calories").getDouble("value");
                    temporary_carbs = reader.getJSONObject("labelNutrients").getJSONObject("carbohydrates").getDouble("value");;
                    temporary_fats = reader.getJSONObject("labelNutrients").getJSONObject("fat").getDouble("value");
                    temporary_proteins = reader.getJSONObject("labelNutrients").getJSONObject("protein").getDouble("value");
                }
                catch (Exception e)
                {

                }

                final String final_name = name_to_add;
                final String final_id = id_to_add;
                final double final_cals = temporary_cals;
                final double final_carbs = temporary_carbs;
                final double final_fats = temporary_fats;
                final double final_proteins = temporary_proteins;
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
                        final String history_child = "Users/" + User.CURRENT.email + "/history/" + select_date.replace('/', '-');

                        FoodItem entry = new FoodItem(
                                final_name,
                                (long) final_cals,
                                (long) final_fats,
                                (long) final_carbs,
                                (long) final_proteins,
                                final_id
                        );

                        foodHistory.add(entry);
                        myRef.child(history_child).child(entry.getId()).setValue(new MiniItem(entry));

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
                                        temp_cals,
                                        temp_fats,
                                        temp_carbs,
                                        temp_proteins,
                                        key)
                                );
                            }
                        }

                        eaten_cals += final_cals;
                        eaten_carbs += final_carbs;
                        eaten_fats += final_fats;
                        eaten_proteins += final_proteins;


                        calories.setText(eaten_cals + " / " + User.CURRENT.getNeededCalories());
                        fats.setText(eaten_fats + " / " + User.CURRENT.getNeededFats());
                        carbs.setText(eaten_carbs + " / " + User.CURRENT.getNeededCarbs());
                        proteins.setText(eaten_proteins + " / " + User.CURRENT.getNeededProtein());

                        mAdapter = new HistoryAdapter(foodHistory);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(DailyActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                /////////////////////////////////////////////
                /////////////////////////////////////////////
                String base2 = "https://api.nal.usda.gov/fdc/v1/";
                String apiKey = "?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu";
                String json1 = buffer.toString();
                String complete2 = "";

                try {
                    JSONObject JSONReader = new JSONObject(json1);
                    String id = JSONReader.getJSONArray("foods").getJSONObject(0).getString("fdcId");
                    complete2 = base2 + id + apiKey;
                }
                catch (Exception e)
                {

                }

                url = new URL("https://api.nal.usda.gov/fdc/v1/415048?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();
                line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            txtJson = result;
        }
    }
}
