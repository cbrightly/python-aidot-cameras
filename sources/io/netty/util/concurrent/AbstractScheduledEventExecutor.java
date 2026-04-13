package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractScheduledEventExecutor extends AbstractEventExecutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Queue<ScheduledFutureTask<?>> scheduledTaskQueue;

    static {
        Class<AbstractScheduledEventExecutor> cls = AbstractScheduledEventExecutor.class;
    }

    protected static long nanoTime() {
        return ScheduledFutureTask.nanoTime();
    }

    /* access modifiers changed from: package-private */
    public Queue<ScheduledFutureTask<?>> scheduledTaskQueue() {
        if (this.scheduledTaskQueue == null) {
            this.scheduledTaskQueue = new PriorityQueue();
        }
        return this.scheduledTaskQueue;
    }

    private static boolean isNullOrEmpty(Queue<ScheduledFutureTask<?>> queue) {
        return queue == null || queue.isEmpty();
    }

    /* access modifiers changed from: protected */
    public void cancelScheduledTasks() {
        if (inEventLoop()) {
            Queue<ScheduledFutureTask<?>> scheduledTaskQueue2 = this.scheduledTaskQueue;
            if (!isNullOrEmpty(scheduledTaskQueue2)) {
                for (ScheduledFutureTask<?> task : (ScheduledFutureTask[]) scheduledTaskQueue2.toArray(new ScheduledFutureTask[scheduledTaskQueue2.size()])) {
                    task.cancelWithoutRemove(false);
                }
                scheduledTaskQueue2.clear();
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public final Runnable pollScheduledTask() {
        return pollScheduledTask(nanoTime());
    }

    /* access modifiers changed from: protected */
    public final Runnable pollScheduledTask(long nanoTime) {
        if (inEventLoop()) {
            Queue<ScheduledFutureTask<?>> scheduledTaskQueue2 = this.scheduledTaskQueue;
            ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue2 == null ? null : scheduledTaskQueue2.peek();
            if (scheduledTask == null || scheduledTask.deadlineNanos() > nanoTime) {
                return null;
            }
            scheduledTaskQueue2.remove();
            return scheduledTask;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public final long nextScheduledTaskNano() {
        Queue<ScheduledFutureTask<?>> scheduledTaskQueue2 = this.scheduledTaskQueue;
        ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue2 == null ? null : scheduledTaskQueue2.peek();
        if (scheduledTask == null) {
            return -1;
        }
        return Math.max(0, scheduledTask.deadlineNanos() - nanoTime());
    }

    /* access modifiers changed from: package-private */
    public final ScheduledFutureTask<?> peekScheduledTask() {
        Queue<ScheduledFutureTask<?>> scheduledTaskQueue2 = this.scheduledTaskQueue;
        if (scheduledTaskQueue2 == null) {
            return null;
        }
        return scheduledTaskQueue2.peek();
    }

    /* access modifiers changed from: protected */
    public final boolean hasScheduledTasks() {
        Queue<ScheduledFutureTask<?>> scheduledTaskQueue2 = this.scheduledTaskQueue;
        ScheduledFutureTask<?> scheduledTask = scheduledTaskQueue2 == null ? null : scheduledTaskQueue2.peek();
        return scheduledTask != null && scheduledTask.deadlineNanos() <= nanoTime();
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        ObjectUtil.checkNotNull(command, "command");
        ObjectUtil.checkNotNull(unit, "unit");
        if (delay < 0) {
            delay = 0;
        }
        return schedule(new ScheduledFutureTask(this, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        ObjectUtil.checkNotNull(callable, "callable");
        ObjectUtil.checkNotNull(unit, "unit");
        if (delay < 0) {
            delay = 0;
        }
        return schedule(new ScheduledFutureTask(this, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        Runnable runnable = command;
        long j = initialDelay;
        long j2 = period;
        TimeUnit timeUnit = unit;
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[]{Long.valueOf(initialDelay)}));
        } else if (j2 > 0) {
            return schedule(new ScheduledFutureTask(this, Executors.callable(runnable, (Object) null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(j)), timeUnit.toNanos(j2)));
        } else {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[]{Long.valueOf(period)}));
        }
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        Runnable runnable = command;
        long j = initialDelay;
        long j2 = delay;
        TimeUnit timeUnit = unit;
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[]{Long.valueOf(initialDelay)}));
        } else if (j2 > 0) {
            return schedule(new ScheduledFutureTask(this, Executors.callable(runnable, (Object) null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(j)), -timeUnit.toNanos(j2)));
        } else {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[]{Long.valueOf(delay)}));
        }
    }

    /* access modifiers changed from: package-private */
    public <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
        if (inEventLoop()) {
            scheduledTaskQueue().add(task);
        } else {
            execute(new Runnable() {
                public void run() {
                    AbstractScheduledEventExecutor.this.scheduledTaskQueue().add(task);
                }
            });
        }
        return task;
    }

    /* access modifiers changed from: package-private */
    public final void removeScheduled(final ScheduledFutureTask<?> task) {
        if (inEventLoop()) {
            scheduledTaskQueue().remove(task);
        } else {
            execute(new Runnable() {
                public void run() {
                    AbstractScheduledEventExecutor.this.removeScheduled(task);
                }
            });
        }
    }
}
