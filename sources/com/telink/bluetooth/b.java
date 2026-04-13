package com.telink.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.UUID;

/* compiled from: LeBluetooth */
public final class b {
    private static b a;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Handler b = new Handler();
    /* access modifiers changed from: private */
    public d c = new d(this, (a) null);
    private volatile boolean d = false;
    private volatile boolean e = false;
    private BluetoothAdapter.LeScanCallback f;
    private BluetoothLeScanner g;
    private ScanCallback h;
    /* access modifiers changed from: private */
    public c i;
    private BluetoothAdapter j;
    /* access modifiers changed from: private */
    public Context k;
    private boolean l = false;

    /* compiled from: LeBluetooth */
    public interface c {
        void a();

        void b();

        void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr);

        void onScanFail(int i);
    }

    private b() {
    }

    public static b e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 13421, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        synchronized (b.class) {
            if (a == null) {
                a = new b();
            }
        }
        return a;
    }

    public boolean g() {
        boolean z;
        synchronized (this) {
            z = this.e;
        }
        return z;
    }

    public void k(c callback) {
        if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 13422, new Class[]{c.class}, Void.TYPE).isSupported) {
            this.i = callback;
            if (callback != null) {
                if (h()) {
                    this.h = new a();
                } else {
                    this.f = new C0215b();
                }
            }
        }
    }

    /* compiled from: LeBluetooth */
    public class a extends ScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onScanResult(int i, ScanResult result) {
            Object[] objArr = {new Integer(i), result};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13430, new Class[]{Integer.TYPE, ScanResult.class}, Void.TYPE).isSupported) {
                if (b.this.h()) {
                    byte[] scanRecord = null;
                    if (result.getScanRecord() != null) {
                        scanRecord = result.getScanRecord().getBytes();
                    }
                    if (b.this.i != null) {
                        b.this.i.onLeScan(result.getDevice(), result.getRssi(), scanRecord);
                    }
                }
                if (b.this.i() && !com.telink.util.b.a(b.this.k)) {
                    b.this.b.removeCallbacks(b.this.c);
                }
            }
        }

        public void onScanFailed(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13431, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (errorCode != 1 && b.this.i != null) {
                    b.this.i.onScanFail(4);
                }
            }
        }
    }

    /* renamed from: com.telink.bluetooth.b$b  reason: collision with other inner class name */
    /* compiled from: LeBluetooth */
    public class C0215b implements BluetoothAdapter.LeScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0215b() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Object[] objArr = {device, new Integer(rssi), scanRecord};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13432, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
                if (b.this.i != null) {
                    b.this.i.onLeScan(device, rssi, scanRecord);
                }
                if (b.this.i() && !com.telink.util.b.a(b.this.k)) {
                    b.this.b.removeCallbacks(b.this.c);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        com.telink.bluetooth.d.d("LeBluetooth#StartScan");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003c, code lost:
        if (f() != false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003f, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r1.d = true;
        j(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0046, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0048, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean l(java.util.UUID[] r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0052 }
            r8 = 0
            r1[r8] = r10     // Catch:{ all -> 0x0052 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0052 }
            r4 = 0
            r5 = 13423(0x346f, float:1.881E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0052 }
            java.lang.Class<java.util.UUID[]> r2 = java.util.UUID[].class
            r6[r8] = r2     // Catch:{ all -> 0x0052 }
            java.lang.Class r7 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0052 }
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0052 }
            boolean r2 = r1.isSupported     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x0027
            java.lang.Object r10 = r1.result     // Catch:{ all -> 0x0052 }
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch:{ all -> 0x0052 }
            boolean r10 = r10.booleanValue()     // Catch:{ all -> 0x0052 }
            monitor-exit(r9)
            return r10
        L_0x0027:
            r1 = r9
            monitor-enter(r1)     // Catch:{ all -> 0x0052 }
            boolean r2 = r1.e     // Catch:{ all -> 0x004f }
            if (r2 != 0) goto L_0x004c
            boolean r2 = r1.d     // Catch:{ all -> 0x004f }
            if (r2 == 0) goto L_0x0032
            goto L_0x004c
        L_0x0032:
            monitor-exit(r1)     // Catch:{ all -> 0x004f }
            java.lang.String r2 = "LeBluetooth#StartScan"
            com.telink.bluetooth.d.d(r2)     // Catch:{ all -> 0x0052 }
            boolean r2 = r1.f()     // Catch:{ all -> 0x0052 }
            if (r2 != 0) goto L_0x0040
            monitor-exit(r9)
            return r8
        L_0x0040:
            monitor-enter(r1)     // Catch:{ all -> 0x0052 }
            r1.d = r0     // Catch:{ all -> 0x0049 }
            r1.j(r10)     // Catch:{ all -> 0x0049 }
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            monitor-exit(r9)
            return r0
        L_0x0049:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            throw r0     // Catch:{ all -> 0x0052 }
        L_0x004c:
            monitor-exit(r1)     // Catch:{ all -> 0x004f }
            monitor-exit(r9)
            return r0
        L_0x004f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004f }
            throw r0     // Catch:{ all -> 0x0052 }
        L_0x0052:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.b.l(java.util.UUID[]):boolean");
    }

    private void j(UUID[] serviceUUIDs) {
        if (!PatchProxy.proxy(new Object[]{serviceUUIDs}, this, changeQuickRedirect, false, 13424, new Class[]{UUID[].class}, Void.TYPE).isSupported) {
            if (i() && !com.telink.util.b.a(this.k)) {
                this.b.removeCallbacks(this.c);
                this.b.postDelayed(this.c, 10000);
            } else if (h()) {
                BluetoothLeScanner bluetoothLeScanner = this.j.getBluetoothLeScanner();
                this.g = bluetoothLeScanner;
                if (bluetoothLeScanner == null) {
                    synchronized (this) {
                        this.e = false;
                    }
                    c cVar = this.i;
                    if (cVar != null) {
                        cVar.onScanFail(4);
                        return;
                    }
                    return;
                }
                bluetoothLeScanner.startScan(this.h);
                synchronized (this) {
                    this.e = true;
                }
                this.i.a();
            } else if (!this.j.startLeScan(serviceUUIDs, this.f)) {
                synchronized (this) {
                    this.e = false;
                }
                c cVar2 = this.i;
                if (cVar2 != null) {
                    cVar2.onScanFail(4);
                }
            } else {
                synchronized (this) {
                    this.e = true;
                }
                this.i.a();
            }
        }
    }

    public boolean h() {
        return Build.VERSION.SDK_INT >= 21 && this.l;
    }

    public boolean i() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002b, code lost:
        if (h() == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002d, code lost:
        r2 = r1.g;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002f, code lost:
        if (r2 == null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0031, code lost:
        r2.stopScan(r1.h);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0037, code lost:
        r2 = r1.j;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0039, code lost:
        if (r2 == null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003b, code lost:
        r2.stopLeScan(r1.f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        com.telink.bluetooth.d.a("蓝牙停止异常");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void m() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x005d }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x005d }
            r4 = 0
            r5 = 13425(0x3471, float:1.8812E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x005d }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x005d }
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x005d }
            boolean r1 = r1.isSupported     // Catch:{ all -> 0x005d }
            if (r1 == 0) goto L_0x0018
            monitor-exit(r8)
            return
        L_0x0018:
            r1 = r8
            java.lang.String r2 = "LeBluetooth#stopScan"
            com.telink.bluetooth.d.d(r2)     // Catch:{ all -> 0x005d }
            monitor-enter(r1)     // Catch:{ all -> 0x005d }
            boolean r2 = r1.e     // Catch:{ all -> 0x005a }
            if (r2 != 0) goto L_0x0026
            monitor-exit(r1)     // Catch:{ all -> 0x005a }
            monitor-exit(r8)
            return
        L_0x0026:
            monitor-exit(r1)     // Catch:{ all -> 0x005a }
            boolean r2 = r1.h()     // Catch:{ Exception -> 0x0041 }
            if (r2 == 0) goto L_0x0037
            android.bluetooth.le.BluetoothLeScanner r2 = r1.g     // Catch:{ Exception -> 0x0041 }
            if (r2 == 0) goto L_0x0040
            android.bluetooth.le.ScanCallback r3 = r1.h     // Catch:{ Exception -> 0x0041 }
            r2.stopScan(r3)     // Catch:{ Exception -> 0x0041 }
            goto L_0x0040
        L_0x0037:
            android.bluetooth.BluetoothAdapter r2 = r1.j     // Catch:{ Exception -> 0x0041 }
            if (r2 == 0) goto L_0x0040
            android.bluetooth.BluetoothAdapter$LeScanCallback r3 = r1.f     // Catch:{ Exception -> 0x0041 }
            r2.stopLeScan(r3)     // Catch:{ Exception -> 0x0041 }
        L_0x0040:
            goto L_0x0048
        L_0x0041:
            r2 = move-exception
            java.lang.String r3 = "蓝牙停止异常"
            com.telink.bluetooth.d.a(r3)     // Catch:{ all -> 0x005d }
        L_0x0048:
            monitor-enter(r1)     // Catch:{ all -> 0x005d }
            r1.d = r0     // Catch:{ all -> 0x0057 }
            r1.e = r0     // Catch:{ all -> 0x0057 }
            monitor-exit(r1)     // Catch:{ all -> 0x0057 }
            com.telink.bluetooth.b$c r0 = r1.i     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0055
            r0.b()     // Catch:{ all -> 0x005d }
        L_0x0055:
            monitor-exit(r8)
            return
        L_0x0057:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0057 }
            throw r0     // Catch:{ all -> 0x005d }
        L_0x005a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x005a }
            throw r0     // Catch:{ all -> 0x005d }
        L_0x005d:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.b.m():void");
    }

    public boolean f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13426, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        BluetoothAdapter bluetoothAdapter = this.j;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            return false;
        }
        return true;
    }

    /* compiled from: LeBluetooth */
    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private d() {
        }

        /* synthetic */ d(b x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13433, new Class[0], Void.TYPE).isSupported) {
                if (b.this.i != null) {
                    b.this.i.onScanFail(8);
                }
            }
        }
    }
}
