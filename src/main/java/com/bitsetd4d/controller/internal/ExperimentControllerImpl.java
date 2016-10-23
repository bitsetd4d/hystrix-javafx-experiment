package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.ExperimentConfiguration;
import com.bitsetd4d.controller.ExperimentController;
import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import com.bitsetd4d.controller.TaskConfiguration;
import com.google.common.annotations.VisibleForTesting;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class ExperimentControllerImpl implements ExperimentController {

    private final ReadOnlyBooleanWrapper readOnlyBooleanWrapper = new ReadOnlyBooleanWrapper();

    private final ExperimentConfiguration experimentConfiguration = new ExperimentConfigurationImpl();
    private final TaskConfiguration taskConfiguration = new TaskConfigurationImpl();
    private final HystrixExperimentConfiguration hystrixExperimentConfiguration = new HystrixExperimentConfigurationImpl();

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