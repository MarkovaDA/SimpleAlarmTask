package com.alarm.darya.simplealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlarmScreenActivity extends AppCompatActivity {

    Intent serviceIntent;
    Intent messageIntent;
    TextView txtAlarmTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtAlarmTitle = (TextView)findViewById(R.id.txtAlarmTitle);

        setContentView(R.layout.activity_alarm_screen);
        serviceIntent = new Intent(this, RingtonPlayingService.class);

        messageIntent = new Intent(MainActivity.MESSAGE_INTENT_ACTION_TITLE);
        startService(serviceIntent);
    }

    void onClickBtnWakeUp(View btn) {
        stopService(serviceIntent);
        sendBroadcast(messageIntent);
        //finish();
    }

    void onClickBtnDelay(View btn) {
        //отправить alarmIndex извлечь информацию из Bundle activity
        messageIntent.putExtra("ALARM_ACTION", AlarmActionType.ALARM_DELAY);
        stopService(serviceIntent);
        sendBroadcast(messageIntent);
        //finish();
    }
}
