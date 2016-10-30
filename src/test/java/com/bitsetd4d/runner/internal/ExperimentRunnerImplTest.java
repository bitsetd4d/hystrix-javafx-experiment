package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.CommandResult;
import com.bitsetd4d.runner.ExecutorServiceProvider;
import com.bitsetd4d.runner.ExperimentCommandProvider;
import com.netflix.hystrix.HystrixCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentRunnerImplTest {

    @Mock
    private ExecutorServiceProvider mockExecutorServiceProvider;

    @Mock
    private ExperimentCommandProvider mockExperimentCommandProvider;

    @Mock
    private HystrixCommand<CommandResult> mockCommand;

    @Spy
    private ExecutorService executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private ExperimentRunnerImpl runner;

    @Before
    public void setUp() throws Exception {
        runner = new ExperimentRunnerImpl(mockExecutorServiceProvider, mockExperimentCommandProvider);
        when(mockExecutorServiceProvider.newFixedThreadPool(anyInt())).thenReturn(executor);
        when(mockExperimentCommandProvider.newCommand(anyString(), anyInt(), anyBoolean(), anyInt(), anyBoolean())).thenReturn(mockCommand);
        when(mockCommand.execute()).thenReturn(new CommandResult(1, CommandResult.State.OK));
    }

    @Test
    public void commandExecutedWithCorrectParameters() throws Exception {
        final int taskCount = 2;
        final int runDelay = 10;
        final int fallbackDelay = 11;
        List<List<CommandResult>> testResults = new ArrayList<>();
        runner.setListener(results -> testResults.add(results));
        runner.setTasks(taskCount);
        runner.setThreads(1);
        runner.setRunDelay(runDelay);
        runner.setFallbackDelay(fallbackDelay);
        runner.start();
        sleep(200);

        verify(mockExperimentCommandProvider, times(taskCount)).newCommand(anyString(), eq(runDelay), eq(false), eq(fallbackDelay), eq(false));

        assertThat(testResults, hasSize(1));
        assertThat(testResults.get(0), hasSize(taskCount));
    }

    @Test
    public void stopPreventsFurtherExecution() throws Exception {
        final int runDelay = 1000;
        final int taskCount = 10;
        final int fallbackDelay = 11;

        final Object waiter = new Object();

        when(mockCommand.execute()).then(invocation -> {
            waitOnNotificationOnObject(runDelay, waiter);
            return new CommandResult(2, CommandResult.State.OK);
        });

        List<List<CommandResult>> testResults = new ArrayList<>();
        runner.setListener(results -> testResults.add(results));
        runner.setTasks(taskCount);
        runner.setThreads(1);
        runner.setRunDelay(runDelay);
        runner.setFallbackDelay(fallbackDelay);
        runner.start();

        wakeupThreadsWaitingOnObject(waiter);
        sleep(100);

        runner.stop();
        sleep(2000);

        verify(mockExperimentCommandProvider, times(taskCount)).newCommand(anyString(), eq(runDelay), eq(false), eq(fallbackDelay), eq(false));

        assertThat(testResults, hasSize(1));
        assertThat(testResults.get(0), hasSize(1));
    }

    private void waitOnNotificationOnObject(int timeToWait, Object waiter) throws InterruptedException {
        synchronized (waiter) {
            waiter.wait(timeToWait);
        }
    }

    private void wakeupThreadsWaitingOnObject(Object waiter) {
        synchronized (waiter) {
            waiter.notifyAll();
        }
    }

    @Test
    public void setHystrixConfiguration() throws Exception {
        // TODO
    }

    @Test
    public void verifyThreadsAttributeUsedForThreadPool() throws Exception {
        // Given
        runner.setThreads(33);
        // When
        runner.start();
        // Then
        verify(mockExecutorServiceProvider, times(1)).newFixedThreadPool(33);
    }

    @Test(expected = IllegalArgumentException.class)
    public void threadsMustBeGreaterThanZero() throws Exception {
        runner.setThreads(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tasksMustBeGreaterThanZero() throws Exception {
        runner.setTasks(0);
    }

    @Test
    public void verifyTasksAttributeUsedForThreadPool() throws Exception {
        final int taskCount = 9;
        // Given
        runner.setTasks(taskCount);
        runner.setListener(results -> { /* no-op */ });
        // When
        runner.start();
        // Then
        verify(executor, times(taskCount)).execute(any(Runnable.class));
    }

    @Test
    public void getMetrics() throws Exception {
        assertThat(runner.getMetrics(), is(notNullValue()));
    }

}