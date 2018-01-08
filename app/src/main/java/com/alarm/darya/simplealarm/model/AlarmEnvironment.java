package com.alarm.darya.simplealarm.model;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alarm.darya.simplealarm.AlarmReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


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

        /*Bundle extras = new Bundle();
        extras.putSerializable("alarm", alarm);
        alarmIntent.putExtras(extras);*/
        writeAlarmData();
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

    void writeAlarmData() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(entityAlarm);
            out.flush();
            byte[] data = bos.toByteArray();
            alarmIntent.putExtra("alarm", data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
