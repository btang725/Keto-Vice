package com.example.test;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class usdaJSONRetriever extends AppCompatActivity {
    //Variables
    String usdaJson, APIKey;
    String txtJson;
    ProgressDialog pd;

    //Constructor
    usdaJSONRetriever(){
        APIKey = "api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu";
        usdaJson = "";
    }
    //https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu&location=Denver+CO
    //Methods

    //Gets json from query.
    private void getJSON(String query) {
        usdaJson = "https://api.nal.usda.gov/fdc/v1/search?api_key=NbU3jt6cnbykzengF4XfOuLCIRhSaXIfM7hsZOLu&generalSearchInput=keto"; //Testable String
        usdaJson = "https://api.fda.gov/drug/event.json?limit=1";
        //query = "Cheddar%20Cheese"; //example
        query.replace(" ", "%20");  //In case of spaces for correct search results.
        //usdaJson = "https://api.nal.usda.gov/fdc/v1/search?"+APIKey+"&generalSearchInput="+query;
        new JsonTask().execute(usdaJson); //Will put JSON text into txtJson variable

        System.out.println(txtJson);     //Just testing, erase later...
        System.out.println("GAY");
    }

    public String getInformation(String query) {
        getJSON(query);
        return txtJson;
    }

    //JSON reader
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(usdaJSONRetriever.this);
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
            txtJson = result;
        }
    }
}