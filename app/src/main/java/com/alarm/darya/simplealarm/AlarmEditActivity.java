package com.alarm.darya.simplealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alarm.darya.simplealarm.model.Alarm;
import com.alarm.darya.simplealarm.model.SignalType;

import static java.lang.Integer.parseInt;


public class AlarmEditActivity extends AppCompatActivity {
    EditText txtAlarmTitle;

    EditText txtAlarmHour;
    EditText txtAlarmMinute;

    TextView txtWindowTitle;
    Spinner spinnerSignalType;
    Button btnSave;
    Alarm selectedAlarm;
    String windowTitle;

    CheckBox chbMon, chbTue, chbWed, chbThu, chbFru, chbSat, chbSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedAlarm = (Alarm)bundle.getSerializable("selectedAlarm");
        windowTitle = intent.getStringExtra("action");
        txtWindowTitle = (TextView)findViewById(R.id.windowTitle);
        txtWindowTitle.setText(windowTitle);

        txtAlarmTitle = (EditText)findViewById(R.id.txtAlarmTitle);
        txtAlarmTitle.setText(selectedAlarm.getName());

        txtAlarmHour = (EditText)findViewById(R.id.txtAlarmHour);
        txtAlarmHour.setText(selectedAlarm.getHourView());

        txtAlarmMinute = (EditText)findViewById(R.id.txtAlarmMinute);
        txtAlarmMinute.setText(selectedAlarm.getMinuteView());

        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(onBtnSaveClick);

        setSpinnerInitStatus();
        setCheckboxesInitStatus();
    }
    //действия при нажатии кнопки "сохранить"
    View.OnClickListener onBtnSaveClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (selectedAlarm == null) {
                return;
            }
            else {
                //cоздаём новую сущность будильника и передаем ее родительской активити для добавления
                selectedAlarm.setName(txtAlarmTitle.getText().toString());
                String minuteInput = txtAlarmHour.getText().toString();
                String hourInput = txtAlarmMinute.getText().toString();

                if (!isInteger(minuteInput) || !isInteger(hourInput)) {
                    notifyErrorAlarmCreation();
                    return;
                }

                selectedAlarm.setTimeHour(parseInt(minuteInput));
                selectedAlarm.setTimeMinute(parseInt(hourInput));

                //какие-то параметры будильника заполнены некорректно
                if (!selectedAlarm.isValid()) {
                    notifyErrorAlarmCreation();
                    return;
                }

                Intent currentIntent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedAlarm", selectedAlarm);
                currentIntent.putExtras(bundle);
                setResult(RESULT_OK, currentIntent);
                finish();
            }
        }
    };
    //выбор типа мелодии
    AdapterView.OnItemSelectedListener onSpinnerItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = (String)parent.getItemAtPosition(position);
            selectedAlarm.setSignalType(SignalType.valueOf(item));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    //выбор дня недели срабатывания
    void onCheckboxClicked(View view) {
        CheckBox chbDayOfWeek = (CheckBox)view;
        boolean checked = chbDayOfWeek.isChecked();
        Object tag = chbDayOfWeek.getTag();
        int number = parseInt(tag.toString());
        selectedAlarm.setDayOfWeek(number, checked);
    }

    void setCheckboxesInitStatus() {
        chbMon = (CheckBox)findViewById(R.id.chMon);
        chbTue = (CheckBox)findViewById(R.id.chTue);
        chbWed = (CheckBox)findViewById(R.id.chWed);
        chbThu = (CheckBox)findViewById(R.id.chThu);
        chbFru = (CheckBox)findViewById(R.id.chFru);
        chbSat = (CheckBox)findViewById(R.id.chSat);
        chbSun = (CheckBox)findViewById(R.id.chSun);

        CheckBox[] allChb = {chbMon, chbTue,chbWed, chbThu, chbFru, chbSat, chbSun};
        boolean[] daysOfWeek = selectedAlarm.getDaysOfWeek();
        boolean day;
        for(int i=0; i < daysOfWeek.length; i++) {
            day = daysOfWeek[i];
            if (day) {
                allChb[i].setChecked(day);
            }
        }
    }

    void setSpinnerInitStatus() {
        spinnerSignalType = (Spinner)findViewById(R.id.spinnerSignalType);

        ArrayAdapter<CharSequence> spinnerAdapter =
            ArrayAdapter.createFromResource(this, R.array.signal_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSignalType.setAdapter(spinnerAdapter);
        spinnerSignalType.setOnItemSelectedListener(onSpinnerItemSelect);
        spinnerSignalType.setSelection(selectedAlarm.getSignalType().ordinal());
    }

    void onTimeSelectBtnClicked(View btn) {
        Intent intent = new Intent(AlarmEditActivity.this, TimeSelectActivity.class);
        startActivity(intent);
    }

    private void notifyErrorAlarmCreation() {
        Toast.makeText( AlarmEditActivity.this,
                "Параметры будильника некорректны", Toast.LENGTH_SHORT).show();
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
