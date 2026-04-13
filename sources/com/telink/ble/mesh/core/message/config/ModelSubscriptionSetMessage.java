package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ModelSubscriptionSetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t = 0;
    private int u;
    private int v;
    private int w;
    private boolean x = true;

    public static ModelSubscriptionSetMessage I(int destinationAddress, int mode, int elementAddress, int groupAddress, int modelId, boolean isSig) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(mode), new Integer(elementAddress), new Integer(groupAddress), new Integer(modelId), new Byte(isSig ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12487, new Class[]{cls, cls, cls, cls, cls, Boolean.TYPE}, ModelSubscriptionSetMessage.class);
        if (proxy.isSupported) {
            return (ModelSubscriptionSetMessage) proxy.result;
        }
        ModelSubscriptionSetMessage message = new ModelSubscriptionSetMessage(destinationAddress);
        message.t = mode;
        message.u = elementAddress;
        message.v = groupAddress;
        message.w = modelId;
        message.x = isSig;
        return message;
    }

    public ModelSubscriptionSetMessage(int destinationAddress) {
        super(destinationAddress);
        e(120);
    }

    public int k() {
        return (this.t == 0 ? Opcode.CFG_MODEL_SUB_ADD : Opcode.CFG_MODEL_SUB_DEL).value;
    }

    public int o() {
        return Opcode.CFG_MODEL_SUB_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12488, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.x) {
            return ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.u).putShort((short) this.v).putShort((short) this.w).array();
        }
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.u).putShort((short) this.v).putInt(this.w).array();
    }

    public boolean J() {
        return this.x;
    }
}
