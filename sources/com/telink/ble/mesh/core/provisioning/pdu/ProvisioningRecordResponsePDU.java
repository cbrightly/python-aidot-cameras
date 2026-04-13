package com.telink.ble.mesh.core.provisioning.pdu;

import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import java.nio.ByteOrder;

public class ProvisioningRecordResponsePDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte[] a;
    public byte b;
    public int c;
    public int d;
    public int e;
    public byte[] f;

    public static ProvisioningRecordResponsePDU b(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 12974, new Class[]{byte[].class}, ProvisioningRecordResponsePDU.class);
        if (proxy.isSupported) {
            return (ProvisioningRecordResponsePDU) proxy.result;
        }
        ProvisioningRecordResponsePDU responsePDU = new ProvisioningRecordResponsePDU();
        responsePDU.a = data;
        int index = 0 + 1;
        responsePDU.b = data[0];
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        responsePDU.c = MeshUtils.b(data, index, 2, byteOrder);
        int index2 = index + 2;
        responsePDU.d = MeshUtils.b(data, index2, 2, byteOrder);
        int index3 = index2 + 2;
        responsePDU.e = MeshUtils.b(data, index3, 2, byteOrder);
        int index4 = index3 + 2;
        byte[] bArr = new byte[(data.length - index4)];
        responsePDU.f = bArr;
        System.arraycopy(data, index4, bArr, 0, bArr.length);
        return responsePDU;
    }

    public byte getState() {
        return 11;
    }

    public byte[] a() {
        return this.a;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12975, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ProvisioningRecordResponsePDU{status=" + this.b + ", recordID=" + this.c + ", fragmentOffset=" + this.d + ", totalLength=" + this.e + ", data=" + e.a(this.f) + '}';
    }
}
