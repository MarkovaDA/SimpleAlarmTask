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

    private PendingIntent pendingIntent;

    public AlarmControlManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarms = new ArrayList<>();
    }

    void addAlarm(Alarm alarm) {
        AlarmEnvironment alarmEnv = new AlarmEnvironment(context, alarm);
        alarms.add(alarmEnv);
    }

    //запустить будильник
    void setOnAlarm(int index) {
        if (index >= alarms.size())
            return;

        AlarmEnvironment alarmEnv = alarms.get(index);
        Alarm alarm = alarmEnv.getEntityAlarm();
        PendingIntent alarmPending = alarmEnv.getAlarmNewPendingIntent();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                getSchedule(alarm),
                alarmPending);
    }

    //отложить будильник (на 1 мин)
    void delayAlarm(int index) {
        //метод не работает - отредактировать
        if (index >= alarms.size())
            return;
        AlarmEnvironment alarmEnv = alarms.get(index);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1 * 60 * 1000, alarmEnv.getAlarmNewPendingIntent());
    }

    //отменить будильник
    void cancelAlarm(int index) {
        if (index >= alarms.size())
            return;

        AlarmEnvironment alarmEnv = alarms.get(index);
        PendingIntent alarmPendingIntent =
                alarmEnv.getCurrentPendingIntent();
        alarmManager.cancel(alarmPendingIntent);
    }

    void deleteAlarm(int index) {
        if (index >= alarms.size())
            return;

        cancelAlarm(index);
        alarms.remove(index);
    }

    //редактировать будильник по индексу index
    void editAlarm(Alarm alarm) {
        AlarmEnvironment alarmEnv = alarms.get(alarm.getId());
        alarms.get(alarm.getId())
                .setEntityAlarm(alarm);
        //перезаписываем данные в интент (это не помогает)
        alarmEnv.writeAlarmDataToIntent();
        //если будильник был включен, включаем его согласно новому раписанию
        if (alarm.getOn()) {
            cancelAlarm(alarm.getId()); //отменяем старое расписание
            setOnAlarm(alarm.getId()); //возобнавляем новое
        }
        //перезаписть информацию в intent
    }

    AlarmEnvironment getAlarmByIndex(int index) {
        return this.alarms.get(index);
    }

    private Long getSchedule(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
        calendar.set(Calendar.MINUTE, alarm.getTimeMinute());
        calendar.set(Calendar.SECOND, 00);
        return calendar.getTimeInMillis();
    }
}
