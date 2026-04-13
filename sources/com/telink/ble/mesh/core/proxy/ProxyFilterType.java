package com.telink.ble.mesh.core.proxy;

import com.meituan.robust.ChangeQuickRedirect;

public enum ProxyFilterType {
    WhiteList((byte) 0),
    BlackList((byte) 1);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final byte value;

    private ProxyFilterType(byte value2) {
        this.value = value2;
    }
}
