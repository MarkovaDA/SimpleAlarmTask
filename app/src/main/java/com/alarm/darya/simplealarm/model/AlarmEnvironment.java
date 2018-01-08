package com.alarm.darya.simplealarm.model;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alarm.darya.simplealarm.AlarmReceiver;


public class AlarmEnvironment {
    public Intent alarmIntent;
    public PendingIntent alarmPendingIntent;
    public Alarm entityAlarm;

    private int intentId;
    private Context context;

    public AlarmEnvironment(Context context, Alarm alarm) {
        entityAlarm = alarm;
        alarmIntent = new Intent(context, AlarmReceiver.class);
        this.context = context;

        Bundle extras = new Bundle();
        extras.putSerializable("alarm", alarm);
        alarmIntent.putExtras(extras);

        intentId = (int)System.currentTimeMillis();

        alarmPendingIntent = PendingIntent
                .getBroadcast(context, intentId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent getAlarmPendingIntent() {
        intentId = (int)System.currentTimeMillis();

        alarmPendingIntent = PendingIntent
                .getBroadcast(context, intentId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return alarmPendingIntent;
    }
}
