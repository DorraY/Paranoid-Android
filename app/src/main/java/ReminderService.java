import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.paranoidandroid.AlertReceiver;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class ReminderService extends JobService {
    private ReminderService reminderService;
    private static final String TAG = "Ceci est un Rappel";
    private boolean jobCancelled = false;
    private FirebaseAuth auth;
    private Object currentUser;
    private String msg;


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reminderService = new ReminderService();
        setDoseAlarm(params);

        return true;
    }
    private void setDoseAlarm(final JobParameters params) {
        for (int j=0; j<10;j++){
            Log.d(TAG , msg+":runing" +j);
                      Calendar c = Calendar.getInstance();

            c.set(Calendar.HOUR_OF_DAY,13);
            c.set(Calendar.MINUTE, 35+j);
            c.set(Calendar.SECOND,0);


            startAlarm(c , j+ msg+":alarm"+j);


        }
        Log.d(TAG, msg+";Job finished");


    }





    private void startAlarm(Calendar c,String message) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;

    }
}
