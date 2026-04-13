package io.netty.util.concurrent;

import java.lang.Thread;

public interface ThreadProperties {
    long id();

    boolean isAlive();

    boolean isDaemon();

    boolean isInterrupted();

    String name();

    int priority();

    StackTraceElement[] stackTrace();

    Thread.State state();
}
