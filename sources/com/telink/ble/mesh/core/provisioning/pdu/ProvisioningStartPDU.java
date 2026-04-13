package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.provisioning.AuthenticationMethod;

public class ProvisioningStartPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte a;
    public byte b;
    public byte c;
    public byte d;
    public byte e;

    public static ProvisioningStartPDU b(boolean staticOOBSupported) {
        byte b2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(staticOOBSupported ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 12977, new Class[]{Boolean.TYPE}, ProvisioningStartPDU.class);
        if (proxy.isSupported) {
            return (ProvisioningStartPDU) proxy.result;
        }
        ProvisioningStartPDU startPDU = new ProvisioningStartPDU();
        startPDU.a = 0;
        startPDU.b = 0;
        if (staticOOBSupported) {
            b2 = AuthenticationMethod.StaticOOB.value;
        } else {
            b2 = AuthenticationMethod.NoOOB.value;
        }
        startPDU.c = b2;
        startPDU.d = 0;
        startPDU.e = 0;
        return startPDU;
    }

    public void c(boolean publicKeyEnable) {
        this.b = publicKeyEnable ? (byte) 1 : 0;
    }

    public byte[] a() {
        return new byte[]{this.a, this.b, this.c, this.d, this.e};
    }

    public byte getState() {
        return 2;
    }
}
