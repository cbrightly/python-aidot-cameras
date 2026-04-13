package com.leedarson.smartcamera.kvswebrtc.utils;

import com.leedarson.base.utils.r;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: IpcThreadUtil */
public class d {
    private static d a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private ThreadFactory b;
    private ExecutorService c;
    private ExecutorService d;
    private ExecutorService e;
    private ExecutorService f;

    public static d a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10107, new Class[0], d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        if (a == null) {
            synchronized (d.class) {
                if (a == null) {
                    a = new d();
                }
            }
        }
        return a;
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10108, new Class[0], Void.TYPE).isSupported) {
            if (this.b == null) {
                this.b = new r("webrtc-util");
            }
        }
    }

    public ExecutorService c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10109, new Class[0], ExecutorService.class);
        if (proxy.isSupported) {
            return (ExecutorService) proxy.result;
        }
        e();
        if (this.c == null) {
            this.c = Executors.newSingleThreadExecutor(this.b);
        }
        return this.c;
    }

    public ExecutorService b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10111, new Class[0], ExecutorService.class);
        if (proxy.isSupported) {
            return (ExecutorService) proxy.result;
        }
        e();
        if (this.f == null) {
            this.f = Executors.newSingleThreadExecutor(this.b);
        }
        return this.f;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10112(0x2780, float:1.417E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.concurrent.ExecutorService r1 = r0.f
            if (r1 == 0) goto L_0x0029
            boolean r1 = r1.isShutdown()
            if (r1 != 0) goto L_0x0029
            java.util.concurrent.ExecutorService r1 = r0.f
            r1.shutdown()
            r1 = 0
            r0.f = r1
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.utils.d.f():void");
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10113, new Class[0], Void.TYPE).isSupported) {
            ExecutorService executorService = this.e;
            if (executorService != null && !executorService.isShutdown()) {
                this.e.shutdown();
                this.e = null;
            }
            ExecutorService executorService2 = this.c;
            if (executorService2 != null && !executorService2.isShutdown()) {
                this.c.shutdown();
                this.c = null;
            }
        }
    }

    public ExecutorService d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10114, new Class[0], ExecutorService.class);
        if (proxy.isSupported) {
            return (ExecutorService) proxy.result;
        }
        e();
        if (this.d == null) {
            this.d = Executors.newFixedThreadPool(2, this.b);
        }
        return this.d;
    }
}
