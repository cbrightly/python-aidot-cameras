package com.leedarson.serviceimpl.blec075.strategy;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceinterface.listener.ScanDeviceRuleListener;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import io.reactivex.e;
import io.reactivex.functions.f;
import io.reactivex.processors.c;
import java.util.concurrent.TimeUnit;
import meshsdk.MeshScanLog;
import timber.log.a;

/* compiled from: BleConnectDeviceImproveStrategyV2 */
public class i implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private c<BleDevice> a = c.Y(1);
    private String b = "";
    private String c = "";
    private b d;
    private String e;
    public ScanDeviceRuleListener f;
    b g;
    b h;

    public void k(b _seekDisposble) {
        this.d = _seekDisposble;
    }

    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord, @Nullable ScanRecord record) {
        boolean isTargetDevice;
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{bluetoothDevice, new Integer(rssi), scanRecord, record}, this, changeQuickRedirect, false, 6554, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            BleDevice bleDevice = new BleDevice(bluetoothDevice, rssi, scanRecord, System.currentTimeMillis());
            if (this.f != null) {
                if (a(bleDevice) && this.f.checkIsTarget(bluetoothDevice, rssi, scanRecord, record)) {
                    z = true;
                }
                isTargetDevice = z;
            } else {
                isTargetDevice = a(bleDevice);
            }
            if (isTargetDevice) {
                MeshScanLog.d("BLE.TRV 找到Target数据 mac=" + bleDevice.c() + " address=" + bleDevice.a().getAddress());
                m();
                this.a.onNext(bleDevice);
                this.a.onComplete();
                b bVar = this.g;
                if (bVar != null && !bVar.isDisposed()) {
                    this.g.dispose();
                }
            }
        }
    }

    public void onScanFail(int errorCode) {
        if (!PatchProxy.proxy(new Object[]{new Integer(errorCode)}, this, changeQuickRedirect, false, 6555, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            timber.log.a.c("BLE.Connect 扫描出现异常 errorCode=" + errorCode, new Object[0]);
        }
    }

    public String onFromBz() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6556, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "fromBz:" + this.e;
    }

    private boolean a(BleDevice bleDevice) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6557, new Class[]{BleDevice.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] record = bleDevice.f();
        if (record == null || record.length <= 0) {
            return false;
        }
        String recordHexString = h.b(bleDevice.f());
        if (!TextUtils.isEmpty(this.b) && bleDevice.c().replace(":", "").equals(this.b.replace(":", ""))) {
            timber.log.a.i("BLE.TRV  match 111111 ", new Object[0]);
            return true;
        } else if (!TextUtils.isEmpty(this.b) && recordHexString.toUpperCase().contains(this.b.toUpperCase())) {
            timber.log.a.i("BLE.TRV  match 2222222", new Object[0]);
            return true;
        } else if (TextUtils.isEmpty(this.c) || !recordHexString.toUpperCase().contains(this.c.replace("\"", "").toUpperCase())) {
            return false;
        } else {
            timber.log.a.i("BLE.TRV  match 3333333", new Object[0]);
            return true;
        }
    }

    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6558, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.base.c.k().B(this);
        }
    }

    private void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6559, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.base.c.k().x(this);
        }
    }

    public e<BleDevice> j(String deviceMac, String advertisingData, int timeOut, String fromBz) {
        Class<String> cls = String.class;
        Object[] objArr = {deviceMac, advertisingData, new Integer(timeOut), fromBz};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 6560, new Class[]{cls, cls, Integer.TYPE, cls}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        this.b = deviceMac;
        this.c = advertisingData;
        this.e = fromBz;
        l();
        int timeOutTemp = 10;
        if (timeOut > 0) {
            timeOutTemp = timeOut;
        }
        b bVar = this.g;
        if (bVar != null && !bVar.isDisposed()) {
            this.g.dispose();
        }
        this.g = e.w(1).g((long) timeOutTemp, TimeUnit.SECONDS).I(new a(this, deviceMac), new b(this));
        return this.a.e(300, TimeUnit.MILLISECONDS);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: com.leedarson.serviceimpl.blec075.beans.BleScanCacheBean} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void g(java.lang.String r11, java.lang.Integer r12) {
        /*
            r10 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            r9 = 1
            r1[r9] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r8] = r0
            java.lang.Class<java.lang.Integer> r0 = java.lang.Integer.class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 6565(0x19a5, float:9.2E-42)
            r2 = r10
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r10
            r0.m()
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.BleC075Service> r2 = com.leedarson.serviceinterface.BleC075Service.class
            java.lang.Object r1 = r1.g(r2)
            com.leedarson.serviceinterface.BleC075Service r1 = (com.leedarson.serviceinterface.BleC075Service) r1
            r2 = 0
            java.lang.String r3 = "BleConnectDevice"
            if (r1 == 0) goto L_0x008b
            timber.log.a$b r4 = timber.log.a.g(r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "LdsConnectDevice:   扫描超时-正在尝试使用蓝牙缓存池-蓝牙扫描缓存服务(可用) "
            r5.append(r6)
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r4.m(r5, r6)
            java.lang.String r4 = r0.b
            java.lang.Object r4 = r1.getBleCacheDeviceBeanByBzMac(r4)
            if (r4 == 0) goto L_0x00a5
            java.lang.String r4 = r0.b
            java.lang.Object r4 = r1.getBleCacheDeviceBeanByBzMac(r4)
            r2 = r4
            com.leedarson.serviceimpl.blec075.beans.BleScanCacheBean r2 = (com.leedarson.serviceimpl.blec075.beans.BleScanCacheBean) r2
            com.clj.fastble.data.BleDevice r4 = r2._bleDevice
            r4.z = r9
            timber.log.a$b r4 = timber.log.a.g(r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "LdsConnectDevice: 扫描超时-正在尝试使用蓝牙缓存池-蓝牙扫描缓存服务找到目标 _tempCache="
            r5.append(r6)
            r5.append(r2)
            java.lang.String r6 = ",deviceMac="
            r5.append(r6)
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r4.m(r5, r6)
            goto L_0x00a5
        L_0x008b:
            timber.log.a$b r4 = timber.log.a.g(r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "LdsConnectDevice:   扫描超时-正在尝试使用蓝牙缓存池-蓝牙扫描缓存服务(不可用) "
            r5.append(r6)
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r4.m(r5, r6)
        L_0x00a5:
            com.clj.fastble.data.BleDevice r4 = new com.clj.fastble.data.BleDevice
            r5 = 0
            r4.<init>((android.bluetooth.BluetoothDevice) r5)
            if (r2 == 0) goto L_0x00ca
            com.clj.fastble.data.BleDevice r4 = r2._bleDevice
            timber.log.a$b r3 = timber.log.a.g(r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "LdsConnectDevice:   扫描超时-正在尝试使用蓝牙缓存池-目标设备已锁定-正在投递 "
            r5.append(r6)
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r3.m(r5, r6)
            goto L_0x00e4
        L_0x00ca:
            timber.log.a$b r3 = timber.log.a.g(r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "LdsConnectDevice:   扫描超时-正在尝试使用蓝牙缓存池-目标设备未找到 "
            r5.append(r6)
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r3.m(r5, r6)
        L_0x00e4:
            io.reactivex.processors.c<com.clj.fastble.data.BleDevice> r3 = r0.a
            r3.onNext(r4)
            io.reactivex.processors.c<com.clj.fastble.data.BleDevice> r3 = r0.a
            r3.onComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.blec075.strategy.i.g(java.lang.String, java.lang.Integer):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6564, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("StrategyV2");
            g2.m("BLE.TRV seekForTargetBleDevice:寻找设备超时 mac=" + this.b + "  exception=" + throwable.toString(), new Object[0]);
        }
    }

    public e<BleDevice> b(ScanDeviceRuleListener checkTargetInterface, boolean z, String mac, String fromBz) {
        Class<String> cls = String.class;
        Object[] objArr = {checkTargetInterface, new Byte(z ? (byte) 1 : 0), mac, fromBz};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 6561, new Class[]{ScanDeviceRuleListener.class, Boolean.TYPE, cls, cls}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        this.b = mac;
        this.e = fromBz;
        this.f = checkTargetInterface;
        l();
        b bVar = this.h;
        if (bVar != null && !bVar.isDisposed()) {
            timber.log.a.g("BleConnectDevice").m("commonSeekForTargetBleDevice dispose", new Object[0]);
            this.h.dispose();
        }
        this.h = e.w(1).g(20, TimeUnit.SECONDS).I(new c(this, mac), d.c);
        return this.a.x(new a()).e(300, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ void d(String mac, Integer num) {
        Class[] clsArr = {String.class, Integer.class};
        if (!PatchProxy.proxy(new Object[]{mac, num}, this, changeQuickRedirect, false, 6563, clsArr, Void.TYPE).isSupported) {
            MeshScanLog.d("commonSeekForTargetBleDevice 扫描到设备:" + mac + "，进入调用stopScan逻辑");
            m();
        }
    }

    static /* synthetic */ void e(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 6562, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("BleConnectDevice");
            g2.m("commonSeekForTargetBleDevice throwable:" + throwable.getMessage(), new Object[0]);
        }
    }

    /* compiled from: BleConnectDeviceImproveStrategyV2 */
    public class a implements f<BleDevice, BleDevice> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6567, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((BleDevice) obj);
        }

        public BleDevice a(BleDevice bleDevice) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6566, new Class[]{BleDevice.class}, BleDevice.class);
            if (proxy.isSupported) {
                return (BleDevice) proxy.result;
            }
            a.b g = timber.log.a.g("BleConnectDevice");
            g.m("commonSeekForTargetBleDevice map bleDevice:" + bleDevice.c(), new Object[0]);
            return bleDevice;
        }
    }
}
