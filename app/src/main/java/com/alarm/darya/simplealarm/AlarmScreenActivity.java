package com.alarm.darya.simplealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alarm.darya.simplealarm.model.Alarm;

public class AlarmScreenActivity extends AppCompatActivity {

    Intent serviceIntent;
    Intent messageIntent;
    TextView txtAlarmTitle;
    TextView txtAlarmTime;

    Alarm runningAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm_screen);

        runningAlarm = (Alarm)getIntent().getExtras().getSerializable("alarm");
        txtAlarmTitle = (TextView)findViewById(R.id.txtAlarmTitle);
        txtAlarmTime = (TextView)findViewById(R.id.txtAlarmTime);

        txtAlarmTitle.setText(runningAlarm.getName());
        txtAlarmTime.setText(runningAlarm.getTimeView());

        serviceIntent = new Intent(this, RingtonPlayingService.class);
        messageIntent = new Intent(MainActivity.MESSAGE_INTENT_ACTION_TITLE);
        startService(serviceIntent);
    }

    void onClickBtnWakeUp(View btn) {
        stopService(serviceIntent);
        finish();
    }

    void onClickBtnDelay(View btn) {
        messageIntent.putExtra("alarmIndex", runningAlarm.getId());
        messageIntent.putExtra("ALARM_ACTION", AlarmActionType.ALARM_DELAY.ordinal());
        stopService(serviceIntent);
        sendBroadcast(messageIntent);
        finish();
    }
}
