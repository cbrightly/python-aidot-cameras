package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ModelSubscriptionGetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean t = true;
    private int u;
    private int v;

    public static ModelSubscriptionGetMessage I(int destinationAddress, int elementAddress, int modelId, boolean isSig) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(elementAddress), new Integer(modelId), new Byte(isSig ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12479, new Class[]{cls, cls, cls, Boolean.TYPE}, ModelSubscriptionGetMessage.class);
        if (proxy.isSupported) {
            return (ModelSubscriptionGetMessage) proxy.result;
        }
        ModelSubscriptionGetMessage message = new ModelSubscriptionGetMessage(destinationAddress);
        message.u = elementAddress;
        message.v = modelId;
        message.t = isSig;
        return message;
    }

    public ModelSubscriptionGetMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public int k() {
        return Opcode.CFG_SIG_MODEL_SUB_GET.value;
    }

    public int o() {
        return Opcode.CFG_SIG_MODEL_SUB_LIST.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12480, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.t) {
            return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.u).putShort((short) this.v).array();
        }
        return null;
    }
}
