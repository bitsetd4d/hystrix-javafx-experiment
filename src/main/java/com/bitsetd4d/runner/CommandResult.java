package com.bitsetd4d.runner;

import java.util.Objects;

public final class CommandResult {

    public enum State {
        OK, FALLBACK
    }

    private final int timeToExecute;
    private final State state;
    private final String thread;

    public CommandResult(int timeToExecute, State state) {
        this.timeToExecute = timeToExecute;
        this.state = state;
        this.thread = Thread.currentThread().getName();
    }

    public int getTimeToExecute() {
        return timeToExecute;
    }

    public State getState() {
        return state;
    }

    public String getThread() {
        return thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResult result = (CommandResult) o;
        return timeToExecute == result.timeToExecute &&
                state == result.state &&
                Objects.equals(thread, result.thread);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeToExecute, state, thread);
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