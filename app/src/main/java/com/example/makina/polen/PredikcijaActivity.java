package com.example.makina.polen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredikcijaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_predikcija);

        final Context context = this;

        String[] gradovi = new String[18];
        for(int i=0; i<18; i++) gradovi[i] = MainActivity.id_lokacija.get(i+1);

        List<String> list = new ArrayList<String>(Arrays.asList(gradovi));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gradovi,
                android.R.layout.simple_spinner_item
        ) ;

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_gradovi);
        spinner.setAdapter(adapter);

        final DatePicker picker = (DatePicker) findViewById(R.id.kalendar);

        final Button btn = (Button) findViewById(R.id.predikcija_launch_button);

        int grad_id;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ScreenSlidePagerActivity.class);

                intent.putExtra("dan", picker.getDayOfMonth());
                intent.putExtra("mesec", picker.getMonth());
                intent.putExtra("godina",picker.getYear());
                intent.putExtra("grad_id", spinner.getSelectedItemPosition() + 1);
                intent.putExtra("caller", 1);

                startActivity(intent);

            }
        });
            }


}
