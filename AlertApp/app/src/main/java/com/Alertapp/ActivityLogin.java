package com.Alertapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {

    private EditText et_Name,et_Pass, et_ConPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Name = (EditText)findViewById(R.id.et_Name);
        et_Pass = (EditText)findViewById(R.id.et_Pass);
        et_ConPass = (EditText)findViewById(R.id.et_CnfPass);
    }

    public void onClickSignUp(View view)
    {
        String password = et_Pass.getText().toString();
        String confPass = et_ConPass.getText().toString();

        if(password.equals(confPass))
        {
            String hashPass=Utils.getHashOfPassword(password);
            User newUser = new User(et_Name.getText().toString(),hashPass.toString());
            DataStorage dbHandler = new DataStorage(this);
            dbHandler.addUser(newUser);
            Intent intent = new Intent (getApplicationContext(), ActivityHome.class);
            startActivity(intent);
        }
        else
        {
            LoginErrorMessage();
        }
    }

    public void onClickSignIn(View view)
    {
        String password = et_Pass.getText().toString();
        String name = et_Name.getText().toString();
        DataStorage data =new DataStorage(this);
        if(data.isUserPasswordValid(name,password))
        {
            Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
            startActivity(intent);
        }
        else
        {
            LoginErrorMessage();
        }
    }

    public void onBtnCancelClick(View view)
    {
        moveTaskToBack(true);
        finish();
    }

    private void LoginErrorMessage()
    {
        android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(this);
        dlgAlert.setMessage("wrong password or username");
        dlgAlert.setTitle("Error Message...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.show();
    }

}
