package com.example.test.ui.login;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;

import com.example.test.Home;
import com.example.test.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference myRef;    //Testing a reference for Firebase
    private FirebaseDatabase fbd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        final Button loginButton = findViewById(R.id.login);

        fbd = FirebaseDatabase.getInstance();
        myRef = fbd.getReference("Users");

        loginButton.setEnabled(true);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String email = usernameEditText.getText().toString().replace('.', ',');;
                        String pass = passwordEditText.getText().toString();
                        if (snapshot.hasChild(email))
                        {
                            String other_pass = ((String) snapshot.child(email).child("password").getValue());
                            if(other_pass.equals(pass))
                            {
                                User.CURRENT = new User( // TODO FIX THIS
                                        (String) snapshot.child(email).child("name").getValue(),
                                        (String) snapshot.child(email).child("age").getValue(),
                                        (String) snapshot.child(email).child("height (ft)").getValue(),
                                        (String) snapshot.child(email).child("height (in)").getValue(),
                                        (String) snapshot.child(email).child("weight").getValue(),
                                        (String) snapshot.child(email).child("gender").getValue(),
                                        (String) snapshot.child(email).child("email").getValue(),
                                        (String) snapshot.child(email).child("password").getValue(),
                                        (String) snapshot.child(email).child("goal").getValue(),
                                        (String) snapshot.child(email).child("activity level").getValue()
                                );

                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Unknown email or pass", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Unknown email or pass", Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
