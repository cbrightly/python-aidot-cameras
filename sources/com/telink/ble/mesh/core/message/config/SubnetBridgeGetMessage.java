package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class SubnetBridgeGetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.SUBNET_BRIDGE_GET.value;
    }

    public int o() {
        return Opcode.SUBNET_BRIDGE_STATUS.value;
    }

    public byte[] l() {
        return null;
    }
}
