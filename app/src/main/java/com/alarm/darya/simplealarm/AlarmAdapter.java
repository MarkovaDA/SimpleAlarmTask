package com.alarm.darya.simplealarm;

import android.app.AlertDialog;
import android.content.Context;
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

/**
 * Created by Darya on 02.01.2018.
 */

public class AlarmAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    BaseAdapter currentAdapter = this;

    ArrayList<Alarm> alarms;
    int index = 0;

    AlarmAdapter(Context context, ArrayList<Alarm> alarms) {
        ctx = context;
        this.alarms = alarms;//список будильников
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ((TextView)view.findViewById(R.id.alarmName)).setText(alarm.getName());

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

    OnCheckedChangeListener onStatusChanged = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {
            getAlarm((Integer) button.getTag()).setOn(isChecked);
        }
    };

    View.OnClickListener onDeleteBtnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            //диалоговое окно подтверждения удаления
            alarms.remove((int)(v.getTag()));
            currentAdapter.notifyDataSetChanged();
        }
    };

}
