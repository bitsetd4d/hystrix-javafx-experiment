package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.ExecutorServiceProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentRunnerImplTest {

    @Mock
    private ExecutorServiceProvider mockExecutorServiceProvider;

    @Mock
    private ExecutorService mockExecutor;

    private ExperimentRunnerImpl runner;

    @Before
    public void setUp() throws Exception {
        runner = new ExperimentRunnerImpl(mockExecutorServiceProvider);
        when(mockExecutorServiceProvider.newFixedThreadPool(anyInt())).thenReturn(mockExecutor);
    }

    @Test
    public void start() throws Exception {
        runner.start();
    }

    @Test
    public void stop() throws Exception {

    }

    @Test
    public void setHystrixConfiguration() throws Exception {

    }

    @Test
    public void setRunDelay() throws Exception {

    }

    @Test
    public void setRunException() throws Exception {

    }

    @Test
    public void setFallbackDelay() throws Exception {

    }

    @Test
    public void setFallbackException() throws Exception {

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
        // When
        runner.start();
        // Then
        verify(mockExecutor, times(taskCount)).execute(any(Runnable.class));
    }


    @Test
    public void getMetrics() throws Exception {
        assertThat(runner.getMetrics(), is(notNullValue()));
    }

}