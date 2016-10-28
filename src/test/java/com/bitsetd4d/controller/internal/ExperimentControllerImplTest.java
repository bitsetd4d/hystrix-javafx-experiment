package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.ExperimentConfiguration;
import com.bitsetd4d.controller.ExperimentMetrics;
import com.bitsetd4d.controller.HystrixExperimentConfiguration;
import com.bitsetd4d.controller.TaskConfiguration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ExperimentControllerImplTest {

    private ExperimentControllerImpl controller;

    @Before
    public void setUp() throws Exception {
        controller = new ExperimentControllerImpl();
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

    @Test
    public void getExperimentConfiguration() throws Exception {
        ExperimentConfiguration configuration = controller.getExperimentConfiguration();
        assertThat(configuration, notNullValue());
        assertThat(configuration, sameInstance(controller.getExperimentConfiguration()));
    }

    @Test
    public void getExperimentMetrics() throws Exception {
        SimpleObjectProperty<ExperimentMetrics> myProperty = new SimpleObjectProperty<>();
        myProperty.bind(controller.experimentMetricsProperty());

        ExperimentMetrics experimentMetrics = myProperty.get();
        assertThat(experimentMetrics, is(notNullValue()));
        assertThat(controller.getExperimentMetrics(), equalTo(experimentMetrics));
        assertThat(controller.experimentMetricsProperty().get(), equalTo(experimentMetrics));
    }
}