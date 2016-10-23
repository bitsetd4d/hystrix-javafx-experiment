package com.bitsetd4d.controller.internal;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TaskConfigurationImplTest {

    private TaskConfigurationImpl taskConfiguration;

    @Before
    public void setUp() throws Exception {
        taskConfiguration = new TaskConfigurationImpl();
    }

    @Test
    public void runDelayProperty() throws Exception {
        IntegerProperty myProperty = new SimpleIntegerProperty();
        myProperty.bindBidirectional(taskConfiguration.runDelayProperty());

        myProperty.set(22);
        assertThat(taskConfiguration.runDelayProperty().get(), is(equalTo(22)));
        assertThat(taskConfiguration.getRunDelay(), is(equalTo(22)));

        taskConfiguration.runDelayProperty().set(33);
        assertThat(myProperty.get(), is(equalTo(33)));
        assertThat(taskConfiguration.getRunDelay(), is(equalTo(33)));

        taskConfiguration.setRunDelay(44);
        assertThat(taskConfiguration.runDelayProperty().get(), is(equalTo(44)));
        assertThat(myProperty.get(), is(equalTo(44)));
    }

    @Test
    public void runExceptionProperty() throws Exception {
        BooleanProperty myProperty = new SimpleBooleanProperty();
        myProperty.bindBidirectional(taskConfiguration.runExceptionProperty());

        myProperty.set(false);
        assertThat(taskConfiguration.runExceptionProperty().get(), is(equalTo(false)));
        assertThat(taskConfiguration.isRunException(), is(equalTo(false)));

        taskConfiguration.runExceptionProperty().set(true);
        assertThat(myProperty.get(), is(equalTo(true)));
        assertThat(taskConfiguration.isRunException(), is(equalTo(true)));

        taskConfiguration.setRunException(false);
        assertThat(taskConfiguration.runExceptionProperty().get(), is(equalTo(false)));
        assertThat(taskConfiguration.isRunException(), is(equalTo(false)));
    }

    @Test
    public void fallbackDelayProperty() throws Exception {
        IntegerProperty myProperty = new SimpleIntegerProperty();
        myProperty.bindBidirectional(taskConfiguration.fallbackDelayProperty());

        myProperty.set(66);
        assertThat(taskConfiguration.fallbackDelayProperty().get(), is(equalTo(66)));
        assertThat(taskConfiguration.getFallbackDelay(), is(equalTo(66)));

        taskConfiguration.fallbackDelayProperty().set(77);
        assertThat(myProperty.get(), is(equalTo(77)));
        assertThat(taskConfiguration.getFallbackDelay(), is(equalTo(77)));

        taskConfiguration.setFallbackDelay(88);
        assertThat(taskConfiguration.fallbackDelayProperty().get(), is(equalTo(88)));
        assertThat(myProperty.get(), is(equalTo(88)));
    }

    @Test
    public void fallbackExceptionProperty() throws Exception {
        BooleanProperty myProperty = new SimpleBooleanProperty();
        myProperty.bindBidirectional(taskConfiguration.fallbackExceptionProperty());

        myProperty.set(false);
        assertThat(taskConfiguration.fallbackExceptionProperty().get(), is(equalTo(false)));
        assertThat(taskConfiguration.isFallbackException(), is(equalTo(false)));

        taskConfiguration.fallbackExceptionProperty().set(true);
        assertThat(myProperty.get(), is(equalTo(true)));
        assertThat(taskConfiguration.isFallbackException(), is(equalTo(true)));

        taskConfiguration.setFallbackException(false);
        assertThat(taskConfiguration.fallbackExceptionProperty().get(), is(equalTo(false)));
        assertThat(taskConfiguration.isFallbackException(), is(equalTo(false)));
    }

}