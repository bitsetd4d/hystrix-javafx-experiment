package com.bitsetd4d.runner;

import com.netflix.hystrix.HystrixCommand;

public interface ExperimentCommandProvider {

    HystrixCommand<CommandResult> newCommand(String groupKey, int runDelay, boolean runException, int fallbackDelay, boolean fallbackException);

}