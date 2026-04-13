package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BlobTransferStartMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private TransferMode t;
    private long u;
    private int v;
    private int w;
    private int x;

    public int k() {
        return Opcode.BLOB_TRANSFER_START.value;
    }

    public int o() {
        return Opcode.BLOB_TRANSFER_STATUS.value;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12654, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN).put((byte) (this.t.mode << 6)).putLong(this.u).putInt(this.v).put((byte) this.w).putShort((short) this.x).array();
    }
}
