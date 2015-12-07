package com.example.makina.polen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class ScreenSlidePagerActivity extends AppCompatActivity{

    public GPSTracker tracker;
    public static double lat, longit;


    private ViewPager mPager;

    public static PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);


        //tracker = new GPSTracker(ScreenSlidePagerActivity.this);

        // Dodaj adapter na ViewPager
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        /*if(tracker.canGetLocation()) {
            tracker.getLocation();
            lat = tracker.getLatitude();
            longit = tracker.getLongitude();
        }else{
            tracker.showSettingsAlert();
        }*/

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

            Bundle bundle = new Bundle();
            bundle.clear();
            int pos = MainActivity.pozicijaBiljke(position);
            bundle.putInt("length", pos);
            ScreenSlidePageFragment tmp = new ScreenSlidePageFragment();
            tmp.setArguments(bundle);
            return tmp;
        }

        @Override
        public int getCount() {

            return MainActivity.countBiljke();
        }
    }
}

