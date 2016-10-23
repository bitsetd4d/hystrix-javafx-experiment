package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.ExperimentController;
import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import com.bitsetd4d.controller.TaskConfiguration;
import com.google.common.annotations.VisibleForTesting;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleIntegerProperty;

public class ExperimentControllerImpl implements ExperimentController {

    private final IntegerProperty threadsProperty = new SimpleIntegerProperty();
    private final IntegerProperty tasksProperty = new SimpleIntegerProperty();
    private final ReadOnlyBooleanWrapper readOnlyBooleanWrapper = new ReadOnlyBooleanWrapper();
    private final TaskConfiguration taskConfiguration = new TaskConfigurationImpl();
    private final HystrixExperimentConfiguration hystrixExperimentConfiguration = new HystrixExperimentConfigurationImpl();

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

    // --------------------------------
    // Running
    // --------------------------------
    @Override
    public boolean isRunning() {
        return readOnlyBooleanWrapper.get();
    }

    @Override
    public ReadOnlyBooleanProperty isRunningProperty() {
        return readOnlyBooleanWrapper.getReadOnlyProperty();
    }

    @VisibleForTesting
    void setRunning(boolean running) {
        readOnlyBooleanWrapper.set(running);
    }

    // --------------------------------
    // Related Configuration
    // --------------------------------
    @Override
    public TaskConfiguration getTaskConfiguration() {
        return taskConfiguration;
    }

    @Override
    public HystrixExperimentConfiguration getHystrixExperimentConfiguration() {
        return hystrixExperimentConfiguration;
    }

}
