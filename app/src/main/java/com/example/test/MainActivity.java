package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView title, logo;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        Button buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
          
        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        Button buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener(){

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

          
        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        title = findViewById(R.id.imageView4);
        logo = findViewById(R.id.imageView3);

        //Animations
        title.setAnimation(topAnim);
        logo.setAnimation(topAnim);
        buttonSignIn.setAnimation(bottomAnim);
        buttonSignUp.setAnimation(bottomAnim);
    }

}

