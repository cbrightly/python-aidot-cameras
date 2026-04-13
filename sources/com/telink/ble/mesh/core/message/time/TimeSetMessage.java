package com.telink.ble.mesh.core.message.time;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TimeSetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long t;
    private byte u;
    private byte v;
    private byte w;
    private int x;
    private int y;
    private boolean z = false;

    public static TimeSetMessage I(int address, int appKeyIndex, long taiSeconds, int zoneOffset, int rspMax) {
        Object[] objArr = {new Integer(address), new Integer(appKeyIndex), new Long(taiSeconds), new Integer(zoneOffset), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12793, new Class[]{cls, cls, Long.TYPE, cls, cls}, TimeSetMessage.class);
        if (proxy.isSupported) {
            return (TimeSetMessage) proxy.result;
        }
        TimeSetMessage message = new TimeSetMessage(address, appKeyIndex);
        message.t = taiSeconds;
        message.y = zoneOffset;
        message.C(rspMax);
        return message;
    }

    public TimeSetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int o() {
        if (this.z) {
            return Opcode.TIME_STATUS.value;
        }
        return -1;
    }

    public int k() {
        return Opcode.TIME_SET.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12794, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) ((int) this.t)).put((byte) ((int) (this.t >> 8))).put((byte) ((int) (this.t >> 16))).put((byte) ((int) (this.t >> 24))).put((byte) ((int) (this.t >> 32))).put(this.u).put(this.v).putShort((short) ((this.x << 1) | this.w)).put((byte) this.y);
        return byteBuffer.array();
    }

    public void J(boolean ack) {
        this.z = ack;
    }
}
