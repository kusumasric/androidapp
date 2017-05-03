package com.Alertapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText Name,pass,conpass;
    public Context context;
    DataStorage dbhandler=new DataStorage(this);
    Intent location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name  = (EditText)findViewById(R.id.etName);
        pass    =(EditText)findViewById(R.id.etPass);
        conpass =(EditText)findViewById(R.id.etCnfPass);
        scheduleAlarm();

      //  location=new Intent(this,Locationservice.class);
       // startService(location);

    }

    public void scheduleAlarm() {

        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);

        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1*60*1000, pIntent);

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

    //onClick function here
    public void onclicksignup(View view)
    {
        //converting password to hash snd storing into DB
        //Starting another activity to get weather information.
        Intent intent = new Intent (getApplicationContext(),Home.class);
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
