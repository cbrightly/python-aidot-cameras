package com.telink.ble.mesh.core.provisioning.pdu;

import com.meituan.robust.ChangeQuickRedirect;

public class ProvisioningInvitePDU implements ProvisioningStatePDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte a;

    public ProvisioningInvitePDU(byte attentionDuration) {
        this.a = attentionDuration;
    }

    public byte[] a() {
        return new byte[]{this.a};
    }

    public byte getState() {
        return 0;
    }
}
