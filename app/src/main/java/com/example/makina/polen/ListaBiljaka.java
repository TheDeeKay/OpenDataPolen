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
import java.util.HashMap;
import java.util.List;


public class ListaBiljaka extends AppCompatActivity {

    public ListaBiljaka() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_biljaka);

        String[] biljke = new String[25];
        int j = 0;
        for (HashMap.Entry<String, Integer> entry : MainActivity.biljke_id.entrySet()) {
            biljke[j++] = entry.getKey();
        }

        List<String> listaBiljaka = new ArrayList<String>(Arrays.asList(biljke));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.lista_biljaka_item,
                R.id.fragment_list_item_textview,
                listaBiljaka);

        ListView list = (ListView) findViewById(R.id.listview);

        list.setAdapter(adapter);

    }

    //onClick za item iz liste biljaka, precrtava i oznacava da se ne gledaju
    public void onClick(View view) {

        TextView txt = (TextView) view;

        int id = MainActivity.biljke_id.get(txt.getText());

        if (MainActivity.biljkeChecked[id] == 0) {

            txt.setPaintFlags(txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            MainActivity.biljkeChecked[id] = 1;


        } else {
            MainActivity.biljkeChecked[id] = 0;
            txt.setPaintFlags(txt.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }




        ScreenSlidePagerActivity.mPagerAdapter.notifyDataSetChanged();
    }


    //Adapter koji gleda da li je biljka izbacena iz opticaja ili ne
    /*private class BoljiAdapter<String> extends ArrayAdapter<String>{


        public BoljiAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public TextView getView(int position, View convertView, ViewGroup parent) {
            TextView text = (TextView) super.getView(position, convertView, parent);
            if(MainActivity.biljkeChecked[position] == 1)
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            return text;
        }


    }*/
}




