package com.telink.ble.mesh.core.message.rp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ScanStartMessage extends RemoteProvisionMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;
    private byte u;
    private byte[] v;

    public int k() {
        return Opcode.REMOTE_PROV_SCAN_START.value;
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12752, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : super.o();
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12753, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer bf = ByteBuffer.allocate(this.v == null ? 2 : 18).order(ByteOrder.LITTLE_ENDIAN).put(this.t).put(this.u);
        byte[] bArr = this.v;
        if (bArr != null) {
            bf.put(bArr);
        }
        return bf.array();
    }
}
