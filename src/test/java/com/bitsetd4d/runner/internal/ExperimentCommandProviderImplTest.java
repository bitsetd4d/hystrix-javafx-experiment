package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.CommandResult;
import com.bitsetd4d.runner.CommandResult.State;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class ExperimentCommandProviderImplTest {

    private int maxTimeToExecute = 10;

    private String groupKey;
    private ExperimentCommandProviderImpl commandProvider;

    private int timeToExecute;
    private Exception exceptionRunningCommand;
    private HystrixCommand<CommandResult> commandUnderTest;
    private CommandResult commandResult;

    @Before
    public void setUp() throws Exception {
        groupKey = UUID.randomUUID().toString();
        ConfigurationManager.getConfigInstance().clear();
        com.netflix.hystrix.Hystrix.reset();

        commandProvider = new ExperimentCommandProviderImpl();
        exceptionRunningCommand = null;
        timeToExecute = 0;

        long t1 = System.currentTimeMillis();
        HystrixCommand<CommandResult> warmup = commandProvider.newCommand(groupKey, 0, false, 0, false);
        warmup.execute();
        maxTimeToExecute = 100 + (int)(System.currentTimeMillis() - t1);
    }

    private CommandResult timedRunCommand(Supplier<CommandResult> supplier) {
        long t1 = System.currentTimeMillis();
        try {
            return supplier.get();
        } catch (Exception e) {
            exceptionRunningCommand = e;
            return null;
        } finally {
            timeToExecute = (int) (System.currentTimeMillis() - t1);
        }
    }

    private Matcher<Integer> between(int minTime, int maxTime) {
        return is(both(greaterThanOrEqualTo(minTime)).and(lessThanOrEqualTo(maxTime)));
    }

    @Test
    public void newCommandThatExecutesNormally() throws Exception {
        commandUnderTest = commandProvider.newCommand(groupKey, 0, false, 0, false);
        commandResult = timedRunCommand(() -> commandUnderTest.execute());
        assertCommandExecutedNormallyBetweenTimes(0, maxTimeToExecute, State.OK, false);
    }

    @Test
    public void newCommandThatExecutesWithDelay() throws Exception {
        final int delay = 500;
        commandUnderTest = commandProvider.newCommand(groupKey, delay, false, 0, false);
        commandResult = timedRunCommand(() -> commandUnderTest.execute());
        assertCommandExecutedNormallyBetweenTimes(delay, delay + maxTimeToExecute, State.OK, false);
    }

    @Test
    public void newCommandWithDelayThatThrowsException() throws Exception {
        final int delay = 500;
        commandUnderTest = commandProvider.newCommand(groupKey, delay, true, 0, false);
        commandResult = timedRunCommand(() -> commandUnderTest.execute());
        assertCommandExecutedNormallyBetweenTimes(delay, delay + maxTimeToExecute, State.FALLBACK, false);
    }

    @Test
    public void newCommandThatThrowsExceptionSoCallsFallback() throws Exception {
        commandUnderTest = commandProvider.newCommand(groupKey, 0, true, 0, false);
        commandResult = timedRunCommand(() -> commandUnderTest.execute());
        assertCommandExecutedNormallyBetweenTimes(0, maxTimeToExecute, State.FALLBACK, false);
    }

    @Test
    public void newCommandWithFallbackThatExecutesWithDelay() throws Exception {
        final int fallbackDelay = 500;
        commandUnderTest = commandProvider.newCommand(groupKey, 0, true, fallbackDelay, false);
        commandResult = timedRunCommand(() -> commandUnderTest.execute());
        assertCommandExecutedNormallyBetweenTimes(fallbackDelay, fallbackDelay + maxTimeToExecute, State.FALLBACK, false);
    }

    @Test
    public void newCommandWithFallbackWithDelayThatThrowsException() throws Exception {
        final int fallbackDelay = 500;
        commandUnderTest = commandProvider.newCommand(groupKey, 0, true, fallbackDelay, true);
        commandResult = timedRunCommand(() -> commandUnderTest.execute());
        assertCommandExecutedNormallyBetweenTimes(fallbackDelay, fallbackDelay + maxTimeToExecute, State.FALLBACK, true);
    }

    private void assertCommandExecutedNormallyBetweenTimes(int minTime, int maxTime, State expectedState, boolean expectResult) {
        assertThat(timeToExecute, between(minTime, maxTime));
        assertThat(commandUnderTest.getExecutionTimeInMilliseconds(), between(minTime, maxTime));

        if (expectResult) {
            assertThat(commandResult, nullValue());
            assertThat(exceptionRunningCommand, not(nullValue()));
        } else {
            assertThat(commandResult, not(nullValue()));
            assertThat(commandResult.getState(), equalTo(expectedState));
            assertThat(commandResult.getThread(), not(nullValue()));
            assertThat(commandResult.getTimeToExecute(), between(minTime, maxTime));
            assertThat(exceptionRunningCommand, nullValue());
        }
        assertThat(commandUnderTest.getCommandGroup().name(), equalTo(groupKey));
        assertThat(commandUnderTest.isExecutionComplete(), equalTo(true));
    }

}