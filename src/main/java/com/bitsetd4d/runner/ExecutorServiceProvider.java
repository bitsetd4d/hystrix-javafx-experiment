package com.bitsetd4d.runner;

import java.util.concurrent.ExecutorService;

@FunctionalInterface
public interface ExecutorServiceProvider {

    ExecutorService newFixedThreadPool(int threads);

}