package com.Alertapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.Alertapp.R;

public class MainActivity extends AppCompatActivity {

    EditText Name,pass,conpass;
    public Context context;
    DataStorage dbhandler=new DataStorage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name  = (EditText)findViewById(R.id.etName);
        pass    =(EditText)findViewById(R.id.etPass);
        conpass =(EditText)findViewById(R.id.etCnfPass);
        Intent intent=new Intent(this,Weather2.class);
        startService(intent);

    }

    //onClick function here
    public void onclicksignup(View view)
    {
        //converting password to hash snd storing into DB
        //Starting another activity to get weather information.
        Intent intent = new Intent (getApplicationContext(),Signup.class);
        intent.putExtra("name",Name.getText().toString());
        intent.putExtra("pass",pass.getText().toString());
        String password=pass.getText().toString();
        String confpass=conpass.getText().toString();
        if(password.equals(confpass))
        {
            String hashpass=" ";

            hashpass=Utils.Convertpasstohash(password);
            user newuser=new user(Name.getText().toString(),hashpass.toString());
            dbhandler.addrow(newuser);
            try {
                 startActivity(intent);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
           //error handling of wrong name and wrong password in signup page
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
                    this);

            dlgAlert.setMessage("wrong password or username");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.show();
            //RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main) ;
            //layout.addView(  );
             dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                        }
                    });
        }
    }

    public void onclicksignin(View view)
    {
        Intent intent = new Intent (getApplicationContext(),SignIn.class);
        try{
                startActivity(intent);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void exitApp(View view)
    {
        moveTaskToBack(true);
        finish();
    }



}
