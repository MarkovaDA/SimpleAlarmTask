package com.alarm.darya.simplealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

import com.alarm.darya.simplealarm.model.Alarm;
import com.alarm.darya.simplealarm.model.AlarmService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static android.content.Context.ALARM_SERVICE;


public class AlarmControlManager {

    private List<AlarmService> alarms;
    private AlarmManager alarmManager;
    private Context context;

    public AlarmControlManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarms = new ArrayList<>();
    }

    //добавить новый будильник
    void addAlarm(Alarm alarm) {
        //поднимаем контекст для будильника
        alarms.add(new AlarmService(context, alarm));
    }

    //запустить будильник - index - номер в списке
    void setOnAlarm(int index) {
        if (index >= alarms.size())
            return;

        AlarmService alarmService = alarms.get(index);
        Alarm alarm = alarmService.getEntityAlarm();
        PendingIntent alarmPending = alarmService.getAlarmNewPendingIntent();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getSchedule(alarm), alarmPending);
    }

    void setOnAlarm(Alarm alarm) {
        //если время будильника не валидное - включение не должно произойти
        if (!alarm.isValidTime())
            return;

       int index =  findAlarmIndexByEntity(alarm);

       AlarmService alarmService = alarms.get(index);
       PendingIntent alarmPending = alarmService.getAlarmNewPendingIntent();
       alarmManager.setExact(AlarmManager.RTC_WAKEUP, getSchedule(alarm), alarmPending);
    }

    private int findAlarmIndexByEntity(Alarm entity) {
        for(AlarmService service: alarms) {
            if (service.getEntityAlarm().equals(entity))
                return alarms.indexOf(service);
        }
        return -1;
    }

    //отложить будильник (на 1 мин)
    void delayAlarm(int index) {
        //метод не работает - отредактировать
        if (index >= alarms.size())
            return;
        AlarmService alarmEnv = alarms.get(index);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1 * 60 * 1000, alarmEnv.getAlarmNewPendingIntent());
    }

    //отменить будильник
    void cancelAlarm(int index) {
        if (index >= alarms.size())
            return;

        AlarmService alarmEnv = alarms.get(index);
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
        int index = findAlarmIndexByEntity(alarm);
        alarms.get(index).setEntityAlarm(alarm);
        alarms.get(index).writeAlarmDataToIntent();

        if (alarm.getOn()) {
            cancelAlarm(index);
            setOnAlarm(index);
        }
    }

    AlarmService getLastAddedAlarm() {
        return this.alarms.get(alarms.size() - 1);
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
