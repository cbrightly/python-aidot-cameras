package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;

public final class PromiseCombiner {
    private Promise<Void> aggregatePromise;
    /* access modifiers changed from: private */
    public Throwable cause;
    /* access modifiers changed from: private */
    public boolean doneAdding;
    /* access modifiers changed from: private */
    public int doneCount;
    /* access modifiers changed from: private */
    public int expectedCount;
    private final GenericFutureListener<Future<?>> listener = new GenericFutureListener<Future<?>>() {
        public void operationComplete(Future<?> future) {
            PromiseCombiner.access$004(PromiseCombiner.this);
            if (!future.isSuccess() && PromiseCombiner.this.cause == null) {
                Throwable unused = PromiseCombiner.this.cause = future.cause();
            }
            if (PromiseCombiner.this.doneCount == PromiseCombiner.this.expectedCount && PromiseCombiner.this.doneAdding) {
                boolean unused2 = PromiseCombiner.this.tryPromise();
            }
        }
    };

    static /* synthetic */ int access$004(PromiseCombiner x0) {
        int i = x0.doneCount + 1;
        x0.doneCount = i;
        return i;
    }

    public void add(Promise promise) {
        checkAddAllowed();
        this.expectedCount++;
        promise.addListener(this.listener);
    }

    public void addAll(Promise... promises) {
        checkAddAllowed();
        this.expectedCount += promises.length;
        for (Promise promise : promises) {
            promise.addListener(this.listener);
        }
    }

    public void finish(Promise<Void> aggregatePromise2) {
        if (!this.doneAdding) {
            this.doneAdding = true;
            this.aggregatePromise = (Promise) ObjectUtil.checkNotNull(aggregatePromise2, "aggregatePromise");
            if (this.doneCount == this.expectedCount) {
                tryPromise();
                return;
            }
            return;
        }
        throw new IllegalStateException("Already finished");
    }

    /* access modifiers changed from: private */
    public boolean tryPromise() {
        Throwable th = this.cause;
        return th == null ? this.aggregatePromise.trySuccess(null) : this.aggregatePromise.tryFailure(th);
    }

    private void checkAddAllowed() {
        if (this.doneAdding) {
            throw new IllegalStateException("Adding promises is not allowed after finished adding");
        }
    }
}
