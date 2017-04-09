package com.Alertapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.String;
import java.util.ArrayList;

/**
 * Created by kusumasri on 2/12/17.
 */

public class DataStorage extends SQLiteOpenHelper {

    public  static final int dbversion=4;

    public DataStorage(Context context)
    {
        super(context, "Database.db",null,dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery="create table authentication(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL unique, password TEXT );";
        db.execSQL(createQuery);
        String createq1="create table rules(id INTEGER PRIMARY KEY AUTOINCREMENT, rulename TEXT NOT NULL, ruledes TEXT);";
        db.execSQL(createq1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS authentication");
        db.execSQL("DROP TABLE IF EXISTS rules");
        onCreate(db);
    }

    public void addRule(Rule rule)
    {
        ContentValues val=new ContentValues();
        val.put("rulename",rule.getRulename());
        val.put("ruledes",rule.getRuledesc());
        SQLiteDatabase db=getWritableDatabase();
        db.insert("rules",null,val);
        db.close();
    }

    public void addrow(user user)
    {
        ContentValues val=new ContentValues();
        val.put("username",user.getuname());
        val.put("password",user.getpass());
        SQLiteDatabase db=getWritableDatabase();
        db.insert("authentication",null,val);
        db.close();
    }
    public void deleteuser(String usern)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM authentication WHERE username =\""+usern+"\";");
    }

    public boolean getpass(String uname,String password)
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.rawQuery("SELECT password FROM authentication WHERE username=\""+uname+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                     String newpass=Utils.Convertpasstohash(password);
                     String pass= c.getString(c.getColumnIndex("password"));
                       if(newpass.equals(pass))
                           return true;
                }while (c.moveToNext());
            }
        }
        return false;
    }

    public ArrayList<Rule> getrules()
    {
        ArrayList<Rule> arrayrules=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  * FROM  rules;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                    if (cursor.moveToFirst()) {
                    do {
                        Rule obj = new Rule();
                        obj.setid(cursor.getInt(cursor.getColumnIndex("id")));
                        obj.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                        obj.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                      arrayrules.add(obj);
                    } while (cursor.moveToNext());
            }
            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
        }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return arrayrules;
    }

    public void deleterule(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        //String string =String.valueOf(id);
        db.execSQL("DELETE FROM rules WHERE  id=\""+id+"\";");
    }


}
