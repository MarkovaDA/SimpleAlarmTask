package com.alarm.darya.simplealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alarm.darya.simplealarm.model.Alarm;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView alarmList;//список созданных будильников
    ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    AlarmAdapter alarmAdapter;
    final String LOG_TAG = "LISTVIEW:";

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
            Intent intent = new Intent(MainActivity.this, AlarmEditActivity.class);
            //передаем объект будильника для редактирования
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedAlarm", alarms.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    void initAlarms() {
        for(int i=0; i < 10;i++) {
            alarms.add(new Alarm("Alarm" + i, i+1, true));
        }
    }
}
