package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ProvisioningCapabilityPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte[] a;
    public byte b;
    public short c;
    public byte d;
    public byte e;
    public byte f;
    public short g;
    public byte h;
    public short i;

    public static ProvisioningCapabilityPDU b(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 12968, new Class[]{byte[].class}, ProvisioningCapabilityPDU.class);
        if (proxy.isSupported) {
            return (ProvisioningCapabilityPDU) proxy.result;
        }
        if (data == null || data.length < 11) {
            return null;
        }
        ProvisioningCapabilityPDU capability = new ProvisioningCapabilityPDU();
        capability.a = data;
        int index = 0 + 1;
        capability.b = data[0];
        int index2 = index + 1;
        int index3 = index2 + 1;
        capability.c = (short) ((data[index2] & 255) | ((data[index] & 255) << 8));
        int index4 = index3 + 1;
        capability.d = data[index3];
        int index5 = index4 + 1;
        capability.e = data[index4];
        int index6 = index5 + 1;
        capability.f = data[index5];
        int index7 = index6 + 1;
        int index8 = index7 + 1;
        capability.g = (short) (((data[index6] & 255) << 8) | (data[index7] & 255));
        int index9 = index8 + 1;
        capability.h = data[index8];
        capability.i = (short) (((data[index9] & 255) << 8) | (data[index9 + 1] & 255));
        return capability;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12969, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ProvisioningCapabilityPDU{eleNum=" + this.b + ", algorithms=" + this.c + ", publicKeyType=" + this.d + ", staticOOBType=" + this.e + ", outputOOBSize=" + this.f + ", outputOOBAction=" + this.g + ", inputOOBSize=" + this.h + ", inputOOBAction=" + this.i + '}';
    }

    public byte[] a() {
        return this.a;
    }

    public byte getState() {
        return 1;
    }

    public boolean c() {
        return this.e != 0;
    }
}
