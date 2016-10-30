package com.bitsetd4d.guice;

import com.bitsetd4d.controller.ExperimentController;
import com.bitsetd4d.controller.internal.ExperimentControllerImpl;
import com.bitsetd4d.runner.ExecutorServiceProvider;
import com.bitsetd4d.runner.ExperimentCommandProvider;
import com.bitsetd4d.runner.ExperimentRunner;
import com.bitsetd4d.runner.internal.ExperimentCommandProviderImpl;
import com.bitsetd4d.runner.internal.ExperimentRunnerImpl;
import com.bitsetd4d.runner.internal.StandardExecutorServiceProvider;
import com.google.inject.AbstractModule;

public class ExperimentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExperimentController.class).to(ExperimentControllerImpl.class);
        bind(ExperimentRunner.class).to(ExperimentRunnerImpl.class);
        bind(ExecutorServiceProvider.class).to(StandardExecutorServiceProvider.class);
        bind(ExperimentCommandProvider.class).to(ExperimentCommandProviderImpl.class);
    }
}
