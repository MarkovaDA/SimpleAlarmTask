package com.alarm.darya.simplealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

import com.alarm.darya.simplealarm.model.Alarm;
import com.alarm.darya.simplealarm.model.AlarmEnvironment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static android.content.Context.ALARM_SERVICE;


public class AlarmControlManager {

    private List<AlarmEnvironment> alarms;
    private AlarmManager alarmManager;
    private Context context;

    public AlarmControlManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarms = new ArrayList<>();
    }

    void createAlarm(Alarm alarm) {
        AlarmEnvironment alarmEnv = new AlarmEnvironment(context, alarm);
        alarms.add(alarmEnv);
    }

    //запустить будильник
    void setOnAlarm(int index) {
        AlarmEnvironment alarmEnv = alarms.get(index);
        Alarm alarm = alarmEnv.entityAlarm;
        PendingIntent alarmPending = alarmEnv.alarmPendingIntent;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getSchedule(alarm), alarmPending);
    }
    //отложить будильник (на 1 мин)
    void delayAlarm(int index) {
        AlarmEnvironment alarmEnv = alarms.get(index);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1 * 60 * 1000, alarmEnv.alarmPendingIntent);
    }

    //отменить будильник
    void cancelAlarm(int index) {
        AlarmEnvironment alarmEnv = alarms.get(index);
        PendingIntent alarmPendingIntent = alarmEnv.alarmPendingIntent;
        alarmManager.cancel(alarmPendingIntent);
    }

    void deleteAlarm(int index) {
        cancelAlarm(index);
        alarms.remove(index);
    }

    void editAlarm(int index) {
        //перезапуск будильника?
    }

    private Long getSchedule(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
        calendar.set(Calendar.MINUTE, alarm.getTimeMinute());
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        return calendar.getTimeInMillis();
    }
}
