package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BlobChunkTransferMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private byte[] u;

    public static BlobChunkTransferMessage I(int destinationAddress, int appKeyIndex, int chunkNumber, byte[] chunkData) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(chunkNumber), chunkData};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12636, new Class[]{cls, cls, cls, byte[].class}, BlobChunkTransferMessage.class);
        if (proxy.isSupported) {
            return (BlobChunkTransferMessage) proxy.result;
        }
        BlobChunkTransferMessage message = new BlobChunkTransferMessage(destinationAddress, appKeyIndex);
        message.C(1);
        message.t = chunkNumber;
        message.u = chunkData;
        return message;
    }

    public BlobChunkTransferMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.BLOB_CHUNK_TRANSFER.value;
    }

    public int o() {
        return -1;
    }

    public byte[] l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12637, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return ByteBuffer.allocate(this.u.length + 2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.t).put(this.u).array();
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12638, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "BlobChunkTransferMessage{chunkNumber=" + this.t + ", chunkData=" + e.a(this.u) + '}';
    }
}
