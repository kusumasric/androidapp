package com.Alertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.Alertapp.Home.*;
/**
 * Created by kusumasri on 4/1/17.
 */

public class Addrule extends Activity{

    EditText et_rulename,et_ruledesc;
    String rulename,ruledesc;
    DataStorage dbhandler=new DataStorage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.addnewrule);
        et_rulename=(EditText)findViewById(R.id.et_rulename);
        et_ruledesc=(EditText)findViewById(R.id.et_ruledesc);
    }

    public void onclickadd(View view)
    {
        rulename=et_rulename.getText().toString();
        ruledesc=et_ruledesc.getText().toString();
        Rule rulenew=new Rule(rulename,ruledesc);
        dbhandler.addRule(rulenew);
      /*  Home home1=new Home();
        home1.addRulestoListview();*/
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {

        finishActivity(1);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    public void onclickcancel(View view)
    {
            finish();

    }
}
