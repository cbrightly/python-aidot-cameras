package com.alibaba.android.arouter.thread;

import com.alibaba.android.arouter.utils.e;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: DefaultPoolExecutor */
public class b extends ThreadPoolExecutor {
    private static final int c;
    private static final int d;
    private static final int f;
    private static volatile b q;

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        c = availableProcessors;
        int i = availableProcessors + 1;
        d = i;
        f = i;
    }

    public static b a() {
        if (q == null) {
            synchronized (b.class) {
                if (q == null) {
                    q = new b(d, f, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(64), new c());
                }
            }
        }
        return q;
    }

    /* compiled from: DefaultPoolExecutor */
    public class a implements RejectedExecutionHandler {
        a() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            com.alibaba.android.arouter.launcher.a.c.d("ARouter::", "Task rejected, too many task!");
        }
    }

    private b(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new a());
    }

    /* access modifiers changed from: protected */
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && (r instanceof Future)) {
            try {
                ((Future) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            com.alibaba.android.arouter.facade.template.b bVar = com.alibaba.android.arouter.launcher.a.c;
            bVar.e("ARouter::", "Running task appeared exception! Thread [" + Thread.currentThread().getName() + "], because [" + t.getMessage() + "]\n" + e.a(t.getStackTrace()));
        }
    }
}
