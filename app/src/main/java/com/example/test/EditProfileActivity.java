package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//IMPLEMENT CORRECTLY
public class EditProfileActivity extends AppCompatActivity {
    EditText ed_name;
    EditText ed_age;
    EditText ed_height;
    EditText ed_weight;
    EditText ed_mail;
    EditText ed_pass;
    Spinner ed_spinner_s;
    String[] ed_gender = {"Male", "Female"};
    ArrayAdapter<String> my_arrayAdapter;
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
        ed_weight = (EditText)findViewById(R.id.editWeight);
        ed_mail = (EditText)findViewById(R.id.editEmail);
        ed_pass = (EditText)findViewById(R.id.editPassword);
        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference("Users");

        ed_spinner_s = (Spinner)findViewById(R.id.editGender_dropdown);
        my_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ed_gender);
        ed_spinner_s.setAdapter(my_arrayAdapter);

        //Sign Up Button
        Button btnSignUp = (Button) findViewById(R.id.update_button);
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String id = myRef.push().getKey();          //Generates random key for database
                String name = ed_name.getText().toString();
                String age = ed_age.getText().toString();
                String height = ed_height.getText().toString();
                String weight = ed_weight.getText().toString();
                String mail = ed_mail.getText().toString();
                String pass = ed_pass.getText().toString();
                String gd = ed_spinner_s.getSelectedItem().toString();

                if(ed_pass.getText().length() < 5)
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
                    editCurrentUser(id, name, age, height, weight, gd, mail, pass);

                    //TODO: go to recommendation instead of home page
                    startActivity(new Intent(EditProfileActivity.this, Home.class));
                }
            }
        });
    }

    //FIX THIS SHITTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
    //this function does a basic write (creating new user) to firebase db
    private void editCurrentUser(String userID, String name, String age, String height, String weight, String gd, String mail, String pass){
        User u = new User(userID, name, age, height, weight, gd, mail, pass);
        myRef.child(mail).setValue(u);

        System.out.println("very cool");
    }
}

