package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BlobTransferCancelMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long t;

    public int k() {
        return Opcode.BLOB_TRANSFER_CANCEL.value;
    }

    public int o() {
        return Opcode.BLOB_TRANSFER_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12651, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN).putLong(this.t).array();
    }
}
