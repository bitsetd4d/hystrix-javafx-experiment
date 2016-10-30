package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.CommandResult;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ThrottledResultNotifierTest {

    private ThrottledResultNotifier notifier;

    @Before
    public void setUp() throws Exception {
        notifier = new ThrottledResultNotifier();
    }

    @Test
    public void fastCallbacksResultInSingleCallbackWithResults() throws Exception {
        // Given
        List<List<CommandResult>> testResults = new ArrayList<>();
        notifier.setListener((callbackResults) -> testResults.add(callbackResults));

        // When
        final int resultCount = 10;
        for (int i = 0; i< resultCount; i++) {
            notifier.recordResult(new CommandResult(1, CommandResult.State.OK));
        }
        sleep(200);

        // Then
        assertThat(testResults, hasSize(1));
        List<CommandResult> result0 = testResults.get(0);
        assertThat(result0, hasSize(resultCount));
    }

    @Test
    public void slowerCallbacksResultInSeveralCallbacksWithResults() throws Exception {
        // Given
        List<List<CommandResult>> testResults = new ArrayList<>();
        notifier.setListener((callbackResults) -> testResults.add(callbackResults));

        // When
        final int groupCount = 3;
        final int resultCount = 10;
        for (int i = 0; i < groupCount; i++) {
            for (int j = 0; j < resultCount; j++) {
                notifier.recordResult(new CommandResult(1, CommandResult.State.OK));
            }
            sleep(200);
        }

        // Then
        assertThat(testResults, hasSize(3));
        testResults.forEach(resultsList -> assertThat(resultsList, hasSize(resultCount)));
    }

}