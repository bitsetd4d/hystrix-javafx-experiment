package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.ExperimentConfiguration;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ExperimentConfigurationImpl implements ExperimentConfiguration {

    private final IntegerProperty threadsProperty = new SimpleIntegerProperty();
    private final IntegerProperty tasksProperty = new SimpleIntegerProperty();

    // --------------------------------
    // Threads
    // --------------------------------
    @Override
    public final void setThreads(int threads) {
        threadsProperty.set(threads);
    }

    @Override
    public final int getThreads() {
        return threadsProperty.get();
    }

    @Override
    public IntegerProperty threadsProperty() {
        return threadsProperty;
    }

    // --------------------------------
    // Tasks
    // --------------------------------
    @Override
    public final void setTasks(int tasks) {
        tasksProperty.set(tasks);
    }

    @Override
    public final int getTasks() {
        return tasksProperty.get();
    }

    @Override
    public IntegerProperty tasksProperty() {
        return tasksProperty;
    }

}
