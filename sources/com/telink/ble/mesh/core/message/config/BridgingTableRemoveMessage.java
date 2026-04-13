package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BridgingTableRemoveMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int t;
    public int u;
    public int v;
    public int w;

    public int k() {
        return Opcode.BRIDGING_TABLE_REMOVE.value;
    }

    public int o() {
        return Opcode.BRIDGING_TABLE_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12452, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        return ByteBuffer.allocate(7).order(byteOrder).put(MeshUtils.l((this.t & 4095) | ((this.u & 4095) << 12), 3, byteOrder)).putShort((short) this.v).putShort((short) this.w).array();
    }
}
