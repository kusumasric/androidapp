package com.Alertapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kusumasri.Alertapp.R;

import static android.app.AlertDialog.*;

/**
 * Created by kusumasri on 2/19/17.
 */

public class SignIn extends Activity {
    EditText Name,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Name = (EditText) findViewById(R.id.Name);
        pass = (EditText) findViewById(R.id.password);
    }

    public void signinvalidation(View view) {
        String password = pass.getText().toString();
        String name = Name.getText().toString();
      /*  String hashpass;
         byte[] salt = {};
        try {
            salt = Utils.getSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        hashpass = Utils.Convertpasstohash(password, salt);*/
        DataStorage data =new DataStorage(this);
        boolean res=data.getpass(name,password);
        if(res) {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            intent.putExtra("name", Name.getText().toString());
            intent.putExtra("pass", pass.getText().toString());
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            //error handling of wrong name and wrong password in signup page
            Builder dlgAlert = new Builder(
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


}
