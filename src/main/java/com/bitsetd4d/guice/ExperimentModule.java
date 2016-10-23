package com.bitsetd4d.guice;

import com.bitsetd4d.controller.ExperimentController;
import com.bitsetd4d.controller.internal.ExperimentControllerImpl;
import com.google.inject.AbstractModule;

public class ExperimentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExperimentController.class).to(ExperimentControllerImpl.class);
    }
}
