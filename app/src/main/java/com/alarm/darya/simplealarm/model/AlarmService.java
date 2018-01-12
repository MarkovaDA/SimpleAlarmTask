package com.alarm.darya.simplealarm.model;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.alarm.darya.simplealarm.AlarmReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class AlarmService {
    Intent alarmIntent;
    PendingIntent alarmPendingIntent;
    Alarm entityAlarm;

    private int intentId;
    private Context context;

    public AlarmService(Context ctx, Alarm alarm) {

        if (!isAlarmValid(alarm))
            return;

        context = ctx;
        entityAlarm = alarm;
        alarmIntent =
                new Intent(context, AlarmReceiver.class);

        writeAlarmDataToIntent();

        intentId = (int)System.currentTimeMillis();

        alarmPendingIntent = PendingIntent
                .getBroadcast(context, intentId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean isAlarmValid(Alarm alarm) {
        return (alarm.id == null || alarm.name == null || alarm.name != "");
    }

    public PendingIntent getAlarmNewPendingIntent() {
        intentId = (int)System.currentTimeMillis();
        alarmPendingIntent = PendingIntent
                .getBroadcast(context,
                        intentId,
                        alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        return alarmPendingIntent;
    }

    public PendingIntent getCurrentPendingIntent() {
        return alarmPendingIntent;
    }

    public Intent getAlarmIntent() {
        return alarmIntent;
    }

    public Alarm getEntityAlarm() {
        return entityAlarm;
    }

    public void setEntityAlarm(Alarm entityAlarm) {
        this.entityAlarm = entityAlarm;
    }

    public void setAlarmIntent(Intent alarmIntent) {
        this.alarmIntent = alarmIntent;
    }

    //метод записи в интенет должен вызываться и при каждом обновлении/редактировании будильника
    public void writeAlarmDataToIntent() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(entityAlarm);
            out.flush();
            byte[] data = bos.toByteArray();
            this.alarmIntent.putExtra("alarm", data);
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