package com.leedarson.newui.view.ldsnakebar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.ref.WeakReference;

/* compiled from: SnackbarManager */
public class b {
    private static b a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private final Object b = new Object();
    private final Handler c = new Handler(Looper.getMainLooper(), new a());
    private c d;
    private c e;

    /* renamed from: com.leedarson.newui.view.ldsnakebar.b$b  reason: collision with other inner class name */
    /* compiled from: SnackbarManager */
    public interface C0120b {
        void dismiss(int i);

        void show();
    }

    static b c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 5358, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        if (a == null) {
            a = new b();
        }
        return a;
    }

    private b() {
    }

    /* compiled from: SnackbarManager */
    public class a implements Handler.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean handleMessage(Message message) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 5373, new Class[]{Message.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (message.what) {
                case 0:
                    b.this.d((c) message.obj);
                    return true;
                default:
                    return false;
            }
        }
    }

    public void n(int duration, C0120b callback) {
        Object[] objArr = {new Integer(duration), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5359, new Class[]{Integer.TYPE, C0120b.class}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (g(callback)) {
                    c cVar = this.d;
                    cVar.b = duration;
                    this.c.removeCallbacksAndMessages(cVar);
                    m(this.d);
                    return;
                }
                if (h(callback)) {
                    this.e.b = duration;
                } else {
                    this.e = new c(duration, callback);
                }
                c cVar2 = this.d;
                if (cVar2 == null || !a(cVar2, 4)) {
                    this.d = null;
                    o();
                }
            }
        }
    }

    public void b(C0120b callback, int event) {
        if (!PatchProxy.proxy(new Object[]{callback, new Integer(event)}, this, changeQuickRedirect, false, 5360, new Class[]{C0120b.class, Integer.TYPE}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (g(callback)) {
                    a(this.d, event);
                } else if (h(callback)) {
                    a(this.e, event);
                }
            }
        }
    }

    public void i(C0120b callback) {
        if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5361, new Class[]{C0120b.class}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (g(callback)) {
                    this.d = null;
                    if (this.e != null) {
                        o();
                    }
                }
            }
        }
    }

    public void j(C0120b callback) {
        if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5362, new Class[]{C0120b.class}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (g(callback)) {
                    m(this.d);
                }
            }
        }
    }

    public void k(C0120b callback) {
        if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5363, new Class[]{C0120b.class}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (g(callback)) {
                    c cVar = this.d;
                    if (!cVar.c) {
                        cVar.c = true;
                        this.c.removeCallbacksAndMessages(cVar);
                    }
                }
            }
        }
    }

    public void l(C0120b callback) {
        if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5364, new Class[]{C0120b.class}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (g(callback)) {
                    c cVar = this.d;
                    if (cVar.c) {
                        cVar.c = false;
                        m(cVar);
                    }
                }
            }
        }
    }

    public boolean e(C0120b callback) {
        boolean g;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5365, new Class[]{C0120b.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        synchronized (this.b) {
            g = g(callback);
        }
        return g;
    }

    public boolean f(C0120b callback) {
        boolean z = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5366, new Class[]{C0120b.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        synchronized (this.b) {
            if (!g(callback)) {
                if (!h(callback)) {
                    z = false;
                }
            }
        }
        return z;
    }

    /* compiled from: SnackbarManager */
    public static class c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final WeakReference<C0120b> a;
        int b;
        boolean c;

        c(int duration, C0120b callback) {
            this.a = new WeakReference<>(callback);
            this.b = duration;
        }

        /* access modifiers changed from: package-private */
        public boolean a(C0120b callback) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5374, new Class[]{C0120b.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            return callback != null && this.a.get() == callback;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void o() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5367(0x14f7, float:7.521E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.newui.view.ldsnakebar.b$c r1 = r0.e
            if (r1 == 0) goto L_0x0030
            r0.d = r1
            r2 = 0
            r0.e = r2
            java.lang.ref.WeakReference<com.leedarson.newui.view.ldsnakebar.b$b> r1 = r1.a
            java.lang.Object r1 = r1.get()
            com.leedarson.newui.view.ldsnakebar.b$b r1 = (com.leedarson.newui.view.ldsnakebar.b.C0120b) r1
            if (r1 == 0) goto L_0x002e
            r1.show()
            goto L_0x0030
        L_0x002e:
            r0.d = r2
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.ldsnakebar.b.o():void");
    }

    private boolean a(c record, int event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{record, new Integer(event)}, this, changeQuickRedirect, false, 5368, new Class[]{c.class, Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        C0120b callback = (C0120b) record.a.get();
        if (callback == null) {
            return false;
        }
        this.c.removeCallbacksAndMessages(record);
        callback.dismiss(event);
        return true;
    }

    private boolean g(C0120b callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5369, new Class[]{C0120b.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        c cVar = this.d;
        return cVar != null && cVar.a(callback);
    }

    private boolean h(C0120b callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5370, new Class[]{C0120b.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        c cVar = this.e;
        return cVar != null && cVar.a(callback);
    }

    private void m(c r) {
        if (!PatchProxy.proxy(new Object[]{r}, this, changeQuickRedirect, false, 5371, new Class[]{c.class}, Void.TYPE).isSupported) {
            int i = r.b;
            if (i != -2) {
                int durationMs = 2750;
                if (i > 0) {
                    durationMs = r.b;
                } else if (i == -1) {
                    durationMs = 1500;
                }
                this.c.removeCallbacksAndMessages(r);
                Handler handler = this.c;
                handler.sendMessageDelayed(Message.obtain(handler, 0, r), (long) durationMs);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(c record) {
        if (!PatchProxy.proxy(new Object[]{record}, this, changeQuickRedirect, false, 5372, new Class[]{c.class}, Void.TYPE).isSupported) {
            synchronized (this.b) {
                if (this.d == record || this.e == record) {
                    a(record, 2);
                }
            }
        }
    }
}
