package com.google.firebase.messaging.threads;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.google.errorprone.annotations.CompileTimeConstant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolableExecutors {
    private static final ExecutorFactory DEFAULT_INSTANCE;
    private static volatile ExecutorFactory instance;

    static {
        DefaultExecutorFactory defaultExecutorFactory = new DefaultExecutorFactory();
        DEFAULT_INSTANCE = defaultExecutorFactory;
        instance = defaultExecutorFactory;
    }

    private PoolableExecutors() {
    }

    public static ExecutorFactory factory() {
        return instance;
    }

    public static class DefaultExecutorFactory implements ExecutorFactory {
        private static final long CORE_THREAD_TIMEOUT_SECS = 60;

        private DefaultExecutorFactory() {
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public ExecutorService newThreadPool(ThreadPriority priority) {
            return Executors.unconfigurableExecutorService(Executors.newCachedThreadPool());
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public ExecutorService newThreadPool(ThreadFactory threadFactory, ThreadPriority priority) {
            return Executors.unconfigurableExecutorService(Executors.newCachedThreadPool(threadFactory));
        }

        @NonNull
        public ExecutorService newThreadPool(int maxConcurrency, ThreadPriority priority) {
            return newThreadPool(maxConcurrency, Executors.defaultThreadFactory(), priority);
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public ExecutorService newThreadPool(int maxConcurrency, ThreadFactory threadFactory, ThreadPriority priority) {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(maxConcurrency, maxConcurrency, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory);
            executor.allowCoreThreadTimeOut(true);
            return Executors.unconfigurableExecutorService(executor);
        }

        @NonNull
        public ExecutorService newSingleThreadExecutor(ThreadPriority priority) {
            return newThreadPool(1, priority);
        }

        @NonNull
        public ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory, ThreadPriority priority) {
            return newThreadPool(1, threadFactory, priority);
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public ScheduledExecutorService newScheduledThreadPool(int maxConcurrency, ThreadPriority priority) {
            return Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(maxConcurrency));
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public ScheduledExecutorService newScheduledThreadPool(int maxConcurrency, ThreadFactory threadFactory, ThreadPriority priority) {
            return Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(maxConcurrency, threadFactory));
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public void executeOneOff(@CompileTimeConstant String moduleName, @CompileTimeConstant String name, ThreadPriority priority, Runnable runnable) {
            new Thread(runnable, name).start();
        }

        @SuppressLint({"ThreadPoolCreation"})
        @NonNull
        public Future<?> submitOneOff(@CompileTimeConstant String moduleName, @CompileTimeConstant String name, ThreadPriority priority, Runnable runnable) {
            FutureTask<?> task = new FutureTask<>(runnable, (Object) null);
            new Thread(task, name).start();
            return task;
        }
    }

    static void installExecutorFactory(ExecutorFactory instance2) {
        if (instance == DEFAULT_INSTANCE) {
            instance = instance2;
            return;
        }
        throw new IllegalStateException("Trying to install an ExecutorFactory twice!");
    }
}
