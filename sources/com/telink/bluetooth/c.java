package com.telink.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.os.Looper;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.a;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Peripheral */
public class c extends BluetoothGattCallback {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected final Queue<b> a = new ConcurrentLinkedQueue();
    protected final Queue<b> b = new ConcurrentLinkedQueue();
    protected final Map<String, b> c = new ConcurrentHashMap();
    protected final Handler d = new Handler(Looper.getMainLooper());
    protected final Handler e = new Handler(Looper.getMainLooper());
    protected final Handler f = new Handler(Looper.getMainLooper());
    protected final Runnable g = new e(this, (a) null);
    protected final Runnable h = new d(this, (a) null);
    protected final Runnable i = new C0216c(this, (a) null);
    private final Object j = new Object();
    protected BluetoothDevice k;
    protected BluetoothGatt l;
    protected int m;
    protected byte[] n;
    protected String o;
    protected String p;
    protected byte[] q;
    protected int r;
    protected List<BluetoothGattService> s;
    protected AtomicBoolean t = new AtomicBoolean(false);
    protected boolean u;
    protected int v = 5000;
    protected int w = 10000;
    protected long x;
    private AtomicInteger y = new AtomicInteger(1);

    static /* synthetic */ boolean a(c x0, b x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13474, new Class[]{c.class, b.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.k(x1);
    }

    static /* synthetic */ void b(c x0, b x1) {
        Class[] clsArr = {c.class, b.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13475, clsArr, Void.TYPE).isSupported) {
            x0.F(x1);
        }
    }

    static /* synthetic */ void c(c x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13476, new Class[]{c.class}, Void.TYPE).isSupported) {
            x0.f();
        }
    }

    public c(BluetoothDevice device, byte[] scanRecord, int rssi) {
        this.k = device;
        this.n = scanRecord;
        this.m = rssi;
        this.o = device.getName();
        this.p = device.getAddress();
        this.r = device.getType();
    }

    public String t() {
        return this.o;
    }

    public String u() {
        return this.p;
    }

    public byte[] v() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13434, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.q == null) {
            String[] strArray = u().split(":");
            int length = strArray.length;
            this.q = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.q[i2] = (byte) (Integer.parseInt(strArray[i2], 16) & 255);
            }
            com.telink.util.a.e(this.q, 0, length - 1);
        }
        return this.q;
    }

    public boolean w() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13435, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return this.y.get() == 4;
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13437, new Class[0], Void.TYPE).isSupported) {
            if (this.y.get() == 2 || this.y.get() == 4) {
                d.a("disconnect " + t() + " -- " + u());
                e();
                synchronized (this.j) {
                    if (this.l == null) {
                        this.y.set(1);
                    } else if (this.y.get() == 4) {
                        this.l.disconnect();
                        this.y.set(8);
                    } else {
                        this.l.disconnect();
                        this.l.close();
                        this.y.set(16);
                    }
                }
            }
        }
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13438, new Class[0], Void.TYPE).isSupported) {
            this.t.set(false);
            I();
            d();
            this.a.clear();
            this.b.clear();
            this.c.clear();
            this.f.removeCallbacksAndMessages((Object) null);
        }
    }

    public boolean H(a.C0214a callback, a command) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback, command}, this, changeQuickRedirect, false, 13439, new Class[]{a.C0214a.class, a.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.y.get() != 4) {
            return false;
        }
        C(new b(callback, command));
        return true;
    }

    public final void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13440, new Class[0], Void.TYPE).isSupported) {
            this.u = false;
            this.e.removeCallbacks(this.g);
            this.e.removeCallbacksAndMessages((Object) null);
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13442, new Class[0], Void.TYPE).isSupported) {
            n(this.u);
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13443, new Class[0], Void.TYPE).isSupported) {
            n(false);
        }
    }

    public void B(List<BluetoothGattService> list) {
    }

    public void z(byte[] data, UUID serviceUUID, UUID characteristicUUID, Object tag) {
    }

    public void A() {
    }

    public void n(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13444, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (enable) {
                this.e.removeCallbacks(this.g);
                this.e.postDelayed(this.g, (long) this.v);
                return;
            }
            this.e.removeCallbacks(this.g);
            this.e.removeCallbacksAndMessages((Object) null);
        }
    }

    private void C(b commandContext) {
        if (!PatchProxy.proxy(new Object[]{commandContext}, this, changeQuickRedirect, false, 13445, new Class[]{b.class}, Void.TYPE).isSupported) {
            d.a("postCommand");
            if (commandContext.a.f < 0) {
                synchronized (this.b) {
                    this.b.add(commandContext);
                    F(commandContext);
                }
                return;
            }
            this.a.add(commandContext);
            if (!this.t.get()) {
                E();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0044, code lost:
        if (r2 != null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0047, code lost:
        r1 = r2.a.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004d, code lost:
        if (r1 == com.telink.bluetooth.a.b.ENABLE_NOTIFY) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0051, code lost:
        if (r1 == com.telink.bluetooth.a.b.DISABLE_NOTIFY) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0053, code lost:
        r3 = r0.b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0055, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0.b.add(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005b, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0062, code lost:
        if (r0.t.get() != false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0064, code lost:
        r0.t.set(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006e, code lost:
        r3 = r2.a.f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0072, code lost:
        if (r3 <= 0) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0074, code lost:
        r4 = java.lang.System.currentTimeMillis();
        r6 = r0.x;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007e, code lost:
        if (r6 == 0) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0085, code lost:
        if ((r4 - r6) < ((long) r3)) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0088, code lost:
        r0.f.postDelayed(r0.i, (long) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0091, code lost:
        F(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0095, code lost:
        F(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void E() {
        /*
            r10 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13446(0x3486, float:1.8842E-41)
            r2 = r10
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r10
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "processing : "
            r1.append(r2)
            java.util.concurrent.atomic.AtomicBoolean r2 = r0.t
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.telink.bluetooth.d.a(r1)
            java.util.Queue<com.telink.bluetooth.c$b> r1 = r0.a
            monitor-enter(r1)
            java.util.Queue<com.telink.bluetooth.c$b> r2 = r0.a     // Catch:{ all -> 0x0099 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0099 }
            if (r2 == 0) goto L_0x003b
            monitor-exit(r1)     // Catch:{ all -> 0x0099 }
            return
        L_0x003b:
            java.util.Queue<com.telink.bluetooth.c$b> r2 = r0.a     // Catch:{ all -> 0x0099 }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0099 }
            com.telink.bluetooth.c$b r2 = (com.telink.bluetooth.c.b) r2     // Catch:{ all -> 0x0099 }
            monitor-exit(r1)     // Catch:{ all -> 0x0099 }
            if (r2 != 0) goto L_0x0047
            return
        L_0x0047:
            com.telink.bluetooth.a r1 = r2.a
            com.telink.bluetooth.a$b r1 = r1.c
            com.telink.bluetooth.a$b r3 = com.telink.bluetooth.a.b.ENABLE_NOTIFY
            if (r1 == r3) goto L_0x006e
            com.telink.bluetooth.a$b r3 = com.telink.bluetooth.a.b.DISABLE_NOTIFY
            if (r1 == r3) goto L_0x006e
            java.util.Queue<com.telink.bluetooth.c$b> r3 = r0.b
            monitor-enter(r3)
            java.util.Queue<com.telink.bluetooth.c$b> r4 = r0.b     // Catch:{ all -> 0x006b }
            r4.add(r2)     // Catch:{ all -> 0x006b }
            monitor-exit(r3)     // Catch:{ all -> 0x006b }
            java.util.concurrent.atomic.AtomicBoolean r3 = r0.t
            boolean r3 = r3.get()
            if (r3 != 0) goto L_0x006e
            java.util.concurrent.atomic.AtomicBoolean r3 = r0.t
            r4 = 1
            r3.set(r4)
            goto L_0x006e
        L_0x006b:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x006b }
            throw r4
        L_0x006e:
            com.telink.bluetooth.a r3 = r2.a
            int r3 = r3.f
            if (r3 <= 0) goto L_0x0095
            long r4 = java.lang.System.currentTimeMillis()
            long r6 = r0.x
            r8 = 0
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 == 0) goto L_0x0091
            long r6 = r4 - r6
            long r8 = (long) r3
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 < 0) goto L_0x0088
            goto L_0x0091
        L_0x0088:
            android.os.Handler r6 = r0.f
            java.lang.Runnable r7 = r0.i
            long r8 = (long) r3
            r6.postDelayed(r7, r8)
            goto L_0x0094
        L_0x0091:
            r0.F(r2)
        L_0x0094:
            goto L_0x0098
        L_0x0095:
            r0.F(r2)
        L_0x0098:
            return
        L_0x0099:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0099 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.c.E():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0087, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void F(com.telink.bluetooth.c.b r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0088 }
            r2 = 0
            r1[r2] = r10     // Catch:{ all -> 0x0088 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0088 }
            r4 = 0
            r5 = 13447(0x3487, float:1.8843E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0088 }
            java.lang.Class<com.telink.bluetooth.c$b> r0 = com.telink.bluetooth.c.b.class
            r6[r2] = r0     // Catch:{ all -> 0x0088 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0088 }
            r2 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0088 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0088 }
            if (r0 == 0) goto L_0x001f
            monitor-exit(r9)
            return
        L_0x001f:
            r0 = r9
            com.telink.bluetooth.a r1 = r10.a     // Catch:{ all -> 0x0088 }
            r7 = r1
            com.telink.bluetooth.a$b r1 = r7.c     // Catch:{ all -> 0x0088 }
            r8 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0088 }
            r1.<init>()     // Catch:{ all -> 0x0088 }
            java.lang.String r2 = "processCommand : "
            r1.append(r2)     // Catch:{ all -> 0x0088 }
            java.lang.String r2 = r7.toString()     // Catch:{ all -> 0x0088 }
            r1.append(r2)     // Catch:{ all -> 0x0088 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0088 }
            com.telink.bluetooth.d.a(r1)     // Catch:{ all -> 0x0088 }
            int[] r1 = com.telink.bluetooth.c.a.a     // Catch:{ all -> 0x0088 }
            int r2 = r8.ordinal()     // Catch:{ all -> 0x0088 }
            r1 = r1[r2]     // Catch:{ all -> 0x0088 }
            switch(r1) {
                case 1: goto L_0x007b;
                case 2: goto L_0x006b;
                case 3: goto L_0x005b;
                case 4: goto L_0x0053;
                case 5: goto L_0x004b;
                default: goto L_0x004a;
            }     // Catch:{ all -> 0x0088 }
        L_0x004a:
            goto L_0x0086
        L_0x004b:
            java.util.UUID r1 = r7.a     // Catch:{ all -> 0x0088 }
            java.util.UUID r2 = r7.b     // Catch:{ all -> 0x0088 }
            r0.l(r10, r1, r2)     // Catch:{ all -> 0x0088 }
            goto L_0x0086
        L_0x0053:
            java.util.UUID r1 = r7.a     // Catch:{ all -> 0x0088 }
            java.util.UUID r2 = r7.b     // Catch:{ all -> 0x0088 }
            r0.o(r10, r1, r2)     // Catch:{ all -> 0x0088 }
            goto L_0x0086
        L_0x005b:
            r0.D()     // Catch:{ all -> 0x0088 }
            java.util.UUID r3 = r7.a     // Catch:{ all -> 0x0088 }
            java.util.UUID r4 = r7.b     // Catch:{ all -> 0x0088 }
            r5 = 1
            byte[] r6 = r7.d     // Catch:{ all -> 0x0088 }
            r1 = r0
            r2 = r10
            r1.J(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0088 }
            goto L_0x0086
        L_0x006b:
            r0.D()     // Catch:{ all -> 0x0088 }
            java.util.UUID r3 = r7.a     // Catch:{ all -> 0x0088 }
            java.util.UUID r4 = r7.b     // Catch:{ all -> 0x0088 }
            r5 = 2
            byte[] r6 = r7.d     // Catch:{ all -> 0x0088 }
            r1 = r0
            r2 = r10
            r1.J(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0088 }
            goto L_0x0086
        L_0x007b:
            r0.D()     // Catch:{ all -> 0x0088 }
            java.util.UUID r1 = r7.a     // Catch:{ all -> 0x0088 }
            java.util.UUID r2 = r7.b     // Catch:{ all -> 0x0088 }
            r0.G(r10, r1, r2)     // Catch:{ all -> 0x0088 }
        L_0x0086:
            monitor-exit(r9)
            return
        L_0x0088:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.c.F(com.telink.bluetooth.c$b):void");
    }

    /* compiled from: Peripheral */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.b.values().length];
            a = iArr;
            try {
                iArr[a.b.READ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.b.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.b.WRITE_NO_RESPONSE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[a.b.ENABLE_NOTIFY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[a.b.DISABLE_NOTIFY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13448, new Class[0], Void.TYPE).isSupported) {
            d.a("commandCompleted");
            if (this.t.get()) {
                this.t.set(false);
            }
            E();
        }
    }

    private void i(b commandContext, Object data) {
        Class[] clsArr = {b.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{commandContext, data}, this, changeQuickRedirect, false, 13449, clsArr, Void.TYPE).isSupported) {
            d.a("commandSuccess");
            this.x = System.currentTimeMillis();
            if (commandContext != null) {
                a command = commandContext.a;
                a.C0214a callback = commandContext.b;
                commandContext.a();
                if (callback != null) {
                    callback.a(this, command, data);
                }
            }
        }
    }

    private void j(Object data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 13450, new Class[]{Object.class}, Void.TYPE).isSupported) {
            i(this.b.poll(), data);
        }
    }

    private void g(b commandContext, String errorMsg) {
        Class[] clsArr = {b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{commandContext, errorMsg}, this, changeQuickRedirect, false, 13451, clsArr, Void.TYPE).isSupported) {
            d.a("commandError:" + errorMsg);
            this.x = System.currentTimeMillis();
            if (commandContext != null) {
                a command = commandContext.a;
                a.C0214a callback = commandContext.b;
                commandContext.a();
                if (callback != null) {
                    callback.c(this, command, errorMsg);
                }
            }
        }
    }

    private void h(String errorMsg) {
        if (!PatchProxy.proxy(new Object[]{errorMsg}, this, changeQuickRedirect, false, 13452, new Class[]{String.class}, Void.TYPE).isSupported) {
            g(this.b.poll(), errorMsg);
        }
    }

    private boolean k(b commandContext) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{commandContext}, this, changeQuickRedirect, false, 13453, new Class[]{b.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        d.a("commandTimeout");
        this.x = System.currentTimeMillis();
        if (commandContext != null) {
            a command = commandContext.a;
            a.C0214a callback = commandContext.b;
            commandContext.a();
            if (callback != null) {
                return callback.b(this, command);
            }
        }
        return false;
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13454, new Class[0], Void.TYPE).isSupported) {
            if (this.w > 0) {
                this.d.removeCallbacksAndMessages((Object) null);
                this.d.postDelayed(this.h, (long) this.w);
            }
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13455, new Class[0], Void.TYPE).isSupported) {
            this.d.removeCallbacksAndMessages((Object) null);
        }
    }

    private void G(b bVar, UUID serviceUUID, UUID characteristicUUID) {
        Class[] clsArr = {b.class, UUID.class, UUID.class};
        if (!PatchProxy.proxy(new Object[]{bVar, serviceUUID, characteristicUUID}, this, changeQuickRedirect, false, 13456, clsArr, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            BluetoothGattService service = this.l.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUUID);
                if (characteristic == null) {
                    success = false;
                    errorMsg = "read characteristic error: characteristic-null ";
                } else if (!this.l.readCharacteristic(characteristic)) {
                    success = false;
                    errorMsg = "read characteristic error";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                h(errorMsg);
                f();
            }
        }
    }

    private void J(b bVar, UUID serviceUUID, UUID characteristicUUID, int writeType, byte[] data) {
        if (!PatchProxy.proxy(new Object[]{bVar, serviceUUID, characteristicUUID, new Integer(writeType), data}, this, changeQuickRedirect, false, 13457, new Class[]{b.class, UUID.class, UUID.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            BluetoothGattService service = this.l.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = q(service, characteristicUUID, writeType);
                if (characteristic != null) {
                    characteristic.setValue(data);
                    characteristic.setWriteType(writeType);
                    if (!this.l.writeCharacteristic(characteristic)) {
                        success = false;
                        errorMsg = "write characteristic error";
                    }
                } else {
                    success = false;
                    errorMsg = "no characteristic";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                h(errorMsg);
                f();
            }
        }
    }

    private void o(b commandContext, UUID serviceUUID, UUID characteristicUUID) {
        Class[] clsArr = {b.class, UUID.class, UUID.class};
        if (!PatchProxy.proxy(new Object[]{commandContext, serviceUUID, characteristicUUID}, this, changeQuickRedirect, false, 13458, clsArr, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            BluetoothGattService service = this.l.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = p(service, characteristicUUID);
                if (characteristic == null) {
                    success = false;
                    errorMsg = "no characteristic";
                } else if (!this.l.setCharacteristicNotification(characteristic, true)) {
                    success = false;
                    errorMsg = "enable notification error";
                } else {
                    this.c.put(s(serviceUUID, characteristic), commandContext);
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                g(commandContext, errorMsg);
            }
            f();
        }
    }

    private void l(b commandContext, UUID serviceUUID, UUID characteristicUUID) {
        Class[] clsArr = {b.class, UUID.class, UUID.class};
        if (!PatchProxy.proxy(new Object[]{commandContext, serviceUUID, characteristicUUID}, this, changeQuickRedirect, false, 13459, clsArr, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            BluetoothGattService service = this.l.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = p(service, characteristicUUID);
                if (characteristic != null) {
                    this.c.remove(s(serviceUUID, characteristic));
                    if (!this.l.setCharacteristicNotification(characteristic, false)) {
                        success = false;
                        errorMsg = "disable notification error";
                    }
                } else {
                    success = false;
                    errorMsg = "no characteristic";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                g(commandContext, errorMsg);
            }
            f();
        }
    }

    private BluetoothGattCharacteristic q(BluetoothGattService service, UUID characteristicUUID, int writeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{service, characteristicUUID, new Integer(writeType)}, this, changeQuickRedirect, false, 13460, new Class[]{BluetoothGattService.class, UUID.class, Integer.TYPE}, BluetoothGattCharacteristic.class);
        if (proxy.isSupported) {
            return (BluetoothGattCharacteristic) proxy.result;
        }
        int writeProperty = 8;
        if (writeType == 1) {
            writeProperty = 4;
        }
        for (BluetoothGattCharacteristic c2 : service.getCharacteristics()) {
            if ((c2.getProperties() & writeProperty) != 0 && characteristicUUID.equals(c2.getUuid())) {
                return c2;
            }
        }
        return null;
    }

    private BluetoothGattCharacteristic p(BluetoothGattService service, UUID characteristicUUID) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{service, characteristicUUID}, this, changeQuickRedirect2, false, 13461, new Class[]{BluetoothGattService.class, UUID.class}, BluetoothGattCharacteristic.class);
        if (proxy.isSupported) {
            return (BluetoothGattCharacteristic) proxy.result;
        }
        BluetoothGattCharacteristic characteristic = null;
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        Iterator<BluetoothGattCharacteristic> it = characteristics.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BluetoothGattCharacteristic c2 = it.next();
            if ((c2.getProperties() & 16) != 0 && characteristicUUID.equals(c2.getUuid())) {
                characteristic = c2;
                break;
            }
        }
        if (characteristic != null) {
            return characteristic;
        }
        for (BluetoothGattCharacteristic c3 : characteristics) {
            if ((c3.getProperties() & 32) != 0 && characteristicUUID.equals(c3.getUuid())) {
                return c3;
            }
        }
        return characteristic;
    }

    private String r(BluetoothGattCharacteristic characteristic) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{characteristic}, this, changeQuickRedirect, false, 13462, new Class[]{BluetoothGattCharacteristic.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : s(characteristic.getService().getUuid(), characteristic);
    }

    private String s(UUID serviceUUID, BluetoothGattCharacteristic characteristic) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serviceUUID, characteristic}, this, changeQuickRedirect2, false, 13463, new Class[]{UUID.class, BluetoothGattCharacteristic.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return String.valueOf(serviceUUID) + "|" + characteristic.getUuid() + "|" + characteristic.getInstanceId();
    }

    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int status, int newState) {
        Object[] objArr = {bluetoothGatt, new Integer(status), new Integer(newState)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13464, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            d.a("onConnectionStateChange  status :" + status + " state : " + newState);
            if (newState == 2) {
                this.y.set(4);
                BluetoothGatt bluetoothGatt2 = this.l;
                if (bluetoothGatt2 == null || !bluetoothGatt2.discoverServices()) {
                    d.a("remote service discovery has been stopped status = " + newState);
                    m();
                    return;
                }
                x();
                return;
            }
            synchronized (this.j) {
                d.a("Close");
                BluetoothGatt bluetoothGatt3 = this.l;
                if (bluetoothGatt3 != null) {
                    bluetoothGatt3.close();
                    this.y.set(16);
                }
                e();
                this.y.set(1);
                d.a("Peripheral#onConnectionStateChange#onDisconnect");
                y();
            }
        }
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        Class[] clsArr = {BluetoothGatt.class, BluetoothGattCharacteristic.class};
        if (!PatchProxy.proxy(new Object[]{gatt, characteristic}, this, changeQuickRedirect, false, 13465, clsArr, Void.TYPE).isSupported) {
            super.onCharacteristicChanged(gatt, characteristic);
            b commandContext = this.c.get(r(characteristic));
            if (commandContext != null) {
                byte[] value = characteristic.getValue();
                a aVar = commandContext.a;
                z(value, aVar.a, aVar.b, aVar.e);
            }
        }
    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Object[] objArr = {gatt, characteristic, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13466, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onCharacteristicRead(gatt, characteristic, status);
            d();
            if (status == 0) {
                j(characteristic.getValue());
            } else {
                h("read characteristic failed");
            }
            f();
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Object[] objArr = {gatt, characteristic, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13467, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            d();
            if (status == 0) {
                j((Object) null);
            } else {
                h("write characteristic fail");
            }
            d.a("onCharacteristicWrite newStatus : " + status);
            f();
        }
    }

    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Object[] objArr = {gatt, descriptor, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13468, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onDescriptorRead(gatt, descriptor, status);
            d();
            if (status == 0) {
                j(descriptor.getValue());
            } else {
                h("read description failed");
            }
            f();
        }
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Object[] objArr = {gatt, descriptor, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13469, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onDescriptorWrite(gatt, descriptor, status);
            d();
            if (status == 0) {
                j((Object) null);
            } else {
                h("write description failed");
            }
            f();
        }
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 13470, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onServicesDiscovered(gatt, status);
            if (status == 0) {
                List<BluetoothGattService> services = gatt.getServices();
                this.s = services;
                B(services);
                return;
            }
            d.a("Service discovery failed");
            m();
        }
    }

    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        Object[] objArr = {gatt, new Integer(rssi), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13471, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            super.onReadRemoteRssi(gatt, rssi, status);
            if (status == 0 && rssi != this.m) {
                this.m = rssi;
                A();
            }
        }
    }

    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        Object[] objArr = {gatt, new Integer(mtu), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13472, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            super.onMtuChanged(gatt, mtu, status);
            d.a("mtu changed : " + mtu);
        }
    }

    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 13473, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onReliableWriteCompleted(gatt, status);
        }
    }

    /* compiled from: Peripheral */
    public final class b {
        public static ChangeQuickRedirect changeQuickRedirect;
        public a a;
        public a.C0214a b;

        public b(a.C0214a callback, a command) {
            this.b = callback;
            this.a = command;
        }

        public void a() {
            this.a = null;
            this.b = null;
        }
    }

    /* compiled from: Peripheral */
    public final class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private e() {
        }

        /* synthetic */ e(c x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13479, new Class[0], Void.TYPE).isSupported) {
                c cVar = c.this;
                if (cVar.u && cVar.w()) {
                    BluetoothGatt bluetoothGatt = c.this.l;
                    if (bluetoothGatt != null) {
                        bluetoothGatt.readRemoteRssi();
                    }
                    c cVar2 = c.this;
                    cVar2.e.postDelayed(cVar2.g, (long) cVar2.v);
                }
            }
        }
    }

    /* compiled from: Peripheral */
    public final class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private d() {
        }

        /* synthetic */ d(c x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13478, new Class[0], Void.TYPE).isSupported) {
                synchronized (c.this.b) {
                    b commandContext = c.this.b.peek();
                    if (commandContext != null) {
                        a command = commandContext.a;
                        a.C0214a callback = commandContext.b;
                        if (c.a(c.this, commandContext)) {
                            commandContext.a = command;
                            commandContext.b = callback;
                            c.b(c.this, commandContext);
                        } else {
                            c.this.b.poll();
                            c.c(c.this);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: com.telink.bluetooth.c$c  reason: collision with other inner class name */
    /* compiled from: Peripheral */
    public final class C0216c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private C0216c() {
        }

        /* synthetic */ C0216c(c x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13477, new Class[0], Void.TYPE).isSupported) {
                synchronized (c.this.b) {
                    c.b(c.this, c.this.b.peek());
                }
            }
        }
    }
}
