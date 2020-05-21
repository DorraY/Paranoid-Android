package com.example.paranoidandroid.backgroundService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.paranoidandroid.AlertReceiver;
import com.example.paranoidandroid.Model.Medicine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.Date;


public class BackgroundService extends Service {


    final DatabaseReference programmeRef = FirebaseDatabase.getInstance().getReference("Medicament");

    ValueEventListener medListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot medSnapshot : dataSnapshot.getChildren()) {
                Medicine med = medSnapshot.getValue(Medicine.class);
                long startTimeStamp = med.getDateDebCons().getTime();
                Date startDate = new Date(startTimeStamp);
                Calendar cStart = Calendar.getInstance();
                cStart.setTime(startDate);
                long endTimeStamp = med.getDateEnd().getTime();
                Date endDate = new Date(endTimeStamp);
                Calendar cEnd = Calendar.getInstance();
                cEnd.setTime(endDate);
                while (cStart.before(cEnd)) {
                    startAlarm(cStart, med);
                    cStart.add(Calendar.DAY_OF_MONTH, 1);
                }
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    private void startAlarm(Calendar c, Medicine medicine) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.setAction("paranoid");
        intent.putExtra("medicine", medicine.getRefMed());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        programmeRef.addValueEventListener(medListener);
        return START_STICKY;
    }
}
