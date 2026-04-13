package com.leedarson.serviceimpl.ble.manager;

import android.util.Pair;
import androidx.collection.ArrayMap;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: BLEManage */
public class c {
    public static ArrayMap a = new ArrayMap();
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String b;
    private HashMap<String, Pair<String, byte[]>> c;
    private com.leedarson.serviceimpl.ble.bean.b d;
    private int e;

    /* compiled from: BLEManage */
    public static class b {
        /* access modifiers changed from: private */
        public static c a = new c();
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    private c() {
        this.b = c.class.getSimpleName();
        this.c = new HashMap<>();
        this.e = 1;
        this.d = new com.leedarson.serviceimpl.ble.bean.b();
    }

    public com.leedarson.serviceimpl.ble.bean.b c() {
        return this.d;
    }

    public int d() {
        int sn;
        int sn2 = this.e;
        if (sn2 % NeedPermissionEvent.PER_IPC_SPEAK_PERM == 0) {
            sn = 1;
        } else {
            sn = sn2 % NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        }
        this.e = sn + 1;
        return sn;
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6259, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("BleC075ServiceImpl").h("BLE.TRV cleanConnectMacSet 清空BLE自动连接池", new Object[0]);
            this.c.clear();
        }
    }

    public void e(String devId, String realMac) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{devId, realMac}, this, changeQuickRedirect, false, 6261, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (this.c.containsKey(devId)) {
                timber.log.a.g("BleC075ServiceImpl").h("BLE.TRV removeConnectMacSet 移除BLE自动连接池 devId:%s", devId);
                this.c.remove(devId);
                return;
            }
            Iterator<Pair<String, byte[]>> iterator = this.c.values().iterator();
            while (iterator.hasNext()) {
                String next = (String) iterator.next().first;
                String reserveMac = com.leedarson.serviceimpl.ble.utils.b.a(realMac.replace(":", ""));
                if (next.equals(realMac) || next.equals(reserveMac)) {
                    timber.log.a.g("BleC075ServiceImpl").h("移除BLE自动连接池 realMac:%s", realMac);
                    timber.log.a.g("BleC075ServiceImpl").h("BLE.TRV removeConnectMacSet 移除BLE自动连接池 devId:%s   realMac:%s    reserveMac:%s", devId, realMac, reserveMac);
                    iterator.remove();
                }
            }
        }
    }

    public static c b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 6265, new Class[0], c.class);
        return proxy.isSupported ? (c) proxy.result : b.a;
    }
}
