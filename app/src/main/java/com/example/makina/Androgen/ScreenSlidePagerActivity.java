package com.example.makina.Androgen;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ScreenSlidePagerActivity extends AppCompatActivity {

    public static int dan = 7, mesec = 6, godina = 2016, grad_id = 0;

    public static ViewPager mPager;

    static Context context;

    public static PagerAdapter mPagerAdapter;

    public static GPSTracker gps = null;

    public static ArrayList<ScreenSlidePageFragment> sc = new ArrayList<ScreenSlidePageFragment>();

    @Override
    protected void onResume() {
        mPagerAdapter.notifyDataSetChanged();
        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        context = this;

        gps = new GPSTracker(this);

        if(gps.location != null) postaviLokaciju(gps.location);

        Intent intent = getIntent();
        if((int) intent.getExtras().get("caller") == 1){
            dan = (int) intent.getExtras().get("dan");
            mesec = (int) intent.getExtras().get("mesec");
            godina = (int) intent.getExtras().get("godina");
            grad_id = (int) intent.getExtras().get("grad_id");
            sc.clear();
        }

            for (int i = 0; i < MainActivity.UKUPNO_BILJAKA; i++)
                sc.add(ScreenSlidePageFragment.newInstance(i));

        // Dodaj adapter na ViewPager
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }


    //Dugme za meni, onClick
    public void klikMeni(View view){
        Intent intent = new Intent(this, ListaBiljaka.class);
        startActivity(intent);

    }

    //Postavlja grad_id na trenutnu lokaciju, ako je to moguce
    public static void postaviLokaciju(Location location){

        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> address = null;

        try {
            address = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(address != null){
            String grad = address.get(0).getLocality();

            if(MainActivity.lokacija_id.containsKey(grad))
                grad_id = MainActivity.lokacija_id.get(grad);
        }

        Log.e("postaviLokaciju", "Lokacija je: " + gps.getLongitude() + ", " + gps.getLatitude()
                + ", a grad je " + MainActivity.id_lokacija.get(grad_id));
    }


    //PagerAdapter, pravi ViewPager iteme za biljke, ukupno countBiljke()
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            /*
            Ako je sve deselektovano, vrati mu default slider screen
            Na default slider screenu pise da treba da selektuje neku biljku
             */
            if(sc.isEmpty())
                return new ScreenSlidePageFragment();

            //U suprotnom, vrati biljku sa odgovarajuce pozicije
            return sc.get(position);
        }

        @Override
        public int getCount() {

            //Ako je lista prazna, vrati da je count 1 (da se prikaze bazni fragment)
            if(sc.isEmpty()) return 1;

            //U suprotnom, vrati broj selektovanih biljaka
            return MainActivity.countBiljke();
        }

        @Override
        public int getItemPosition(Object object) {

            //TODO optimizovati ovo

            /*//Ako se item nalazi u nizu selektovanih biljaka, vrati njegov indeks
            int index = sc.indexOf(object);
            if(index >= 0) return index;*/

            //Inace, obrisi ga
            return POSITION_NONE;
        }
    }
}

