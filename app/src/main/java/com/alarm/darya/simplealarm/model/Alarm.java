package com.alarm.darya.simplealarm.model;

import android.net.Uri;

import java.io.Serializable;



public class Alarm implements Serializable {
    final int count = 7;

    String name;//название будильника
    Integer id;
    Boolean isOn;//включен ли будильник
    SignalType signalType; //тип сигнала будильника

    Uri AlarmTone;

    int timeHour; //час срабатывания
    int timeMinute; //время срабатывания

    boolean[] daysOfWeek;//дни недели,в которые будильник срабатывает

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public Alarm(String name, Integer id, Boolean isOn) {
        this.name = name;
        this.id = id;
        this.isOn = isOn;
        this.signalType = SignalType.Melody;//default
        this.timeHour = 0;
        this.timeMinute = 0;
        daysOfWeek = new boolean[count];
    }

    public boolean[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getOn() {
        return isOn;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }

    public void setDayOfWeek(int index, boolean status) {
        this.daysOfWeek[index] = status;
    }

    //строковое представление времени
    public String getTimeView() {
        String timeFormat = "%s:%s";
        String hourView = String.valueOf(this.timeHour);
        String minuteView = String.valueOf(this.timeMinute);

        if (this.timeHour < 10) {
            hourView = "0" + hourView;

        }

        if (this.timeMinute < 10) {
            minuteView = "0" + minuteView;
        }

        return String.format(timeFormat, hourView, minuteView);
    }

    public String getDayOfWeekView() {
        String days[] = {" Пн", " Вт", " Ср", " Чт", " Пт", " Cб", " Вс",};
        String result = "";
        boolean[] week = this.getDaysOfWeek();

        for(int i=0; i < week.length; i++) {
            if (week[i]) {
                result += days[i];
            }
        }
        return result;
    }

    public boolean isEveryDay() {
        for(boolean day: daysOfWeek) {
            if (day)
                return false;
        }
        return true;
    }
}
