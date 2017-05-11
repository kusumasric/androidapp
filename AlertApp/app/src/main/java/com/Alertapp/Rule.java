package com.Alertapp;

/**
 * Created by kusumasri on 4/1/17.
 */

public class Rule {

    public int id;
    public String ruleName;
    public String ruleDesc;

    public Rule()
    {

    }

    public Rule(String rulename,String ruledes)
    {
        ruleName=rulename;
        ruleDesc=ruledes;
    }

    public void setid(int id)
    {
        this.id=id;
    }

    public void setrulename(String rulename)
    {
        this.ruleName=rulename;
    }
    public void setRuledesc(String ruledesc)
    {
        this.ruleDesc=ruledesc;
    }

    public int getid()
    {
        return id;
    }

    public String getRulename()
    {
        return ruleName;
    }
    public String getRuledesc()
    {
        return ruleDesc;
    }

}
