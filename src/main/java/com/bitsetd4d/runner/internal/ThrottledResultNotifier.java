package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.CommandResult;
import com.bitsetd4d.runner.ExperimentRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThrottledResultNotifier {

    public static final int DELAY_BETWEEN_NOTIFICATIONS_MS = 100;

    private ExperimentRunner.ResultsListener listener;

    private List<CommandResult> results = new ArrayList<>();
    private final Object resultsLock = new Object();
    private boolean notifyInFlight = false;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    void setListener(ExperimentRunner.ResultsListener listener) {
        this.listener = listener;
    }

    void recordResult(CommandResult result) {
        if (listener == null) {
            throw new RuntimeException("No listener set");
        }
        synchronized (resultsLock) {
            results.add(result);
            if (!notifyInFlight) {
                notifyInFlight = true;
                triggerNotification();
            }
        }
    }

    private void triggerNotification() {
        scheduledExecutorService.schedule(() -> notifyListeners(), DELAY_BETWEEN_NOTIFICATIONS_MS, TimeUnit.MILLISECONDS);
    }

    private void notifyListeners() {
        List<CommandResult> resultsToNotify = results;
        synchronized (resultsLock) {
            results = new ArrayList<>();
            notifyInFlight = false;
        }
        listener.onResults(resultsToNotify);
    }
}
