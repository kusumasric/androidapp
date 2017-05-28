package com.Alertapp;

/**
 * Created by kusumasri on 2/12/17.
 */

public class User {
    public int id;
    public String userName;
    public String pass;

    public User(String uname, String pass)
    {
        this.userName =uname;
        this.pass=pass;
    }

    public String getuname()
    {
        return userName;
    }
    public String getpass()
    {
        return pass;
    }

}
