package com.sensorsdata.analytics.android.sdk.util;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadUtils {
    private static final String TAG = "SA.ThreadUtils";
    private static final Map<Integer, Map<Integer, ExecutorService>> TYPE_PRIORITY_POOLS = new HashMap();
    private static final byte TYPE_SINGLE = -1;

    public static ExecutorService getSinglePool() {
        return getPoolByTypeAndPriority(-1);
    }

    public static ExecutorService getSinglePool(int priority) {
        return getPoolByTypeAndPriority(-1, priority);
    }

    private static ExecutorService getPoolByTypeAndPriority(int type) {
        return getPoolByTypeAndPriority(type, 5);
    }

    private static ExecutorService getPoolByTypeAndPriority(int type, int priority) {
        ExecutorService pool;
        Map<Integer, Map<Integer, ExecutorService>> map = TYPE_PRIORITY_POOLS;
        synchronized (map) {
            Map<Integer, ExecutorService> priorityPools = map.get(Integer.valueOf(type));
            if (priorityPools == null) {
                Map<Integer, ExecutorService> priorityPools2 = new ConcurrentHashMap<>();
                pool = ThreadPoolExecutorUtil.createPool(type, priority);
                priorityPools2.put(Integer.valueOf(priority), pool);
                map.put(Integer.valueOf(type), priorityPools2);
            } else {
                pool = priorityPools.get(Integer.valueOf(priority));
                if (pool == null) {
                    pool = ThreadPoolExecutorUtil.createPool(type, priority);
                    priorityPools.put(Integer.valueOf(priority), pool);
                }
            }
        }
        return pool;
    }

    public static final class ThreadPoolExecutorUtil extends ThreadPoolExecutor {
        private final AtomicInteger mSubmittedCount = new AtomicInteger();
        private LinkedBlockingQueueUtil mWorkQueue;

        /* access modifiers changed from: private */
        public static ExecutorService createPool(int type, int priority) {
            switch (type) {
                case -1:
                    return new ThreadPoolExecutorUtil(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueueUtil(), new UtilsThreadFactory("single", priority));
                default:
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    LinkedBlockingQueueUtil linkedBlockingQueueUtil = new LinkedBlockingQueueUtil();
                    return new ThreadPoolExecutorUtil(type, type, 0, timeUnit, linkedBlockingQueueUtil, new UtilsThreadFactory("fixed(" + type + ")", priority));
            }
        }

        ThreadPoolExecutorUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, LinkedBlockingQueueUtil workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
            ThreadPoolExecutorUtil unused = workQueue.mPool = this;
            this.mWorkQueue = workQueue;
        }

        private int getSubmittedCount() {
            return this.mSubmittedCount.get();
        }

        /* access modifiers changed from: protected */
        public void afterExecute(Runnable r, Throwable t) {
            this.mSubmittedCount.decrementAndGet();
            super.afterExecute(r, t);
        }

        public void execute(Runnable command) {
            if (!isShutdown()) {
                this.mSubmittedCount.incrementAndGet();
                try {
                    super.execute(command);
                } catch (RejectedExecutionException e) {
                    SALog.i(ThreadUtils.TAG, "This will not happen!");
                    this.mWorkQueue.offer(command);
                } catch (Throwable th) {
                    this.mSubmittedCount.decrementAndGet();
                }
            }
        }
    }

    public static final class LinkedBlockingQueueUtil extends LinkedBlockingQueue<Runnable> {
        private int mCapacity = Integer.MAX_VALUE;
        /* access modifiers changed from: private */
        public volatile ThreadPoolExecutorUtil mPool;

        LinkedBlockingQueueUtil() {
        }

        LinkedBlockingQueueUtil(boolean isAddSubThreadFirstThenAddQueue) {
            if (isAddSubThreadFirstThenAddQueue) {
                this.mCapacity = 0;
            }
        }

        LinkedBlockingQueueUtil(int capacity) {
            this.mCapacity = capacity;
        }

        public boolean offer(Runnable runnable) {
            if (this.mCapacity > size() || this.mPool == null || this.mPool.getPoolSize() >= this.mPool.getMaximumPoolSize()) {
                return super.offer(runnable);
            }
            return false;
        }
    }

    public static final class UtilsThreadFactory extends AtomicLong implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final boolean isDaemon;
        private final String namePrefix;
        private final int priority;

        UtilsThreadFactory(String prefix, int priority2) {
            this(prefix, priority2, false);
        }

        UtilsThreadFactory(String prefix, int priority2, boolean isDaemon2) {
            this.namePrefix = prefix + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
            this.priority = priority2;
            this.isDaemon = isDaemon2;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, this.namePrefix + getAndIncrement()) {
                public void run() {
                    try {
                        super.run();
                    } catch (Throwable th) {
                        SALog.i("ThreadUtils", "Request threw uncaught throwable");
                    }
                }
            };
            t.setDaemon(this.isDaemon);
            t.setPriority(this.priority);
            return t;
        }
    }
}
