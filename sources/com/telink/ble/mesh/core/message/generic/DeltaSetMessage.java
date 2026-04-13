package com.telink.ble.mesh.core.message.generic;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DeltaSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte u;
    private byte v;
    private byte w;
    private boolean x;
    private boolean y;

    public int k() {
        return (this.x ? Opcode.G_DELTA_SET : Opcode.G_DELTA_SET_NOACK).value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12671, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.y) {
            return ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putInt(this.t).put(this.u).put(this.v).put(this.w).array();
        }
        return ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).putInt(this.t).put(this.u).array();
    }
}
