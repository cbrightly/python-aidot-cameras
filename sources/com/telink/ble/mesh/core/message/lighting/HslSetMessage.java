package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class HslSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A = false;
    private int t;
    private int u;
    private int v;
    private byte w = 0;
    private byte x = 0;
    private byte y = 0;
    private boolean z = false;

    public static HslSetMessage I(int address, int appKeyIndex, int lightness, int hue, int saturation, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Integer(lightness), new Integer(hue), new Integer(saturation), new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12705, new Class[]{cls, cls, cls, cls, cls, Boolean.TYPE, cls}, HslSetMessage.class);
        if (proxy.isSupported) {
            return (HslSetMessage) proxy.result;
        }
        HslSetMessage message = new HslSetMessage(address, appKeyIndex);
        message.t = lightness;
        message.u = hue;
        message.v = saturation;
        message.z = ack;
        message.C(rspMax);
        return message;
    }

    public HslSetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        H(6);
        e(150);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12706, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.z ? Opcode.LIGHT_HSL_STATUS.value : super.o();
    }

    public int k() {
        return (this.z ? Opcode.LIGHT_HSL_SET : Opcode.LIGHT_HSL_SET_NOACK).value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12707, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.A) {
            return ByteBuffer.allocate(9).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u).putShort((short) this.v).put(this.w).put(this.x).put(this.y).array();
        }
        return ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).putShort((short) this.u).putShort((short) this.v).put(this.w).array();
    }
}
