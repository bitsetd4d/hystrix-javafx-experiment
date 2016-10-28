package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.ExperimentMetrics;

import java.util.Objects;

public class ExperimentMetricsImpl implements ExperimentMetrics {

    public static final ExperimentMetrics ZERO = new ExperimentMetricsImpl(0, 0, 0, 0, 0);

    private final int success;
    private final int failures;
    private final double mean;
    private final double min;
    private final double max;

    public ExperimentMetricsImpl(int success, int failures, double mean, double min, double max) {
        this.success = success;
        this.failures = failures;
        this.mean = mean;
        this.min = min;
        this.max = max;
    }

    @Override
    public int getSuccess() {
        return success;
    }

    @Override
    public int getFailures() {
        return failures;
    }

    @Override
    public double getMeanExecution() {
        return mean;
    }

    @Override
    public double getMinExecution() {
        return min;
    }

    @Override
    public double getMaxExecution() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentMetricsImpl that = (ExperimentMetricsImpl) o;
        return success == that.success &&
                failures == that.failures &&
                Double.compare(that.mean, mean) == 0 &&
                Double.compare(that.min, min) == 0 &&
                Double.compare(that.max, max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, failures, mean, min, max);
    }
}