package com.example.free.mycoolweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2017/10/10.
 */

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
     /*
     * Province 创建表语句
     * */
      public static final String CREATE_PROVINCE="create table Province("
             +"id integer primary key autoincrement,"
             +"province_name text,"
             +"province_code text)";
    /**
     * City表建表语句
     */
    public static final  String CREATE_CITY = "create table City("
            +"id integer primary key autoincrement,"
            +"city_name text,"
            +"city_code text,"
            +"province_id integer)";
    /**
     * County表创建语句
     */
    public static final String CREATE_COUTY = "create table County("
            + "id integer primary key autoincrement,"
            + "conuty_name text,"
            + "conuty_code text,"
            + "city_id integer)";

    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUTY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
