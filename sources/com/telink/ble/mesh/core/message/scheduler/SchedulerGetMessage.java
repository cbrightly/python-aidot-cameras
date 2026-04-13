package com.telink.ble.mesh.core.message.scheduler;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.lighting.LightingMessage;

public class SchedulerGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.SCHD_GET.value;
    }

    public int o() {
        return Opcode.SCHD_STATUS.value;
    }
}
