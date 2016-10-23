package com.bitsetd4d.controller;

import javafx.beans.property.ObjectProperty;

import java.util.List;

public interface HystrixExperimentConfiguration {

    void setHystrixConfig(List<String> configuration);
    List<String> getHystrixConfig();
    ObjectProperty<List<String>> hystrixConfigProperty();

}
