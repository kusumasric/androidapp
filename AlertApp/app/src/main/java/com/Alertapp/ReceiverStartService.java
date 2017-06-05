package com.Alertapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kusumasri on 4/22/17.
 */

public class ReceiverStartService extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentService = new Intent(context, ServiceCallsAsync.class);
        context.startService(intentService);
    }
}
