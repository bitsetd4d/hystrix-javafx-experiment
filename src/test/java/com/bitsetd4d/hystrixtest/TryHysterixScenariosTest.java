package com.bitsetd4d.hystrixtest;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TryHysterixScenariosTest {

    private static final String COMMAND_NAME = "executesCommand";
    public static final int TIMEOUT = 2000;

    private String groupKey;

    private int threads = 20;
    private ExecutorService executorService;
    private CountDownLatch submitCountdownLatch;

    private CopyOnWriteArrayList<CommandResult> testResults;

    @Before
    public void setUp() throws Exception {
        setupHystrixConfiguration();
        setupTest();
    }

    private void setupTest() {
        //executorService = Executors.newFixedThreadPool(threads, r -> new Thread("MAIN-THREAD-"+System.nanoTime()));
        executorService = Executors.newFixedThreadPool(threads);
        submitCountdownLatch = new CountDownLatch(threads);
        testResults = new CopyOnWriteArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    private void setupHystrixConfiguration() throws Exception {
        groupKey = UUID.randomUUID().toString();
        ConfigurationManager.getConfigInstance().clear();
        ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.threadpool.%s.coreSize", groupKey), String.valueOf(threads));
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", String.valueOf(threads));
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests", String.valueOf(threads));
        ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.command.%s-%s.execution.isolation.thread.timeoutInMilliseconds", groupKey, COMMAND_NAME), String.valueOf(TIMEOUT));
        ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.command.%s-%s.circuitBreaker.enabled", groupKey, COMMAND_NAME), "false");
        com.netflix.hystrix.Hystrix.reset();
        Thread.sleep(100);
    }

    @Test
    public void commandsExecuteNormally() {
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey));
        analyseResults("Normal Execution");
    }

    @Test
    public void commandsThrowsExceptionForcingFallback() {
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey).withExceptionOnRun());
        analyseResults("Throws Exception forcing fallback");
    }

    @Test
    public void circuitBreakerIsOpenSoStraightToFallback() throws Exception {
        ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.command.%s-%s.circuitBreaker.enabled", groupKey, COMMAND_NAME), "true");
        ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.command.%s-%s.circuitBreaker.forceOpen", groupKey, COMMAND_NAME), "true");
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey));
        analyseResults("Circuit breaker open");
    }

    @Test
    public void commandsThrowsExceptionForcingFallbackWhichIsSlow() {
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey).withExceptionOnRun().withSlowFallback(500));
        analyseResults("Throws Exception forcing fallback which is slow");
    }

    @Test
    public void commandIsSlowForcingFallback() {
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey).withSlowCommand());
        analyseResults("Command is slow forcing fallback");
    }

    @Test
    public void commandIsSlowForcingFallbackAndFallbackIsAlsoSlow() {
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey).withSlowCommand().withSlowFallback(1000));
        analyseResults("Command is slow forcing fallback");
    }

    @Test
    public void commandIsSlowForcingFallbackAndFallbackIsSuperSuperSlow() {
        submitTasksAndAwaitCompletion(() -> new TestCommand(groupKey).withSlowCommand().withSlowFallback(5000));
        analyseResults("Command is slow forcing fallback which is super slow");
    }

    private void analyseResults(String scenario) {
        System.out.println("---------------------------------------");
        System.out.println(scenario);
        System.out.println("---------------------------------------");
        testResults.forEach(System.out::println);
        System.out.println();
        OptionalInt min = testResults.stream().mapToInt(r -> r.timeToExecute).min();
        OptionalInt max = testResults.stream().mapToInt(r -> r.timeToExecute).max();
        OptionalDouble mean = testResults.stream().mapToInt(r -> r.timeToExecute).average();
        System.out.println(String.format("Min: %d  Max: %d  Mean: %f", min.orElse(0), max.orElse(0), mean.orElse(0)));
        System.out.println();
    }

    private void submitTasksAndAwaitCompletion(Supplier<TestCommand> commandSupplier) {
        for (int i = 0; i < threads; i++) {
            TestCommand testCommand = commandSupplier.get();
            executorService.execute(() -> {
                CommandResult commandResult = testCommand.execute();
                testResults.add(commandResult);
                submitCountdownLatch.countDown();
            });
        }
        try {
            submitCountdownLatch.await(20, SECONDS);
        } catch (InterruptedException e) {}
    }

    private static class TestCommand extends HystrixCommand<CommandResult> {

        private long created = System.currentTimeMillis();
        private boolean exceptionOnRun = false;
        private int slowCommand = 0;
        private int slowFallback = 0;

        public TestCommand(String groupKey) {
            super(HystrixCommandGroupKey.Factory.asKey(groupKey), HystrixThreadPoolKey.Factory.asKey(COMMAND_NAME));
        }

        public TestCommand withExceptionOnRun() {
            exceptionOnRun = true;
            return this;
        }

        public TestCommand withSlowCommand() {
            this.slowCommand = TIMEOUT * 2;
            return this;
        }

        public TestCommand withSlowFallback(int slowFallback) {
            this.slowFallback = slowFallback;
            return this;
        }

        @Override
        protected CommandResult run() throws Exception {
            if (slowCommand > 0) {
                try {
                    Thread.sleep(slowCommand);
                } catch (InterruptedException e) {
                    System.out.println("Command sleep was interrupted");
                }
            }
            if (exceptionOnRun) throw new RuntimeException("Deliberate exception on run");
            return new CommandResult(getTimeToExecute(), State.OK);
        }

        @Override
        protected CommandResult getFallback() {
            if (slowFallback > 0) {
                sleep(slowFallback);
            }
            return new CommandResult(getTimeToExecute(), State.FALLBACK);
        }

        private void sleep(int time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private int getTimeToExecute() {
            return (int) (System.currentTimeMillis() - created);
        }
    }

    private enum State {
        OK, FALLBACK
    }

    private static class CommandResult {
        int timeToExecute;
        State state;
        String thread;

        public CommandResult(int timeToExecute, State state) {
            this.timeToExecute = timeToExecute;
            this.state = state;
            this.thread = Thread.currentThread().getName();
        }

        @Override
        public String toString() {
            return "CommandResult{" +
                    "timeToExecute=" + timeToExecute +
                    ", state=" + state +
                    ", thread='" + thread + '\'' +
                    '}';
        }
    }
}