package com.example.makina.polen;

import java.util.ArrayList;

/**
 * Created by Makina on 12/6/2015.
 */
public class nekaklasa {

    public nekaklasa(){}

    public nekaklasa(ArrayList<String[]> array){



    }

    public static nekaklasa getInstance(ArrayList<String[]> array){
        return new nekaklasa(array);
    }
}
