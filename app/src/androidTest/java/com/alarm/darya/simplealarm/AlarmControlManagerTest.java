package com.alarm.darya.simplealarm;

import android.app.PendingIntent;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alarm.darya.simplealarm.model.Alarm;
import com.alarm.darya.simplealarm.model.AlarmEnvironment;

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
        //MockitoAnnotations.initMocks(this);
        appContext = InstrumentationRegistry.getTargetContext();
        this.alarmControlManager = new AlarmControlManager(appContext);
    }

    @Test
    public void addAlarmTest() throws Exception {
        int index = 0;
        //добавление тестового будильника
        Alarm alarm = new Alarm("Test alarm", index, false);
        alarmControlManager.addAlarm(alarm);
        assertEquals(alarmControlManager.getAlarmByIndex(index).entityAlarm, alarm);
    }

    @Test
    public void setOnAlarmTest() throws Exception {
        //добавление будильника
        int index = 0;
        Alarm alarm = new Alarm("Test alarm", index, false);
        alarmControlManager.addAlarm(alarm);
        alarmControlManager.setOnAlarm(index);
        //извлекаем entity добавленного будильника
        AlarmEnvironment alarmEnvironment = alarmControlManager.getAlarmByIndex(index);
        //проверяем, что служба будильника запущена
        assertTrue(PendingIntent
            .getBroadcast(
                appContext,
                0,
                alarmEnvironment.alarmIntent,
                PendingIntent.FLAG_NO_CREATE) !=  null
            );
    }

    @Test
    public void editAlarmTest() throws Exception {
        //Alarm alarm1
        //добавить будильник
        //Извлечь добавленный будильник
        //Изменить параметры, добавить новый
    }
}
