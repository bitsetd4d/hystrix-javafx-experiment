package com.bitsetd4d.controller.internal;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExperimentMetricsImplTest {

    private ExperimentMetricsImpl metrics;
    private final int success = 1;
    private final int failures = 2;
    private final double mean = 3.0;
    private final double min = 1.5;
    private final double max = 5.5;

    @Before
    public void setUp() throws Exception {
        metrics = new ExperimentMetricsImpl(success, failures, mean, min, max);
    }

    @Test
    public void getSuccess() throws Exception {
        assertThat(metrics.getSuccess(), equalTo(success));
    }

    @Test
    public void getFailures() throws Exception {
        assertThat(metrics.getFailures(), equalTo(failures));
    }

    @Test
    public void getMeanExecution() throws Exception {
        assertThat(metrics.getMeanExecution(), equalTo(mean));
    }

    @Test
    public void getMinExecution() throws Exception {
        assertThat(metrics.getMinExecution(), equalTo(min));
    }

    @Test
    public void getMaxExecution() throws Exception {
        assertThat(metrics.getMaxExecution(), equalTo(max));
    }

}