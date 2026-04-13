package com.telink.ble.mesh.core.message.rp;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class LinkOpenMessage extends RemoteProvisionMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] t;

    public int k() {
        return Opcode.REMOTE_PROV_LINK_OPEN.value;
    }

    public int o() {
        return Opcode.REMOTE_PROV_LINK_STS.value;
    }

    public byte[] l() {
        return this.t;
    }
}
