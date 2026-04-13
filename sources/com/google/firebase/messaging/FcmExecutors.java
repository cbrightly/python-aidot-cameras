package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.firebase.messaging.threads.PoolableExecutors;
import com.google.firebase.messaging.threads.ThreadPriority;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FcmExecutors {
    private static final String THREAD_FILE = "Firebase-Messaging-File";
    static final String THREAD_FILE_IO = "Firebase-Messaging-File-Io";
    private static final String THREAD_INIT = "Firebase-Messaging-Init";
    private static final String THREAD_INTENT_HANDLE = "Firebase-Messaging-Intent-Handle";
    private static final String THREAD_NETWORK_IO = "Firebase-Messaging-Network-Io";
    static final String THREAD_RPC_TASK = "Firebase-Messaging-Rpc-Task";
    private static final String THREAD_TASK = "Firebase-Messaging-Task";
    private static final String THREAD_TOPICS_IO = "Firebase-Messaging-Topics-Io";

    static Executor newRpcTasksExecutor() {
        return newCachedSingleThreadExecutor(THREAD_RPC_TASK);
    }

    static Executor newFileIOExecutor() {
        return newCachedSingleThreadExecutor(THREAD_FILE_IO);
    }

    @SuppressLint({"ThreadPoolCreation"})
    private static Executor newCachedSingleThreadExecutor(String threadName) {
        return new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue(), new NamedThreadFactory(threadName));
    }

    @SuppressLint({"ThreadPoolCreation"})
    static ScheduledExecutorService newTopicsSyncExecutor() {
        return new ScheduledThreadPoolExecutor(1, new NamedThreadFactory(THREAD_TOPICS_IO));
    }

    @SuppressLint({"ThreadPoolCreation"})
    static ExecutorService newNetworkIOExecutor() {
        return Executors.newSingleThreadExecutor(new NamedThreadFactory(THREAD_NETWORK_IO));
    }

    @SuppressLint({"ThreadPoolCreation"})
    static ExecutorService newTaskExecutor() {
        return Executors.newSingleThreadExecutor(new NamedThreadFactory(THREAD_TASK));
    }

    @SuppressLint({"ThreadPoolCreation"})
    static ExecutorService newFileExecutor() {
        return Executors.newSingleThreadExecutor(new NamedThreadFactory(THREAD_FILE));
    }

    @SuppressLint({"ThreadPoolCreation"})
    static ExecutorService newIntentHandleExecutor() {
        return PoolableExecutors.factory().newSingleThreadExecutor(new NamedThreadFactory(THREAD_INTENT_HANDLE), ThreadPriority.HIGH_SPEED);
    }

    @SuppressLint({"ThreadPoolCreation"})
    static ScheduledExecutorService newInitExecutor() {
        return new ScheduledThreadPoolExecutor(1, new NamedThreadFactory(THREAD_INIT));
    }

    private FcmExecutors() {
    }
}
