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

    public AlarmEnvironment(Context context, Alarm alarm) {
        entityAlarm = alarm;
        alarmIntent = new Intent(context, AlarmReceiver.class);

        Bundle extras = new Bundle();
        extras.putSerializable("alarm", alarm);
        alarmIntent.putExtras(extras);

        alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
