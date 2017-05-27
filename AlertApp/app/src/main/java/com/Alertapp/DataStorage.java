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

//TODO: Rename this to DBAdaptor
public class DataStorage extends SQLiteOpenHelper {

    public  static final int dbVersion=20;

    public DataStorage(Context context)
    {
        super(context, "Database.db",null,dbVersion);
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
        String createquerrydate="create table datetimecondition(id INTEGER PRIMARY KEY AUTOINCREMENT, ruleid INTEGER NOT NULL, datetime TEXT);";
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
        ContentValues ruleTableValues=new ContentValues();
        ruleTableValues.put("rulename",rule.getRulename());
        ruleTableValues.put("ruledes",rule.getRuledesc());
        SQLiteDatabase db=getWritableDatabase();
        Long ruleId=db.insert("rules",null,ruleTableValues);
        ContentValues conditionTableValues=new ContentValues();
        conditionTableValues.put("ruleid",ruleId);
        if(rule.baseconditionobj instanceof Locationcondition ) {
            conditionTableValues.put("location",((Locationcondition) rule.baseconditionobj).getLocation());
            db.insert("locationcondition",null,conditionTableValues);
        }
        if(rule.baseconditionobj instanceof WeatherCondition) {
            conditionTableValues.put("mintemp",((WeatherCondition) rule.baseconditionobj).getMinTemp());
            conditionTableValues.put("maxtemp",((WeatherCondition) rule.baseconditionobj).getMaxTemp());
            db.insert("weathercondition",null,conditionTableValues);
        }
        if(rule.baseconditionobj instanceof Timecondition ) {
            conditionTableValues.put("datetime",((Timecondition) rule.baseconditionobj).getDatetime());
            db.insert("datetimecondition",null,conditionTableValues);
        }
        db.close();

    }

    public void addrow(User User)
    {
        ContentValues val=new ContentValues();
        val.put("username", User.getuname());
        val.put("password", User.getpass());
        SQLiteDatabase db=getWritableDatabase();
        db.insert("authentication",null,val);
        db.close();
    }



    public boolean getpass(String uname,String password)
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.rawQuery("SELECT password FROM authentication WHERE username=\""+uname+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                     String newpass=Utils.getHashOfPassword(password);
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
        ArrayList<Rule> allarrayrules=new ArrayList<>();
        allarrayrules.addAll(getlocationcondition());
        allarrayrules.addAll(gettimecondition());
        allarrayrules.addAll(getweathercondition());
        return allarrayrules;
    }


    //To get all weather conditions
    public ArrayList<Rule> getweathercondition()
    {
        ArrayList<Rule> arrayweather=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  weathercondition.id,weathercondition.ruleid,weathercondition.mintemp,weathercondition.maxtemp,rules.rulename,rules.ruledes" +
                " FROM  weathercondition JOIN rules ON rules.id=weathercondition.ruleid;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                         Rule obj = new Rule();
                         WeatherCondition wcobj=new WeatherCondition();
                         obj.setid(cursor.getInt(cursor.getColumnIndex("id")));
                         obj.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                         obj.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                         wcobj.setMinTemp(cursor.getInt(cursor.getColumnIndex("mintemp")));
                         wcobj.setMaxTemp(cursor.getInt(cursor.getColumnIndex("maxtemp")));
                         obj.setBaseconditionobj(wcobj);
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
    public ArrayList<Rule> getlocationcondition()
    {
        ArrayList<Rule> arraylocation=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  locationcondition.id,locationcondition.ruleid,locationcondition.location,rules.rulename,rules.ruledes " +
                "FROM locationcondition JOIN rules ON rules.id=locationcondition.ruleid;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                        Rule obj=new Rule();
                        Locationcondition locobj = new Locationcondition();
                        obj.setid(cursor.getInt(cursor.getColumnIndex("id")));
                        obj.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                        obj.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                        locobj.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                        obj.setBaseconditionobj(locobj);
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
    public ArrayList<Rule> gettimecondition()
    {
        ArrayList<Rule> arraytime=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery = "SELECT  datetimecondition.id,datetimecondition.ruleid,datetimecondition.datetime,rules.rulename,rules.ruledes" +
                " FROM  datetimecondition JOIN rules ON rules.id=datetimecondition.ruleid;";
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                        Rule obj=new Rule();
                        Timecondition timeobj = new Timecondition();
                        obj.setid(cursor.getInt(cursor.getColumnIndex("id")));
                        obj.setrulename(cursor.getString(cursor.getColumnIndex("rulename")));
                        obj.setRuledesc(cursor.getString(cursor.getColumnIndex("ruledes")));
                        timeobj.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                        obj.setBaseconditionobj(timeobj);
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
    public void deleterule(Rule rule)
    {
        SQLiteDatabase db=getWritableDatabase();

        db.execSQL("DELETE FROM rules WHERE  rulename=\""+rule.getRulename()+"\";");
        Cursor c=db.rawQuery("SELECT rules.id FROM weathercondition JOIN rules WHERE rules.rulename=\""+rule.getRulename()+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    db.execSQL("DELETE FROM weathercondition WHERE ruleid=\""+c.getInt(c.getColumnIndex("id"))+"\";");

                }while (c.moveToNext());
            }
        }
        c=db.rawQuery("SELECT rules.id FROM locationcondition JOIN rules WHERE rules.rulename=\""+rule.getRulename()+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    db.execSQL("DELETE FROM locationcondition WHERE ruleid=\""+c.getInt(c.getColumnIndex("id"))+"\";");

                }while (c.moveToNext());
            }
        }


        c=db.rawQuery("SELECT rules.id FROM datetimecondition JOIN rules WHERE rules.rulename=\""+rule.getRulename()+"\";",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    db.execSQL("DELETE FROM  datetimecondition WHERE ruleid=\""+c.getInt(c.getColumnIndex("id"))+"\";");

                }while (c.moveToNext());
            }
        }
    }

}
