package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;

public class FastThreadLocalThread extends Thread {
    private final boolean cleanupFastThreadLocals = false;
    private InternalThreadLocalMap threadLocalMap;

    public FastThreadLocalThread() {
    }

    public FastThreadLocalThread(Runnable target) {
        super(FastThreadLocalRunnable.wrap(target));
    }

    public FastThreadLocalThread(ThreadGroup group, Runnable target) {
        super(group, FastThreadLocalRunnable.wrap(target));
    }

    public FastThreadLocalThread(String name) {
        super(name);
    }

    public FastThreadLocalThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public FastThreadLocalThread(Runnable target, String name) {
        super(FastThreadLocalRunnable.wrap(target), name);
    }

    public FastThreadLocalThread(ThreadGroup group, Runnable target, String name) {
        super(group, FastThreadLocalRunnable.wrap(target), name);
    }

    public FastThreadLocalThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, FastThreadLocalRunnable.wrap(target), name, stackSize);
    }

    public final InternalThreadLocalMap threadLocalMap() {
        return this.threadLocalMap;
    }

    public final void setThreadLocalMap(InternalThreadLocalMap threadLocalMap2) {
        this.threadLocalMap = threadLocalMap2;
    }

    public boolean willCleanupFastThreadLocals() {
        return this.cleanupFastThreadLocals;
    }

    public static boolean willCleanupFastThreadLocals(Thread thread) {
        return (thread instanceof FastThreadLocalThread) && ((FastThreadLocalThread) thread).willCleanupFastThreadLocals();
    }
}
