package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;

public class LightnessSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte u = 0;
    private byte v = 0;
    private byte w = 0;
    private boolean x = false;
    private boolean y = false;

    public static LightnessSetMessage I(int address, int appKeyIndex, int lightness, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Integer(lightness), new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12720, new Class[]{cls, cls, cls, Boolean.TYPE, cls}, LightnessSetMessage.class);
        if (proxy.isSupported) {
            return (LightnessSetMessage) proxy.result;
        }
        LightnessSetMessage message = new LightnessSetMessage(address, appKeyIndex);
        message.t = lightness;
        message.x = ack;
        message.C(rspMax);
        return message;
    }

    public LightnessSetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        H(2);
        e(150);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12721, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.x ? Opcode.LIGHTNESS_STATUS.value : super.o();
    }

    public int k() {
        return (this.x ? Opcode.LIGHTNESS_SET : Opcode.LIGHTNESS_SET_NOACK).value;
    }

    public byte[] l() {
        if (this.y) {
            int i = this.t;
            return new byte[]{(byte) i, (byte) (i >> 8), this.u, this.v, this.w};
        }
        int i2 = this.t;
        return new byte[]{(byte) i2, (byte) (i2 >> 8), this.u};
    }
}
