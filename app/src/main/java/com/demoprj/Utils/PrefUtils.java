package com.demoprj.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;

/**
 * Created by Mounil on 22/03/2016.
 */
public class PrefUtils {
    public static String SHAREPREFNAME = "Batliwala";
    public static String ID = "Id";
    public static String NAME = "name";
    public static String ISADMIN = "isAdmin";
    public static String DATE = "date";

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public PrefUtils(Context context){
        sp = context.getSharedPreferences(SHAREPREFNAME,Context.MODE_PRIVATE);
    }
    public void SetId(int id){
        editor = sp.edit();
        editor.putInt(ID,id);
        editor.commit();
    }
    public int getId(){
        return sp.getInt(ID,0);
    }
    public void SetName(String name){
        editor = sp.edit();
        editor.putString(NAME, name);
        editor.commit();
    }
    public String getName(){
        return sp.getString(NAME, null);
    }
    public void SetisAdmin(boolean isAdmin){
        editor = sp.edit();
        editor.putBoolean(ISADMIN, isAdmin);
        editor.commit();
    }
    public Boolean getisAdmin(){
        return sp.getBoolean(ISADMIN, false);
    }
    public void setDate(String date){
        editor = sp.edit();
        editor.putString(DATE, date);
        editor.commit();
    }
    public String getDate(){
        return sp.getString(DATE, null);
    }
}
