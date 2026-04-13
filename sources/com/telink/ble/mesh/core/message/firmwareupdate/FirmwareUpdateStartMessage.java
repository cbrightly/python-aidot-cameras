package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FirmwareUpdateStartMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;
    private int u;
    private long v;
    private int w;
    private byte[] x;

    public int k() {
        return Opcode.FIRMWARE_UPDATE_START.value;
    }

    public int o() {
        return Opcode.FIRMWARE_UPDATE_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12614, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(this.x.length + 12).order(ByteOrder.LITTLE_ENDIAN).put(this.t).putShort((short) this.u).putLong(this.v).put((byte) this.w).put(this.x).array();
    }
}
