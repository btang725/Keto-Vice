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
    usdaJSONRetriever usda;
    ProgressDialog pd;
    TextView txtJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        //usda = new usdaJSONRetriever();

        buttonDummy = (Button) findViewById(R.id.buttonDummy);
        Toast.makeText(getApplicationContext(), "I fear nothing is here. Better bust that rear before I get near.", Toast.LENGTH_SHORT).show();

        buttonDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You clicked a button, congratulations!", Toast.LENGTH_SHORT).show();
//                usda.getInformation("My query");
//                System.out.println("Gay");
                new JsonTask().execute("https://api.nal.usda.gov/fdc/v1/search?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu&generalSearchInput=keto"); //YOUR URL ADDRESS HERE!!!
                System.out.println("Hey");
                System.out.println(txtJson);

            }
        });
    }
    //Just copy pasted everything below for now...
    private class JsonTask extends AsyncTask<String, String, String> {


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(RecommendationActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            txtJson.setText(result);
        }
    }

}
