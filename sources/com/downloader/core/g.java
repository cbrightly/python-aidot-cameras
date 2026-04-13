package com.downloader.core;

import android.os.Process;
import java.util.concurrent.ThreadFactory;

/* compiled from: PriorityThreadFactory */
public class g implements ThreadFactory {
    /* access modifiers changed from: private */
    public final int c;

    g(int threadPriority) {
        this.c = threadPriority;
    }

    /* compiled from: PriorityThreadFactory */
    public class a implements Runnable {
        final /* synthetic */ Runnable c;

        a(Runnable runnable) {
            this.c = runnable;
        }

        public void run() {
            try {
                Process.setThreadPriority(g.this.c);
            } catch (Throwable th) {
            }
            this.c.run();
        }
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(new a(runnable));
    }
}
