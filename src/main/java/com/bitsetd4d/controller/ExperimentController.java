package com.bitsetd4d.controller;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

public interface ExperimentController {

    void start();
    void stop();

    ReadOnlyObjectProperty<ExperimentMetrics> experimentMetricsProperty();
    ExperimentMetrics getExperimentMetrics();

    boolean isRunning();
    ReadOnlyBooleanProperty isRunningProperty();

    ExperimentConfiguration getExperimentConfiguration();
    TaskConfiguration getTaskConfiguration();
    HystrixExperimentConfiguration getHystrixExperimentConfiguration();

}
