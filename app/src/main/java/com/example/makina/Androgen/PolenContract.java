package com.example.makina.Androgen;

import android.provider.BaseColumns;

/**
 * Created by Aleksa on 31-Jan-16.
 */


public class PolenContract {

    //Contract za tabelu podataka sa sajta
    public static final class HistoricalEntry implements BaseColumns {

        //Ime tabele podataka
        public static final String TABLE_NAME = "data_entries";

        //Kolona za ID biljke
        public static final String COLUMN_PLANT_ID = "id_biljke";

        //Kolona za ID lokacije
        public static final String COLUMN_LOCATION_ID = "id_lokacije";

        //Kolona za datum
        public static final String COLUMN_DATE = "datum";

        //Kolona za koncentraciju
        public static final String COLUMN_CONCENTRATION = "koncentracija";

        //Kolona za tendenciju
        public static final String COLUMN_TENDENCY = "tendencija";
    }


}
