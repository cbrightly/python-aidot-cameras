package com.telink.ble.mesh.foundation;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.util.MeshLogger;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class EventBus<T> {
    private static final int a;
    private static final int b;
    private static final int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final BlockingQueue<Runnable> d;
    private static final ThreadFactory e;
    private static final ExecutorService f;
    protected final Map<T, List<EventListener<T>>> g = new ConcurrentHashMap();
    protected final Queue<Event<T>> h = new ConcurrentLinkedQueue();
    protected final Handler i;
    protected final Handler j = new Handler(Looper.getMainLooper());
    private final Object k = new Object();
    protected boolean l = false;
    private final Runnable m = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13080, new Class[0], Void.TYPE).isSupported) {
                EventBus.a(EventBus.this);
            }
        }
    };

    static /* synthetic */ void a(EventBus x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13079, new Class[]{EventBus.class}, Void.TYPE).isSupported) {
            x0.d();
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
        DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory();
        e = defaultThreadFactory;
        f = new ThreadPoolExecutor(i2, i3, 1, TimeUnit.SECONDS, linkedBlockingQueue, defaultThreadFactory);
    }

    public EventBus(Looper looper) {
        this.i = new Handler(looper);
    }

    public void b(T eventType, EventListener<T> listener) {
        List<EventListener<T>> listeners;
        Class[] clsArr = {Object.class, EventListener.class};
        if (!PatchProxy.proxy(new Object[]{eventType, listener}, this, changeQuickRedirect, false, 13071, clsArr, Void.TYPE).isSupported) {
            synchronized (this.g) {
                if (this.g.containsKey(eventType)) {
                    listeners = this.g.get(eventType);
                } else {
                    listeners = new CopyOnWriteArrayList<>();
                    this.g.put(eventType, listeners);
                }
                if (!listeners.contains(listener)) {
                    listeners.add(listener);
                }
            }
        }
    }

    public void g(EventListener<T> listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 13072, new Class[]{EventListener.class}, Void.TYPE).isSupported) {
            synchronized (this.g) {
                for (T eventType : this.g.keySet()) {
                    h(eventType, listener);
                }
            }
        }
    }

    public void h(T eventType, EventListener<T> listener) {
        Class[] clsArr = {Object.class, EventListener.class};
        if (!PatchProxy.proxy(new Object[]{eventType, listener}, this, changeQuickRedirect, false, 13073, clsArr, Void.TYPE).isSupported) {
            synchronized (this.g) {
                if (this.g.containsKey(eventType)) {
                    this.g.get(eventType).remove(listener);
                }
            }
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13074, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.g) {
                for (T eventType : this.g.keySet()) {
                    this.g.get(eventType).clear();
                    this.g.remove(eventType);
                }
            }
        }
    }

    public void c(Event<T> event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13075, new Class[]{Event.class}, Void.TYPE).isSupported) {
            this.h.add(event);
            MeshLogger.h("post event : " + event.getType() + "--" + event.getClass().getSimpleName());
            synchronized (this.k) {
                if (!this.l) {
                    f();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0033, code lost:
        switch(com.telink.ble.mesh.foundation.EventBus.AnonymousClass2.a[r2.getThreadMode().ordinal()]) {
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
    private void f() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13076(0x3314, float:1.8323E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Queue<com.telink.ble.mesh.foundation.Event<T>> r1 = r0.h
            monitor-enter(r1)
            java.util.Queue<com.telink.ble.mesh.foundation.Event<T>> r2 = r0.h     // Catch:{ all -> 0x0050 }
            java.lang.Object r2 = r2.peek()     // Catch:{ all -> 0x0050 }
            com.telink.ble.mesh.foundation.Event r2 = (com.telink.ble.mesh.foundation.Event) r2     // Catch:{ all -> 0x0050 }
            if (r2 != 0) goto L_0x0026
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            return
        L_0x0026:
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            int[] r1 = com.telink.ble.mesh.foundation.EventBus.AnonymousClass2.a
            com.telink.ble.mesh.foundation.Event$ThreadMode r3 = r2.getThreadMode()
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
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.EventBus.f():void");
    }

    /* renamed from: com.telink.ble.mesh.foundation.EventBus$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Event.ThreadMode.values().length];
            a = iArr;
            try {
                iArr[Event.ThreadMode.Background.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Event.ThreadMode.Main.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Event.ThreadMode.Default.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        r3 = r2.getType();
        r1 = null;
        r4 = r0.g;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r0.g.containsKey(r3) == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        r1 = r0.g.get(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        if (r1 == null) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        if (r1.isEmpty() != false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        r4 = r0.k;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004b, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r0.l = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004f, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0050, code lost:
        r4 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0058, code lost:
        if (r4.hasNext() == false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005a, code lost:
        r5 = r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
        if (r5 == null) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0062, code lost:
        r5.performed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0069, code lost:
        e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13077(0x3315, float:1.8325E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Queue<com.telink.ble.mesh.foundation.Event<T>> r1 = r0.h
            monitor-enter(r1)
            java.util.Queue<com.telink.ble.mesh.foundation.Event<T>> r2 = r0.h     // Catch:{ all -> 0x0070 }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0070 }
            com.telink.ble.mesh.foundation.Event r2 = (com.telink.ble.mesh.foundation.Event) r2     // Catch:{ all -> 0x0070 }
            if (r2 != 0) goto L_0x0026
            monitor-exit(r1)     // Catch:{ all -> 0x0070 }
            return
        L_0x0026:
            monitor-exit(r1)     // Catch:{ all -> 0x0070 }
            java.lang.Object r3 = r2.getType()
            r1 = 0
            java.util.Map<T, java.util.List<com.telink.ble.mesh.foundation.EventListener<T>>> r4 = r0.g
            monitor-enter(r4)
            java.util.Map<T, java.util.List<com.telink.ble.mesh.foundation.EventListener<T>>> r5 = r0.g     // Catch:{ all -> 0x006d }
            boolean r5 = r5.containsKey(r3)     // Catch:{ all -> 0x006d }
            if (r5 == 0) goto L_0x0040
            java.util.Map<T, java.util.List<com.telink.ble.mesh.foundation.EventListener<T>>> r5 = r0.g     // Catch:{ all -> 0x006d }
            java.lang.Object r5 = r5.get(r3)     // Catch:{ all -> 0x006d }
            java.util.List r5 = (java.util.List) r5     // Catch:{ all -> 0x006d }
            r1 = r5
        L_0x0040:
            monitor-exit(r4)     // Catch:{ all -> 0x006d }
            if (r1 == 0) goto L_0x0069
            boolean r4 = r1.isEmpty()
            if (r4 != 0) goto L_0x0069
            java.lang.Object r4 = r0.k
            monitor-enter(r4)
            r5 = 1
            r0.l = r5     // Catch:{ all -> 0x0066 }
            monitor-exit(r4)     // Catch:{ all -> 0x0066 }
            java.util.Iterator r4 = r1.iterator()
        L_0x0054:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0069
            java.lang.Object r5 = r4.next()
            com.telink.ble.mesh.foundation.EventListener r5 = (com.telink.ble.mesh.foundation.EventListener) r5
            if (r5 == 0) goto L_0x0065
            r5.performed(r2)
        L_0x0065:
            goto L_0x0054
        L_0x0066:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0066 }
            throw r5
        L_0x0069:
            r0.e()
            return
        L_0x006d:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x006d }
            throw r5
        L_0x0070:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0070 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.EventBus.d():void");
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13078, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.k) {
                this.l = false;
            }
            if (!this.h.isEmpty()) {
                f();
            }
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger c = new AtomicInteger(1);
        public static ChangeQuickRedirect changeQuickRedirect;
        private final AtomicInteger d = new AtomicInteger(1);
        private final ThreadGroup f;
        private final String q;

        DefaultThreadFactory() {
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

        public Thread newThread(final Runnable r) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{r}, this, changeQuickRedirect, false, 13081, new Class[]{Runnable.class}, Thread.class);
            if (proxy.isSupported) {
                return (Thread) proxy.result;
            }
            Runnable run = new Runnable() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13082, new Class[0], Void.TYPE).isSupported) {
                        Process.setThreadPriority(10);
                        r.run();
                    }
                }
            };
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
