package com.bitsetd4d.controller;

import javafx.beans.property.ReadOnlyBooleanProperty;

public interface ExperimentController {

    boolean isRunning();
    ReadOnlyBooleanProperty isRunningProperty();

    ExperimentConfiguration getExperimentConfiguration();
    TaskConfiguration getTaskConfiguration();
    HystrixExperimentConfiguration getHystrixExperimentConfiguration();
}
