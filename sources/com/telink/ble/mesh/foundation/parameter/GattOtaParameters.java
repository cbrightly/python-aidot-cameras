package com.telink.ble.mesh.foundation.parameter;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.telink.ble.mesh.entity.ConnectionFilter;

public class GattOtaParameters extends Parameters {
    public static ChangeQuickRedirect changeQuickRedirect;

    public GattOtaParameters(ConnectionFilter filter, byte[] firmware) {
        e("com.telink.ble.com.telink.ble.mesh.light.COMMON_PROXY_FILTER_INIT_NEEDED", true);
        e("com.telink.ble.com.telink.ble.mesh.light.ACTION_CONNECTION_FILTER", filter);
        k(firmware);
    }

    public void k(byte[] firmware) {
        if (!PatchProxy.proxy(new Object[]{firmware}, this, changeQuickRedirect, false, 13374, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.ACTION_OTA_FIRMWARE", firmware);
        }
    }
}
