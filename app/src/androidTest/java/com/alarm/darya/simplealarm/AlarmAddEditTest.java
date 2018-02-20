package com.alarm.darya.simplealarm;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Categories.class)
@Suite.SuiteClasses({AlarmServiceTest.class, AlarmControlManagerTest.class})
public class AlarmAddEditTest {}
