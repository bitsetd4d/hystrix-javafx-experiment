package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.CommandResult;
import com.bitsetd4d.runner.ExperimentCommandProvider;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class ExperimentCommandProviderImpl implements ExperimentCommandProvider {

    private static final String COMMAND_NAME = "testCommand";

    @Override
    public HystrixCommand<CommandResult> newCommand(String groupKey, int runDelay, boolean runException, int fallbackDelay, boolean fallbackException) {
        return new TestCommand(groupKey)
                .withRunDelay(runDelay)
                .withExceptionOnRun(runException)
                .withFallbackDelay(fallbackDelay)
                .withFallbackException(fallbackException);
    }

    private static class TestCommand extends HystrixCommand<CommandResult> {

        private long created = System.currentTimeMillis();
        private boolean exceptionOnRun = false;
        private int runDelay = 0;
        private int fallbackDelay = 0;
        private boolean exceptionOnFallback = false;

        TestCommand(String groupKey) {
            super(HystrixCommandGroupKey.Factory.asKey(groupKey), HystrixThreadPoolKey.Factory.asKey(COMMAND_NAME));
        }

        TestCommand withExceptionOnRun(boolean exceptionOnRun) {
            this.exceptionOnRun = exceptionOnRun;
            return this;
        }

        TestCommand withRunDelay(int runDelay) {
            this.runDelay = runDelay;
            return this;
        }

        TestCommand withFallbackDelay(int fallbackDelay) {
            this.fallbackDelay = fallbackDelay;
            return this;
        }

        TestCommand withFallbackException(boolean exceptionOnFallback) {
            this.exceptionOnFallback = exceptionOnFallback;
            return this;
        }

        @Override
        protected CommandResult run() throws Exception {
            if (runDelay > 0) {
                try {
                    Thread.sleep(runDelay);
                } catch (InterruptedException e) {
                    System.out.println("Command sleep was interrupted");
                }
            }
            if (exceptionOnRun) throw new RuntimeException("Deliberate exception on run");
            return new CommandResult(getTimeToExecute(), CommandResult.State.OK);
        }

        @Override
        protected CommandResult getFallback() {
            if (fallbackDelay > 0) {
                sleep(fallbackDelay);
            }
            if (exceptionOnFallback) throw new RuntimeException("Deliberate exception on fallback");
            return new CommandResult(getTimeToExecute(), CommandResult.State.FALLBACK);
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

}