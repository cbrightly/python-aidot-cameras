package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;

public class ProvisioningRandomPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte[] a;

    public ProvisioningRandomPDU(byte[] confirm) {
        this.a = confirm;
    }

    public byte[] a() {
        return this.a;
    }

    public byte getState() {
        return 6;
    }
}
