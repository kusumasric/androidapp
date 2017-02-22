package com.example.kusumasri.sample1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.String;
import com.example.kusumasri.sample1.Utils;
/**
 * Created by kusumasri on 2/12/17.
 */

public class DataStorage extends SQLiteOpenHelper {

    public  static final int dbversion=1;

    public DataStorage(Context context)
    {
        super(context, "users",null,dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery="create table authentication(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL unique, password TEXT );";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS authentication");
        onCreate(db);
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
    public void delete(String usern)
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
}
