package org.glassfish.grizzly.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import org.glassfish.grizzly.CompletionHandler;

public class SafeFutureImpl<R> implements FutureImpl<R> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Object chSync = new Object();
    private Set<CompletionHandler<R>> completionHandlers;
    private final SafeFutureImpl<R>.Sync sync = new Sync();

    static {
        Class<SafeFutureImpl> cls = SafeFutureImpl.class;
    }

    public void addCompletionHandler(CompletionHandler<R> completionHandler) {
        if (isDone()) {
            notifyCompletionHandler(completionHandler);
            return;
        }
        synchronized (this.chSync) {
            if (!isDone()) {
                if (this.completionHandlers == null) {
                    this.completionHandlers = new HashSet(2);
                }
                this.completionHandlers.add(completionHandler);
                return;
            }
            notifyCompletionHandler(completionHandler);
        }
    }

    public static <R> SafeFutureImpl<R> create() {
        return new SafeFutureImpl<>();
    }

    public void result(R result) {
        this.sync.innerSet(result);
    }

    public void failure(Throwable failure) {
        this.sync.innerSetException(failure);
    }

    public void markForRecycle(boolean recycleResult) {
    }

    public void recycle(boolean recycleResult) {
    }

    public void recycle() {
    }

    public R getResult() {
        if (!isDone()) {
            return null;
        }
        try {
            return get();
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onComplete() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        r0 = isCancelled();
        r2 = org.glassfish.grizzly.impl.SafeFutureImpl.Sync.access$100(r7.sync);
        r3 = org.glassfish.grizzly.impl.SafeFutureImpl.Sync.access$200(r7.sync);
        r4 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        if (r4.hasNext() == false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        r5 = r4.next();
        r4.remove();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
        if (r0 == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r5.cancelled();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        if (r3 == null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        r5.failed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
        r5.completed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyCompletionHandlers() {
        /*
            r7 = this;
            boolean r0 = r7.isDone()
            if (r0 == 0) goto L_0x004d
            java.lang.Object r0 = r7.chSync
            monitor-enter(r0)
            java.util.Set<org.glassfish.grizzly.CompletionHandler<R>> r1 = r7.completionHandlers     // Catch:{ all -> 0x004a }
            if (r1 != 0) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            return
        L_0x000f:
            r2 = 0
            r7.completionHandlers = r2     // Catch:{ all -> 0x004a }
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            boolean r0 = r7.isCancelled()
            org.glassfish.grizzly.impl.SafeFutureImpl<R>$Sync r2 = r7.sync
            java.lang.Object r2 = r2.result
            org.glassfish.grizzly.impl.SafeFutureImpl<R>$Sync r3 = r7.sync
            java.lang.Throwable r3 = r3.exception
            java.util.Iterator r4 = r1.iterator()
        L_0x0028:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0049
            java.lang.Object r5 = r4.next()
            org.glassfish.grizzly.CompletionHandler r5 = (org.glassfish.grizzly.CompletionHandler) r5
            r4.remove()
            if (r0 == 0) goto L_0x003f
            r5.cancelled()     // Catch:{ Exception -> 0x003d }
            goto L_0x0048
        L_0x003d:
            r6 = move-exception
            goto L_0x0048
        L_0x003f:
            if (r3 == 0) goto L_0x0045
            r5.failed(r3)     // Catch:{ Exception -> 0x003d }
            goto L_0x0048
        L_0x0045:
            r5.completed(r2)     // Catch:{ Exception -> 0x003d }
        L_0x0048:
            goto L_0x0028
        L_0x0049:
            return
        L_0x004a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            throw r1
        L_0x004d:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.impl.SafeFutureImpl.notifyCompletionHandlers():void");
    }

    private void notifyCompletionHandler(CompletionHandler<R> completionHandler) {
        if (isCancelled()) {
            completionHandler.cancelled();
            return;
        }
        try {
            try {
                completionHandler.completed(get());
            } catch (Exception e) {
            }
        } catch (ExecutionException e2) {
            completionHandler.failed(e2.getCause());
        } catch (Exception e3) {
            completionHandler.failed(e3);
        }
    }

    public boolean isCancelled() {
        return this.sync.innerIsCancelled();
    }

    public boolean isDone() {
        return this.sync.ranOrCancelled();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.sync.innerCancel(mayInterruptIfRunning);
    }

    public R get() {
        return this.sync.innerGet();
    }

    public R get(long timeout, TimeUnit unit) {
        return this.sync.innerGet(unit.toNanos(timeout));
    }

    /* access modifiers changed from: protected */
    public void done() {
        notifyCompletionHandlers();
        onComplete();
    }

    public final class Sync extends AbstractQueuedSynchronizer {
        private static final int CANCELLED = 3;
        private static final int RAN = 2;
        private static final int READY = 0;
        private static final int RESULT = 1;
        private static final long serialVersionUID = -7828117401763700385L;
        /* access modifiers changed from: private */
        public Throwable exception;
        /* access modifiers changed from: private */
        public R result;

        private Sync() {
        }

        /* access modifiers changed from: private */
        public boolean ranOrCancelled() {
            return (getState() & 3) != 0;
        }

        /* access modifiers changed from: protected */
        public int tryAcquireShared(int ignore) {
            return ranOrCancelled() ? 1 : -1;
        }

        /* access modifiers changed from: protected */
        public boolean tryReleaseShared(int ignore) {
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean innerIsCancelled() {
            return getState() == 3;
        }

        /* access modifiers changed from: package-private */
        public R innerGet() {
            acquireSharedInterruptibly(0);
            if (getState() == 3) {
                throw new CancellationException();
            } else if (this.exception == null) {
                return this.result;
            } else {
                throw new ExecutionException(this.exception);
            }
        }

        /* access modifiers changed from: package-private */
        public R innerGet(long nanosTimeout) {
            if (!tryAcquireSharedNanos(0, nanosTimeout)) {
                throw new TimeoutException();
            } else if (getState() == 3) {
                throw new CancellationException();
            } else if (this.exception == null) {
                return this.result;
            } else {
                throw new ExecutionException(this.exception);
            }
        }

        /* access modifiers changed from: package-private */
        public void innerSet(R v) {
            if (compareAndSetState(0, 1)) {
                this.result = v;
                setState(2);
                releaseShared(0);
                SafeFutureImpl.this.done();
            }
        }

        /* access modifiers changed from: package-private */
        public void innerSetException(Throwable t) {
            if (compareAndSetState(0, 1)) {
                this.exception = t;
                setState(2);
                releaseShared(0);
                SafeFutureImpl.this.done();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean innerCancel(boolean mayInterruptIfRunning) {
            if (!compareAndSetState(0, 3)) {
                return false;
            }
            releaseShared(0);
            SafeFutureImpl.this.done();
            return true;
        }
    }
}
