package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.TaskConfiguration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TaskConfigurationImpl implements TaskConfiguration {

    private final IntegerProperty runDelayProperty = new SimpleIntegerProperty(this, "runDelay");
    private final BooleanProperty runExceptionProperty = new SimpleBooleanProperty(this, "runException");
    private final IntegerProperty fallbackDelayProperty = new SimpleIntegerProperty(this, "fallbackDelay");
    private final BooleanProperty fallbackExceptionProperty = new SimpleBooleanProperty(this, "fallbackException");

    // --------------------------------
    // Run Delay
    // --------------------------------
    @Override
    public final int getRunDelay() {
        return runDelayProperty.get();
    }

    @Override
    public final void setRunDelay(int delay) {
        runDelayProperty.set(delay);
    }

    @Override
    public IntegerProperty runDelayProperty() {
        return runDelayProperty;
    }

    // --------------------------------
    // Run Exception
    // --------------------------------
    @Override
    public final void setRunException(boolean exception) {
        runExceptionProperty.set(exception);
    }

    @Override
    public final boolean isRunException() {
        return runExceptionProperty.get();
    }

    @Override
    public BooleanProperty runExceptionProperty() {
        return runExceptionProperty;
    }

    // --------------------------------
    // Fallback Delay
    // --------------------------------
    @Override
    public int getFallbackDelay() {
        return fallbackDelayProperty.get();
    }

    @Override
    public void setFallbackDelay(int delay) {
        fallbackDelayProperty.set(delay);
    }

    @Override
    public IntegerProperty fallbackDelayProperty() {
        return fallbackDelayProperty;
    }

    // --------------------------------
    // Fallback Exception
    // --------------------------------
    @Override
    public void setFallbackException(boolean exception) {
        fallbackExceptionProperty.set(exception);
    }

    @Override
    public boolean isFallbackException() {
        return fallbackExceptionProperty.get();
    }

    @Override
    public BooleanProperty fallbackExceptionProperty() {
        return fallbackExceptionProperty;
    }
}
