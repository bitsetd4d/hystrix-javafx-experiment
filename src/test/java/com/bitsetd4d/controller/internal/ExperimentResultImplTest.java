package com.bitsetd4d.controller.internal;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ExperimentResultImplTest {

    @Test
    public void constructedValuesAccessedAsProperties() throws Exception {
        String expectedState = "state";
        String expectedThread = "thread";
        int expectedTimeToExecute = 99;
        String expectedComment = "comment";

        ExperimentResultImpl result = new ExperimentResultImpl(
                expectedState,
                expectedThread,
                expectedTimeToExecute,
                expectedComment);

        assertThat(result.getState(), equalTo(expectedState));
        assertThat(result.stateProperty().get(), equalTo(expectedState));

        assertThat(result.getThread(), equalTo(expectedThread));
        assertThat(result.threadProperty().get(), equalTo(expectedThread));

        assertThat(result.getTimeToExecute(), equalTo(expectedTimeToExecute));
        assertThat(result.timeToExecuteProperty().get(), equalTo(expectedTimeToExecute));

        assertThat(result.getComment(), equalTo(expectedComment));
        assertThat(result.commentProperty().get(), equalTo(expectedComment));
    }
}