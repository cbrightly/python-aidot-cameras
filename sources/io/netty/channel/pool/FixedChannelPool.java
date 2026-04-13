package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FixedChannelPool extends SimpleChannelPool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final IllegalStateException FULL_EXCEPTION = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("Too many outstanding acquire operations"), FixedChannelPool.class, "acquire0(...)"));
    static final IllegalStateException POOL_CLOSED_ON_ACQUIRE_EXCEPTION = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("FixedChannelPooled was closed"), FixedChannelPool.class, "acquire0(...)"));
    static final IllegalStateException POOL_CLOSED_ON_RELEASE_EXCEPTION = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("FixedChannelPooled was closed"), FixedChannelPool.class, "release(...)"));
    /* access modifiers changed from: private */
    public static final TimeoutException TIMEOUT_EXCEPTION = ((TimeoutException) ThrowableUtil.unknownStackTrace(new TimeoutException("Acquire operation took longer then configured maximum time"), FixedChannelPool.class, "<init>(...)"));
    /* access modifiers changed from: private */
    public final long acquireTimeoutNanos;
    /* access modifiers changed from: private */
    public int acquiredChannelCount;
    /* access modifiers changed from: private */
    public boolean closed;
    /* access modifiers changed from: private */
    public final EventExecutor executor;
    private final int maxConnections;
    private final int maxPendingAcquires;
    /* access modifiers changed from: private */
    public int pendingAcquireCount;
    /* access modifiers changed from: private */
    public final Queue<AcquireTask> pendingAcquireQueue;
    private final Runnable timeoutTask;

    public enum AcquireTimeoutAction {
        NEW,
        FAIL
    }

    static {
        Class<FixedChannelPool> cls = FixedChannelPool.class;
    }

    static /* synthetic */ int access$1108(FixedChannelPool x0) {
        int i = x0.acquiredChannelCount;
        x0.acquiredChannelCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$906(FixedChannelPool x0) {
        int i = x0.pendingAcquireCount - 1;
        x0.pendingAcquireCount = i;
        return i;
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections2) {
        this(bootstrap, handler, maxConnections2, Integer.MAX_VALUE);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections2, int maxPendingAcquires2) {
        this(bootstrap, handler, ChannelHealthChecker.ACTIVE, (AcquireTimeoutAction) null, -1, maxConnections2, maxPendingAcquires2);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections2, int maxPendingAcquires2) {
        this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections2, maxPendingAcquires2, true);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections2, int maxPendingAcquires2, boolean releaseHealthCheck) {
        this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections2, maxPendingAcquires2, releaseHealthCheck, true);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections2, int maxPendingAcquires2, boolean releaseHealthCheck, boolean lastRecentUsed) {
        super(bootstrap, handler, healthCheck, releaseHealthCheck, lastRecentUsed);
        long j = acquireTimeoutMillis;
        int i = maxConnections2;
        int i2 = maxPendingAcquires2;
        this.pendingAcquireQueue = new ArrayDeque();
        if (i < 1) {
            throw new IllegalArgumentException("maxConnections: " + i + " (expected: >= 1)");
        } else if (i2 >= 1) {
            if (action == null && j == -1) {
                this.timeoutTask = null;
                this.acquireTimeoutNanos = -1;
            } else if (action == null && j != -1) {
                throw new NullPointerException("action");
            } else if (action == null || j >= 0) {
                this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(j);
                switch (AnonymousClass6.$SwitchMap$io$netty$channel$pool$FixedChannelPool$AcquireTimeoutAction[action.ordinal()]) {
                    case 1:
                        this.timeoutTask = new TimeoutTask() {
                            public void onTimeout(AcquireTask task) {
                                task.promise.setFailure(FixedChannelPool.TIMEOUT_EXCEPTION);
                            }
                        };
                        break;
                    case 2:
                        this.timeoutTask = new TimeoutTask() {
                            public void onTimeout(AcquireTask task) {
                                task.acquired();
                                Future unused = FixedChannelPool.super.acquire(task.promise);
                            }
                        };
                        break;
                    default:
                        throw new Error();
                }
            } else {
                throw new IllegalArgumentException("acquireTimeoutMillis: " + j + " (expected: >= 0)");
            }
            this.executor = bootstrap.group().next();
            this.maxConnections = i;
            this.maxPendingAcquires = i2;
        } else {
            throw new IllegalArgumentException("maxPendingAcquires: " + i2 + " (expected: >= 1)");
        }
    }

    /* renamed from: io.netty.channel.pool.FixedChannelPool$6  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$channel$pool$FixedChannelPool$AcquireTimeoutAction;

        static {
            int[] iArr = new int[AcquireTimeoutAction.values().length];
            $SwitchMap$io$netty$channel$pool$FixedChannelPool$AcquireTimeoutAction = iArr;
            try {
                iArr[AcquireTimeoutAction.FAIL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$channel$pool$FixedChannelPool$AcquireTimeoutAction[AcquireTimeoutAction.NEW.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public Future<Channel> acquire(final Promise<Channel> promise) {
        try {
            if (this.executor.inEventLoop()) {
                acquire0(promise);
            } else {
                this.executor.execute(new Runnable() {
                    public void run() {
                        FixedChannelPool.this.acquire0(promise);
                    }
                });
            }
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void acquire0(Promise<Channel> promise) {
        if (!this.executor.inEventLoop()) {
            throw new AssertionError();
        } else if (this.closed) {
            promise.setFailure(POOL_CLOSED_ON_ACQUIRE_EXCEPTION);
        } else {
            int i = this.acquiredChannelCount;
            if (i >= this.maxConnections) {
                if (this.pendingAcquireCount >= this.maxPendingAcquires) {
                    promise.setFailure(FULL_EXCEPTION);
                } else {
                    AcquireTask task = new AcquireTask(promise);
                    if (this.pendingAcquireQueue.offer(task)) {
                        this.pendingAcquireCount++;
                        Runnable runnable = this.timeoutTask;
                        if (runnable != null) {
                            task.timeoutFuture = this.executor.schedule(runnable, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
                        }
                    } else {
                        promise.setFailure(FULL_EXCEPTION);
                    }
                }
                if (this.pendingAcquireCount <= 0) {
                    throw new AssertionError();
                }
            } else if (i >= 0) {
                Promise<Channel> p = this.executor.newPromise();
                AcquireListener l = new AcquireListener(promise);
                l.acquired();
                p.addListener(l);
                super.acquire(p);
            } else {
                throw new AssertionError();
            }
        }
    }

    public Future<Void> release(final Channel channel, final Promise<Void> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        super.release(channel, this.executor.newPromise().addListener(new FutureListener<Void>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<FixedChannelPool> cls = FixedChannelPool.class;
            }

            public void operationComplete(Future<Void> future) {
                if (!FixedChannelPool.this.executor.inEventLoop()) {
                    throw new AssertionError();
                } else if (FixedChannelPool.this.closed) {
                    channel.close();
                    promise.setFailure(FixedChannelPool.POOL_CLOSED_ON_RELEASE_EXCEPTION);
                } else if (future.isSuccess()) {
                    FixedChannelPool.this.decrementAndRunTaskQueue();
                    promise.setSuccess(null);
                } else {
                    if (!(future.cause() instanceof IllegalArgumentException)) {
                        FixedChannelPool.this.decrementAndRunTaskQueue();
                    }
                    promise.setFailure(future.cause());
                }
            }
        }));
        return promise;
    }

    /* access modifiers changed from: private */
    public void decrementAndRunTaskQueue() {
        int i = this.acquiredChannelCount - 1;
        this.acquiredChannelCount = i;
        if (i >= 0) {
            runTaskQueue();
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public void runTaskQueue() {
        AcquireTask task;
        while (this.acquiredChannelCount < this.maxConnections && (task = this.pendingAcquireQueue.poll()) != null) {
            ScheduledFuture<?> timeoutFuture = task.timeoutFuture;
            if (timeoutFuture != null) {
                timeoutFuture.cancel(false);
            }
            this.pendingAcquireCount--;
            task.acquired();
            super.acquire(task.promise);
        }
        if (this.pendingAcquireCount < 0) {
            throw new AssertionError();
        } else if (this.acquiredChannelCount < 0) {
            throw new AssertionError();
        }
    }

    public final class AcquireTask extends AcquireListener {
        final long expireNanoTime;
        final Promise<Channel> promise;
        ScheduledFuture<?> timeoutFuture;

        public AcquireTask(Promise<Channel> promise2) {
            super(promise2);
            this.expireNanoTime = System.nanoTime() + FixedChannelPool.this.acquireTimeoutNanos;
            this.promise = FixedChannelPool.this.executor.newPromise().addListener(this);
        }
    }

    public abstract class TimeoutTask implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public abstract void onTimeout(AcquireTask acquireTask);

        static {
            Class<FixedChannelPool> cls = FixedChannelPool.class;
        }

        private TimeoutTask() {
        }

        public final void run() {
            if (FixedChannelPool.this.executor.inEventLoop()) {
                long nanoTime = System.nanoTime();
                while (true) {
                    AcquireTask task = (AcquireTask) FixedChannelPool.this.pendingAcquireQueue.peek();
                    if (task != null && nanoTime - task.expireNanoTime >= 0) {
                        FixedChannelPool.this.pendingAcquireQueue.remove();
                        FixedChannelPool.access$906(FixedChannelPool.this);
                        onTimeout(task);
                    } else {
                        return;
                    }
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    public class AcquireListener implements FutureListener<Channel> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected boolean acquired;
        private final Promise<Channel> originalPromise;

        static {
            Class<FixedChannelPool> cls = FixedChannelPool.class;
        }

        AcquireListener(Promise<Channel> originalPromise2) {
            this.originalPromise = originalPromise2;
        }

        public void operationComplete(Future<Channel> future) {
            if (!FixedChannelPool.this.executor.inEventLoop()) {
                throw new AssertionError();
            } else if (FixedChannelPool.this.closed) {
                if (future.isSuccess()) {
                    future.getNow().close();
                }
                this.originalPromise.setFailure(FixedChannelPool.POOL_CLOSED_ON_ACQUIRE_EXCEPTION);
            } else if (future.isSuccess()) {
                this.originalPromise.setSuccess(future.getNow());
            } else {
                if (this.acquired) {
                    FixedChannelPool.this.decrementAndRunTaskQueue();
                } else {
                    FixedChannelPool.this.runTaskQueue();
                }
                this.originalPromise.setFailure(future.cause());
            }
        }

        public void acquired() {
            if (!this.acquired) {
                FixedChannelPool.access$1108(FixedChannelPool.this);
                this.acquired = true;
            }
        }
    }

    public void close() {
        this.executor.execute(new Runnable() {
            public void run() {
                if (!FixedChannelPool.this.closed) {
                    boolean unused = FixedChannelPool.this.closed = true;
                    while (true) {
                        AcquireTask task = (AcquireTask) FixedChannelPool.this.pendingAcquireQueue.poll();
                        if (task == null) {
                            int unused2 = FixedChannelPool.this.acquiredChannelCount = 0;
                            int unused3 = FixedChannelPool.this.pendingAcquireCount = 0;
                            FixedChannelPool.super.close();
                            return;
                        }
                        ScheduledFuture<?> f = task.timeoutFuture;
                        if (f != null) {
                            f.cancel(false);
                        }
                        task.promise.setFailure(new ClosedChannelException());
                    }
                }
            }
        });
    }
}
