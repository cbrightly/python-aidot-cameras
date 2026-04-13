package com.leedarson.base.http.observer;

import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.i;
import io.reactivex.o;
import io.reactivex.p;
import io.reactivex.r;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: LDSPublishCompose */
public class l {
    public static r a = io.reactivex.schedulers.a.b(i(2, "ioSchedule", 10));
    public static r b = io.reactivex.schedulers.a.b(i(2, "httpRecord", 4));
    public static r c = io.reactivex.schedulers.a.b(i(1, "ldsUnzipWeb", 1));
    public static ChangeQuickRedirect changeQuickRedirect;
    public static r d = io.reactivex.schedulers.a.b(i(1, "ldsBleConnect", 2));
    public static r e = io.reactivex.schedulers.a.b(i(1, "ldsCameraSchedule", 2));
    public static r f;
    public static r g;
    public static r h = io.reactivex.schedulers.a.b(i(1, "ldsCoreHttpImageDownload", 1));
    public static r i = io.reactivex.schedulers.a.b(i(1, "native-mqtt-watchdog-service", 1));
    public static r j = io.reactivex.schedulers.a.b(i(2, "ldsTcpSchedule", 4));
    public static r k = io.reactivex.schedulers.a.b(i(1, "ldsLightRhythmSchedule", 2));
    public static ExecutorService l = i(1, "tempExcurotService", 1);
    public static ExecutorService m = i(1, "ldsMeshUpdateOrSaveSchedule", 10);
    public static ExecutorService n = i(1, "ldsMeshGattConnection", 1);

    static {
        r b2 = io.reactivex.schedulers.a.b(j());
        f = b2;
        g = b2;
    }

    public static <T> i<T, T> c() {
        return c.a;
    }

    static /* synthetic */ org.reactivestreams.a f(e upstream) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{upstream}, (Object) null, changeQuickRedirect, true, 207, new Class[]{e.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        return upstream.M(a).A(io.reactivex.android.schedulers.a.a());
    }

    public static <T> i<T, T> b() {
        return g.a;
    }

    static /* synthetic */ org.reactivestreams.a e(e upstream) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{upstream}, (Object) null, changeQuickRedirect, true, 206, new Class[]{e.class}, org.reactivestreams.a.class);
        return proxy.isSupported ? (org.reactivestreams.a) proxy.result : upstream.M(a);
    }

    public static <T> i<T, T> a() {
        return d.a;
    }

    static /* synthetic */ org.reactivestreams.a d(e upstream) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{upstream}, (Object) null, changeQuickRedirect, true, 205, new Class[]{e.class}, org.reactivestreams.a.class);
        return proxy.isSupported ? (org.reactivestreams.a) proxy.result : upstream.M(b);
    }

    static /* synthetic */ o h(io.reactivex.l upstream) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{upstream}, (Object) null, changeQuickRedirect, true, KitWrapItem.TYPE_VERSION, new Class[]{io.reactivex.l.class}, o.class);
        if (proxy.isSupported) {
            return (o) proxy.result;
        }
        return upstream.b0(a).J(io.reactivex.android.schedulers.a.a());
    }

    public static <T> p<T, T> k() {
        return f.a;
    }

    public static ExecutorService i(int corePoolSize, String bzName, int maxPoolSize) {
        Object[] objArr = {new Integer(corePoolSize), bzName, new Integer(maxPoolSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 199, new Class[]{cls, String.class, cls}, ExecutorService.class);
        return proxy.isSupported ? (ExecutorService) proxy.result : new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new a(bzName));
    }

    public static ExecutorService j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 200, new Class[0], ExecutorService.class);
        return proxy.isSupported ? (ExecutorService) proxy.result : new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), e.c);
    }

    static /* synthetic */ Thread g(Runnable r) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{r}, (Object) null, changeQuickRedirect, true, 201, new Class[]{Runnable.class}, Thread.class);
        if (proxy.isSupported) {
            return (Thread) proxy.result;
        }
        Thread t = new Thread(r, "corehttp");
        t.setDaemon(false);
        return t;
    }

    /* compiled from: LDSPublishCompose */
    public static class a implements ThreadFactory {
        private static final AtomicInteger c = new AtomicInteger(1);
        public static ChangeQuickRedirect changeQuickRedirect;
        private final ThreadGroup d;
        private final AtomicInteger f = new AtomicInteger(1);
        private final String q;

        public a(String bzName) {
            ThreadGroup threadGroup;
            SecurityManager s = System.getSecurityManager();
            if (s != null) {
                threadGroup = s.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.d = threadGroup;
            this.q = bzName + "-lds-thread-" + c.getAndIncrement();
        }

        public Thread newThread(Runnable r) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{r}, this, changeQuickRedirect, false, 208, new Class[]{Runnable.class}, Thread.class);
            if (proxy.isSupported) {
                return (Thread) proxy.result;
            }
            ThreadGroup threadGroup = this.d;
            Thread thread = new Thread(threadGroup, r, this.q + this.f.getAndIncrement(), 0);
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
