package com.didichuxing.doraemonkit.util.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import meshsdk.ctrl.GroupCtrlAdapter;

public class ThreadPoolProxy {
    private int mCorePoolSize;
    ThreadPoolExecutor mExecutor;
    private int mMaximumPoolSize;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        this.mCorePoolSize = corePoolSize;
        this.mMaximumPoolSize = maximumPoolSize;
    }

    private void initThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown() || this.mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                ThreadPoolExecutor threadPoolExecutor2 = this.mExecutor;
                if (threadPoolExecutor2 == null || threadPoolExecutor2.isShutdown() || this.mExecutor.isTerminated()) {
                    this.mExecutor = new ThreadPoolExecutor(this.mCorePoolSize, this.mMaximumPoolSize, GroupCtrlAdapter.RETRY_TIMEOUT, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
                }
            }
        }
    }

    public void execute(Runnable task) {
        initThreadPoolExecutor();
        this.mExecutor.execute(task);
    }

    public Future submit(Runnable task) {
        initThreadPoolExecutor();
        return this.mExecutor.submit(task);
    }

    public void remove(Runnable task) {
        initThreadPoolExecutor();
        this.mExecutor.remove(task);
    }
}
