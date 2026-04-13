package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AppKeyAddMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private int u;
    private byte[] v;

    public AppKeyAddMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public void K(int netKeyIndex) {
        this.t = netKeyIndex;
    }

    public void J(int appKeyIndex) {
        this.u = appKeyIndex;
    }

    public void I(byte[] appKey) {
        this.v = appKey;
    }

    public int k() {
        return Opcode.APPKEY_ADD.value;
    }

    public int o() {
        return Opcode.APPKEY_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12445, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        return ByteBuffer.allocate(19).order(byteOrder).put(MeshUtils.l((this.t & 4095) | ((this.u & 4095) << 12), 3, byteOrder)).put(this.v).array();
    }
}
