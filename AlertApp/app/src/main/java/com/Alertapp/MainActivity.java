package com.Alertapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

// TODO: Follow naming format for activity classes and activity layout as per
// http://stackoverflow.com/questions/5582079/layout-files-naming-conventions
public class MainActivity extends AppCompatActivity {

    public EditText et_Name,et_Pass,et_Conpass;
    public Context context;
    public DataStorage dbhandler = new DataStorage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Name = (EditText)findViewById(R.id.et_Name);
        et_Pass = (EditText)findViewById(R.id.et_Pass);
        et_Conpass = (EditText)findViewById(R.id.et_CnfPass);
    }

    public void onclicksignup(View view)
    {
        Intent intent = new Intent (getApplicationContext(),HomePage.class);
        intent.putExtra("name",et_Name.getText().toString());
        String password = et_Pass.getText().toString();
        String confpass = et_Conpass.getText().toString();
        if(password.equals(confpass))
        {
            String hashpass=" ";
            hashpass=Utils.Convertpasstohash(password);
            User newuser = new User(et_Name.getText().toString(),hashpass.toString());
            dbhandler.addrow(newuser);
            startActivity(intent);
        }
        else
        {
            //TODO:put the below code in a function and use it in both place
            LoginErrorMessage();
        }
    }

    public void onclicksignin(View view)
    {
        String password = et_Pass.getText().toString();
        String name = et_Name.getText().toString();

        DataStorage data =new DataStorage(this);
        boolean res=data.getpass(name,password);
        if(res)
        {
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            intent.putExtra("name", et_Name.getText().toString());
            startActivity(intent);

        }
        else
        {
            LoginErrorMessage();

        }
    }

    public void exitApp(View view)
    {
        moveTaskToBack(true);
        finish();
    }

    public void LoginErrorMessage()
    {
        android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(this);
        dlgAlert.setMessage("wrong password or username");
        dlgAlert.setTitle("Error Message...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.show();
    }
    
    //TODO: below functions are un-necessary


}
