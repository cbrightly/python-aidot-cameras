package com.google.firebase.concurrent;

import java.util.concurrent.Executor;

public interface PausableExecutor extends Executor {
    boolean isPaused();

    void pause();

    void resume();
}
