package io.netty.util.concurrent;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import io.netty.util.Signal;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(new CancellationException(), DefaultPromise.class, "cancel(...)"));
    private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
    private static final Signal SUCCESS = Signal.valueOf(DefaultPromise.class.getName() + ".SUCCESS");
    private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultPromise.class.getName() + ".UNCANCELLABLE");
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultPromise.class);
    private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
    private final EventExecutor executor;
    private Object listeners;
    private boolean notifyingListeners;
    private volatile Object result;
    private short waiters;

    public DefaultPromise(EventExecutor executor2) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(executor2, "executor");
    }

    protected DefaultPromise() {
        this.executor = null;
    }

    public Promise<V> setSuccess(V result2) {
        if (setSuccess0(result2)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    public boolean trySuccess(V result2) {
        if (!setSuccess0(result2)) {
            return false;
        }
        notifyListeners();
        return true;
    }

    public Promise<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, cause);
    }

    public boolean tryFailure(Throwable cause) {
        if (!setFailure0(cause)) {
            return false;
        }
        notifyListeners();
        return true;
    }

    public boolean setUncancellable() {
        if (RESULT_UPDATER.compareAndSet(this, (Object) null, UNCANCELLABLE)) {
            return true;
        }
        Object result2 = this.result;
        if (!isDone0(result2) || !isCancelled0(result2)) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        Object result2 = this.result;
        return (result2 == null || result2 == UNCANCELLABLE || (result2 instanceof CauseHolder)) ? false : true;
    }

    public boolean isCancellable() {
        return this.result == null;
    }

    public Throwable cause() {
        Object result2 = this.result;
        if (result2 instanceof CauseHolder) {
            return ((CauseHolder) result2).cause;
        }
        return null;
    }

    public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
        ObjectUtil.checkNotNull(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        synchronized (this) {
            addListener0(listener);
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners2) {
        ObjectUtil.checkNotNull(listeners2, "listeners");
        synchronized (this) {
            int length = listeners2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                GenericFutureListener<? extends Future<? super V>> listener = listeners2[i];
                if (listener == null) {
                    break;
                }
                addListener0(listener);
                i++;
            }
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
        ObjectUtil.checkNotNull(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        synchronized (this) {
            removeListener0(listener);
        }
        return this;
    }

    public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners2) {
        ObjectUtil.checkNotNull(listeners2, "listeners");
        synchronized (this) {
            int length = listeners2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                GenericFutureListener<? extends Future<? super V>> listener = listeners2[i];
                if (listener == null) {
                    break;
                }
                removeListener0(listener);
                i++;
            }
        }
        return this;
    }

    /* JADX INFO: finally extract failed */
    public Promise<V> await() {
        if (isDone()) {
            return this;
        }
        if (!Thread.interrupted()) {
            checkDeadLock();
            synchronized (this) {
                while (!isDone()) {
                    incWaiters();
                    try {
                        wait();
                        decWaiters();
                    } catch (Throwable th) {
                        decWaiters();
                        throw th;
                    }
                }
            }
            return this;
        }
        throw new InterruptedException(toString());
    }

    public Promise<V> awaitUninterruptibly() {
        if (isDone()) {
            return this;
        }
        checkDeadLock();
        boolean interrupted = false;
        synchronized (this) {
            while (!isDone()) {
                incWaiters();
                try {
                    wait();
                } catch (InterruptedException e) {
                    interrupted = true;
                } catch (Throwable th) {
                    decWaiters();
                    throw th;
                }
                decWaiters();
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    public boolean await(long timeout, TimeUnit unit) {
        return await0(unit.toNanos(timeout), true);
    }

    public boolean await(long timeoutMillis) {
        return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
    }

    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        try {
            return await0(unit.toNanos(timeout), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long timeoutMillis) {
        try {
            return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public V getNow() {
        Object result2 = this.result;
        if ((result2 instanceof CauseHolder) || result2 == SUCCESS) {
            return null;
        }
        return result2;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!RESULT_UPDATER.compareAndSet(this, (Object) null, CANCELLATION_CAUSE_HOLDER)) {
            return false;
        }
        checkNotifyWaiters();
        notifyListeners();
        return true;
    }

    public boolean isCancelled() {
        return isCancelled0(this.result);
    }

    public boolean isDone() {
        return isDone0(this.result);
    }

    public Promise<V> sync() {
        await();
        rethrowIfFailed();
        return this;
    }

    public Promise<V> syncUninterruptibly() {
        awaitUninterruptibly();
        rethrowIfFailed();
        return this;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    /* access modifiers changed from: protected */
    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('@');
        StringBuilder buf = sb.append(Integer.toHexString(hashCode()));
        Object result2 = this.result;
        if (result2 == SUCCESS) {
            buf.append("(success)");
        } else if (result2 == UNCANCELLABLE) {
            buf.append("(uncancellable)");
        } else if (result2 instanceof CauseHolder) {
            buf.append("(failure: ");
            buf.append(((CauseHolder) result2).cause);
            buf.append(')');
        } else if (result2 != null) {
            buf.append("(success: ");
            buf.append(result2);
            buf.append(')');
        } else {
            buf.append("(incomplete)");
        }
        return buf;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return this.executor;
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        EventExecutor e = executor();
        if (e != null && e.inEventLoop()) {
            throw new BlockingOperationException(toString());
        }
    }

    protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> listener) {
        ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
        ObjectUtil.checkNotNull(future, "future");
        ObjectUtil.checkNotNull(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        notifyListenerWithStackOverFlowProtection(eventExecutor, future, listener);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r1 = io.netty.util.internal.InternalThreadLocalMap.get();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyListeners() {
        /*
            r4 = this;
            io.netty.util.concurrent.EventExecutor r0 = r4.executor()
            boolean r1 = r0.inEventLoop()
            if (r1 == 0) goto L_0x0028
            io.netty.util.internal.InternalThreadLocalMap r1 = io.netty.util.internal.InternalThreadLocalMap.get()
            int r2 = r1.futureListenerStackDepth()
            int r3 = MAX_LISTENER_STACK_DEPTH
            if (r2 >= r3) goto L_0x0028
            int r3 = r2 + 1
            r1.setFutureListenerStackDepth(r3)
            r4.notifyListenersNow()     // Catch:{ all -> 0x0023 }
            r1.setFutureListenerStackDepth(r2)
            return
        L_0x0023:
            r3 = move-exception
            r1.setFutureListenerStackDepth(r2)
            throw r3
        L_0x0028:
            io.netty.util.concurrent.DefaultPromise$1 r1 = new io.netty.util.concurrent.DefaultPromise$1
            r1.<init>()
            safeExecute(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.DefaultPromise.notifyListeners():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = io.netty.util.internal.InternalThreadLocalMap.get();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void notifyListenerWithStackOverFlowProtection(io.netty.util.concurrent.EventExecutor r3, final io.netty.util.concurrent.Future<?> r4, final io.netty.util.concurrent.GenericFutureListener<?> r5) {
        /*
            boolean r0 = r3.inEventLoop()
            if (r0 == 0) goto L_0x0024
            io.netty.util.internal.InternalThreadLocalMap r0 = io.netty.util.internal.InternalThreadLocalMap.get()
            int r1 = r0.futureListenerStackDepth()
            int r2 = MAX_LISTENER_STACK_DEPTH
            if (r1 >= r2) goto L_0x0024
            int r2 = r1 + 1
            r0.setFutureListenerStackDepth(r2)
            notifyListener0(r4, r5)     // Catch:{ all -> 0x001f }
            r0.setFutureListenerStackDepth(r1)
            return
        L_0x001f:
            r2 = move-exception
            r0.setFutureListenerStackDepth(r1)
            throw r2
        L_0x0024:
            io.netty.util.concurrent.DefaultPromise$2 r0 = new io.netty.util.concurrent.DefaultPromise$2
            r0.<init>(r4, r5)
            safeExecute(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.DefaultPromise.notifyListenerWithStackOverFlowProtection(io.netty.util.concurrent.EventExecutor, io.netty.util.concurrent.Future, io.netty.util.concurrent.GenericFutureListener):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        if ((r0 instanceof io.netty.util.concurrent.DefaultFutureListeners) == false) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        notifyListeners0((io.netty.util.concurrent.DefaultFutureListeners) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        notifyListener0(r3, (io.netty.util.concurrent.GenericFutureListener) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r2 = r3.listeners;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
        if (r2 != null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0028, code lost:
        r3.notifyingListeners = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002d, code lost:
        r0 = r2;
        r3.listeners = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0030, code lost:
        monitor-exit(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyListenersNow() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.notifyingListeners     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x0035
            java.lang.Object r0 = r3.listeners     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x000a
            goto L_0x0035
        L_0x000a:
            r1 = 1
            r3.notifyingListeners = r1     // Catch:{ all -> 0x0037 }
            r1 = 0
            r3.listeners = r1     // Catch:{ all -> 0x0037 }
            monitor-exit(r3)     // Catch:{ all -> 0x0037 }
        L_0x0012:
            boolean r2 = r0 instanceof io.netty.util.concurrent.DefaultFutureListeners
            if (r2 == 0) goto L_0x001d
            r2 = r0
            io.netty.util.concurrent.DefaultFutureListeners r2 = (io.netty.util.concurrent.DefaultFutureListeners) r2
            r3.notifyListeners0(r2)
            goto L_0x0023
        L_0x001d:
            r2 = r0
            io.netty.util.concurrent.GenericFutureListener r2 = (io.netty.util.concurrent.GenericFutureListener) r2
            notifyListener0(r3, r2)
        L_0x0023:
            monitor-enter(r3)
            java.lang.Object r2 = r3.listeners     // Catch:{ all -> 0x0032 }
            if (r2 != 0) goto L_0x002d
            r1 = 0
            r3.notifyingListeners = r1     // Catch:{ all -> 0x0032 }
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            return
        L_0x002d:
            r0 = r2
            r3.listeners = r1     // Catch:{ all -> 0x0032 }
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            goto L_0x0012
        L_0x0032:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            throw r1
        L_0x0035:
            monitor-exit(r3)     // Catch:{ all -> 0x0037 }
            return
        L_0x0037:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0037 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.DefaultPromise.notifyListenersNow():void");
    }

    private void notifyListeners0(DefaultFutureListeners listeners2) {
        GenericFutureListener<?>[] a = listeners2.listeners();
        int size = listeners2.size();
        for (int i = 0; i < size; i++) {
            notifyListener0(this, a[i]);
        }
    }

    /* access modifiers changed from: private */
    public static void notifyListener0(Future future, GenericFutureListener l) {
        try {
            l.operationComplete(future);
        } catch (Throwable t) {
            InternalLogger internalLogger = logger;
            internalLogger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
        }
    }

    private void addListener0(GenericFutureListener<? extends Future<? super V>> listener) {
        Object obj = this.listeners;
        if (obj == null) {
            this.listeners = listener;
        } else if (obj instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) obj).add(listener);
        } else {
            this.listeners = new DefaultFutureListeners((GenericFutureListener) obj, listener);
        }
    }

    private void removeListener0(GenericFutureListener<? extends Future<? super V>> listener) {
        Object obj = this.listeners;
        if (obj instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) obj).remove(listener);
        } else if (obj == listener) {
            this.listeners = null;
        }
    }

    private boolean setSuccess0(V result2) {
        return setValue0(result2 == null ? SUCCESS : result2);
    }

    private boolean setFailure0(Throwable cause) {
        return setValue0(new CauseHolder((Throwable) ObjectUtil.checkNotNull(cause, "cause")));
    }

    private boolean setValue0(Object objResult) {
        AtomicReferenceFieldUpdater<DefaultPromise, Object> atomicReferenceFieldUpdater = RESULT_UPDATER;
        if (!atomicReferenceFieldUpdater.compareAndSet(this, (Object) null, objResult) && !atomicReferenceFieldUpdater.compareAndSet(this, UNCANCELLABLE, objResult)) {
            return false;
        }
        checkNotifyWaiters();
        return true;
    }

    private synchronized void checkNotifyWaiters() {
        if (this.waiters > 0) {
            notifyAll();
        }
    }

    private void incWaiters() {
        short s = this.waiters;
        if (s != Short.MAX_VALUE) {
            this.waiters = (short) (s + 1);
            return;
        }
        throw new IllegalStateException("too many waiters: " + this);
    }

    private void decWaiters() {
        this.waiters = (short) (this.waiters - 1);
    }

    private void rethrowIfFailed() {
        Throwable cause = cause();
        if (cause != null) {
            PlatformDependent.throwException(cause);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        if (r0 == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0040, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x005f, code lost:
        if (isDone() == false) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0062, code lost:
        if (r0 == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0064, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006b, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r6 = r13 - (java.lang.System.nanoTime() - r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean await0(long r13, boolean r15) {
        /*
            r12 = this;
            boolean r0 = r12.isDone()
            r1 = 1
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            r2 = 0
            int r0 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0013
            boolean r0 = r12.isDone()
            return r0
        L_0x0013:
            if (r15 == 0) goto L_0x0026
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x001c
            goto L_0x0026
        L_0x001c:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            java.lang.String r1 = r12.toString()
            r0.<init>(r1)
            throw r0
        L_0x0026:
            r12.checkDeadLock()
            long r4 = java.lang.System.nanoTime()
            r6 = r13
            r0 = 0
        L_0x002f:
            monitor-enter(r12)     // Catch:{ all -> 0x008e }
            boolean r8 = r12.isDone()     // Catch:{ all -> 0x008b }
            if (r8 == 0) goto L_0x0041
            monitor-exit(r12)     // Catch:{ all -> 0x008b }
            if (r0 == 0) goto L_0x0040
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x0040:
            return r1
        L_0x0041:
            r12.incWaiters()     // Catch:{ all -> 0x008b }
            r8 = 1000000(0xf4240, double:4.940656E-318)
            long r10 = r6 / r8
            long r8 = r6 % r8
            int r8 = (int) r8     // Catch:{ InterruptedException -> 0x0055 }
            r12.wait(r10, r8)     // Catch:{ InterruptedException -> 0x0055 }
        L_0x004f:
            r12.decWaiters()     // Catch:{ all -> 0x008b }
            goto L_0x005a
        L_0x0053:
            r1 = move-exception
            goto L_0x0087
        L_0x0055:
            r8 = move-exception
            if (r15 != 0) goto L_0x0085
            r0 = 1
            goto L_0x004f
        L_0x005a:
            monitor-exit(r12)     // Catch:{ all -> 0x008b }
            boolean r8 = r12.isDone()     // Catch:{ all -> 0x008e }
            if (r8 == 0) goto L_0x006c
            if (r0 == 0) goto L_0x006b
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x006b:
            return r1
        L_0x006c:
            long r8 = java.lang.System.nanoTime()     // Catch:{ all -> 0x008e }
            long r8 = r8 - r4
            long r6 = r13 - r8
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 > 0) goto L_0x002f
            boolean r1 = r12.isDone()     // Catch:{ all -> 0x008e }
            if (r0 == 0) goto L_0x0084
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x0084:
            return r1
        L_0x0085:
            throw r8     // Catch:{ all -> 0x0053 }
        L_0x0087:
            r12.decWaiters()     // Catch:{ all -> 0x008b }
            throw r1     // Catch:{ all -> 0x008b }
        L_0x008b:
            r1 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x008b }
            throw r1     // Catch:{ all -> 0x008e }
        L_0x008e:
            r1 = move-exception
            if (r0 == 0) goto L_0x0098
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x0098:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.DefaultPromise.await0(long, boolean):boolean");
    }

    /* access modifiers changed from: package-private */
    public void notifyProgressiveListeners(long progress, long total) {
        Object listeners2 = progressiveListeners();
        if (listeners2 != null) {
            ProgressiveFuture<V> self = (ProgressiveFuture) this;
            EventExecutor executor2 = executor();
            if (executor2.inEventLoop()) {
                if (listeners2 instanceof GenericProgressiveFutureListener[]) {
                    notifyProgressiveListeners0(self, (GenericProgressiveFutureListener[]) listeners2, progress, total);
                    return;
                }
                notifyProgressiveListener0(self, (GenericProgressiveFutureListener) listeners2, progress, total);
            } else if (listeners2 instanceof GenericProgressiveFutureListener[]) {
                final GenericProgressiveFutureListener<?>[] array = (GenericProgressiveFutureListener[]) listeners2;
                final ProgressiveFuture<V> progressiveFuture = self;
                final long j = progress;
                final long j2 = total;
                safeExecute(executor2, new Runnable() {
                    public void run() {
                        DefaultPromise.notifyProgressiveListeners0(progressiveFuture, array, j, j2);
                    }
                });
            } else {
                final ProgressiveFuture<V> progressiveFuture2 = self;
                final GenericProgressiveFutureListener<ProgressiveFuture<V>> genericProgressiveFutureListener = (GenericProgressiveFutureListener) listeners2;
                final long j3 = progress;
                final long j4 = total;
                safeExecute(executor2, new Runnable() {
                    public void run() {
                        DefaultPromise.notifyProgressiveListener0(progressiveFuture2, genericProgressiveFutureListener, j3, j4);
                    }
                });
            }
        }
    }

    private synchronized Object progressiveListeners() {
        Object listeners2 = this.listeners;
        if (listeners2 == null) {
            return null;
        }
        if (listeners2 instanceof DefaultFutureListeners) {
            DefaultFutureListeners dfl = (DefaultFutureListeners) listeners2;
            int progressiveSize = dfl.progressiveSize();
            switch (progressiveSize) {
                case 0:
                    return null;
                case 1:
                    for (GenericFutureListener<?> l : dfl.listeners()) {
                        if (l instanceof GenericProgressiveFutureListener) {
                            return l;
                        }
                    }
                    return null;
                default:
                    GenericFutureListener<?>[] array = dfl.listeners();
                    GenericProgressiveFutureListener<?>[] copy = new GenericProgressiveFutureListener[progressiveSize];
                    int i = 0;
                    int j = 0;
                    while (j < progressiveSize) {
                        GenericFutureListener<?> l2 = array[i];
                        if (l2 instanceof GenericProgressiveFutureListener) {
                            int j2 = j + 1;
                            copy[j] = (GenericProgressiveFutureListener) l2;
                            j = j2;
                        }
                        i++;
                    }
                    return copy;
            }
        } else if (listeners2 instanceof GenericProgressiveFutureListener) {
            return listeners2;
        } else {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners2, long progress, long total) {
        int length = listeners2.length;
        int i = 0;
        while (i < length) {
            GenericProgressiveFutureListener<?> l = listeners2[i];
            if (l != null) {
                notifyProgressiveListener0(future, l, progress, total);
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener l, long progress, long total) {
        try {
            l.operationProgressed(future, progress, total);
        } catch (Throwable t) {
            InternalLogger internalLogger = logger;
            internalLogger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", t);
        }
    }

    private static boolean isCancelled0(Object result2) {
        return (result2 instanceof CauseHolder) && (((CauseHolder) result2).cause instanceof CancellationException);
    }

    private static boolean isDone0(Object result2) {
        return (result2 == null || result2 == UNCANCELLABLE) ? false : true;
    }

    public static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable cause2) {
            this.cause = cause2;
        }
    }

    private static void safeExecute(EventExecutor executor2, Runnable task) {
        try {
            executor2.execute(task);
        } catch (Throwable t) {
            rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", t);
        }
    }
}
