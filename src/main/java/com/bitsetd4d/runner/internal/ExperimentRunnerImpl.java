package com.bitsetd4d.runner.internal;

import com.bitsetd4d.controller.ExperimentMetrics;
import com.bitsetd4d.controller.internal.ExperimentMetricsImpl;
import com.bitsetd4d.runner.ExecutorServiceProvider;
import com.bitsetd4d.runner.ExperimentRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ExperimentRunnerImpl implements ExperimentRunner {

    private ExecutorServiceProvider executorServiceProvider;
    private ExecutorService executorService;

    private int threads = 1;
    private int tasks = 1;

    public ExperimentRunnerImpl(ExecutorServiceProvider executorServiceProvider) {
        this.executorServiceProvider = executorServiceProvider;
    }

    @Override
    public void start() {
        executorService = executorServiceProvider.newFixedThreadPool(threads);
        for (int i=0; i<tasks; i++) {
            scheduleNewTask();
        }
    }

    private void scheduleNewTask() {
        executorService.execute(() -> {}); // TODO
    }

    @Override
    public void stop() {

    }

    @Override
    public void setHystrixConfiguration(List<String> configuration) {

    }

    @Override
    public void setRunDelay(int delay) {

    }

    @Override
    public void setRunException(boolean exception) {

    }

    @Override
    public void setFallbackDelay(int delay) {

    }

    @Override
    public void setFallbackException(boolean exception) {

    }

    @Override
    public void setThreads(int threads) {
        if (threads <= 0) throw new IllegalArgumentException("threads must be > 0");
        this.threads = threads;
    }

    @Override
    public void setTasks(int tasks) {
        if (tasks <= 0) throw new IllegalArgumentException("tasks must be > 0");
        this.tasks = tasks;
    }

    @Override
    public ExperimentMetrics getMetrics() {
        return new ExperimentMetricsImpl(0, 0, 0, 0, 0);
    }
}