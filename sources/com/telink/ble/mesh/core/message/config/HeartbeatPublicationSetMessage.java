package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class HeartbeatPublicationSetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte u;
    private byte v;
    private byte w;
    private int x;
    private int y;

    public int k() {
        return Opcode.HEARTBEAT_PUB_SET.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12466, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(9).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort((short) this.t).put(this.u).put(this.v).put(this.w).putShort((short) this.x).putShort((short) this.y);
        return byteBuffer.array();
    }
}
