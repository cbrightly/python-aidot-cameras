package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class NetworkTransmitSetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int t;
    private int u;

    public int k() {
        return Opcode.CFG_NW_TRANSMIT_SET.value;
    }

    public int o() {
        return Opcode.CFG_NW_TRANSMIT_STATUS.value;
    }

    public byte[] l() {
        return new byte[]{(byte) ((this.t & 7) | (this.u << 3))};
    }
}
