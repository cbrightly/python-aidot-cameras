package com.google.firebase.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class FirebaseExecutors {
    private FirebaseExecutors() {
    }

    public static Executor newSequentialExecutor(Executor delegate) {
        return new SequentialExecutor(delegate);
    }

    public static Executor newLimitedConcurrencyExecutor(Executor delegate, int concurrency) {
        return new LimitedConcurrencyExecutor(delegate, concurrency);
    }

    public static ExecutorService newLimitedConcurrencyExecutorService(ExecutorService delegate, int concurrency) {
        return new LimitedConcurrencyExecutorService(delegate, concurrency);
    }

    public static ScheduledExecutorService newLimitedConcurrencyScheduledExecutorService(ExecutorService delegate, int concurrency) {
        return new DelegatingScheduledExecutorService(newLimitedConcurrencyExecutorService(delegate, concurrency), ExecutorsRegistrar.SCHEDULER.get());
    }

    public static PausableExecutor newPausableExecutor(Executor delegate) {
        return new PausableExecutorImpl(false, delegate);
    }

    public static PausableExecutorService newPausableExecutorService(ExecutorService delegate) {
        return new PausableExecutorServiceImpl(false, delegate);
    }

    public static PausableScheduledExecutorService newPausableScheduledExecutorService(ScheduledExecutorService delegate) {
        return new PausableScheduledExecutorServiceImpl(newPausableExecutorService(delegate), ExecutorsRegistrar.SCHEDULER.get());
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    public enum DirectExecutor implements Executor {
        INSTANCE;

        public void execute(Runnable command) {
            command.run();
        }
    }
}
