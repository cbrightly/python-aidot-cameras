package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class SubnetBridgeSetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    public byte t;

    public int k() {
        return Opcode.SUBNET_BRIDGE_SET.value;
    }

    public int o() {
        return Opcode.SUBNET_BRIDGE_STATUS.value;
    }

    public byte[] l() {
        return new byte[]{this.t};
    }
}
