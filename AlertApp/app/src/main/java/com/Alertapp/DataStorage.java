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

    public  static final int dbversion=11;

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
        String createquerryweather="create table weathercondition(id INTEGER PRIMARY KEY AUTOINCREMENT, ruleid INTEGER NOT NULL, mintemp INTEGER DEFAULT 0, maxtemp INTEGER DEFAULT 120);";
        db.execSQL(createquerryweather);
        String createquerrylocation="create table locationcondition(id INTEGER PRIMARY KEY AUTOINCREMENT, ruleid INTEGER NOT NULL, location TEXT);";
        db.execSQL(createquerrylocation);
        String createquerrydate="create table datetimecondition(id INTEGER PRIMARY KEY AUTOINCREMENT, ruleid INTEGER NOT NULL, date TEXT,time TEXT);";
        db.execSQL(createquerrydate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS authentication");
        db.execSQL("DROP TABLE IF EXISTS rules");
        db.execSQL("DROP TABLE IF EXISTS weathercondition");
        db.execSQL("DROP TABLE IF EXISTS locationcondition");
        db.execSQL("DROP TABLE IF EXISTS datetimecondition");
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


//To add weather condition
    public void addweather(WeatherCondition wc,String rulename)
    {
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT id FROM rules WHERE rulename =\""+rulename+"\";";
        Cursor c;
        int id=0;
        c = db.rawQuery(query,null);
        if(c!=null){
            if  (c.moveToFirst()) {
                do {
                    id=c.getInt(c.getColumnIndex("id"));

                }while (c.moveToNext());
            }
        }

        wc.rule.id=id;
        ContentValues val=new ContentValues();
        val.put("ruleid",wc.rule.getid());
        val.put("mintemp",wc.getMintemp());
        val.put("maxtemp",wc.getMaxtemp());
        db.insert("weathercondition",null,val);
        db.close();

    }

    //To add locationcondition
    public void addlocation(Locationcondition lc,String rulename)
    {
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT id FROM rules WHERE rulename =\""+rulename+"\";";
        Cursor c;
        int id=0;
        c = db.rawQuery(query,null);
        if(c!=null){
            if  (c.moveToFirst()) {
                do {
                    id=c.getInt(c.getColumnIndex("id"));

                }while (c.moveToNext());
            }
        }

        lc.rule.id=id;
        ContentValues val=new ContentValues();
        val.put("ruleid",lc.rule.getid());
        val.put("location",lc.getLocation());
        db.insert("locationcondition",null,val);
        db.close();

    }


    //To add datetime condition
    public void adddatetime(Timecondition tc,String rulename)
    {
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT id FROM rules WHERE rulename =\""+rulename+"\";";
        Cursor c;
        int id=0;
        c = db.rawQuery(query,null);
        if(c!=null){
            if  (c.moveToFirst()) {
                do {
                    id=c.getInt(c.getColumnIndex("id"));

                }while (c.moveToNext());
            }
        }

        tc.rule.id=id;
        ContentValues val=new ContentValues();
        val.put("ruleid",tc.rule.getid());
        val.put("date",tc.getDate());
        val.put("time",tc.getTime());
        db.insert("datetimecondition",null,val);
        db.close();

    }



//To delete weather
    public void deleteweather(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        //String string =String.valueOf(id);
        db.execSQL("DELETE FROM weathercondition WHERE  ruleid=\""+id+"\";");
        deleterule(id);
    }


    //To delete location
    public void deletelocation(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        //String string =String.valueOf(id);
        db.execSQL("DELETE FROM locationcondition WHERE  ruleid=\""+id+"\";");
        deleterule(id);
    }

//To delete User
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

    //To get all the rules

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


    //To get all weather conditions
    public ArrayList<WeatherCondition> getweathercondition()
    {
        ArrayList<WeatherCondition> arrayweather=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  weathercondition.id,weathercondition.ruleid,weathercondition.mintemp,weathercondition.maxtemp,rules.rulename,rules.ruledes" +
                " FROM  weathercondition JOIN rules ON rules.id=weathercondition.ruleid;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                        WeatherCondition obj = new WeatherCondition();
                        obj.setid(cursor.getInt(cursor.getColumnIndex("id")));
                        obj.setMintemp(cursor.getInt(cursor.getColumnIndex("mintemp")));
                        obj.setMaxtemp(cursor.getInt(cursor.getColumnIndex("maxtemp")));
                        obj.setruleid(cursor.getInt(cursor.getColumnIndex("ruleid")));
                        obj.rule.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                        obj.rule.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                        arrayweather.add(obj);
                    } while (cursor.moveToNext());
                }
            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return arrayweather;
    }



    //To get all locationconditions
    public ArrayList<Locationcondition> getlocationcondition()
    {
        ArrayList<Locationcondition> arraylocation=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  locationcondition.id,locationcondition.ruleid,locationcondition.location,rules.rulename,rules.ruledes " +
                "FROM locationcondition JOIN rules ON rules.id=locationcondition.ruleid;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                            Locationcondition obj = new Locationcondition();
                            obj.setId(cursor.getInt(cursor.getColumnIndex("id")));
                            obj.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                            obj.rule.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                            obj.rule.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                            obj.setruleid(cursor.getInt(cursor.getColumnIndex("ruleid")));

                            arraylocation.add(obj);
                    } while (cursor.moveToNext());
                }
            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return arraylocation;
    }


    //To get all datetimecondition
    public ArrayList<Timecondition> gettimecondition()
    {
        ArrayList<Timecondition> arraytime=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  datetimecondition.id,datetimecondition.ruleid,datetimecondition.date,datetimecondition.time,rules.rulename,rules.ruledes" +
                " FROM  datetimecondition JOIN rules ON rules.id=datetimecondition.ruleid;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                        Timecondition obj = new Timecondition();
                        obj.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        obj.setTime(cursor.getString(cursor.getColumnIndex("time")));
                        obj.setDate(cursor.getString(cursor.getColumnIndex("date")));
                        obj.rule.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                        obj.rule.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                        obj.setruleid(cursor.getInt(cursor.getColumnIndex("ruleid")));
                        arraytime.add(obj);
                    } while (cursor.moveToNext());
                }
            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return arraytime;
    }










//To delete rules

    public void deleterule(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        //String string =String.valueOf(id);
        db.execSQL("DELETE FROM rules WHERE  id=\""+id+"\";");
        Cursor c=db.rawQuery("SELECT id FROM weathercondition WHERE ruleid=\""+id+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    db.execSQL("DELETE FROM weathercondition WHERE ruleid=\""+id+"\";");

                }while (c.moveToNext());
            }
        }
        c=db.rawQuery("SELECT id FROM locationcondition WHERE ruleid=\""+id+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    db.execSQL("DELETE FROM locationcondition WHERE ruleid=\""+id+"\";");

                }while (c.moveToNext());
            }
        }


        c=db.rawQuery("SELECT id FROM datetimecondition WHERE ruleid=\""+id+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    db.execSQL("DELETE FROM  datetimecondition WHERE ruleid=\""+id+"\";");

                }while (c.moveToNext());
            }
        }


    }


}
