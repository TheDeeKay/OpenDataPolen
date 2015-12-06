package com.example.makina.polen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredikcijaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predikcija);



        String[] gradovi = new String[18];
        for(int i=0; i<18; i++) gradovi[i] = MainActivity.id_lokacija.get(i+1);

        List<String> list = new ArrayList<String>(Arrays.asList(gradovi));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gradovi,
                android.R.layout.simple_spinner_item
        ) ;

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.gradovi);
        spinner.setAdapter(adapter);

        DatePicker picker = (DatePicker) findViewById(R.id.kalendar);



            }



}
