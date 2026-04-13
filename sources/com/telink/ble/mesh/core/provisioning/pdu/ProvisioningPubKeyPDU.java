package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ProvisioningPubKeyPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte[] a;
    public byte[] b;
    private byte[] c;

    public static ProvisioningPubKeyPDU b(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 12971, new Class[]{byte[].class}, ProvisioningPubKeyPDU.class);
        if (proxy.isSupported) {
            return (ProvisioningPubKeyPDU) proxy.result;
        }
        if (data.length != 64) {
            return null;
        }
        ProvisioningPubKeyPDU pubKeyPDU = new ProvisioningPubKeyPDU();
        pubKeyPDU.c = data;
        byte[] bArr = new byte[32];
        pubKeyPDU.a = bArr;
        pubKeyPDU.b = new byte[32];
        System.arraycopy(data, 0, bArr, 0, 32);
        System.arraycopy(data, 32, pubKeyPDU.b, 0, 32);
        return pubKeyPDU;
    }

    public byte[] a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12972, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bArr = this.c;
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = this.a;
        if (bArr2 == null || this.b == null) {
            return null;
        }
        byte[] re = new byte[64];
        System.arraycopy(bArr2, 0, re, 0, bArr2.length);
        byte[] bArr3 = this.b;
        System.arraycopy(bArr3, 0, re, 32, bArr3.length);
        return re;
    }

    public byte getState() {
        return 3;
    }
}
