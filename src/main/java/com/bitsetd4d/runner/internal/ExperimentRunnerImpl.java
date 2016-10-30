package com.bitsetd4d.runner.internal;

import com.bitsetd4d.controller.ExperimentMetrics;
import com.bitsetd4d.controller.internal.ExperimentMetricsImpl;
import com.bitsetd4d.runner.CommandResult;
import com.bitsetd4d.runner.ExecutorServiceProvider;
import com.bitsetd4d.runner.ExperimentCommandProvider;
import com.bitsetd4d.runner.ExperimentRunner;
import com.netflix.hystrix.HystrixCommand;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ExperimentRunnerImpl implements ExperimentRunner {

    private static String GROUP_KEY = "experiment";

    private ExecutorServiceProvider executorServiceProvider;
    private ExecutorService executorService;
    private ExperimentCommandProvider experimentCommandProvider;

    private int threads = 1;
    private int tasks = 1;
    private int runDelay;
    private boolean runException;
    private int fallbackDelay;
    private boolean fallbackException;

    private ThrottledResultNotifier resultNotifier = new ThrottledResultNotifier();

    public ExperimentRunnerImpl(ExecutorServiceProvider executorServiceProvider, ExperimentCommandProvider experimentCommandProvider) {
        this.executorServiceProvider = executorServiceProvider;
        this.experimentCommandProvider = experimentCommandProvider;
    }

    @Override
    public void start() {
        executorService = executorServiceProvider.newFixedThreadPool(threads);
        for (int i=0; i<tasks; i++) {
            scheduleNewTask();
        }
    }

    private void scheduleNewTask() {
        HystrixCommand<CommandResult> command = experimentCommandProvider.newCommand(GROUP_KEY, runDelay, runException, fallbackDelay, fallbackException);
        executeCommand(command);
    }

    private void executeCommand(HystrixCommand<CommandResult> command) {
        executorService.execute(() -> {
            CommandResult result = command.execute();
            resultNotifier.recordResult(result);
        });
    }

    @Override
    public void stop() {
        executorService.shutdownNow();
    }

    @Override
    public void setHystrixConfiguration(List<String> configuration) {
        // TODO
    }

    @Override
    public void setRunDelay(int runDelay) {
        this.runDelay = runDelay;
    }

    @Override
    public void setRunException(boolean runException) {
        this.runException = runException;
    }

    @Override
    public void setFallbackDelay(int fallbackDelay) {
        this.fallbackDelay = fallbackDelay;
    }

    @Override
    public void setFallbackException(boolean fallbackException) {
        this.fallbackException = fallbackException;
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

    @Override
    public void setListener(ResultsListener listener) {
        resultNotifier.setListener(listener);
    }

}