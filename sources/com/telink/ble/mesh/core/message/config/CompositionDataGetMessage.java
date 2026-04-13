package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class CompositionDataGetMessage extends ConfigMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public CompositionDataGetMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public int k() {
        return Opcode.COMPOSITION_DATA_GET.value;
    }

    public int o() {
        return Opcode.COMPOSITION_DATA_STATUS.value;
    }

    public byte[] l() {
        return new byte[]{-1};
    }
}
