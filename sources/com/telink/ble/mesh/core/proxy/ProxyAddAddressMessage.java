package com.telink.ble.mesh.core.proxy;

import androidx.annotation.NonNull;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ProxyAddAddressMessage extends ProxyConfigurationMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int[] a;

    public ProxyAddAddressMessage(@NonNull int[] addressArray) {
        this.a = addressArray;
    }

    public byte b() {
        return 1;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12978, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer buffer = ByteBuffer.allocate((this.a.length * 2) + 1).order(ByteOrder.BIG_ENDIAN).put(b());
        for (int address : this.a) {
            buffer.putShort((short) address);
        }
        return buffer.array();
    }
}
