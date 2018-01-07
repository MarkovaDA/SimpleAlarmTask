package com.alarm.darya.simplealarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.alarm.darya.simplealarm.model.Alarm;

import java.util.ArrayList;


public class AlarmAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    BaseAdapter currentAdapter = this;
    AlertDialog.Builder modal;

    ArrayList<Alarm> alarms;
    int alarmIndexForRemove;

    Intent messageIntent;

    AlarmAdapter(Context context, ArrayList<Alarm> alarms) {
        this.context = context;
        this.alarms = alarms;//список будильников
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        messageIntent = new Intent(MainActivity.MESSAGE_INTENT_ACTION_TITLE);

        modal = new AlertDialog.Builder(context);
        modal.setMessage("Вы уверены что хотите удалить этот будильник?");
        modal.setPositiveButton("ОК", dialogClickListener);
        modal.setNegativeButton("отмена", dialogClickListener);
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        Alarm alarm = getAlarm(position);//текущий отображаемый будильник

        ((TextView)view.findViewById(R.id.alarmName))
                .setText(alarm.getName());

        ((TextView)view.findViewById(R.id.alarmTimeTxt))
                .setText(alarmTimeView(alarm.getTimeHour(), alarm.getTimeMinute()));

        ((TextView)view.findViewById(R.id.alarmDaysOfWeeks))
                .setText(alarmDayOfWeekView(alarm));

        CheckBox chStatus = (CheckBox) view.findViewById(R.id.alarmStatus);
        chStatus.setOnCheckedChangeListener(onStatusChanged);
        chStatus.setTag(position);
        chStatus.setChecked(alarm.getOn());

        Button delButton = ((Button)view.findViewById(R.id.alarmDeleteBtn));
        this.notifyDataSetChanged();
        delButton.setTag(position);
        delButton.setOnClickListener(onDeleteBtnClickListener);
        return view;
    }

    Alarm getAlarm(int position) {
        return ((Alarm) getItem(position));
    }

    void removeAlarm(int position) {
        alarms.remove(position);
        //отправить сигнал о том, что будильник удален
        currentAdapter.notifyDataSetChanged();
    }

    OnCheckedChangeListener onStatusChanged = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {
            int alarmIndex = (int)button.getTag();
            getAlarm(alarmIndex).setOn(isChecked);
            messageIntent.putExtra("alarmIndex", alarmIndex);
            if (isChecked) {
                //включить будильник
                messageIntent.putExtra("ALARM_ACTION", AlarmActionType.ALARM_SET_ON.ordinal());
            }
            else {
                //выключить будильник
                messageIntent.putExtra("ALARM_ACTION", AlarmActionType.ALARM_SET_OFF.ordinal());
            }
            context.sendBroadcast(messageIntent);
        }
    };

    View.OnClickListener onDeleteBtnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
        alarmIndexForRemove = (int)(v.getTag());
        modal.show();
        }
    };


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                removeAlarm(alarmIndexForRemove);
                //оповещаем MainActivity о запросе удаления будильника
                messageIntent.putExtra("ALARM_ACTION", AlarmActionType.ALARM_DELETE.ordinal());
                //передаем индекс удаляемого будильника
                messageIntent.putExtra("alarmIndex", alarmIndexForRemove);
                context.sendBroadcast(messageIntent);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
            }
        }
    };

    String alarmTimeView(int hour, int minute) {
        String hourView = String.valueOf(hour);
        String minuteView = String.valueOf(minute);
        String timeView = "%s:%s";

        if (hour < 10) {
            hourView = "0" + hour;
        }
        if (minute < 10) {
            minuteView = "0" + minute;
        }
        return String.format(timeView, hourView, minuteView);
    }

    String alarmDayOfWeekView(Alarm alarm) {
        String days[] = {" Пн", " Вт", " Ср", " Чт", " Пт", " Cб", "Вс"};
        String result = "";
        boolean[] week = alarm.getDaysOfWeek();

        for(int i=0; i < week.length; i++) {
            if (week[i]) {
                result += days[i];
            }
        }
        return result;
    }
}
