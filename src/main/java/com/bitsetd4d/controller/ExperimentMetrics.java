package com.bitsetd4d.controller;

public interface ExperimentMetrics {

    int getSuccess();
    int getFailures();

    double getMeanExecution();
    double getMinExecution();
    double getMaxExecution();

}
