package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.ExperimentResult;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class ExperimentResultImpl implements ExperimentResult {

    private ReadOnlyStringWrapper stateProperty = new ReadOnlyStringWrapper(this, "state");
    private ReadOnlyStringWrapper threadProperty = new ReadOnlyStringWrapper(this, "thread");
    private ReadOnlyIntegerWrapper timeToExecuteProperty = new ReadOnlyIntegerWrapper(this, "timeToExecute");
    private ReadOnlyStringWrapper commentProperty = new ReadOnlyStringWrapper(this, "comment");

    public ExperimentResultImpl(String state, String thread, int timeToExecute, String comment) {
        stateProperty.set(state);
        threadProperty.set(thread);
        timeToExecuteProperty.set(timeToExecute);
        commentProperty.set(comment);
    }

    @Override
    public String getState() {
        return stateProperty.get();
    }

    @Override
    public ReadOnlyStringProperty stateProperty() {
        return stateProperty.getReadOnlyProperty();
    }

    @Override
    public String getThread() {
        return threadProperty.get();
    }

    @Override
    public ReadOnlyStringProperty threadProperty() {
        return threadProperty.getReadOnlyProperty();
    }

    @Override
    public int getTimeToExecute() {
        return timeToExecuteProperty.get();
    }

    @Override
    public ReadOnlyIntegerProperty timeToExecuteProperty() {
        return timeToExecuteProperty.getReadOnlyProperty();
    }

    @Override
    public String getComment() {
        return commentProperty.get();
    }

    @Override
    public ReadOnlyStringProperty commentProperty() {
        return commentProperty.getReadOnlyProperty();
    }
}