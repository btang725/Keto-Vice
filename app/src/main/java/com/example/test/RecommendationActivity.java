package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecommendationActivity extends AppCompatActivity {
    Button buttonDummy;
    TextView txtJson;
    fdaJSONRetriever fda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        buttonDummy = (Button) findViewById(R.id.buttonDummy);
        Toast.makeText(getApplicationContext(), "I fear nothing is here. Better bust that rear before I get near.", Toast.LENGTH_SHORT).show();

        buttonDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You clicked a button, congratulations!", Toast.LENGTH_SHORT).show();
                System.out.println(txtJson);
            }
        });
    }


}
