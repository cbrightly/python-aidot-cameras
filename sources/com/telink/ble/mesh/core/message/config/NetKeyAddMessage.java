package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NetKeyAddMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int t;
    public byte[] u;

    public int k() {
        return Opcode.NETKEY_ADD.value;
    }

    public int o() {
        return Opcode.NETKEY_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12495, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(18).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).put(this.u).array();
    }
}
