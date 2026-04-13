package com.leedarson.serviceimpl.bledebug.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelUuid;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.leedarson.serviceinterface.event.BleDeviceFoundEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import com.telink.ble.mesh.core.ble.LeScanSetting;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.util.ContextUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import meshsdk.MeshLog;

/* compiled from: BleScanner */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public g a;
    private BluetoothAdapter.LeScanCallback b;
    private BluetoothLeScanner c;
    private ScanCallback d;
    private final Object e = new Object();
    private boolean f = false;
    /* access modifiers changed from: private */
    public f g;
    /* access modifiers changed from: private */
    public LeScanFilter h;
    /* access modifiers changed from: private */
    public LeScanSetting i;
    private Handler j;
    private long k = 0;
    /* access modifiers changed from: private */
    public boolean l = false;
    private BleDebugController m;
    private Set<AdvertisingDevice> n = new LinkedHashSet();
    /* access modifiers changed from: private */
    public LinkedList<Long> o = new LinkedList<>();
    private Runnable p = new c();
    private Runnable q = new d();

    /* compiled from: BleScanner */
    public interface f {
        void a();

        void b();

        void c(boolean z);

        void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr);

        void onScanFail(int i);
    }

    /* compiled from: BleScanner */
    public enum g {
        DEFAULT,
        Lollipop;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void d(a x0, long x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Long(x1)}, (Object) null, changeQuickRedirect, true, 6739, new Class[]{a.class, Long.TYPE}, Void.TYPE).isSupported) {
            x0.y(x1);
        }
    }

    static /* synthetic */ void e(a x0, BluetoothDevice x1, int x2, byte[] x3) {
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6735, new Class[]{a.class, BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            x0.q(x1, x2, x3);
        }
    }

    static /* synthetic */ void f(a x0, int x1, String x2) {
        Object[] objArr = {x0, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6736, new Class[]{a.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.r(x1, x2);
        }
    }

    static /* synthetic */ boolean j(a x0, LeScanFilter x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6737, new Class[]{a.class, LeScanFilter.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.u(x1);
    }

    static /* synthetic */ boolean k(a x0, LeScanFilter x1, ScanSettings x2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6738, new Class[]{a.class, LeScanFilter.class, ScanSettings.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.v(x1, x2);
    }

    private a() {
    }

    public void t(f scannerCallback) {
        this.g = scannerCallback;
    }

    public a(g scannerType, HandlerThread handlerThread, BleDebugController meshController) {
        this.m = meshController;
        this.j = new Handler(handlerThread.getLooper());
        if (scannerType == null || !ContextUtil.a()) {
            this.a = g.DEFAULT;
        } else {
            this.a = scannerType;
        }
        switch (e.a[this.a.ordinal()]) {
            case 1:
                o();
                return;
            case 2:
                p();
                return;
            default:
                return;
        }
    }

    /* compiled from: BleScanner */
    public static /* synthetic */ class e {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[g.values().length];
            a = iArr;
            try {
                iArr[g.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[g.Lollipop.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.bledebug.ble.a$a  reason: collision with other inner class name */
    /* compiled from: BleScanner */
    public class C0128a implements BluetoothAdapter.LeScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0128a() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            String[] strArr;
            int i = 0;
            if (!PatchProxy.proxy(new Object[]{device, new Integer(rssi), scanRecord}, this, changeQuickRedirect, false, 6740, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
                a.this.s(device, rssi, scanRecord);
                boolean unused = a.this.l = true;
                if (a.this.h != null) {
                    LeScanFilter filter = a.this.h;
                    String[] strArr2 = filter.c;
                    if (!(strArr2 == null || strArr2.length == 0)) {
                        int length = strArr2.length;
                        int i2 = 0;
                        while (i2 < length) {
                            if (!strArr2[i2].equalsIgnoreCase(device.getAddress())) {
                                i2++;
                            } else {
                                return;
                            }
                        }
                    }
                    boolean isTarget = true;
                    String[] strArr3 = filter.b;
                    if (strArr3 != null && strArr3.length != 0) {
                        isTarget = false;
                        int length2 = strArr3.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length2) {
                                break;
                            } else if (strArr3[i3].equalsIgnoreCase(device.getAddress())) {
                                isTarget = true;
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                    if (isTarget && (strArr = filter.d) != null && strArr.length != 0) {
                        isTarget = false;
                        String deviceName = device.getName();
                        if (!TextUtils.isEmpty(device.getName())) {
                            String[] strArr4 = filter.d;
                            int length3 = strArr4.length;
                            while (true) {
                                if (i >= length3) {
                                    break;
                                } else if (deviceName.equals(strArr4[i])) {
                                    isTarget = true;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    }
                    if (isTarget) {
                        a.e(a.this, device, rssi, scanRecord);
                        return;
                    }
                    return;
                }
                a.e(a.this, device, rssi, scanRecord);
            }
        }
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6715, new Class[0], Void.TYPE).isSupported) {
            this.b = new C0128a();
        }
    }

    /* compiled from: BleScanner */
    public class b extends ScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onScanResult(int callbackType, ScanResult result) {
            String[] strArr;
            int i = 0;
            if (!PatchProxy.proxy(new Object[]{new Integer(callbackType), result}, this, changeQuickRedirect, false, 6741, new Class[]{Integer.TYPE, ScanResult.class}, Void.TYPE).isSupported) {
                super.onScanResult(callbackType, result);
                boolean unused = a.this.l = true;
                BluetoothDevice device = result.getDevice();
                byte[] scanRecord = result.getScanRecord().getBytes();
                int rssi = result.getRssi();
                a.this.s(device, rssi, scanRecord);
                if (!(a.this.h == null || (strArr = a.this.h.c) == null || strArr.length == 0)) {
                    int length = strArr.length;
                    while (i < length) {
                        if (!strArr[i].equalsIgnoreCase(device.getAddress())) {
                            i++;
                        } else {
                            return;
                        }
                    }
                }
                a.e(a.this, device, rssi, scanRecord);
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            if (!PatchProxy.proxy(new Object[]{results}, this, changeQuickRedirect, false, 6742, new Class[]{List.class}, Void.TYPE).isSupported) {
                super.onBatchScanResults(results);
            }
        }

        public void onScanFailed(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6743, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onScanFailed(errorCode);
                a aVar = a.this;
                a.f(aVar, errorCode, "scanner failed by : " + errorCode);
            }
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6716, new Class[0], Void.TYPE).isSupported) {
            this.c = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
            this.d = new b();
        }
    }

    public void s(BluetoothDevice dev, int rssi, byte[] scanRecord) {
        Object[] objArr = {dev, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6717, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            if (this.n.add(new AdvertisingDevice(dev, rssi, scanRecord))) {
                MeshLog.v("post to ble thread->" + Thread.currentThread().getName() + ",scanRecord:" + com.leedarson.base.utils.e.a(scanRecord));
                BleDeviceFoundEvent event = new BleDeviceFoundEvent(1);
                event.bluetoothDevice = dev;
                event.rssi = rssi;
                event.scanRecord = scanRecord;
                org.greenrobot.eventbus.c.c().l(event);
                return;
            }
            MeshLog.v("device that wait for posting to ble already exist!!!:" + dev.getAddress());
        }
    }

    private void q(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
        f fVar;
        Object[] objArr = {bluetoothDevice, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6718, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            if (scanRecord != null && (fVar = this.g) != null) {
                fVar.onLeScan(bluetoothDevice, rssi, scanRecord);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void B() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0048 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0048 }
            r4 = 0
            r5 = 6719(0x1a3f, float:9.415E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0048 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0048 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0048 }
            boolean r1 = r1.isSupported     // Catch:{ all -> 0x0048 }
            if (r1 == 0) goto L_0x0018
            monitor-exit(r8)
            return
        L_0x0018:
            r1 = r8
            boolean r2 = r1.f     // Catch:{ all -> 0x0048 }
            if (r2 != 0) goto L_0x001f
            monitor-exit(r8)
            return
        L_0x001f:
            java.util.Set<com.telink.ble.mesh.entity.AdvertisingDevice> r2 = r1.n     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0026
            r2.clear()     // Catch:{ all -> 0x0048 }
        L_0x0026:
            r1.D()     // Catch:{ all -> 0x0048 }
            r1.C()     // Catch:{ all -> 0x0048 }
            com.leedarson.serviceimpl.bledebug.ble.a$g r2 = r1.a     // Catch:{ all -> 0x0048 }
            com.leedarson.serviceimpl.bledebug.ble.a$g r3 = com.leedarson.serviceimpl.bledebug.ble.a.g.DEFAULT     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x0036
            r1.z()     // Catch:{ all -> 0x0048 }
            goto L_0x003d
        L_0x0036:
            com.leedarson.serviceimpl.bledebug.ble.a$g r3 = com.leedarson.serviceimpl.bledebug.ble.a.g.Lollipop     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x003d
            r1.A()     // Catch:{ all -> 0x0048 }
        L_0x003d:
            r1.f = r0     // Catch:{ all -> 0x0048 }
            com.leedarson.serviceimpl.bledebug.ble.a$f r0 = r1.g     // Catch:{ all -> 0x0048 }
            if (r0 == 0) goto L_0x0046
            r0.b()     // Catch:{ all -> 0x0048 }
        L_0x0046:
            monitor-exit(r8)
            return
        L_0x0048:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.bledebug.ble.a.B():void");
    }

    public synchronized void w(@Nullable LeScanFilter leScanFilter, @Nullable LeScanSetting leScanSetting) {
        if (!PatchProxy.proxy(new Object[]{leScanFilter, leScanSetting}, this, changeQuickRedirect, false, 6721, new Class[]{LeScanFilter.class, LeScanSetting.class}, Void.TYPE).isSupported) {
            Set<AdvertisingDevice> set = this.n;
            if (set != null) {
                set.clear();
            }
            this.j.removeCallbacksAndMessages((Object) null);
            if (this.f) {
                B();
            }
            this.l = false;
            this.f = true;
            this.h = leScanFilter;
            if (leScanSetting == null) {
                this.i = LeScanSetting.a();
            } else {
                this.i = leScanSetting;
            }
            long delay = 0;
            long now = System.currentTimeMillis();
            if (this.o.size() > 0) {
                Long first = this.o.peek();
                if (now - first.longValue() >= 35000) {
                    this.o.poll();
                } else if (this.o.size() >= 5) {
                    delay = 35000 - (now - first.longValue());
                    MeshLog.w("搜索延迟执行：" + delay);
                    com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(this).c(a.class.getName()).t("LdsBleMesh");
                    t.p("搜索延迟执行：" + delay).a().b();
                }
            }
            x(delay);
        }
    }

    private void y(long timeout) {
        Object[] objArr = {new Long(timeout)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6722, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            MeshLog.e("开启蓝牙扫描 startScanningTimeoutTask timeout:" + timeout);
            this.j.removeCallbacks(this.p);
            this.j.postDelayed(this.p, timeout);
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6723, new Class[0], Void.TYPE).isSupported) {
            MeshLog.d("stopScanningTimeoutTask");
            this.j.removeCallbacks(this.p);
        }
    }

    /* compiled from: BleScanner */
    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6744, new Class[0], Void.TYPE).isSupported) {
                a.this.B();
                if (a.this.g != null) {
                    a.this.g.c(a.this.l);
                }
            }
        }
    }

    private void x(long delay) {
        Object[] objArr = {new Long(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6724, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            this.j.removeCallbacks(this.q);
            this.j.postDelayed(this.q, delay);
        }
    }

    private void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6725, new Class[0], Void.TYPE).isSupported) {
            this.j.removeCallbacks(this.q);
        }
    }

    /* compiled from: BleScanner */
    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6745, new Class[0], Void.TYPE).isSupported) {
                long now = System.currentTimeMillis();
                if (a.this.o.size() == 5) {
                    a.this.o.poll();
                }
                a.this.o.add(Long.valueOf(now));
                boolean scanStarted = false;
                if (a.this.a == g.DEFAULT) {
                    a aVar = a.this;
                    scanStarted = a.j(aVar, aVar.h);
                } else if (a.this.a == g.Lollipop) {
                    a aVar2 = a.this;
                    scanStarted = a.k(aVar2, aVar2.h, (ScanSettings) null);
                }
                a aVar3 = a.this;
                a.d(aVar3, aVar3.i.b);
                if (!scanStarted) {
                    a.f(a.this, 6, "scan action start failed");
                } else if (a.this.g != null) {
                    a.this.g.a();
                }
            }
        }
    }

    private void r(int errorCode, String str) {
        if (!PatchProxy.proxy(new Object[]{new Integer(errorCode), str}, this, changeQuickRedirect, false, 6726, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            this.f = false;
            f fVar = this.g;
            if (fVar != null) {
                fVar.onScanFail(errorCode);
            }
        }
    }

    private boolean u(LeScanFilter leScanFilter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{leScanFilter}, this, changeQuickRedirect, false, 6727, new Class[]{LeScanFilter.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.b == null) {
            return false;
        }
        return BluetoothAdapter.getDefaultAdapter().startLeScan(leScanFilter == null ? null : leScanFilter.a, this.b);
    }

    private boolean v(LeScanFilter leScanFilter, ScanSettings scanSettings) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{leScanFilter, scanSettings}, this, changeQuickRedirect, false, 6728, new Class[]{LeScanFilter.class, ScanSettings.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.c == null || this.d == null) {
            return false;
        }
        List<ScanFilter> scanFilters = n(leScanFilter);
        if (scanSettings == null) {
            scanSettings = m();
        }
        this.c.startScan(scanFilters, scanSettings, this.d);
        return true;
    }

    private void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6729, new Class[0], Void.TYPE).isSupported) {
            if (this.b != null) {
                BluetoothAdapter.getDefaultAdapter().stopLeScan(this.b);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void A() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 6730(0x1a4a, float:9.431E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.bluetooth.le.BluetoothLeScanner r1 = r0.c
            if (r1 == 0) goto L_0x0022
            android.bluetooth.le.ScanCallback r2 = r0.d
            if (r2 == 0) goto L_0x0022
            r1.stopScan(r2)
        L_0x0022:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.bledebug.ble.a.A():void");
    }

    private List<ScanFilter> n(LeScanFilter filter) {
        int i2 = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filter}, this, changeQuickRedirect, false, 6733, new Class[]{LeScanFilter.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        if (filter == null) {
            return null;
        }
        Integer[] numArr = new Integer[3];
        UUID[] uuidArr = filter.a;
        numArr[0] = Integer.valueOf(uuidArr == null ? 0 : uuidArr.length);
        String[] strArr = filter.b;
        numArr[1] = Integer.valueOf(strArr == null ? 0 : strArr.length);
        String[] strArr2 = filter.d;
        if (strArr2 != null) {
            i2 = strArr2.length;
        }
        numArr[2] = Integer.valueOf(i2);
        int maxSize = ((Integer) Collections.max(Arrays.asList(numArr))).intValue();
        if (maxSize == 0) {
            return null;
        }
        List<ScanFilter> results = new ArrayList<>();
        for (int i3 = 0; i3 < maxSize; i3++) {
            ScanFilter.Builder builder = new ScanFilter.Builder();
            UUID[] uuidArr2 = filter.a;
            if (uuidArr2 != null && uuidArr2.length > i3) {
                builder.setServiceUuid(ParcelUuid.fromString(uuidArr2[i3].toString()));
            }
            String[] strArr3 = filter.b;
            if (strArr3 != null && strArr3.length > i3) {
                builder.setDeviceAddress(strArr3[i3]);
            }
            String[] strArr4 = filter.d;
            if (strArr4 != null && strArr4.length > i3) {
                builder.setDeviceName(strArr4[i3]);
            }
            results.add(builder.build());
        }
        return results;
    }

    private ScanSettings m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6734, new Class[0], ScanSettings.class);
        if (proxy.isSupported) {
            return (ScanSettings) proxy.result;
        }
        ScanSettings.Builder builder = new ScanSettings.Builder().setScanMode(2).setReportDelay(0);
        int i2 = ContextUtil.a;
        if (i2 >= 23) {
            builder.setCallbackType(1);
            builder.setMatchMode(1);
            builder.setNumOfMatches(3);
        }
        if (i2 >= 26) {
            builder.setLegacy(true);
            builder.setPhy(255);
        }
        return builder.build();
    }
}
