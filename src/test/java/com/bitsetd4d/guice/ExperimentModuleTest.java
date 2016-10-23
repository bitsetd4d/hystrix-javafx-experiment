package com.bitsetd4d.guice;

import com.bitsetd4d.controller.ExperimentController;
import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import com.bitsetd4d.controller.TaskConfiguration;
import com.bitsetd4d.controller.internal.ExperimentControllerImpl;
import com.bitsetd4d.controller.internal.HystrixExperimentConfigurationImpl;
import com.bitsetd4d.controller.internal.TaskConfigurationImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ExperimentModuleTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ExperimentModule());
    }

    @Test
    public void createsExperimentController() throws Exception {
        ExperimentController instance = injector.getInstance(ExperimentController.class);
        assertThat(instance, instanceOf(ExperimentControllerImpl.class));
    }

    @Test
    public void createsHystrixExperimentConfiguration() throws Exception {
        HystrixExperimentConfiguration instance = injector.getInstance(HystrixExperimentConfiguration.class);
        assertThat(instance, instanceOf(HystrixExperimentConfigurationImpl.class));
    }

    @Test
    public void createsTaskConfiguration() throws Exception {
        TaskConfiguration instance = injector.getInstance(TaskConfiguration.class);
        assertThat(instance, instanceOf(TaskConfigurationImpl.class));
    }
}