package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class NodeResetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public NodeResetMessage(int destinationAddress) {
        super(destinationAddress);
        e(100);
    }

    public int k() {
        return Opcode.NODE_RESET.value;
    }

    public byte[] l() {
        return null;
    }

    public int o() {
        return Opcode.NODE_RESET_STATUS.value;
    }
}
