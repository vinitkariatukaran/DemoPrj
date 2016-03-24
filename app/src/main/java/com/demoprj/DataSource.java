package com.demoprj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mounil.shah on 12/9/2015.
 */
public class DataSource {

    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new MyDbHelper(context,MyDbHelper.DBName,null,MyDbHelper.DBVersion);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }

}
