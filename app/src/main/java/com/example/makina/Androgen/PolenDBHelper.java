package com.example.makina.Androgen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.makina.Androgen.PolenContract.HistoricalEntry;

/**
 * Created by Aleksa on 31-Jan-16.
 */
public class PolenDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "data.db";

    public PolenDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_DATA_TABLE =
                "CREATE TABLE " + HistoricalEntry.TABLE_NAME + " (" +
                        HistoricalEntry._ID + " INTEGER PRIMARY KEY, " +
                        HistoricalEntry.COLUMN_PLANT_ID + " INTEGER NOT NULL, " +
                        HistoricalEntry.COLUMN_LOCATION_ID + " INTEGER NOT NULL, " +
                        HistoricalEntry.COLUMN_DATE + " DATE NOT NULL, " +
                        HistoricalEntry.COLUMN_CONCENTRATION + " INTEGER NOT NULL, " +
                        HistoricalEntry.COLUMN_TENDENCY + " INTEGER NOT NULL" +
                        " );";

        db.execSQL(SQL_CREATE_DATA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP IF TABLE NAME EXISTS " + HistoricalEntry.TABLE_NAME);

        onCreate(db);

    }
}
