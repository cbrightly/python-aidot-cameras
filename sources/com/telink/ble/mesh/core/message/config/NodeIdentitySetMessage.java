package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NodeIdentitySetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private int u;

    public NodeIdentitySetMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public int k() {
        return Opcode.NODE_ID_SET.value;
    }

    public int o() {
        return Opcode.NODE_ID_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12509, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int i = this.t & 4095;
        return ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN).putShort((short) (this.t & 4095)).put((byte) this.u).array();
    }

    public void J(int netKeyIndex) {
        this.t = netKeyIndex;
    }

    public void I(int identity) {
        this.u = identity;
    }
}
