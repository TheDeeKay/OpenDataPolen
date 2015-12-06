package com.example.makina.polen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentList extends AppCompatActivity {

    public FragmentList() {
        // Required empty public constructor
    }

    public FragmentList(int count) {



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        String[] biljke = {
                "Kaktus",
                "Ambrozija",
                "Lipa",
                "Kuruz",
                "Senica"
        };

        List<String> listaBiljaka = new ArrayList<String>(Arrays.asList(biljke));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.fragment_list_item,
                R.id.imgButton,
                listaBiljaka);

        ListView list = (ListView) findViewById(R.id.listview);

        list.setAdapter(adapter);

    }


}

