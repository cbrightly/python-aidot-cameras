package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ScheduledFutureTask<V> extends PromiseTask<V> implements ScheduledFuture<V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long START_TIME = System.nanoTime();
    private static final AtomicLong nextTaskId = new AtomicLong();
    private long deadlineNanos;
    private final long id;
    private final long periodNanos;

    static {
        Class<ScheduledFutureTask> cls = ScheduledFutureTask.class;
    }

    static long nanoTime() {
        return System.nanoTime() - START_TIME;
    }

    static long deadlineNanos(long delay) {
        return nanoTime() + delay;
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor executor, Runnable runnable, V result, long nanoTime) {
        this(executor, PromiseTask.toCallable(runnable, result), nanoTime);
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor executor, Callable<V> callable, long nanoTime, long period) {
        super(executor, callable);
        this.id = nextTaskId.getAndIncrement();
        if (period != 0) {
            this.deadlineNanos = nanoTime;
            this.periodNanos = period;
            return;
        }
        throw new IllegalArgumentException("period: 0 (expected: != 0)");
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor executor, Callable<V> callable, long nanoTime) {
        super(executor, callable);
        this.id = nextTaskId.getAndIncrement();
        this.deadlineNanos = nanoTime;
        this.periodNanos = 0;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return super.executor();
    }

    public long deadlineNanos() {
        return this.deadlineNanos;
    }

    public long delayNanos() {
        return Math.max(0, deadlineNanos() - nanoTime());
    }

    public long delayNanos(long currentTimeNanos) {
        return Math.max(0, deadlineNanos() - (currentTimeNanos - START_TIME));
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(delayNanos(), TimeUnit.NANOSECONDS);
    }

    public int compareTo(Delayed o) {
        if (this == o) {
            return 0;
        }
        ScheduledFutureTask<?> that = (ScheduledFutureTask) o;
        long d = deadlineNanos() - that.deadlineNanos();
        if (d < 0) {
            return -1;
        }
        if (d > 0) {
            return 1;
        }
        long j = this.id;
        long j2 = that.id;
        if (j < j2) {
            return -1;
        }
        if (j != j2) {
            return 1;
        }
        throw new Error();
    }

    public void run() {
        if (executor().inEventLoop()) {
            try {
                if (this.periodNanos == 0) {
                    if (setUncancellableInternal()) {
                        setSuccessInternal(this.task.call());
                    }
                } else if (!isCancelled()) {
                    this.task.call();
                    if (!executor().isShutdown()) {
                        long p = this.periodNanos;
                        if (p > 0) {
                            this.deadlineNanos += p;
                        } else {
                            this.deadlineNanos = nanoTime() - p;
                        }
                        if (!isCancelled()) {
                            Queue<ScheduledFutureTask<?>> scheduledTaskQueue = ((AbstractScheduledEventExecutor) executor()).scheduledTaskQueue;
                            if (scheduledTaskQueue != null) {
                                scheduledTaskQueue.add(this);
                                return;
                            }
                            throw new AssertionError();
                        }
                    }
                }
            } catch (Throwable cause) {
                setFailureInternal(cause);
            }
        } else {
            throw new AssertionError();
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean canceled = super.cancel(mayInterruptIfRunning);
        if (canceled) {
            ((AbstractScheduledEventExecutor) executor()).removeScheduled(this);
        }
        return canceled;
    }

    /* access modifiers changed from: package-private */
    public boolean cancelWithoutRemove(boolean mayInterruptIfRunning) {
        return super.cancel(mayInterruptIfRunning);
    }

    /* access modifiers changed from: protected */
    public StringBuilder toStringBuilder() {
        StringBuilder buf = super.toStringBuilder();
        buf.setCharAt(buf.length() - 1, StringUtil.COMMA);
        buf.append(" id: ");
        buf.append(this.id);
        buf.append(", deadline: ");
        buf.append(this.deadlineNanos);
        buf.append(", period: ");
        buf.append(this.periodNanos);
        buf.append(')');
        return buf;
    }
}
