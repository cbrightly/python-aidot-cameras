package com.telink.bluetooth.light;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.b;
import com.telink.bluetooth.light.f;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: LightAdapter */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public AtomicInteger A = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public AtomicInteger B = new AtomicInteger(0);
    private p C;
    private boolean D = false;
    private boolean E;
    /* access modifiers changed from: private */
    public j F;
    private int G;
    /* access modifiers changed from: private */
    public long H;
    /* access modifiers changed from: private */
    public long I;
    private Handler J;
    private HandlerThread K;
    /* access modifiers changed from: private */
    public AtomicBoolean L = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean M = new AtomicBoolean(false);
    private Runnable N = new a();
    private final com.telink.util.e<Integer> a = new c(this, (a) null);
    private final com.telink.util.e<Integer> b = new o(this, (a) null);
    private final com.telink.util.e<Integer> c = new m(this, (a) null);
    private final com.telink.util.e<Integer> d = new h(this, (a) null);
    private final com.telink.util.e<Integer> e = new g(this, (a) null);
    private final com.telink.util.e<Integer> f = new i(this, (a) null);
    private final com.telink.util.e<Integer> g = new l(this, (a) null);
    private final com.telink.util.e<Integer> h = new d(this, (a) null);
    private final com.telink.util.e<Integer> i = new k(this, (a) null);
    private final com.telink.util.e<Integer> j = new C0219e(this, (a) null);
    private final AtomicInteger k = new AtomicInteger(1);
    private final AtomicInteger l = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public final AtomicInteger m = new AtomicInteger(-1);
    private final AtomicBoolean n = new AtomicBoolean(false);
    protected b o;
    protected Context p;
    protected j q;
    protected f r;
    /* access modifiers changed from: private */
    public j s;
    /* access modifiers changed from: private */
    public j t;
    /* access modifiers changed from: private */
    public Handler u;
    private Runnable v;
    /* access modifiers changed from: private */
    public int w = 200;
    private Handler x;
    private Runnable y;
    /* access modifiers changed from: private */
    public int z = 0;

    /* compiled from: LightAdapter */
    public interface b {
        void a(int i);

        void b(f fVar, int i, int i2, int i3);

        void c(g gVar, int i, com.telink.bluetooth.a aVar, boolean z);

        void e(g gVar, int i, int i2, int i3, byte[] bArr, byte[] bArr2);

        void f(int i, int i2, int i3);
    }

    static /* synthetic */ void a(e x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 13624, new Class[]{e.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.P(x1);
        }
    }

    static /* synthetic */ void f(e x0, int x1, boolean x2) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1), new Byte(x2 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 13625, new Class[]{e.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.S(x1, x2);
        }
    }

    static /* synthetic */ void g(e x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 13626, new Class[]{e.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.R(x1);
        }
    }

    static /* synthetic */ void h(e x0, g x1) {
        Class[] clsArr = {e.class, g.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13627, clsArr, Void.TYPE).isSupported) {
            x0.I(x1);
        }
    }

    static /* synthetic */ void i(e x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 13628, new Class[]{e.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.B(x1);
        }
    }

    static /* synthetic */ void j(e x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 13629, new Class[]{e.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.Q(x1);
        }
    }

    static /* synthetic */ void q(e x0, int x1, boolean x2, boolean x3) {
        Object[] objArr = {x0, new Integer(x1), new Byte(x2 ? (byte) 1 : 0), new Byte(x3 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13631, new Class[]{e.class, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            x0.T(x1, x2, x3);
        }
    }

    static /* synthetic */ void r(e x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13632, new Class[]{e.class, cls, cls}, Void.TYPE).isSupported) {
            x0.M(x1, x2);
        }
    }

    static /* synthetic */ boolean u(e x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13633, new Class[]{e.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.V();
    }

    static /* synthetic */ int v(e x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13634, new Class[]{e.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.F();
    }

    static /* synthetic */ void y(e x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13636, new Class[]{e.class}, Void.TYPE).isSupported) {
            x0.A();
        }
    }

    static /* synthetic */ void z(e x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13637, new Class[]{e.class}, Void.TYPE).isSupported) {
            x0.X();
        }
    }

    public j E() {
        return this.q;
    }

    public int D() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13577, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.k.get();
    }

    private synchronized void P(int value) {
        Object[] objArr = {new Integer(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13578, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.k.getAndSet(value);
            com.telink.bluetooth.d.a("set mode : " + value);
        }
    }

    public synchronized void U(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 13580, new Class[]{Context.class}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("light mAdapter start");
            if (!this.n.get()) {
                O(true);
                P(1);
                this.p = context;
                this.s = new j();
                this.t = new j();
                this.C = new p();
                f fVar = new f();
                this.r = fVar;
                fVar.g(22, this.g);
                this.r.g(3, this.a);
                this.r.g(4, this.a);
                this.r.g(5, this.a);
                this.r.g(0, this.a);
                this.r.g(1, this.a);
                this.r.g(10, this.b);
                this.r.g(11, this.b);
                this.r.g(71, this.c);
                this.r.g(73, this.c);
                this.r.g(72, this.c);
                this.r.g(80, this.d);
                this.r.g(81, this.d);
                this.r.g(30, this.f);
                this.r.g(31, this.f);
                this.r.g(40, this.h);
                this.r.g(41, this.h);
                this.r.g(82, this.e);
                this.r.g(83, this.e);
                this.r.g(90, this.j);
                this.r.g(91, this.j);
                this.r.g(92, this.j);
                this.r.g(93, this.j);
                this.r.g(94, this.j);
                HandlerThread handlerThread = new HandlerThread("LightAdapter Thread");
                this.K = handlerThread;
                handlerThread.start();
                this.u = new Handler(this.K.getLooper());
                this.v = new f(this, (a) null);
                this.x = new Handler(this.K.getLooper());
                this.y = new n(this, (a) null);
                B(true);
                this.J = new Handler();
                com.telink.bluetooth.b.e().k(this.C);
            }
        }
    }

    public synchronized void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13581, new Class[0], Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("light mAdapter stop");
            if (this.n.get()) {
                O(false);
                P(1);
                X();
                B(false);
                C(false);
                Handler handler = this.u;
                if (handler != null) {
                    handler.removeCallbacksAndMessages((Object) null);
                    this.u = null;
                }
                this.v = null;
                this.p = null;
                this.K.quit();
                this.K = null;
                Handler handler2 = this.x;
                if (handler2 != null) {
                    handler2.removeCallbacksAndMessages((Object) null);
                    this.x = null;
                }
                this.y = null;
                Handler handler3 = this.J;
                if (handler3 != null) {
                    handler3.removeCallbacksAndMessages((Object) null);
                    this.J = null;
                }
                this.r.l();
                this.r.K();
                this.r = null;
                this.C = null;
                this.s.a();
                this.s = null;
                this.t.a();
                this.t = null;
            }
        }
    }

    private void I(g light) {
        if (!PatchProxy.proxy(new Object[]{light}, this, changeQuickRedirect, false, 13589, new Class[]{g.class}, Void.TYPE).isSupported) {
            Arrays.copyOf(light.P(), 16);
            this.q.f("com.telink.bluetooth.light.PARAM_MESH_PASSWORD");
            throw null;
        }
    }

    public boolean N(byte b2, int i2, byte[] bArr, Object obj, int i3) {
        Object[] objArr = {new Byte(b2), new Integer(i2), bArr, obj, new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13597, new Class[]{Byte.TYPE, cls, byte[].class, Object.class, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int address = i2;
        Object tag = obj;
        byte opcode = b2;
        byte[] params = bArr;
        int delay = i3;
        if (!this.n.get() || !this.r.S()) {
            return false;
        }
        if (tag == null) {
            return this.r.b0(opcode, address, params, true, delay);
        }
        return this.r.c0(opcode, address, params, true, tag, delay);
    }

    private boolean V() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13601, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!com.telink.bluetooth.b.e().g()) {
            this.L.set(true);
            this.M.set(true);
            long delay = 0;
            if (!H()) {
                delay = 0;
            } else if (System.currentTimeMillis() - this.I < 6000) {
                delay = 6000 - (System.currentTimeMillis() - this.I);
                if (delay > 6000) {
                    delay = 6000;
                }
            }
            this.J.removeCallbacks(this.N);
            this.J.postDelayed(this.N, delay);
        }
        return true;
    }

    private void X() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13602, new Class[0], Void.TYPE).isSupported) {
            this.J.removeCallbacks(this.N);
            com.telink.bluetooth.b.e().m();
        }
    }

    /* compiled from: LightAdapter */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13638, new Class[0], Void.TYPE).isSupported) {
                if (!com.telink.bluetooth.b.e().l((UUID[]) null)) {
                    e.a(e.this, 1);
                }
            }
        }
    }

    private void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13603, new Class[0], Void.TYPE).isSupported) {
            if (this.L.get()) {
                if (com.telink.bluetooth.b.e().f()) {
                    M(1, 1);
                } else {
                    M(1, 2);
                }
            } else if (this.M.get()) {
                M(1, 3);
            }
        }
    }

    public synchronized void G(boolean disconnect) {
        if (!PatchProxy.proxy(new Object[]{new Byte(disconnect ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 13604, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.n.get()) {
                if (D() != 1) {
                    com.telink.bluetooth.d.a("LightAdapter#idleMode");
                    P(1);
                    this.m.getAndSet(-1);
                    B(false);
                    if (disconnect) {
                        this.r.K();
                    }
                    X();
                }
            }
        }
    }

    public g J(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Object[] objArr = {device, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 13612, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, g.class);
        if (proxy.isSupported) {
            return (g) proxy.result;
        }
        Iterator<b> c2 = c.b().c();
        g light = null;
        while (c2.hasNext()) {
            b filter = c2.next();
            try {
                light = filter.a(device, rssi, scanRecord);
                continue;
            } catch (Exception e2) {
                com.telink.bluetooth.d.b("Advertise Filter Exception : " + filter.toString() + "--" + e2.getMessage(), e2);
                continue;
            }
            if (light != null) {
                break;
            }
        }
        return light;
    }

    public boolean K(g light) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{light}, this, changeQuickRedirect, false, 13613, new Class[]{g.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int mode = D();
        j params = E();
        if (params == null || params.f("com.telink.bluetooth.light.PARAM_MESH_NAME") == null) {
            return false;
        }
        byte[] meshName = com.telink.util.f.c(params.f("com.telink.bluetooth.light.PARAM_MESH_NAME"), 16);
        byte[] meshName1 = light.P();
        if (mode == 2) {
            if (params.f("com.telink.bluetooth.light.PARAM_OUT_OF_MESH") == null) {
                return false;
            }
            return com.telink.util.a.c(meshName, meshName1) || com.telink.util.a.c(com.telink.util.f.c(params.f("com.telink.bluetooth.light.PARAM_OUT_OF_MESH"), 16), meshName1);
        } else if (mode == 8) {
            if (!com.telink.util.a.c(meshName, meshName1)) {
                return false;
            }
            String mac = params.f("com.telink.bluetooth.light.PARAM_AUTO_CONNECT_MAC");
            if (TextUtils.isEmpty(mac) || mac.equals(light.u())) {
                return true;
            }
            return false;
        }
    }

    public void L(byte[] bArr) {
        if (!PatchProxy.proxy(new Object[]{bArr}, this, changeQuickRedirect, false, 13614, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            byte[] data = bArr;
            if (data.length >= 20) {
                int position = 7 + 1;
                int opcode = data[7] & 255;
                if (((data[position] & 255) << 8) + (data[position + 1] & 255) == h.a().g()) {
                    int src = (data[3] & 255) + ((data[4] & 255) << 8);
                    byte[] params = new byte[10];
                    System.arraycopy(data, 10, params, 0, 10);
                    b bVar = this.o;
                    if (bVar != null) {
                        byte[] bArr2 = params;
                        bVar.e(this.r.N(), D(), opcode, src, params, data);
                        return;
                    }
                }
            }
        }
    }

    private int F() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13615, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.l.get();
    }

    private synchronized void Q(int value) {
        Object[] objArr = {new Integer(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13616, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.l.getAndSet(value);
        }
    }

    private synchronized void O(boolean value) {
        Object[] objArr = {new Byte(value ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13617, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.n.getAndSet(value);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void T(int r11, boolean r12, boolean r13) {
        /*
            r10 = this;
            monitor-enter(r10)
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x006b }
            java.lang.Integer r2 = new java.lang.Integer     // Catch:{ all -> 0x006b }
            r2.<init>(r11)     // Catch:{ all -> 0x006b }
            r3 = 0
            r1[r3] = r2     // Catch:{ all -> 0x006b }
            java.lang.Byte r2 = new java.lang.Byte     // Catch:{ all -> 0x006b }
            r2.<init>(r12)     // Catch:{ all -> 0x006b }
            r8 = 1
            r1[r8] = r2     // Catch:{ all -> 0x006b }
            java.lang.Byte r2 = new java.lang.Byte     // Catch:{ all -> 0x006b }
            r2.<init>(r13)     // Catch:{ all -> 0x006b }
            r4 = 2
            r1[r4] = r2     // Catch:{ all -> 0x006b }
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect     // Catch:{ all -> 0x006b }
            r6 = 0
            r7 = 13618(0x3532, float:1.9083E-41)
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch:{ all -> 0x006b }
            java.lang.Class r2 = java.lang.Integer.TYPE     // Catch:{ all -> 0x006b }
            r0[r3] = r2     // Catch:{ all -> 0x006b }
            java.lang.Class r2 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x006b }
            r0[r8] = r2     // Catch:{ all -> 0x006b }
            r0[r4] = r2     // Catch:{ all -> 0x006b }
            java.lang.Class r9 = java.lang.Void.TYPE     // Catch:{ all -> 0x006b }
            r2 = r10
            r3 = r5
            r4 = r6
            r5 = r7
            r6 = r0
            r7 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x006b }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x006b }
            if (r0 == 0) goto L_0x003f
            monitor-exit(r10)
            return
        L_0x003f:
            r0 = r10
            if (r12 != 0) goto L_0x004a
            int r1 = r0.D()     // Catch:{ all -> 0x006b }
            if (r1 != r8) goto L_0x004a
            monitor-exit(r10)
            return
        L_0x004a:
            if (r13 != 0) goto L_0x0056
            java.util.concurrent.atomic.AtomicInteger r1 = r0.m     // Catch:{ all -> 0x006b }
            int r1 = r1.get()     // Catch:{ all -> 0x006b }
            if (r1 != r11) goto L_0x0056
            monitor-exit(r10)
            return
        L_0x0056:
            java.util.concurrent.atomic.AtomicInteger r1 = r0.m     // Catch:{ all -> 0x006b }
            int r1 = r1.getAndSet(r11)     // Catch:{ all -> 0x006b }
            com.telink.bluetooth.light.e$b r2 = r0.o     // Catch:{ all -> 0x006b }
            if (r2 == 0) goto L_0x0069
            com.telink.bluetooth.light.f r3 = r0.r     // Catch:{ all -> 0x006b }
            int r4 = r0.D()     // Catch:{ all -> 0x006b }
            r2.b(r3, r4, r1, r11)     // Catch:{ all -> 0x006b }
        L_0x0069:
            monitor-exit(r10)
            return
        L_0x006b:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.light.e.T(int, boolean, boolean):void");
    }

    private void S(int newStatus, boolean ignoreIdleMode) {
        Object[] objArr = {new Integer(newStatus), new Byte(ignoreIdleMode ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13619, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            T(newStatus, ignoreIdleMode, false);
        }
    }

    private void R(int newStatus) {
        if (!PatchProxy.proxy(new Object[]{new Integer(newStatus)}, this, changeQuickRedirect, false, 13620, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("LightAdapter#setStatus:" + newStatus);
            T(newStatus, false, false);
        }
    }

    private void M(int stateCode, int errorCode) {
        f fVar;
        Object[] objArr = {new Integer(stateCode), new Integer(errorCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13621, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (this.o != null) {
                com.telink.bluetooth.d.a("reportError: state-" + stateCode + " error-" + errorCode);
                int deviceId = 0;
                if (!(stateCode == 1 || (fVar = this.r) == null || fVar.N() == null)) {
                    deviceId = this.r.N().O();
                }
                this.o.f(stateCode, errorCode, deviceId);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void B(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Boolean.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 13622(0x3536, float:1.9088E-41)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            android.os.Handler r1 = r0.u
            if (r1 == 0) goto L_0x0041
            java.lang.Runnable r2 = r0.v
            if (r2 != 0) goto L_0x002e
            goto L_0x0041
        L_0x002e:
            if (r9 == 0) goto L_0x003b
            boolean r3 = r0.D
            if (r3 != 0) goto L_0x003e
            int r3 = r0.w
            long r3 = (long) r3
            r1.postDelayed(r2, r3)
            goto L_0x003e
        L_0x003b:
            r1.removeCallbacks(r2)
        L_0x003e:
            r0.D = r9
            return
        L_0x0041:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.light.e.B(boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0022, code lost:
        r1 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void C(boolean r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r10)
            r8 = 0
            r1[r8] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Boolean.TYPE
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13623(0x3537, float:1.909E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0022
            return
        L_0x0022:
            r1 = r9
            android.os.Handler r2 = r1.x
            if (r2 == 0) goto L_0x0043
            java.lang.Runnable r3 = r1.y
            if (r3 != 0) goto L_0x002c
            goto L_0x0043
        L_0x002c:
            if (r10 == 0) goto L_0x003d
            boolean r4 = r1.E
            if (r4 == 0) goto L_0x0033
            return
        L_0x0033:
            r1.G = r8
            r1.E = r0
            r4 = 0
            r2.postDelayed(r3, r4)
            goto L_0x0042
        L_0x003d:
            r2.removeCallbacks(r3)
            r1.E = r8
        L_0x0042:
            return
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.light.e.C(boolean):void");
    }

    /* compiled from: LightAdapter */
    public final class p implements b.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        public p() {
        }

        public void onScanFail(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13681, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a(" scan fail : " + errorCode);
                b bVar = e.this.o;
                if (bVar != null) {
                    bVar.a(errorCode);
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13682, new Class[0], Void.TYPE).isSupported) {
                long unused = e.this.I = System.currentTimeMillis();
            }
        }

        public void b() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            g light;
            if (!PatchProxy.proxy(new Object[]{device, new Integer(rssi), scanRecord}, this, changeQuickRedirect, false, 13683, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
                if (e.this.L.get()) {
                    e.this.L.set(false);
                }
                e eVar = e.this;
                if (eVar.o != null && eVar.D() != 1 && e.this.D() != 4 && !e.this.s.b(device.getAddress()) && (light = e.this.J(device, rssi, scanRecord)) != null && e.this.K(light)) {
                    if (e.this.M.get()) {
                        e.this.M.set(false);
                    }
                    com.telink.bluetooth.d.a("add scan result : " + device.getAddress());
                    int mode = e.this.D();
                    if (mode == 2) {
                        e.this.q.c("com.telink.bluetooth.light.PARAM_SCAN_TYPE_SINGLE", false);
                        throw null;
                    } else if (mode == 8) {
                        e.this.s.e(light);
                    } else if (mode == 16) {
                        e.this.s.e(light);
                    }
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class c implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private c() {
        }

        /* synthetic */ c(e x0, a x1) {
            this();
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13639, new Class[0], Void.TYPE).isSupported) {
                e.f(e.this, 1, true);
                int mode = e.this.D();
                if (mode != 1) {
                    if (mode == 4) {
                        e.this.r.V();
                    }
                    e.g(e.this, 2);
                    e.h(e.this, e.this.r.N());
                    return;
                }
                e.this.r.V();
            }
        }

        private void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13640, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("onLoginSuccess " + e.this.r.N().u());
                e.f(e.this, 3, true);
                int mode = e.this.D();
                if (mode == 4) {
                    e.g(e.this, 11);
                    e.this.q.f("com.telink.bluetooth.light.PARAM_NEW_MESH_NAME");
                    throw null;
                } else if (mode == 8) {
                    e.i(e.this, false);
                    e.j(e.this, 1);
                    e.this.s.a();
                    e.this.B.set(0);
                    long unused = e.this.H = 0;
                    e.this.q.b("com.telink.bluetooth.light.PARAM_AUTO_ENABLE_NOTIFICATION");
                    throw null;
                } else if (mode == 16) {
                    e.j(e.this, 1);
                    e.this.q.a("com.telink.bluetooth.light.PARAM_DEVICE_LIST");
                    throw null;
                }
            }
        }

        private void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13641, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("onLoginFail " + e.this.r.N().u());
                e.f(e.this, 4, true);
                int mode = e.this.D();
                if (mode == 4) {
                    e.j(e.this, 2);
                    if (e.this.m.get() != 10 && e.this.m.get() != 13) {
                        e.g(e.this, 12);
                    }
                } else if (mode == 8) {
                    e.this.s.a();
                    e.this.B.set(0);
                    e.j(e.this, 2);
                    e.i(e.this, true);
                } else if (mode == 16) {
                    e.j(e.this, 1);
                    e.g(e.this, 51);
                }
            }
        }

        private void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13642, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("onNError " + e.this.r.N().u());
                e.f(e.this, 4, true);
                e.j(e.this, 1);
                e.g(e.this, 5);
                e.this.G(true);
            }
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13643, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 0:
                        d();
                        return;
                    case 1:
                    case 4:
                        c();
                        return;
                    case 3:
                        b();
                        return;
                    case 5:
                        e();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class k implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private k() {
        }

        /* synthetic */ k(e x0, a x1) {
            this();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
            r1 = r8;
            r2 = r1.a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void c(com.telink.bluetooth.a r9) {
            /*
                r8 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r2 = 0
                r1[r2] = r9
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<com.telink.bluetooth.a> r4 = com.telink.bluetooth.a.class
                r6[r2] = r4
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 13670(0x3566, float:1.9156E-41)
                r2 = r8
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r1 = r1.isSupported
                if (r1 == 0) goto L_0x001d
                return
            L_0x001d:
                r1 = r8
                com.telink.bluetooth.light.e r2 = com.telink.bluetooth.light.e.this
                com.telink.bluetooth.light.e$b r3 = r2.o
                if (r3 == 0) goto L_0x0033
                com.telink.bluetooth.light.f r2 = r2.r
                com.telink.bluetooth.light.g r2 = r2.N()
                com.telink.bluetooth.light.e r4 = com.telink.bluetooth.light.e.this
                int r4 = r4.D()
                r3.c(r2, r4, r9, r0)
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.light.e.k.c(com.telink.bluetooth.a):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
            r0 = r9;
            r1 = r0.a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void b(com.telink.bluetooth.a r10) {
            /*
                r9 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r10
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<com.telink.bluetooth.a> r0 = com.telink.bluetooth.a.class
                r6[r8] = r0
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 13671(0x3567, float:1.9157E-41)
                r2 = r9
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x001d
                return
            L_0x001d:
                r0 = r9
                com.telink.bluetooth.light.e r1 = com.telink.bluetooth.light.e.this
                com.telink.bluetooth.light.e$b r2 = r1.o
                if (r2 == 0) goto L_0x0033
                com.telink.bluetooth.light.f r1 = r1.r
                com.telink.bluetooth.light.g r1 = r1.N()
                com.telink.bluetooth.light.e r3 = com.telink.bluetooth.light.e.this
                int r3 = r3.D()
                r2.c(r1, r3, r10, r8)
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.light.e.k.b(com.telink.bluetooth.a):void");
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13672, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                f.g lightEvent = (f.g) event;
                switch (((Integer) lightEvent.b()).intValue()) {
                    case 50:
                        c((com.telink.bluetooth.a) lightEvent.d());
                        return;
                    case 51:
                        b((com.telink.bluetooth.a) lightEvent.d());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class l implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private l() {
        }

        /* synthetic */ l(e x0, a x1) {
            this();
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13673, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                if (eVar.o != null) {
                    eVar.L((byte[]) ((f.g) event).d());
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class o implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private o() {
        }

        /* synthetic */ o(e x0, a x1) {
            this();
        }

        private void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13678, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("onResetMeshSuccess " + e.this.r.N().u());
                e.g(e.this, 10);
                if (e.this.D() == 4) {
                    e.this.A.getAndIncrement();
                    e.j(e.this, 1);
                }
            }
        }

        private void b(String reason) {
            if (!PatchProxy.proxy(new Object[]{reason}, this, changeQuickRedirect, false, 13679, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("onResetMeshFail " + e.this.r.N().u() + " error msg : " + reason);
                e.g(e.this, 12);
                if (e.this.D() == 4) {
                    e.j(e.this, 2);
                }
            }
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13680, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                f.g lightEvent = (f.g) event;
                switch (((Integer) lightEvent.b()).intValue()) {
                    case 10:
                        c();
                        return;
                    case 11:
                        b((String) lightEvent.d());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class h implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private h() {
        }

        /* synthetic */ h(e x0, a x1) {
            this();
        }

        private void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13656, new Class[0], Void.TYPE).isSupported) {
                int mode = e.this.D();
                if (mode != 4 && mode != 8 && mode != 16) {
                    e.f(e.this, 60, true);
                }
            }
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13657, new Class[0], Void.TYPE).isSupported) {
                int mode = e.this.D();
                if (mode != 4 && mode != 8 && mode != 16) {
                    e.f(e.this, 61, true);
                }
            }
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13658, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 80:
                        c();
                        return;
                    case 81:
                        b();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class g implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private g() {
        }

        /* synthetic */ g(e x0, a x1) {
            this();
        }

        private void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13653, new Class[0], Void.TYPE).isSupported) {
                int mode = e.this.D();
                Log.e("weicb", "onGetFirmwareIDSuccess mode = " + mode);
                e.f(e.this, 62, true);
            }
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13654, new Class[0], Void.TYPE).isSupported) {
                int mode = e.this.D();
                Log.e("weicb", "onGetFirmwareIDFailure mode = " + mode);
                if (mode != 4 && mode != 8 && mode != 16) {
                    e.f(e.this, 63, true);
                }
            }
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13655, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 82:
                        c();
                        return;
                    case 83:
                        b();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class i implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private i() {
        }

        /* synthetic */ i(e x0, a x1) {
            this();
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13659, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 30:
                        e.g(e.this, 20);
                        return;
                    case 31:
                        e.g(e.this, 21);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class d implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private d() {
        }

        /* synthetic */ d(e x0, a x1) {
            this();
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13644, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 40:
                        e.g(e.this, 70);
                        e.a(e.this, 1);
                        return;
                    case 41:
                        e.g(e.this, 71);
                        e.a(e.this, 1);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class m implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private m() {
        }

        /* synthetic */ m(e x0, a x1) {
            this();
        }

        private void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13674, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("OTA Success");
                e.f(e.this, 50, true);
                e.a(e.this, 1);
            }
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13675, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("OTA Failure");
                e.f(e.this, 51, true);
                e.a(e.this, 1);
            }
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13676, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 71:
                        c();
                        return;
                    case 72:
                        b();
                        return;
                    case 73:
                        e.q(e.this, 52, true, true);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* renamed from: com.telink.bluetooth.light.e$e  reason: collision with other inner class name */
    /* compiled from: LightAdapter */
    public final class C0219e implements com.telink.util.e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        private C0219e() {
        }

        /* synthetic */ C0219e(e x0, a x1) {
            this();
        }

        public void a(com.telink.util.c<Integer> event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13645, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
                switch (event.b().intValue()) {
                    case 90:
                        e.r(e.this, 2, 1);
                        return;
                    case 91:
                        e.r(e.this, 2, 2);
                        return;
                    case 92:
                        e.r(e.this, 3, 1);
                        return;
                    case 93:
                        e.r(e.this, 3, 2);
                        return;
                    case 94:
                        e.r(e.this, 3, 3);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        private boolean c;
        private long d;
        private int f;

        private f() {
            this.f = 5000;
        }

        /* synthetic */ f(e x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13646, new Class[0], Void.TYPE).isSupported) {
                int mode = e.this.D();
                if (mode == 2) {
                    d();
                } else if (mode == 4) {
                    f();
                } else if (mode == 8) {
                    a();
                } else if (mode == 16) {
                    b();
                }
                if (e.this.u != null) {
                    e.this.u.postDelayed(this, (long) e.this.w);
                }
            }
        }

        private void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13647, new Class[0], Void.TYPE).isSupported) {
                if (!e.u(e.this)) {
                    e.a(e.this, 1);
                } else {
                    e.this.q.c("com.telink.bluetooth.light.PARAM_SCAN_TYPE_SINGLE", false);
                    throw null;
                }
            }
        }

        private void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13648, new Class[0], Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("update()");
                if (e.v(e.this) != 1) {
                    if (e.this.A.get() >= e.this.z || e.this.B.get() >= e.this.z) {
                        com.telink.bluetooth.d.a("updateCount.get()：" + e.this.A.get() + "nextLightIndex.get()：" + e.this.B.get() + "lightCount：" + e.this.z);
                        e.j(e.this, 1);
                        e.this.B.set(0);
                        e.g(e.this, 13);
                        e.this.G(false);
                        return;
                    }
                    e.j(e.this, 1);
                    g light = e.this.t.c(e.this.B.getAndIncrement());
                    if (light == null || light.B) {
                        e.j(e.this, 2);
                    } else {
                        e.this.q.d("com.telink.bluetooth.light.PARAM_TIMEOUT_SECONDS");
                        throw null;
                    }
                }
            }
        }

        private void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13649, new Class[0], Void.TYPE).isSupported) {
                if (e.v(e.this) != 1) {
                    if (this.c) {
                        if (System.currentTimeMillis() - this.d >= ((long) this.f)) {
                            this.c = false;
                        } else {
                            return;
                        }
                    }
                    if (!e.u(e.this)) {
                        e.a(e.this, 1);
                    } else if (!c() && e.this.s.f() > 0) {
                        e.j(e.this, 1);
                        e();
                        e.this.q.d("com.telink.bluetooth.light.PARAM_TIMEOUT_SECONDS");
                        throw null;
                    }
                }
            }
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13650, new Class[0], Void.TYPE).isSupported) {
                if (e.v(e.this) != 1 && e.this.s.f() > 0) {
                    e.j(e.this, 1);
                    e.this.q.d("com.telink.bluetooth.light.PARAM_TIMEOUT_SECONDS");
                    throw null;
                }
            }
        }

        private boolean c() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13651, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (e.this.H == 0) {
                long unused = e.this.H = System.currentTimeMillis() - ((long) e.this.w);
                return false;
            }
            e.this.q.e("com.telink.bluetooth.light.PARAM_OFFLINE_TIMEOUT_SECONDS", 0);
            throw null;
        }

        private void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13652, new Class[0], Void.TYPE).isSupported) {
                e.y(e.this);
                e.z(e.this);
                this.c = true;
                this.d = System.currentTimeMillis();
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class n implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private n() {
        }

        /* synthetic */ n(e x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13677, new Class[0], Void.TYPE).isSupported) {
                if (e.this.D() == 8 && e.this.F != null && e.this.r.S()) {
                    e.this.F.e("com.telink.bluetooth.light.PARAM_AUTO_REFRESH_NOTIFICATION_DELAY", WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
                    throw null;
                }
            }
        }
    }

    /* compiled from: LightAdapter */
    public final class j {
        public static ChangeQuickRedirect changeQuickRedirect;
        private CopyOnWriteArrayList<g> a = new CopyOnWriteArrayList<>();

        public j() {
        }

        public void e(g light) {
            if (!PatchProxy.proxy(new Object[]{light}, this, changeQuickRedirect, false, 13660, new Class[]{g.class}, Void.TYPE).isSupported) {
                if (d(light.u()) == -1) {
                    this.a.add(light);
                }
            }
        }

        @Nullable
        public g c(int index) {
            Object[] objArr = {new Integer(index)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 13661, new Class[]{Integer.TYPE}, g.class);
            if (proxy.isSupported) {
                return (g) proxy.result;
            }
            if (index < 0 || index >= this.a.size()) {
                return null;
            }
            return this.a.get(index);
        }

        public boolean b(String macAddress) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 13663, new Class[]{String.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (d(macAddress) != -1) {
                return true;
            }
            return false;
        }

        public int f() {
            int size;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13664, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            synchronized (this) {
                size = this.a.size();
            }
            return size;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13665, new Class[0], Void.TYPE).isSupported) {
                synchronized (this) {
                    this.a.clear();
                }
            }
        }

        private int d(String macAddress) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 13666, new Class[]{String.class}, Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            int count = f();
            for (int i = 0; i < count; i++) {
                if (this.a.get(i).u().equals(macAddress)) {
                    return i;
                }
            }
            return -1;
        }
    }

    private boolean H() {
        return Build.VERSION.SDK_INT >= 24;
    }
}
