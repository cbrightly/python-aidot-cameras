package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CtlSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A;
    private int t;
    private int u;
    private int v;
    private byte w;
    private byte x;
    private byte y;
    private boolean z;

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12687, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.z ? Opcode.LIGHT_CTL_STATUS.value : super.o();
    }

    public int k() {
        return (this.z ? Opcode.LIGHT_CTL_SET : Opcode.LIGHT_CTL_SET_NOACK).value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12688, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.A) {
            return ByteBuffer.allocate(9).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u).putShort((short) this.v).put(this.w).put(this.x).put(this.y).array();
        }
        return ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u).putShort((short) this.v).put(this.w).array();
    }
}
