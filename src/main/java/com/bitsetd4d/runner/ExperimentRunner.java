package com.bitsetd4d.runner;

import com.bitsetd4d.controller.ExperimentMetrics;

import java.util.List;

public interface ExperimentRunner {

    void start();
    void stop();

    void setHystrixConfiguration(List<String> configuration);

    void setRunDelay(int delay);
    void setRunException(boolean exception);
    void setFallbackDelay(int delay);
    void setFallbackException(boolean exception);

    void setThreads(int threads);
    void setTasks(int tasks);

    ExperimentMetrics getMetrics();
}