package com.telink.ble.mesh.core.networking.transport.lower;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;

public class UnsegmentedControlMessagePDU extends LowerTransportPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    final int g = 0;
    private int h;
    byte[] i;

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12922, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte header = (byte) (this.h | 0);
        byte[] bArr = this.i;
        if (bArr == null) {
            return new byte[]{header};
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(bArr.length + 1);
        byteBuffer.put(header).put(this.i);
        return byteBuffer.array();
    }
}
