package com.alibaba.android.arouter.thread;

import androidx.annotation.NonNull;
import com.alibaba.android.arouter.facade.template.b;
import java.lang.Thread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: DefaultThreadFactory */
public class c implements ThreadFactory {
    private static final AtomicInteger c = new AtomicInteger(1);
    private final AtomicInteger d = new AtomicInteger(1);
    private final ThreadGroup f;
    private final String q;

    public c() {
        ThreadGroup threadGroup;
        SecurityManager s = System.getSecurityManager();
        if (s != null) {
            threadGroup = s.getThreadGroup();
        } else {
            threadGroup = Thread.currentThread().getThreadGroup();
        }
        this.f = threadGroup;
        this.q = "ARouter task pool No." + c.getAndIncrement() + ", thread No.";
    }

    public Thread newThread(@NonNull Runnable runnable) {
        String threadName = this.q + this.d.getAndIncrement();
        com.alibaba.android.arouter.launcher.a.c.c("ARouter::", "Thread production, name is [" + threadName + "]");
        Thread thread = new Thread(this.f, runnable, threadName, 0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        thread.setUncaughtExceptionHandler(new a());
        return thread;
    }

    /* compiled from: DefaultThreadFactory */
    public class a implements Thread.UncaughtExceptionHandler {
        a() {
        }

        public void uncaughtException(Thread thread, Throwable ex) {
            b bVar = com.alibaba.android.arouter.launcher.a.c;
            bVar.c("ARouter::", "Running task appeared exception! Thread [" + thread.getName() + "], because [" + ex.getMessage() + "]");
        }
    }
}
