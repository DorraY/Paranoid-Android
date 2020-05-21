package com.example.paranoidandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("paranoid")){
            String medicineName = intent.getStringExtra("medicine");

            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification("", String.format(" $s ", medicineName));
            notificationHelper.getManager().notify(1, nb.build());
        }
    }
}
