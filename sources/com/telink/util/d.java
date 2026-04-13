package com.telink.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import androidx.annotation.NonNull;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.util.c;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: EventBus */
public class d<T> {
    private static final int a;
    private static final int b;
    private static final int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final BlockingQueue<Runnable> d;
    private static final ThreadFactory e;
    private static final ExecutorService f;
    protected final Map<T, List<e<T>>> g = new ConcurrentHashMap();
    protected final Queue<c<T>> h = new ConcurrentLinkedQueue();
    protected final Handler i = new Handler(Looper.myLooper());
    protected final Handler j = new Handler(Looper.getMainLooper());
    private final Object k = new Object();
    protected boolean l = false;
    private final Runnable m = new a();

    static /* synthetic */ void f(d x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13877, new Class[]{d.class}, Void.TYPE).isSupported) {
            x0.i();
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        a = availableProcessors;
        int i2 = availableProcessors + 1;
        b = i2;
        int i3 = (availableProcessors * 2) + 1;
        c = i3;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(128);
        d = linkedBlockingQueue;
        c cVar = new c();
        e = cVar;
        f = new ThreadPoolExecutor(i2, i3, 1, TimeUnit.SECONDS, linkedBlockingQueue, cVar);
    }

    /* compiled from: EventBus */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13878, new Class[0], Void.TYPE).isSupported) {
                d.f(d.this);
            }
        }
    }

    public void g(T eventType, e<T> listener) {
        List<EventListener<T>> listeners;
        Class[] clsArr = {Object.class, e.class};
        if (!PatchProxy.proxy(new Object[]{eventType, listener}, this, changeQuickRedirect, false, 13869, clsArr, Void.TYPE).isSupported) {
            synchronized (this.g) {
                if (this.g.containsKey(eventType)) {
                    listeners = this.g.get(eventType);
                } else {
                    listeners = new ArrayList<>();
                    this.g.put(eventType, listeners);
                }
                if (!listeners.contains(listener)) {
                    listeners.add(listener);
                }
            }
        }
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13872, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.g) {
                for (T eventType : this.g.keySet()) {
                    this.g.get(eventType).clear();
                    this.g.remove(eventType);
                }
            }
        }
    }

    public void h(c<T> event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13873, new Class[]{c.class}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("event looper : " + event.a());
            this.h.add(event);
            com.telink.bluetooth.d.a("postCommand event : " + event.b() + "--" + event.getClass().getName());
            synchronized (this.k) {
                if (!this.l) {
                    k();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0033, code lost:
        switch(com.telink.util.d.b.a[r2.a().ordinal()]) {
            case 1: goto L_0x0047;
            case 2: goto L_0x003f;
            case 3: goto L_0x0037;
            default: goto L_0x0036;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0037, code lost:
        r0.i.post(r0.m);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        r0.j.post(r0.m);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0047, code lost:
        f.execute(r0.m);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13874(0x3632, float:1.9442E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Queue<com.telink.util.c<T>> r1 = r0.h
            monitor-enter(r1)
            java.util.Queue<com.telink.util.c<T>> r2 = r0.h     // Catch:{ all -> 0x0050 }
            java.lang.Object r2 = r2.peek()     // Catch:{ all -> 0x0050 }
            com.telink.util.c r2 = (com.telink.util.c) r2     // Catch:{ all -> 0x0050 }
            if (r2 != 0) goto L_0x0026
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            return
        L_0x0026:
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            int[] r1 = com.telink.util.d.b.a
            com.telink.util.c$a r3 = r2.a()
            int r3 = r3.ordinal()
            r1 = r1[r3]
            switch(r1) {
                case 1: goto L_0x0047;
                case 2: goto L_0x003f;
                case 3: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x004f
        L_0x0037:
            android.os.Handler r1 = r0.i
            java.lang.Runnable r3 = r0.m
            r1.post(r3)
            goto L_0x004f
        L_0x003f:
            android.os.Handler r1 = r0.j
            java.lang.Runnable r3 = r0.m
            r1.post(r3)
            goto L_0x004f
        L_0x0047:
            java.util.concurrent.ExecutorService r1 = f
            java.lang.Runnable r3 = r0.m
            r1.execute(r3)
        L_0x004f:
            return
        L_0x0050:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.util.d.k():void");
    }

    /* compiled from: EventBus */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.a.values().length];
            a = iArr;
            try {
                iArr[c.a.Background.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[c.a.Main.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[c.a.Default.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        com.telink.bluetooth.d.a("process postCommand event : " + r2.b() + "--" + r2.getClass().getName());
        r3 = r2.b();
        r1 = null;
        r4 = r0.g;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0057, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005e, code lost:
        if (r0.g.containsKey(r3) == false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0060, code lost:
        r1 = r0.g.get(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0069, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006a, code lost:
        if (r1 == null) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0070, code lost:
        if (r1.isEmpty() != false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0072, code lost:
        r4 = r0.k;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r0.l = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0078, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0079, code lost:
        r4 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0081, code lost:
        if (r4.hasNext() == false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0083, code lost:
        r5 = (com.telink.util.e) r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0089, code lost:
        if (r5 == null) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008b, code lost:
        r5.a(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0092, code lost:
        j();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0095, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13875(0x3633, float:1.9443E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Queue<com.telink.util.c<T>> r1 = r0.h
            monitor-enter(r1)
            java.util.Queue<com.telink.util.c<T>> r2 = r0.h     // Catch:{ all -> 0x0099 }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0099 }
            com.telink.util.c r2 = (com.telink.util.c) r2     // Catch:{ all -> 0x0099 }
            if (r2 != 0) goto L_0x0026
            monitor-exit(r1)     // Catch:{ all -> 0x0099 }
            return
        L_0x0026:
            monitor-exit(r1)     // Catch:{ all -> 0x0099 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "process postCommand event : "
            r1.append(r3)
            java.lang.Object r3 = r2.b()
            r1.append(r3)
            java.lang.String r3 = "--"
            r1.append(r3)
            java.lang.Class r3 = r2.getClass()
            java.lang.String r3 = r3.getName()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.telink.bluetooth.d.a(r1)
            java.lang.Object r3 = r2.b()
            r1 = 0
            java.util.Map<T, java.util.List<com.telink.util.e<T>>> r4 = r0.g
            monitor-enter(r4)
            java.util.Map<T, java.util.List<com.telink.util.e<T>>> r5 = r0.g     // Catch:{ all -> 0x0096 }
            boolean r5 = r5.containsKey(r3)     // Catch:{ all -> 0x0096 }
            if (r5 == 0) goto L_0x0069
            java.util.Map<T, java.util.List<com.telink.util.e<T>>> r5 = r0.g     // Catch:{ all -> 0x0096 }
            java.lang.Object r5 = r5.get(r3)     // Catch:{ all -> 0x0096 }
            java.util.List r5 = (java.util.List) r5     // Catch:{ all -> 0x0096 }
            r1 = r5
        L_0x0069:
            monitor-exit(r4)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x0092
            boolean r4 = r1.isEmpty()
            if (r4 != 0) goto L_0x0092
            java.lang.Object r4 = r0.k
            monitor-enter(r4)
            r5 = 1
            r0.l = r5     // Catch:{ all -> 0x008f }
            monitor-exit(r4)     // Catch:{ all -> 0x008f }
            java.util.Iterator r4 = r1.iterator()
        L_0x007d:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0092
            java.lang.Object r5 = r4.next()
            com.telink.util.e r5 = (com.telink.util.e) r5
            if (r5 == 0) goto L_0x008e
            r5.a(r2)
        L_0x008e:
            goto L_0x007d
        L_0x008f:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x008f }
            throw r5
        L_0x0092:
            r0.j()
            return
        L_0x0096:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0096 }
            throw r5
        L_0x0099:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0099 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.util.d.i():void");
    }

    private void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13876, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.k) {
                this.l = false;
            }
            if (!this.h.isEmpty()) {
                k();
            }
        }
    }

    /* compiled from: EventBus */
    public static class c implements ThreadFactory {
        private static final AtomicInteger c = new AtomicInteger(1);
        public static ChangeQuickRedirect changeQuickRedirect;
        private final AtomicInteger d = new AtomicInteger(1);
        private final ThreadGroup f;
        private final String q;

        c() {
            ThreadGroup threadGroup;
            SecurityManager s = System.getSecurityManager();
            if (s != null) {
                threadGroup = s.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.f = threadGroup;
            this.q = "pool-" + c.getAndIncrement() + "-thread-";
        }

        /* compiled from: EventBus */
        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ Runnable c;

            a(Runnable runnable) {
                this.c = runnable;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13880, new Class[0], Void.TYPE).isSupported) {
                    Process.setThreadPriority(10);
                    this.c.run();
                }
            }
        }

        public Thread newThread(@NonNull Runnable r) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{r}, this, changeQuickRedirect, false, 13879, new Class[]{Runnable.class}, Thread.class);
            if (proxy.isSupported) {
                return (Thread) proxy.result;
            }
            Runnable run = new a(r);
            ThreadGroup threadGroup = this.f;
            Thread thread = new Thread(threadGroup, run, this.q + this.d.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }
}
