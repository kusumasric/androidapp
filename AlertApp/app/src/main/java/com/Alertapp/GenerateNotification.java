package com.Alertapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;

/**
 * Created by kusumasri on 4/7/17.
 */

public class GenerateNotification extends IntentService {

    NotificationCompat.Builder notification;
    private static int num = 1;
    ArrayList<Locationcondition> rules = new ArrayList<>();
    DataStorage data = new DataStorage(this);

    public GenerateNotification() {
        super("GenerateNotification");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        rules = data.getlocationcondition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notification.setColor(color);
            notification.setSmallIcon(R.drawable.images);


        } else {
            notification.setSmallIcon(R.drawable.sjsulogo);
        }

        for (int i = 0; i < rules.size(); i++) {
            notification.setTicker(" kusuma");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle(rules.get(i).getLocation());
            notification.setContentText(rules.get(i).rule.getRuledesc());
            Intent[] intents = new Intent[1];
            intents[0] = new Intent(this, MainActivity.class);
            PendingIntent pintent = PendingIntent.getActivities(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pintent);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(num++, notification.build());



        }


    }
}