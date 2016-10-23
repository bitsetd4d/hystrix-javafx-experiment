package com.bitsetd4d.guice;

import com.bitsetd4d.controller.ExperimentController;
import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import com.bitsetd4d.controller.TaskConfiguration;
import com.bitsetd4d.controller.internal.ExperimentControllerImpl;
import com.bitsetd4d.controller.internal.HystrixExperimentConfigurationImpl;
import com.bitsetd4d.controller.internal.TaskConfigurationImpl;
import com.google.inject.AbstractModule;

public class ExperimentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExperimentController.class).to(ExperimentControllerImpl.class);
        bind(HystrixExperimentConfiguration.class).to(HystrixExperimentConfigurationImpl.class);
        bind(TaskConfiguration.class).to(TaskConfigurationImpl.class);
    }
}
