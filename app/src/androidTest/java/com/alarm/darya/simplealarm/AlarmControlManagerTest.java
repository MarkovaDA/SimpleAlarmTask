package com.alarm.darya.simplealarm;

import android.app.PendingIntent;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alarm.darya.simplealarm.model.Alarm;
import com.alarm.darya.simplealarm.model.AlarmService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class AlarmControlManagerTest  {
    AlarmControlManager alarmControlManager;
    Context appContext;

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getTargetContext();
        alarmControlManager = new AlarmControlManager(appContext);
    }

    @Test
    public void addAlarmTest() throws Exception {
        Alarm alarm = new Alarm("Test alarm", 1);
        alarmControlManager.addAlarm(alarm);

        assertEquals(alarmControlManager
                .getLastAddedAlarm().getEntityAlarm(), alarm);
    }


    @Test
    public void editAlarmTest() throws Exception {

        Alarm alarm = new Alarm("Alarm for editing", 12);
        alarm.setTimeMinute(5);
        alarm.setTimeHour(10);

        alarmControlManager.addAlarm(alarm);
        //редактируем entity
        alarm.setTimeHour(12);
        alarm.setOn(true);
        //убеждаемся, что параметры будильника идентичны отредактированным
        alarmControlManager.editAlarm(alarm);

        assertEquals(alarmControlManager
                .getLastAddedAlarm().getEntityAlarm(), alarm);
    }

    @Test
    public void setOnAlarmTest() throws Exception {
        Alarm alarm =
                new Alarm("Test alarm", 1);
        //alarm.setTimeHour(12);
        //alarm.setTimeMinute(24);

        alarmControlManager.addAlarm(alarm);//добавляем будильник
        alarmControlManager.setOnAlarm(alarm);//включаем только что добавленный будильник

        AlarmService alarmService = alarmControlManager.getLastAddedAlarm();

        //проверяем, что служба данного будильника запущена
        assertTrue(PendingIntent
                .getBroadcast(appContext,
                        alarmService.getIntentId(),
                        alarmService.getAlarmIntent(),
                        PendingIntent.FLAG_NO_CREATE) !=  null
        );
    }

}
