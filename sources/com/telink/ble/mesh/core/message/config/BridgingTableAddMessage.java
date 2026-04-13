package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BridgingTableAddMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte t;
    public int u;
    public int v;
    public int w;
    public int x;

    public int k() {
        return Opcode.BRIDGING_TABLE_ADD.value;
    }

    public int o() {
        return Opcode.BRIDGING_TABLE_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12451, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        return ByteBuffer.allocate(8).order(byteOrder).put(this.t).put(MeshUtils.l((this.u & 4095) | ((this.v & 4095) << 12), 3, byteOrder)).putShort((short) this.w).putShort((short) this.x).array();
    }
}
