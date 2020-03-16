package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class SignupActivity extends AppCompatActivity {
    EditText et_name;
    EditText et_age;
    EditText et_height;
    EditText et_weight;
    EditText et_mail;
    EditText et_pass;
    Spinner spinner_s;
    String[] gender = {"Male", "Female"};
    ArrayAdapter<String>arrayAdapter;
    //Private Variables
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
        et_weight = (EditText)findViewById(R.id.weight);
        et_mail = (EditText)findViewById(R.id.email);
        et_pass = (EditText)findViewById(R.id.password);
        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference("Users"); //Testing Firebase

        spinner_s = (Spinner)findViewById(R.id.gender_dropdown);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gender);
        spinner_s.setAdapter(arrayAdapter);

        //Sign Up Button
        Button btnSignUp = (Button) findViewById(R.id.signup_button);
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String id = myRef.push().getKey();          //Generates random key for database
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                String mail = et_mail.getText().toString().replace('.', ',');
                String pass = et_pass.getText().toString();
                String gd = spinner_s.getSelectedItem().toString();

                if(et_pass.getText().length() < 5)
                    Toast.makeText(getApplicationContext(), "Password must be at least 5 characters", Toast.LENGTH_LONG).show();
                else if(
                    name.length() == 0
                    || age.length() == 0
                    || height.length() == 0
                    || weight.length() == 0
                    || mail.length() == 0
                )
                {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    writeNewUser(id, name, age, height, weight, gd, mail, pass);

                    //TODO: go to recommendation instead of home page
                    startActivity(new Intent(SignupActivity.this, Home.class));
                }
            }
        });
    }

    //this function does a basic write (creating new user) to firebase db
    private void writeNewUser(String userID, String name, String age, String height, String weight, String gd, String mail, String pass){
        User u = new User(userID, name, age, height, weight, gd, mail, pass);
        myRef.child(mail).setValue(u);

        System.out.println("very cool");
    }
}
