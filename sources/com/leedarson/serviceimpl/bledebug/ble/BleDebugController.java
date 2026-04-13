package com.leedarson.serviceimpl.bledebug.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import com.leedarson.serviceimpl.bledebug.bean.ReconnectParams;
import com.leedarson.serviceimpl.bledebug.ble.a;
import com.leedarson.serviceimpl.bledebug.ble.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.telink.ble.mesh.core.ble.BleReflexHelper;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import com.telink.ble.mesh.core.ble.LeScanSetting;
import com.telink.ble.mesh.core.ble.UUIDInfo;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.event.BluetoothEvent;
import com.telink.ble.mesh.foundation.event.GattConnectionEvent;
import com.telink.ble.mesh.foundation.event.GattNotificationEvent;
import com.telink.ble.mesh.foundation.event.ScanEvent;
import com.telink.ble.mesh.foundation.parameter.AutoConnectParameters;
import com.telink.ble.mesh.foundation.parameter.Parameters;
import com.telink.ble.mesh.foundation.parameter.ScanParameters;
import com.telink.ble.mesh.util.MeshLogger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import meshsdk.MeshLog;
import meshsdk.util.ProcedureCollector;
import pub.devrel.easypermissions.EasyPermissions;

public final class BleDebugController {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A = false;
    private c.d B = new b();
    private a.f C = new c();
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bledebug.b D;
    private final String a = "MeshController";
    private a b;
    private c c;
    private Context d;
    private HandlerThread e;
    /* access modifiers changed from: private */
    public Handler f;
    private e g = e.MODE_IDLE;
    private Parameters h;
    private boolean i = false;
    private final Object j = new Object();
    /* access modifiers changed from: private */
    public boolean k = false;
    private Set<AdvertisingDevice> l = new LinkedHashSet();
    private byte[] m = null;
    private byte[] n = null;
    private boolean o = false;
    private boolean p = false;
    /* access modifiers changed from: private */
    public int q = 0;
    private int r = 0;
    private boolean s = false;
    /* access modifiers changed from: private */
    public BluetoothDevice t;
    private long u = 0;
    private AutoConnectParameters v;
    private int w = 0;
    public String x = "";
    private ReconnectParams y;
    private BroadcastReceiver z = new BroadcastReceiver() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 6700, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                String action = intent.getAction();
                if (action != null && "android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                    BleDebugController.a(BleDebugController.this, intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0));
                }
            }
        }
    };

    public enum e {
        MODE_IDLE,
        MODE_SCAN,
        MODE_PROVISION,
        MODE_GATT_CONNECTION;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void a(BleDebugController x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 6691, new Class[]{BleDebugController.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.y(x1);
        }
    }

    static /* synthetic */ void c(BleDebugController x0, BluetoothDevice x1, int x2, byte[] x3) {
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6697, new Class[]{BleDebugController.class, BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            x0.G(x1, x2, x3);
        }
    }

    static /* synthetic */ int d(BleDebugController x0) {
        int i2 = x0.q;
        x0.q = i2 + 1;
        return i2;
    }

    static /* synthetic */ void f(BleDebugController x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 6698, new Class[]{BleDebugController.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.F(x1);
        }
    }

    static /* synthetic */ void g(BleDebugController x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 6699, new Class[]{BleDebugController.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.H(x1);
        }
    }

    static /* synthetic */ void h(BleDebugController x0, String x1) {
        Class[] clsArr = {BleDebugController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6692, clsArr, Void.TYPE).isSupported) {
            x0.v(x1);
        }
    }

    static /* synthetic */ void i(BleDebugController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6693, new Class[]{BleDebugController.class}, Void.TYPE).isSupported) {
            x0.z();
        }
    }

    static /* synthetic */ void l(BleDebugController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6694, new Class[]{BleDebugController.class}, Void.TYPE).isSupported) {
            x0.D();
        }
    }

    static /* synthetic */ void n(BleDebugController x0, byte[] x1) {
        Class[] clsArr = {BleDebugController.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6695, clsArr, Void.TYPE).isSupported) {
            x0.E(x1);
        }
    }

    static /* synthetic */ void o(BleDebugController x0, UUID x1, UUID x2, byte[] x3) {
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 6696, new Class[]{BleDebugController.class, UUID.class, UUID.class, byte[].class}, Void.TYPE).isSupported) {
            x0.J(x1, x2, x3);
        }
    }

    public void N(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 6656, new Class[]{Context.class}, Void.TYPE).isSupported) {
            MeshLog.e("SUFUN.MeshController====>" + hashCode());
            HandlerThread handlerThread = new HandlerThread("Mesh-Controller" + this.x);
            this.e = handlerThread;
            handlerThread.start();
            this.f = new Handler(this.e.getLooper());
            this.d = context.getApplicationContext();
            u(this.e);
            t(this.e);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            this.d.registerReceiver(this.z, filter);
        }
    }

    public ReconnectParams r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6657, new Class[0], ReconnectParams.class);
        if (proxy.isSupported) {
            return (ReconnectParams) proxy.result;
        }
        if (this.y == null) {
            this.y = new ReconnectParams();
        }
        return this.y;
    }

    public void M(ReconnectParams reconnectParams) {
        this.y = reconnectParams;
    }

    public void Q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6658, new Class[0], Void.TYPE).isSupported) {
            this.g = e.MODE_IDLE;
            this.r = 0;
            this.o = false;
            R();
            Set<AdvertisingDevice> set = this.l;
            if (set != null) {
                set.clear();
            }
            c cVar = this.c;
            if (cVar != null) {
                cVar.v("Control#stop,释放资源");
                this.c.U((c.d) null);
                this.c = null;
            }
            Handler handler = this.f;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
                this.f = null;
            }
            HandlerThread handlerThread = this.e;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                this.e = null;
            }
            if (!this.A) {
                this.d.unregisterReceiver(this.z);
                this.A = true;
            }
        }
    }

    private void y(int state) {
        String stateInfo;
        Object[] objArr = {new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6660, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (state) {
                case 10:
                    stateInfo = "bluetooth disabled";
                    c cVar = this.c;
                    if (cVar != null) {
                        cVar.v("蓝牙未打开");
                        break;
                    }
                    break;
                case 11:
                    stateInfo = "bluetooth turning on";
                    break;
                case 12:
                    stateInfo = "bluetooth enabled";
                    break;
                case 13:
                    stateInfo = "bluetooth turning off";
                    break;
                default:
                    stateInfo = "unknown";
                    break;
            }
            x(state, stateInfo);
        }
    }

    private void u(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 6661, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            a aVar = new a(a.g.DEFAULT, handlerThread, this);
            this.b = aVar;
            aVar.t(this.C);
        }
    }

    private void t(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 6662, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            c cVar = new c(this.d, handlerThread);
            this.c = cVar;
            cVar.y = this.x;
            cVar.U(this.B);
        }
    }

    public void s(boolean disconnect, String reason) {
        Object[] objArr = {new Byte(disconnect ? (byte) 1 : 0), reason};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6663, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            v("---idle--- " + disconnect);
            this.f.removeCallbacksAndMessages((Object) null);
            if (disconnect) {
                this.c.v(reason);
            }
            R();
        }
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6667, new Class[0], Void.TYPE).isSupported) {
            this.k = false;
            this.b.B();
        }
    }

    public void p(BluetoothDevice device) {
        if (!PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 6668, new Class[]{BluetoothDevice.class}, Void.TYPE).isSupported) {
            R();
            this.t = device;
            this.c.t(device);
        }
    }

    public void q() {
        c cVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6669, new Class[0], Void.TYPE).isSupported && (cVar = this.c) != null) {
            cVar.v("常规断开");
        }
    }

    public void P(ScanParameters scanParameters) {
        if (!PatchProxy.proxy(new Object[]{scanParameters}, this, changeQuickRedirect, false, 6670, new Class[]{ScanParameters.class}, Void.TYPE).isSupported) {
            this.f.removeCallbacksAndMessages((Object) null);
            this.g = e.MODE_SCAN;
            this.l.clear();
            this.h = scanParameters;
            MeshLog.i("####### startScan.scanParameters===>");
            K();
            if (!this.c.v("start scan ，waiting for disconnect..")) {
                O();
            } else {
                v("waiting for disconnect...");
            }
        }
    }

    private void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6671, new Class[0], Void.TYPE).isSupported) {
            v("start scan: " + this.g + "  macAddress=" + this.x);
            ProcedureCollector.autoConnectState = "startScan";
            LeScanSetting scanSetting = new LeScanSetting(this.h.d("com.telink.ble.com.telink.ble.mesh.light.COMMON_SCAN_MIN_SPACING", 500), this.h.d("com.telink.ble.com.telink.ble.mesh.light.COMMON_SCAN_TIMEOUT", 10000));
            this.b.w((LeScanFilter) this.h.b("com.telink.ble.com.telink.ble.mesh.light.SCAN_FILTERS"), scanSetting);
        }
    }

    public void C(boolean success, String desc) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0), desc}, this, changeQuickRedirect, false, 6677, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            K();
            s(false, (String) null);
            B(new GattConnectionEvent(this, success ? "com.telink.sig.com.telink.ble.mesh.CONNECT_SUCCESS" : "com.telink.sig.com.telink.ble.mesh.CONNECT_FAIL", desc));
        }
    }

    private void x(int state, String desc) {
        Object[] objArr = {new Integer(state), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6678, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            MeshLog.i("bluetooth event: " + state + " -- " + desc + "  macAddress=" + this.x);
            BluetoothEvent event = new BluetoothEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BLUETOOTH_STATE_CHANGE");
            event.c(state);
            event.b(desc);
            B(event);
        }
    }

    private void B(Event event) {
    }

    private void K() {
        this.p = false;
        this.q = 0;
    }

    private void E(byte[] completePacket) {
        if (!PatchProxy.proxy(new Object[]{completePacket}, this, changeQuickRedirect, false, 6680, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (completePacket.length > 1) {
                byte b2 = (byte) (completePacket[0] & 63);
                byte[] payloadData = new byte[(completePacket.length - 1)];
                System.arraycopy(completePacket, 1, payloadData, 0, payloadData.length);
            }
        }
    }

    private void J(UUID serviceUUID, UUID characteristicUUID, byte[] data) {
        Class[] clsArr = {UUID.class, UUID.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{serviceUUID, characteristicUUID, data}, this, changeQuickRedirect, false, 6681, clsArr, Void.TYPE).isSupported) {
            B(new GattNotificationEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_UNEXPECTED_NOTIFY", serviceUUID, characteristicUUID, data));
        }
    }

    private void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6682, new Class[0], Void.TYPE).isSupported) {
            K();
            c.d dVar = this.B;
            if (dVar != null) {
                dVar.c();
            }
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6683, new Class[0], Void.TYPE).isSupported) {
            this.f.removeCallbacksAndMessages((Object) null);
            this.v = null;
            if (this.i) {
                this.i = false;
                this.q = -1;
            }
            this.o = false;
            this.r = 0;
            this.f.postDelayed(new a(), (long) r().reconnectDelay);
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6701, new Class[0], Void.TYPE).isSupported) {
                BleDebugController.d(BleDebugController.this);
                if (BleDebugController.this.q >= BleDebugController.this.r().maxCount) {
                    BleDebugController bleDebugController = BleDebugController.this;
                    BleDebugController.h(bleDebugController, "gatt controller 连接次数上限:" + BleDebugController.this.q);
                    BleDebugController.i(BleDebugController.this);
                    return;
                }
                BleDebugController bleDebugController2 = BleDebugController.this;
                BleDebugController.h(bleDebugController2, "gatt controller 内部重连:" + BleDebugController.this.q);
                BleDebugController bleDebugController3 = BleDebugController.this;
                bleDebugController3.p(bleDebugController3.t);
            }
        }
    }

    public class b implements c.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6702, new Class[0], Void.TYPE).isSupported) {
                if (BleDebugController.this.D != null) {
                    BleDebugController.this.D.f(BleDebugController.this.x);
                }
            }
        }

        public void onConnected() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6703, new Class[0], Void.TYPE).isSupported) {
                if (BleDebugController.this.D != null) {
                    BleDebugController.this.D.c(BleDebugController.this.x);
                }
            }
        }

        public void onDisconnected() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6704, new Class[0], Void.TYPE).isSupported) {
                BleDebugController.l(BleDebugController.this);
                if (BleDebugController.this.D != null) {
                    BleDebugController.this.D.onDisconnect(BleDebugController.this.x);
                }
            }
        }

        public void a() {
        }

        public void d(List<BluetoothGattService> list) {
            if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 6705, new Class[]{List.class}, Void.TYPE).isSupported) {
                BleDebugController.this.f.removeCallbacksAndMessages((Object) null);
                if (BleDebugController.this.D != null) {
                    BleDebugController.this.D.d(BleDebugController.this.x);
                }
            }
        }

        public void b(UUID serviceUUID, UUID charUUID, byte[] data) {
            Class[] clsArr = {UUID.class, UUID.class, byte[].class};
            if (!PatchProxy.proxy(new Object[]{serviceUUID, charUUID, data}, this, changeQuickRedirect, false, 6706, clsArr, Void.TYPE).isSupported) {
                if (!charUUID.equals(UUIDInfo.k)) {
                    if (charUUID.equals(UUIDInfo.i) || charUUID.equals(UUIDInfo.f)) {
                        BleDebugController.n(BleDebugController.this, data);
                    } else {
                        BleDebugController.o(BleDebugController.this, serviceUUID, charUUID, data);
                    }
                }
            }
        }
    }

    private void A(AdvertisingDevice device) {
        if (!PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 6684, new Class[]{AdvertisingDevice.class}, Void.TYPE).isSupported) {
            if (this.l.add(device)) {
                try {
                    com.leedarson.serviceimpl.bledebug.b bVar = this.D;
                    if (bVar != null) {
                        bVar.g(device);
                    }
                } catch (Exception e2) {
                    timber.log.a.b(e2);
                    e2.printStackTrace();
                }
            }
        }
    }

    private void F(int errorCode) {
        Object[] objArr = {new Integer(errorCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6685, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            ScanEvent scanEvent = new ScanEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_FAIL", (AdvertisingDevice) null);
            scanEvent.c(errorCode);
            B(scanEvent);
        }
    }

    private void H(boolean z2) {
        if (!PatchProxy.proxy(new Object[]{new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 6686, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            boolean b2 = EasyPermissions.a(this.d, "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION");
            MeshLog.i("scanning timeout: " + this.g + ",hasLocationPermission:" + b2 + ",  macAddress=" + this.x);
            switch (d.a[this.g.ordinal()]) {
                case 1:
                    I();
                    return;
                case 2:
                    C(false, "connection fail: scan timeout");
                    return;
                default:
                    return;
            }
        }
    }

    public static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[e.values().length];
            a = iArr;
            try {
                iArr[e.MODE_SCAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[e.MODE_GATT_CONNECTION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6687, new Class[0], Void.TYPE).isSupported) {
            s(false, (String) null);
            B(new ScanEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_TIMEOUT", (AdvertisingDevice) null));
        }
    }

    private void G(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Object[] objArr = {device, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6688, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            synchronized (this.j) {
                if (this.g == e.MODE_SCAN) {
                    if (this.h.c("com.telink.ble.com.telink.ble.mesh.light.SCAN_SINGLE_MODE", false)) {
                        R();
                    }
                    A(new AdvertisingDevice(device, rssi, scanRecord));
                }
            }
        }
    }

    public class c implements a.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Object[] objArr = {device, new Integer(rssi), scanRecord};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6707, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
                MeshLog.v("scan:" + device.getName() + ",thread:" + Thread.currentThread().getName() + " --mac: " + device.getAddress() + " --rssi:" + rssi + " --record: " + com.leedarson.base.utils.e.b(scanRecord, ":"));
                BleDebugController.c(BleDebugController.this, device, rssi, scanRecord);
            }
        }

        public void onScanFail(int errorCode) {
            if (!PatchProxy.proxy(new Object[]{new Integer(errorCode)}, this, changeQuickRedirect, false, 6708, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                boolean unused = BleDebugController.this.k = false;
                BleDebugController.f(BleDebugController.this, errorCode);
                MeshLog.e("mesh controller onScanFail:" + errorCode + "  macAddress=" + BleDebugController.this.x);
                if (errorCode == 2) {
                    com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(BleDebugController.this).c(BleDebugController.class.getName()).t("LdsBleMesh");
                    t.p("mesh controller onScanFail:" + errorCode).a().b();
                    BleReflexHelper.e();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6709, new Class[0], Void.TYPE).isSupported) {
                boolean unused = BleDebugController.this.k = true;
            }
        }

        public void b() {
        }

        public void c(boolean anyDeviceFound) {
            Object[] objArr = {new Byte(anyDeviceFound ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6710, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                BleDebugController.g(BleDebugController.this, anyDeviceFound);
            }
        }
    }

    private void v(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 6689, new Class[]{String.class}, Void.TYPE).isSupported) {
            w(logMessage, 2);
        }
    }

    private void w(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 6690, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage, "MeshController", level);
        }
    }

    public void L(com.leedarson.serviceimpl.bledebug.b gattTestListener) {
        this.D = gattTestListener;
    }
}
