package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.*;
import com.bitsetd4d.runner.CommandResult;
import com.bitsetd4d.runner.ExperimentRunner;
import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.stream.Collectors;

import static javafx.application.Platform.runLater;

public class ExperimentControllerImpl implements ExperimentController {

    private final ReadOnlyBooleanWrapper readOnlyBooleanWrapper = new ReadOnlyBooleanWrapper(this, "readOnly");
    private final ListProperty<ExperimentResult> experimentResultsProperty = new SimpleListProperty<>(this, "experimentResultsProperty", FXCollections.observableArrayList());

    private final ReadOnlyObjectWrapper<ExperimentMetrics> readOnlyExperimentMetricsWrapper
            = new ReadOnlyObjectWrapper<>(this, "experimentMetrics", ExperimentMetricsImpl.ZERO);

    private final ExperimentConfiguration experimentConfiguration = new ExperimentConfigurationImpl();
    private final TaskConfiguration taskConfiguration = new TaskConfigurationImpl();
    private final HystrixExperimentConfiguration hystrixExperimentConfiguration = new HystrixExperimentConfigurationImpl();

    private final ExperimentRunner experimentRunner;

    @Inject
    public ExperimentControllerImpl(ExperimentRunner experimentRunner) {
        this.experimentRunner = experimentRunner;
        experimentRunner.setListener(results -> onResults(results));
    }

    @Override
    public void start() {
        setRunning(true);
        configureRunner();
        experimentRunner.start();
    }

    private void configureRunner() {
        experimentRunner.setRunDelay(taskConfiguration.getRunDelay());
        experimentRunner.setRunException(taskConfiguration.isRunException());
        experimentRunner.setFallbackDelay(taskConfiguration.getFallbackDelay());
        experimentRunner.setFallbackException(taskConfiguration.isFallbackException());
        experimentRunner.setTasks(experimentConfiguration.getTasks());
        experimentRunner.setThreads(experimentConfiguration.getThreads());
        experimentRunner.setHystrixConfiguration(hystrixExperimentConfiguration.getHystrixConfig());
    }

    private void onResults(List<CommandResult> results) {
        List<ExperimentResult> experimentResults = results
                .stream()
                .map(r -> new ExperimentResultImpl(r.getState().name(), r.getThread(), r.getTimeToExecute(), ""))
                .collect(Collectors.toList());

        runLater(() -> experimentResultsProperty.addAll(experimentResults));
    }

    @Override
    public void stop() {
        setRunning(false);
        experimentRunner.stop();
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

    @Override
    public ListProperty<ExperimentResult> experimentResultListProperty() {
        return experimentResultsProperty;
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