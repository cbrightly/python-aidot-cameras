package com.telink.ble.mesh.core.ble;

import android.bluetooth.BluetoothAdapter;
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
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceimpl.BleMeshServiceImpl;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceimpl.elkstrays.b;
import com.leedarson.serviceimpl.reporters.AddDeviceStepBean;
import com.leedarson.serviceimpl.reporters.AutoConnectDeviceStepBean;
import com.leedarson.serviceimpl.reporters.c;
import com.leedarson.serviceimpl.reporters.e;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.BleMeshService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.GattRequest;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.MeshService;
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
import meshsdk.MeshLogNew;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.util.MeshConstants;

public class GattConnection extends BluetoothGattCallback {
    /* access modifiers changed from: private */
    public static long a = 300;
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A = 23;
    public String B = "";
    public MeshController C;
    private BleMeshServiceImpl D;
    private BleC075Service E;
    int F = 0;
    /* access modifiers changed from: private */
    public boolean G = false;
    /* access modifiers changed from: private */
    public ArrayBlockingQueue<GattRequestItemWrapItem> H = new ArrayBlockingQueue<>(50);
    Handler I = new Handler(Looper.getMainLooper());
    int J = 0;
    private final String b = "Mesh-GATT";
    private Context c;
    /* access modifiers changed from: private */
    public Handler d;
    /* access modifiers changed from: private */
    public BluetoothGatt e;
    private BluetoothDevice f;
    private int g;
    /* access modifiers changed from: private */
    public final Object h = new Object();
    protected final Runnable i = new RequestMtuTimeoutRunnable();
    protected final Runnable j = new ConnectionTimeoutRunnable();
    protected final Runnable k = new DisconnectionTimeoutRunnable();
    protected final Runnable l = new ServicesDiscoveringRunnable();
    protected final Runnable m = new ServicesDiscoveringTimeoutRunnable();
    protected final Runnable n = new CommandTimeoutRunnable();
    protected final Handler o = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final Queue<GattRequest> p = new ConcurrentLinkedQueue();
    private final Object q = new Object();
    private boolean r = false;
    private AtomicBoolean s = new AtomicBoolean(false);
    private long t = 6000;
    private final int u = 3;
    private String v;
    /* access modifiers changed from: private */
    public int w;
    protected List<BluetoothGattService> x;
    /* access modifiers changed from: private */
    public ConnectionCallback y;
    private byte[] z;

    public interface ConnectionCallback {
        void a();

        void b(UUID uuid, UUID uuid2, byte[] bArr);

        void c(String str);

        void d();

        void e(boolean z, int i, int i2, List<BluetoothGattService> list);

        void f(boolean z);

        void g(String str, String str2, int i);
    }

    static /* synthetic */ boolean b(GattConnection x0, GattRequestItemWrapItem x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12343, new Class[]{GattConnection.class, GattRequestItemWrapItem.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.t0(x1);
    }

    static /* synthetic */ void d(GattConnection x0, String x1) {
        Class[] clsArr = {GattConnection.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12344, clsArr, Void.TYPE).isSupported) {
            x0.Y(x1);
        }
    }

    static /* synthetic */ void e(GattConnection x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12345, new Class[]{GattConnection.class}, Void.TYPE).isSupported) {
            x0.X();
        }
    }

    static /* synthetic */ void f(GattConnection x0, String x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12346, new Class[]{GattConnection.class, String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.V(x1, x2);
        }
    }

    static /* synthetic */ void g(GattConnection x0, String x1) {
        Class[] clsArr = {GattConnection.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12347, clsArr, Void.TYPE).isSupported) {
            x0.S(x1);
        }
    }

    static /* synthetic */ boolean m(GattConnection x0, GattRequest x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12348, new Class[]{GattConnection.class, GattRequest.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.a0(x1);
    }

    static /* synthetic */ void n(GattConnection x0, GattRequest x1) {
        Class[] clsArr = {GattConnection.class, GattRequest.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12349, clsArr, Void.TYPE).isSupported) {
            x0.e0(x1);
        }
    }

    static /* synthetic */ void q(GattConnection x0, String x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12342, new Class[]{GattConnection.class, String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.T(x1, x2);
        }
    }

    public final class RequestMtuTimeoutRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private RequestMtuTimeoutRunnable() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12358, new Class[0], Void.TYPE).isSupported) {
                if (GattConnection.this.y != null) {
                    GattConnection.this.d.removeCallbacks(GattConnection.this.i);
                    if (GattConnection.this.y != null) {
                        GattConnection.this.y.e(true, -1, 256, GattConnection.this.x);
                    }
                }
            }
        }
    }

    private GattConnection() {
    }

    public GattConnection(Context context, HandlerThread thread, MeshController meshController) {
        this.c = context.getApplicationContext();
        this.d = new Handler(thread.getLooper());
        this.D = (BleMeshServiceImpl) a.c().g(BleMeshService.class);
        this.C = meshController;
        this.E = (BleC075Service) a.c().g(BleC075Service.class);
    }

    public void r0(ConnectionCallback connectionCallback) {
        this.y = connectionCallback;
    }

    public boolean N() {
        boolean z2;
        synchronized (this.h) {
            z2 = this.w == 2;
        }
        return z2;
    }

    public boolean O() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12271, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : P(false);
    }

    public boolean P(boolean real) {
        Object[] objArr = {new Byte(real ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12272, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!N()) {
            return false;
        }
        if (L(real) != null) {
            return true;
        }
        return false;
    }

    public void h0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12274, new Class[0], Void.TYPE).isSupported) {
            C();
            w0("proxyInit");
        }
    }

    public void g0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12275, new Class[0], Void.TYPE).isSupported) {
            C();
            v0();
            w0("provisionInit");
        }
    }

    public void m0(byte b2, byte[] bArr, String str) {
        byte[] pkt;
        byte oct0;
        if (!PatchProxy.proxy(new Object[]{new Byte(b2), bArr, str}, this, changeQuickRedirect, false, 12276, new Class[]{Byte.TYPE, byte[].class, String.class}, Void.TYPE).isSupported) {
            byte[] data = bArr;
            byte type = b2;
            String extra = str;
            int mtu = this.A - 3;
            boolean isProvisioningPdu = type == 3;
            if (data.length > mtu - 1) {
                int pktNum = (int) Math.ceil(((double) data.length) / ((double) (mtu - 1)));
                for (int i2 = 0; i2 < pktNum; i2++) {
                    if (i2 != pktNum - 1) {
                        if (i2 == 0) {
                            oct0 = (byte) (type | 64);
                        } else {
                            oct0 = (byte) (type | OTACommand.RESP_ID_VERSION);
                        }
                        pkt = new byte[mtu];
                        pkt[0] = oct0;
                        System.arraycopy(data, (mtu - 1) * i2, pkt, 1, mtu - 1);
                    } else {
                        int restSize = data.length - ((mtu - 1) * i2);
                        byte[] pkt2 = new byte[(restSize + 1)];
                        pkt2[0] = (byte) (type | -64);
                        System.arraycopy(data, (mtu - 1) * i2, pkt2, 1, restSize);
                        pkt = pkt2;
                    }
                    if (isProvisioningPdu) {
                        S("sendPvRequest 分包发送");
                        o0(pkt);
                    } else {
                        S("sendProxyRequest 分包发送");
                        n0(pkt, extra);
                    }
                }
                return;
            }
            byte[] proxyData = new byte[(data.length + 1)];
            proxyData[0] = type;
            System.arraycopy(data, 0, proxyData, 1, data.length);
            if (isProvisioningPdu) {
                o0(proxyData);
            } else {
                n0(proxyData, extra);
            }
        }
    }

    public void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12277, new Class[0], Void.TYPE).isSupported) {
            S("write ccc in provision service");
            GattRequest cmd = GattRequest.b();
            BluetoothGattService service = K();
            if (service != null) {
                cmd.a = service.getUuid();
                cmd.b = UUIDInfo.f;
                cmd.c = UUIDInfo.l;
                cmd.e = new byte[]{1, 0};
                cmd.d = GattRequest.RequestType.WRITE_DESCRIPTOR;
                p0(cmd);
            }
        }
    }

    public void w0(String tag) {
        if (!PatchProxy.proxy(new Object[]{tag}, this, changeQuickRedirect, false, 12278, new Class[]{String.class}, Void.TYPE).isSupported) {
            S("write ccc in proxy service");
            GattRequest cmd = GattRequest.b();
            BluetoothGattService service = L(false);
            if (service != null) {
                cmd.a = service.getUuid();
                cmd.b = UUIDInfo.i;
                cmd.c = UUIDInfo.l;
                cmd.e = new byte[]{1, 0};
                cmd.d = GattRequest.RequestType.WRITE_DESCRIPTOR;
                cmd.f = tag;
                p0(cmd);
            }
        }
    }

    public boolean D() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12279, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        GattRequest cmd = GattRequest.b();
        if (!N() || !u()) {
            return false;
        }
        cmd.a = UUIDInfo.j;
        cmd.b = UUIDInfo.k;
        cmd.e = new byte[]{1};
        cmd.d = GattRequest.RequestType.WRITE_NO_RESPONSE;
        p0(cmd);
        return true;
    }

    private void o0(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 12280, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            GattRequest cmd = GattRequest.b();
            cmd.b = UUIDInfo.e;
            BluetoothGattService service = K();
            if (service == null) {
                MeshLog.e("sendPvRequest fail when gattService is null:" + J());
                com.leedarson.log.elk.a o2 = com.leedarson.log.elk.a.y(this).o("info");
                o2.p("sendPvRequest fail when gattService is null:" + J());
                return;
            }
            cmd.a = service.getUuid();
            cmd.e = (byte[]) data.clone();
            cmd.d = GattRequest.RequestType.WRITE_NO_RESPONSE;
            p0(cmd);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ac A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ad  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void n0(byte[] r26, java.lang.String r27) {
        /*
            r25 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r26
            r2 = 1
            r1[r2] = r27
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r8] = r0
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 12281(0x2ff9, float:1.721E-41)
            r2 = r25
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0025
            return
        L_0x0025:
            r1 = r25
            r2 = r27
            r3 = r26
            if (r2 != 0) goto L_0x0030
            java.lang.String r0 = ""
            goto L_0x0031
        L_0x0030:
            r0 = r2
        L_0x0031:
            r4 = r0
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            r9 = 0
            r7 = 0
            r11 = 0
            java.lang.String r12 = ""
            r13 = 0
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x008d }
            if (r0 != 0) goto L_0x0082
            com.telink.ble.mesh.entity.MsgExtra r0 = com.telink.ble.mesh.entity.MsgExtra.a(r2)     // Catch:{ Exception -> 0x008d }
            if (r0 == 0) goto L_0x007f
            java.lang.String r15 = r0.c     // Catch:{ Exception -> 0x008d }
            r5 = r15
            java.lang.String r15 = r0.b     // Catch:{ Exception -> 0x007a }
            r4 = r15
            java.lang.String r15 = r0.d     // Catch:{ Exception -> 0x0074 }
            r6 = r15
            r26 = r9
            long r8 = r0.e     // Catch:{ Exception -> 0x006e }
            r9 = r8
            int r8 = r0.f     // Catch:{ Exception -> 0x006a }
            r11 = r8
            java.lang.String r8 = r0.g     // Catch:{ Exception -> 0x006a }
            r12 = r8
            r15 = r4
            r8 = r5
            long r4 = r0.h     // Catch:{ Exception -> 0x0066 }
            r13 = r4
            r5 = r8
            r4 = r15
            goto L_0x0086
        L_0x0066:
            r0 = move-exception
            r5 = r8
            r4 = r15
            goto L_0x0090
        L_0x006a:
            r0 = move-exception
            r15 = r4
            r8 = r5
            goto L_0x0090
        L_0x006e:
            r0 = move-exception
            r15 = r4
            r8 = r5
            r9 = r26
            goto L_0x0090
        L_0x0074:
            r0 = move-exception
            r15 = r4
            r8 = r5
            r26 = r9
            goto L_0x0090
        L_0x007a:
            r0 = move-exception
            r8 = r5
            r26 = r9
            goto L_0x0090
        L_0x007f:
            r26 = r9
            goto L_0x0084
        L_0x0082:
            r26 = r9
        L_0x0084:
            r9 = r26
        L_0x0086:
            r18 = r9
            r0 = r11
            r8 = r12
            r20 = r13
            goto L_0x0096
        L_0x008d:
            r0 = move-exception
            r26 = r9
        L_0x0090:
            r18 = r9
            r0 = r11
            r8 = r12
            r20 = r13
        L_0x0096:
            r11 = r4
            r16 = r5
            r13 = r6
            r22 = r18
            r24 = r0
            r12 = r8
            r14 = r20
            com.telink.ble.mesh.core.ble.GattRequest r10 = com.telink.ble.mesh.core.ble.GattRequest.b()
            r9 = 0
            android.bluetooth.BluetoothGattService r17 = r1.L(r9)
            if (r17 != 0) goto L_0x00ad
            return
        L_0x00ad:
            java.util.UUID r9 = r17.getUuid()
            r10.a = r9
            java.util.UUID r9 = com.telink.ble.mesh.core.ble.UUIDInfo.h
            r10.b = r9
            java.lang.Object r9 = r3.clone()
            byte[] r9 = (byte[]) r9
            r10.e = r9
            com.telink.ble.mesh.core.ble.GattRequest$RequestType r9 = com.telink.ble.mesh.core.ble.GattRequest.RequestType.WRITE_NO_RESPONSE
            r10.d = r9
            r10.f = r2
            com.telink.ble.mesh.core.ble.GattConnection$1 r9 = new com.telink.ble.mesh.core.ble.GattConnection$1
            r26 = r9
            r27 = r0
            r0 = r10
            r10 = r1
            r9.<init>(r11, r12, r13, r14, r16)
            r0.h = r9
            com.telink.ble.mesh.foundation.MeshService r9 = com.telink.ble.mesh.foundation.MeshService.k()
            boolean r9 = r9.p()
            if (r9 == 0) goto L_0x00e0
            r1.p0(r0)
            goto L_0x00e3
        L_0x00e0:
            r1.q0(r0)
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.ble.GattConnection.n0(byte[], java.lang.String):void");
    }

    private void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12282, new Class[0], Void.TYPE).isSupported) {
            BluetoothGattService provisionService = K();
            if (provisionService != null) {
                GattRequest gattRequest = GattRequest.b();
                gattRequest.d = GattRequest.RequestType.ENABLE_NOTIFY;
                gattRequest.a = provisionService.getUuid();
                gattRequest.b = UUIDInfo.f;
                p0(gattRequest);
            }
            BluetoothGattService proxyService = L(false);
            if (proxyService != null) {
                GattRequest gattRequest2 = GattRequest.b();
                gattRequest2.d = GattRequest.RequestType.ENABLE_NOTIFY;
                gattRequest2.a = proxyService.getUuid();
                gattRequest2.b = UUIDInfo.i;
                p0(gattRequest2);
            }
            GattRequest gattRequest3 = GattRequest.b();
            gattRequest3.d = GattRequest.RequestType.ENABLE_NOTIFY;
            gattRequest3.a = UUIDInfo.j;
            gattRequest3.b = UUIDInfo.k;
            p0(gattRequest3);
        }
    }

    private boolean u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12283, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        List<BluetoothGattService> list = this.x;
        if (list == null) {
            return false;
        }
        for (BluetoothGattService service : list) {
            if (service.getUuid().equals(UUIDInfo.j)) {
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    if (characteristic.getUuid().equals(UUIDInfo.k)) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    private BluetoothGattService K() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12284, new Class[0], BluetoothGattService.class);
        if (proxy.isSupported) {
            return (BluetoothGattService) proxy.result;
        }
        List<BluetoothGattService> services = this.x;
        if (services == null) {
            return null;
        }
        for (BluetoothGattService service : services) {
            if (service.getUuid().equals(UUIDInfo.d) || service.getUuid().equals(UUIDInfo.c)) {
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    if (characteristic.getUuid().equals(UUIDInfo.e)) {
                        return service;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private BluetoothGattService L(boolean real) {
        Object[] objArr = {new Byte(real ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12285, new Class[]{Boolean.TYPE}, BluetoothGattService.class);
        if (proxy.isSupported) {
            return (BluetoothGattService) proxy.result;
        }
        List<BluetoothGattService> services = this.x;
        if (services == null) {
            return null;
        }
        for (BluetoothGattService service : services) {
            if (service.getUuid().equals(UUIDInfo.g) || (!real && service.getUuid().equals(UUIDInfo.c))) {
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    if (characteristic.getUuid().equals(UUIDInfo.h)) {
                        return service;
                    }
                }
                continue;
            }
        }
        return null;
    }

    public void x(BluetoothDevice bluetoothDevice, int rssi) {
        if (!PatchProxy.proxy(new Object[]{bluetoothDevice, new Integer(rssi)}, this, changeQuickRedirect, false, 12286, new Class[]{BluetoothDevice.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (bluetoothDevice.equals(this.f)) {
                this.f = bluetoothDevice;
                this.g = rssi;
                w();
                return;
            }
            this.f = bluetoothDevice;
            this.g = rssi;
            if (z("GattConnect类切换另一个ble 连接")) {
                S(" waiting for disconnect -- ");
                this.s.set(true);
                this.C.d0(new AddDeviceStepBean("等待已连接上的bleGatter断开"));
                return;
            }
            S(" already disconnected -- ");
            w();
        }
    }

    public void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12287, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.h) {
                int i2 = this.w;
                if (i2 == 2) {
                    BluetoothDevice bluetoothDevice = this.f;
                    U(bluetoothDevice != null ? bluetoothDevice.getAddress() : "");
                    List<BluetoothGattService> list = this.x;
                    if (list != null) {
                        b0(list);
                    }
                } else if (i2 == 0) {
                    this.w = 1;
                    BleC075Service bleC075Service = this.E;
                    if (bleC075Service != null) {
                        this.v = bleC075Service.getConnectedDevicesV2(BaseApplication.b());
                    }
                    s(this.f, this.g);
                    MeshController meshController = this.C;
                    meshController.K1("调用connectGatt，发起连接:" + this.f.getAddress() + ",rssi:" + this.g);
                    MeshController meshController2 = this.C;
                    StringBuilder sb = new StringBuilder();
                    sb.append("调用connectGatt，发起连接:");
                    sb.append(this.f.getAddress());
                    meshController2.J1(sb.toString());
                    BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(this.f.getAddress());
                    this.f = remoteDevice;
                    if (Build.VERSION.SDK_INT >= 23) {
                        this.e = remoteDevice.connectGatt(this.c, false, this, 2);
                    } else {
                        this.e = remoteDevice.connectGatt(this.c, false, this);
                    }
                    if (this.e == null) {
                        z("发起连接时返回gatt对象为空");
                        this.w = 0;
                        V("发起gattConnection时gatt对象为空,非法调用", -1);
                    } else {
                        this.d.postDelayed(this.j, 10000);
                    }
                }
            }
        }
    }

    private void s(BluetoothDevice device, int rssi) {
        if (!PatchProxy.proxy(new Object[]{device, new Integer(rssi)}, this, changeQuickRedirect, false, 12288, new Class[]{BluetoothDevice.class, Integer.TYPE}, Void.TYPE).isSupported) {
            String step = "开始ble连接(" + device.getAddress() + "," + this.v + ")";
            this.C.c0(new AutoConnectDeviceStepBean(step, e.CODE_SUCCESS.getCode()).setStepStartBleConnect(true).setRssi(rssi).setMac(device.getAddress()), MeshConstants.AC_STATE_DEV_CONNECTING);
            this.C.d0(new AddDeviceStepBean(step).setStepStartBleConnect(true).setRssi(rssi));
        }
    }

    public boolean z(String reason) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{reason}, this, changeQuickRedirect, false, 12289, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : A(reason, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x01d9, code lost:
        s0();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x01dc, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean A(java.lang.String r11, boolean r12) {
        /*
            r10 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r12)
            r9 = 1
            r1[r9] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r6[r9] = r7
            r4 = 0
            r5 = 12290(0x3002, float:1.7222E-41)
            r2 = r10
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x002f
            java.lang.Object r11 = r1.result
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            return r11
        L_0x002f:
            r1 = r10
            if (r11 == 0) goto L_0x004f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "try to disconnect blemesh isDisconnectMainPoint?"
            r2.append(r3)
            r2.append(r12)
            java.lang.String r3 = ",reason is :"
            r2.append(r3)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            meshsdk.MeshLog.i(r2)
        L_0x004f:
            r1.v()
            r1.G = r8
            java.lang.Object r2 = r1.h
            monitor-enter(r2)
            int r3 = r1.w     // Catch:{ all -> 0x01e1 }
            if (r3 != 0) goto L_0x005d
            monitor-exit(r2)     // Catch:{ all -> 0x01e1 }
            return r8
        L_0x005d:
            android.bluetooth.BluetoothGatt r4 = r1.e     // Catch:{ all -> 0x01e1 }
            if (r4 == 0) goto L_0x01dd
            if (r3 != r0) goto L_0x0117
            if (r12 == 0) goto L_0x0069
            java.lang.String r0 = "主"
            goto L_0x006b
        L_0x0069:
            java.lang.String r0 = ""
        L_0x006b:
            android.bluetooth.BluetoothDevice r3 = r1.f     // Catch:{ all -> 0x01e1 }
            if (r3 == 0) goto L_0x010d
            boolean r3 = meshsdk.datamgr.MeshDataManager.flagNetConfingAdddevices     // Catch:{ all -> 0x01e1 }
            if (r3 == 0) goto L_0x00a4
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e1 }
            r3.<init>()     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "断开已连接上的bleGatter"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r0)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "节点("
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothDevice r4 = r1.f     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = r4.getAddress()     // Catch:{ all -> 0x01e1 }
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ","
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r11)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e1 }
            com.leedarson.serviceimpl.reporters.b.d(r3)     // Catch:{ all -> 0x01e1 }
        L_0x00a4:
            com.telink.ble.mesh.foundation.MeshController r3 = r1.C     // Catch:{ all -> 0x01e1 }
            com.leedarson.serviceimpl.reporters.AddDeviceStepBean r4 = new com.leedarson.serviceimpl.reporters.AddDeviceStepBean     // Catch:{ all -> 0x01e1 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e1 }
            r5.<init>()     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = "断开已连接上的bleGatter"
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            r5.append(r0)     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = "节点("
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothDevice r6 = r1.f     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = r6.getAddress()     // Catch:{ all -> 0x01e1 }
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            r5.append(r11)     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = ")"
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x01e1 }
            r4.<init>(r5)     // Catch:{ all -> 0x01e1 }
            r3.d0(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e1 }
            r3.<init>()     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "断开已连接上的bleGatter"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r0)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "节点("
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothDevice r4 = r1.f     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = r4.getAddress()     // Catch:{ all -> 0x01e1 }
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ","
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r11)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e1 }
            meshsdk.MeshLogNew.v(r3)     // Catch:{ all -> 0x01e1 }
        L_0x010d:
            r3 = 3
            r1.w = r3     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothGatt r3 = r1.e     // Catch:{ all -> 0x01e1 }
            r3.disconnect()     // Catch:{ all -> 0x01e1 }
            goto L_0x01d7
        L_0x0117:
            if (r3 != r9) goto L_0x01d7
            if (r12 == 0) goto L_0x011f
            java.lang.String r0 = "主"
            goto L_0x0121
        L_0x011f:
            java.lang.String r0 = ""
        L_0x0121:
            android.bluetooth.BluetoothDevice r3 = r1.f     // Catch:{ all -> 0x01e1 }
            if (r3 == 0) goto L_0x01c3
            boolean r3 = meshsdk.datamgr.MeshDataManager.flagNetConfingAdddevices     // Catch:{ all -> 0x01e1 }
            if (r3 == 0) goto L_0x015a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e1 }
            r3.<init>()     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "断开正在连接的bleGatter"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r0)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "节点("
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothDevice r4 = r1.f     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = r4.getAddress()     // Catch:{ all -> 0x01e1 }
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ","
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r11)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e1 }
            com.leedarson.serviceimpl.reporters.b.d(r3)     // Catch:{ all -> 0x01e1 }
        L_0x015a:
            com.telink.ble.mesh.foundation.MeshController r3 = r1.C     // Catch:{ all -> 0x01e1 }
            com.leedarson.serviceimpl.reporters.AddDeviceStepBean r4 = new com.leedarson.serviceimpl.reporters.AddDeviceStepBean     // Catch:{ all -> 0x01e1 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e1 }
            r5.<init>()     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = "断开正在连接的bleGatter"
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            r5.append(r0)     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = "节点("
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothDevice r6 = r1.f     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = r6.getAddress()     // Catch:{ all -> 0x01e1 }
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            r5.append(r11)     // Catch:{ all -> 0x01e1 }
            java.lang.String r6 = ")"
            r5.append(r6)     // Catch:{ all -> 0x01e1 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x01e1 }
            r4.<init>(r5)     // Catch:{ all -> 0x01e1 }
            r3.d0(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e1 }
            r3.<init>()     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "断开正在连接的bleGatter"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r0)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = "节点("
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothDevice r4 = r1.f     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = r4.getAddress()     // Catch:{ all -> 0x01e1 }
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ","
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            r3.append(r11)     // Catch:{ all -> 0x01e1 }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ all -> 0x01e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e1 }
            meshsdk.MeshLogNew.v(r3)     // Catch:{ all -> 0x01e1 }
        L_0x01c3:
            android.bluetooth.BluetoothGatt r3 = r1.e     // Catch:{ all -> 0x01e1 }
            r3.disconnect()     // Catch:{ all -> 0x01e1 }
            r1.l0()     // Catch:{ all -> 0x01e1 }
            android.bluetooth.BluetoothGatt r3 = r1.e     // Catch:{ all -> 0x01e1 }
            r3.close()     // Catch:{ all -> 0x01e1 }
            r3 = 0
            r1.e = r3     // Catch:{ all -> 0x01e1 }
            r1.w = r8     // Catch:{ all -> 0x01e1 }
            monitor-exit(r2)     // Catch:{ all -> 0x01e1 }
            return r8
        L_0x01d7:
            monitor-exit(r2)     // Catch:{ all -> 0x01e1 }
            r1.s0()
            return r9
        L_0x01dd:
            r1.w = r8     // Catch:{ all -> 0x01e1 }
            monitor-exit(r2)     // Catch:{ all -> 0x01e1 }
            return r8
        L_0x01e1:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x01e1 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.ble.GattConnection.A(java.lang.String, boolean):boolean");
    }

    private synchronized void l0() {
        BluetoothGatt bluetoothGatt;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12291, new Class[0], Void.TYPE).isSupported) {
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

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12292, new Class[0], Void.TYPE).isSupported) {
            this.d.removeCallbacks(this.k);
            this.d.postDelayed(this.k, CacheHandler.delayTime);
        }
    }

    private void u0() {
        long delay;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12293, new Class[0], Void.TYPE).isSupported) {
            if (this.f.getBondState() == 12) {
                delay = 1600;
            } else {
                delay = 300;
            }
            this.J = 0;
            this.d.removeCallbacks(this.l);
            this.d.postDelayed(this.l, delay);
        }
    }

    private void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12294, new Class[0], Void.TYPE).isSupported) {
            k0();
            t();
            this.p.clear();
            this.r = false;
            this.d.removeCallbacksAndMessages((Object) null);
        }
    }

    private void U(String macAddress) {
        if (!PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 12295, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d.removeCallbacks(this.j);
            ConnectionCallback connectionCallback = this.y;
            if (connectionCallback != null) {
                connectionCallback.c(macAddress);
            }
        }
    }

    public boolean k0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12297, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (Build.VERSION.SDK_INT >= 27) {
            return false;
        }
        if (this.e == null) {
            S("refresh error: gatt null");
            return false;
        }
        S("Device#refreshCache#prepare");
        try {
            BluetoothGatt localBluetoothGatt = this.e;
            Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (localMethod != null) {
                return ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
            }
        } catch (Exception e2) {
            S("An exception occurs while refreshing device");
        }
        return false;
    }

    private void b0(List<BluetoothGattService> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 12298, new Class[]{List.class}, Void.TYPE).isSupported) {
            S("service discover complete,remove mServicesDiscoveringTimeoutRunnable:" + this.m);
            this.J = 0;
            this.d.removeCallbacks(this.m);
            ConnectionCallback connectionCallback = this.y;
            if (connectionCallback != null) {
                connectionCallback.d();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.d.removeCallbacks(this.i);
                this.d.postDelayed(this.i, GroupCtrlAdapter.RETRY_TIMEOUT);
                boolean requestMtu = this.e.requestMtu(256);
                ConnectionCallback connectionCallback2 = this.y;
                if (connectionCallback2 != null) {
                    connectionCallback2.f(requestMtu);
                }
            }
        }
    }

    private void V(String reason, int status) {
        if (!PatchProxy.proxy(new Object[]{reason, new Integer(status)}, this, changeQuickRedirect, false, 12299, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            this.J = 0;
            this.d.removeCallbacks(this.j);
            this.d.removeCallbacks(this.k);
            this.d.removeCallbacks(this.l);
            this.d.removeCallbacks(this.m);
            this.x = null;
            if (this.s.get()) {
                S("收到ble断开 onDisconnected, 之前业务在等待ble断开连接，时机已到，开始连接");
                this.s.set(false);
                w();
                return;
            }
            ConnectionCallback connectionCallback = this.y;
            if (connectionCallback != null) {
                BluetoothDevice bluetoothDevice = this.f;
                connectionCallback.g(bluetoothDevice != null ? bluetoothDevice.getAddress() : "", reason, status);
            }
        }
    }

    public String J() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12300, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BluetoothDevice bluetoothDevice = this.f;
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getAddress();
    }

    public String I() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12301, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BluetoothDevice bluetoothDevice = this.f;
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getName();
    }

    public boolean p0(@NonNull GattRequest gattRequest) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12302, new Class[]{GattRequest.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        synchronized (this.h) {
            if (this.w != 2) {
                S("sendRequest fail, not connected, mConnectionState is:" + this.w);
                return false;
            }
            this.p.add(gattRequest);
            d0();
            return true;
        }
    }

    public boolean q0(@NonNull GattRequest gattRequest) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12303, new Class[]{GattRequest.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        e0(gattRequest);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
        r2 = r0.p;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        if (r0.p.isEmpty() != false) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002c, code lost:
        r1 = r0.p.peek();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        if (r1 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        r3 = r0.q;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0038, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.r = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        e0(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0044, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0045, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 12304(0x3010, float:1.7242E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.lang.Object r1 = r0.q
            monitor-enter(r1)
            boolean r2 = r0.r     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0020
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            return
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            java.util.Queue<com.telink.ble.mesh.core.ble.GattRequest> r2 = r0.p
            monitor-enter(r2)
            java.util.Queue<com.telink.ble.mesh.core.ble.GattRequest> r1 = r0.p     // Catch:{ all -> 0x0046 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x0044
            java.util.Queue<com.telink.ble.mesh.core.ble.GattRequest> r1 = r0.p     // Catch:{ all -> 0x0046 }
            java.lang.Object r1 = r1.peek()     // Catch:{ all -> 0x0046 }
            com.telink.ble.mesh.core.ble.GattRequest r1 = (com.telink.ble.mesh.core.ble.GattRequest) r1     // Catch:{ all -> 0x0046 }
            if (r1 == 0) goto L_0x0044
            java.lang.Object r3 = r0.q     // Catch:{ all -> 0x0046 }
            monitor-enter(r3)     // Catch:{ all -> 0x0046 }
            r4 = 1
            r0.r = r4     // Catch:{ all -> 0x0041 }
            monitor-exit(r3)     // Catch:{ all -> 0x0041 }
            r0.e0(r1)     // Catch:{ all -> 0x0046 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.ble.GattConnection.d0():void");
    }

    private void e0(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12305, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            GattRequest.RequestType requestType = gattRequest.d;
            T("process request : " + gattRequest.toString(), 0);
            switch (AnonymousClass3.a[requestType.ordinal()]) {
                case 1:
                    c0();
                    i0(gattRequest);
                    return;
                case 2:
                    c0();
                    x0(gattRequest, 2);
                    return;
                case 3:
                    c0();
                    x0(gattRequest, 1);
                    return;
                case 4:
                    c0();
                    j0(gattRequest);
                    return;
                case 5:
                    c0();
                    y0(gattRequest);
                    return;
                case 6:
                    B(gattRequest);
                    return;
                case 7:
                    y(gattRequest);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.telink.ble.mesh.core.ble.GattConnection$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
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

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12306, new Class[0], Void.TYPE).isSupported) {
            if (this.t > 0) {
                this.o.removeCallbacksAndMessages((Object) null);
                this.o.postDelayed(this.n, this.t);
            }
        }
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12307, new Class[0], Void.TYPE).isSupported) {
            this.o.removeCallbacksAndMessages((Object) null);
        }
    }

    private void W(BluetoothGattCharacteristic characteristic, byte[] data) {
        if (!PatchProxy.proxy(new Object[]{characteristic, data}, this, changeQuickRedirect, false, 12308, new Class[]{BluetoothGattCharacteristic.class, byte[].class}, Void.TYPE).isSupported) {
            UUID charUUID = characteristic.getUuid();
            UUID serviceUUID = characteristic.getService().getUuid();
            if (!charUUID.equals(UUIDInfo.f) && !charUUID.equals(UUIDInfo.i)) {
                ConnectionCallback connectionCallback = this.y;
                if (connectionCallback != null) {
                    connectionCallback.b(serviceUUID, charUUID, data);
                }
            } else if (data == null || data.length == 0) {
                T("empty packet received!", 3);
            } else {
                byte[] completePacket = H(data);
                if (completePacket == null) {
                    T("waiting for segment pkt", 0);
                } else if (completePacket.length <= 1) {
                    T("complete notification length err", 3);
                } else {
                    ConnectionCallback connectionCallback2 = this.y;
                    if (connectionCallback2 != null) {
                        connectionCallback2.b(serviceUUID, charUUID, completePacket);
                    }
                }
            }
        }
    }

    private byte[] H(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 12309, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte sar = (byte) (data[0] & 192);
        switch (sar) {
            case Byte.MIN_VALUE:
            case IOTCAPIs.IOTC_ER_DEVICE_IS_SLEEP /*-64*/:
                byte[] bArr = this.z;
                if (bArr == null) {
                    T("segment first pkt no found", 3);
                    break;
                } else if ((bArr[0] & 63) != (data[0] & 63) || data.length <= 1) {
                    T("other segment ", 3);
                    break;
                } else {
                    byte[] tempBuffer = new byte[((bArr.length + data.length) - 1)];
                    System.arraycopy(bArr, 0, tempBuffer, 0, bArr.length);
                    System.arraycopy(data, 1, tempBuffer, this.z.length, data.length - 1);
                    if (sar == Byte.MIN_VALUE) {
                        this.z = tempBuffer;
                        return null;
                    }
                    this.z = null;
                    return tempBuffer;
                }
                break;
            case 0:
                return data;
            case 64:
                data[0] = (byte) (data[0] & 63);
                this.z = data;
                return null;
        }
        return null;
    }

    public Queue<GattRequest> M() {
        return this.p;
    }

    private void Y(String errorMsg) {
        GattRequest request;
        if (!PatchProxy.proxy(new Object[]{errorMsg}, this, changeQuickRedirect, false, 12310, new Class[]{String.class}, Void.TYPE).isSupported) {
            synchronized (this.p) {
                request = this.p.poll();
            }
            if (request != null) {
                S("send request tag:" + request.f + " error: " + errorMsg + ",mac:" + J());
                GattRequest.Callback requestCallback = request.h;
                if (requestCallback != null) {
                    requestCallback.a(request, errorMsg);
                    return;
                }
                return;
            }
            S("send request error: " + errorMsg + ",mac:" + J());
        }
    }

    private boolean a0(@NonNull GattRequest gattRequest) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12311, new Class[]{GattRequest.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        T("gatt request timeout", 0);
        GattRequest.Callback requestCallback = gattRequest.h;
        if (requestCallback != null) {
            return requestCallback.c(gattRequest);
        }
        return false;
    }

    private void Z(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 12312, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            GattRequest gattRequest = this.p.poll();
            if (gattRequest != null) {
                T("send request success: tag - " + gattRequest.f, 0);
                GattRequest.Callback requestCallback = gattRequest.h;
                if ("proxyInit".equals(gattRequest.f)) {
                    MeshService.k().l().c0(new AutoConnectDeviceStepBean(AutoConnectDeviceStepBean.STEP_PROXY_INIT_SEND_SUCCESS, e.CODE_SUCCESS.getCode()), "");
                } else if ("sendMeshBeaconPdu".equals(gattRequest.f)) {
                    MeshService.k().l().c0(new AutoConnectDeviceStepBean(AutoConnectDeviceStepBean.STEP_CHECK_SEQUENCE_NUMBER_SUCCESS, e.CODE_SUCCESS.getCode()), "");
                }
                if (requestCallback != null) {
                    requestCallback.b(gattRequest, data);
                    return;
                }
                return;
            }
            S("request not found");
        }
    }

    private void X() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12313, new Class[0], Void.TYPE).isSupported) {
            T("gatt request completed", 0);
            synchronized (this.q) {
                if (this.r) {
                    this.r = false;
                }
            }
            d0();
        }
    }

    public void B(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12314, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = E(service, characteristicUUID);
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
                Y("enable notification error: " + errorMsg + " - " + characteristicUUID);
            } else {
                Z((byte[]) null);
            }
            X();
        }
    }

    private void y(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12315, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            boolean success = true;
            String errorMsg = "";
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = E(service, characteristicUUID);
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
                S(errInfo);
                Y(errInfo);
            } else {
                Z((byte[]) null);
            }
            X();
        }
    }

    private void j0(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12316, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
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
                Y(errorMsg);
                X();
            }
        }
    }

    private void y0(GattRequest gattRequest) {
        if (!PatchProxy.proxy(new Object[]{gattRequest}, this, changeQuickRedirect, false, 12317, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
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
                Y(errorMsg);
                X();
            }
        }
    }

    private void i0(GattRequest request) {
        if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 12318, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
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
                Y(errorMsg);
                X();
            }
        }
    }

    public class GattRequestItemWrapItem {
        public GattRequest a;
        public long b = 0;
        BluetoothGattCharacteristic c;

        GattRequestItemWrapItem() {
        }
    }

    public void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12319, new Class[0], Void.TYPE).isSupported) {
            if (!this.G) {
                this.G = true;
                new Thread(new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12353, new Class[0], Void.TYPE).isSupported) {
                            while (GattConnection.this.G) {
                                try {
                                    GattRequestItemWrapItem itemWrapItem = (GattRequestItemWrapItem) GattConnection.this.H.take();
                                    while (true) {
                                        if (GattConnection.b(GattConnection.this, itemWrapItem)) {
                                            break;
                                        }
                                        Thread.sleep(100);
                                        if (System.currentTimeMillis() - itemWrapItem.b > GattConnection.a) {
                                            GattConnection.this.I.post(new Runnable() {
                                                public static ChangeQuickRedirect changeQuickRedirect;

                                                public void run() {
                                                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12354, new Class[0], Void.TYPE).isSupported) {
                                                        GattConnection.d(GattConnection.this, "write characteristic error writeCharacteristic");
                                                        GattConnection.e(GattConnection.this);
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    MeshLog.e("processWriteByBluetoothGatt.exception  e=" + e.toString());
                                }
                            }
                        }
                    }
                }).start();
            }
        }
    }

    private boolean t0(GattRequestItemWrapItem itemWrapItem) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{itemWrapItem}, this, changeQuickRedirect, false, 12320, new Class[]{GattRequestItemWrapItem.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.e.writeCharacteristic(itemWrapItem.c);
    }

    private void x0(GattRequest gattRequest, int writeType) {
        String errorMsg;
        boolean success;
        if (!PatchProxy.proxy(new Object[]{gattRequest, new Integer(writeType)}, this, changeQuickRedirect, false, 12321, new Class[]{GattRequest.class, Integer.TYPE}, Void.TYPE).isSupported) {
            UUID serviceUUID = gattRequest.a;
            UUID characteristicUUID = gattRequest.b;
            byte[] data = gattRequest.e;
            BluetoothGattService service = this.e.getService(serviceUUID);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = F(service, characteristicUUID, writeType);
                if (characteristic != null) {
                    characteristic.setValue(data);
                    characteristic.setWriteType(writeType);
                    GattRequestItemWrapItem gattRequestItemWrapItem = new GattRequestItemWrapItem();
                    gattRequestItemWrapItem.b = System.currentTimeMillis();
                    gattRequestItemWrapItem.c = characteristic;
                    gattRequestItemWrapItem.a = gattRequest;
                    try {
                        l.n.execute(new a(this, gattRequestItemWrapItem));
                        f0();
                        return;
                    } catch (Exception e2) {
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
                Y(errorMsg);
                X();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: Q */
    public /* synthetic */ void R(GattRequestItemWrapItem gattRequestItemWrapItem) {
        if (!PatchProxy.proxy(new Object[]{gattRequestItemWrapItem}, this, changeQuickRedirect, false, 12341, new Class[]{GattRequestItemWrapItem.class}, Void.TYPE).isSupported) {
            try {
                this.H.put(gattRequestItemWrapItem);
            } catch (Exception e2) {
                MeshLog.e("put....queueItems.put exception:" + e2.getMessage());
            }
        }
    }

    private BluetoothGattCharacteristic F(BluetoothGattService service, UUID characteristicUUID, int writeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{service, characteristicUUID, new Integer(writeType)}, this, changeQuickRedirect, false, 12324, new Class[]{BluetoothGattService.class, UUID.class, Integer.TYPE}, BluetoothGattCharacteristic.class);
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

    private BluetoothGattCharacteristic E(BluetoothGattService service, UUID characteristicUUID) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{service, characteristicUUID}, this, changeQuickRedirect2, false, 12325, new Class[]{BluetoothGattService.class, UUID.class}, BluetoothGattCharacteristic.class);
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
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12326, clsArr, Void.TYPE).isSupported) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }
    }

    public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        Object[] objArr = {gatt, new Integer(txPhy), new Integer(rxPhy), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {BluetoothGatt.class, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12328, clsArr, Void.TYPE).isSupported) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }
    }

    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        String str;
        Object[] objArr = {gatt, new Integer(status), new Integer(newState)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12329, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            S("onConnectionStateChange:" + this.f.getAddress() + "  status :" + status + " state : " + newState + "   this.mBluetoothDevice=" + this.f.hashCode() + "  this.mGatt=" + this.e.toString() + "   gatt=" + gatt.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(this.f.getAddress());
            sb.append("->");
            if (newState == 2) {
                str = "设备连接成功了";
            } else {
                str = "设备连接失败设备信息:" + status;
            }
            sb.append(str);
            MeshLogNew.v(sb.toString());
            if (newState == 2) {
                synchronized (this.h) {
                    this.w = 2;
                }
                U(this.f.getAddress());
                u0();
                return;
            }
            synchronized (this.h) {
                if (newState == 0) {
                    try {
                        this.C.J1("配网 onConnectionStateChange 收到已断开连接事件:" + J());
                        this.C.K1("上线 onConnectionStateChange 收到已断开连接事件:" + J());
                    } catch (Throwable th) {
                        throw th;
                    }
                } else if (newState == 1) {
                    this.C.J1("配网 onConnectionStateChange 收到正在连接事件:" + J());
                    this.C.K1("上线 onConnectionStateChange 收到正在连接事件:" + J());
                } else if (newState == 3) {
                    this.C.J1("配网 onConnectionStateChange 收到正在断开连接事件:" + J());
                    this.C.K1("上线 onConnectionStateChange 收到正在断开连接事件:" + J());
                }
                S("Gatt Close");
                gatt.close();
                v();
                this.w = 0;
                V("onConnection状态变化(系统回调)  status=" + status + "   newState=" + newState, status);
            }
        }
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 12330, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (status == 0) {
                List<BluetoothGattService> services = gatt.getServices();
                this.x = services;
                b0(services);
                return;
            }
            S("Service discovery failed");
            z("ble特征值Service 搜索失败");
        }
    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Object[] objArr = {gatt, characteristic, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12331, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onCharacteristicRead(gatt, characteristic, status);
            t();
            if (G(status)) {
                Z(characteristic.getValue());
            } else {
                Y("read characteristic failed");
            }
            X();
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Object[] objArr = {gatt, characteristic, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12332, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            t();
            if (G(status)) {
                Z((byte[]) null);
            } else {
                Y("write characteristic fail");
            }
            X();
        }
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        Class[] clsArr = {BluetoothGatt.class, BluetoothGattCharacteristic.class};
        if (!PatchProxy.proxy(new Object[]{gatt, characteristic}, this, changeQuickRedirect, false, 12333, clsArr, Void.TYPE).isSupported) {
            super.onCharacteristicChanged(gatt, characteristic);
            W(characteristic, characteristic.getValue());
        }
    }

    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Object[] objArr = {gatt, descriptor, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12334, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onDescriptorRead(gatt, descriptor, status);
            t();
            if (G(status)) {
                Z(descriptor.getValue());
            } else {
                Y("read descriptor fail");
            }
            X();
        }
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Object[] objArr = {gatt, descriptor, new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12335, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onDescriptorWrite(gatt, descriptor, status);
            t();
            if (G(status)) {
                Z((byte[]) null);
            } else {
                Y("write descriptor fail");
            }
            X();
        }
    }

    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 12336, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onReliableWriteCompleted(gatt, status);
        }
    }

    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        Object[] objArr = {gatt, new Integer(rssi), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12337, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }
    }

    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        Object[] objArr = {gatt, new Integer(mtu), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12338, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
            S("onMtuChanged: " + mtu + ",status:" + status);
            this.d.removeCallbacks(this.i);
            super.onMtuChanged(gatt, mtu, status);
            if (G(status)) {
                this.A = mtu;
            }
            ConnectionCallback connectionCallback = this.y;
            if (connectionCallback != null) {
                connectionCallback.e(false, status, mtu, this.x);
            }
        }
    }

    public final class ConnectionTimeoutRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private ConnectionTimeoutRunnable() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12356, new Class[0], Void.TYPE).isSupported) {
                MeshLog.i("SUFUN  mac=" + GattConnection.this.B + "   检测到超时/正在尝试关闭释放原有的连接");
                if (!GattConnection.this.z("连接超时")) {
                    GattConnection.f(GattConnection.this, "被强制中断(连接超时)-ConnectionTimeoutRunnable:10000", -2);
                }
            }
        }
    }

    public final class DisconnectionTimeoutRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private DisconnectionTimeoutRunnable() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12357, new Class[0], Void.TYPE).isSupported) {
                GattConnection.g(GattConnection.this, "disconnection timeout");
                synchronized (GattConnection.this.h) {
                    if (GattConnection.this.e != null) {
                        GattConnection.this.e.disconnect();
                        GattConnection.this.e.close();
                        BluetoothGatt unused = GattConnection.this.e = null;
                    }
                    int unused2 = GattConnection.this.w = 0;
                    GattConnection.f(GattConnection.this, "执行DisConnect时未收到系统的断开调用：timeOut=2000", -3);
                }
            }
        }
    }

    public final class ServicesDiscoveringRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private ServicesDiscoveringRunnable() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12359, new Class[0], Void.TYPE).isSupported) {
                if (GattConnection.this.y != null) {
                    GattConnection.this.y.a();
                }
                GattConnection.this.d.removeCallbacks(GattConnection.this.m);
                GattConnection gattConnection = GattConnection.this;
                GattConnection.g(gattConnection, "post mServicesDiscoveringTimeoutRunnable:" + GattConnection.this.m + " delay " + 3000);
                GattConnection.this.d.postDelayed(GattConnection.this.m, GroupCtrlAdapter.RETRY_TIMEOUT);
                if (GattConnection.this.e == null || !GattConnection.this.e.discoverServices()) {
                    GattConnection.this.d.removeCallbacks(GattConnection.this.l);
                    GattConnection.this.z("serviceDiscover 失败");
                    MeshController meshController = GattConnection.this.C;
                    e eVar = e.CODE_BLE_DISCOVER_SERVICE_FAIL;
                    meshController.c0(new AutoConnectDeviceStepBean("获取蓝牙服务失败", eVar.getCode()), MeshConstants.AC_STATE_START_FIND_SERVICE);
                    GattConnection.this.C.d0(new AddDeviceStepBean("获取蓝牙服务失败", eVar.getCode()));
                }
            }
        }
    }

    public final class ServicesDiscoveringTimeoutRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private ServicesDiscoveringTimeoutRunnable() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12360, new Class[0], Void.TYPE).isSupported) {
                GattConnection gattConnection = GattConnection.this;
                GattConnection.g(gattConnection, "serviceDiscover 超时 retry:" + GattConnection.this.J + ",runnable:" + this);
                GattConnection gattConnection2 = GattConnection.this;
                int i = gattConnection2.J;
                if (i >= 2) {
                    gattConnection2.z("serviceDiscover 超时，达到重试次数上限");
                    if (MeshController.Mode.MODE_AUTO_CONNECT == MeshService.k().f()) {
                        c.c(new AutoConnectDeviceStepBean("获取蓝牙服务超时", e.CODE_BLE_DISCOVER_SERVICE_TIMEOUT.getCode()));
                        return;
                    }
                    return;
                }
                gattConnection2.J = i + 1;
                gattConnection2.d.removeCallbacks(GattConnection.this.l);
                b.a("post mServicesDiscoveringRunnable retry:" + GattConnection.this.l);
                GattConnection.this.d.post(GattConnection.this.l);
            }
        }
    }

    public final class CommandTimeoutRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private CommandTimeoutRunnable() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12355, new Class[0], Void.TYPE).isSupported) {
                synchronized (GattConnection.this.p) {
                    GattRequest gattRequest = (GattRequest) GattConnection.this.p.peek();
                    if (gattRequest != null) {
                        if (GattConnection.m(GattConnection.this, gattRequest)) {
                            GattConnection.n(GattConnection.this, gattRequest);
                        } else {
                            gattRequest.a();
                            GattConnection.this.p.poll();
                            GattConnection.e(GattConnection.this);
                        }
                    }
                }
            }
        }
    }

    private boolean G(int status) {
        return status == 0;
    }

    private void S(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12339, new Class[]{String.class}, Void.TYPE).isSupported) {
            T(logMessage + "  macAddress=" + this.B, 2);
        }
    }

    private void T(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 12340, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g("SIGMesh GattConnection:" + logMessage, "Mesh-GATT", level);
        }
    }
}
