package com.telink.ble.mesh.foundation.parameter;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import com.telink.ble.mesh.core.ble.UUIDInfo;
import java.util.UUID;

public class ScanParameters extends Parameters {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LeScanFilter b = new LeScanFilter();

    public static ScanParameters k(boolean provisioned, boolean single) {
        Object[] objArr = {new Byte(provisioned ? (byte) 1 : 0), new Byte(single ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13390, new Class[]{cls, cls}, ScanParameters.class);
        if (proxy.isSupported) {
            return (ScanParameters) proxy.result;
        }
        ScanParameters parameters = new ScanParameters();
        if (provisioned) {
            LeScanFilter leScanFilter = parameters.b;
            leScanFilter.a = new UUID[]{UUIDInfo.g};
        } else {
            LeScanFilter leScanFilter2 = parameters.b;
            leScanFilter2.a = new UUID[]{UUIDInfo.d};
        }
        parameters.h(parameters.b);
        parameters.l(single);
        return parameters;
    }

    public void l(boolean single) {
        Object[] objArr = {new Byte(single ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13391, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            e("com.telink.ble.com.telink.ble.mesh.light.SCAN_SINGLE_MODE", Boolean.valueOf(single));
        }
    }
}
