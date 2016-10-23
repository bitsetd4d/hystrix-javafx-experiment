package com.bitsetd4d.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public interface TaskConfiguration {

    int getRunDelay();
    void setRunDelay(int delay);
    IntegerProperty runDelayProperty();

    void setRunException(boolean exception);
    boolean isRunException();
    BooleanProperty runExceptionProperty();

    int getFallbackDelay();
    void setFallbackDelay(int delay);
    IntegerProperty fallbackDelayProperty();

    void setFallbackException(boolean exception);
    boolean isFallbackException();
    BooleanProperty fallbackExceptionProperty();

}
