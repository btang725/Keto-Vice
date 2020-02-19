package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((Button)findViewById(R.id.signup_button)).setText("test");
                db.child("Users").child("0001").child("age").setValue("100");
            }
        });
    }
}
