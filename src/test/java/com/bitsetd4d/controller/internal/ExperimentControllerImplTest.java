package com.bitsetd4d.controller.internal;

import com.bitsetd4d.controller.*;
import com.bitsetd4d.guice.ExperimentModule;
import com.bitsetd4d.util.JavaFxTestUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ExperimentControllerImplTest {

    private ExperimentController controller;

    static {
        JavaFxTestUtil.setupJavaFX();
    }

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new ExperimentModule());
        controller = injector.getInstance(ExperimentController.class);

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

    @Test
    public void getExperimentResults() throws Exception {
        assertThat(controller.experimentResultListProperty(), notNullValue());
    }

    @Test
    public void runningSimpleExperimentShouldProduceResults() throws Exception {
        final int taskCount = 2;
        final int runDelay = 10;
        final int fallbackDelay = 11;

        controller.getExperimentConfiguration().setTasks(taskCount);
        controller.getExperimentConfiguration().setThreads(1);
        controller.getTaskConfiguration().setRunDelay(runDelay);
        controller.getTaskConfiguration().setFallbackDelay(fallbackDelay);

        assertThat(controller.isRunning(), equalTo(false));
        assertThat(controller.experimentResultListProperty().get(), hasSize(0));

        controller.start();
        assertThat(controller.isRunning(), equalTo(true));

        sleep(2500);
        assertThat(controller.experimentResultListProperty().get(), hasSize(taskCount));

        controller.stop();
        assertThat(controller.isRunning(), equalTo(false));
        assertThat(controller.experimentResultListProperty().get(), hasSize(taskCount));
    }

    @Test
    public void runningLotsOfTasksShouldProduceResults() throws Exception {
        final int taskCount = 200;
        final int runDelay = 0;
        final int fallbackDelay = 0;

        controller.getExperimentConfiguration().setTasks(taskCount);
        controller.getExperimentConfiguration().setThreads(20);
        controller.getTaskConfiguration().setRunDelay(runDelay);
        controller.getTaskConfiguration().setFallbackDelay(fallbackDelay);

        assertThat(controller.isRunning(), equalTo(false));
        assertThat(controller.experimentResultListProperty().get(), hasSize(0));

        controller.start();
        assertThat(controller.isRunning(), equalTo(true));

        sleep(2500);
        assertThat(controller.experimentResultListProperty().get(), hasSize(taskCount));

        controller.stop();
        assertThat(controller.isRunning(), equalTo(false));
        assertThat(controller.experimentResultListProperty().get(), hasSize(taskCount));
    }
}