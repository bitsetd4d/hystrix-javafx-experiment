package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.*;
import com.google.common.annotations.VisibleForTesting;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class ExperimentControllerImpl implements ExperimentController {

    private final ReadOnlyBooleanWrapper readOnlyBooleanWrapper = new ReadOnlyBooleanWrapper();

    private final ReadOnlyObjectWrapper<ExperimentMetrics> readOnlyExperimentMetricsWrapper
            = new ReadOnlyObjectWrapper<>(ExperimentMetricsImpl.ZERO);

    private final ExperimentConfiguration experimentConfiguration = new ExperimentConfigurationImpl();
    private final TaskConfiguration taskConfiguration = new TaskConfigurationImpl();
    private final HystrixExperimentConfiguration hystrixExperimentConfiguration = new HystrixExperimentConfigurationImpl();

    @Override
    public void start() {
        setRunning(true);
    }

    @Override
    public void stop() {
        setRunning(false);
    }

    @Override
    public ReadOnlyObjectProperty<ExperimentMetrics> experimentMetricsProperty() {
        return readOnlyExperimentMetricsWrapper.getReadOnlyProperty();
    }

    @Override
    public ExperimentMetrics getExperimentMetrics() {
        return readOnlyExperimentMetricsWrapper.get();
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
    public ExperimentConfiguration getExperimentConfiguration() {
        return experimentConfiguration;
    }

    @Override
    public TaskConfiguration getTaskConfiguration() {
        return taskConfiguration;
    }

    @Override
    public HystrixExperimentConfiguration getHystrixExperimentConfiguration() {
        return hystrixExperimentConfiguration;
    }

}