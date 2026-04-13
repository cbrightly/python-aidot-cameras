package com.downloader.core;

import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: DownloadExecutor */
public class c extends ThreadPoolExecutor {
    c(int maxNumThreads, ThreadFactory threadFactory) {
        super(maxNumThreads, maxNumThreads, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), threadFactory);
    }

    public Future<?> submit(Runnable task) {
        d futureTask = new d((com.downloader.internal.c) task);
        execute(futureTask);
        return futureTask;
    }
}
