package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractReferenceCounted implements ReferenceCounted {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
    private volatile int refCnt = 1;

    /* access modifiers changed from: protected */
    public abstract void deallocate();

    public final int refCnt() {
        return this.refCnt;
    }

    /* access modifiers changed from: protected */
    public final void setRefCnt(int refCnt2) {
        refCntUpdater.set(this, refCnt2);
    }

    public ReferenceCounted retain() {
        return retain0(1);
    }

    public ReferenceCounted retain(int increment) {
        return retain0(ObjectUtil.checkPositive(increment, "increment"));
    }

    private ReferenceCounted retain0(int increment) {
        AtomicIntegerFieldUpdater<AbstractReferenceCounted> atomicIntegerFieldUpdater = refCntUpdater;
        int oldRef = atomicIntegerFieldUpdater.getAndAdd(this, increment);
        if (oldRef > 0 && oldRef + increment >= oldRef) {
            return this;
        }
        atomicIntegerFieldUpdater.getAndAdd(this, -increment);
        throw new IllegalReferenceCountException(oldRef, increment);
    }

    public boolean release() {
        return release0(1);
    }

    public boolean release(int decrement) {
        return release0(ObjectUtil.checkPositive(decrement, "decrement"));
    }

    private boolean release0(int decrement) {
        AtomicIntegerFieldUpdater<AbstractReferenceCounted> atomicIntegerFieldUpdater = refCntUpdater;
        int oldRef = atomicIntegerFieldUpdater.getAndAdd(this, -decrement);
        if (oldRef == decrement) {
            deallocate();
            return true;
        } else if (oldRef >= decrement && oldRef - decrement <= oldRef) {
            return false;
        } else {
            atomicIntegerFieldUpdater.getAndAdd(this, decrement);
            throw new IllegalReferenceCountException(oldRef, decrement);
        }
    }
}
