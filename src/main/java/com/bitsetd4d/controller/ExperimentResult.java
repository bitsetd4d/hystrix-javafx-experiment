package com.bitsetd4d.controller;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;

public interface ExperimentResult {

    String getState();
    ReadOnlyStringProperty stateProperty();

    String getThread();
    ReadOnlyStringProperty threadProperty();

    int getTimeToExecute();
    ReadOnlyIntegerProperty timeToExecuteProperty();

    String getComment();
    ReadOnlyStringProperty commentProperty();

}