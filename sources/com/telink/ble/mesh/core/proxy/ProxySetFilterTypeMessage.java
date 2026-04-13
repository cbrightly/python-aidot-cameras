package com.telink.ble.mesh.core.proxy;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ProxySetFilterTypeMessage extends ProxyConfigurationMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte a;

    public ProxySetFilterTypeMessage(byte filterType) {
        this.a = filterType;
    }

    public byte b() {
        return 0;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12985, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return new byte[]{b(), this.a};
    }
}
