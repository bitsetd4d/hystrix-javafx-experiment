package com.bitsetd4d.controller;

import com.bitsetd4d.controller.internal.ExperimentControllerImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class RunExperimentTest {

    private ExperimentController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ExperimentControllerImpl();
    }

    @Test
    public void startingExperimentShouldAffectRunningProperty() {
        assertThat(controller.isRunning(), equalTo(false));

        controller.start();
        assertThat(controller.isRunning(), equalTo(true));

        controller.stop();
        assertThat(controller.isRunning(), equalTo(false));
    }
}