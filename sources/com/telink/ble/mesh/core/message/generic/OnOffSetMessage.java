package com.telink.ble.mesh.core.message.generic;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class OnOffSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;
    private byte u = 0;
    private byte v = 0;
    private byte w = 0;
    private boolean x = false;
    private boolean y = false;

    public static OnOffSetMessage I(int address, int appKeyIndex, int onOff, boolean ack, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Integer(onOff), new Byte(ack ? (byte) 1 : 0), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12679, new Class[]{cls, cls, cls, Boolean.TYPE, cls}, OnOffSetMessage.class);
        if (proxy.isSupported) {
            return (OnOffSetMessage) proxy.result;
        }
        OnOffSetMessage message = new OnOffSetMessage(address, appKeyIndex);
        message.t = (byte) onOff;
        message.x = ack;
        message.H(1);
        message.C(rspMax);
        return message;
    }

    public OnOffSetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        e(150);
    }

    public int o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12680, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return this.x ? Opcode.G_ONOFF_STATUS.value : super.o();
    }

    public int k() {
        return (this.x ? Opcode.G_ONOFF_SET : Opcode.G_ONOFF_SET_NOACK).value;
    }

    public byte[] l() {
        if (this.y) {
            return new byte[]{this.t, this.u, this.v, this.w};
        }
        return new byte[]{this.t, this.u};
    }
}
