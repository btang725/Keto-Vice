package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    //TextViews
    EditText ed_name;
    EditText ed_age;
    EditText ed_height;
    EditText ed_height2;
    EditText ed_weight;
    EditText ed_mail;
    EditText ed_pass;

    //Dropdowns
    Spinner ed_spinner_s;
    String[] ed_gender = {"Male", "Female"};
    ArrayAdapter<String> ed_arrayAdapter;

    Spinner ed_spinner_g;
    String[] ed_goals = {"Lose Weight", "Maintain Weight", "Gain Weight"};
    ArrayAdapter<String> ed_goalAdapter;

    Spinner ed_spinner_e;
    String[] ed_exer = {"Sedentary", "Moderate", "Active"};
    ArrayAdapter<String> ed_exerAdapter;

    //Private Variables
    private DatabaseReference myRef;    //Testing a reference for Firebase
    private FirebaseDatabase fbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //Declaring Variables
        ed_name = (EditText)findViewById(R.id.editFirstLast);
        ed_age = (EditText)findViewById(R.id.editAge);
        ed_height = (EditText)findViewById(R.id.editHeight);
        ed_height2 = (EditText)findViewById(R.id.editHeight2);
        ed_weight = (EditText)findViewById(R.id.editWeight);
        ed_mail = (EditText)findViewById(R.id.editEmail);
        ed_pass = (EditText)findViewById(R.id.editPassword);
        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference("Users");

        ed_spinner_s = (Spinner)findViewById(R.id.editGender_dropdown);
        ed_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ed_gender);
        ed_spinner_s.setAdapter(ed_arrayAdapter);

        ed_spinner_g = (Spinner)findViewById(R.id.editGoal_dropdown);
        ed_goalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ed_goals);
        ed_spinner_g.setAdapter(ed_goalAdapter);

        ed_spinner_e = (Spinner)findViewById(R.id.editExer_dropdown);
        ed_exerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ed_exer);
        ed_spinner_e.setAdapter(ed_exerAdapter);

        //Setting Text
        ed_name.setText(User.CURRENT.name);
        ed_age.setText(User.CURRENT.age);
        ed_height.setText(User.CURRENT.height);
        ed_height2.setText(User.CURRENT.height2);
        ed_weight.setText(User.CURRENT.weight);
        ed_mail.setText(User.CURRENT.email);
        ed_pass.setText(User.CURRENT.password);

        //Setting Spinners
        int pos1 = find(ed_gender, User.CURRENT.gender);
        ed_spinner_s.setSelection(pos1, true);

        int pos2 = find(ed_goals, User.CURRENT.goal);
        ed_spinner_g.setSelection(pos2, true);

        int pos3 = find(ed_exer,User.CURRENT.exer);
        ed_spinner_e.setSelection(pos3, true);

        Log.d("myTag", "Gender: " + User.CURRENT.gender);
        Log.d("myTag", "Goal: " + User.CURRENT.goal);
        Log.d("myTag", "Exer: " + User.CURRENT.exer);
        Log.d("myTag", "P1: " + pos1);
        Log.d("myTag", "P2: "+ pos2);
        Log.d("myTag", "P3: "+ pos3);


        //Update Button
        Button btnSignUp = (Button) findViewById(R.id.update_button);
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = ed_name.getText().toString();
                String age = ed_age.getText().toString();
                String height = ed_height.getText().toString();
                String height2 = ed_height2.getText().toString();
                String weight = ed_weight.getText().toString();
                String mail = ed_mail.getText().toString().replace('.', ',');
                String pass = ed_pass.getText().toString();
                String gd = ed_spinner_s.getSelectedItem().toString();
                String go = ed_spinner_g.getSelectedItem().toString();
                String ex = ed_spinner_e.getSelectedItem().toString();

                if(ed_pass.getText().length() < 5)
                    Toast.makeText(getApplicationContext(), "Password must be at least 5 characters", Toast.LENGTH_LONG).show();
                else if(
                        name.length() == 0
                                || age.length() == 0
                                || height.length() == 0
                                || height2.length() == 0
                                || weight.length() == 0
                                || mail.length() == 0
                )
                {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    editCurrentUser(name, age, height, height2, weight, gd, mail, pass, go, ex);
                    startActivity(new Intent(EditProfileActivity.this, Home.class));
                }
            }
        });
    }

    public static int find(String[] a, String target)
    {
        for (int i = 0; i < a.length; i++)
            if (a[i].equals(target))
                return i;

        return -1;
    }

    private void editCurrentUser(String name, String age, String height, String height2, String weight, String gd, String mail, String pass, String goal, String exer){
        User u = new User(name, age, height, height2, weight, gd, mail, pass, goal, exer);
        myRef.child(User.CURRENT.email).removeValue();
        HashMap<String, String> temp = new HashMap<>();
        User.CURRENT = u;
        temp.put("name", name);
        temp.put("age", age);
        temp.put("height (ft)", height);
        temp.put("height (in)", height2);
        temp.put("weight", weight);
        temp.put("gender", gd);
        temp.put("email", mail);
        temp.put("password", pass);
        temp.put("goal", goal);
        temp.put("activity level", exer);

        myRef.child(mail).setValue(temp);
    }
}

