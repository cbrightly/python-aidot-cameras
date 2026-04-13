package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class UnorderedThreadPoolEventExecutor extends ScheduledThreadPoolExecutor implements EventExecutor {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) UnorderedThreadPoolEventExecutor.class);
    private final Set<EventExecutor> executorSet;
    private final Promise<?> terminationFuture;

    public UnorderedThreadPoolEventExecutor(int corePoolSize) {
        this(corePoolSize, (ThreadFactory) new DefaultThreadFactory((Class<?>) UnorderedThreadPoolEventExecutor.class));
    }

    public UnorderedThreadPoolEventExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
        this.terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
        this.executorSet = Collections.singleton(this);
    }

    public UnorderedThreadPoolEventExecutor(int corePoolSize, RejectedExecutionHandler handler) {
        this(corePoolSize, new DefaultThreadFactory((Class<?>) UnorderedThreadPoolEventExecutor.class), handler);
    }

    public UnorderedThreadPoolEventExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
        this.terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
        this.executorSet = Collections.singleton(this);
    }

    public EventExecutor next() {
        return this;
    }

    public EventExecutorGroup parent() {
        return this;
    }

    public boolean inEventLoop() {
        return false;
    }

    public boolean inEventLoop(Thread thread) {
        return false;
    }

    public <V> Promise<V> newPromise() {
        return new DefaultPromise(this);
    }

    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new DefaultProgressivePromise(this);
    }

    public <V> Future<V> newSucceededFuture(V result) {
        return new SucceededFuture(this, result);
    }

    public <V> Future<V> newFailedFuture(Throwable cause) {
        return new FailedFuture(this, cause);
    }

    public boolean isShuttingDown() {
        return isShutdown();
    }

    public List<Runnable> shutdownNow() {
        List<Runnable> tasks = super.shutdownNow();
        this.terminationFuture.trySuccess(null);
        return tasks;
    }

    public void shutdown() {
        super.shutdown();
        this.terminationFuture.trySuccess(null);
    }

    public Future<?> shutdownGracefully() {
        return shutdownGracefully(2, 15, TimeUnit.SECONDS);
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        shutdown();
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    public Iterator<EventExecutor> iterator() {
        return this.executorSet.iterator();
    }

    /* access modifiers changed from: protected */
    public <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {
        return runnable instanceof NonNotifyRunnable ? task : new RunnableScheduledFutureTask((EventExecutor) this, runnable, task);
    }

    /* access modifiers changed from: protected */
    public <V> RunnableScheduledFuture<V> decorateTask(Callable<V> callable, RunnableScheduledFuture<V> task) {
        return new RunnableScheduledFutureTask((EventExecutor) this, callable, task);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return (ScheduledFuture) super.schedule(command, delay, unit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return (ScheduledFuture) super.schedule(callable, delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return (ScheduledFuture) super.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return (ScheduledFuture) super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public Future<?> submit(Runnable task) {
        return (Future) super.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return (Future) super.submit(task, result);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return (Future) super.submit(task);
    }

    public void execute(Runnable command) {
        super.schedule(new NonNotifyRunnable(command), 0, TimeUnit.NANOSECONDS);
    }

    public static final class RunnableScheduledFutureTask<V> extends PromiseTask<V> implements RunnableScheduledFuture<V>, ScheduledFuture<V> {
        private final RunnableScheduledFuture<V> future;

        RunnableScheduledFutureTask(EventExecutor executor, Runnable runnable, RunnableScheduledFuture<V> future2) {
            super(executor, runnable, null);
            this.future = future2;
        }

        RunnableScheduledFutureTask(EventExecutor executor, Callable<V> callable, RunnableScheduledFuture<V> future2) {
            super(executor, callable);
            this.future = future2;
        }

        public void run() {
            if (!isPeriodic()) {
                super.run();
            } else if (!isDone()) {
                try {
                    this.task.call();
                } catch (Throwable cause) {
                    if (!tryFailureInternal(cause)) {
                        UnorderedThreadPoolEventExecutor.logger.warn("Failure during execution of task", cause);
                    }
                }
            }
        }

        public boolean isPeriodic() {
            return this.future.isPeriodic();
        }

        public long getDelay(TimeUnit unit) {
            return this.future.getDelay(unit);
        }

        public int compareTo(Delayed o) {
            return this.future.compareTo(o);
        }
    }

    public static final class NonNotifyRunnable implements Runnable {
        private final Runnable task;

        NonNotifyRunnable(Runnable task2) {
            this.task = task2;
        }

        public void run() {
            this.task.run();
        }
    }
}
