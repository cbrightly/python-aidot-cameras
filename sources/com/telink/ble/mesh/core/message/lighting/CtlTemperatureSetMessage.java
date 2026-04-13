package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CtlTemperatureSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private int u;
    private byte v = 0;
    private byte w = 0;
    private byte x = 0;
    private boolean y = false;
    private boolean z = false;

    public static CtlTemperatureSetMessage I(int address, int appKeyIndex, int temperature, int deltaUV, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Integer(temperature), new Integer(deltaUV), new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12696, new Class[]{cls, cls, cls, cls, Boolean.TYPE, cls}, CtlTemperatureSetMessage.class);
        if (proxy.isSupported) {
            return (CtlTemperatureSetMessage) proxy.result;
        }
        CtlTemperatureSetMessage message = new CtlTemperatureSetMessage(address, appKeyIndex);
        message.t = temperature;
        message.u = deltaUV;
        message.y = ack;
        message.C(rspMax);
        return message;
    }

    public CtlTemperatureSetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        H(4);
        e(150);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12697, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.y ? Opcode.LIGHT_CTL_TEMP_STATUS.value : super.o();
    }

    public int k() {
        return (this.y ? Opcode.LIGHT_CTL_TEMP_SET : Opcode.LIGHT_CTL_TEMP_SET_NOACK).value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12698, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.z) {
            return ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u).put(this.v).put(this.w).put(this.x).array();
        }
        return ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u).put(this.v).array();
    }
}
