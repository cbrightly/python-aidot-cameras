package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.ThreadCache;

public final class SuspendStatus {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ThreadCache.CachedTypeIndex<SuspendStatus> CACHE_IDX = ThreadCache.obtainIndex(SuspendStatus.class, 4);
    private final Thread initThread = Thread.currentThread();
    private State state;

    public enum State {
        NOT_SUSPENDED,
        SUSPENDED,
        INVALIDATED
    }

    public static SuspendStatus create() {
        SuspendStatus status = (SuspendStatus) ThreadCache.takeFromCache(CACHE_IDX);
        if (status == null) {
            status = new SuspendStatus();
        }
        if (status.initThread == Thread.currentThread()) {
            status.state = State.NOT_SUSPENDED;
            return status;
        }
        throw new AssertionError();
    }

    private SuspendStatus() {
    }

    public void suspend() {
        if (Thread.currentThread() == this.initThread) {
            State state2 = this.state;
            State state3 = State.NOT_SUSPENDED;
            if (state2 == state3) {
                this.state = State.SUSPENDED;
                return;
            }
            throw new IllegalStateException("Can not suspend. Expected suspend state='" + state3 + "' but was '" + this.state + "'");
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public boolean getAndInvalidate() {
        Thread currentThread = Thread.currentThread();
        Thread thread = this.initThread;
        if (currentThread == thread) {
            boolean wasSuspended = this.state == State.SUSPENDED;
            this.state = State.INVALIDATED;
            ThreadCache.putToCache(thread, CACHE_IDX, this);
            return wasSuspended;
        }
        throw new AssertionError();
    }

    public void reset() {
        if (Thread.currentThread() == this.initThread) {
            this.state = State.NOT_SUSPENDED;
            return;
        }
        throw new AssertionError();
    }
}
