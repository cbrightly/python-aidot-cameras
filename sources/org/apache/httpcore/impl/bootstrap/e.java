package org.apache.httpcore.impl.bootstrap;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: ThreadFactoryImpl */
public class e implements ThreadFactory {
    private final String c;
    private final ThreadGroup d;
    private final AtomicLong f;

    e(String namePrefix, ThreadGroup group) {
        this.c = namePrefix;
        this.d = group;
        this.f = new AtomicLong();
    }

    e(String namePrefix) {
        this(namePrefix, (ThreadGroup) null);
    }

    public Thread newThread(Runnable target) {
        ThreadGroup threadGroup = this.d;
        return new Thread(threadGroup, target, this.c + "-" + this.f.incrementAndGet());
    }
}
