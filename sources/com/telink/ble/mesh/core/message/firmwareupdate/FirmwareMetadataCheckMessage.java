package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FirmwareMetadataCheckMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte[] u;

    public int k() {
        return Opcode.FIRMWARE_UPDATE_FIRMWARE_METADATA_CHECK.value;
    }

    public int o() {
        return Opcode.FIRMWARE_UPDATE_FIRMWARE_METADATA_STATUS.value;
    }

    public byte[] l() {
        int metadataLen = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12593, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bArr = this.u;
        if (bArr != null && bArr.length > 0) {
            metadataLen = bArr.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(metadataLen + 1).order(ByteOrder.LITTLE_ENDIAN).put((byte) this.t);
        if (metadataLen != 0) {
            buffer.put(this.u);
        }
        return buffer.array();
    }
}
