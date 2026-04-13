package com.didichuxing.doraemonkit.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorUtil {
    private static ExecutorService sExecutorService;

    private ExecutorUtil() {
    }

    public static void execute(Runnable r) {
        if (sExecutorService == null) {
            sExecutorService = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS, new SynchronousQueue(), new ThreadPoolExecutor.AbortPolicy());
        }
        sExecutorService.execute(r);
    }
}
