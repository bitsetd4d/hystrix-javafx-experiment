package com.bitsetd4d.controller;

import javafx.beans.property.IntegerProperty;

public interface ExperimentConfiguration {

    void setThreads(int threads);
    int getThreads();
    IntegerProperty threadsProperty();

    void setTasks(int tasks);
    int getTasks();
    IntegerProperty tasksProperty();

}
