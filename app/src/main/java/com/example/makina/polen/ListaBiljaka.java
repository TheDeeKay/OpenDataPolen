package com.example.makina.polen;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListaBiljaka extends AppCompatActivity {

    public ListaBiljaka() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_biljaka);

        //Napravi string imena biljaka
        String[] biljke = new String[MainActivity.UKUPNO_BILJAKA];
        for (int i = 0; i < MainActivity.UKUPNO_BILJAKA; i++)
            biljke[i] = MainActivity.id_biljke.get(i);

        List<String> listaBiljaka = new ArrayList<String>(Arrays.asList(biljke));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.lista_biljaka_item,
                R.id.fragment_list_item_textview,
                listaBiljaka);

        ListView list = (ListView) findViewById(R.id.lista_biljaka);

        list.setAdapter(adapter);

        }

    //onClick za item iz liste biljaka, precrtava i oznacava da se ne gledaju
    public void onClick(View view) {

        TextView txt = (TextView) view;

        int id = MainActivity.biljke_id.get(txt.getText());

        if (MainActivity.biljkeChecked[id] == 0) {

            txt.setPaintFlags(txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            MainActivity.biljkeChecked[id] = 1;
            ScreenSlidePagerActivity.sc.remove(MainActivity.pozicijaBiljkeUSelektovanim(id));

        } else {
            MainActivity.biljkeChecked[id] = 0;
            txt.setPaintFlags(txt.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            ScreenSlidePagerActivity.sc.add(MainActivity.pozicijaBiljkeUSelektovanim(id), ScreenSlidePageFragment.newInstance(id));
        }

        //ScreenSlidePagerActivity.mPagerAdapter.notifyDataSetChanged();

        //ScreenSlidePagerActivity.mPager.setAdapter(ScreenSlidePagerActivity.mPagerAdapter);

    }

}




