package com.example.bolin.intervaltimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;

public class IntervalTimerActivity extends AppCompatActivity {
    private static String TAG = "IntervalTimerActivity";

    private static int millisPerMinute = 60 * 1000;
    //private static int millisPerMinute = 12 * 1000;

    /**
     * Arrange for the activity to return at a specific time.
     * Call finish() after calling this method().
     * This function can be called from anywhere that has a valid Context.
     */
    public static void scheduleWakeup(Context ctx, int[] alarms, int index) {
        long timeMillis = new Date().getTime() + alarms[index] * millisPerMinute;
        Log.d(TAG, "Scheduling wakeup for " + timeMillis);
        Intent intent = new Intent(ctx, IntervalTimerActivity.class);
        intent.putExtra("alarm", true);
        intent.putExtra("alarms", alarms);
        intent.putExtra("index", index);
        Log.d(TAG, "index " + index);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager mgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        mgr.cancel(pi);    // Cancel any previously-scheduled wakeups
        mgr.set(AlarmManager.RTC_WAKEUP, timeMillis, pi);

        // TODO Switch to the following which need API level 19,
        // currently our Galaxy Nexus is at API level 18.
        //mgr.setExact(AlarmManager.RTC_WAKEUP, timeMillis, pi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_timer);

        Intent intent = getIntent();
        int[] alarms = intent.getIntArrayExtra("alarms");
        int index = intent.getIntExtra("index", 0);
        if ( intent.getBooleanExtra("alarm", false)) {
            Log.d(TAG, "alarm TRUE ");
            index++;
        } else {
            Log.d(TAG, "alarm FALSE ");
        }
        playSound(index);

        if (index < alarms.length) {
            IntervalTimerActivity.scheduleWakeup(this, alarms, index);
        } else {
            Log.d(TAG, "done");
        }
        finish();
    }

    private String[] filenames = {
            "ship",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six"
    };

    public void playSound(int index) {
        Resources resources = getResources();
        String packageName = getPackageName();
        int resID=resources.getIdentifier(filenames[index], "raw", packageName);
        MediaPlayer mediaPlayer=MediaPlayer.create(this,resID);
        mediaPlayer.start();
    }
}
