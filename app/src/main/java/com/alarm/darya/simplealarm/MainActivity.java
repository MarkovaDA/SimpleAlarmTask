package com.alarm.darya.simplealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alarm.darya.simplealarm.model.Alarm;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE_ALARM_EDIT = 1;
    final int REQUEST_CODE_ALARM_CREATE = 2;

    final static String MESSAGE_INTENT_ACTION_TITLE =
            "com.alarm.darya.simplealarm.ALARM_ACTION";

    ListView alarmList;
    ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    AlarmAdapter alarmAdapter;

    AlarmControlManager alarmControlManager;
    BroadcastReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initAlarms();
        registerMessageReceiver();

        alarmAdapter = new AlarmAdapter(this, alarms);
        alarmList = (ListView)(findViewById(R.id.alarmList));
        alarmList.setAdapter(alarmAdapter);
        alarmList.setClickable(true);
        //по клику на элементе будильника открываем форму редактирования
        alarmList.setOnItemClickListener(onListItemClick);
        alarmControlManager = new AlarmControlManager(this);
    }


    OnItemClickListener onListItemClick = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startEditAddActivity(REQUEST_CODE_ALARM_EDIT, alarms.get(position), "Редактирование будильника");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent childIntent) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = childIntent.getExtras();
            Alarm editedAlarm = (Alarm)bundle.getSerializable("selectedAlarm");
            switch (requestCode) {
                case REQUEST_CODE_ALARM_EDIT:
                    alarms.set(editedAlarm.getId(), editedAlarm);//редактирование будильника
                    alarmAdapter.notifyDataSetChanged();
                    alarmControlManager.editAlarm(editedAlarm);
                    break;
                case REQUEST_CODE_ALARM_CREATE:
                    alarms.add(editedAlarm);
                    alarmAdapter.notifyDataSetChanged();
                    alarmControlManager.addAlarm(editedAlarm);
                    break;
            }
        }
        else {
            Toast.makeText(this, "Действие не подтверждено", Toast.LENGTH_SHORT).show();
        }
    }

    void initAlarms() {
        for(int i=0; i < 5; i++) {
            alarms.add(new Alarm("Будильник " + i, i));
        }
    }

    void onBtnAddClicked(View button) {
        startEditAddActivity(REQUEST_CODE_ALARM_CREATE,
         new Alarm("Будильник " + alarms.size(), alarms.size()), "Создание будильника");
    }

    void startEditAddActivity(int code, Alarm entity, String action) {
        Intent intent = new Intent(MainActivity.this, AlarmEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedAlarm", entity);
        intent.putExtras(bundle);
        intent.putExtra("action", action);
        startActivityForResult(intent, code);
    }
    //регистрируем инициатор событий
    void registerMessageReceiver() {
        IntentFilter intentFilter = new IntentFilter(MESSAGE_INTENT_ACTION_TITLE);
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            int alarmAction = intent.getIntExtra("ALARM_ACTION", -1);
            int alarmIndex = intent.getIntExtra("alarmIndex", -1);
            //отложить будильник еще на 5 минут
            if (alarmAction == AlarmActionType.ALARM_DELAY.ordinal()) {
                alarmControlManager.delayAlarm(alarmIndex);
                showMessageToast("Будильник отложен на 5 минут!");
            }
            if (alarmAction == AlarmActionType.ALARM_DELETE.ordinal()) {
                alarmControlManager.deleteAlarm(alarmIndex);
                showMessageToast("Будильник успешно удален!");
            }
            if (alarmAction == AlarmActionType.ALARM_SET_ON.ordinal()) {
                alarmControlManager.setOnAlarm(alarmIndex);
                showMessageToast("Будильник включен!");
            }
            else if (alarmAction == AlarmActionType.ALARM_SET_OFF.ordinal()) {
                alarmControlManager.cancelAlarm(alarmIndex);
                showMessageToast("Будильник отключен!");
            }
            }
        };
        registerReceiver(messageReceiver, intentFilter);
    }

    void showMessageToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
