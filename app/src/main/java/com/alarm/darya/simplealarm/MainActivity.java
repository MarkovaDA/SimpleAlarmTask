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
    final int REQUEST_CODE_EDIT = 1;
    final int REQUEST_CODE_CREATE = 2;

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
            Intent intent = new Intent(MainActivity.this, AlarmEditActivity.class);
            //передаем объект будильника для редактирования
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedAlarm", alarms.get(position));
            intent.putExtras(bundle);
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent childIntent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_EDIT:
                    Bundle bundle = childIntent.getExtras();
                    Alarm editedAlarm = (Alarm)bundle.getSerializable("selectedAlarm");
                    //обновляем отредатированный будильник в списке
                    alarms.set(editedAlarm.getId(), editedAlarm);
                    //перерисовываем список элементов
                    alarmAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_CODE_CREATE:
                    //создание нового объекта-будильника и добавление в список
                    break;
            }
        }
        else {
            Toast.makeText(this, "Ошибка редактирования", Toast.LENGTH_SHORT).show();
        }
    }

    void initAlarms() {
        for(int i=0; i < 10;i++) {
            alarms.add(new Alarm("Alarm" + i, i, true));
        }
    }
}
