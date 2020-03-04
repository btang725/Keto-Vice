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

public class SignupActivity extends AppCompatActivity {

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

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // Go to Home Class after clicking "Sign Up"
        //TODO: go to recommendation instead
        Button btn1 = (Button) findViewById(R.id.signup_btn);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SignupActivity.this, Home.class));

                EditText et_name = (EditText)findViewById(R.id.editText);
                EditText et_age = (EditText)findViewById(R.id.editText2);
                EditText et_height = (EditText)findViewById(R.id.editText3);
                EditText et_weight = (EditText)findViewById(R.id.editText4);
                EditText et_mail = (EditText)findViewById(R.id.editText5);
                EditText et_pass = (EditText)findViewById(R.id.editText6);

                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                String mail = et_mail.getText().toString();
                String pass = et_pass.getText().toString();

                String gd = spinner_s.getSelectedItem().toString();

//                 TODO: Fix this .. does userID need to be a String?
//                private void writeNewUser(int userID, String name, String age, String height, String weight, String gd, String mail, String pass){
                    int userID = 0;
                    User u = new User(userID, name, age, height, weight, gd, mail, pass);
                    mDatabase.child("users").child(userID).setValue(u);
                    userID = userID + 1;
//                }
            }
        });



    }
}
