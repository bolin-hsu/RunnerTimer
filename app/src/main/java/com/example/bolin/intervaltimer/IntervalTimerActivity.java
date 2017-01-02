package com.example.bolin.intervaltimer;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IntervalTimerActivity extends AppCompatActivity {
    //private int millisPerMinute = 60 * 1000;
    private int millisPerMinute = 1000;

    private Handler handler = new Handler();

    // Interval length in milli-seconds
    private int[] alarmTimes = {
            5,  // run
            5,  // walk
            10, // run
            5,  // walk
            10, // run
            5,  // walk
    };

    private int totalOffset = 0;

    private int alarmTimeIndex = 0;

    class Alarm implements Runnable {
        private Integer offset;
        public Alarm(int offset) {
            this.offset = offset;
        }
        @Override
        public void run() {
            ((TextView)findViewById(R.id.textview_id)).setText(offset.toString());
            playSound();;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_timer);
        playSound();
        // Schedule all alarms
        for (int i = 0; i < alarmTimes.length; ++i) {
            totalOffset += alarmTimes[alarmTimeIndex++];
            handler.postDelayed(new Alarm(totalOffset), totalOffset * millisPerMinute);
        }
        // All alarms played, so destroying the current activity.
        totalOffset += 2;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, totalOffset * millisPerMinute);
    }

    public void playSound() {
        Resources resources = getResources();
        String packageName = getPackageName();
        int resID=resources.getIdentifier("ship", "raw", packageName);
        MediaPlayer mediaPlayer=MediaPlayer.create(this,resID);
        mediaPlayer.start();
    }
}
