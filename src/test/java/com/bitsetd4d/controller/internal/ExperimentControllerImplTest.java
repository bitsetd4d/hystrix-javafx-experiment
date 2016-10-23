package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import com.bitsetd4d.controller.TaskConfiguration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExperimentControllerImplTest {

    private ExperimentControllerImpl controller;

    @Before
    public void setUp() throws Exception {
        controller = new ExperimentControllerImpl();
    }

    @Test
    public void threadsProperty() throws Exception {
        IntegerProperty myProperty = new SimpleIntegerProperty();
        myProperty.bindBidirectional(controller.threadsProperty());

        myProperty.set(66);
        assertThat(controller.threadsProperty().get(), is(equalTo(66)));
        assertThat(controller.getThreads(), is(equalTo(66)));

        controller.threadsProperty().set(77);
        assertThat(myProperty.get(), is(equalTo(77)));
        assertThat(controller.getThreads(), is(equalTo(77)));

        controller.setThreads(88);
        assertThat(controller.threadsProperty().get(), is(equalTo(88)));
        assertThat(myProperty.get(), is(equalTo(88)));
    }

    @Test
    public void tasksProperty() throws Exception {
        IntegerProperty myProperty = new SimpleIntegerProperty();
        myProperty.bindBidirectional(controller.tasksProperty());

        myProperty.set(66);
        assertThat(controller.tasksProperty().get(), is(equalTo(66)));
        assertThat(controller.getTasks(), is(equalTo(66)));

        controller.tasksProperty().set(77);
        assertThat(myProperty.get(), is(equalTo(77)));
        assertThat(controller.getTasks(), is(equalTo(77)));

        controller.setTasks(88);
        assertThat(controller.tasksProperty().get(), is(equalTo(88)));
        assertThat(myProperty.get(), is(equalTo(88)));
    }

    @Test
    public void isRunningProperty() throws Exception {
        BooleanProperty myProperty = new SimpleBooleanProperty();
        myProperty.bind(controller.isRunningProperty());
        assertThat(controller.isRunning(), is(false));
        assertThat(myProperty.get(), is(false));

        controller.setRunning(true);
        assertThat(myProperty.get(), is(true));
        assertThat(controller.isRunning(), is(true));
    }

    @Test
    public void getTaskConfiguration() throws Exception {
        TaskConfiguration taskConfiguration = controller.getTaskConfiguration();
        assertThat(taskConfiguration, notNullValue());
        assertThat(taskConfiguration, sameInstance(controller.getTaskConfiguration()));
    }

    @Test
    public void getHystrixExperimentConfiguration() throws Exception {
        HystrixExperimentConfiguration configuration = controller.getHystrixExperimentConfiguration();
        assertThat(configuration, notNullValue());
        assertThat(configuration, sameInstance(controller.getHystrixExperimentConfiguration()));
    }

}