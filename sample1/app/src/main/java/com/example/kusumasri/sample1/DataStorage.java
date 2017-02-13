package com.example.kusumasri.sample1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kusumasri on 2/12/17.
 */

public class DataStorage extends SQLiteOpenHelper {

    private static final int dbversion=1;
    private static String dbname="usertable";
    public static String username="usern";
    public static String password="pass";
    public static String id="id";

    public DataStorage(Context context,String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,dbname,factory,dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table"+dbname+"("+id+"INTEGER PRIMARY KEY AUTOINCREMENT "+username+" TEXT "+password+"TEXT "+");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+dbname);
        onCreate(db);
    }

    public void addrow(user user)
    {
        ContentValues val=new ContentValues();
        val.put(username,user.getuname());
        val.put(password,user.getpass());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(dbname,null,val);
        db.close();
    }
    public void delete(String usern)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM "+dbname+"WHERE "+username+"=\""+usern+"\";");

    }
}
