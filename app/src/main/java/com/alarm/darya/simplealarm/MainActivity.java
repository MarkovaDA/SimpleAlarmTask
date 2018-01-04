package com.alarm.darya.simplealarm;

import android.content.Intent;
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

    ListView alarmList;
    ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAlarms();
        alarmAdapter = new AlarmAdapter(this, alarms);
        alarmList = (ListView)(findViewById(R.id.alarmList));
        alarmList.setAdapter(alarmAdapter);
        alarmList.setClickable(true);
        //по клику на элементе будильника открываем форму редактирования
        alarmList.setOnItemClickListener(onListItemClick);
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
                    break;
                case REQUEST_CODE_ALARM_CREATE:
                    alarms.add(editedAlarm); //добавление будильника
                    alarmAdapter.notifyDataSetChanged();
                    break;
            }
        }
        else {
            Toast.makeText(this, "Действие не подтверждено", Toast.LENGTH_SHORT).show();
        }
    }

    void initAlarms() {
        for(int i=0; i < 10;i++) {
            alarms.add(new Alarm("Будильник" + i, i, true));
        }
    }

    void onBtnAddClicked(View button) {
        startEditAddActivity(REQUEST_CODE_ALARM_CREATE,
                new Alarm("Будильник" + alarms.size(), alarms.size(), false), "Создание будильника");
    }

    void startEditAddActivity(int code, Alarm entity, String action) {
        Intent intent = new Intent(MainActivity.this, AlarmEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedAlarm", entity);
        intent.putExtras(bundle);
        intent.putExtra("action", action);
        startActivityForResult(intent, code);
    }
}
