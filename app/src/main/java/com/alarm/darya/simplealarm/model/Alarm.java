package com.alarm.darya.simplealarm.model;

import java.io.Serializable;



public class Alarm implements Serializable{
    String name;//название будильника
    Integer id;
    Boolean isOn;//включен ли будильник
    SignalType signalType; //тип сигнала будильника
    Boolean[] daysOfWeek;//дни недели,в которые будильник срабатывает
    String schedule; //время срабатывания (преобразовать к необходимому формату)

    public Alarm(String name, Integer id, Boolean isOn) {
        this.name = name;
        this.id = id;
        this.isOn = isOn;
        this.signalType = SignalType.Melody;//default
        daysOfWeek = new Boolean[7];
    }

    public String getSchedule() {
        return schedule;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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
}
