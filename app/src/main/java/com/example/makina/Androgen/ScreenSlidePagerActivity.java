package com.example.makina.Androgen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;


public class ScreenSlidePagerActivity extends AppCompatActivity {

    public static int dan = 7, mesec = 6, godina = 2016, grad_id = 1;

    public static ViewPager mPager;

    public static PagerAdapter mPagerAdapter;

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

        GPSTracker gps = new GPSTracker(this);

        Log.e("onCreate", gps.getLatitude() + ", " + gps.getLongitude());

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

