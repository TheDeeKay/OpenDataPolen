package com.example.makina.Androgen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListaBiljaka extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_biljaka);

        //Napravi string imena biljaka
        String[] biljke = new String[MainActivity.UKUPNO_BILJAKA];
        for (int i = 0; i < MainActivity.UKUPNO_BILJAKA; i++)
            biljke[i] = MainActivity.id_biljke.get(i);

        List<String> listaBiljaka = new ArrayList<String>(Arrays.asList(biljke));

        NekiAdapter<String> adapter = new NekiAdapter<String>(this,
                R.layout.lista_biljaka_item,
                R.id.fragment_list_item_textview,
                listaBiljaka);

        ListView list = (ListView) findViewById(R.id.lista_biljaka);

        list.setDivider(null);
        list.setDividerHeight(40);

        list.setAdapter(adapter);

    }

    //onClick za item iz liste biljaka, precrtava i oznacava da se ne gledaju
    public void onClick(View view) {

        TextView txt = (TextView) view;

        int id = MainActivity.biljke_id.get(txt.getText());

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_selektovane_biljke),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        if (MainActivity.biljkeChecked[id] == 0) {

            txt.setPaintFlags(txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            MainActivity.biljkeChecked[id] = 1;
            ScreenSlidePagerActivity.sc.remove(MainActivity.pozicijaBiljkeUSelektovanim(id));
            editor.putInt(Integer.toString(id), 1);

        } else {
            MainActivity.biljkeChecked[id] = 0;
            txt.setPaintFlags(txt.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            ScreenSlidePagerActivity.sc.add(MainActivity.pozicijaBiljkeUSelektovanim(id), ScreenSlidePageFragment.newInstance(id));
            editor.putInt(Integer.toString(id), 0);
        }

        editor.commit();

    }

    //Novi adapter, samo da bi se zadrzavao strikethrough prilikom skrolovanja i ponovnog otvaranja liste
    private class NekiAdapter<String> extends ArrayAdapter<String> {

        public NekiAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public TextView getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text = (TextView) view;
            if (MainActivity.biljkeChecked[position] == 1)
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else text.setPaintFlags(text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            return text;

        }

    }
}




