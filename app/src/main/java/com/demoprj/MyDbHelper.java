package com.demoprj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mounil.shah on 12/9/2015.
 */
public class MyDbHelper extends SQLiteOpenHelper{

    public static String DBName = "Bottle";
    public static int DBVersion = 1;

    public  static String USER_ID="user_id";
    public  static String FIRST_NAME="first_name";
    public  static String MIDDLE_NAME="middle_name";
    public  static String LAST_NAME="last_name";
    public  static String ADDRESS="address";
    public  static String GAS_TYPE="gas_type";
    public  static String LATLNG="latlng";
    public  static String PHONE_NUMBER="phone_number";

    public  static String GAS_BOOKING_ID="gas_booking_id";
    public  static String NAME="name";
    public  static String BOTTLE_SIZE="bottle_size";
    public  static String TOTAL="total";
    public  static String QUANTITY="quantity";
    public  static String DELIVERY_STATUS="delivery_status";
    public  static String DELIVERY_BOY_ID="delivery_boy_id";
    public  static String DATE_OF_BOOKING="date_of_booking";
    public  static String DATE_OF_DELIVERY="date_of_delivery";

    public  static String GAS_TYPE_ID="gas_type_id";

    public  static String GAS_SIZE_ID="gas_size_id";
    public  static String GAS_SIZE="gas_size";

    public  static String PRICE_ID="price_id";
    public  static String PRICE ="price";

    public  static String DELIVERY_BOY_NAME="delivery_boy_name";
    public  static String DELIVERY_BOY_PHONE_NUMBER="phone_number";
    public  static String DELIVERY_BOY_ADDRESS="address";
    public  static String DELIVERY_BOY_USERNAME="username";
    public  static String DELIVERY_BOY_PASSWORD="password";

    public  static String TABLE_USER="user";
    public  static String TABLE_GAS_BOOKING="gas_booking";
    public  static String TABLE_GAS_TYPE="gas_type";
    public  static String TABLE_GAS_SIZE="gas_size";
    public  static String TABLE_DELIVERY_BOY="delivery_boy";

    public  static String TABLE_PRICE="price";

    public static String QUERY_FOR_CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+" ( "
                                                                      + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                                      +FIRST_NAME + " TEXT, "
                                                                      +MIDDLE_NAME + " TEXT, "
                                                                      +LAST_NAME + " TEXT, "
                                                                      +ADDRESS + " TEXT, "
                                                                      +LATLNG + " TEXT, "
                                                                      +PHONE_NUMBER+ " TEXT )";

    public static String QUERY_FOR_CREATE_TABLE_GAS_BOOKING = "CREATE TABLE "+TABLE_GAS_BOOKING+" ( "
                                                                      + GAS_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                                      +NAME + " TEXT, "
                                                                      +USER_ID + " INTEGER, "
                                                                      +BOTTLE_SIZE + " INTEGER, "
                                                                      +TOTAL + " REAL, "
                                                                      +QUANTITY + " INTEGER, "
                                                                      +DELIVERY_STATUS+ " TEXT, "
                                                                      +DELIVERY_BOY_ID+ " INTEGER, "
                                                                      +DELIVERY_BOY_NAME+ " TEXT, "
                                                                      +DATE_OF_DELIVERY+ " TEXT, "
                                                                      +DATE_OF_BOOKING+ " TEXT )";

    public static String QUERY_FOR_CREATE_TABLE_GAS_TYPE = "CREATE TABLE "+TABLE_GAS_TYPE+" ( "
            + GAS_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +GAS_TYPE + " TEXT )";

    public static String QUERY_FOR_CREATE_TABLE_GAS_SIZE = "CREATE TABLE "+TABLE_GAS_SIZE+" ( "
            + GAS_SIZE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +GAS_TYPE_ID + " INTEGER, "
            +GAS_SIZE + " INTEGER )";

    public static String QUERY_FOR_CREATE_TABLE_DELIVERY_BOY = "CREATE TABLE "+TABLE_DELIVERY_BOY+" ( "
            + DELIVERY_BOY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DELIVERY_BOY_NAME + " TEXT, "
            +DELIVERY_BOY_PHONE_NUMBER + " TEXT, "
            +DELIVERY_BOY_ADDRESS + " TEXT, "
            + DELIVERY_BOY_USERNAME+ " TEXT, "
            +DELIVERY_BOY_PASSWORD + " TEXT )";

    public static String QUERY_FOR_CREATE_TABLE_PRICE = "CREATE TABLE "+TABLE_PRICE+" ( "
            + PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +PRICE + " REAL, "
            + GAS_SIZE_ID + " INTEGER )";
    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_FOR_CREATE_TABLE_USER);
        db.execSQL(QUERY_FOR_CREATE_TABLE_GAS_BOOKING);
        db.execSQL(QUERY_FOR_CREATE_TABLE_DELIVERY_BOY);
        db.execSQL(QUERY_FOR_CREATE_TABLE_PRICE);
        db.execSQL(QUERY_FOR_CREATE_TABLE_GAS_SIZE);
        db.execSQL(QUERY_FOR_CREATE_TABLE_GAS_TYPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
