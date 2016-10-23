package com.bitsetd4d.controller.internal;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExperimentConfigurationImplTest {

    private ExperimentConfigurationImpl configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ExperimentConfigurationImpl();
    }

    @Test
    public void threadsProperty() throws Exception {
        IntegerProperty myProperty = new SimpleIntegerProperty();
        myProperty.bindBidirectional(configuration.threadsProperty());

        myProperty.set(66);
        assertThat(configuration.threadsProperty().get(), is(equalTo(66)));
        assertThat(configuration.getThreads(), is(equalTo(66)));

        configuration.threadsProperty().set(77);
        assertThat(myProperty.get(), is(equalTo(77)));
        assertThat(configuration.getThreads(), is(equalTo(77)));

        configuration.setThreads(88);
        assertThat(configuration.threadsProperty().get(), is(equalTo(88)));
        assertThat(myProperty.get(), is(equalTo(88)));
    }

    @Test
    public void tasksProperty() throws Exception {
        IntegerProperty myProperty = new SimpleIntegerProperty();
        myProperty.bindBidirectional(configuration.tasksProperty());

        myProperty.set(66);
        assertThat(configuration.tasksProperty().get(), is(equalTo(66)));
        assertThat(configuration.getTasks(), is(equalTo(66)));

        configuration.tasksProperty().set(77);
        assertThat(myProperty.get(), is(equalTo(77)));
        assertThat(configuration.getTasks(), is(equalTo(77)));

        configuration.setTasks(88);
        assertThat(configuration.tasksProperty().get(), is(equalTo(88)));
        assertThat(myProperty.get(), is(equalTo(88)));
    }

}