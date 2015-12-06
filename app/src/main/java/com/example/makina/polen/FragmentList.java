package com.example.makina.polen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentList extends Fragment {

    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        String[] biljke = {
                "Kaktus",
                "Ambrozija",
                "Lipa",
                "Kuruz",
                "Senica"
        };

        List<String> listaBiljaka = new ArrayList<String>(Arrays.asList(biljke));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        R.layout.fragment_list_item,
                R.id.imgButton,
                listaBiljaka);

        ListView list = (ListView) rootView.findViewById(R.id.listview);

        list.setAdapter(adapter);


                return rootView;
    }
}

