package com.alarm.darya.simplealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alarm.darya.simplealarm.model.Alarm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Calendar;


public class AlarmReceiver extends BroadcastReceiver {
    static Intent alarmScreenTask;
    Alarm runningAlarm;

    @Override
    public void onReceive(Context context, Intent intent) {
        readAlarmRunningData(intent);

        if (alarmScreenTask == null)
            alarmScreenTask = new Intent(context, AlarmScreenActivity.class);

        int dayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        dayIndex = (dayIndex == 1) ? 7 : dayIndex;

        boolean res = runningAlarm.getDaysOfWeek()[dayIndex - 2];
        //проверка - если время, на которое будильник установлен, больше текущего
        if (res || runningAlarm.isEveryDay() && (runningAlarm != null )) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("alarm", runningAlarm);
            alarmScreenTask.putExtras(bundle);
            context.startActivity(alarmScreenTask);
        }
    }

    void readAlarmRunningData(Intent intent) {
        ByteArrayInputStream bis = new ByteArrayInputStream(intent.getByteArrayExtra("alarm"));
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            this.runningAlarm = (Alarm)in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
