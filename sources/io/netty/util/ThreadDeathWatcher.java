package io.netty.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
public final class ThreadDeathWatcher {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ThreadDeathWatcher.class);
    /* access modifiers changed from: private */
    public static final Queue<Entry> pendingEntries = new ConcurrentLinkedQueue();
    /* access modifiers changed from: private */
    public static final AtomicBoolean started = new AtomicBoolean();
    static final ThreadFactory threadFactory;
    private static final Watcher watcher = new Watcher();
    private static volatile Thread watcherThread;

    static {
        String poolName = "threadDeathWatcher";
        String serviceThreadPrefix = SystemPropertyUtil.get("io.netty.serviceThreadPrefix");
        if (!StringUtil.isNullOrEmpty(serviceThreadPrefix)) {
            poolName = serviceThreadPrefix + poolName;
        }
        threadFactory = new DefaultThreadFactory(poolName, true, 1, (ThreadGroup) null);
    }

    public static void watch(Thread thread, Runnable task) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (task == null) {
            throw new NullPointerException("task");
        } else if (thread.isAlive()) {
            schedule(thread, task, true);
        } else {
            throw new IllegalArgumentException("thread must be alive.");
        }
    }

    public static void unwatch(Thread thread, Runnable task) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (task != null) {
            schedule(thread, task, false);
        } else {
            throw new NullPointerException("task");
        }
    }

    private static void schedule(Thread thread, Runnable task, boolean isWatch) {
        pendingEntries.add(new Entry(thread, task, isWatch));
        if (started.compareAndSet(false, true)) {
            final Thread watcherThread2 = threadFactory.newThread(watcher);
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    watcherThread2.setContextClassLoader((ClassLoader) null);
                    return null;
                }
            });
            watcherThread2.start();
            watcherThread = watcherThread2;
        }
    }

    public static boolean awaitInactivity(long timeout, TimeUnit unit) {
        if (unit != null) {
            Thread watcherThread2 = watcherThread;
            if (watcherThread2 == null) {
                return true;
            }
            watcherThread2.join(unit.toMillis(timeout));
            return true ^ watcherThread2.isAlive();
        }
        throw new NullPointerException("unit");
    }

    private ThreadDeathWatcher() {
    }

    public static final class Watcher implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final List<Entry> watchees;

        private Watcher() {
            this.watchees = new ArrayList();
        }

        public void run() {
            while (true) {
                fetchWatchees();
                notifyWatchees();
                fetchWatchees();
                notifyWatchees();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                if (this.watchees.isEmpty() && ThreadDeathWatcher.pendingEntries.isEmpty()) {
                    if (!ThreadDeathWatcher.started.compareAndSet(true, false)) {
                        throw new AssertionError();
                    } else if (ThreadDeathWatcher.pendingEntries.isEmpty() || !ThreadDeathWatcher.started.compareAndSet(false, true)) {
                        return;
                    }
                }
            }
        }

        private void fetchWatchees() {
            while (true) {
                Entry e = (Entry) ThreadDeathWatcher.pendingEntries.poll();
                if (e != null) {
                    if (e.isWatch) {
                        this.watchees.add(e);
                    } else {
                        this.watchees.remove(e);
                    }
                } else {
                    return;
                }
            }
        }

        private void notifyWatchees() {
            List<Entry> watchees2 = this.watchees;
            int i = 0;
            while (i < watchees2.size()) {
                Entry e = watchees2.get(i);
                if (!e.thread.isAlive()) {
                    watchees2.remove(i);
                    try {
                        e.task.run();
                    } catch (Throwable t) {
                        ThreadDeathWatcher.logger.warn("Thread death watcher task raised an exception:", t);
                    }
                } else {
                    i++;
                }
            }
        }
    }

    public static final class Entry {
        final boolean isWatch;
        final Runnable task;
        final Thread thread;

        Entry(Thread thread2, Runnable task2, boolean isWatch2) {
            this.thread = thread2;
            this.task = task2;
            this.isWatch = isWatch2;
        }

        public int hashCode() {
            return this.thread.hashCode() ^ this.task.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry that = (Entry) obj;
            if (this.thread == that.thread && this.task == that.task) {
                return true;
            }
            return false;
        }
    }
}
