package com.bolin.android.intervaltimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTimer(View view) {
        Intent intent = new Intent(this, IntervalTimerActivity.class);
        // TODO Create GUI for user to edit the alarms array
        int[] alarms = {5, 5, 10, 5, 10, 5};
        intent.putExtra("alarms", alarms);
        startActivity(intent);
    }
}
