package com.leedarson.serviceimpl.bledebug.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.GattRequest;
import com.telink.ble.mesh.core.ble.UUIDInfo;
import com.telink.ble.mesh.util.MeshLogger;
import com.tutk.IOTC.IOTCAPIs;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: GattConnection */
public class c extends BluetoothGattCallback {
    /* access modifiers changed from: private */
    public static long a = 300;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public ArrayBlockingQueue<g> A = new ArrayBlockingQueue<>(50);
    Handler B = new Handler(Looper.getMainLooper());
    private io.reactivex.disposables.b C;
    int D = 0;
    private final String b = "Mesh-GATT";
    private Context c;
    /* access modifiers changed from: private */
    public Handler d;
    /* access modifiers changed from: private */
    public BluetoothGatt e;
    private BluetoothDevice f;
    /* access modifiers changed from: private */
    public final Object g = new Object();
    protected final Runnable h = new e(this, (b) null);
    protected final Runnable i = new f(this, (b) null);
    protected final Runnable j = new h(this, (b) null);
    protected final Runnable k = new i(this, (b) null);
    protected final Runnable l = new C0130c(this, (b) null);
    protected final Handler m = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final Queue<GattRequest> n = new ConcurrentLinkedQueue();
    private final Object o = new Object();
    private boolean p = false;
    private AtomicBoolean q = new AtomicBoolean(false);
    private long r = 6000;
    private final int s = 3;
    /* access modifiers changed from: private */
    public int t;
    protected List<BluetoothGattService> u;
    /* access modifiers changed from: private */
    public d v;
    private byte[] w;
    private int x = 23;
    public String y = "";
    /* access modifiers changed from: private */
    public boolean z = false;

    /* compiled from: GattConnection */
    public interface d {
        void a();

        void b(UUID uuid, UUID uuid2, byte[] bArr);

        void c();

        void d(List<BluetoothGattService> list);

        void onConnected();

        void onDisconnected();
    }

    static /* synthetic */ void a(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6817, clsArr, Void.TYPE).isSupported) {
            x0.I(x1);
        }
    }

    static /* synthetic */ void b(c x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6818, new Class[]{c.class}, Void.TYPE).isSupported) {
            x0.H();
        }
    }

    static /* synthetic */ void c(c x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6819, new Class[]{c.class}, Void.TYPE).isSupported) {
            x0.F();
        }
    }

    static /* synthetic */ void d(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6820, clsArr, Void.TYPE).isSupported) {
            x0.C(x1);
        }
    }

    static /* synthetic */ boolean k(c x0, GattRequest x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6821, new Class[]{c.class, GattRequest.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.K(x1);
    }

    static /* synthetic */ void l(c x0, GattRequest x1) {
        Class[] clsArr = {c.class, GattRequest.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6822, clsArr, Void.TYPE).isSupported) {
            x0.O(x1);
        }
    }

    static /* synthetic */ boolean o(c x0, g x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6816, new Class[]{c.class, g.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.W(x1);
    }

    public c(Context context, HandlerThread thread) {
        this.c = context.getApplicationContext();
        this.d = new Handler(thread.getLooper());
    }

    public void U(d connectionCallback) {
        this.v = connectionCallback;
    }

    public void t(BluetoothDevice bluetoothDevice) {
        if (!PatchProxy.proxy(new Object[]{bluetoothDevice}, this, changeQuickRedirect, false, 6763, new Class[]{BluetoothDevice.class}, Void.TYPE).isSupported) {
            if (bluetoothDevice.equals(this.f)) {
                s();
                return;
            }
            this.f = bluetoothDevice;
            if (v("GattConnect类切换另一个ble 连接")) {
                C(" waiting for disconnect -- ");
                this.q.set(true);
                return;
            }
            C(" already disconnected -- ");
            s();
        }
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6764, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.g) {
                int i2 = this.t;
                if (i2 == 2) {
                    E();
                    List<BluetoothGattService> list = this.u;
                    if (list != null) {
                        L(list);
                    }
                } else if (i2 == 0) {
                    this.t = 1;
                    int i3 = Build.VERSION.SDK_INT;
                    if (i3 >= 26) {
                        this.e = this.f.connectGatt(this.c, false, this, 2, 1, new Handler(Looper.getMainLooper()));
                    } else if (i3 >= 23) {
                        this.e = this.f.connectGatt(this.c, false, this, 2);
                    } else {
                        this.e = this.f.connectGatt(this.c, false, this);
                    }
                    if (this.e == null) {
                        v("发起连接时返回gatt对象为空");
                        this.t = 0;
                        F();
                    } else {
                        this.d.postDelayed(this.h, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006a, code lost:
        V();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006d, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean v(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 6765(0x1a6d, float:9.48E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0025
            java.lang.Object r10 = r1.result
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            return r10
        L_0x0025:
            r1 = r9
            if (r10 == 0) goto L_0x003c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "ble mesh disconnect ,reason is :"
            r2.append(r3)
            r2.append(r10)
            java.lang.String r2 = r2.toString()
            meshsdk.MeshLog.i(r2)
        L_0x003c:
            r1.r()
            r1.z = r8
            java.lang.Object r2 = r1.g
            monitor-enter(r2)
            int r3 = r1.t     // Catch:{ all -> 0x0072 }
            if (r3 != 0) goto L_0x004a
            monitor-exit(r2)     // Catch:{ all -> 0x0072 }
            return r8
        L_0x004a:
            android.bluetooth.BluetoothGatt r4 = r1.e     // Catch:{ all -> 0x0072 }
            if (r4 == 0) goto L_0x006e
            r5 = 2
            if (r3 != r5) goto L_0x0058
            r3 = 3
            r1.t = r3     // Catch:{ all -> 0x0072 }
            r4.disconnect()     // Catch:{ all -> 0x0072 }
            goto L_0x0069
        L_0x0058:
            if (r3 != r0) goto L_0x0069
            r4.disconnect()     // Catch:{ all -> 0x0072 }
            r1.T()     // Catch:{ all -> 0x0072 }
            android.bluetooth.BluetoothGatt r0 = r1.e     // Catch:{ all -> 0x0072 }
            r0.close()     // Catch:{ all -> 0x0072 }
            r1.t = r8     // Catch:{ all -> 0x0072 }
            monitor-exit(r2)     // Catch:{ all -> 0x0072 }
            return r8
        L_0x0069:
            monitor-exit(r2)     // Catch:{ all -> 0x0072 }
            r1.V()
            return r0
        L_0x006e:
            r1.t = r8     // Catch:{ all -> 0x0072 }
            monitor-exit(r2)     // Catch:{ all -> 0x0072 }
            return r8
        L_0x0072:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0072 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.bledebug.ble.c.v(java.lang.String):boolean");
    }

    private synchronized void T() {
        BluetoothGatt bluetoothGatt;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6766, new Class[0], Void.TYPE).isSupported) {
            try {
                Method refresh = BluetoothGatt.class.getMethod("refresh", new Class[0]);
                if (!(refresh == null || (bluetoothGatt = this.e) == null)) {
                    boolean success = ((Boolean) refresh.invoke(bluetoothGatt, new Object[0])).booleanValue();
                    MeshLog.i("refreshDeviceCache, is success:  " + success);
                }
            } catch (Exception e2) {
                MeshLog.i("exception occur while refreshing device: " + e2.getMessage());
                e2.printStackTrace();
            }
        } else {
            return;
        }
        return;
    }

    private void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6767, new Class[0], Void.TYPE).isSupported) {
            this.d.removeCallbacks(this.i);
            this.d.postDelayed(this.i, CacheHandler.delayTime);
        }
    }

    private void X() {
        long delay;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6768, new Class[0], Void.TYPE).isSupported) {
            if (this.f.getBondState() == 12) {
                delay = 1600;
            } else {
                delay = 300;
            }
            this.D = 0;
            this.d.removeCallbacks(this.j);
            this.d.postDelayed(this.j, delay);
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6769, new Class[0], Void.TYPE).isSupported) {
            S();
            q();
            this.n.clear();
            this.p = false;
            this.d.removeCallbacksAndMessages((Object) null);
        }
    }

    private void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6770, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.C;
            if (bVar != null && !bVar.isDisposed()) {
                this.C.dispose();
            }
            this.d.removeCallbacks(this.h);
            d dVar = this.v;
            if (dVar != null) {
                dVar.onConnected();
            }
        }
    }

    public boolean S() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6772, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (Build.VERSION.SDK_INT >= 27) {
            return false;
        }
        if (this.e == null) {
            C("refresh error: gatt null");
            return false;
        }
        C("Device#refreshCache#prepare");
        try {
            BluetoothGatt localBluetoothGatt = this.e;
            Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (localMethod != null) {
                return ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
            }
        } catch (Exception e2) {
            C("An exception occurs while refreshing device");
        }
        return false;
    }

    private void L(List<BluetoothGattService> services) {
        if (!PatchProxy.proxy(new Object[]{services}, this, changeQuickRedirect, false, 6773, new Class[]{List.class}, Void.TYPE).isSupported) {
            C("service discover complete,remove mServicesDiscoveringTimeoutRunnable");
            this.D = 0;
            this.d.removeCallbacks(this.k);
            d dVar = this.v;
            if (dVar != null) {
                dVar.d(services);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.e.requestMtu(517);
            }
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6774, new Class[0], Void.TYPE).isSupported) {
            this.D = 0;
            this.d.removeCallbacks(this.i);
            this.d.removeCallbacks(this.j);
            this.d.removeCallbacks(this.k);
            io.reactivex.disposables.b bVar = this.C;
            if (bVar != null && !bVar.isDisposed()) {
                this.C.dispose();
            }
            this.u = null;
            if (this.q.get()) {
                this.q.set(false);
                s();
                return;
            }
            d dVar = this.v;
            if (dVar != null) {
                dVar.onDisconnected();
            }
        }
    }

    public String B() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6775, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BluetoothDevice bluetoothDevice = this.f;
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getAddress();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
        r2 = r0.n;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        if (r0.n.isEmpty() != false) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002c, code lost:
        r1 = r0.n.peek();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        if (r1 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        r3 = r0.o;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0038, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.p = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        O(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0044, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0045, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void N() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 6779(0x1a7b, float:9.5E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.lang.Object r1 = r0.o
            monitor-enter(r1)
            boolean r2 = r0.p     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0020
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            return
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            java.util.Queue<com.telink.ble.mesh.core.ble.GattRequest> r2 = r0.n
            monitor-enter(r2)
            java.util.Queue<com.telink.ble.mesh.core.ble.GattRequest> r1 = r0.n     // Catch:{ all -> 0x0046 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x0044
            java.util.Queue<com.telink.ble.mesh.core.ble.GattRequest> r1 = r0.n     // Catch:{ all -> 0x0046 }
            java.lang.Object r1 = r1.peek()     // Catch:{ all -> 0x0046 }
            com.telink.ble.mesh.core.ble.GattRequest r1 = (com.telink.ble.mesh.core.ble.GattRequest) r1     // Catch:{ all -> 0x0046 }
            if (r1 == 0) goto L_0x0044
            java.lang.Object r3 = r0.o     // Catch:{ all -> 0x0046 }
            monitor-enter(r3)     // Catch:{ all -> 0x0046 }
            r4 = 1
            r0.p = r4     // Catch:{ all -> 0x0041 }
            monitor-exit(r3)     // Catch:{ all -> 0x0041 }
            r0.O(r1)     // Catch:{ all -> 0x0046 }
            goto L_0x0044
        L_0x0041:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0041 }
            throw r4     // Catch:{ all -> 0x0046 }
        L_0x0044:
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
            return
        L_0x0046:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
            throw r1
        L_0x0049:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.bledebug.ble.c.N():void");
    }

    private void O(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 6780, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            GattRequest.RequestType requestType = gattRequest.d;
            D("process request : " + gattRequest.toString(), 0);
            switch (b.a[requestType.ordinal()]) {
                case 1:
                    M();
                    Q(gattRequest);
                    return;
                case 2:
                    M();
                    Y(gattRequest, 2);
                    return;
                case 3:
                    M();
                    Y(gattRequest, 1);
                    return;
                case 4:
                    M();
                    R(gattRequest);
                    return;
                case 5:
                    M();
                    Z(gattRequest);
                    return;
                case 6:
                    w(gattRequest);
                    return;
                case 7:
                    u(gattRequest);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: GattConnection */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[GattRequest.RequestType.values().length];
            a = iArr;
            try {
                iArr[GattRequest.RequestType.READ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[GattRequest.RequestType.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[GattRequest.RequestType.WRITE_NO_RESPONSE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[GattRequest.RequestType.READ_DESCRIPTOR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[GattRequest.RequestType.WRITE_DESCRIPTOR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[GattRequest.RequestType.ENABLE_NOTIFY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[GattRequest.RequestType.DISABLE_NOTIFY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6781, new Class[0], Void.TYPE).isSupported) {
            if (this.r > 0) {
                this.m.removeCallbacksAndMessages((Object) null);
                this.m.postDelayed(this.l, this.r);
            }
        }
    }

    private void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6782, new Class[0], Void.TYPE).isSupported) {
            this.m.removeCallbacksAndMessages((Object) null);
        }
    }

    private void G(BluetoothGattCharacteristic characteristic, byte[] data) {
        if (!PatchProxy.proxy(new Object[]{characteristic, data}, this, changeQuickRedirect, false, 6783, new Class[]{BluetoothGattCharacteristic.class, byte[].class}, Void.TYPE).isSupported) {
            UUID charUUID = characteristic.getUuid();
            UUID serviceUUID = characteristic.getService().getUuid();
            D("on notify -- " + com.leedarson.base.utils.e.b(data, ":") + "\ncharUUID:" + charUUID + "\nserviceUUID" + serviceUUID, 0);
            if (!charUUID.equals(UUIDInfo.f) && !charUUID.equals(UUIDInfo.i)) {
                d dVar = this.v;
                if (dVar != null) {
                    dVar.b(serviceUUID, charUUID, data);
                }
            } else if (data == null || data.length == 0) {
                D("empty packet received!", 3);
            } else {
                byte[] completePacket = A(data);
                if (completePacket == null) {
                    D("waiting for segment pkt", 0);
                    return;
                }
                D("completed notification data: " + com.leedarson.base.utils.e.b(completePacket, ":"), 1);
                if (completePacket.length <= 1) {
                    D("complete notification length err", 3);
                    return;
                }
                d dVar2 = this.v;
                if (dVar2 != null) {
                    dVar2.b(serviceUUID, charUUID, completePacket);
                }
            }
        }
    }

    private byte[] A(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6784, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte sar = (byte) (data[0] & 192);
        switch (sar) {
            case Byte.MIN_VALUE:
            case IOTCAPIs.IOTC_ER_DEVICE_IS_SLEEP /*-64*/:
                byte[] bArr = this.w;
                if (bArr == null) {
                    D("segment first pkt no found", 3);
                    break;
                } else if ((bArr[0] & 63) != (data[0] & 63) || data.length <= 1) {
                    D("other segment ", 3);
                    break;
                } else {
                    byte[] tempBuffer = new byte[((bArr.length + data.length) - 1)];
                    System.arraycopy(bArr, 0, tempBuffer, 0, bArr.length);
                    System.arraycopy(data, 1, tempBuffer, this.w.length, data.length - 1);
                    if (sar == Byte.MIN_VALUE) {
                        this.w = tempBuffer;
                        return null;
                    }
                    this.w = null;
                    return tempBuffer;
                }
                break;
            case 0:
                return data;
            case 64:
                data[0] = (byte) (data[0] & 63);
                this.w = data;
                return null;
        }
        return null;
    }

    private void I(String errorMsg) {
        GattRequest request;
        GattRequest.Callback requestCallback;
        if (!PatchProxy.proxy(new Object[]{errorMsg}, this, changeQuickRedirect, false, 6785, new Class[]{String.class}, Void.TYPE).isSupported) {
            C("request error: " + errorMsg);
            synchronized (this.n) {
                request = this.n.poll();
            }
            if (request != null && (requestCallback = request.h) != null) {
                requestCallback.a(request, errorMsg);
            }
        }
    }

    private boolean K(@NonNull GattRequest gattRequest) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 6786, new Class[]{GattRequest.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        D("gatt request timeout", 0);
        GattRequest.Callback requestCallback = gattRequest.h;
        if (requestCallback != null) {
            return requestCallback.c(gattRequest);
        }
        return false;
    }

    private void J(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6787, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            GattRequest gattRequest = this.n.poll();
            if (gattRequest != null) {
                D("request success: tag - " + gattRequest.f, 0);
                GattRequest.Callback requestCallback = gattRequest.h;
                if (requestCallback != null) {
                    requestCallback.b(gattRequest, data);
                    return;
                }
                return;
            }
            C("request not found");
        }
    }

    private void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6788, new Class[0], Void.TYPE).isSupported) {
            D("gatt request completed", 0);
            synchronized (this.o) {
                if (this.p) {
                    this.p = false;
                }
            }
            N();
        }
    }

    public void w(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 6789, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = x(service, characteristicUUID);
                if (characteristic == null) {
                    success = false;
                    errorMsg = "no characteristic";
                } else if (!this.e.setCharacteristicNotification(characteristic, true)) {
                    success = false;
                    errorMsg = "enable notification error";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                I("enable notification error: " + errorMsg + " - " + characteristicUUID);
            } else {
                J((byte[]) null);
            }
            H();
        }
    }

    private void u(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 6790, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = x(service, characteristicUUID);
                if (characteristic == null) {
                    success = false;
                    errorMsg = "no characteristic";
                } else if (!this.e.setCharacteristicNotification(characteristic, false)) {
                    success = false;
                    errorMsg = "disable notification error";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                String errInfo = "disable notification error: " + errorMsg + " - " + characteristicUUID;
                C(errInfo);
                I(errInfo);
            } else {
                J((byte[]) null);
            }
            H();
        }
    }

    private void R(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 6791, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            UUID descriptorUUID = gattRequest.c;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUUID);
                if (characteristic != null) {
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUUID);
                    if (descriptor == null) {
                        success = false;
                        errorMsg = "read descriptor error - descriptor not found";
                    } else if (!this.e.readDescriptor(descriptor)) {
                        success = false;
                        errorMsg = "read descriptor error";
                    }
                } else {
                    success = false;
                    errorMsg = "read characteristic error - characteristic not found";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                I(errorMsg);
                H();
            }
        }
    }

    private void Z(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 6792, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            UUID descriptorUUID = gattRequest.c;
            byte[] data = gattRequest.e;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUUID);
                if (characteristic != null) {
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUUID);
                    if (descriptor != null) {
                        descriptor.setValue(data);
                        if (!this.e.writeDescriptor(descriptor)) {
                            success = false;
                            errorMsg = "write characteristic error writeDescriptor";
                        }
                    } else {
                        success = false;
                        errorMsg = "no descriptor";
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
                I(errorMsg);
                H();
            }
        }
    }

    private void Q(GattRequest request) {
        if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 6793, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = request.a;
            UUID characteristicUUID = request.b;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUUID);
                if (characteristic == null) {
                    success = false;
                    errorMsg = "read characteristic error - characteristic not found";
                } else if (!this.e.readCharacteristic(characteristic)) {
                    success = false;
                    errorMsg = "read characteristic error";
                }
            } else {
                success = false;
                errorMsg = "service is not offered by the remote device";
            }
            if (!success) {
                I(errorMsg);
                H();
            }
        }
    }

    /* compiled from: GattConnection */
    public class g {
        public GattRequest a;
        public long b = 0;
        BluetoothGattCharacteristic c;

        g() {
        }
    }

    public void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6794, new Class[0], Void.TYPE).isSupported) {
            if (!this.z) {
                this.z = true;
                new Thread(new a()).start();
            }
        }
    }

    /* compiled from: GattConnection */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6826, new Class[0], Void.TYPE).isSupported) {
                while (c.this.z) {
                    try {
                        g itemWrapItem = (g) c.this.A.take();
                        while (true) {
                            if (c.o(c.this, itemWrapItem)) {
                                break;
                            }
                            Thread.sleep(100);
                            if (System.currentTimeMillis() - itemWrapItem.b > c.a) {
                                c.this.B.post(new C0129a());
                                break;
                            }
                        }
                    } catch (Exception e) {
                        MeshLog.e("processWriteByBluetoothGatt.exception  e=" + e.toString());
                    }
                }
            }
        }

        /* renamed from: com.leedarson.serviceimpl.bledebug.ble.c$a$a  reason: collision with other inner class name */
        /* compiled from: GattConnection */
        public class C0129a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0129a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6827, new Class[0], Void.TYPE).isSupported) {
                    c.a(c.this, "write characteristic error writeCharacteristic");
                    c.b(c.this);
                }
            }
        }
    }

    private boolean W(g itemWrapItem) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{itemWrapItem}, this, changeQuickRedirect, false, 6795, new Class[]{g.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.e.writeCharacteristic(itemWrapItem.c);
    }

    private void Y(GattRequest gattRequest, int writeType) {
        String errorMsg;
        boolean success;
        if (!PatchProxy.proxy(new Object[]{gattRequest, new Integer(writeType)}, this, changeQuickRedirect, false, 6796, new Class[]{GattRequest.class, Integer.TYPE}, Void.TYPE).isSupported) {
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            byte[] data = gattRequest.e;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = y(service, characteristicUUID, writeType);
                if (characteristic != null) {
                    characteristic.setValue(data);
                    characteristic.setWriteType(writeType);
                    g gattRequestItemWrapItem = new g();
                    gattRequestItemWrapItem.b = System.currentTimeMillis();
                    gattRequestItemWrapItem.c = characteristic;
                    gattRequestItemWrapItem.a = gattRequest;
                    try {
                        this.A.put(gattRequestItemWrapItem);
                        P();
                        return;
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                        MeshLog.e("  queueItems.put(gattRequestItemWrapItem).exception  ex=" + e2.toString());
                        return;
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
                I(errorMsg);
                H();
            }
        }
    }

    private BluetoothGattCharacteristic y(BluetoothGattService service, UUID characteristicUUID, int writeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{service, characteristicUUID, new Integer(writeType)}, this, changeQuickRedirect, false, 6799, new Class[]{BluetoothGattService.class, UUID.class, Integer.TYPE}, BluetoothGattCharacteristic.class);
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

    private BluetoothGattCharacteristic x(BluetoothGattService service, UUID characteristicUUID) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{service, characteristicUUID}, this, changeQuickRedirect2, false, 6800, new Class[]{BluetoothGattService.class, UUID.class}, BluetoothGattCharacteristic.class);
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

    public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        Object[] objArr = {gatt, new Integer(txPhy), new Integer(rxPhy), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {BluetoothGatt.class, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6801, clsArr, Void.TYPE).isSupported) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }
    }

    public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        Object[] objArr = {gatt, new Integer(txPhy), new Integer(rxPhy), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {BluetoothGatt.class, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6802, clsArr, Void.TYPE).isSupported) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }
    }

    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int status, int newState) {
        Object[] objArr = {bluetoothGatt, new Integer(status), new Integer(newState)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6803, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            C("SUFUN.onConnectionStateChange  status :" + status + " state : " + newState);
            StringBuilder sb = new StringBuilder();
            sb.append("SUFUN.GattConnectionChange");
            sb.append(newState == 2 ? "设备连接成功了" : "onConnectionStateChange 设备断开 or 连接失败设备信息");
            sb.append(B());
            sb.append("  newState=");
            sb.append(newState);
            sb.append("   status=");
            sb.append(status);
            C(sb.toString());
            if (newState == 2) {
                synchronized (this.g) {
                    this.t = 2;
                }
                E();
                X();
                return;
            }
            synchronized (this.g) {
                C("Close");
                BluetoothGatt bluetoothGatt2 = this.e;
                if (bluetoothGatt2 != null) {
                    bluetoothGatt2.close();
                }
                r();
                this.t = 0;
                F();
            }
        }
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 6804, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (status == 0) {
                List<BluetoothGattService> services = gatt.getServices();
                this.u = services;
                L(services);
                return;
            }
            C("Service discovery failed");
            v("ble特征值Service 搜索失败");
        }
    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Object[] objArr = {gatt, characteristic, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6805, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onCharacteristicRead(gatt, characteristic, status);
            q();
            if (z(status)) {
                J(characteristic.getValue());
            } else {
                I("read characteristic failed");
            }
            H();
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Object[] objArr = {gatt, characteristic, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6806, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            q();
            if (z(status)) {
                J((byte[]) null);
            } else {
                I("write characteristic fail");
            }
            H();
        }
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        Class[] clsArr = {BluetoothGatt.class, BluetoothGattCharacteristic.class};
        if (!PatchProxy.proxy(new Object[]{gatt, characteristic}, this, changeQuickRedirect, false, 6807, clsArr, Void.TYPE).isSupported) {
            super.onCharacteristicChanged(gatt, characteristic);
            G(characteristic, characteristic.getValue());
        }
    }

    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Object[] objArr = {gatt, descriptor, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6808, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onDescriptorRead(gatt, descriptor, status);
            q();
            if (z(status)) {
                J(descriptor.getValue());
            } else {
                I("read descriptor fail");
            }
            H();
        }
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Object[] objArr = {gatt, descriptor, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6809, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onDescriptorWrite(gatt, descriptor, status);
            q();
            if (z(status)) {
                J((byte[]) null);
            } else {
                I("write descriptor fail");
            }
            H();
        }
    }

    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 6810, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onReliableWriteCompleted(gatt, status);
        }
    }

    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        Object[] objArr = {gatt, new Integer(rssi), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6811, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }
    }

    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        Object[] objArr = {gatt, new Integer(mtu), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6812, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            C("onMtuChanged: " + mtu);
            timber.log.a.i("SUFUN.onMtuChanged.BleDebug.GattConnection   --> mtu=" + mtu + "   status=" + status, new Object[0]);
            super.onMtuChanged(gatt, mtu, status);
            if (z(status)) {
                this.x = mtu;
            }
        }
    }

    /* compiled from: GattConnection */
    public final class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private e() {
        }

        /* synthetic */ e(c x0, b x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6829, new Class[0], Void.TYPE).isSupported) {
                MeshLog.i("SUFUN  mac=" + c.this.y + "   检测到超时/正在尝试关闭释放原有的连接");
                if (!c.this.v("连接超时")) {
                    c.c(c.this);
                }
            }
        }
    }

    /* compiled from: GattConnection */
    public final class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private f() {
        }

        /* synthetic */ f(c x0, b x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6830, new Class[0], Void.TYPE).isSupported) {
                c.d(c.this, "disconnection timeout");
                synchronized (c.this.g) {
                    if (c.this.e != null) {
                        c.this.e.disconnect();
                        c.this.e.close();
                    }
                    int unused = c.this.t = 0;
                    c.c(c.this);
                }
            }
        }
    }

    /* compiled from: GattConnection */
    public final class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private h() {
        }

        /* synthetic */ h(c x0, b x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6831, new Class[0], Void.TYPE).isSupported) {
                if (c.this.e == null || !c.this.e.discoverServices()) {
                    c.this.v("serviceDiscover 失败");
                    return;
                }
                c.d(c.this, "start services discovering");
                if (c.this.v != null) {
                    c.this.v.a();
                }
                c.this.d.removeCallbacks(c.this.k);
                c.this.d.postDelayed(c.this.k, GroupCtrlAdapter.RETRY_TIMEOUT);
            }
        }
    }

    /* compiled from: GattConnection */
    public final class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private i() {
        }

        /* synthetic */ i(c x0, b x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6832, new Class[0], Void.TYPE).isSupported) {
                c cVar = c.this;
                c.d(cVar, "serviceDiscover 超时 retry:" + c.this.D);
                c cVar2 = c.this;
                int i = cVar2.D;
                if (i >= 2) {
                    cVar2.v("serviceDiscover 超时，达到重试次数上限");
                    return;
                }
                cVar2.D = i + 1;
                cVar2.d.removeCallbacks(c.this.j);
                c.this.d.post(c.this.j);
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.bledebug.ble.c$c  reason: collision with other inner class name */
    /* compiled from: GattConnection */
    public final class C0130c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private C0130c() {
        }

        /* synthetic */ C0130c(c x0, b x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6828, new Class[0], Void.TYPE).isSupported) {
                synchronized (c.this.n) {
                    GattRequest gattRequest = (GattRequest) c.this.n.peek();
                    if (gattRequest != null) {
                        if (c.k(c.this, gattRequest)) {
                            c.l(c.this, gattRequest);
                        } else {
                            gattRequest.a();
                            c.this.n.poll();
                            c.b(c.this);
                        }
                    }
                }
            }
        }
    }

    private boolean z(int status) {
        return status == 0;
    }

    private void C(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 6813, new Class[]{String.class}, Void.TYPE).isSupported) {
            D(logMessage + "  macAddress=" + this.y, 2);
        }
    }

    private void D(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 6814, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage, "Mesh-GATT", level);
        }
    }
}
