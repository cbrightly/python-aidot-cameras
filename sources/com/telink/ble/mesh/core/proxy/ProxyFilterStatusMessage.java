package com.telink.ble.mesh.core.proxy;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ProxyFilterStatusMessage extends ProxyConfigurationMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte a;
    private int b;

    public static ProxyFilterStatusMessage b(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 12980, new Class[]{byte[].class}, ProxyFilterStatusMessage.class);
        if (proxy.isSupported) {
            return (ProxyFilterStatusMessage) proxy.result;
        }
        if (data.length != 4) {
            return null;
        }
        ProxyFilterStatusMessage instance = new ProxyFilterStatusMessage();
        int index = 0 + 1;
        if (data[0] != 3) {
            return null;
        }
        instance.a = data[index];
        instance.b = MeshUtils.b(data, index + 1, 2, ByteOrder.BIG_ENDIAN);
        return instance;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12981, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).put(this.a).putShort((short) this.b).array();
    }

    public byte c() {
        return this.a;
    }
}
