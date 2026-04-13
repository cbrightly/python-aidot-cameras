package com.telink.ble.mesh.core.networking;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.OpcodeType;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AccessLayerPDU implements NetworkingPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a;
    public byte[] b;

    private AccessLayerPDU() {
    }

    public AccessLayerPDU(int opcode, byte[] params) {
        this.a = opcode;
        this.b = params;
    }

    public static AccessLayerPDU a(byte[] payload) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{payload}, (Object) null, changeQuickRedirect, true, 12800, new Class[]{byte[].class}, AccessLayerPDU.class);
        if (proxy.isSupported) {
            return (AccessLayerPDU) proxy.result;
        }
        AccessLayerPDU accessPDU = new AccessLayerPDU();
        OpcodeType opType = OpcodeType.getByFirstByte(payload[0]);
        accessPDU.a = 0;
        int index = 0;
        int i = 0;
        while (true) {
            int i2 = opType.length;
            if (i < i2) {
                accessPDU.a = ((payload[index] & 255) << (i * 8)) | accessPDU.a;
                i++;
                index++;
            } else {
                int paramLen = payload.length - i2;
                byte[] bArr = new byte[paramLen];
                accessPDU.b = bArr;
                System.arraycopy(payload, index, bArr, 0, paramLen);
                return accessPDU;
            }
        }
    }

    public byte[] b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12801, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int opcodeLen = OpcodeType.getByFirstByte((byte) this.a).length;
        byte[] bArr = this.b;
        if (bArr == null || bArr.length == 0) {
            return MeshUtils.l(this.a, opcodeLen, ByteOrder.LITTLE_ENDIAN);
        }
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + opcodeLen);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        return allocate.order(byteOrder).put(MeshUtils.l(this.a, opcodeLen, byteOrder)).put(this.b).array();
    }
}
