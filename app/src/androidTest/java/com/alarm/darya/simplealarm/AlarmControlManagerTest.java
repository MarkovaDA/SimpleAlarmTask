package com.alarm.darya.simplealarm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alarm.darya.simplealarm.model.Alarm;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    @Category(IntegrationTest.class)
    public void addValidAlarmTest() throws Exception {
        Alarm alarm = new Alarm("Test alarm", 132);
        alarm.setTimeMinute(5);
        alarm.setTimeHour(15);
        assertTrue(alarmControlManager.addAlarm(alarm));
    }

    @Test
    @Category(IntegrationTest.class)
    public void addNotValidAlarmTest1() {
        Alarm alarm = new Alarm("Test alarm1", 783);
        //не указываем время срабатывания будильника
        assertFalse(alarmControlManager.addAlarm(alarm));
    }

    @Test
    @Category(IntegrationTest.class)
    public void addNotValidAlarmTest2() {
        //добавляем пустой будильник - не должен добавиться
        assertFalse(alarmControlManager.addAlarm(null));
    }

    @Test
    @Category(IntegrationTest.class)
    public void addNotValidAlarmTest3() {
        Alarm alarm = new Alarm("Test alarm", 284);
        alarm.setTimeMinute(67);
        alarm.setTimeHour(153);
        //указываем некорректное время срабатывания
        assertFalse(alarmControlManager.addAlarm(alarm));
    }

    @Test
    @Category(IntegrationTest.class)
    public void editAlarmTest()  {

        Alarm alarm = new Alarm("Alarm for editing", 12);
        alarm.setTimeMinute(5);
        alarm.setTimeHour(10);
        alarmControlManager.addAlarm(alarm);

        //редактируем entity
        alarm.setTimeHour(12);
        alarm.setOn(true);

        alarmControlManager.editAlarm(alarm);

        //убеждаемся, что параметры будильника идентичны отредактированным
        assertEquals(alarmControlManager
                .getLastAddedAlarm().getEntityAlarm(), alarm);
    }


}
