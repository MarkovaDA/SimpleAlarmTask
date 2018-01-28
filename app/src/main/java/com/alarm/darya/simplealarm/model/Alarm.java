package com.alarm.darya.simplealarm.model;
import java.io.Serializable;



public class Alarm implements Serializable {
    final int count = 7;

    String name;//название будильника
    Integer id;

    Boolean isOn;//включен ли будильник
    SignalType signalType; //тип сигнала будильника
    Integer timeHour; //час срабатывания
    Integer timeMinute; //время срабатывания

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

    public Alarm(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.isOn = false;//значение по умолчанию
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
        String hourView = getHourView(null);
        String minuteView = getMinuteView(null);
        return String.format(timeFormat, hourView, minuteView);
    }

    public String getMinuteView(Integer timeMinute) {
        if (timeMinute == null)
            timeMinute = this.timeMinute;

        if (timeMinute > 59)
            timeMinute = 59;

        if (timeMinute < 10) {
            return "0".concat(String.valueOf(timeMinute));
        }
        return String.valueOf(timeMinute);
    }

    public String getHourView(Integer timeHour) {
        if (timeHour == null)
            timeHour = this.timeHour;

        if (timeHour > 23)
            timeHour = 23;

        if (timeHour < 10) {
            return "0".concat(String.valueOf(timeHour));
        }
        return String.valueOf(timeHour);
    }

    private boolean isValidMinute() {
        int minute = this.timeMinute;
        return minute >= 0 && minute < 60;
    }

    private boolean isValidHour() {
        int hour = this.timeHour;
        return hour >= 0 && hour <= 23;
    }

    public boolean isValidTime() {
        return isValidHour() && isValidMinute();
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

    //звонить каждый день? (если день недели не указан)
    public boolean isEveryDay() {
        for(boolean day: daysOfWeek) {
            if (day)
                return false;
        }
        return true;
    }
}
