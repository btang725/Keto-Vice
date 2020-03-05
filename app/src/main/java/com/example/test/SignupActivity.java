package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.ui.login.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import User class HERE
import com.example.test.User;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    //Views
    EditText et_name = (EditText)findViewById(R.id.editText);
    EditText et_age = (EditText)findViewById(R.id.editText2);
    EditText et_height = (EditText)findViewById(R.id.editText3);
    EditText et_weight = (EditText)findViewById(R.id.editText4);
    EditText et_mail = (EditText)findViewById(R.id.editText5);
    EditText et_pass = (EditText)findViewById(R.id.editText6);

    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    Spinner spinner_s;
    String[] gender = {"Male", "Female"};
    ArrayAdapter<String>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        spinner_s = (Spinner)findViewById(R.id.spinner_dropdown);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender);
        spinner_s.setAdapter(arrayAdapter);

        spinner_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Your selection is: " + gender[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Sign Up Button
        Button btn1 = (Button) findViewById(R.id.signup_btn);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                String mail = et_mail.getText().toString();
                String pass = et_pass.getText().toString();

                String gd = spinner_s.getSelectedItem().toString();

                writeNewUser(id, name, age, height, weight, gd, mail, pass);
                //read save-data firebase link to push? to get unique ID

                //TODO: go to recommendation instead of home page
                startActivity(new Intent(SignupActivity.this, Home.class));
            }
        });

    }

    //this function does a basic write (creating new user) to firebase db
    private void writeNewUser(String userID, String name, String age, String height, String weight, String gd, String mail, String pass){
        User u = new User(userID, name, age, height, weight, gd, mail, pass);
        mDatabase.child("users").child(userID).setValue(u);
    }
}
