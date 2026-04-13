package io.reactivex.internal.schedulers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: SchedulerPoolFactory */
public final class m {
    public static final boolean a;
    public static final int b;
    static final AtomicReference<ScheduledExecutorService> c = new AtomicReference<>();
    static final Map<ScheduledThreadPoolExecutor, Object> d = new ConcurrentHashMap();

    static {
        Properties properties = System.getProperties();
        a pp = new a();
        pp.a(properties);
        a = pp.a;
        b = pp.b;
        b();
    }

    public static void b() {
        d(a);
    }

    static void d(boolean purgeEnabled) {
        if (purgeEnabled) {
            while (true) {
                AtomicReference<ScheduledExecutorService> atomicReference = c;
                ScheduledExecutorService curr = atomicReference.get();
                if (curr == null) {
                    ScheduledExecutorService next = Executors.newScheduledThreadPool(1, new i("RxSchedulerPurge"));
                    if (atomicReference.compareAndSet(curr, next)) {
                        b bVar = new b();
                        int i = b;
                        next.scheduleAtFixedRate(bVar, (long) i, (long) i, TimeUnit.SECONDS);
                        return;
                    }
                    next.shutdownNow();
                } else {
                    return;
                }
            }
        }
    }

    /* compiled from: SchedulerPoolFactory */
    public static final class a {
        boolean a;
        int b;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void a(Properties properties) {
            if (properties.containsKey("rx2.purge-enabled")) {
                this.a = Boolean.parseBoolean(properties.getProperty("rx2.purge-enabled"));
            } else {
                this.a = true;
            }
            if (!this.a || !properties.containsKey("rx2.purge-period-seconds")) {
                this.b = 1;
                return;
            }
            try {
                this.b = Integer.parseInt(properties.getProperty("rx2.purge-period-seconds"));
            } catch (NumberFormatException e) {
                this.b = 1;
            }
        }
    }

    public static ScheduledExecutorService a(ThreadFactory factory) {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1, factory);
        c(a, exec);
        return exec;
    }

    static void c(boolean purgeEnabled, ScheduledExecutorService exec) {
        if (purgeEnabled && (exec instanceof ScheduledThreadPoolExecutor)) {
            d.put((ScheduledThreadPoolExecutor) exec, exec);
        }
    }

    /* compiled from: SchedulerPoolFactory */
    public static final class b implements Runnable {
        b() {
        }

        public void run() {
            Iterator it = new ArrayList(m.d.keySet()).iterator();
            while (it.hasNext()) {
                ScheduledThreadPoolExecutor e = (ScheduledThreadPoolExecutor) it.next();
                if (e.isShutdown()) {
                    m.d.remove(e);
                } else {
                    e.purge();
                }
            }
        }
    }
}
