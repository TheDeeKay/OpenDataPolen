package com.example.makina.polen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    /*
    Za test potrebe
     */
    static TextView text = null;

    //Sluzi da prati offset od danasnjeg datuma (pri pregledu podataka)
    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        Uzmi intent, uzmi extra koji dolazi kao offset, promeni trenutni offset
         */
        Intent intent = getIntent();

        int j = intent.getIntExtra("Dan offset", 0);
        i = i-j;

        text = (TextView) findViewById(R.id.textview);

        FetchData fetch = new FetchData();
        fetch.execute();

    }



    /*
    onClick metod za Biljke dugme, pokrece BiljkeActivity
     */
    public void clickBiljke(View view){

        Intent intent = new Intent(this, BiljkeActivity.class);

        startActivity(intent);
    }

    /*
    onClick metod za Levo dugme, pokrece main activity, menja offset za -1
     */
    /*public void clickLevoButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Dan offset", -1);

        startActivity(intent);
    }*/

    /*
    onClick metod za Desno dugme, pokrece main activity, menja offset za +1
     */
    /*public void clickDesnoButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Dan offset", -1);

        startActivity(intent);
    }*/

    /*
    onClick metod za Danas dugme, pokrece main activity, vraca offset na 0
     */




    /*
    Za test potrebe, kao ispis
     */
    public static void setIt(String[] string){


        StringBuffer res = new StringBuffer();

        for(String s : string) res.append(s);
        text.setText(res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    Async Task za fetchovanje JSON data
     */
    public static class FetchData extends AsyncTask<String, Void, String>{

        private String[] getDataFromJson(String JsonStr)
                throws JSONException {

            /*
            Mora da se deklarise van try bloka da bi mogao da se koristi u return-u
             */
            String rezultat[] = null;

            try {

                /*
                Parsiraj result, pa records da bi dobio entryje sa kolonama
                 */
                JSONObject Json = new JSONObject(JsonStr);
                JSONObject result = Json.getJSONObject("result");
                JSONArray records = result.getJSONArray("records");

                rezultat = new String[20];
                for(int i = 0; i < records.length(); i++) {

                    //Kolone koje posle sklapamo u rezultat
                    String datum;
                    String tendencija;
                    String koncentracija;

                    JSONObject dan = records.getJSONObject(i);

                    datum = dan.getString("DATUM");
                    tendencija = dan.getString("TENDENCIJA");
                    koncentracija = dan.getString("KONCENTRACIJA");

                    rezultat[i] = "Datum: "+datum+"\nTendencija: "+tendencija+
                            "\nKoncentracija: "+koncentracija+"\n";
                }

            } catch(JSONException e){
                Log.v("getDataFromJson", "Error", e);
                return null;
            }


            return rezultat;

        }

        String JsonString = null;


        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            try {
                //Konstruisi URL za konekciju na sajt agencije
                String baseUrl = "http://data.sepa.gov.rs/api/action/datastore/search.json?resource_id=d291f9c7-b1a4-4c97-9618-c545a22fb23d&limit=20";
                URL url = new URL(baseUrl);

                // Postavi request na GET i uspostavi konekciju
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Ucitaj input stream u String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    Log.e("Async Task", "inputStream is empty");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                //Prebaci u string buffer
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Buffer prazan,nema sta da se parsira
                    return null;
                }
                JsonString = buffer.toString();

            } catch (IOException e) {
                Log.e("Async task", "Error ", e);
                // Neuspesno uspostavljanje konekcije, nema parsiranja
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Async task", "Error closing stream", e);
                    }
                }
            }
            return JsonString;

        }

        @Override
        protected void onPostExecute(String JsonString) {
            try {
                setIt(getDataFromJson(JsonString));
            } catch(JSONException e){
                Log.v("onPostExecute", "JSON Error", e);
            }
        }
    }
}
