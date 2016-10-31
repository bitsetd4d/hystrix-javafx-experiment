package com.bitsetd4d.view;

import com.bitsetd4d.util.JavaFxTestUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class SimpleViewControllerTest {

    static {
        JavaFxTestUtil.setupJavaFX();
    }

    private SimpleViewController controller;

    @Before
    public void setUp() throws Exception {
        controller = SimpleViewController.newInstance();
    }

    @Test
    public void ensureControlsInitialisedFromFxml() throws Exception {
        assertThat(controller.getTasksSlider(), notNullValue());
        assertThat(controller.getThreadsSlider(), notNullValue());
        assertThat(controller.getStartButton(), notNullValue());
        assertThat(controller.getStopButton(), notNullValue());
        assertThat(controller.getIdentityProgressIndicator(), notNullValue());
        assertThat(controller.getRunningLabel(), notNullValue());
        assertThat(controller.getFeedbackLabel(), notNullValue());
        assertThat(controller.getResultsTreeTableView(), notNullValue());
        assertThat(controller.getStateColumn(), notNullValue());
        assertThat(controller.getTimeColumn(), notNullValue());
        assertThat(controller.getThreadColumn(), notNullValue());
        assertThat(controller.getCommentColumn(), notNullValue());
        assertThat(controller.getParent(), notNullValue());
    }
}