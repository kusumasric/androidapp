package com.Alertapp;

/**
 * Created by kusumasri on 2/12/17.
 */

public class User {
    public int id;
    public String uname;
    public String pass;


    public User(String uname, String pass)
    {
        this.uname=uname;
        this.pass=pass;

    }
    public void setid(int id)
    {
        this.id=id;
    }
    public void setuname(String uname)
    {
        this.uname=uname;
    }
    public void setpass(String pass)
    {
        this.pass=pass;
    }

    public int getid()
    {
        return id;
    }
    public String getuname()
    {
        return uname;
    }
    public String getpass()
    {
        return pass;
    }

}
