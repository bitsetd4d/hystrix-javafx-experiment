package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class HystrixExperimentConfigurationImpl implements HystrixExperimentConfiguration {

    private final ObjectProperty<List<String>> hystrixConfigProperty = new SimpleObjectProperty<>(this, "hystrixConfig");

    @Override
    public final void setHystrixConfig(List<String> configuration) {
        hystrixConfigProperty.setValue(configuration);
    }

    @Override
    public final List<String> getHystrixConfig() {
        return hystrixConfigProperty.get();
    }

    @Override
    public ObjectProperty<List<String>> hystrixConfigProperty() {
        return hystrixConfigProperty;
    }
}
