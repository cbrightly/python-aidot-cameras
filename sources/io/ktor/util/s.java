package io.ktor.util;

import java.util.concurrent.locks.ReentrantLock;

/* compiled from: LockJvm.kt */
public final class s {
    private final ReentrantLock a = new ReentrantLock();

    public final void a() {
        this.a.lock();
    }

    public final void b() {
        this.a.unlock();
    }
}
