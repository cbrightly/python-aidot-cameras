package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GlobalEventExecutor extends AbstractScheduledEventExecutor {
    public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
    private static final long SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) GlobalEventExecutor.class);
    final ScheduledFutureTask<Void> quietPeriodTask;
    /* access modifiers changed from: private */
    public final AtomicBoolean started;
    final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue();
    private final TaskRunner taskRunner;
    private final Future<?> terminationFuture;
    volatile Thread thread;
    final ThreadFactory threadFactory;

    private GlobalEventExecutor() {
        Callable callable = Executors.callable(new Runnable() {
            public void run() {
            }
        }, (Object) null);
        long j = SCHEDULE_QUIET_PERIOD_INTERVAL;
        ScheduledFutureTask scheduledFutureTask = new ScheduledFutureTask((AbstractScheduledEventExecutor) this, callable, ScheduledFutureTask.deadlineNanos(j), -j);
        this.quietPeriodTask = scheduledFutureTask;
        this.threadFactory = new DefaultThreadFactory(DefaultThreadFactory.toPoolName(getClass()), false, 5, (ThreadGroup) null);
        this.taskRunner = new TaskRunner();
        this.started = new AtomicBoolean();
        this.terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
        scheduledTaskQueue().add(scheduledFutureTask);
    }

    public EventExecutorGroup parent() {
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Runnable} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Runnable takeTask() {
        /*
            r6 = this;
            java.util.concurrent.BlockingQueue<java.lang.Runnable> r0 = r6.taskQueue
        L_0x0002:
            io.netty.util.concurrent.ScheduledFutureTask r1 = r6.peekScheduledTask()
            if (r1 != 0) goto L_0x0013
            r2 = 0
            java.lang.Object r3 = r0.take()     // Catch:{ InterruptedException -> 0x0011 }
            java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch:{ InterruptedException -> 0x0011 }
            r2 = r3
            goto L_0x0012
        L_0x0011:
            r3 = move-exception
        L_0x0012:
            return r2
        L_0x0013:
            long r2 = r1.delayNanos()
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0029
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x0026 }
            java.lang.Object r4 = r0.poll(r2, r4)     // Catch:{ InterruptedException -> 0x0026 }
            java.lang.Runnable r4 = (java.lang.Runnable) r4     // Catch:{ InterruptedException -> 0x0026 }
            goto L_0x002f
        L_0x0026:
            r4 = move-exception
            r5 = 0
            return r5
        L_0x0029:
            java.lang.Object r4 = r0.poll()
            java.lang.Runnable r4 = (java.lang.Runnable) r4
        L_0x002f:
            if (r4 != 0) goto L_0x003b
            r6.fetchFromScheduledTaskQueue()
            java.lang.Object r5 = r0.poll()
            r4 = r5
            java.lang.Runnable r4 = (java.lang.Runnable) r4
        L_0x003b:
            if (r4 == 0) goto L_0x003e
            return r4
        L_0x003e:
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.GlobalEventExecutor.takeTask():java.lang.Runnable");
    }

    private void fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable scheduledTask = pollScheduledTask(nanoTime);
        while (scheduledTask != null) {
            this.taskQueue.add(scheduledTask);
            scheduledTask = pollScheduledTask(nanoTime);
        }
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    private void addTask(Runnable task) {
        if (task != null) {
            this.taskQueue.add(task);
            return;
        }
        throw new NullPointerException("task");
    }

    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return false;
    }

    public boolean awaitInactivity(long timeout, TimeUnit unit) {
        if (unit != null) {
            Thread thread2 = this.thread;
            if (thread2 != null) {
                thread2.join(unit.toMillis(timeout));
                return !thread2.isAlive();
            }
            throw new IllegalStateException("thread was not started");
        }
        throw new NullPointerException("unit");
    }

    public void execute(Runnable task) {
        if (task != null) {
            addTask(task);
            if (!inEventLoop()) {
                startThread();
                return;
            }
            return;
        }
        throw new NullPointerException("task");
    }

    private void startThread() {
        if (this.started.compareAndSet(false, true)) {
            final Thread t = this.threadFactory.newThread(this.taskRunner);
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    t.setContextClassLoader((ClassLoader) null);
                    return null;
                }
            });
            this.thread = t;
            t.start();
        }
    }

    public final class TaskRunner implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<GlobalEventExecutor> cls = GlobalEventExecutor.class;
        }

        TaskRunner() {
        }

        public void run() {
            while (true) {
                Runnable task = GlobalEventExecutor.this.takeTask();
                if (task != null) {
                    try {
                        task.run();
                    } catch (Throwable t) {
                        GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", t);
                    }
                    if (task != GlobalEventExecutor.this.quietPeriodTask) {
                        continue;
                    }
                }
                GlobalEventExecutor globalEventExecutor = GlobalEventExecutor.this;
                Queue<ScheduledFutureTask<?>> scheduledTaskQueue = globalEventExecutor.scheduledTaskQueue;
                if (globalEventExecutor.taskQueue.isEmpty() && (scheduledTaskQueue == null || scheduledTaskQueue.size() == 1)) {
                    if (!GlobalEventExecutor.this.started.compareAndSet(true, false)) {
                        throw new AssertionError();
                    } else if ((GlobalEventExecutor.this.taskQueue.isEmpty() && (scheduledTaskQueue == null || scheduledTaskQueue.size() == 1)) || !GlobalEventExecutor.this.started.compareAndSet(false, true)) {
                        return;
                    }
                }
            }
        }
    }
}
