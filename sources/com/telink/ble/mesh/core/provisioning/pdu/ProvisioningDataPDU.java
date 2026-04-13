package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;

public class ProvisioningDataPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte[] a;

    public ProvisioningDataPDU(byte[] encryptedData) {
        this.a = encryptedData;
    }

    public byte[] a() {
        return this.a;
    }

    public byte getState() {
        return 7;
    }
}
