package com.leedarson.base.utils;

import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: ThreadPoolUtils */
public class u {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ExecutorService a;
    private ExecutorService b;

    /* compiled from: ThreadPoolUtils */
    public enum b {
        FixedThread,
        CachedThread,
        SingleThread;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public u(b type, int corePoolSize, String name) {
        this.b = l.i(corePoolSize, name, corePoolSize + 1);
        switch (a.a[type.ordinal()]) {
            case 1:
                this.a = l.i(corePoolSize, name, corePoolSize + 1);
                return;
            case 2:
                this.a = Executors.newSingleThreadExecutor();
                return;
            case 3:
                this.a = Executors.newCachedThreadPool();
                return;
            default:
                this.a = this.b;
                return;
        }
    }

    /* compiled from: ThreadPoolUtils */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.FixedThread.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.SingleThread.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.CachedThread.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void a(Runnable command) {
        if (!PatchProxy.proxy(new Object[]{command}, this, changeQuickRedirect, false, 516, new Class[]{Runnable.class}, Void.TYPE).isSupported) {
            this.a.execute(command);
        }
    }

    public List<Runnable> c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 519, new Class[0], List.class);
        return proxy.isSupported ? (List) proxy.result : this.a.shutdownNow();
    }

    public boolean b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 520, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.a.isShutdown();
    }
}
