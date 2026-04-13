package com.google.firebase.concurrent;

import android.os.Process;
import android.os.StrictMode;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public class CustomThreadFactory implements ThreadFactory {
    private static final ThreadFactory DEFAULT = Executors.defaultThreadFactory();
    private final String namePrefix;
    private final StrictMode.ThreadPolicy policy;
    private final int priority;
    private final AtomicLong threadCount = new AtomicLong();

    CustomThreadFactory(String namePrefix2, int priority2, @Nullable StrictMode.ThreadPolicy policy2) {
        this.namePrefix = namePrefix2;
        this.priority = priority2;
        this.policy = policy2;
    }

    public Thread newThread(Runnable r) {
        Thread thread = DEFAULT.newThread(new a(this, r));
        thread.setName(String.format(Locale.ROOT, "%s Thread #%d", new Object[]{this.namePrefix, Long.valueOf(this.threadCount.getAndIncrement())}));
        return thread;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$newThread$0 */
    public /* synthetic */ void a(Runnable r) {
        Process.setThreadPriority(this.priority);
        StrictMode.ThreadPolicy threadPolicy = this.policy;
        if (threadPolicy != null) {
            StrictMode.setThreadPolicy(threadPolicy);
        }
        r.run();
    }
}
