package com.example.makina.polen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
    private int id_biljke;
    private int id_lokacije = 1;
    double longitude = 20.46;
    double lattitude = 44.81;

    int position;

    //Konstruktor sa argumentom pozicije
    public static ScreenSlidePageFragment newInstance(int position){

        ScreenSlidePageFragment tmp = new ScreenSlidePageFragment();

        tmp.position = position;

        return tmp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        //Referenca na button za meni za predikciju
        Button predikcijaBtn = (Button) rootView.findViewById(R.id.predikcija_button);



        //ClickListener za predikciju, poziva predikcija activity
        predikcijaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PredikcijaActivity.class);
                startActivity(intent);
            }
        });


        //Referenca na TextView za ime biljke
        TextView imeBiljke = (TextView) rootView.findViewById(R.id.fragment_ime_biljke);
        //Postavi ime biljke na odgovarajuce
        imeBiljke.setText(MainActivity.id_biljke.get(position));

        String ime_lokacija = MainActivity.id_lokacija.get(ScreenSlidePagerActivity.grad_id);

        double koncentracija = 100/3*MainActivity.predikcija(ScreenSlidePagerActivity.dan,
                ScreenSlidePagerActivity.mesec, ScreenSlidePagerActivity.godina,
                MainActivity.biljke_alergenost.get(MainActivity.id_biljke.get(position)),
                MainActivity.lokacija_visina.get(ime_lokacija),
                MainActivity.lokacija_sirina.get(ime_lokacija),
                MainActivity.biljke_grupa.get(imeBiljke.getText()),
                position, ScreenSlidePagerActivity.grad_id);
        String k_tmp = null;
        if( koncentracija > 67 ) k_tmp = "Dangerous";
        else if(koncentracija > 34) k_tmp = "Be careful";
        else k_tmp = "Nothing to worry";

        //Promeni tekst poruke
        TextView porukaText = (TextView) rootView.findViewById(R.id.fragment_poruka);
        porukaText.setText(k_tmp);

        porukaText.setText(k_tmp);

        TextView koncentracijaText = (TextView)rootView.findViewById(R.id.koncentracija);
        koncentracijaText.setText(String.valueOf(Math.round(koncentracija))+"%");

        return rootView;
    }
}