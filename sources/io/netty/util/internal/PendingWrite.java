package io.netty.util.internal;

import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Promise;

public final class PendingWrite {
    private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>() {
        /* access modifiers changed from: protected */
        public PendingWrite newObject(Recycler.Handle handle) {
            return new PendingWrite(handle);
        }
    };
    private final Recycler.Handle handle;
    private Object msg;
    private Promise<Void> promise;

    public static PendingWrite newInstance(Object msg2, Promise<Void> promise2) {
        PendingWrite pending = RECYCLER.get();
        pending.msg = msg2;
        pending.promise = promise2;
        return pending;
    }

    private PendingWrite(Recycler.Handle handle2) {
        this.handle = handle2;
    }

    public boolean recycle() {
        this.msg = null;
        this.promise = null;
        return RECYCLER.recycle(this, this.handle);
    }

    public boolean failAndRecycle(Throwable cause) {
        ReferenceCountUtil.release(this.msg);
        Promise<Void> promise2 = this.promise;
        if (promise2 != null) {
            promise2.setFailure(cause);
        }
        return recycle();
    }

    public boolean successAndRecycle() {
        Promise<Void> promise2 = this.promise;
        if (promise2 != null) {
            promise2.setSuccess(null);
        }
        return recycle();
    }

    public Object msg() {
        return this.msg;
    }

    public Promise<Void> promise() {
        return this.promise;
    }

    public Promise<Void> recycleAndGet() {
        Promise<Void> promise2 = this.promise;
        recycle();
        return promise2;
    }
}
