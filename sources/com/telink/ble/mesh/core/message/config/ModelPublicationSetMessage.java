package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.entity.ModelPublication;

public class ModelPublicationSetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ModelPublication t;

    public ModelPublicationSetMessage(int destinationAddress, ModelPublication modelPublication) {
        super(destinationAddress);
        this.t = modelPublication;
        this.o = Opcode.CFG_MODEL_PUB_STATUS.value;
        this.m = 1;
    }

    public int k() {
        return Opcode.CFG_MODEL_PUB_SET.value;
    }

    public int o() {
        return this.o;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12473, new Class[0], byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : this.t.toBytes();
    }
}
