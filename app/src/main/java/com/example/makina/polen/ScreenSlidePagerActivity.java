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
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        tracker = new GPSTracker(ScreenSlidePagerActivity.this);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    public void klikMeni(View view){
        Intent intent = new Intent(this, FragmentList.class);
        startActivity(intent);

        if(tracker.canGetLocation()) {
            tracker.getLocation();
            lat = tracker.getLatitude();
            longit = tracker.getLongitude();
        }else{
            tracker.showSettingsAlert();
        }

    }



    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(tracker.canGetLocation()) {
                tracker.getLocation();
                lat = tracker.getLatitude();
                longit = tracker.getLongitude();
            }else{
                tracker.showSettingsAlert();
            }

            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt("length", position);
            ScreenSlidePageFragment tmp = new ScreenSlidePageFragment();
            tmp.setArguments(bundle);
            return tmp;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

