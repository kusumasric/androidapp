package com.Alertapp;

/**
 * Created by kusumasri on 4/1/17.
 */

public class Rule {

    int id;
    String Rulename;
    String Ruledesc;
    public Rule()
    {

    }

    public Rule(String rulename,String ruledes)
    {
        Rulename=rulename;
        Ruledesc=ruledes;
    }

    public void setid(int id)
    {
        this.id=id;
    }

    public void setrulename(String rulename)
    {
        this.Rulename=rulename;
    }
    public void setRuledesc(String ruledesc)
    {
        this.Ruledesc=ruledesc;
    }

    public int getid()
    {
        return id;
    }

    public String getRulename()
    {
        return Rulename;
    }
    public String getRuledesc()
    {
        return Ruledesc;
    }

}
