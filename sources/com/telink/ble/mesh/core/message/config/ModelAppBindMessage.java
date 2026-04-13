package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ModelAppBindMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private int u;
    private int v;
    private boolean w = true;

    public ModelAppBindMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public void J(int elementAddress) {
        this.t = elementAddress;
    }

    public void I(int appKeyIndex) {
        this.u = appKeyIndex;
    }

    public void K(int modelIdentifier) {
        this.v = modelIdentifier;
    }

    public void L(boolean sigModel) {
        this.w = sigModel;
    }

    public int k() {
        return Opcode.MODE_APP_BIND.value;
    }

    public int o() {
        return Opcode.MODE_APP_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12467, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer paramsBuffer = ByteBuffer.allocate(this.w ? 6 : 8).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u);
        if (this.w) {
            paramsBuffer.putShort((short) this.v);
        } else {
            paramsBuffer.putInt(this.v);
        }
        return paramsBuffer.array();
    }
}
