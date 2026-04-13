package com.leedarson.serviceimpl.base;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelUuid;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.util.ContextUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import meshsdk.MeshScanLog;
import meshsdk.util.BleCompat;

/* compiled from: LDSBleScanner */
public class c {
    private static c a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String b = "LDSBleScanner";
    /* access modifiers changed from: private */
    public e c;
    private BluetoothLeScanner d;
    private ScanCallback e;
    private boolean f = false;
    private Handler g;
    /* access modifiers changed from: private */
    public CopyOnWriteArraySet<d> h;
    SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<String> j = new ArrayList();
    private Set<AdvertisingDevice> k = new LinkedHashSet();
    /* access modifiers changed from: private */
    public LinkedList<Long> l = new LinkedList<>();
    private Runnable m = new C0124c();
    private long n = 0;
    private boolean o = false;
    private io.reactivex.processors.b<String> p = io.reactivex.processors.b.Y();
    private io.reactivex.disposables.b q;

    /* compiled from: LDSBleScanner */
    public enum e {
        Lollipop;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void a(c x0, int x1, String x2) {
        Object[] objArr = {x0, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6136, new Class[]{c.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.s(x1, x2);
        }
    }

    static /* synthetic */ void d(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6137, clsArr, Void.TYPE).isSupported) {
            x0.q(x1);
        }
    }

    static /* synthetic */ void e(c x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6138, new Class[]{c.class}, Void.TYPE).isSupported) {
            x0.t();
        }
    }

    static /* synthetic */ boolean g(c x0, LeScanFilter x1, ScanSettings x2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6139, new Class[]{c.class, LeScanFilter.class, ScanSettings.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.w(x1, x2);
    }

    public synchronized int x(d observer) {
        boolean hasPermission = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{observer}, this, changeQuickRedirect, false, 6110, new Class[]{d.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        this.h.add(observer);
        q("调用startRegisterScan,添加扫描监听者 fromBz:" + observer.onFromBz() + ",监听扫描数量:" + this.h.size());
        if (w.R()) {
            hasPermission = BleCompat.checkNeededPermission(BaseApplication.b());
        } else if (!w.f(BaseApplication.b()) || !BleCompat.checkNeededPermission(BaseApplication.b())) {
            hasPermission = false;
        }
        if (!hasPermission) {
            q(getClass().getSimpleName() + " 没有扫描权限, 跳过此次扫描, observer:" + observer.getClass().getSimpleName());
            return -1;
        }
        q("invoke startScanV2");
        y();
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b5, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void B(com.leedarson.serviceimpl.base.d r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x00b6 }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x00b6 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x00b6 }
            r4 = 0
            r5 = 6111(0x17df, float:8.563E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x00b6 }
            java.lang.Class<com.leedarson.serviceimpl.base.d> r7 = com.leedarson.serviceimpl.base.d.class
            r6[r2] = r7     // Catch:{ all -> 0x00b6 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x00b6 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00b6 }
            boolean r1 = r1.isSupported     // Catch:{ all -> 0x00b6 }
            if (r1 == 0) goto L_0x001f
            monitor-exit(r8)
            return
        L_0x001f:
            r1 = r8
            java.util.concurrent.CopyOnWriteArraySet<com.leedarson.serviceimpl.base.d> r2 = r1.h     // Catch:{ all -> 0x00b6 }
            boolean r2 = r2.remove(r9)     // Catch:{ all -> 0x00b6 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b6 }
            r3.<init>()     // Catch:{ all -> 0x00b6 }
            java.lang.String r4 = "业务成功移除扫描的回调监听:"
            r3.append(r4)     // Catch:{ all -> 0x00b6 }
            if (r9 == 0) goto L_0x0037
            java.lang.String r4 = r9.onFromBz()     // Catch:{ all -> 0x00b6 }
            goto L_0x0039
        L_0x0037:
            java.lang.String r4 = ""
        L_0x0039:
            r3.append(r4)     // Catch:{ all -> 0x00b6 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00b6 }
            r1.q(r3)     // Catch:{ all -> 0x00b6 }
            java.util.concurrent.CopyOnWriteArraySet<com.leedarson.serviceimpl.base.d> r3 = r1.h     // Catch:{ all -> 0x00b6 }
            int r3 = r3.size()     // Catch:{ all -> 0x00b6 }
            if (r3 != 0) goto L_0x0055
            java.lang.String r0 = "Android BLE stop scan.no observer !!"
            r1.q(r0)     // Catch:{ all -> 0x00b6 }
            r0 = 2
            r1.C(r0)     // Catch:{ all -> 0x00b6 }
            goto L_0x00b4
        L_0x0055:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ all -> 0x00b6 }
            java.lang.String r4 = "["
            r3.<init>(r4)     // Catch:{ all -> 0x00b6 }
            r4 = 0
            java.util.concurrent.CopyOnWriteArraySet<com.leedarson.serviceimpl.base.d> r5 = r1.h     // Catch:{ all -> 0x00b6 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x00b6 }
        L_0x0063:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x00b6 }
            if (r6 == 0) goto L_0x007f
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x00b6 }
            com.leedarson.serviceimpl.base.d r6 = (com.leedarson.serviceimpl.base.d) r6     // Catch:{ all -> 0x00b6 }
            if (r6 == 0) goto L_0x007e
            java.lang.String r7 = r6.onFromBz()     // Catch:{ all -> 0x00b6 }
            r3.append(r7)     // Catch:{ all -> 0x00b6 }
            java.lang.String r7 = ","
            r3.append(r7)     // Catch:{ all -> 0x00b6 }
            r4 = 1
        L_0x007e:
            goto L_0x0063
        L_0x007f:
            if (r4 == 0) goto L_0x008d
            java.lang.String r5 = r3.toString()     // Catch:{ all -> 0x00b6 }
            int r5 = r5.length()     // Catch:{ all -> 0x00b6 }
            int r5 = r5 - r0
            r3.deleteCharAt(r5)     // Catch:{ all -> 0x00b6 }
        L_0x008d:
            java.lang.String r0 = "]"
            r3.append(r0)     // Catch:{ all -> 0x00b6 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b6 }
            r0.<init>()     // Catch:{ all -> 0x00b6 }
            java.lang.String r5 = "ble scan 扫描监听器还有:"
            r0.append(r5)     // Catch:{ all -> 0x00b6 }
            java.util.concurrent.CopyOnWriteArraySet<com.leedarson.serviceimpl.base.d> r5 = r1.h     // Catch:{ all -> 0x00b6 }
            int r5 = r5.size()     // Catch:{ all -> 0x00b6 }
            r0.append(r5)     // Catch:{ all -> 0x00b6 }
            java.lang.String r5 = "个，不能stopScan，监听器如下:"
            r0.append(r5)     // Catch:{ all -> 0x00b6 }
            r0.append(r3)     // Catch:{ all -> 0x00b6 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b6 }
            r1.q(r0)     // Catch:{ all -> 0x00b6 }
        L_0x00b4:
            monitor-exit(r8)
            return
        L_0x00b6:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.base.c.B(com.leedarson.serviceimpl.base.d):void");
    }

    public static c k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 6113, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        return a;
    }

    public void l(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 6114, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            this.h = new CopyOnWriteArraySet<>();
            this.g = new Handler(handlerThread.getLooper());
            e eVar = e.Lollipop;
            this.c = eVar;
            switch (d.a[eVar.ordinal()]) {
                case 1:
                    m();
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: LDSBleScanner */
    public static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[e.values().length];
            a = iArr;
            try {
                iArr[e.Lollipop.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    /* compiled from: LDSBleScanner */
    public class a extends ScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onScanResult(int callbackType, ScanResult result) {
            Object[] objArr = {new Integer(callbackType), result};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6140, new Class[]{Integer.TYPE, ScanResult.class}, Void.TYPE).isSupported) {
                super.onScanResult(callbackType, result);
                BluetoothDevice device = result.getDevice();
                byte[] scanRecord = result.getScanRecord().getBytes();
                c.this.r(device, result.getRssi(), scanRecord, result.getScanRecord());
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            if (!PatchProxy.proxy(new Object[]{results}, this, changeQuickRedirect, false, 6141, new Class[]{List.class}, Void.TYPE).isSupported) {
                super.onBatchScanResults(results);
            }
        }

        public void onScanFailed(int errorCode) {
            if (!PatchProxy.proxy(new Object[]{new Integer(errorCode)}, this, changeQuickRedirect, false, 6142, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onScanFailed(errorCode);
                if (errorCode == 1) {
                    timber.log.a.c(" Fails to start scan as BLE scan with the same settings is already started by the app.", new Object[0]);
                    return;
                }
                c cVar = c.this;
                c.a(cVar, errorCode, "scanner failed by : " + errorCode);
            }
        }
    }

    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6117, new Class[0], Void.TYPE).isSupported) {
            this.d = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
            this.e = new a();
        }
    }

    /* compiled from: LDSBleScanner */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ BluetoothDevice c;
        final /* synthetic */ int d;
        final /* synthetic */ byte[] f;
        final /* synthetic */ ScanRecord q;

        b(BluetoothDevice bluetoothDevice, int i, byte[] bArr, ScanRecord scanRecord) {
            this.c = bluetoothDevice;
            this.d = i;
            this.f = bArr;
            this.q = scanRecord;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6143, new Class[0], Void.TYPE).isSupported) {
                if (c.this.h != null && c.this.h.size() > 0) {
                    Iterator<ScannerObserver> iterator = c.this.h.iterator();
                    while (iterator.hasNext()) {
                        ((d) iterator.next()).onLeScan(this.c, this.d, this.f, this.q);
                    }
                }
            }
        }
    }

    public void r(BluetoothDevice dev, int rssi, byte[] scanRecord, ScanRecord record) {
        Object[] objArr = {dev, new Integer(rssi), scanRecord, record};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6118, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            this.g.post(new b(dev, rssi, scanRecord, record));
        }
    }

    public synchronized void C(int stopReason) {
        if (!PatchProxy.proxy(new Object[]{new Integer(stopReason)}, this, changeQuickRedirect, false, 6119, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            u(stopReason);
            if (!this.f) {
                q("当前没在扫描，没必要停止扫描");
                return;
            }
            Set<AdvertisingDevice> set = this.k;
            if (set != null) {
                set.clear();
            }
            D();
            if (this.c == e.Lollipop) {
                q("check stopLollipopScan");
                A();
            }
            this.f = false;
        }
    }

    private synchronized void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6120, new Class[0], Void.TYPE).isSupported) {
            Set<AdvertisingDevice> set = this.k;
            if (set != null) {
                set.clear();
            }
            if (this.f) {
                MeshScanLog.d(getClass().getSimpleName() + " 当前正在扫描，跳过");
                return;
            }
            this.f = true;
            q("设置当前扫描状态:isScanning=true");
            this.g.removeCallbacksAndMessages((Object) null);
            long delay = 0;
            long now = System.currentTimeMillis();
            if (this.l.size() > 0) {
                Long first = this.l.peek();
                if (now - first.longValue() >= 35000) {
                    q("本地扫描触发时间:" + j(now) + ",与第一次触发的时间:" + j(first.longValue()) + ",间隔大于35s,移除队列头数据");
                    this.l.poll();
                    t();
                } else if (this.l.size() >= 5) {
                    long dt = now - first.longValue();
                    delay = 35000 - dt;
                    q("扫描队列已超过5次，本次扫描时间: " + j(now) + ",与第一次扫描时间间隔:" + dt + ",延迟:" + delay + "扫描");
                    com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(this).c(c.class.getName()).t("LdsBleMesh");
                    StringBuilder sb = new StringBuilder();
                    sb.append("搜索延迟执行：");
                    sb.append(delay);
                    t.p(sb.toString()).a().b();
                }
            }
            z(delay);
        }
    }

    private void z(long delay) {
        Object[] objArr = {new Long(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6121, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            MeshScanLog.d("延迟：" + delay + "扫描");
            this.g.removeCallbacks(this.m);
            this.g.postDelayed(this.m, delay);
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6122, new Class[0], Void.TYPE).isSupported) {
            this.g.removeCallbacks(this.m);
        }
    }

    /* renamed from: com.leedarson.serviceimpl.base.c$c  reason: collision with other inner class name */
    /* compiled from: LDSBleScanner */
    public class C0124c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0124c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6144, new Class[0], Void.TYPE).isSupported) {
                long now = System.currentTimeMillis();
                if (c.this.l.size() == 5) {
                    c.d(c.this, "当前扫描时间队列已经5个了，移除头");
                    c.this.l.poll();
                }
                c.d(c.this, "加入本次扫描时间到队列");
                c.this.l.add(Long.valueOf(now));
                c.e(c.this);
                boolean scanStarted = false;
                if (c.this.c == e.Lollipop && BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    MeshScanLog.d("startLollipopScan");
                    scanStarted = c.g(c.this, (LeScanFilter) null, (ScanSettings) null);
                }
                if (!scanStarted) {
                    c.d(c.this, "startScan failed");
                    c.a(c.this, 6, "scan action start failed");
                }
            }
        }
    }

    private void s(int errorCode, String desc) {
        if (!PatchProxy.proxy(new Object[]{new Integer(errorCode), desc}, this, changeQuickRedirect, false, 6123, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            q("onScanFailed errorCode:" + errorCode + ",desc:" + desc);
            this.f = false;
            this.o = false;
            CopyOnWriteArraySet<d> copyOnWriteArraySet = this.h;
            if (copyOnWriteArraySet != null && copyOnWriteArraySet.size() > 0) {
                Iterator<d> it = this.h.iterator();
                while (it.hasNext()) {
                    it.next().onScanFail(errorCode);
                }
            }
        }
    }

    private boolean w(LeScanFilter leScanFilter, ScanSettings scanSettings) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{leScanFilter, scanSettings}, this, changeQuickRedirect, false, 6124, new Class[]{LeScanFilter.class, ScanSettings.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean flagOver8Seconds = this.n < System.currentTimeMillis() - 8000;
        StringBuilder sb = new StringBuilder();
        sb.append("距离上一次开始扫 ");
        sb.append(flagOver8Seconds ? "超过了" : "没有超过");
        sb.append("8s");
        q(sb.toString());
        io.reactivex.disposables.b bVar = this.q;
        if (bVar != null && !bVar.isDisposed()) {
            this.q.dispose();
        }
        if (flagOver8Seconds || !this.o) {
            this.o = true;
            this.n = System.currentTimeMillis();
            q("更新最新扫描时间:" + j(this.n));
            q("LDSBleScanner.startLollipopScan 蓝牙扫描被调用 mLeScanner.startScan");
            BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
            this.d = bluetoothLeScanner;
            if (bluetoothLeScanner == null || this.e == null) {
                return false;
            }
            List<ScanFilter> scanFilters = i(leScanFilter);
            if (scanSettings == null) {
                scanSettings = h();
            }
            q("真正调用sdk扫描接口 ScannerType Lollipop startLeScan invoke");
            this.d.startScan(scanFilters, scanSettings, this.e);
            return true;
        }
        q("当前还在扫描中 并且 距离上一次扫描，没有超过8s，无必要重复扫描");
        return true;
    }

    private void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6125, new Class[0], Void.TYPE).isSupported) {
            if (this.d != null && this.e != null) {
                q("发送延迟结束扫描的消息 LDSBleScanner.stopLollipopScan");
                v();
                this.p.onNext("结束扫描");
            }
        }
    }

    private void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6126, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.q;
            if (bVar != null && !bVar.isDisposed()) {
                this.q.dispose();
            }
            long delay = 7000;
            if (this.h.size() == 0) {
                delay = 0;
            }
            this.q = this.p.g(delay, TimeUnit.MILLISECONDS).I(new a(this, delay), b.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ void o(long tempDelay, String str) {
        if (!PatchProxy.proxy(new Object[]{new Long(tempDelay), str}, this, changeQuickRedirect, false, 6135, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
            this.o = false;
            q("收到:" + tempDelay + "s后停止扫描消息，触发调用sdk停止扫描的接口，设置_isScanningFlag=false stopLeScan invoke");
            this.d.stopScan(this.e);
        }
    }

    static /* synthetic */ void p(Throwable throwable) {
    }

    private List<ScanFilter> i(LeScanFilter filter) {
        int i2 = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filter}, this, changeQuickRedirect, false, 6129, new Class[]{LeScanFilter.class}, List.class);
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

    private ScanSettings h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6130, new Class[0], ScanSettings.class);
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
        MeshScanLog.d("使用默认的scanSetting");
        return builder.build();
    }

    private void q(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6131, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshScanLog.d(msg);
        }
    }

    private String j(long time) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(time)}, this, changeQuickRedirect, false, 6132, new Class[]{Long.TYPE}, String.class);
        return proxy.isSupported ? (String) proxy.result : this.i.format(new Date(time));
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6133, new Class[0], Void.TYPE).isSupported) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("最新扫描时间队列:[");
            boolean hasDot = false;
            Iterator it = this.l.iterator();
            while (it.hasNext()) {
                stringBuffer.append(this.i.format((Long) it.next()));
                stringBuffer.append(",");
                hasDot = true;
            }
            if (hasDot) {
                stringBuffer.deleteCharAt(stringBuffer.toString().length() - 1);
            }
            stringBuffer.append("]");
            q(stringBuffer.toString());
        }
    }

    private void u(int stopReason) {
        Object[] objArr = {new Integer(stopReason)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6134, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (stopReason) {
                case 1:
                    q("蓝牙被关闭，准备停止扫描");
                    return;
                case 2:
                    q("没有业务在监听扫描了，准备停止扫描");
                    return;
                default:
                    return;
            }
        }
    }
}
