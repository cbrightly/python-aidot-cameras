package com.telink.ble.mesh.foundation.parameter;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Parameters {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected Map<String, Object> a = new LinkedHashMap();

    public Parameters() {
        i(0);
        j(10000);
        g(10000);
        f(2);
        e("com.telink.ble.com.telink.ble.mesh.light.COMMON_PROXY_FILTER_INIT_NEEDED", true);
    }

    public void e(String key, Object value) {
        Map<String, Object> map;
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 13375, clsArr, Void.TYPE).isSupported && (map = this.a) != null) {
            map.put(key, value);
        }
    }

    public Object b(String key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 13376, new Class[]{String.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        Map<String, Object> map = this.a;
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    public boolean c(String key, boolean defaultValue) {
        Object[] objArr = {key, new Byte(defaultValue ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13377, new Class[]{String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Map<String, Object> map = this.a;
        if (map == null || !map.containsKey(key)) {
            return defaultValue;
        }
        return ((Boolean) this.a.get(key)).booleanValue();
    }

    public long d(String key, long defaultValue) {
        Object[] objArr = {key, new Long(defaultValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13379, new Class[]{String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        Map<String, Object> map = this.a;
        if (map != null) {
            return ((Long) map.get(key)).longValue();
        }
        return defaultValue;
    }

    public void i(long spacing) {
        Object[] objArr = {new Long(spacing)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13383, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.COMMON_SCAN_MIN_SPACING", Long.valueOf(spacing));
        }
    }

    public void j(long timeout) {
        Object[] objArr = {new Long(timeout)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13384, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.COMMON_SCAN_TIMEOUT", Long.valueOf(timeout));
        }
    }

    public void g(long timeout) {
        Object[] objArr = {new Long(timeout)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13385, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.COMMON_CONNECT_TIMEOUT", Long.valueOf(timeout));
        }
    }

    public void f(int retry) {
        Object[] objArr = {new Integer(retry)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13386, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.COMMON_CONNECT_RETRY", Integer.valueOf(retry));
        }
    }

    public void h(LeScanFilter scanFilter) {
        if (!PatchProxy.proxy(new Object[]{scanFilter}, this, changeQuickRedirect, false, 13387, new Class[]{LeScanFilter.class}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.SCAN_FILTERS", scanFilter);
        }
    }

    public LeScanFilter a(UUID uuid) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uuid}, this, changeQuickRedirect, false, 13389, new Class[]{UUID.class}, LeScanFilter.class);
        if (proxy.isSupported) {
            return (LeScanFilter) proxy.result;
        }
        LeScanFilter scanFilter = new LeScanFilter();
        scanFilter.a = new UUID[]{uuid};
        return scanFilter;
    }
}
