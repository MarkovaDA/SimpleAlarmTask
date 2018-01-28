package com.alarm.darya.simplealarm.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "alarm",active=true, createInDb = true)
public class AlarmEntity {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "hour")
    private Integer hour;

    @Property(nameInDb = "minute")
    private Integer minute;
    //тип мелодии
    //связать с днем недели
}
