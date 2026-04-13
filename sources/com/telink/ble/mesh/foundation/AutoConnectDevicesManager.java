package com.telink.ble.mesh.foundation;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.Nullable;
import com.leedarson.serviceimpl.elkstrays.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;

public class AutoConnectDevicesManager {
    private static final AutoConnectDevicesManager a = new AutoConnectDevicesManager();
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean b = false;
    private int c = -1;
    public List<AutoConnectDevice> d = new ArrayList();

    public static AutoConnectDevicesManager d() {
        return a;
    }

    public static class AutoConnectDevice implements Comparable<AutoConnectDevice> {
        public static ChangeQuickRedirect changeQuickRedirect;
        private BluetoothDevice c;
        private int d;
        private int f = -1;
        private int q;

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 13068, new Class[]{Object.class}, Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : a((AutoConnectDevice) obj);
        }

        public BluetoothDevice b() {
            return this.c;
        }

        public void f(BluetoothDevice device) {
            this.c = device;
        }

        public int e() {
            return this.d;
        }

        public void k(int rssi) {
            this.d = rssi;
        }

        public int d() {
            return this.f;
        }

        public void j(int onoff) {
            this.f = onoff;
        }

        public int c() {
            return this.q;
        }

        public void h(int dimming) {
            this.q = dimming;
        }

        public boolean equals(@Nullable Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 13066, new Class[]{Object.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            return ((AutoConnectDevice) obj).c.getAddress().equals(this.c.getAddress());
        }

        public int a(AutoConnectDevice o) {
            int i = this.d;
            int i2 = o.d;
            if (i > i2) {
                return -1;
            }
            if (i < i2) {
                return 1;
            }
            return 0;
        }

        public String toString() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13067, new Class[0], String.class);
            return proxy.isSupported ? (String) proxy.result : this.c.getAddress();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(com.telink.ble.mesh.foundation.AutoConnectDevicesManager.AutoConnectDevice r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0045 }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x0045 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0045 }
            r4 = 0
            r5 = 13063(0x3307, float:1.8305E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0045 }
            java.lang.Class<com.telink.ble.mesh.foundation.AutoConnectDevicesManager$AutoConnectDevice> r0 = com.telink.ble.mesh.foundation.AutoConnectDevicesManager.AutoConnectDevice.class
            r6[r2] = r0     // Catch:{ all -> 0x0045 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0045 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0045 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x001f
            monitor-exit(r8)
            return
        L_0x001f:
            r0 = r8
            java.util.List<com.telink.ble.mesh.foundation.AutoConnectDevicesManager$AutoConnectDevice> r1 = r0.d     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.contains(r9)     // Catch:{ all -> 0x0045 }
            if (r1 != 0) goto L_0x0043
            java.util.List<com.telink.ble.mesh.foundation.AutoConnectDevicesManager$AutoConnectDevice> r1 = r0.d     // Catch:{ all -> 0x0045 }
            r1.add(r9)     // Catch:{ all -> 0x0045 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0045 }
            r1.<init>()     // Catch:{ all -> 0x0045 }
            java.lang.String r2 = "autoConnect埋点 添加到缓存列表:"
            r1.append(r2)     // Catch:{ all -> 0x0045 }
            java.util.List<com.telink.ble.mesh.foundation.AutoConnectDevicesManager$AutoConnectDevice> r2 = r0.d     // Catch:{ all -> 0x0045 }
            r1.append(r2)     // Catch:{ all -> 0x0045 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0045 }
            com.leedarson.serviceimpl.reporters.c.f(r1)     // Catch:{ all -> 0x0045 }
        L_0x0043:
            monitor-exit(r8)
            return
        L_0x0045:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.AutoConnectDevicesManager.a(com.telink.ble.mesh.foundation.AutoConnectDevicesManager$AutoConnectDevice):void");
    }

    public synchronized AutoConnectDevice c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13064, new Class[0], AutoConnectDevice.class);
        if (proxy.isSupported) {
            return (AutoConnectDevice) proxy.result;
        }
        if (this.d.size() == 0) {
            return null;
        }
        int i = this.c + 1;
        this.c = i;
        if (i < this.d.size()) {
            return this.d.get(this.c);
        }
        this.c = -1;
        return c();
    }

    public synchronized void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13065, new Class[0], Void.TYPE).isSupported) {
            this.d.clear();
            b.a("重新扫描设备节点，清除旧上线缓存列表");
        }
    }
}
