package com.bitsetd4d.runner.internal;

import com.bitsetd4d.runner.ExecutorServiceProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StandardExecutorServiceProvider implements ExecutorServiceProvider {

    @Override
    public ExecutorService newFixedThreadPool(int threads) {
        return Executors.newFixedThreadPool(threads);
    }
}