package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;

public class ProvisioningRecordsGetPDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;

    public byte[] a() {
        return null;
    }

    public byte getState() {
        return 12;
    }
}
