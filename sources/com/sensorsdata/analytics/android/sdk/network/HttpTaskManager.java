package com.sensorsdata.analytics.android.sdk.network;

import com.sensorsdata.analytics.android.sdk.ThreadNameConstants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpTaskManager {
    private static final int POOL_SIZE = 2;
    private static volatile ExecutorService executor = null;

    private HttpTaskManager() {
    }

    private static ExecutorService getInstance() {
        if (executor == null) {
            synchronized (HttpTaskManager.class) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactoryWithName(ThreadNameConstants.THREAD_DEEP_LINK_REQUEST));
                }
            }
        }
        return executor;
    }

    static void execute(Runnable runnable) {
        getInstance().execute(runnable);
    }

    public static class ThreadFactoryWithName implements ThreadFactory {
        private final String name;

        ThreadFactoryWithName(String name2) {
            this.name = name2;
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, this.name);
        }
    }
}
