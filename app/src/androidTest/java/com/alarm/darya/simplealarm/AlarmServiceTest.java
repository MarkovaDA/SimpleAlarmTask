package com.alarm.darya.simplealarm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alarm.darya.simplealarm.model.Alarm;
import com.alarm.darya.simplealarm.model.AlarmService;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)

public class AlarmServiceTest {
    Context appContext;

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    /*
    * Alarm - валидный (appContext)
    * context - null
    * */
    @Test
    @Category(IntegrationTest.class)
    public void createInstanceAlarmIsValidContextIsNullTest() {
        Alarm alarm = new Alarm("alarmTitle", 0);
        alarm.setTimeHour(18);
        alarm.setTimeMinute(32);

        AlarmService service = new AlarmService(null, alarm);
        assertFalse(service.isValid());
    }

    /*
    * Alarm - null
    * context - null
    * */
    @Test
    @Category(IntegrationTest.class)
    public void createInstanceAlarmIsNullContextIsNullTest() {
        AlarmService service = new AlarmService(null, null);
        assertFalse(service.isValid());
    }

    /*
    * Alarm - валидный
    * context - валидный (appContext)
    * */
    @Test
    @Category(IntegrationTest.class)
    public void createInstanceAlarmIsValidContextIsValidTest() {
        Alarm alarm = new Alarm("alarmTitle", 0);
        alarm.setTimeHour(18);
        alarm.setTimeMinute(32);
        AlarmService service = new AlarmService(appContext, alarm);
        assertTrue(service.isValid());
    }

    /*
   * Alarm - не валидный
   * context - валиднй (appСontext)
   * */
    @Test
    @Category(IntegrationTest.class)
    public void createInstanceAlarmIsNotValidContextIsValid() {
        Alarm alarm = new Alarm("alarmTitle", 0);
        alarm.setTimeHour(183);
        AlarmService service = new AlarmService(appContext, alarm);
        assertFalse(service.isValid());
    }
}
