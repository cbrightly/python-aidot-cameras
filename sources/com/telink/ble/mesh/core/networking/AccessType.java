package com.telink.ble.mesh.core.networking;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum AccessType {
    APPLICATION(1),
    DEVICE(0);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final byte akf;

    public static AccessType getByAkf(byte akf2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(akf2)}, (Object) null, changeQuickRedirect, true, 12804, new Class[]{Byte.TYPE}, AccessType.class);
        if (proxy.isSupported) {
            return (AccessType) proxy.result;
        }
        for (AccessType at : values()) {
            if (at.akf == akf2) {
                return at;
            }
        }
        return null;
    }

    private AccessType(int akf2) {
        this.akf = (byte) akf2;
    }
}
