package com.alarm.darya.simplealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.alarm.darya.simplealarm.model.Alarm;


public class AlarmEditActivity extends AppCompatActivity {
    EditText txtAlarmTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);
        txtAlarmTitle = (EditText)findViewById(R.id.txtAlarmTitle);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Alarm selectedAlarm = (Alarm)bundle.getSerializable("selectedAlarm");
        txtAlarmTitle.setText(selectedAlarm.getName());
    }
}
