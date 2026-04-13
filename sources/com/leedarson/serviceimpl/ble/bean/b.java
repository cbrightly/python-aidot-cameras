package com.leedarson.serviceimpl.ble.bean;

import android.text.TextUtils;
import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.blec075.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import timber.log.a;

/* compiled from: ScanCache */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "";
    private HashMap<String, BleDevice> b = new HashMap<>();

    public void b(String mac, BleDevice dev) {
        if (!PatchProxy.proxy(new Object[]{mac, dev}, this, changeQuickRedirect, false, 6253, new Class[]{String.class, BleDevice.class}, Void.TYPE).isSupported) {
            a.i("ConnectRetry scanCache.put--> mac=" + mac + "  bleDevice  mac=" + dev.c() + "   key=" + dev.b() + "  getScanRecord.hex =" + h.b(dev.f()), new Object[0]);
            if (!TextUtils.isEmpty(dev.h())) {
                d(dev);
            }
            if (mac.contains("30:04") || mac.contains("3004")) {
                a.i("sufun:cacheDeviceMap  开始更新  3003缓存", new Object[0]);
            }
            this.b.put(mac.toUpperCase().replace(":", ""), dev);
            a("putdevice");
        }
    }

    private void a(String ref) {
    }

    private void d(BleDevice bleDevice) {
        if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6256, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(bleDevice.h()) || !TextUtils.isEmpty(h.b(bleDevice.f()))) {
                Iterator<BleDevice> it = this.b.values().iterator();
                ArrayList<String> _preToRemoveVituralMacs = new ArrayList<>();
                for (String key : this.b.keySet()) {
                    BleDevice _cacheDevice = this.b.get(key);
                    if (!TextUtils.isEmpty(_cacheDevice.i()) && _cacheDevice.i().equals(bleDevice.i())) {
                        _preToRemoveVituralMacs.add(key);
                    }
                }
                for (int i = 0; i < _preToRemoveVituralMacs.size(); i++) {
                    if (_preToRemoveVituralMacs.get(i).contains("30:04") || _preToRemoveVituralMacs.get(i).contains("3004")) {
                        a.i("sufun:cacheDeviceMap 开始删除3004缓存", new Object[0]);
                    }
                    this.b.remove(_preToRemoveVituralMacs.get(i));
                }
                a("removeExpireBledeviceCache");
            }
        }
    }

    public BleDevice c(String likeMac, String likeAdvert) {
        Class<String> cls = String.class;
        boolean z = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{likeMac, likeAdvert}, this, changeQuickRedirect, false, 6257, new Class[]{cls, cls}, BleDevice.class);
        if (proxy.isSupported) {
            return (BleDevice) proxy.result;
        }
        BleDevice connectDevice = null;
        Iterator<BleDevice> iterator = this.b.values().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                break;
            }
            BleDevice device = iterator.next();
            a.g("").m("devices1=" + h.b(device.f()) + device.d(), new Object[0]);
            if (TextUtils.isEmpty(likeAdvert) || !h.b(device.f()).toLowerCase().contains(likeAdvert.replace("\"", "").toLowerCase())) {
                if (TextUtils.isEmpty(likeMac) || !h.b(device.f()).toLowerCase().contains(likeMac.toLowerCase())) {
                    if (!TextUtils.isEmpty(likeMac) && likeMac.equals(device.d())) {
                        a.g("").h("devices3=" + device.d(), new Object[0]);
                        connectDevice = device;
                        break;
                    }
                } else {
                    a.g("").h("ConnectRetry scanCache.queryDevice record=" + h.b(device.f()) + "mac=" + device.c() + device.d(), new Object[0]);
                    connectDevice = device;
                    break;
                }
            } else {
                a.g("").h("devices2=" + h.b(device.f()) + device.d(), new Object[0]);
                connectDevice = device;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("queryDevice  connectDevice==null?");
        if (connectDevice == null) {
            z = true;
        }
        sb.append(z);
        a(sb.toString());
        return connectDevice;
    }
}
