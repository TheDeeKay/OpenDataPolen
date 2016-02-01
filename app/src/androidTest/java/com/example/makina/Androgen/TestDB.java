package com.example.makina.Androgen;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

/**
 * Created by Aleksa on 01-Feb-16.
 */
public class TestDB extends AndroidTestCase {

    void deleteDatabase() {
        mContext.deleteDatabase(PolenDBHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteDatabase();
    }

    public void testCreateDb() throws Throwable {

        mContext.deleteDatabase(PolenDBHelper.DATABASE_NAME);

        SQLiteDatabase db = new PolenDBHelper(this.mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

    }

}
