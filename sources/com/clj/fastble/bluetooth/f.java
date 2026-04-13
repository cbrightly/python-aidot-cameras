package com.clj.fastble.bluetooth;

import android.os.Build;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.utils.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import timber.log.a;

/* compiled from: MultipleBluetoothController */
public class f {
    private final b<String, d> a = new b<>(com.clj.fastble.a.o().p());
    private final HashMap<String, d> b = new HashMap<>();

    public synchronized d b(BleDevice bleDevice) {
        d bleBluetooth;
        bleBluetooth = new d(bleDevice);
        if (!this.b.containsKey(bleBluetooth.O())) {
            this.b.put(bleBluetooth.O(), bleBluetooth);
        }
        return bleBluetooth;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void k(com.clj.fastble.bluetooth.d r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 != 0) goto L_0x0005
            monitor-exit(r2)
            return
        L_0x0005:
            java.util.HashMap<java.lang.String, com.clj.fastble.bluetooth.d> r0 = r2.b     // Catch:{ all -> 0x001c }
            java.lang.String r1 = r3.O()     // Catch:{ all -> 0x001c }
            boolean r0 = r0.containsKey(r1)     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x001a
            java.util.HashMap<java.lang.String, com.clj.fastble.bluetooth.d> r0 = r2.b     // Catch:{ all -> 0x001c }
            java.lang.String r1 = r3.O()     // Catch:{ all -> 0x001c }
            r0.remove(r1)     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r2)
            return
        L_0x001c:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.clj.fastble.bluetooth.f.k(com.clj.fastble.bluetooth.d):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(com.clj.fastble.bluetooth.d r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 != 0) goto L_0x0005
            monitor-exit(r2)
            return
        L_0x0005:
            com.clj.fastble.utils.b<java.lang.String, com.clj.fastble.bluetooth.d> r0 = r2.a     // Catch:{ all -> 0x001c }
            java.lang.String r1 = r3.O()     // Catch:{ all -> 0x001c }
            boolean r0 = r0.containsKey(r1)     // Catch:{ all -> 0x001c }
            if (r0 != 0) goto L_0x001a
            com.clj.fastble.utils.b<java.lang.String, com.clj.fastble.bluetooth.d> r0 = r2.a     // Catch:{ all -> 0x001c }
            java.lang.String r1 = r3.O()     // Catch:{ all -> 0x001c }
            r0.put(r1, r3)     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r2)
            return
        L_0x001c:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.clj.fastble.bluetooth.f.a(com.clj.fastble.bluetooth.d):void");
    }

    public synchronized void j(d bleBluetooth) {
        if (bleBluetooth != null) {
            Iterator<Map.Entry<String, d>> it = this.a.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getKey().contains(bleBluetooth.O())) {
                    it.remove();
                }
            }
        }
    }

    public synchronized boolean h(BleDevice bleDevice) {
        boolean z;
        if (bleDevice != null) {
            if (this.a.containsKey(bleDevice.b())) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    public synchronized d e(BleDevice bleDevice) {
        if (bleDevice != null) {
            if (this.a.containsKey(bleDevice.b())) {
                return this.a.get(bleDevice.b());
            }
        }
        return null;
    }

    public synchronized void c(BleDevice bleDevice) {
        if (h(bleDevice)) {
            e(bleDevice).K();
        } else {
            timber.log.a.i("BleBusiness.auto  client.disconnect   未找到bleDevice=" + bleDevice.toString(), new Object[0]);
        }
    }

    public synchronized void d() {
        for (Map.Entry<String, d> stringBleBluetoothEntry : this.a.entrySet()) {
            ((d) stringBleBluetoothEntry.getValue()).K();
        }
        this.a.clear();
    }

    /* compiled from: MultipleBluetoothController */
    public class a implements Comparator<d> {
        a() {
        }

        /* renamed from: a */
        public int compare(d lhs, d rhs) {
            return lhs.O().compareToIgnoreCase(rhs.O());
        }
    }

    public synchronized List<d> f() {
        List<BleBluetooth> bleBluetoothList;
        bleBluetoothList = new ArrayList<>(this.a.values());
        Collections.sort(bleBluetoothList, new a());
        return bleBluetoothList;
    }

    public synchronized List<BleDevice> g() {
        List<BleDevice> deviceList;
        i();
        deviceList = new ArrayList<>();
        for (d BleBluetooth : f()) {
            if (BleBluetooth != null) {
                deviceList.add(BleBluetooth.N());
            }
        }
        return deviceList;
    }

    public void i() {
        if (Build.VERSION.SDK_INT >= 18) {
            List<d> f = f();
            int i = 0;
            while (f != null && i < f.size()) {
                d bleBluetooth = f.get(i);
                if (!com.clj.fastble.a.o().A(bleBluetooth.N())) {
                    a.b g = timber.log.a.g("MultipleBluetooth");
                    g.h(" refreshConnectedDevice刷新缓存池已连接的蓝牙设备(移除设备) --->" + bleBluetooth.O(), new Object[0]);
                    j(bleBluetooth);
                }
                i++;
            }
        }
    }
}
