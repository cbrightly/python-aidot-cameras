package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class CtlTemperatureGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.LIGHT_CTL_TEMP_GET.value;
    }

    public int o() {
        return Opcode.LIGHT_CTL_TEMP_STATUS.value;
    }
}
