package com.bitsetd4d.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;

public interface ExperimentController {

    void setThreads(int threads);
    int getThreads();
    IntegerProperty threadsProperty();

    void setTasks(int tasks);
    int getTasks();
    IntegerProperty tasksProperty();

    boolean isRunning();
    ReadOnlyBooleanProperty isRunningProperty();

    TaskConfiguration getTaskConfiguration();
    HystrixExperimentConfiguration getHystrixExperimentConfiguration();
}
