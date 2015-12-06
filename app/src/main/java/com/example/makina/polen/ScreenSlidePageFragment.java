package com.example.makina.polen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
    private int id_biljke;
    private int id_lokacije;
    double longitude = 20.1;
    double lattitude = 56.2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        Button btn = (Button) rootView.findViewById(R.id.bezobrazluk);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PredikcijaActivity.class);
                startActivity(intent);
            }
        });

        Button buteen = (Button) rootView.findViewById(R.id.fragment_poruka);



        int position = getArguments().getInt("length");
        MainActivity.Kljuc kljuc = MainActivity.pozicije.get(position);
        this.id_biljke = kljuc.id_biljke;
        this.id_lokacije = kljuc.id_lokacije;

        Log.e("KLJUCEVI", id_biljke + ", " + MainActivity.id_biljke.get(id_biljke));


        TextView text = (TextView) rootView.findViewById(R.id.fragment_ime_biljke);

        text.setText(MainActivity.id_biljke.get(this.id_biljke));

        int koncentracija = 100/3*MainActivity.predikcija(15, 7, 2016, 2, longitude, lattitude,
                3, 6, 13);
        String k_tmp = null;
        if( koncentracija > 67 ) k_tmp = "Dangerous";
        else if(koncentracija > 34) k_tmp = "Be careful";
        else k_tmp = "Nothing to worry";
        buteen.setText(k_tmp);

        Log.e("NADAMO SE",  ScreenSlidePagerActivity.lat + " " + ScreenSlidePagerActivity.longit);

        TextView poruka = (TextView) rootView.findViewById(R.id.fragment_poruka);

        //poruka.setText(String.valueOf(koncentracija/3));

        TextView beten = (TextView)rootView.findViewById(R.id.ajde);
        beten.setText(String.valueOf(koncentracija));

        return rootView;
    }
}