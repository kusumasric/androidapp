package com.example.kusumasri.backgroundservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.app.Activity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String str="kusuma";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(str,"this is created");
        Intent intent=new Intent(this,Weather2.class);
        startService(intent);

    }
    public void startService(View view) {
        startService(new Intent(getBaseContext(), Weather2.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), Weather2.class));
    }
}
