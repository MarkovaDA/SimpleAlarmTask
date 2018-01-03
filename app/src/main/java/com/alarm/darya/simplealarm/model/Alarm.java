package com.alarm.darya.simplealarm.model;


import java.io.Serializable;

public class Alarm implements Serializable{
    String name;
    Integer id;
    Boolean isOn;
    //время
    //дни недели - enums
    //тип мелодии
    public Alarm(String name, Integer id, Boolean isOn) {
        this.name = name;
        this.id = id;
        this.isOn = isOn;
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
}
