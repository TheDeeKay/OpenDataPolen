package com.example.makina.polen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    static int[] biljkeChecked = new int[25];



    class Kljuc{
        int id_lokacije;
        int id_biljke;

        public Kljuc(int k1, int k2){id_lokacije=k1;id_biljke=k2;}

    }

    public static InputStream isVrstePolena;
    public static InputStream isNormalizacija;
    public static InputStream isStanice;
    public static InputStream isStepeni;
    public static InputStream isTezine;

    public static ArrayList<Kljuc> pozicije = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isVrstePolena = getResources().openRawResource(R.raw.vrste_polena);
        isNormalizacija = getResources().openRawResource(R.raw.normalizacija);
        isStanice = getResources().openRawResource(R.raw.lokacije_stanica_polen);
        isStepeni = getResources().openRawResource(R.raw.stepeni);
        isTezine = getResources().openRawResource(R.raw.tezine);

        citanje_iz_fajlova();
        generisi_haseve();
        pozicije.add(new Kljuc(10, 5));
        pozicije.add(new Kljuc(7,6));

        FetchData fetch = new FetchData();
        fetch.execute();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.container, new ScreenSlidePageFragment()).commit();
        }*/

        Intent slider = new Intent(this, ScreenSlidePagerActivity.class);

        startActivity(slider);



        /*
        Uzmi intent, uzmi extra koji dolazi kao offset, promeni trenutni offset
         */
        //Intent intent = getIntent();

        //int j = intent.getIntExtra("Dan offset", 0);
        //i = i-j;

        //text = (TextView) findViewById(R.id.textview);


        double dan = 30,
                mesec = 15,
                godina = 2016,
                visina = 55,
                sirina = 43.23,
                alergenost = 2;
        int id_vrste = 10, id_grupe = 1, id_lokacije = 5;

        int predikcija_ = predikcija(dan, mesec, godina, alergenost, visina, sirina, id_grupe, id_vrste, id_lokacije);

        //TextView text = (TextView) findViewById(R.id.fragment_poruka);

        //System.out.println(predikcija_ + " PRSTI ");


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

    public static HashMap<String, Integer> biljke_id = new HashMap<>();
    public static HashMap<String, Integer> biljke_grupa = new HashMap<>();
    public static HashMap<String, Integer> biljke_alergenost = new HashMap<>();
    public static HashMap<String, Integer> lokacija_id = new HashMap<>();
    public static HashMap<String, Double> lokacija_visina = new HashMap<>();
    public static HashMap<String, Double> lokacija_sirina = new HashMap<>();
    public static HashMap<Integer, String> id_biljke = new HashMap<>();
    public static HashMap<Integer, String> id_lokacija = new HashMap<>();



    public void zadaj_grupu(String s, String g){
        if (g.equals("Drveće"))
            biljke_grupa.put(s,0);
        else
        if (g.equals("Trava"))
            biljke_grupa.put(s,2);
        else
            biljke_grupa.put(s,1);
    }

    public void generisi_haseve(){



        try {
            //dodavanje normalizacije


            StringBuilder str = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(isVrstePolena, "UTF-8"));
            br.readLine();
            String line = null;
            int i = 0;
            while (i < 25) {
                line = br.readLine();
                String[] parts = line.split(",");
                id_biljke.put(Integer.parseInt(parts[0]), parts[2]);
                biljke_id.put(parts[2],Integer.parseInt(parts[0]) - 1);
                zadaj_grupu(parts[2],parts[3]);
                biljke_alergenost.put(parts[2],Integer.parseInt(parts[4]));
                i++;
            }

            br.close();




            str = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(isStanice, "UTF-8"));
            br.readLine();
            line = null;
            i = 1;
            line = br.readLine();
            while(i<17){
                String[] parts = line.split(",");
                if(parts[1].equals("Beograd - Novi Beograd")){
                    String[] pp = parts[1].split(" ");
                    parts[1] = pp[0];
                }
                id_lokacija.put(Integer.parseInt(parts[0]), parts[1]);
                lokacija_id.put(parts[1], Integer.parseInt(parts[0]) - 1);
                lokacija_sirina.put(parts[1], Double.valueOf(parts[2]));
                lokacija_visina.put(parts[1], Double.valueOf(parts[3]));
                i++;
                line = br.readLine();
            }


            br.close();


            for(String s: biljke_id.keySet())
                System.out.println(" " + s);

            System.out.println("RANDOM");


            for(String s: lokacija_id.keySet())
                System.out.println(" " + s);


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortiraj_listu(ArrayList<String[]> lista, final int col_godina, final int col_mesec, final int col_dan){



        //Sorting
        Collections.sort(lista, new Comparator<String[]>() {
            @Override
            public int compare(String[] str1, String[] str2) {

                if (str2[col_godina].equals(str1[col_godina])) {
                    if (str2[col_mesec].equals(str1[col_mesec])){
                        return str1[col_dan].compareTo(str2[col_dan]);
                    }else{
                        return str1[col_mesec].compareTo(str2[col_mesec]);
                    }
                } else {
                    return str1[col_godina].compareTo(str2[col_godina]);
                }

            }
        });

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

                ArrayList<String[]> lista = new ArrayList<>();

                rezultat = new String[records.length()];
                for(int i = 0; i < records.length(); i++) {

                    //Kolone koje posle sklapamo u rezultat
                    String datum;
                    String tendencija;
                    String koncentracija;

                    JSONObject dan = records.getJSONObject(i);

                    datum = dan.getString("DATUM");
                    String[] parts = datum.split("-");
                    tendencija = dan.getString("TENDENCIJA");
                    koncentracija = dan.getString("KONCENTRACIJA");

                    String[] linija  = new String[5];
                    linija[0] = parts[0];linija[1] = parts[1];linija[2] = parts[2];

                    linija[3] = tendencija; linija[4] = koncentracija;

                    lista.add(linija);

                    rezultat[i] = "Datum: "+datum+"\nTendencija: "+tendencija+
                            "\nKoncentracija: "+koncentracija+"\n";
                }

                sortiraj_listu(lista, 0, 1, 2);


                for(int i=0;i<30;i++)
                {
                    String[] str = lista.get(i);


                    for(int j=0;j<5;j++)
                        System.out.println(" " + str[j]);
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
                String baseUrl = "http://data.sepa.gov.rs/api/action/datastore/search.json?resource_id=d291f9c7-b1a4-4c97-9618-c545a22fb23d";
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

    }

    private static double[] update_stepene(double[] glavni, ArrayList<double[]> stepeni){
        double[] ret = new double[stepeni.size()];
        int len = stepeni.size();
        for(int k=0;k<len;k++) {
            double[] tmp = stepeni.get(k);
            ret[k] = update_pojedinacni_feature(glavni, tmp);
        }
        return ret;
    }

    private static double update_pojedinacni_feature(double[] glavni, double[] stepeni){
        double ret = 1;
        for(int i=0;i<stepeni.length;i++)
            ret *= Math.pow(glavni[i],stepeni[i]);

        return ret;
    }


    private static double mnozenje_vektora(double[] prvi, double[] drugi, double bias){
        double rez = 0;
        if( prvi.length != drugi.length)
            return rez;
        for(int i=0;i<prvi.length;i++)
            rez += prvi[i]*drugi[i];
        rez += bias;

        return rez;
    }

    private static double sigmoid(double x){
        return (double)1/(1 + Math.exp(-x));
    }

    private static ArrayList<double[]> listaStepena;
    private static double[] bias;
    private static ArrayList<double[]> listatezina;
    private static double[] mean;
    private static double[] std;



    private void citanje_iz_fajlova(){
        StringBuilder text = new StringBuilder();
        try {
            InputStream is = getResources().openRawResource(R.raw.stepeni);

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line1, line2;
            while ((line1 = br.readLine()) != null) {
                line2 = br.readLine();
                text.append(line1);
                text.append(line2);
                text.append('\n');
            }
            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }

        listaStepena = new ArrayList<>();
        String stepeniStr = text.toString();
        String[] delovi = stepeniStr.split("\n");


        String[] prviDeo = delovi[0].split(" ");
        String[] prvi_podeljen = prviDeo[0].split("," );

        double[] tmp_vec;
        tmp_vec = new double[53];
        tmp_vec[0] = (double)Integer.valueOf(prvi_podeljen[2]);


        int tmp_vec_cnt = 1;
        for(int i=1;i<prviDeo.length-1;i++) {
            if (i == 37)
                continue;
            tmp_vec[tmp_vec_cnt++] = (double) Integer.valueOf(prviDeo[i]);
        }

        String[] posl_podeljen = prviDeo[prviDeo.length-1].split(",");

        tmp_vec[52] = Double.parseDouble(posl_podeljen[0]);
        tmp_vec[52] = Math.ceil(tmp_vec[52]);

        listaStepena.add(tmp_vec);






        for(int main_cnt=1;main_cnt<delovi.length-1;main_cnt++){
            String[] tmpprviDeo = delovi[main_cnt].split(" ");
            String[] tmpprvi_podeljen = tmpprviDeo[1].split("," );


            double[] tmp_vec_new = new double[53];
            tmp_vec_new[0] = (double)Integer.valueOf(tmpprvi_podeljen[1]);

            tmp_vec_cnt = 1;
            for(int i=2;i<tmpprviDeo.length-1;i++) {


                if (i == 38) {
                    continue;
                }

                tmp_vec_new[tmp_vec_cnt++] = (double) Integer.valueOf(tmpprviDeo[i]);
            }

            String[] tmp_posl_podeljen = tmpprviDeo[tmpprviDeo.length-1].split(",");

            tmp_vec_new[tmp_vec_cnt++] = (double)Integer.valueOf(tmp_posl_podeljen[0]);


            listaStepena.add(tmp_vec_new);

        }




        String[] tmpprviDeo = delovi[delovi.length-1].split(" ");
        String[] tmpprvi_podeljen = tmpprviDeo[1].split("," );

        double[] tmp = new double[53];
        tmp[0] = (double)Integer.valueOf(tmpprvi_podeljen[1]);

        tmp_vec_cnt = 1;
        for(int i=2;i<tmpprviDeo.length-1;i++) {
            if (i == 38)
                continue;
            tmp[tmp_vec_cnt++] = (double) Integer.valueOf(tmpprviDeo[i]);
        }

        String[] tmp_posl_podeljen = tmpprviDeo[tmpprviDeo.length-1].split(",");

        tmp[tmp_vec_cnt++] = (double)Integer.valueOf(tmp_posl_podeljen[0]);

        listaStepena.add(tmp);


        /*
        System.out.println("*******************************");
        System.out.println("Lista stepeni: ");
        for(int i=0;i<listaStepena.size();i++){
            double[] stp = listaStepena.get(i);
            for(int j=0;j<stp.length;j++)
                System.out.print(" " + stp[j]);
            System.out.println();
        }

        System.out.print("Konacan Vektor: ");
        for(int i=0;i<konacanVektor.length;i++)
            System.out.print(" " + konacanVektor[i]);
        System.out.println();

        */

        text = new StringBuilder();
        listatezina = new ArrayList<>();
        bias = new double[3];
        try {
            InputStream is = getResources().openRawResource(R.raw.tezine);

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            int cnt = 1;
            int i = 0;
            while(i<198){
                line = br.readLine();
                text.append(line);
                i++;
            }
            text.append('\n');




            while (cnt < 3) {
                i = 0;
                while(i<196) {
                    line = br.readLine();
                    text.append(line);
                    i++;
                }

                text.append('\n');
                cnt++;
            }

            String[] bias_str = (br.readLine()).split(" ");

            for(int z=0;z<3;z++)
                bias[z] = Double.valueOf(bias_str[z]);

            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }

        String[] tezine = text.toString().split("\n");



        for(int i=0;i<tezine.length;i++){
            int j_cnt = 0;
            String[] delovi_tezina = tezine[i].split(" ");
            double[] tmp_tezina = new double[1485];
            for(int j=1;j<delovi_tezina.length-1;j++) {
                if (delovi_tezina[j].equals(" ") || delovi_tezina[j].equals(""))
                    continue;
                tmp_tezina[j_cnt++] = Double.valueOf(delovi_tezina[j]);
            }
            listatezina.add(tmp_tezina);
        }

        mean = new double[1485];
        std = new double[1485];


        try {
            //dodavanje normalizacije
            InputStream is = getResources().openRawResource(R.raw.normalizacija);
            StringBuilder meanStr = new StringBuilder();
            StringBuilder stdStr = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            int i = 0;
            while (i < 188) {
                line = br.readLine();
                meanStr.append(line);
                i++;
            }

            while(i < 376){
                line = br.readLine();
                stdStr.append(line);
                i++;
            }

            String[] mean_parts = (meanStr.toString()).split(" ");
            int mean_cnt = 0;
            for(int k=0;k<mean_parts.length;k++) {
                if (mean_parts[k].equals(" ") | mean_parts[k].equals(""))
                    continue;
                else {
                    mean[mean_cnt++] = Double.valueOf(mean_parts[k]);
                }
            }

            String[] std_parts = (stdStr.toString()).split(" ");
            int std_cnt = 0;
            for(int k=0;k<std_parts.length;k++) {
                if (std_parts[k].equals(" ") | std_parts[k].equals(""))
                    continue;
                else {
                    std[std_cnt++] = Double.valueOf(std_parts[k]);
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int predikcija(double dan, double mesec, double godina,
                           double alergenost, double duzina, double sirina,
                           double idGrupe, double idVrste, double idLokacije ){

        int vr_vel = 19, grp_vel = 3, lok_vel = 26;


        double[] konacanVektor = new double[53];
        konacanVektor[0] = dan;
        konacanVektor[1] = mesec;
        konacanVektor[2] = godina;
        konacanVektor[3] = alergenost;
        konacanVektor[4] = duzina;
        konacanVektor[5] = sirina;
        konacanVektor[(int)idGrupe + 6] = 1;
        konacanVektor[(int)idVrste + 9] = 1;
        konacanVektor[(int)idLokacije + 35] = 1;

        //for(int i=6;i<53;i++)
        //konacanVektor[i]--;

        double[] finalni_feature = update_stepene(konacanVektor, listaStepena);


        for(int i=0;i<finalni_feature.length;i++)
            finalni_feature[i] = (double)(finalni_feature[i] - mean[i])/std[i];

        double[] rezultati = new double[3];
        for(int i=0;i<3;i++)
            rezultati[i] = mnozenje_vektora(finalni_feature, listatezina.get(i), bias[i]);

        for(int i=0;i<3;i++)
            rezultati[i] = sigmoid(rezultati[i]);


        int retVal = 0;

        rezultati[2] *= 3;

        for(int i=0;i<3;i++)
            System.out.println("Rezultat klasifikatora: " + i +" je: " + rezultati[i]);

        double curr_max = Math.max(rezultati[0], rezultati[1]);
        curr_max = Math.max(rezultati[2], curr_max);


        for(int i=0;i<3;i++)
            if (curr_max == rezultati[i])
                retVal = i;

        return retVal;
    }


}
