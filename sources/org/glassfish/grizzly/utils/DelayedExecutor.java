package org.glassfish.grizzly.utils;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedExecutor {
    public static final long UNSET_TIMEOUT = -1;
    /* access modifiers changed from: private */
    public final long checkIntervalMillis;
    /* access modifiers changed from: private */
    public volatile boolean isStarted;
    /* access modifiers changed from: private */
    public final Queue<DelayQueue> queues;
    private final DelayedRunnable runnable;
    /* access modifiers changed from: private */
    public final Object sync;
    private final ExecutorService threadPool;

    public interface Resolver<E> {
        long getTimeoutMillis(E e);

        boolean removeTimeout(E e);

        void setTimeoutMillis(E e, long j);
    }

    public interface Worker<E> {
        boolean doWork(E e);
    }

    public DelayedExecutor(ExecutorService threadPool2) {
        this(threadPool2, 1000, TimeUnit.MILLISECONDS);
    }

    public DelayedExecutor(ExecutorService threadPool2, long checkInterval, TimeUnit timeunit) {
        this.runnable = new DelayedRunnable();
        this.queues = new ConcurrentLinkedQueue();
        this.sync = new Object();
        if (checkInterval >= 0) {
            this.threadPool = threadPool2;
            this.checkIntervalMillis = TimeUnit.MILLISECONDS.convert(checkInterval, timeunit);
            return;
        }
        throw new IllegalArgumentException("check interval can't be negative");
    }

    public void start() {
        synchronized (this.sync) {
            if (!this.isStarted) {
                this.isStarted = true;
                this.threadPool.execute(this.runnable);
            }
        }
    }

    public void stop() {
        synchronized (this.sync) {
            if (this.isStarted) {
                this.isStarted = false;
                this.sync.notify();
            }
        }
    }

    public void destroy() {
        stop();
        synchronized (this.sync) {
            this.queues.clear();
        }
    }

    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    public <E> DelayQueue<E> createDelayQueue(Worker<E> worker, Resolver<E> resolver) {
        DelayQueue<E> queue = new DelayQueue<>(worker, resolver);
        this.queues.add(queue);
        return queue;
    }

    /* access modifiers changed from: private */
    public static boolean wasModified(long l1, long l2) {
        return l1 != l2;
    }

    public class DelayedRunnable implements Runnable {
        private DelayedRunnable() {
        }

        public void run() {
            while (DelayedExecutor.this.isStarted) {
                long currentTimeMillis = System.currentTimeMillis();
                for (DelayQueue delayQueue : DelayedExecutor.this.queues) {
                    if (!delayQueue.queue.isEmpty()) {
                        Resolver resolver = delayQueue.resolver;
                        Iterator it = delayQueue.queue.keySet().iterator();
                        while (it.hasNext()) {
                            Object element = it.next();
                            long timeoutMillis = resolver.getTimeoutMillis(element);
                            if (timeoutMillis == -1) {
                                it.remove();
                                if (DelayedExecutor.wasModified(timeoutMillis, resolver.getTimeoutMillis(element))) {
                                    delayQueue.queue.put(element, delayQueue);
                                }
                            } else if (currentTimeMillis - timeoutMillis >= 0) {
                                it.remove();
                                if (DelayedExecutor.wasModified(timeoutMillis, resolver.getTimeoutMillis(element))) {
                                    delayQueue.queue.put(element, delayQueue);
                                } else {
                                    try {
                                        if (!delayQueue.worker.doWork(element)) {
                                            delayQueue.queue.put(element, delayQueue);
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
                }
                synchronized (DelayedExecutor.this.sync) {
                    if (DelayedExecutor.this.isStarted) {
                        try {
                            DelayedExecutor.this.sync.wait(DelayedExecutor.this.checkIntervalMillis);
                        } catch (InterruptedException e2) {
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public class DelayQueue<E> {
        final ConcurrentMap<E, DelayQueue> queue = new ConcurrentHashMap();
        final Resolver<E> resolver;
        final Worker<E> worker;

        public DelayQueue(Worker<E> worker2, Resolver<E> resolver2) {
            this.worker = worker2;
            this.resolver = resolver2;
        }

        public void add(E elem, long delay, TimeUnit timeUnit) {
            if (delay >= 0) {
                long delayWithSysTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delay, timeUnit);
                this.resolver.setTimeoutMillis(elem, delayWithSysTime < 0 ? DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE : delayWithSysTime);
                this.queue.put(elem, this);
            }
        }

        public void remove(E elem) {
            this.resolver.removeTimeout(elem);
        }

        public void destroy() {
            DelayedExecutor.this.queues.remove(this);
        }
    }
}
