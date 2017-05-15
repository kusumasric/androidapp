package com.Alertapp;

import java.text.ParseException;

/**
 * Created by kusumasri on 4/1/17.
 */

public class Rule {

    public int id;
    public String ruleName;
    public String ruleDesc;
    Basecondition baseconditionobj;

    public Rule()
    {

    }

    public Basecondition getBaseconditionobj() {
        return baseconditionobj;
    }

    public void setBaseconditionobj(Basecondition baseconditionobj) {
        this.baseconditionobj = baseconditionobj;
    }

    public Rule(String rulename, String ruledes, Basecondition bc)
    {
        ruleName=rulename;
        ruleDesc=ruledes;
        baseconditionobj=bc;
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
