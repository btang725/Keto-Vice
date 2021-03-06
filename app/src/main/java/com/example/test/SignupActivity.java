package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    EditText et_name;
    EditText et_age;
    EditText et_height;
    EditText et_height2;
    EditText et_weight;
    EditText et_mail;
    EditText et_pass;

    //Dropdown Creation
    Spinner spinner_s;
    String[] gender = {"Male", "Female"};
    ArrayAdapter<String>arrayAdapter;

    Spinner spinner_g;
    String[] goals = {"Lose Weight", "Maintain Weight", "Gain Weight"};
    ArrayAdapter<String> goalAdapter;

    Spinner spinner_e;
    String[] exer = {"Sedentary", "Moderate", "Active"};
    ArrayAdapter<String> exerAdapter;

    //Database
    private DatabaseReference myRef;    //Testing a reference for Firebase
    private FirebaseDatabase fbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Declaring Variables
        et_name = (EditText)findViewById(R.id.firstLast);
        et_age = (EditText)findViewById(R.id.age);
        et_height = (EditText)findViewById(R.id.height);
        et_height2 = (EditText)findViewById(R.id.height2);
        et_weight = (EditText)findViewById(R.id.weight);
        et_mail = (EditText)findViewById(R.id.email);
        et_pass = (EditText)findViewById(R.id.password);
        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference("Users"); //Testing Firebase

        //Dropdown Strings
        spinner_s = (Spinner)findViewById(R.id.gender_dropdown);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender);
        spinner_s.setAdapter(arrayAdapter);

        spinner_g = (Spinner)findViewById(R.id.goal_dropdown);
        goalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,goals);
        spinner_g.setAdapter(goalAdapter);

        spinner_e = (Spinner)findViewById(R.id.exer_dropdown);
        exerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,exer);
        spinner_e.setAdapter(exerAdapter);

        //Sign Up Button
        Button btnSignUp = (Button) findViewById(R.id.signup_button);
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String height2 = et_height2.getText().toString();
                String weight = et_weight.getText().toString();
                String mail = et_mail.getText().toString().replace('.', ',');
                String pass = et_pass.getText().toString();
                //gender, goal, exercise level --> string
                String gd = spinner_s.getSelectedItem().toString();
                String go = spinner_g.getSelectedItem().toString();
                String ex = spinner_e.getSelectedItem().toString();

                if(et_pass.getText().length() < 5)
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
                    writeNewUser(name, age, height, height2, weight, gd, mail, pass, go, ex);
                    startActivity(new Intent(SignupActivity.this, Home.class));
                }
            }
        });
    }

    //this function does a basic write (creating new user) to firebase db
    private void writeNewUser(String name, String age, String height, String height2, String weight, String gd, String mail, String pass, String goal, String exer){
        User u = new User(name, age, height, height2, weight, gd, mail, pass, goal, exer);
        User.CURRENT = u;
        HashMap <String, String> temp = new HashMap<>();
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
